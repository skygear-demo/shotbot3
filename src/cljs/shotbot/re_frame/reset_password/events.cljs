(ns shotbot.re-frame.reset-password.events
  (:require [oops.core :refer [oset!]]
            [shotbot.re-frame.utils :refer [reg-event-fx reg-event-db]]))


;; initializes the panel only if user-id and code are passed
;; as arguments from URL dispatch above, no-op otherwise.
;; (e.g. when triggered by the :shotbot/initialized event)
(reg-event-db
  :reset-password/initialize
  (fn initialize [db [user-id code expire-at]]
    (if-not (and user-id code expire-at)
      db
      (assoc db :reset-password {:user-id          user-id
                                 :code             code
                                 :expire-at        (js/parseInt expire-at)
                                 :new-password     ""
                                 :confirm-password ""
                                 :message          ""}))))

(reg-event-db
  :reset-password/set-new-password
  (fn set-new-password [db [new-password]]
    (assoc-in db [:reset-password :new-password] new-password)))

(reg-event-db
  :reset-password/set-confirm-password
  (fn set-confirm-password [db [confirm-password]]
    (assoc-in db [:reset-password :confirm-password] confirm-password)))

(reg-event-fx
  :reset-password/login
  (fn login [_ _]
    {:dispatch [:shotbot/switch-panel :login]}))

(reg-event-fx
  :reset-password/reset-password
  (fn reset-password [{{{:keys [user-id code expire-at
                                new-password
                                confirm-password]} :reset-password
                        :as db} :db} _]
    (if-not (= new-password confirm-password)
      {:db (assoc-in db [:reset-password :message] "Error: passwords do not match.")}
      {:dispatch [:shotbot/modal-push :loading]
       :skygear [:lambda {:action "user:reset-password"
                          :args [user-id code expire-at new-password]}
                 :success-dispatch  [:reset-password/reset-password-success]
                 :fail-dispatch     [:reset-password/reset-password-fail]]})))

(reg-event-fx
  :reset-password/reset-password-success
  (fn reset-password-success [{:keys [db]} _]
    {:dispatch [:shotbot/modal-pop]
     :db (assoc-in db [:reset-password :message] "Password reset successful!")}))

(reg-event-fx
  :reset-password/reset-password-fail
  (fn reset-password-fail [{:keys [db]} [result]]
    {:dispatch [:shotbot/modal-pop]
     :db (assoc-in db [:reset-password :message] (str "Error: " result.error.message))}))
