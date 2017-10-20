(ns shotbot.re-frame.footer.styles)

(def styles
  [[:.footer {:width "100%"
              :overflow "hidden"
              :background-color "#4A4A4A"
              :display "flex"
              :flex-direction "column"
              :align-items "center"
              :justify-content "cetnter"}
    [:a {:color "#6AD4BA"}]
    [:p {:color "#FFF"
         :font-size "1.25rem"
         :margin "0.75rem 0"}]
    [:p.first {:font-size "2rem"
                       :margin-top "1.5rem"
                       :margin-bottom "0.75rem"}]
    [:p.last {:font-size "1rem"
                        :margin-top "0.75rem"
                        :margin-bottom "1.5rem"}]]
   ])
