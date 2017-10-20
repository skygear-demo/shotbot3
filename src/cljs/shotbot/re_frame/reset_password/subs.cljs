(ns shotbot.re-frame.reset-password.subs
  (:require [re-frame.core :refer [reg-sub]]))


(reg-sub
  :reset-password/db
  (fn db [db _]
    (:reset-password db)))

(reg-sub
  :reset-password/message
  :<- [:reset-password/db]
  (fn message [reset-password-db _]
    (:message reset-password-db)))
