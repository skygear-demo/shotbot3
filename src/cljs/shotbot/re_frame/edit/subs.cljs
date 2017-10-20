(ns shotbot.re-frame.edit.subs
  (:require [re-frame.core :refer [reg-sub]]
            [shotbot.render.templates :refer [templates]]
            ))


(reg-sub
  :edit/db
  (fn db [db _]
    (:edit db)))

(reg-sub
  :edit/app-name
  :<- [:edit/db]
  (fn app-name [edit-db _]
    (let [app-name (:app-name edit-db)]
      (if (> (count app-name) 45)
        (str (subs app-name 0 42) "...")
        app-name))))

(reg-sub
  :edit/template-name
  :<- [:edit/db]
  (fn template [edit-db _]
    (-> (:template-id edit-db)
        (templates)
        (:name))))

(reg-sub
  :edit/shot-ids
  :<- [:edit/db]
  (fn shot-ids [edit-db _]
    (:shot-ids edit-db)))

(reg-sub
  :edit/render-data
  :<- [:edit/db]
  (fn render-data [edit-db [_ shot-id]]
    (get-in edit-db [:shot-derrived-states shot-id])))

(reg-sub
  :edit/show-inputs?
  :<- [:edit/db]
  (fn show-inputs? [edit-db [_ shot-id]]
    (get-in edit-db [:shot-transient-states shot-id :show-inputs?])))

(reg-sub
  :edit/saved?
  :<- [:edit/db]
  (fn saved? [edit-db [_ shot-id]]
    (get-in edit-db [:shot-transient-states shot-id :saved?])))

(reg-sub
  :edit/confirm-leave?
  :<- [:edit/db]
  (fn confirm-leave? [edit-db _]
    (when-not (->> (vals (:shot-transient-states edit-db))
                   (every? :saved?))
      "This page contains unsaved data, sure you want to leave?")))

(reg-sub
  :edit/shot-data
  :<- [:edit/db]
  (fn shot-data [edit-db [_ shot-id key-path not-found]]
    (let [value (get-in edit-db (into [:shot-persistant-states shot-id :user-data] key-path))]
      (or value not-found ""))))

