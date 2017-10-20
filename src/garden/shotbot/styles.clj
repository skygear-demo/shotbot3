(ns shotbot.styles
  (:require [garden.def :refer [defstyles]]
            [garden.selectors :as s]
            [garden.stylesheet :refer [at-font-face]]
            [shotbot.re-frame.project.styles :as project]
            [shotbot.re-frame.template.styles :as template]
            [shotbot.re-frame.edit.styles :as edit]
            [shotbot.re-frame.header.styles :as header]
            [shotbot.re-frame.footer.styles :as footer]
            [shotbot.re-frame.settings.styles :as settings]
            [shotbot.re-frame.color-picker.styles :as color-picker]
            [shotbot.re-frame.feedback.styles :as feedback]
            [shotbot.re-frame.android-beta.styles :as android-beta]
            [shotbot.re-frame.login.styles :as login]
            [shotbot.re-frame.sign-up.styles :as sign-up]
            [shotbot.re-frame.reset-password.styles :as reset-password]
            ))


(defstyles screen

  (at-font-face {:font-family "Roboto"
                 :font-weight 700
                 :src         "url(../../font/Roboto/Roboto-Bold.ttf)"})
  (at-font-face {:font-family "Arimo"
                 :font-weight 700
                 :src         "url(../../font/Arimo/Arimo-Bold.ttf)"})
  (at-font-face {:font-family "Montserrat"
                 :font-weight 400
                 :src         "url(../../font/Montserrat/Montserrat-Regular.ttf)"})
  (at-font-face {:font-family "Montserrat"
                 :font-weight 700
                 :src         "url(../../font/Montserrat/Montserrat-Bold.ttf)"})
  (at-font-face {:font-family "Lato"
                 :font-weight 900
                 :src         "url(../../font/Lato/Lato-Black.ttf)"})
  (at-font-face {:font-family "OpenSans"
                 :font-weight 400
                 :src         "url(../../font/OpenSans/OpenSans-Bold.ttf)"})

  [:button:focus {:outline "none"}]
  [:a {:cursor "pointer"}]
  [:body {:font-family "neuzeit-grotesk"}]

  [:html
   :body
   :#app
   (s/div (s/attr "data-reactroot")) { :height "100%"
                                       :width "100%"}]

  [:.overlay {:position "fixed"
              :top "0" :bottom "0"
              :left "0" :right "0"
              :display "flex"
              :align-items "center"
              :justify-content "space-around"}
   [:.overlay--body {:display "flex"
                     :align-items "center"
                     :font-size "4rem"}]]

  [:.overlay.loading {:background-color "rgba(0,0,0,0.7)"
                      :color "#FFF"}]

  [:.overlay.splash {:background-color "#FFF"
                     :color "#000"}
   [:img {:height "7rem"
          :margin "2rem"}]]

  [:.banner {:left "0" :right "0"
             :top "0" :height "40rem"
             :display "flex"
             :flex-direction "column"
             :align-items "center"
             :justify-content "space-around"}
   [:span {:font-size "4rem"
           :font-weight "bold"}]]

  project/styles
  template/styles
  edit/styles
  header/styles
  footer/styles
  settings/styles
  color-picker/styles
  feedback/styles
  android-beta/styles
  login/styles
  sign-up/styles
  reset-password/styles

  )
