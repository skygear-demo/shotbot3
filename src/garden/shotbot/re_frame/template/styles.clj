(ns shotbot.re-frame.template.styles)

(def styles
  [:.template-panel {:min-height "100%"
                     :min-width "1280px"
                     :display "flex"
                     :justify-content "space-between"
                     :flex-direction "column"}
   [:.banner {:background-image "url(../../img/banner-template.png)"
              :background-size "cover"
              :margin-bottom "3rem"}]

   [:.back-arrow {:position "absolute"
                  :margin "12rem 5rem 0"
                  :width "8rem"
                  :cursor "pointer"}]

   [:div.row.first {:margin-top "2rem"
                    :margin-bottom "2rem"}]

   [:.statement {:font-size "1.5rem"}]

   [:.option {:margin-top "3rem"
                       :margin-bottom "1rem"}
    [:.option-header {:width "100%"
                      :display "flex"
                      :align-items "center"
                      :justify-content "space-between"}
     [:.name {:font-size "2rem"}]
     [:.desc {:font-size "1.5rem"
              :color "#000"}]
     [:.choose {:border "1px solid #6AD4BA"
                :cursor "pointer"
                :padding "1rem 3rem"
                :margin-left "1rem"}]]
    [:.option-preview {:width "100%"
                       :padding "1rem 0"}]]
   ])
