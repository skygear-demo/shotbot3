(ns shotbot.re-frame.settings.views
  (:require [re-frame.core :refer [dispatch subscribe]]
            [reagent.core :as r]))

(defn modal []
  (let [message (subscribe [:settings/message])
        user-email (subscribe [:settings/user-email])
        key-down (fn [event]
                   (when (= event.key "Escape")
                     (dispatch [:shotbot/modal-pop])))]
    (r/create-class
     {:component-will-mount
      (fn [_]
        (js/addEventListener "keydown" key-down))
      :component-will-unmount
      (fn [_]
        (js/removeEventListener "keydown" key-down))
      :reagent-render
      (fn modal-reaction []
        [:div.overlay
         [:div.container
          [:div.row
           [:div.col-sm-6.col-sm-offset-3.settings-modal
            [:div
             [:header.settings
              [:div.right-aligner
               [:img {:src "img/icon-close.svg"
                      :on-click #(dispatch [:shotbot/modal-pop])}]]
              [:h1 "Settings"]
              [:p.user-email @user-email]]
             [:form.settings
              {:on-submit (fn [evt]
                            (.preventDefault evt)
                            (dispatch [:settings/change-password]))}
              [:p.form-header "Change my Password"]
              [:input {:type "password"
                       :placeholder "Old Password"
                       :required true
                       :on-change (fn [evt] (dispatch [:settings/old-password
                                                       (-> evt .-target .-value)]))}]
              [:br]
              [:input {:type "password"
                       :placeholder "New Password"
                       :required true
                       :on-change (fn [evt] (dispatch [:settings/new-password
                                                       (-> evt .-target .-value)]))}]
              [:br]
              [:input {:type "password"
                       :placeholder "New Password (Confirm)"
                       :required true
                       :on-change (fn [evt] (dispatch [:settings/new-password-confirm
                                                       (-> evt .-target .-value)]))}]
              [:br]
              [:p.message @message]
              [:input {:type "submit" :value "Save Changes"}]]
             [:footer.settings
              [:p "Comments? Any Advice? Looking for Help?"]
              [:p "Reach us at "
               [:a {:href "mailto:support+shotbot@oursky.com"} "support+shotbot@oursky.com"]]]]]]]])})))
