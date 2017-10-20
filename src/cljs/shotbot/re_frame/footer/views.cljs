(ns shotbot.re-frame.footer.views
  (:require [re-frame.core :refer [dispatch subscribe]]
            ))

(defn oursky-footer []
  [:div.footer
   [:p.first
    "❤ MADE BY " [:a {:href "https://oursky.com/"} "OURSKY"] " WITH LOVE"]
   [:p
    "Comments? Any Advice? Looking for Help?"]
   [:p.last
    "Reach us at " [:a {:href "mailto:support+shotbot@oursky.com"} "support+shotbot@oursky.com"]]])
