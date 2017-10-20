(ns shotbot.render.templates
  (:require [cljs.spec :as s]
            [shotbot.render.add-ons :as add-ons]
            [shotbot.render.layouts :as layouts]))

;; Template Spec ===========================================

(s/def ::layout fn?)
(s/def ::shots
  (s/coll-of (s/keys :req-un [::layout])
             :count 5
             :kind vector?))


(s/def ::name string?)
(s/def ::description string?)
(s/def ::templates
  (s/map-of string?
            (s/keys :req-un [::name
                             ::description
                             ::shots])))


;; Templates ==========================================

(def common-template
  {:width 1242
   :height 2208
   :description ""
   :screen {:url "img/screenshot-placeholder.png"}
   :screen2 {:url "img/screenshot-placeholder.png"}
   })

(def templates
  {"template-1"
   (assoc
     common-template
     :name                "Highlights & Benefits"
     :description         "Got any awards? Let App Store visitors know. You can emphasize app ranking in certain categories or territories as well."
     :background-color    "#5AB6DD"
     :tagline-color       "#FFFFFF"
     :subtagline-color    "#FFFFFF"
     :tagline-font        "700 72pt Arimo"
     :subtagline-font     "700 47pt Arimo"
     :tagline-y           270
     :shots [{:layout                 layouts/tagline-add-on
              :title                  "Credits & Awards"
              :tagline-hint           "How many happy users or any award?"
              :tagline                "Best in 2015 with over\n3M happy users!"
              :tagline-placeholder    "Best in 2015 with over\n3M happy users!"
              :screenshot-hint        "The Main screenshot"
              :frame                  {:url "asset/device/iphone-6s-silver-floor.png"
                                       :placement [215 442 811 1590]}
              :screen                 {:placement [289 642 664 1174]}
              :add-on                 {:placement         [778 1675 385 385]
                                       :template          add-ons/badge
                                       :text              "BEST NEW\nAPP"
                                       :text-placeholder  "BEST NEW\nAPP"
                                       :year              (.getFullYear (js/Date.))
                                       :year-placeholder  (.getFullYear (js/Date.))
                                       :color             "#3155A5"}}
             {:layout                 layouts/tagline
              :title                  "Benefit"
              :tagline-hint           "Write down a benefit of your app"
              :tagline                "Quick and clean\ninterface"
              :tagline-placeholder    "Quick and clean\ninterface"
              :screenshot-hint        "Screenshot that shows the benefit"
              :frame                  {:url "asset/device/iphone-6s-silver-shadow.png"
                                       :placement [116 560 1068 2001]}
              :screen                 {:placement [180 800 842 1445]}}
             {:layout                 layouts/tagline
              :title                  "Benefit"
              :tagline-hint           "What's another benefit of your app?"
              :tagline                "Fast and simple"
              :tagline-placeholder    "Fast and simple"
              :tagline-y              1944
              :screenshot-hint        "Screenshot that shows the benefit"
              :frame                  {:url "asset/device/iphone-6s-silver-shadow.png"
                                       :placement [117 -305 1067 2001]}
              :screen                 {:placement [185 -100 834 1482]}}
             {:layout                 layouts/tagline-dual
              :title                  "Benefit"
              :tagline-hint           "One more benefit points, relate to your key feature:"
              :tagline                "Save your time\nbooking hotel"
              :tagline-placeholder    "Save your time\nbooking hotel"
              :screenshot-hint        "Screenshot that shows the benefit"
              :frame                  {:url "asset/device/iphone-6s-black-shadow.png"
                                       :placement [42 447 675 1273]}
              :screen                 {:placement [80 600 497 883]}
              :frame2                 {:url "asset/device/iphone-6s-silver-shadow2.png"
                                       :placement [600 820 673 1271]}
              :screen2                {:placement [640 970 497 883]}}
             {:layout                 layouts/tagline-sub-add-on
              :title                  "User / Media quote"
              :tagline-hint           "Put down a testimonial in a sentence:"
              :tagline                "\"I love how this app\nmade my life easier!\""
              :tagline-placeholder    "\"I love how this app\nmade my life easier!\""
              :tagline-y              1783
              :subtagline             "App Magazine"
              :subtagline-placeholder "App Magazine"
              :subtagline-y           2047
              :screenshot-hint        "Screenshot of the feature mentioned"
              :frame                  {:url "asset/device/iphone-6s-silver-floor.png"
                                       :placement [281 177 679 1331]}
              :screen                 {:placement [342 340 555 982]}
              :add-on                 {:placement         [721 57 408 408]
                                       :template          add-ons/badge
                                       :text              "BEST NEW\nAPP"
                                       :text-placeholder  "BEST NEW\nAPP"
                                       :year              (.getFullYear (js/Date.))
                                       :year-placeholder  (.getFullYear (js/Date.))
                                       :color             "#3155A5"}}
             ])

   "template-2"
   (assoc
     common-template
     :name                "Show case your Features"
     :description         "Explaining your key features with screenshots directly. Show your visitors what you've got for them and why should they pick your app among you and your competitors."
     :background-color    "#FCFBFC"
     :tagline-color       "#000000"
     :subtagline-color    "#555555"
     :tagline-font        "700 78pt Montserrat"
     :subtagline-font     "400 47pt Montserrat"
     :tagline-y           290
     :subtagline-y        410
     :shots [{:layout                 layouts/tagline-sub
              :title                  "App Summary"
              :tagline-hint           "What does your app do? (In one sentence)"
              :tagline                "Your food portal\nin your pocket."
              :tagline-placeholder    "Your food portal\nin your pocket."
              :subtagline             ""
              :subtagline-placeholder ""
              :screenshot-hint        "A screenshot that summarize what your app does"
              :frame                  {:url "asset/device/iphone-6s-gold-tilt.png"
                                       :placement [200 481 783 2314]}
              :screen                 {:transform [223,1002 882,725 882,2528 223,2559]}}
             {:layout                 layouts/tagline-sub
              :title                  "Highlight a Feature"
              :tagline-hint           "Highlight a feature that makes your app unique:"
              :tagline                "Order your meal\nfrom anywhere!"
              :tagline-placeholder    "Order your meal\nfrom anywhere!"
              :subtagline             ""
              :subtagline-placeholder ""
              :screenshot-hint        "Show the highlighted feature"
              :frame                  {:url "asset/device/iphone-6s-gold-floor.png"
                                       :placement [111 556 1020 2015]}
              :screen                 {:placement [204 808 834 1487]}}
             {:layout                 layouts/tagline-sub
              :title                  "Feature"
              :tagline-hint           "What else does the app do?"
              :tagline                "Search for\nbest restaurants"
              :tagline-placeholder    "Search for\nbest restaurants"
              :subtagline             ""
              :subtagline-placeholder ""
              :tagline-y              2013
              :subtagline-y           1870
              :screenshot-hint        "Feature screenshot"
              :frame                  {:url "asset/device/iphone-6s-gold-floor.png"
                                       :placement [111 -255 1020 2015]}
              :screen                 {:placement [203 -5 835 1487]}}
             {:layout                 layouts/tagline-sub
              :title                  "Feature"
              :tagline-hint           "What else does the app do?"
              :tagline                "Rate and send\nyour feedback!"
              :tagline-placeholder    "Rate and send\nyour feedback!"
              :subtagline             ""
              :subtagline-placeholder ""
              :screenshot-hint        "Feature screenshot"
              :frame                  {:url "asset/device/iphone-6s-gold-floor.png"
                                       :placement [111 556 1020 2015]}
              :screen                 {:placement [204 808 834 1486]}}
             {:layout                 layouts/tagline-sub
              :title                  "Feature"
              :tagline-hint           "Tell the visitors one more killer feature of your app:"
              :tagline                "Enjoy your delivery\nwithin 20 minutes!"
              :tagline-placeholder    "Enjoy your delivery\nwithin 20 minutes!"
              :subtagline             ""
              :subtagline-placeholder ""
              :screenshot-hint        "Feature screenshot"
              :frame                  {:url "asset/device/iphone-6s-gold-isometric.png"
                                       :placement [-300 568 1863 2333]}
              :screen                 {:transform [510,840 1360,840 550,2650 -200,2400]}}
             ])

   "template-3"
   (assoc
     common-template
     :name                "Best for Games"
     :description         "Put up the best screenshots to attract App Store visitors. Share the fun bits of your game!"
     :background-image    "asset/background/1.jpg"
     :tagline-color       "#FFFFFF"
     :subtagline-color    "#FFFFFF"
     :tagline-font        "900 62pt Lato"
     :subtagline-font     "900 36pt Lato"
     :tagline-y           230
     :shots [{:layout                 layouts/tagline
              :title                  "Game Overview"
              :tagline-hint           "What is your game? (In one sentence)"
              :tagline                "The adventure to the\nsecret island."
              :tagline-placeholder    "The adventure to the\nsecret island."
              :tagline-y              260
              :screenshot-hint        "A screenshot that summarize your game"
              :frame                  {:url "asset/device/iphone-6s-black-floor.png"
                                       :placement [135 500 972 1928]}
              :screen                 {:placement [208 720 795 1417]}}
             {:layout                 layouts/tagline-fullshot
              :title                  "Game Play"
              :tagline-hint           "What can player do in your game?"
              :tagline                "Attack with freaky skills"
              :tagline-placeholder    "Attack with freaky skills"
              :screenshot-hint        "Selected gameplay screenshot"
              :screen                 {:placement [0 0 1242 2208]}}
             {:layout                 layouts/tagline-fullshot
              :title                  "Game Play"
              :tagline-hint           "What can player do in your game?"
              :tagline                "Build your own characters"
              :tagline-placeholder    "Build your own characters"
              :screenshot-hint        "Selected gameplay screenshot"
              :screen                 {:placement [0 0 1242 2208]}}
             {:layout                 layouts/tagline-fullshot
              :title                  "Game Play"
              :tagline-hint           "What can player do in your game?"
              :tagline                "Collect special items"
              :tagline-placeholder    "Collect special items"
              :screenshot-hint        "Selected gameplay screenshot"
              :screen                 {:placement [0 0 1242 2208]}}
             {:layout                 layouts/tagline-sub-add-on
              :title                  "Credits & Awards"
              :tagline-hint           "How many happy users or any award?"
              :tagline                "Best in 2015 with over\n3M happy players!"
              :tagline-placeholder    "Best in 2015 with over\n3M happy players!"
              :tagline-y              1898
              :subtagline             "App Magazine"
              :subtagline-placeholder "App Magazine"
              :subtagline-y           2116
              :screenshot-hint        "The Main screenshot"
              :frame                  {:url "asset/device/iphone-6s-black-floor.png"
                                       :placement [241 143 759 1495]}
              :screen                 {:placement [303 320 620 1102]}
              :add-on                 {:placement         [750 50 385 385]
                                       :template          add-ons/badge
                                       :text              "BEST NEW\nAPP"
                                       :text-placeholder  "BEST NEW\nAPP"
                                       :year              (.getFullYear (js/Date.))
                                       :year-placeholder  (.getFullYear (js/Date.))
                                       :color             "#3155A5"}}
             ])

   "template-4"
   (assoc
     common-template
     :name                "Tell your App Story"
     :description         "Show off the uniqueness of your app by explaining its key gestures, interactions and how it works. Stories are always the best way to convey values."
     :tagline-color       "#FFFFFF"
     :subtagline-color    "#FFFFFF"
     :tagline-font        "700 75pt OpenSans"
     :subtagline-font     "700 48pt OpenSans"
     :tagline-y           390
     :subtagline-y        570
     :shots [{:layout                 layouts/tagline
              :title                  "Your app story"
              :tagline-hint           "What is the core problem your app solved?"
              :tagline                "Book hotel\nnow becomes\neffortless!"
              :tagline-placeholder    "Book hotel\nnow becomes\neffortless!"
              :screenshot-hint        "Screenshot that shows the benefit"
              :background-image       "asset/background/2.jpg"
              :frame                  {:url "asset/device/iphone-6s-silver-hand-isometric.png"
                                       :placement [0 880 1142 1329]}
              :screen                 {:transform [625,980 1070,1070 630,1900 195,1755]}}
             {:layout                 layouts/tagline-sub
              :title                  "App usage"
              :tagline-hint           "When / where to use your app?"
              :tagline                "Make reservation\non the go"
              :tagline-placeholder    "Make reservation\non the go"
              :subtagline             ""
              :subtagline-placeholder ""
              :screenshot-hint        "Usage of your app"
              :background-image       "asset/background/3.jpg"
              :frame                  {:url "asset/device/iphone-6s-silver-hand-tilt.png"
                                       :placement [13 815 1227 1393]}
              :add-on                 {:template add-ons/fingers
                                       :placement [840 1440 449 746]
                                       :show? true}
              :screen                 {:transform [91,1215 690,970 1115,2030 520,2270]}}
             {:layout                 layouts/tagline-sub
              :title                  "App usage"
              :tagline-hint           "When / where to use your app?"
              :tagline                "...and rate them\nafterwards."
              :tagline-placeholder    "...and rate them\nafterwards."
              :subtagline             ""
              :subtagline-placeholder ""
              :screenshot-hint        "Usage of your app"
              :background-image       "asset/background/4.jpg"
              :frame                  {:url "asset/device/iphone-6s-silver-hand.png"
                                       :placement [96 779 1127 1434]}
              :screen                 {:placement [304 956 589 1043]}}
             {:layout                 layouts/tagline-sub
              :title                  "App usage"
              :tagline-hint           "When / where to use your app?"
              :tagline                "Works just well\nfor hostels\nand home stays."
              :tagline-placeholder    "Works just well\nfor hostels\nand home stays."
              :subtagline             ""
              :subtagline-placeholder ""
              :screenshot-hint        "Usage of your app"
              :background-image       "asset/background/5.jpg"
              :frame                  {:url "asset/device/iphone-6s-silver-hand.png"
                                       :placement [96 779 1127 1434]}
              :screen                 {:placement [304 956 589 1043]}}
             {:layout                 layouts/tagline-sub
              :title                  "Call to action"
              :tagline-hint           "A strong line to call for action:"
              :tagline                "Download and Enjoy\nevery moment\nin your trip."
              :tagline-placeholder    "Download and Enjoy\nevery moment\nin your trip."
              :tagline-y              310
              :subtagline             ""
              :subtagline-placeholder ""
              :screenshot-hint        "A screenshot that summarize what your app does"
              :background-image       "asset/background/6.jpg"
              :frame                  {:url "asset/device/iphone-6s-silver-floor.png"
                                       :placement [281 662 679 1331]}
              :screen                 {:placement [342 827 555 982]}}
             ])

   "template-5"
   (assoc
     common-template
     :name                "New update release"
     :description         "Ready to release a new update? Feature your killer features to your users!"
     :background-color    "#81DC57"
     :tagline-color       "#FFFFFF"
     :subtagline-color    "#FFFFFF"
     :tagline-font        "400 82pt Montserrat"
     :subtagline-font     "400 52pt Montserrat"
     :tagline-y           280
     :subtagline-y        430
     :shots [{:layout                 layouts/tagline
              :title                  "New Feature"
              :tagline-hint           "The latest update of your app, starts with “Now with [NEW FEATURE]” is a good idea"
              :tagline                "Now with\n[NEW FEATURE]"
              :tagline-placeholder    "Now with\n[NEW FEATURE]"
              :screenshot-hint        "The latest added feature"
              :frame                  {:url "asset/device/iphone-6s-generic-shadow.png"
                                       :placement [227 495 814 1597]}
              :screen                 {:placement [279 680 660 1164]}}
             {:layout                 layouts/tagline-sub
              :title                  "Turn on the feature"
              :tagline-hint           "How to turn on the feature?"
              :tagline                "Auto enabled with\nthe latest update!"
              :tagline-placeholder    "Auto enabled with\nthe latest update!"
              :subtagline             ""
              :subtagline-placeholder ""
              :screenshot-hint        "Guide to turn on the new feature"
              :frame                  {:url "asset/device/iphone-6s-generic-shadow.png"
                                       :placement [138 555 987 1955]}
              :screen                 {:placement [202 784 803 1424]}}
             {:layout                 layouts/tagline-sub
              :title                  "New Feature Benefit"
              :tagline-hint           "What's the benefit with this feature?"
              :tagline                "Save you\nmore time!"
              :tagline-placeholder    "Save you\nmore time!"
              :subtagline             ""
              :subtagline-placeholder ""
              :frame                  {:url "asset/device/iphone-6s-generic-shadow.png"
                                       :placement [138 555 987 1955]}
              :screen                 {:placement [202 784 803 1424]}}
             {:layout                 layouts/tagline-sub-dual
              :title                  "Existing Feature"
              :tagline-hint           "Don't forget to tag the original features you have!"
              :tagline                "Share photo\nwith your friends\nand family"
              :tagline-placeholder    "Share photo\nwith your friends\nand family"
              :tagline-y              2013
              :subtagline             ""
              :subtagline-placeholder ""
              :subtagline-y           1870
              :screenshot-hint        "Screenshot of the feature mentioned"
              :frame                  {:url "asset/device/iphone-6s-generic-shadow.png"
                                       :placement [13 44 647 1277]}
              :screen                 {:placement [52 189 527 933]}
              :frame2                 {:url "asset/device/iphone-6s-generic-shadow.png"
                                       :placement [622 408 647 1277]}
              :screen2                {:placement [661 551 527 933]}}
             {:layout                 layouts/tagline-sub-dual
              :title                  "Existing Feature"
              :tagline-hint           "Don't forget to tag the original features you have!"
              :tagline                "Get updates\nat anywhere!"
              :tagline-placeholder    "Get updates\nat anywhere!"
              :tagline-y              2013
              :subtagline             ""
              :subtagline-placeholder ""
              :subtagline-y           1870
              :screenshot-hint        "Screenshot of the feature mentioned"
              :frame                  {:url "asset/device/iphone-6s-generic-shadow.png"
                                       :placement [13 411 647 1277]}
              :screen                 {:placement [51 555 527 933]}
              :frame2                 {:url "asset/device/iphone-6s-generic-shadow.png"
                                       :placement [621 39 647 1277]}
              :screen2                {:placement [660 189 527 933]}}
             ])

   })


;; Validate data structure on load
(when-not (s/valid? ::templates templates)
  (throw (str "[shotbot] Error: Template Data not Valid!\n"
              (with-out-str (s/explain ::templates templates)))))
