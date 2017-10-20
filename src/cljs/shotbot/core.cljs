(ns shotbot.core
  (:require [reagent.core :as r]
            [re-frame.core :refer [dispatch-sync clear-subscription-cache!]]
            [re-frisk-remote.core :refer [enable-re-frisk-remote!]]
            [madvas.re-frame.google-analytics-fx :as ga-fx]
            [shotbot.re-frame.views :as views]
            [shotbot.re-frame.subs]
            [shotbot.re-frame.events]
            [cljsjs.raven]
            ))

(enable-console-print!)

;; compile-time defines
(goog-define sentry-endpoint "")
(goog-define ga-tracking-id "")
(goog-define enable-re-frisk false)

;; Default app-db values.
;; Existing local-storage data will override values here.
(def default-db
  {:modals [:splash]})


(defn fig-reload []
  (clear-subscription-cache!)
  (r/force-update-all))


(defn ^:export main []
  ;; Sentry
  (when (not= sentry-endpoint "")
    (->> sentry-endpoint
         (.config js/Raven)
         (.install)))
  ;; Google Analytics
  (let [ga-enabled? (not= ga-tracking-id "")]
    (ga-fx/set-enabled! ga-enabled?)
    (when ga-enabled?
      (js/ga "create" ga-tracking-id "auto")))
  ;; re-frisk
  (when enable-re-frisk
    (enable-re-frisk-remote!))
  ;; initialize & mount app
  (dispatch-sync [:shotbot/initialize default-db])
  (r/render-component
    [views/main]
    (.getElementById js/document "app")))


(set! js/window.onload main)
