(ns shotbot.re-frame.login.subs
  (:require [re-frame.core :refer [reg-sub]]))


(reg-sub
  :login/db
  (fn db [db _]
    (:login db)))

(reg-sub
  :login/message
  :<- [:login/db]
  (fn message [login-db _]
    (:message login-db)))

(reg-sub
  :login/email-input-border-botton-color
  :<- [:login/db]
  (fn email-input-border-botton-color [login-db _]
    (:email-input-border-botton-color login-db)))

(reg-sub
  :login/password-input-border-botton-color
  :<- [:login/db]
  (fn password-input-border-botton-color [login-db _]
    (:password-input-border-botton-color login-db)))
