(ns shotbot.re-frame.feedback.views
  (:require [re-frame.core :refer [dispatch subscribe]]
            ))

(defn feedback-button []
  [:div.feedback-button-container
   [:a.feedback-button {:href "https://goo.gl/forms/WbXTm8zfUjjkDPnD3"
                        :target "_blank"
                        :on-click #(dispatch [:feedback/send-feedback])} "feedback"]])
