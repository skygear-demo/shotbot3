(ns shotbot.re-frame.utils
  (:require [re-frame.core :as re-frame]
            [re-frame.core :refer [dispatch]]
            [oops.core :refer [oset!]]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [secretary.core :as secretary :refer-macros [defroute]]
            [shotbot.render.utils :refer [load-shot save-blob]]
            [akiroz.re-frame.storage :as storage]
            [akiroz.re-frame.skygear :as skygear]
            )
  (:import [goog History]))


;; URL routes
(secretary/set-config! :prefix "#")
(def history
  (doto (History.)
    (events/listen
      EventType/NAVIGATE
      (fn [evt] (secretary/dispatch! evt.token)))))
(defroute "/"
  {}
  (oset! js/location "href" "#/login"))
(defroute "/login"
  {}
  (dispatch [:shotbot/init-panel {:panel  :login
                                  :args   []}]))
(defroute "/sign-up"
  {}
  (dispatch [:shotbot/init-panel {:panel  :sign-up
                                  :args   []}]))
(defroute "/reset-password/:user-id/:reset-code/:expire-at"
  {:keys [user-id reset-code expire-at]}
  (dispatch [:shotbot/init-panel {:panel  :reset-password
                                  :args   [user-id reset-code expire-at]}]))
(defroute "/project"
  {}
  (dispatch [:shotbot/init-panel {:panel  :project
                                  :args   []}]))
(defroute "/template/:app-id"
  {:keys [app-id]}
  (dispatch [:shotbot/init-panel {:panel  :template
                                  :args   []
                                  :app-id app-id}]))
(defroute "/edit/:app-id"
  {:keys [app-id]}
  (dispatch [:shotbot/init-panel {:panel  :edit
                                  :args   []
                                  :app-id app-id}]))
(defroute "/not-found"
  {}
  (dispatch [:shotbot/init-panel {:panel  :not-found
                                  :args   []}]))
(defroute "*"
  {}
  (oset! js/location "href" "#/not-found"))

(def ga-tracking
  (re-frame/->interceptor
    :id     :ga-tracking
    :before (fn [context]
              (let [[id :as event] (get-in context [:coeffects :event])]
                (when (id #{:sign-up/sign-up-success
                            :login/login-success
                            :template/choose
                            :edit/export
                            :feedback/send-feedback
                            :android-beta/signup})
                  (re-frame/dispatch [:shotbot/ga-track event])))
              context)))

;; register :skygear fx
(skygear/reg-co-fx! {:fx :skygear})

(re-frame/reg-fx
  :dispatch-promise
  (fn [[event-id promise-obj & args]]
    (.then promise-obj
           (fn [promise-val]
             (re-frame/dispatch (into [event-id promise-val] args))))))

(re-frame/reg-fx
  :load-shots
  (fn [shot-data-list]
    (doseq [{:keys [template-id template-index user-data success-dispatch]} shot-data-list]
      (-> (load-shot {:template-id    template-id
                      :template-index template-index
                      :user-data      user-data})
          (.then (fn [render-data]
                   (re-frame/dispatch (conj success-dispatch render-data))))))))

(re-frame/reg-fx
  :save-blob
  (fn [[blob filename]]
    (save-blob blob filename)))

(re-frame/reg-fx
  :enable-history
  (fn [_]
    (.setEnabled history true)))

(re-frame/reg-fx
  :to-route
  (fn [[panel & parts]]
    (oset! js/location "hash"
           (str "#/" (name panel)
                (apply str (interleave (repeat "/") parts))))))

(re-frame/reg-cofx
  :modernizr
  (fn [coeffects _]
    (assoc coeffects :modernizr
           (js->clj js/Modernizr :keywordize-keys true))))

(defn reg-event-db
  "local-storage injected reg-event-db, same calling semantics.
  an arity 3 variant is added to allow interceptors just like the fx version.
  The :ls key inside :db is automitically persisted."
  ([event-id interceptor-vec handler]
   (re-frame/reg-event-fx
     event-id
     (into [ga-tracking
            re-frame/trim-v
            (storage/persist-db :shotbot :ls)]
           interceptor-vec)
     (fn [{:keys [db]} event-vec]
       {:db (handler db event-vec)})))
  ([event-id handler]
   (reg-event-db event-id [] handler)))


(defn reg-event-fx
  "local-storage injected reg-event-fx, same calling semantics."
  ([event-id interceptor-vec handler]
   (re-frame/reg-event-fx
     event-id
     (into [ga-tracking
            re-frame/trim-v
            (re-frame/inject-cofx :modernizr)
            (storage/persist-db :shotbot :ls)]
           interceptor-vec)
     handler))
  ([event-id handler]
   (reg-event-fx event-id [] handler)))
