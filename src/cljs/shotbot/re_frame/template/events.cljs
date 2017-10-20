(ns shotbot.re-frame.template.events
  (:require [shotbot.re-frame.utils :refer [reg-event-fx reg-event-db]]
            [shotbot.render.templates :refer [templates]]
            ))

(reg-event-fx
  :template/back
  (fn back [{:keys [db]} _]
    {:db (update db :ls dissoc :app-id)
     :dispatch [:shotbot/switch-panel :project]}))

(reg-event-fx
  :template/choose
  (fn choose [{:keys [db]} [template-id]]
    (let [app-id  (get-in db [:ls :app-id])
          shots   (for [i (-> template-id templates :shots count range)]
                    ^:rec {:_type "shot"
                           ;; generate "shot" record ID in advance to
                           ;; reference it in the "app" record
                           :_id (str (random-uuid))
                           :app-id app-id
                           :template-index i})]
      {:skygear [:save (into [^:rec {:_type "app"
                                     :_id app-id
                                     :template-id template-id
                                     :shot-order (clj->js (map :_id shots))}]
                             shots)
                 :success-dispatch [:shotbot/switch-panel :edit]]
       :dispatch [:shotbot/modal-push :loading]})))
