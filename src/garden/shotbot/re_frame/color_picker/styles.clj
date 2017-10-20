(ns shotbot.re-frame.color-picker.styles)

(def styles
  [[:.color-picker {:border "2px solid #CCC"
                    :width "100%"
                    :background-color "#FFF"
                    :position "relative"
                    :display "flex"
                    :align-items "center"
                    :margin "1rem 0"}
    [:.color-preview {:width "4rem"
                      :height "4rem"}]
    [:.color-value {:margin-left "1rem"
                    :font-size "2rem"}]
    [:input {:display "none"}]
    [:button {:position "absolute"
              :opacity "0"
              :padding "0"
              :margin "0"
              :top "0"
              :left "0"
              :width "100%"
              :height "100%"}]]
   ])
