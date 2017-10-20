(ns shotbot.re-frame.settings.styles
  (:require [garden.selectors :as s]))

(def styles
  [:.settings-modal
   {:background "#FFF"
    :font-size "1.5rem"
    :min-height "45rem"
    :padding "4rem"
    :box-shadow "0 0 4rem rgba(0,0,0,0.5)"
    :display "flex"
    :flex-direction "column"
    :justify-content "space-between"}
   [:div.right-aligner {:text-align "right"}
    [:img {:cursor "pointer"}]]
   [:p.user-email {:color "#636363"}]
   [:form
    [:p.form-header {:margin-top "2rem"
                     :margin-botton "1rem"}]
    [:input {:width "100%"
             :border "none"
             :outline "none"
             :border-bottom "1px solid #E4E4E4"
             :margin "1rem 0"}
     [(s/& (s/attr= "type" "submit")) {:border "none"
                                       :background-color "#6AD4BA"
                                       :font-weight "bold"
                                       :font-size "2rem"
                                       :margin-top "1rem"
                                       :padding "1.2rem 3.5rem"}]]
    [:p.message {:font-weight "bold"}]]])
