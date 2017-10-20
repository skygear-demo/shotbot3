(ns shotbot.re-frame.color-picker.views
  (:require-macros [clojure.core.strint :refer [<<]])
  (:require [reagent.core :as r]
            [cljsjs.jscolor]
            ))

(defn color-picker []
  (let [color-picker-class  (str "jsc-" (random-uuid))
        color-preview-id    (str "jsc-sty-" (random-uuid))
        color-input-id      (str "jsc-val-" (random-uuid))]
    (r/create-class
      {:reagent-render
       (fn []
         (let [{:keys [value]} (r/props (r/current-component))]
           [:div.color-picker
            [:div.color-preview {:id color-preview-id}]
            [:span.color-value value]
            [:input {:id color-input-id
                     :defaultValue value}]
            [:button
             {:class color-picker-class
              :data-jscolor (<< "{hash:true,
                                valueElement:'~{color-input-id}',
                                styleElement:'~{color-preview-id}'}")}]]))
       :component-did-mount
       (fn [this]
         (let [{:keys [on-change]} (r/props this)
               input-el (.querySelector (r/dom-node this) "input")]
           (.installByClassName js/jscolor color-picker-class)
           (.addEventListener input-el "change" on-change)))
       })))
