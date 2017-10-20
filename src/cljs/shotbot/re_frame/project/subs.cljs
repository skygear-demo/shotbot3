(ns shotbot.re-frame.project.subs
  (:require [re-frame.core :refer [reg-sub]]))


(reg-sub
  :project/db
  (fn db [db _]
    (:project db)))

(reg-sub
  :project/new-app-name
  :<- [:project/db]
  (fn new-app-name [project-db _]
    (:new-app-name project-db)))

(reg-sub
  :project/new-app-name-input-color
  :<- [:project/db]
  (fn new-app-name [project-db _]
    (:new-app-name-input-color project-db)))

(reg-sub
  :project/apps
  :<- [:project/db]
  (fn apps [project-db _]
    (:apps project-db)))

(reg-sub
  :project/app-ids
  :<- [:project/apps]
  (fn app-ids [apps _]
    (->> (sort-by (comp :created-at val) apps)
         (reverse)
         (map key))))

(reg-sub
  :project/app-name
  :<- [:project/apps]
  (fn app-name [apps [_ app-id]]
    (let [app-name (get-in apps [app-id :name])]
      (if (> (count app-name) 24)
        (str (subs app-name 0 21) "...")
        app-name))))

(reg-sub
  :project/app-thumbnail
  :<- [:project/apps]
  (fn app-thumbnail [apps [_ app-id]]
    (get-in apps [app-id :thumbnail])))
