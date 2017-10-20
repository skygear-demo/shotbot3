(ns shotbot.re-frame.android-beta.events
  (:require [oops.core :refer [oget]]
            [akiroz.re-frame.skygear :refer [inject-db]]
            [shotbot.utils :refer [email?]]
            [shotbot.re-frame.utils :refer [reg-event-fx reg-event-db]]))

(reg-event-fx
  :android-beta/initialize
  (fn initialize [{:keys [db]} _]
    (let [user-id (get-in db [:ls :user-id])]
      {:skygear [:query {:record "android_beta"
                         :where {:_owner_id {:= user-id}}}
                 :success-dispatch [:android-beta/initialized]]})))

(reg-event-fx
  :android-beta/initialized
  [(inject-db :skygear)]
  (fn initialized [{:keys [db]} [signup_record]]
    (let [user-info (get-in db [:skygear :user])]
      {:db (assoc db :android-beta
                  {:show-view (= 0 (count signup_record))
                   :signup-email (oget user-info "email")})})))

(reg-event-db
  :android-beta/set-signup-email
  (fn set-signup-email [db [signup-email]]
    (assoc-in db [:android-beta :signup-email] signup-email)))

(reg-event-db
  :android-beta/hide-signup-view
  (fn hide-signup-view [db _]
    (assoc-in db [:android-beta :show-view] false)))

(reg-event-fx
  :android-beta/signup
  (fn signup [{:keys [db]} _]
    (let [signup-email (get-in db [:android-beta :signup-email])]
      (if-not (email? signup-email)
        {:db (assoc-in db [:android-beta :message] "Error: invalid email format")}
        {:dispatch [:shotbot/modal-push :loading]
         :skygear [:save [^:rec {:_type "android_beta"
                                 :email signup-email}]
                   :success-dispatch [:android-beta/signup-success]]}))))

(reg-event-fx
  :android-beta/signup-success
  (fn signup-success [{:keys [db]} [app]]
    (js/alert "Thank you for signing up. We will notify you as soon ass the Android version is out!")
    {:dispatch-n [[:shotbot/modal-pop]
                  [:android-beta/hide-signup-view]]}))
