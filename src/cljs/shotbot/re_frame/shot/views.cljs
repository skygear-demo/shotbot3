(ns shotbot.re-frame.shot.views
  (:require [reagent.core :as r]
            [shotbot.render.utils :refer [render-to-canvas]]
            ))

(defn shot-canvas []
  (let [update-fn (fn [this]
                    (let [el          (r/dom-node this)
                          render-data (:render-data (r/props this))]
                      (when (= el.nodeName "CANVAS")
                        (render-to-canvas render-data el))))]
    (r/create-class
      {:reagent-render
       (fn []
         (if-let [{[width height] :resolution} (-> (r/current-component)
                                                   (r/props)
                                                   (:render-data))]
           [:canvas {:width width :height height}]
           [:img {:src "img/shot-loading.svg"}]))
       :component-did-mount   update-fn
       :component-did-update  update-fn
       })))
