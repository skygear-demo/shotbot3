(ns shotbot.re-frame.header.events
  (:require [shotbot.re-frame.utils :refer [reg-event-fx reg-event-db]]))


(reg-event-fx
  :header/logout
  (fn logout [{:keys [db]} _]
    (if (js/confirm "Are you sure you want to logout?")
      {:db (assoc-in db [:ls :user-id] nil)
       :dispatch [:shotbot/modal-push :loading]
       :skygear [:logout true
                 :success-dispatch [:shotbot/switch-panel :login]]}
      {})))

