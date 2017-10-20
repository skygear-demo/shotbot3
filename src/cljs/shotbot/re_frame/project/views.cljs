(ns shotbot.re-frame.project.views
  (:require [re-frame.core :refer [dispatch subscribe]]
            [shotbot.re-frame.header.views :refer [header-bar]]
            [shotbot.re-frame.feedback.views :refer [feedback-button]]
            [shotbot.re-frame.android-beta.views :refer [android-beta-signup-view]]
            [shotbot.re-frame.footer.views :refer [oursky-footer]]
            ))

(defn new-app-view []
  (let [new-app-name (subscribe [:project/new-app-name]) new-app-name-input-color (subscribe [:project/new-app-name-input-color])]
    (fn new-app-view-reaction []
      [:form.app.new-app {:on-submit
                          (fn [e]
                            (.preventDefault e)
                            (dispatch [:project/create-new-app]))}
       [:div.left
        [:div
         [:span "Create a New Project"]
         [:p.sub "Make a new set of screenshots for iTunes Connect or exporting them for promotion."]]
        [:textarea {:rows 1
                    :required true
                    :max-length "50"
                    :placeholder "My App Name"
                    :value @new-app-name
                    :style {:border-bottom (str "3px solid" (if (clojure.string/blank? @new-app-name-input-color) "#D8D8D8" @new-app-name-input-color))}
                    :on-change
                    (fn [evt]
                      (let [offset (- evt.target.offsetHeight evt.target.clientHeight)]
                        (set! evt.target.style.height "auto")
                        (set! evt.target.style.height (str (+ offset evt.target.scrollHeight) "px"))
                      )
                      (dispatch [:project/set-new-app-name-input-color evt.target.value])
                      (dispatch [:project/set-new-app-name evt.target.value]))}]]
       [:div.right
        [:input {:type "submit"
                 :value ""}]]])))

(defn thumbnail [app-id]
 (let [app-thumbnail (subscribe [:project/app-thumbnail app-id])]
   (if (empty? @app-thumbnail)
    [:div "Continue to choose a template"]
    [:img {:src @app-thumbnail}]
   )))

(defn app-view [app-id]
  (let [app-name      (subscribe [:project/app-name app-id])
        app-thumbnail (subscribe [:project/app-thumbnail app-id])]
    (fn app-view-reaction []
      [:div.app {:on-click
                 (fn [_]
                   (dispatch [:project/choose-app app-id]))}
       [:div.top
        [:span @app-name]]
       [:div.bottom
        [thumbnail app-id]]])))

(defn panel []
  (let [app-ids (subscribe [:project/app-ids])]
    (fn panel-reaction []
      [:div.project-panel
       [:div
        [header-bar]
        [:div.project-container
         [:div.app-container
          [new-app-view]]
         (for [app-id @app-ids]
           [:div.app-container {:key app-id}
            [app-view app-id]])]
         [android-beta-signup-view]]
       [feedback-button]
       [oursky-footer]])))
