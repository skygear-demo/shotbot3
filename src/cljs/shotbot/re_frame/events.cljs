(ns shotbot.re-frame.events
  (:require [re-frame.registrar :refer [get-handler]]
            [re-frame.core :refer [subscribe]]
            [shotbot.re-frame.utils :refer [reg-event-db reg-event-fx]]
            [shotbot.re-frame.header.events]
            [shotbot.re-frame.feedback.events]
            [shotbot.re-frame.android-beta.events]
            [shotbot.re-frame.project.events]
            [shotbot.re-frame.edit.events]
            [shotbot.re-frame.template.events]
            [shotbot.re-frame.settings.events]
            [shotbot.re-frame.login.events]
            [shotbot.re-frame.sign-up.events]
            [shotbot.re-frame.reset-password.events]
            ))

;; end-point / api-key defined at compile time in project.clj
(goog-define skygear-end-point "")
(goog-define skygear-api-key "")

(reg-event-fx
  :shotbot/initialize
  (fn initialize [{:keys [db]} [default-db]]
    {;; merge persistant fields from local storage
     :db (merge-with merge default-db db)
     :skygear [:config  {:end-point skygear-end-point
                         :api-key   skygear-api-key}
               :access  {:public :none}
               :success-dispatch [:shotbot/initialized]]}))

(reg-event-fx
  :shotbot/initialized
  (fn initialized [{:keys [db]} _]
    {:db (assoc db :modals [])
     :enable-history true}))

(reg-event-fx
  :shotbot/switch-panel
  (fn switch-panel [{:keys [db]} [panel]]
    (let [app-id        (get-in db [:ls :app-id])
          current-panel (get-in db [:ls :panel])
          ;; every panel can provide a sub :<panel>/confirm-leave?
          ;; to ask user for confirmation before leaving the panel
          ;; Expects a string confirmation message or nil.
          confirm-sub     (-> (name current-panel)
                              (str "/confirm-leave?")
                              (keyword))
          confirm-message (and (get-handler :sub confirm-sub false)
                               @(subscribe [confirm-sub]))]
      (if (and confirm-message
               (not (js/confirm confirm-message)))
        {}
        (merge
          {:db (assoc db :modals [])}
          (cond
            (panel #{:login
                     :sign-up
                     :project}) {:to-route [panel]}
            (panel #{:template
                     :edit})    {:to-route [panel app-id]}
            :else               {}))))))

(reg-event-fx
  :shotbot/init-panel
  (fn init-panel [{:keys [db]} [{:keys [panel args app-id]}]]
    (let [logged-in?    (get-in db [:ls :user-id])
          init-event-id (-> (name panel)
                            (str "/initialize")
                            (keyword))
          ;; dispatch initialize event only if exists
          dispatch-fx   (if (get-handler :event init-event-id false)
                          {:dispatch (into [init-event-id] args)}
                          {})]
      (cond
        (and logged-in?
             (panel #{:login
                      :sign-up
                      :reset-password}))  {:to-route [:project]}
        (and (not logged-in?)
             (panel #{:project
                      :template
                      :edit}))            {:to-route [:login]}
        :else                             (merge dispatch-fx
                                                 {:db (-> db
                                                          (assoc-in [:ls :app-id] app-id)
                                                          (assoc-in [:ls :panel] panel))})
        ))))

(reg-event-fx
  :shotbot/ga-track
  (fn ga-track [{{{:keys [user-id]} :ls} :db} [[id]]]
    (let [event-map {:sign-up/sign-up-success  ["auth"          "sign-up"]
                     :login/login-success      ["auth"          "login"]
                     :template/choose          ["project"       "create"]
                     :edit/export              ["project"       "export"]
                     :feedback/send-feedback   ["feedback"      "send"]
                     :android-beta/signup      ["android-beta"  "sign-up"]}]
      {:ga/set [{:userId user-id}]
       :ga/event (get event-map id)})))

(reg-event-db
  :shotbot/modal-push
  (fn modal-push [db [modal]]
    (update db :modals conj modal)))

(reg-event-db
  :shotbot/modal-pop
  (fn modal-pop [db _]
    (update db :modals pop)))

