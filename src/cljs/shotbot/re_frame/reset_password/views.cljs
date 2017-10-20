(ns shotbot.re-frame.reset-password.views
  (:require [re-frame.core :refer [dispatch subscribe]]
            ))

(defn panel []
  (let [message (subscribe [:reset-password/message])]
    (fn panel-reaction []
      [:div.reset-password-panel
       [:div.reset-password-header
        [:span]
        [:div.logo
         [:img {:src "img/icon-shotbot.svg"}]
         [:span "Shotbot"]]
        [:div.login {:on-click (fn [_]
                                 (dispatch [:reset-password/login]))}
         [:img {:src "img/icon-login.svg"}]
         [:span "Login"]]]
       [:form {:on-submit (fn [evt]
                            (.preventDefault evt)
                            (dispatch [:reset-password/reset-password]))}
        [:h1 "RESET PASSWORD"]
        [:p "Enter your new password below"]
        [:input {:type "password"
                 :required true
                 :placeholder "New Password"
                 :on-change (fn [evt]
                              (dispatch [:reset-password/set-new-password
                                         evt.target.value]))}]
        [:input {:type "password"
                 :required true
                 :placeholder "Confirm Password"
                 :on-change (fn [evt]
                              (dispatch [:reset-password/set-confirm-password
                                         evt.target.value]))}]
        [:span.message @message]
        [:input {:type "submit"
                 :value "Reset"}]
        ]
       [:div]])))
