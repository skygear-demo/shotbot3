(ns shotbot.re-frame.feedback.styles)

(def styles
  [[:.feedback-button-container { :position "fixed"
                                  :transform "rotate(-90deg)"
                                  :transform-origin "bottom right"
                                  :right "0"
                                  :bottom "20%" }]
   [:.feedback-button { :display "block"
                        :color "white"
                        :font-size "2rem"
                        :padding "0.2rem 1rem 0.15rem 1rem"
                        :background "#84c5e2" }
    [:&:hover,
     :&:focus { :text-decoration "none"
                :color "white" }]]
])
