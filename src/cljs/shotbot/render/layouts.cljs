(ns shotbot.render.layouts
  (:require [clojure.set :refer [union]]
            [oops.core :refer [oset!]]
            [shotbot.render.drawing :refer [draw-background draw-image draw-text]]))


(defn tagline
  [{:keys [width height title background-image]
    {frame-url :url :as frame} :frame
    {screen-url :url :as screen} :screen
    {add-on-template :template :as add-on} :add-on
    :as params}]
  (let [{add-on-images :images
         add-on-render :render} (if add-on
                                  (add-on-template add-on)
                                  {:images #{} :render (fn [_ ctx] ctx)})]
    {:resolution  [width height]
     :inputs      [{:type :title      :content title}
                   {:type :label      :content (or (:screenshot-hint params)
                                                   "Screenshot")}
                   {:type :file       :key-path [:screen :url]
                                      :asset-field :screen}
                   {:type :label      :content (or (:tagline-hint params)
                                                   "Tagline")}
                   {:type :text       :key-path [:tagline]
                                      :placeholder (:tagline-placeholder params)}
                   ]
     :images      (union add-on-images #{background-image frame-url screen-url})
     :render      (fn [images ctx]
                    (doto ctx
                      (draw-background images
                                       (select-keys params [:background-color
                                                            :background-image
                                                            :width :height]))
                      (draw-image (get images frame-url)
                                  (select-keys frame [:placement :transform]))
                      (draw-image (get images screen-url)
                                  (select-keys screen [:placement :transform]))
                      (draw-text {:font   (:tagline-font params)
                                  :color  (:tagline-color params)
                                  :text   (:tagline params)
                                  :x      (/ width 2)
                                  :y      (:tagline-y params)})
                      (add-on-render images)
                      ))}))


(defn tagline-dual
  [{:keys [width height title background-image]
    {frame-url :url :as frame} :frame
    {screen-url :url :as screen} :screen
    {frame2-url :url :as frame2} :frame2
    {screen2-url :url :as screen2} :screen2
    {add-on-template :template :as add-on} :add-on
    :as params}]
  (let [{add-on-images :images
         add-on-render :render} (if add-on
                                  (add-on-template add-on)
                                  {:images #{} :render (fn [_ ctx] ctx)})]
    {:resolution  [width height]
     :inputs      [{:type :title      :content title}
                   {:type :label      :content (or (:screenshot-hint params)
                                                   "Screenshot")}
                   {:type :file       :key-path [:screen :url]
                                      :asset-field :screen
                                      :label "Left Mobile"}
                   {:type :file       :key-path [:screen2 :url]
                                      :asset-field :screen2
                                      :label "Right Mobile"}
                   {:type :label      :content (or (:tagline-hint params)
                                                   "Tagline")}
                   {:type :text       :key-path [:tagline]
                                      :placeholder (:tagline-placeholder params)}
                   ]
     :images      (union add-on-images #{background-image
                                         frame-url screen-url
                                         frame2-url screen2-url})
     :render      (fn [images ctx]
                    (doto ctx
                      (draw-background images
                                       (select-keys params [:background-color
                                                            :background-image
                                                            :width :height]))
                      (draw-image (get images frame-url)
                                  (select-keys frame [:placement :transform]))
                      (draw-image (get images screen-url)
                                  (select-keys screen [:placement :transform]))
                      (draw-image (get images frame2-url)
                                  (select-keys frame2 [:placement :transform]))
                      (draw-image (get images screen2-url)
                                  (select-keys screen2 [:placement :transform]))
                      (draw-text {:font   (:tagline-font params)
                                  :color  (:tagline-color params)
                                  :text   (:tagline params)
                                  :x      (/ width 2)
                                  :y      (:tagline-y params)})
                      (add-on-render images)
                      ))}))


(defn tagline-sub
  [{:keys [width height title background-image]
    {frame-url :url :as frame} :frame
    {screen-url :url :as screen} :screen
    {add-on-template :template :as add-on} :add-on
    :as params}]
  (let [{add-on-images :images
         add-on-render :render} (if add-on
                                  (add-on-template add-on)
                                  {:images #{} :render (fn [_ ctx] ctx)})]
    {:resolution  [width height]
     :inputs      [{:type :title      :content title}
                   {:type :label      :content (or (:screenshot-hint params)
                                                   "Screenshot")}
                   {:type :file       :key-path [:screen :url]
                                      :asset-field :screen}
                   {:type :label      :content (or (:tagline-hint params)
                                                   "Tagline")}
                   {:type :text       :key-path [:tagline]
                                      :placeholder (:tagline-placeholder params)}
                   {:type :text       :key-path [:subtagline]
                                      :placeholder (:subtagline-placeholder params)}
                   ]
     :images      (union add-on-images #{background-image frame-url screen-url})
     :render      (fn [images ctx]
                    (doto ctx
                      (draw-background images
                                       (select-keys params [:background-color
                                                            :background-image
                                                            :width :height]))
                      (draw-image (get images frame-url)
                                  (select-keys frame [:placement :transform]))
                      (draw-image (get images screen-url)
                                  (select-keys screen [:placement :transform]))
                      (draw-text {:font   (:tagline-font params)
                                  :color  (:tagline-color params)
                                  :text   (:tagline params)
                                  :x      (/ width 2)
                                  :y      (:tagline-y params)})
                      (draw-text {:font   (:subtagline-font params)
                                  :color  (:subtagline-color params)
                                  :text   (:subtagline params)
                                  :x      (/ width 2)
                                  :y      (:subtagline-y params)})
                      (add-on-render images)
                      ))}))


(defn tagline-sub-dual
  [{:keys [width height title background-image]
    {frame-url :url :as frame} :frame
    {screen-url :url :as screen} :screen
    {frame2-url :url :as frame2} :frame2
    {screen2-url :url :as screen2} :screen2
    {add-on-template :template :as add-on} :add-on
    :as params}]
  (let [{add-on-images :images
         add-on-render :render} (if add-on
                                  (add-on-template add-on)
                                  {:images #{} :render (fn [_ ctx] ctx)})]
    {:resolution  [width height]
     :inputs      [{:type :title      :content title}
                   {:type :label      :content (or (:screenshot-hint params)
                                                   "Screenshot")}
                   {:type :file       :key-path [:screen :url]
                                      :asset-field :screen
                                      :label "Left Mobile"}
                   {:type :file       :key-path [:screen2 :url]
                                      :asset-field :screen2
                                      :label "Right Mobile"}
                   {:type :label      :content (or (:tagline-hint params)
                                                   "Tagline")}
                   {:type :text       :key-path [:tagline]
                                      :placeholder (:tagline-placeholder params)}
                   {:type :text       :key-path [:subtagline]
                                      :placeholder (:subtagline-placeholder params)}
                   ]
     :images      (union add-on-images #{background-image
                                         frame-url screen-url
                                         frame2-url screen2-url})
     :render      (fn [images ctx]
                    (doto ctx
                      (draw-background images
                                       (select-keys params [:background-color
                                                            :background-image
                                                            :width :height]))
                      (draw-image (get images frame-url)
                                  (select-keys frame [:placement :transform]))
                      (draw-image (get images screen-url)
                                  (select-keys screen [:placement :transform]))
                      (draw-image (get images frame2-url)
                                  (select-keys frame2 [:placement :transform]))
                      (draw-image (get images screen2-url)
                                  (select-keys screen2 [:placement :transform]))
                      (draw-text {:font   (:tagline-font params)
                                  :color  (:tagline-color params)
                                  :text   (:tagline params)
                                  :x      (/ width 2)
                                  :y      (:tagline-y params)})
                      (draw-text {:font   (:subtagline-font params)
                                  :color  (:subtagline-color params)
                                  :text   (:subtagline params)
                                  :x      (/ width 2)
                                  :y      (:subtagline-y params)})
                      (add-on-render images)
                      ))}))


(defn tagline-fullshot
  [{:keys [width height title background-image]
    {screen-url :url :as screen} :screen
    {add-on-template :template :as add-on} :add-on
    :as params}]
  (let [{add-on-images :images
         add-on-render :render} (if add-on
                                  (add-on-template add-on)
                                  {:images #{} :render (fn [_ ctx] ctx)})]
    {:resolution  [width height]
     :inputs      [{:type :title      :content title}
                   {:type :label      :content (or (:screenshot-hint params)
                                                   "Screenshot")}
                   {:type :file       :key-path [:screen :url]
                                      :asset-field :screen}
                   {:type :label      :content (or (:tagline-hint params)
                                                   "Tagline")}
                   {:type :text       :key-path [:tagline]
                                      :placeholder (:tagline-placeholder params)}
                   ]
     :images      (union add-on-images #{background-image screen-url})
     :render      (fn [images ctx]
                    (doto ctx
                      (draw-background images
                                       (select-keys params [:background-color
                                                            :background-image
                                                            :width :height]))
                      (draw-image (get images screen-url)
                                  (select-keys screen [:placement :transform]))
                      (.save)
                      (oset! "fillStyle" "rgba(0,0,0, 0.6)")
                      (.fillRect 0 0 width 380)
                      (.restore)
                      (draw-text {:font   (:tagline-font params)
                                  :color  (:tagline-color params)
                                  :text   (:tagline params)
                                  :x      (/ width 2)
                                  :y      (:tagline-y params)})
                      (add-on-render images)
                      ))}))

(defn tagline-add-on
  [{:keys [width height title background-image]
    {frame-url :url :as frame} :frame
    {screen-url :url :as screen} :screen
    {add-on-template :template :as add-on} :add-on
    :as params}]
  (let [{add-on-images :images
         add-on-render :render} (if add-on
                                  (add-on-template add-on)
                                  {:images #{} :render (fn [_ ctx] ctx)})]
    {:resolution  [width height]
     :inputs      [{:type :title      :content title}
                   {:type :label      :content (or (:screenshot-hint params)
                                                   "Screenshot")}
                   {:type :file       :key-path [:screen :url]
                                      :asset-field :screen}
                   {:type :label      :content (or (:tagline-hint params)
                                                   "Tagline")}
                   {:type :text       :key-path [:tagline]
                                      :placeholder (:tagline-placeholder params)}
                   {:type :checkbox   :key-path [:add-on :show?]
                                      :label "Badge"}
                   {:type :text-year  :text-placeholder (:text-placeholder add-on)
                                      :year-placeholder (:year-placeholder add-on)
                                      :text-key-path [:add-on :text]
                                      :year-key-path [:add-on :year]}
                   {:type :color      :key-path [:add-on :color]}
                   ]
     :images      (union add-on-images #{background-image frame-url screen-url})
     :render      (fn [images ctx]
                    (doto ctx
                      (draw-background images
                                       (select-keys params [:background-color
                                                            :background-image
                                                            :width :height]))
                      (draw-image (get images frame-url)
                                  (select-keys frame [:placement :transform]))
                      (draw-image (get images screen-url)
                                  (select-keys screen [:placement :transform]))
                      (draw-text {:font   (:tagline-font params)
                                  :color  (:tagline-color params)
                                  :text   (:tagline params)
                                  :x      (/ width 2)
                                  :y      (:tagline-y params)})
                      (add-on-render images)
                      ))}))

(defn tagline-sub-add-on
  [{:keys [width height title background-image]
    {frame-url :url :as frame} :frame
    {screen-url :url :as screen} :screen
    {add-on-template :template :as add-on} :add-on
    :as params}]
  (let [{add-on-images :images
         add-on-render :render} (if add-on
                                  (add-on-template add-on)
                                  {:images #{} :render (fn [_ ctx] ctx)})]
    {:resolution  [width height]
     :inputs      [{:type :title      :content title}
                   {:type :label      :content (or (:screenshot-hint params)
                                                   "Screenshot")}
                   {:type :file       :key-path [:screen :url]
                                      :asset-field :screen}
                   {:type :label      :content (or (:tagline-hint params)
                                                   "Tagline")}
                   {:type :text       :key-path [:tagline]
                                      :placeholder (:tagline-placeholder params)}
                   {:type :text       :key-path [:subtagline]
                                      :placeholder (:subtagline-placeholder params)}
                   {:type :checkbox   :key-path [:add-on :show?]
                                      :label "Badge"}
                   {:type :text-year  :text-placeholder (:text-placeholder add-on)
                                      :year-placeholder (:year-placeholder add-on)
                                      :text-key-path [:add-on :text]
                                      :year-key-path [:add-on :year]}
                   {:type :color      :key-path [:add-on :color]}
                   ]
     :images      (union add-on-images #{background-image frame-url screen-url})
     :render      (fn [images ctx]
                    (doto ctx
                      (draw-background images
                                       (select-keys params [:background-color
                                                            :background-image
                                                            :width :height]))
                      (draw-image (get images frame-url)
                                  (select-keys frame [:placement :transform]))
                      (draw-image (get images screen-url)
                                  (select-keys screen [:placement :transform]))
                      (draw-text {:font   (:tagline-font params)
                                  :color  (:tagline-color params)
                                  :text   (:tagline params)
                                  :x      (/ width 2)
                                  :y      (:tagline-y params)})
                      (draw-text {:font   (:subtagline-font params)
                                  :color  (:subtagline-color params)
                                  :text   (:subtagline params)
                                  :x      (/ width 2)
                                  :y      (:subtagline-y params)})
                      (add-on-render images)
                      ))}))

