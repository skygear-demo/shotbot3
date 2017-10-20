(ns shotbot.re-frame.sign-up.events
  (:require [shotbot.utils :refer [email?]]
            [shotbot.re-frame.utils :refer [reg-event-fx reg-event-db]]))


(reg-event-db
  :sign-up/initialize
  (fn initialize [db _]
    (assoc db :sign-up {:email ""
                        :password ""
                        :first-name ""
                        :last-name ""
                        :message ""})))

(reg-event-db
  :sign-up/set-email
  (fn set-email [db [email]]
    (assoc-in db [:sign-up :email] email)))

(reg-event-db
  :sign-up/set-password
  (fn set-password [db [password]]
    (assoc-in db [:sign-up :password] password)))

(reg-event-db
  :sign-up/set-first-name
  (fn set-first-name [db [first-name]]
    (assoc-in db [:sign-up :first-name] first-name)))

(reg-event-db
  :sign-up/set-last-name
  (fn set-last-name [db [last-name]]
    (assoc-in db [:sign-up :last-name] last-name)))

(reg-event-fx
  :sign-up/login
  (fn login [_ _]
    {:dispatch [:shotbot/switch-panel :login]}))

(reg-event-fx
  :sign-up/sign-up
  (fn sign-up [{{{:keys [email password]} :sign-up :as db} :db}]
    (if-not (email? email)
      {:db (assoc-in db [:sign-up :message] "Error: invalid email format")}
      {:dispatch [:shotbot/modal-push :loading]
       :skygear [:signup {:email    email
                          :password password}
                 :success-dispatch  [:sign-up/sign-up-success]
                 :fail-dispatch     [:sign-up/sign-up-fail]]})))

(reg-event-fx
  :sign-up/sign-up-success
  (fn sign-up-success [{{{:keys [first-name last-name]} :sign-up :as db} :db} [user]]
    {:db (assoc-in db [:ls :user-id] user.id)
     :skygear [:save [^:rec {:_type "user"
                             :_id user.id
                             :first-name first-name
                             :last-name last-name}]
               :success-dispatch [:shotbot/switch-panel :project]]}))

(reg-event-fx
  :sign-up/sign-up-fail
  (fn sign-up-fail [{:keys [db]} [result]]
    {:dispatch [:shotbot/modal-pop]
     :db (assoc-in db [:sign-up :message] (str "Error: " result.error.message))}))

