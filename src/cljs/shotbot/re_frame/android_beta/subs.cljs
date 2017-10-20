(ns shotbot.re-frame.android-beta.subs
  (:require [re-frame.core :refer [reg-sub]]))

(reg-sub
  :android-beta/db
  (fn db [db _]
    (:android-beta db)))

(reg-sub
  :android-beta/show-view
  :<- [:android-beta/db]
  (fn show-view [android-beta-db _]
    (:show-view android-beta-db)))

(reg-sub
  :android-beta/signup-email
  :<- [:android-beta/db]
  (fn signup-email [android-beta-db _]
    (:signup-email android-beta-db)))

(reg-sub
  :android-beta/message
  :<- [:android-beta/db]
  (fn message [android-beta-db _]
    (:message android-beta-db)))
