(ns shotbot.re-frame.login.views
  (:require [re-frame.core :refer [dispatch subscribe]]
            [shotbot.re-frame.footer.views :refer [oursky-footer]]
            ))

(defn panel []
  (let [message (subscribe [:login/message])
        email-input-border-botton-color (subscribe [:login/email-input-border-botton-color])
        password-input-border-botton-color (subscribe [:login/password-input-border-botton-color])]
    (fn panel-reaction []
      [:div.login-panel
       [:div.login-header
        [:span]
        [:div.logo
         [:img {:src "img/icon-shotbot.svg"}]
         [:span "Shotbot"]]
        [:a {:on-click (fn [_]
                         (dispatch [:login/sign-up]))}
         "SIGN UP"]]
       [:form {:on-submit (fn [evt]
                            (.preventDefault evt)
                            (dispatch [:login/login]))}
        [:h1 "LOGIN"]
        [:p "Login to your existing Shotbot Account"]
        [:input {:type "email"
                 :style {:border-bottom (str "1px solid" @email-input-border-botton-color)}
                 :placeholder "Your email"
                 :on-change (fn [evt]
                              (dispatch [:login/set-email evt.target.value]))}]
        [:input {:type "password"
                 :style {:border-bottom (str "1px solid" @password-input-border-botton-color)}
                 :placeholder "Password"
                 :on-change (fn [evt]
                              (dispatch [:login/set-password evt.target.value]))}]
        [:span.message @message]
        [:input {:type "submit"
                 :value "Login"}]
        [:a {:on-click (fn [_]
                         (dispatch [:login/forgot-password]))}
         "Forgot Password?"]
        ]
       [oursky-footer]])))
