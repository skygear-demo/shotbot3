(ns shotbot.re-frame.reset-password.styles
  (:require [garden.selectors :as s]))

(def styles
  [[:.reset-password-panel {:background-color "#F4F4F4"
                            :min-height "78rem"
                            :min-width "56rem"
                            :height "100%"
                            :width "100%"
                            :display "flex"
                            :flex-direction "column"
                            :justify-content "space-between"
                            :align-items "center"}
    [:.reset-password-header {:height "10rem"
                              :width "100%"
                              :background-color "#FFF"
                              :border-bottom "1px solid #E4E4E4"
                              :padding "0 4rem"
                              :display "flex"
                              :justify-content "space-between"
                              :align-items "center"}
     [:.logo {:display "flex"
              :align-items "center"}
      [:img {:height "4rem"}]
      [:span {:margin-left "1rem"
              :font-size "3rem"
              :font-weight "bold"}]]
     [:.login {:display "flex"
               :align-items "center"
               :cursor "pointer"}
      [:img {:height "2rem"}]
      [:span {:margin-left "1rem"
              :font-size "1.5rem"
              :font-weight "bold"}]]]
    [:form {:background-color "#FFF"
            :border "1px solid #E4E4E4"
            :width "50rem"
            :padding "5rem 5rem 2rem"}
     [:h1 {:margin "0"
           :font-size "3rem"}]
     [:p {:margin "0.5rem 0 6rem"
          :font-size "1.5rem"}]
     [:input {:width "100%"}
      [(s/& (s/attr= "type" "text"))
       (s/& (s/attr= "type" "password"))
       {:margin "2rem 0"
        :font-size "2rem"
        :border "none"
        :border-bottom "2px solid #CCC"}
       [:&:focus {:outline "none"
                  :border-bottom "2px solid #6AD4BA"}]
       [:&:invalid {:outline "none"
                    :box-shadow "none"
                    :border-bottom "2px solid #FF6767"}]]
      [(s/& (s/attr= "type" "submit"))
       {:border "none"
        :background-color "#6AD4BA"
        :font-weight "bold"
        :font-size "2rem"
        :margin-top "6rem"
        :padding "1.2rem 3.5rem"}]]
     [:.message {:font-size "1.5rem"
                 :margin-top "1rem"}]
     [:a {:display "block"
          :text-align "center"
          :margin "3rem auto 0"
          :font-size "1.5rem"
          :color "#6AD4BA"}]]]
   ])
