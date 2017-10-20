(ns shotbot.re-frame.settings.events
  (:require [shotbot.re-frame.utils :refer [reg-event-fx reg-event-db]]
            [oops.core :refer [oget]]))

(reg-event-fx
 :settings/show
 (fn show [{:keys [db]} _]
   (let [user-info (get-in db [:skygear :user])]
     {:db (assoc db :settings {:user-email (oget user-info "email")
                               :old-password ""
                               :new-password ""
                               :new-password-confirm ""
                               :message ""})
      :dispatch [:shotbot/modal-push :settings]})))

(reg-event-db
 :settings/old-password
 (fn old-password [db [old-password]]
   (assoc-in db [:settings :old-password] old-password)))

(reg-event-db
 :settings/new-password
 (fn new-password [db [new-password]]
   (assoc-in db [:settings :new-password] new-password)))

(reg-event-db
 :settings/new-password-confirm
 (fn new-password-confirm [db [new-password-confirm]]
   (assoc-in db [:settings :new-password-confirm] new-password-confirm)))

(reg-event-fx
 :settings/change-password
 (fn change-password [{:keys [db]} _]
   (let [{:keys [old-password
                 new-password
                 new-password-confirm]} (:settings db)]
     (if (= new-password new-password-confirm)
       {:skygear [:change-password {:old-password old-password
                                    :new-password new-password}
                  :success-dispatch  [:settings/change-password-success]
                  :fail-dispatch     [:settings/change-password-fail]]
        :dispatch [:shotbot/modal-push :loading]}
       {:db (assoc-in db [:settings :message] "Error: Passwords do not match.")}))))

(reg-event-fx
 :settings/change-password-success
 (fn change-password-success [{:keys [db]} _]
   {:db (assoc-in db [:settings :message] "Password changed successfully!")
    :dispatch [:shotbot/modal-pop]}))

(reg-event-fx
 :settings/change-password-fail
 (fn change-password-fail [{:keys [db]} [result]]
   {:db (assoc-in db [:settings :message] (str "Error: " result.error.message))
    :dispatch [:shotbot/modal-pop]}))
