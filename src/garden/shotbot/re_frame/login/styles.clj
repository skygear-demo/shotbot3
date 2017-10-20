(ns shotbot.re-frame.login.styles
  (:require [garden.selectors :as s]))

(def styles
  [[:.login-panel {:background-color "#F4F4F4"
                   :min-height "78rem"
                   :min-width "56rem"
                   :height "100%"
                   :width "100%"
                   :display "flex"
                   :flex-direction "column"
                   :justify-content "space-between"
                   :align-items "center"}
    [:.login-header {:height "10rem"
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
     [:a {:font-size "1.5rem"
          :color "#6AD4BA"}]]
    [:form {:background-color "#FFF"
            :border "1px solid #E4E4E4"
            :width "50rem"
            :padding "5rem 5rem 2rem"}
     [:h1 {:margin "0"
           :font-size "3rem"}]
     [:p {:margin "0.5rem 0 6rem"
          :color "#636363"
          :font-size "1.5rem"}]
     [:input {:width "100%"}
      [(s/& (s/attr= "type" "email"))
       (s/& (s/attr= "type" "password"))
       {:margin "2rem 0"
        :font-size "2rem"
        :border "none"}
       [:&:focus {:outline "none"}]]
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
