(ns shotbot.re-frame.edit.events
  (:require-macros [clojure.core.strint :refer [<<]])
  (:require [clojure.set :refer [subset?]]
            [goog.fs :as fs]
            [oops.core :refer [oget oset!]]
            [shotbot.re-frame.utils :refer [reg-event-fx reg-event-db]]
            [shotbot.render.utils :refer [render-to-canvas concat-canvases
                                          create-zip url->image canvas->url]]
            ))


(reg-event-fx
  :edit/initialize
  (fn initialize [{:keys [db]} _]
    (let [app-id (get-in db [:ls :app-id])]
      {:db (dissoc db :edit)
       :skygear [:query {:record "app"
                         :where {:_id {:= app-id}}}
                 :query {:record "shot"
                         :where {:app-id {:= app-id}}}
                 :success-dispatch [:edit/initialized]]
       :dispatch [:shotbot/modal-push :loading]})))

(reg-event-fx
  :edit/initialized
  (fn initialized [{:keys [db]} [[[app] shots]]]
    (let [shot-ids      (js->clj (oget app "shot-order"))
          template-id   (oget app "template-id")
          app-name      (oget app "name")
          shot-persistant-states
          (->> (for [shot shots]
                 (let [shot-id        (oget shot "_id")
                       template-index (oget shot "template-index")
                       user-data      (js->clj (or (oget shot "?data") {})
                                               :keywordize-keys true)
                       screen-url     (oget shot "?screen.url")
                       screen2-url    (oget shot "?screen2.url")]
                   [shot-id {:template-index template-index
                             :user-data (-> user-data
                                            (merge (if screen-url
                                                     {:screen {:url screen-url}}
                                                     {}))
                                            (merge (if screen2-url
                                                     {:screen2 {:url screen2-url}}
                                                     {})))}]))
               (into {}))]
      {:db (assoc db :edit {:app-name     app-name
                            :template-id  template-id
                            :shot-ids     shot-ids
                            :shot-derrived-states   {}
                            :shot-persistant-states shot-persistant-states
                            :shot-transient-states
                            (zipmap shot-ids
                                    (repeat {:show-inputs?  false
                                             :saved?        true}))})
       :load-shots (for [[shot-id {:keys [template-index user-data]}]
                         shot-persistant-states]
                     {:template-id      template-id
                      :template-index   template-index
                      :user-data        user-data
                      :success-dispatch [:edit/shot-loaded shot-id true]})
       :dispatch [:shotbot/modal-pop]})))

(reg-event-fx
  :edit/shot-loaded
  (fn shot-loaded [{:keys [db]} [shot-id update-thumbnail? render-data]]
    (let [shot-derrived-states
          (assoc (get-in db [:edit :shot-derrived-states])
                 shot-id render-data)
          db-fx {:db (assoc-in db [:edit :shot-derrived-states]
                               shot-derrived-states)}
          dispatch-fx (if (and update-thumbnail?
                               (subset? (set (get-in db [:edit :shot-ids]))
                                        (set (keys shot-derrived-states))))
                        {:dispatch [:edit/save-thumbnail]}
                        {})]
      (merge db-fx dispatch-fx))))

(reg-event-db
  :edit/toggle-inputs
  (fn toggle-inputs [db [shot-id]]
    (if-not (get-in db [:edit :shot-derrived-states shot-id])
      db ;; no-op when shot is loading
      (update-in db [:edit :shot-transient-states]
                 (fn [shot-transient-states]
                   (->> (for [[id data] shot-transient-states]
                          [id (if (= id shot-id)
                                (update data :show-inputs? not)
                                (assoc data :show-inputs? false))])
                        (into {})))))))

(reg-event-fx
  :edit/update-input
  (fn update-input [{:keys [db]} [shot-id key-path value]]
    (let [new-db (-> db
                     (assoc-in (into [:edit :shot-persistant-states
                                      shot-id :user-data]
                                     key-path)
                               value)
                     (assoc-in [:edit :shot-transient-states shot-id :saved?] false))
          template-id (get-in db [:edit :template-id])
          template-index (get-in db [:edit :shot-persistant-states
                                     shot-id :template-index])
          user-data (get-in new-db [:edit :shot-persistant-states
                                    shot-id :user-data])]
      {:db new-db
       :load-shots [{:template-id template-id
                     :template-index template-index
                     :user-data user-data
                     :success-dispatch [:edit/shot-loaded shot-id false]}]})))

(reg-event-fx
  :edit/save-inputs
  (fn save-inputs [{:keys [db]} [shot-id]]
    (let [shot-data   (-> (get-in db [:edit :shot-persistant-states
                                      shot-id :user-data])
                          (dissoc :screen)
                          (dissoc :screen2)
                          (clj->js))]
      {:db (assoc-in db [:edit :shot-transient-states shot-id :saved?] true)
       :skygear [:save [^:rec {:_type "shot"
                               :_id   shot-id
                               :data  shot-data}]
                 :success-dispatch [:shotbot/modal-pop]]
       :dispatch-n [[:shotbot/modal-push :loading]
                    [:edit/save-thumbnail]]})))

