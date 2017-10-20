(ns shotbot.render.drawing
  (:require [clojure.string :as string]
            [oops.core :refer [oget oset! oapply]]
            [Perspective :as Persp]))


(defn draw-background
  "supply either :background-color or :background-image"
  [ctx images {:keys [background-color background-image width height]}]
  (if background-image
    (.drawImage ctx (get images background-image) 0 0 width height)
    (doto ctx
      (.save)
      (oset! "fillStyle" background-color)
      (.fillRect 0 0 width height)
      (.restore))))

(def height-cache (atom {}))

(defn fillText-multiline
  [ctx {:keys [font text x y]}]
  ;; Measure line height of given font using an off-screen <span>
  ;; element, cache the result in height-cache.
  (when-not (get @height-cache font)
    (let [test-elm (doto (.createElement js/document "span")
                     (.appendChild (.createTextNode js/document "A"))
                     (oset! "style.!position" "fixed")
                     (oset! "style.!x" "-10px")
                     (oset! "style.!y" "-10px")
                     (oset! "style.!font" font))]
      (.appendChild js/document.body test-elm)
      (swap! height-cache assoc font (oget (.getBoundingClientRect test-elm) "height"))
      (.remove test-elm)))
  ;; draw each line on canvas
  (let [lines       (string/split text #"\n")
        line-count  (count lines)
        line-height (get @height-cache font)]
    (doseq [[idx line] (map-indexed vector lines)]
      (.fillText ctx line x (-> y
                                (- (* line-count line-height 0.5))
                                (+ (* idx line-height)))))))

(defn draw-text
  [ctx {:keys [font color] :as args}]
  (doto ctx
    (.save)
    (oset! "textAlign" "center")
    (oset! "textBaseline" "middle")
    (oset! "fillStyle" color)
    (oset! "font" font)
    (fillText-multiline args)
    (.restore)))

(defn draw-image
  "supply either :placement [x y w h] or :transform [x1,y1 x2,y2 x3,y3 x4,y4]"
  [ctx img {:keys [placement transform]}]
  (if placement
    (oapply ctx "drawImage" (into [img] placement))
    (apply Persp/drawImage (into [img ctx] transform))))

