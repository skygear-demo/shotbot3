(ns shotbot.render.utils
  (:require [clojure.string :as string]
            [oops.core :refer [oset! oget]]
            [goog.fs :as fs]
            [cljsjs.jszip]
            [cljsjs.filesaverjs]
            [shotbot.render.templates :as templates]))

(defn render-to-canvas
  "Takes a render data map and a optional canvas element, renders on the canvas and returns it.
  A new canvas is created if one is not provided."
  ([{[width height] :resolution :as render-data}]
   (let [canvas (doto (.createElement js/document "canvas")
                  (oset! "width" width)
                  (oset! "height" height))]
     (render-to-canvas render-data canvas)))
  ([{[width height] :resolution :keys [render]} canvas]
   (doto (.getContext canvas "2d")
     (.clearRect 0 0 width height)
     (render))
   canvas))

(defn concat-canvases
  "Takes an options map with
    :target-height  -- height of shots in the output (excluding margin)
    :image-margin   -- outer margin of output (in px)
    :shot-spacing   -- spacing between shots (in percent, 0.05 = 5%)
  and a seq of HTMLCanvasElements.
  Returns a single side-by-side concatinated canvas."
  [{:keys [target-height
           image-margin
           shot-spacing]}
   canvases]
  (let [source-height (aget (first canvases) "height")
        source-width  (aget (first canvases) "width")
        scale-ratio   (/ target-height source-height)
        target-width  (+ (* source-width scale-ratio (count canvases))
                         (* source-width scale-ratio shot-spacing (- (count canvases) 1)))
        canvas        (doto (.createElement js/document "canvas")
                        (oset! "width" (+ target-width (* 2 image-margin)))
                        (oset! "height" (+ target-height (* 2 image-margin))))
        ctx           (.getContext canvas "2d")]
    (doto ctx
      (oset! "fillStyle" "#FFF")
      (.fillRect 0 0
                 (+ target-width (* 2 image-margin))
                 (+ target-height (* 2 image-margin))))
    (doseq [[idx source-canvas] (map-indexed vector canvases)]
      (let [section-x (+ image-margin (* idx source-width scale-ratio (+ 1 shot-spacing)))
            section-width (* source-width scale-ratio)]
        (doto ctx
          (.drawImage source-canvas
                      section-x       image-margin
                      section-width   target-height)
          (.strokeRect section-x      image-margin
                       section-width  target-height))))
    canvas))

(defn canvas->url
  "Takes an options map with
    :img-format (MIME string)
    :quality (optional 0-1 factor for JPEG compression)
  and a canvas element.
  Returns base64 data URL."
  [{:keys [img-format quality]} canvas]
  (.toDataURL
    canvas
    (or img-format "image/jpeg")
    (or quality 0.9)))

(def asset-url-cache (atom {}))
(defn resolve-asset-url
  "Takes a skygear asset URL
  Returns Promise of actual resource URL."
  [asset-url]
  (if-let [cached-url (get @asset-url-cache asset-url)]
    (js/Promise.resolve cached-url)
    (-> (.lambda js/skygear "asset-proxy" #js[asset-url])
        (.then (fn [result]
                 (swap! asset-url-cache assoc asset-url (oget result "resourceURL"))
                 (oget result "resourceURL"))))))

(def empty-image (doto (js/Image.) (oset! "src" "data:image/svg+xml,<svg xmlns='http://www.w3.org/2000/svg'></svg>")))
(def image-cache (atom {nil empty-image}))
(defn url->image
  "Takes an image URL.
  Returns Promise of Image element."
  [url]
  (if-let [cached-img (get @image-cache url)]
    (js/Promise.resolve cached-img)
    (js/Promise.
      (fn [rsov _]
        (let [a (doto (.createElement js/document "a")
                  (oset! "href" url))
              img (js/Image.)]
          (doto img
            (.addEventListener
              "load" (fn [_]
                       (swap! image-cache assoc url img)
                       (rsov img)))
            (oset! "crossOrigin"
                   (if (= a.protocol "data:")
                     nil ;; CORS will block data URLs in Safari
                     "anonymous"))
            (oset! "src" url)
            ))))))

(defn composition-data-merge [a b]
  (if (map? b)
    (merge a b)
    b))

(defn load-shot
  "Takes a map of :user-data :template-id and :template-index
  returns a Promise of map of render-data map"
  [{:keys [user-data template-id template-index]}]
  (let [template-data (get templates/templates template-id)
        shot-data     (get-in template-data [:shots template-index])
        layout-fn     (:layout shot-data)
        render-data   (layout-fn (merge-with
                                   composition-data-merge
                                   template-data
                                   shot-data
                                   user-data))
        img-urls      (vec (:images render-data))]
    (-> (->> img-urls
             (map (fn [url]
                    (let [a (doto (.createElement js/document "a")
                              (oset! "href" url))]
                      (if (= a.host "asset.skygeario.com")
                        (resolve-asset-url url)
                        (js/Promise.resolve url)))))
             (map (fn [url-promise]
                    (.then url-promise url->image))))
        (clj->js)
        (js/Promise.all)
        (.then (fn [img-objs] (zipmap img-urls img-objs)))
        (.then (fn [images-map] (update render-data :render partial images-map))))))

(defn create-zip
  "Takes vec of {:user-data :template-index :template-id}
  returns Promise of zip Blob
  files are named by template-index"
  [shots]
  (-> (->> shots
           (map load-shot)
           (map (fn [p]
                  (.then p (fn [render-data]
                             ;; Provide a scaled canvas the alter export resolution.
                             ;; To remove this, simply call render-to-canvas without
                             ;; providing a canvas.
                             ;; Refs: #208
                             (let [canvas (doto (.createElement js/document "canvas")
                                            (oset! "width" 1242)
                                            (oset! "height" 2208))
                                   scaling-factor (/ 1242 1080)]
                               (doto (.getContext canvas "2d")
                                 (.scale scaling-factor scaling-factor))
                               (render-to-canvas render-data))))))
           (map (fn [p]
                  (.then p (fn [canvas]
                             (canvas->url {:img-format  "image/jpeg"
                                           :quality     0.98}
                                          canvas)))))
           (map (fn [p]
                  (.then p (fn [data-url]
                             (subs data-url (->> (string/index-of data-url ",")
                                                 (+ 1)))))))
           )
      (clj->js)
      (js/Promise.all)
      (.then (fn [images]
               (let [template-indecies (map :template-index shots)
                     zip-obj           (js/JSZip.)
                     iOS-folder        (.folder zip-obj "iOS")]
                 (doseq [[filename img] (map vector template-indecies images)]
                   (.file iOS-folder (str filename ".jpg") img #js{:base64 true}))
                 (.generateAsync zip-obj #js{:type "blob"}))))))

(defn save-blob
  "Takes a Blob and filename
  downloads the file using FileSaver.js"
  [blob filename]
  (js/saveAs blob filename))
