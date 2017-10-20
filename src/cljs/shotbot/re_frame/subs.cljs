(ns shotbot.re-frame.subs
  (:require [re-frame.core :refer [reg-sub]]
            [oops.core :refer [oget]]
            [shotbot.re-frame.project.subs]
            [shotbot.re-frame.android-beta.subs]
            [shotbot.re-frame.edit.subs]
            [shotbot.re-frame.settings.subs]
            [shotbot.re-frame.login.subs]
            [shotbot.re-frame.sign-up.subs]
            [shotbot.re-frame.reset-password.subs]
            ))

(reg-sub
  :shotbot/panel
  (fn panel [db _]
    (get-in db [:ls :panel])))

(reg-sub
  :shotbot/modals
  (fn modals [db _]
    (get-in db [:modals])))
