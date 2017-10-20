(defproject shotbot3 "3.0.0-beta0"
  :description "Shotbot 3.0 screenshot creator"
  :url "https://shotbot.io/"

  :min-lein-version "2.6.1"
  :plugins [[lein-figwheel "0.5.7"]
            [lein-cljsbuild "1.1.4" :exclusions [[org.clojure/clojure]]]
            [lein-re-frisk "0.4.4"]
            [lein-garden "0.3.0"]
            [lein-hiera "0.9.5"]
            [lein-pdo "0.1.1"]
            [lein-skygear "0.1.5"]
            [lein-cljfmt "0.5.6"]]

  :dependencies [;; language core
                 [org.clojure/clojure "1.9.0-alpha13"]
                 [org.clojure/clojurescript "1.9.229"]
                 [org.clojure/core.incubator "0.1.4"]

                 ;; utilities
                 [binaryage/oops "0.1.0"]
                 [binaryage/devtools "0.8.2"]
                 [secretary "1.2.3"]
                 [re-frisk-remote "0.4.1"]

                 ;; framework
                 [reagent "0.6.0"]
                 [re-frame "0.9.2"]
                 [akiroz.re-frame/storage "0.1.1"]
                 [akiroz.re-frame/skygear "0.1.5-SNAPSHOT"]
                 [madvas.re-frame/google-analytics-fx "0.1.0"]

                 ;; forign libs
                 [cljsjs/jscolor "2.0.4-0"]
                 [cljsjs/jszip "3.1.3-0"]
                 [cljsjs/filesaverjs "1.3.3-0"]
                 [cljsjs/raven "3.9.1-0"]
                 ]

  ;; command aliases
  :aliases {"deploy-staging!"     ["do"
                                   ["clean"]
                                   ["garden" "once"]
                                   ["cljsbuild" "once" "staging"]
                                   ["deploy-skygear" "staging"]]
            "deploy-production!"  ["do"
                                   ["clean"]
                                   ["garden" "once"]
                                   ["cljsbuild" "once" "production"]
                                   ["deploy-skygear" "production"]]
            "dev"                 ["do"
                                   ["clean"]
                                   ["pdo"
                                    ["garden" "auto"]
                                    ["re-frisk"]
                                    ["figwheel"]]]
            }

  ;; generate source dependency tree
  :hiera {:path "doc/ns-graph.png"
          :vertical false
          :show-external true
          :ignore-ns #{re-frame re-frisk-remote oops reagent
                       garden cljs clojure goog}
          :cluster-depth 0}

  ;; clean generated files
  :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                    "resources/public/css/compiled"
                                    "target"]

  ;; watch CSS file changes
  :figwheel {:css-dirs ["resources/public/css"]
             :server-ip "localhost"}

  ;; compile CSS
  :garden {:builds [{:source-paths ["src/garden"]
                     :stylesheet shotbot.styles/screen
                     :compiler {:output-to "resources/public/css/compiled/screen.css"
                                :pretty-print? false}}]}

  ;; compile JS
  :cljsbuild {:builds
              [;; development build
               {:id "dev"
                :source-paths ["src/cljs"]
                :figwheel {:on-jsload shotbot.core/fig-reload}
                :compiler {:main shotbot.core
                           :output-to "resources/public/js/compiled/shotbot3.js"
                           :output-dir "resources/public/js/compiled/out"
                           :asset-path "js/compiled/out"
                           :libs ["src/js"]
                           :source-map-timestamp true
                           :closure-defines {shotbot.core/enable-re-frisk true

                                             shotbot.re-frame.events/skygear-end-point
                                             "https://shotbotdev.skygeario.com/"

                                             shotbot.re-frame.events/skygear-api-key
                                             "309b7c4ce9104b1b94d0c8b887d2cf77"}
                           :preloads [devtools.preload]}}

               ;; minified staging build
               {:id "staging"
                :source-paths ["src/cljs"]
                :compiler {:main shotbot.core
                           :output-to "resources/public/js/compiled/shotbot3.js"
                           :output-dir "target/out_staging"
                           :libs ["src/js"]
                           :closure-defines {shotbot.re-frame.events/skygear-end-point
                                             "https://shotbotstaging.skygeario.com/"

                                             shotbot.re-frame.events/skygear-api-key
                                             "85da2596bf35435b8dcc9df11e437d25"

                                             shotbot.core/ga-tracking-id
                                             "UA-90337813-1"

                                             shotbot.core/sentry-endpoint
                                             "[Masked]"}
                           :optimizations :advanced
                           :language-in :ecmascript5
                           :pretty-print false}}

               ;; minified production build
               {:id "production"
                :source-paths ["src/cljs"]
                :compiler {:main shotbot.core
                           :output-to "resources/public/js/compiled/shotbot3.js"
                           :output-dir "target/out_production"
                           :libs ["src/js"]
                           :closure-defines {shotbot.re-frame.events/skygear-end-point
                                             "https://shotbot.skygeario.com/"

                                             shotbot.re-frame.events/skygear-api-key
                                             "d55efd60669c41f0ac2792436a29f2bf"

                                             shotbot.core/ga-tracking-id
                                             "UA-90337813-1"

                                             shotbot.core/sentry-endpoint
                                             "[Masked]"}
                           :optimizations :advanced
                           :language-in :ecmascript5
                           :pretty-print false}}]}

  ;; deploy to skygear cloud
  :skygear {:dev        {:git-url "ssh://git@git.skygeario.com/shotbotdev.git"
                         :source-dir "src/cloud"
                         :static-dir "resources/public"}
            :staging    {:git-url "ssh://git@git.skygeario.com/shotbotstaging.git"
                         :source-dir "src/cloud"
                         :static-dir "resources/public"}
            :production {:git-url "ssh://git@git.skygeario.com/shotbot.git"
                         :source-dir "src/cloud"
                         :static-dir "resources/public"}}

  )
