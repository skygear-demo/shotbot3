(ns shotbot.re-frame.project.styles
  (:require [garden.selectors :as s]))


(def styles
  [:.project-panel {:min-height "100%"
                    :min-width "1280px"
                    :display "flex"
                    :justify-content "space-between"
                    :flex-direction "column"}
   [:.banner {:background-image "url(../../img/banner-project.png)"
              :background-size "cover"
              :margin-bottom "3rem"}]

   [:.project-container {:overflow "hidden"}]

   [:.app-container {:width "50%"
                     :float "left"}
    [(s/& (s/nth-child "odd"))
     [:.app {:float "right"}]]]

   [:.app {:margin "1rem"
           :padding "2.5rem"
           :height "28rem"
           :width "55rem"
           :border "1px solid #AAAAAA"
           :border-radius "8px"
           :cursor "pointer"
           :display "flex"
           :flex-direction "column"
           :justify-content "space-between"}
    [:span {:font-size "3rem"
            :margin-left "0.5rem"
            :line-height "3rem"}]
    [:.top {:display "flex"
            :justify-content "space-between"
            :align-items "flex-start"}
     [:button {:margin "0" :padding "0"
               :height "4rem" :width "9rem"
               :background-color "#FFF"
               :border "1px solid #6AD4BA"}]]
    [:.bottom {}
     [:img {:height "18rem"
            :min-width "50rem"}]
     [:div {:height "18rem"
            :min-width "50rem"
            :line-height "18rem"
            :border "1px solid #E4E4E4"
            :text-align "center"}]]]

   [:.new-app {:flex-direction "row"
               :cursor "auto"
               :border "1px dashed #AAAAAA"}
    [:p.sub {:font-size "1.5rem"
             :margin-top "2rem"
             :margin-left "0.5rem"
             :color "#888"}]
    [:.left {:display "flex"
             :flex-direction "column"
             :justify-content "space-between"}
     [:textarea {:color "#000"
                 :font-size "3rem"
                 :border "none"
                 :resize "none"
                 :overflow "hidden"
                 :outline "none"}]]
    [:.right {:display "flex"
              :justify-content "space-around"
              :align-items "center"}
     [:input {:width "5rem"
              :height "5rem"
              :border "none"
              :background-color "rgba(0,0,0,0)"
              :background-image "url(../../img/icon-plus.svg)"
              :background-repeat "no-repeat"
              :background-size "contain"
              :background-position "center"}]]]

   ])
