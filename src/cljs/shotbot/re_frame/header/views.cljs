(ns shotbot.re-frame.header.views
  (:require [re-frame.core :refer [dispatch subscribe]]
            ))

(defn header-bar []
  (let [panel (subscribe [:shotbot/panel])]
    (fn []
      [:div.header (cond
                    (= @panel :template) {:class "template-header"}
                    (= @panel :project) {:class "project-header"}
                    (= @panel :edit) {:class "edit-header"})
       [:div.logo {:on-click #(dispatch [:shotbot/switch-panel :project])}
        [:img {:src "img/icon-shotbot.svg"}]
        [:span "Shotbot"]]
       [:span.title]
       [:div.right
        [:div.settings {:on-click #(dispatch [:settings/show])}
         [:img {:src "img/icon-gear-black.svg"}]
         [:span "Settings"]]
        [:div.logout {:on-click #(dispatch [:header/logout])}
         [:img {:src "img/icon-logout.svg"}]
         [:span "Log out"]]
        ]])))
