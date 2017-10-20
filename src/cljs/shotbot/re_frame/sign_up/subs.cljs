(ns shotbot.re-frame.sign-up.subs
  (:require [re-frame.core :refer [reg-sub]]))


(reg-sub
  :sign-up/db
  (fn db [db _]
    (:sign-up db)))

(reg-sub
  :sign-up/message
  :<- [:sign-up/db]
  (fn message [sign-up-db _]
    (:message sign-up-db)))
