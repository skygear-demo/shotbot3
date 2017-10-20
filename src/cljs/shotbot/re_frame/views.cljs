(ns shotbot.re-frame.views
  (:require [re-frame.core :refer [subscribe]]
            [shotbot.re-frame.project.views :as project]
            [shotbot.re-frame.edit.views :as edit]
            [shotbot.re-frame.template.views :as template]
            [shotbot.re-frame.settings.views :as settings]
            [shotbot.re-frame.login.views :as login]
            [shotbot.re-frame.sign-up.views :as sign-up]
            [shotbot.re-frame.reset-password.views :as reset-password]
            ))

(defn loading-overlay []
  [:div.overlay.loading
   [:div.overlay--body
    [:p "Loading..."]]])

(defn splash-overlay []
  [:div.overlay.splash
   [:div.overlay--body
    [:img {:src "img/icon-shotbot.svg"}]
    [:p "Shotbot 3.0"]]])


(defn not-found-panel []
  [:h3 "Page Not Found."])

(defn main []
  (let [panel   (subscribe [:shotbot/panel])
        modals  (subscribe [:shotbot/modals])]
    (fn main-reaction []
      (let [panel-view  (case @panel
                          :login          [login/panel]
                          :sign-up        [sign-up/panel]
                          :reset-password [reset-password/panel]
                          :project        [project/panel]
                          :template       [template/panel]
                          :edit           [edit/panel]
                          #_else          [not-found-panel])
            modal-views (map (fn [modal]
                               (case modal
                                 :settings [settings/modal]
                                 :loading  [loading-overlay]
                                 :splash   [splash-overlay]
                                 #_else    [:div]))
                             @modals)]
        (into [] (concat [:div panel-view] modal-views))))))
