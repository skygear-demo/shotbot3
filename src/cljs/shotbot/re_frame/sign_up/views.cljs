(ns shotbot.re-frame.sign-up.views
  (:require [re-frame.core :refer [dispatch subscribe]]
            [shotbot.re-frame.footer.views :refer [oursky-footer]]
            ))

(defn panel []
  (let [message (subscribe [:sign-up/message])]
    (fn panel-reaction []
      [:div.sign-up-panel
       [:div.sign-up-header
        [:span]
        [:div.logo
         [:img {:src "img/icon-shotbot.svg"}]
         [:span "Shotbot"]]
        [:a {:on-click (fn [_]
                         (dispatch [:sign-up/login]))}
         "LOGIN"]]
       [:form {:on-submit (fn [evt]
                            (.preventDefault evt)
                            (dispatch [:sign-up/sign-up]))}
        [:h1 "SIGN UP"]
        [:p "New to Shotbot? Sign up for a free account now."]
        [:input.first-name {:type "text"
                            :required true
                            :placeholder "First Name"
                            :on-change (fn [evt]
                                         (dispatch [:sign-up/set-first-name
                                                    evt.target.value]))}]
        [:input.last-name {:type "text"
                           :required true
                           :placeholder "Last Name"
                           :on-change (fn [evt]
                                        (dispatch [:sign-up/set-last-name
                                                   evt.target.value]))}]
        [:input {:type "text"
                 :required true
                 :placeholder "Your email"
                 :on-change (fn [evt]
                              (dispatch [:sign-up/set-email
                                         evt.target.value]))}]
        [:input {:type "password"
                 :required true
                 :placeholder "Password"
                 :on-change (fn [evt]
                              (dispatch [:sign-up/set-password
                                         evt.target.value]))}]
        [:span.message @message]
        [:input {:type "submit"
                 :value "Sign Up"}]
        ]
       [oursky-footer]])))
