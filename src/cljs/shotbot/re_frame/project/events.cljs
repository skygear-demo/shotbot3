(ns shotbot.re-frame.project.events
  (:require [oops.core :refer [oget]]
            [shotbot.re-frame.utils :refer [reg-event-fx reg-event-db]]))


(reg-event-fx
  :project/initialize
  (fn initialize [{:keys [db]} _]
    (let [user-id (get-in db [:ls :user-id])]
      {:dispatch-n [[:shotbot/modal-push :loading]
                    [:android-beta/initialize]]
       :skygear [:query {:record "app"
                         :where {:_owner_id {:= user-id}}}
                 :success-dispatch [:project/initialized]]})))

(reg-event-fx
  :project/initialized
  (fn initialized [{:keys [db]} [apps]]
    {:db (assoc db :project
                {:new-app-name ""
                 :new-app-name-input-color "#D8D8D8"
                 :apps (->> (for [app apps]
                              [(oget app "_id")
                               {:name        (oget app "name")
                                :thumbnail   (oget app "?thumbnail")
                                :template-id (oget app "?template-id")
                                :created-at  (oget app "createdAt")}])
                            (into {}))})
     :dispatch [:shotbot/modal-pop]}))

(reg-event-db
  :project/set-new-app-name
  (fn set-app-name [db [app-name]]
    (assoc-in db [:project :new-app-name] app-name)))

(reg-event-db
  :project/set-new-app-name-input-color
  (fn set-app-name-color [db [value]]
    (assoc-in db [:project :new-app-name-input-color] (if (clojure.string/blank? value) "#D8D8D8" "#6AD4BA"))))

(reg-event-fx
  :project/create-new-app
  (fn create-new-app [{:keys [db]} _]
    {:dispatch [:shotbot/modal-push :loading]
     :skygear [:save [^:rec {:_type "app"
                             :name (get-in db [:project :new-app-name])}]
               :success-dispatch [:project/create-new-app-success]]}))

(reg-event-fx
  :project/create-new-app-success
  (fn create-new-app-success [{:keys [db]} [app]]
    {:db (assoc-in db [:ls :app-id] (oget app "_id"))
     :dispatch [:shotbot/switch-panel :template]}))

(reg-event-fx
  :project/choose-app
  (fn choose-app [{:keys [db]} [app-id]]
    {:db (assoc-in db [:ls :app-id] app-id)
     :dispatch [:shotbot/switch-panel
                (if (get-in db [:project :apps app-id :template-id])
                  :edit
                  :template)]}))
