(ns shotbot.re-frame.header.styles)

(def styles
  [[:.header {:width "100%"
              :min-width "1280px"
              :display "flex"
              :justify-content "space-between"}

    [:.logo {:display "flex"
             :align-items "center"
             :font-size "3rem"
             :font-weight "bold"
             :margin-top "1.5rem"
             :margin-bottom "1.5rem"
             :margin-left "5rem"
             :cursor "pointer"}
     [:img {:height "5rem"
            :margin-right "1rem"}]]

    [:.title {:font-size "3rem"}]

    [:.right {:font-size "1.5rem"
              :margin-top "1.5rem"
              :margin-right "5rem"
              :margin-bottom "1.5rem"
              :display "flex"
              :align-items "center"}
     [:.greeting {:font-weight "bold"
                  :margin-right "3rem"}]
     [:.settings
      [:span {:margin-right "3rem"}]]
     [:.settings
      :.logout {:display "flex"
                :align-items "center"
                :cursor "pointer"}
      [:img {:height "2rem"
             :margin-right "0.5rem"}]]]]

     [:.template-header {:position "absolute"
                         :top "0"}]

     [:.project-header {:background-color "#D0EAEF"}]

     [:.edit-header {:background-color "#FFFFFF"}]
   ])
