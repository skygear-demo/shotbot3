(ns shotbot.re-frame.login.events
  (:require [oops.core :refer [oget]]
            [shotbot.utils :refer [email?]]
            [shotbot.re-frame.utils :refer [reg-event-fx reg-event-db]]
            [clojure.string :refer [blank?]]))


(reg-event-db
  :login/initialize
  (fn initialize [db _]
    (assoc db :login {:email ""
                      :password ""
                      :message ""
                      :email-input-border-botton-color "#E4E4E4"
                      :password-input-border-botton-color "#E4E4E4"})))

(reg-event-db
  :login/set-email
  (fn set-email [db [email]]
    (if (email? email)
      (update-in db [:login] assoc :email email :email-input-border-botton-color "#E4E4E4")
      (update-in db [:login] assoc :email email :email-input-border-botton-color "#FF6767"))))

(reg-event-db
  :login/set-password
  (fn set-password [db [password]]
    (if-not (blank? password)
      (update-in db [:login] assoc :password password :password-input-border-botton-color "#E4E4E4")
      (update-in db [:login] assoc :password password :password-input-border-botton-color "#FF6767"))))

(reg-event-fx
  :login/sign-up
  (fn sign-up [_ _]
    {:dispatch [:shotbot/switch-panel :sign-up]}))

(reg-event-fx
  :login/forgot-password
  (fn forgot-password [_ _]
    (let [email (js/prompt "Enter your email to reset password:")]
      (if (or (not email)
              (= email ""))
        {}
        (do
          (js/alert (str "A recovery email will be sent to " email))
          {:skygear [:lambda {:action "user:forgot-password"
                              :args   [email]}]})))))

(reg-event-fx
  :login/login
  (fn login [{{{:keys [email password]} :login :as db} :db} _]
    (cond ;; if
      (and (email? email) (not (blank? password))) {:dispatch [:shotbot/modal-push :loading] ;; email and password ok
                                                    :skygear [:login {:email     email
                                                                      :password  password}
                                                              :success-dispatch  [:login/login-success]
                                                              :fail-dispatch     [:login/login-fail]]}
      (and (not (email? email)) (blank? password)) {:db (update-in db [:login] assoc ;; both not ok
                                                          :message "Error: invalid email or password"
                                                          :email-input-border-botton-color "#FF6767"
                                                          :password-input-border-botton-color "#FF6767")}
      (not (email? email)) {:db (update-in db [:login] assoc ;; email not ok
                            :message "Error: invalid email"
                            :email-input-border-botton-color "#FF6767")}
      (blank? password) {:db (update-in db [:login] assoc ;; passwork not ok
                            :message "Error: invalid password"
                            :password-input-border-botton-color "#FF6767")}
    )))

(reg-event-fx
  :login/login-success
  (fn login-success [{:keys [db]} [user]]
    {:db (assoc-in db [:ls :user-id] (oget user "id"))
     :dispatch [:shotbot/switch-panel :project]}))

(reg-event-fx
  :login/login-fail
  (fn login-fail [{:keys [db]} [result]]
    {:dispatch [:shotbot/modal-pop]
     :db (assoc-in db [:login :message] (str "Error: " result.error.message))}))
