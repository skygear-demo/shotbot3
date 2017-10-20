(ns shotbot.re-frame.android-beta.views
  (:require [re-frame.core :refer [dispatch subscribe]]
            ))

(defn android-beta-signup-view []
  (let [signup-email (subscribe [:android-beta/signup-email])
        show-view (subscribe [:android-beta/show-view])
        message (subscribe [:android-beta/message])]
    (fn android-beta-signup-view-reaction []
      (when @show-view
        [:div.android-beta-signup
          [:form {:on-submit
                    (fn [e]
                      (.preventDefault e)
                      (dispatch [:android-beta/signup]))}
            [:h1 "Sign up for Android Screenshots beta"]
            [:p "ShotBot now is optimized for App Store Screenshots only. Sign up to try out the ShotBot beta for Play Store Screenshots. You will be invited to access Android screenshots features as first batch of beta user!"]
            [:input {:placeholder "Enter your email address"
                     :required true
                     :value @signup-email
                     :on-change
                      (fn [evt]
                        (dispatch [:android-beta/set-signup-email evt.target.value]))}]
            [:button "Notify me!"]]
            [:span.message @message]]))))
