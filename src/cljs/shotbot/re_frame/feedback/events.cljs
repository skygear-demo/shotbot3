(ns shotbot.re-frame.feedback.events
  (:require [shotbot.re-frame.utils :refer [reg-event-fx reg-event-db]]))

(reg-event-fx
  :feedback/send-feedback
  (fn send-feedback [_ _]
    {}))