(reg-event-fx
  :edit/save-thumbnail
  (fn save-thumbnail [{:keys [db]} _]
    (let [thumbnail-url (->> (get-in db [:edit :shot-ids])
                             (map (partial conj [:edit :shot-derrived-states]))
                             (map (partial get-in db))
                             (map render-to-canvas)
                             (concat-canvases {:target-height 250
                                               :image-margin 10
                                               :shot-spacing 0.05})
                             (canvas->url {:img-format "image/jpeg"
                                           :quality 0.5}))]
      {:skygear [:save [^:rec {:_type "app"
                               :_id (get-in db [:ls :app-id])
                               :thumbnail thumbnail-url}]]})))

(reg-event-fx
  :edit/upload
  (fn upload [{:keys [db]} [shot-id asset-field file]]
    (let [obj-url (fs/createObjectUrl file)
          img-promise (url->image obj-url)]
      {:dispatch-promise [:edit/upload-validate img-promise shot-id asset-field file obj-url]
       :dispatch [:shotbot/modal-push :loading]})))

(reg-event-fx
  :edit/upload-validate
  (fn upload-validate [{:keys [db]} [img shot-id asset-field file obj-url]]
    (fs/revokeObjectUrl obj-url)
    (let [w img.naturalWidth
          h img.naturalHeight]
      (when-not (= [w h] [1080 1920])
        (js/alert (<< "The image uploaded had a dimension of ~{w}x~{h}.\n\nOptimal screenshots should be 1080 x 1920 - we will try to resize the image for you, but please try to use image that are in the exact size for best effect and avoid the image to be distorted.")))
      {:db (-> db
               (update-in [:edit :shot-derrived-states] dissoc shot-id)
               (assoc-in [:edit :shot-transient-states shot-id :show-inputs?] false))
       :skygear [:save [^:rec {:_type       "shot"
                               :_id         shot-id
                               asset-field  ^:asset {:file file
                                                     :name file.name}}]
                 :success-dispatch [:edit/upload-success shot-id]]})))

(reg-event-fx
  :edit/upload-success
  (fn upload-success [{:keys [db]} [shot-id shot]]
    (let [screen-url  (oget shot "?screen.url")
          screen2-url (oget shot "?screen2.url")]
      {:dispatch-n [[:shotbot/modal-pop]
                    [:edit/upload-reload-shot shot-id]]
       :db (-> db
               (assoc-in  [:edit :shot-derrived-states shot-id] nil)
               (update-in [:edit :shot-persistant-states shot-id :user-data]
                          (fn [user-data]
                            (-> user-data
                                (merge (if screen-url
                                         {:screen {:url screen-url}}
                                         {}))
                                (merge (if screen2-url
                                         {:screen2 {:url screen2-url}}
                                         {}))))))})))

(reg-event-fx
  :edit/upload-reload-shot
  (fn upload-reload-shot [{:keys [db]} [shot-id]]
    (let [template-id     (get-in db [:edit :template-id])
          template-index  (get-in db [:edit :shot-persistant-states shot-id :template-index])
          user-data       (get-in db [:edit :shot-persistant-states shot-id :user-data])]
      {:load-shots [{:template-id      template-id
                     :template-index   template-index
                     :user-data        user-data
                     :success-dispatch [:edit/shot-loaded shot-id true]}]})))

(reg-event-fx
  :edit/export
  (fn export [{:keys [db]} _]
    (if (js/confirm "Export screenshots?")
      (let [template-id (get-in db [:edit :template-id])
            shots (for [shot (vals (get-in db [:edit :shot-persistant-states]))]
                    (assoc shot :template-id template-id))]
        {:dispatch-promise [:edit/export-download (create-zip shots)]
         :dispatch [:shotbot/modal-push :loading]})
      {})))

(reg-event-fx
  :edit/export-download
  (fn export-download [{:keys [modernizr db]} [zip-blob]]
    ;; if <a download> attr is not avaliable (e.g. in Safari)
    ;; generate a download link using Skygear Asset
    (if (:adownload modernizr)
      {:save-blob [zip-blob "screenshots.zip"]
       :dispatch [:shotbot/modal-pop]}
      {:skygear [:save [^:rec {:_type "app"
                               :_id (get-in db [:ls :app-id])
                               :export-zip ^:asset {:file zip-blob
                                                    :name "screenshots.zip"}}]
                 :success-dispatch [:edit/export-download-asset]]})))

(reg-event-fx
  :edit/export-download-asset
  (fn export-download-asset [_ [app]]
    (->> (doto (.createElement js/document "iframe")
           (oset! "src" (oget app "export-zip.url"))
           (oset! "style.display" "none"))
         (.appendChild js/document.body))
    {:dispatch [:shotbot/modal-pop]}))

