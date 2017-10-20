(ns shotbot.re-frame.settings.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
 :settings/db
 (fn db [db _]
   (:settings db)))

(reg-sub
 :settings/message
 :<- [:settings/db]
 (fn message [settings-db _]
   (:message settings-db)))

(reg-sub
 :settings/user-email
 :<- [:settings/db]
 (fn user-email [settings-db _]
   (:user-email settings-db)))
