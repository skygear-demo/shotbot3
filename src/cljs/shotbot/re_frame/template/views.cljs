(ns shotbot.re-frame.template.views
  (:require [clojure.string :as string]
            [re-frame.core :refer [dispatch subscribe]]
            [shotbot.re-frame.header.views :refer [header-bar]]
            [shotbot.render.templates :refer [templates]]
            [shotbot.re-frame.feedback.views :refer [feedback-button]]
            [shotbot.re-frame.footer.views :refer [oursky-footer]]
            ))

(defn panel []
  [:div.template-panel
   [:div
    [header-bar]
    [:img.back-arrow {:src "img/icon-arrow-left.svg"
                      :on-click #(dispatch [:template/back])}]
    [:div.banner
     [:span "Choose a Template"]]
    [:div.container-fluid
     [:div.row.first
      [:div.col-md-8.col-md-offset-2.statement
       "Pick a template to get started with according to app story. Never mind the theme colours, you can customize them in the next step."]]
     (for [[template-id {template-name :name
                         template-desc :description}] templates]
       [:div.row {:key template-id}
        [:div.col-md-8.col-md-offset-2.option
         [:div.option-header
          [:div
           [:span.name template-name]
           [:p.desc template-desc]]
          [:buttom.choose
           {:on-click #(dispatch [:template/choose template-id])}
           "Choose"]]
         [:img.option-preview {:src (str "img/" template-id ".jpg")}]]])
     ]]
   [feedback-button]
   [oursky-footer]])
