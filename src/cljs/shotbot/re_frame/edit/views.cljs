(ns shotbot.re-frame.edit.views
  (:require [re-frame.core :refer [dispatch subscribe]]
            [reagent.core :as r]
            [oops.core :refer [oget oset!]]
            [shotbot.re-frame.header.views :refer [header-bar]]
            [shotbot.re-frame.feedback.views :refer [feedback-button]]
            [shotbot.re-frame.footer.views :refer [oursky-footer]]
            [shotbot.re-frame.shot.views :refer [shot-canvas]]
            [shotbot.re-frame.color-picker.views :refer [color-picker]]
            ))


(defmulti shot-input
  "Input component multimethod; dispatch on input type.
  Takes 2 args: shot-id and input-spec map.
  Requirements of input-spec depends on type, see methods below."
  (fn [_ spec] (:type spec)))

(defmethod shot-input :title
  [_ {:keys [content]}]
  [:div.title
   [:img {:src "img/icon-bulb.svg"}]
   [:span content]])

(defmethod shot-input :label
  [_ {:keys [content]}]
  [:h3.label content])

(defmethod shot-input :text
  [{:keys [shot-id]} {:keys [key-path placeholder]}]
  (let [value (subscribe [:edit/shot-data shot-id key-path placeholder])]
    [:textarea.text {:rows (->> @value
                                (filter (fn [ch] (= ch \newline)))
                                (count)
                                (+ 1))
                     :default-value @value
                     :on-click
                     (fn [evt]
                       (when (= evt.target.value placeholder)
                         (.select evt.target)))
                     :on-change
                     (fn [evt]
                       (dispatch [:edit/update-input shot-id key-path
                                  evt.target.value]))}]))

(defmethod shot-input :year
  [{:keys [shot-id]} {:keys [key-path placeholder]}]
  (let [value @(subscribe [:edit/shot-data shot-id key-path placeholder])]
    [:input.year {:type :number :step 1
                  :default-value value
                  :on-change
                  (fn [evt]
                    (dispatch [:edit/update-input shot-id key-path
                               evt.target.value]))}]))

(defmethod shot-input :text-year
  [{:keys [shot-id]} {:keys [text-key-path text-placeholder
                             year-key-path year-placeholder]}]
  [:div.text-year
   [shot-input {:shot-id shot-id} {:type :text
                                   :key-path text-key-path
                                   :placeholder text-placeholder}]
   [shot-input {:shot-id shot-id} {:type :year
                                   :key-path year-key-path
                                   :placeholder year-placeholder}]])

(defmethod shot-input :file
  [{:keys [shot-id]} {:keys [key-path asset-field label]}]
  ;; Trigger file pop-up programmatically to workaround Edge issue
  ;; Use hash of shot ID and asset-field as the unique identifier
  ;; https://developer.microsoft.com/en-us/microsoft-edge/platform/issues/8282613/
  (let [new-img?  (= @(subscribe [:edit/shot-data shot-id key-path]) "")
        input-id  (str "file-" (hash [shot-id asset-field]))]
    [:div.file
     [:label {:class (if new-img? "" "change")
              :on-click (fn [_]
                          (.. js/document
                              (getElementById input-id)
                              (click)))}
      [:img {:src (if new-img? "img/icon-up.svg" "img/icon-tick.svg")}]
      [:div.text-container
       [:span.maintext (if new-img? "Upload Screenshot" "Upload New Screenshot")]
       [:span.subtext {:style {:display (if label :block :none)}} label]]]
     [:input {:id input-id
              :type :file
              :accept "image/jpeg,image/png"
              :on-change
              (fn [evt]
                (let [file (-> evt .-target .-files (aget 0))]
                  (dispatch [:edit/upload shot-id asset-field file])))}]]))

(defmethod shot-input :color
  [{:keys [shot-id]} {:keys [key-path]}]
  (let [value @(subscribe [:edit/shot-data shot-id key-path "#3155A5"])]
    [color-picker {:value value
                   :on-change
                   (fn [evt]
                     (dispatch [:edit/update-input shot-id key-path
                                evt.target.value]))}]))

(defmethod shot-input :checkbox
  [{:keys [shot-id]} {:keys [key-path label]}]
  (let [value @(subscribe [:edit/shot-data shot-id key-path])]
    [:label.tickbox
     [:input {:type :checkbox
              :checked (or value false)
              :on-change
              (fn [evt]
                (dispatch [:edit/update-input shot-id key-path
                           evt.target.checked]))}]
     [:span label]]))



(defn shot-editor [{:keys [shot-id]}]
  (let [show-inputs?  (subscribe [:edit/show-inputs? shot-id])
        saved?        (subscribe [:edit/saved? shot-id])
        render-data   (subscribe [:edit/render-data shot-id])]
   (fn shot-editor-reaction []
     [:div.shot {:class (str (if @show-inputs? " inputs--show " "")
                             (if @saved? "" " unsaved-changes--show "))}
      [:span.unsaved-changes "Unsaved Changes"]
      [:div.display {:on-click (fn [_] (dispatch [:edit/toggle-inputs shot-id]))}
       [shot-canvas {:render-data @render-data}]]
      [:div.inputs-wrap
       [:div.inputs
        [:div
         (for [[idx input-spec] (map-indexed vector (:inputs @render-data))]
           [shot-input {:key idx :shot-id shot-id}
            input-spec])]
        [:div
         [:button.save {:disabled @saved?
                        :on-click (fn [_]
                                    (dispatch [:edit/save-inputs shot-id]))}
          "Save"]]]]])))

(defn panel []
  (let [app-name      (subscribe [:edit/app-name])
        template-name (subscribe [:edit/template-name])
        shot-ids      (subscribe [:edit/shot-ids])
        before-unload (fn [e]
                        (when-let [message @(subscribe [:edit/confirm-leave?])]
                          (oset! e "!returnValue" message)
                          message))]
    (r/create-class
      {:component-will-mount
       (fn [_]
         (js/addEventListener "beforeunload" before-unload))
       :component-will-unmount
       (fn [_]
         (js/removeEventListener "beforeunload" before-unload))
       :reagent-render
       (fn panel-reaction []
         [:div.edit-panel
          [:div
           [header-bar]
           [:header
            [:div.app-name @app-name]
            [:div.actions
             [:button.export {:on-click (fn [_] (dispatch [:edit/export]))}
              "Export"]]]
           [:div.template-name
            [:h2 "TEMPLATE"]
            [:h3 @template-name]]
           [:div.shot-list
            (for [shot-id @shot-ids]
              [shot-editor {:key shot-id :shot-id shot-id}])]]
          [feedback-button]
          [oursky-footer]])})))
