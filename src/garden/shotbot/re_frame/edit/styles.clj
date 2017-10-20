(ns shotbot.re-frame.edit.styles)

(def styles
  [:.edit-panel {:min-height "100%"
                 :min-width "1280px"
                 :background-color "#F4F4F4"
                 :display "flex"
                 :justify-content "space-between"
                 :flex-direction "column"}
   [:.header {:background-color "#FFF"}]
   [:header {:padding "3rem 5rem 0"
             :min-width "1280px"
             :width "100%"
             :display "flex"
             :justify-content "space-between"
             :align-items "center"
             :padding-bottom "3rem"
             :background-color "#FFF"
             :border-bottom "1px solid #E4E4E4"}
    [:button {:background-color "#FFF"
              :border "1px solid #979797"
              :padding "1.5rem 3rem"}]
    [:.app-name {:font-size "3rem"}]
    [:.actions {:display "flex"}
     [:.share {:border "1px solid #6AD4BA"
               :margin-left "1rem"}]]]
   [:.template-name {:padding "3rem 5rem 0"}
    [:h2 {:font-size "1.25rem"
          :font-weight "bold"
          :letter-spacing "2px"
          :margin "0 0 0.5rem"}]
    [:h3 {:font-size "2.5rem"
          :color "#979797"
          :margin "0"}]]

   [:.shot-list {:display "flex"
                 :width "100%"
                 :min-width "1280px"
                 :overflow-x "scroll"
                 :padding "2.5rem 5rem 1rem"}
    [:.shot {:width "20%"
             :min-width "25rem"
             :margin-right "2rem"
             :box-shadow "0 0 5px rgba(0,0,0,0.8)"
             :background-color "#FFF"
             :display "flex"}
     [:&:last-child {:margin-right "0"}]
     [:.unsaved-changes {:background-color "#EC344A"
                         :color "#FFF"
                         :font-size "1.4rem"
                         :display "none"
                         :text-align "center"
                         :position "relative"
                         :height "2rem"
                         :width "12rem"
                         :margin-right "-12rem"
                         :top "-2rem"
                         :left "0"}]
     [:.display {:width "100%"
                 :line-height 0}
      [:img {:width "100%"}]
      [:canvas {:width "100%"
                :cursor "pointer"}]]
     [:.inputs-wrap {:position "relative"
                     :display "none"
                     :width "50%"}]
     [:.inputs {:position "absolute"
                :display "flex"
                :height "100%"
                :width "100%"
                :flex-direction "column"
                :justify-content "space-between"
                :overflow-y "scroll"
                :padding "1.5rem"}
      [:.title {:display "flex"
                :align-items "center"}
       [:img {:height "1.8rem"}]
       [:span {:margin-left "0.7rem"
               :font-size "1.7rem"
               :font-weight "bold"}]]
      [:.label {:display "block"
                :white-space "normal"
                :text-align "left"
                :color "#000"
                :font-size "1.2rem"
                :padding "0"
                :margin "2rem 0 1rem"}]
      [:.file
       [:label {:width "100%"
                :height "4rem"
                :margin "0.5rem 0"
                :padding "0"
                :cursor "pointer"
                :border "2px solid #6AD4BA"
                :background-color "#6AD4BA"
                :display "flex"
                :align-items "center"}
        [:&.change {:background-color "#FFF"}]
        [:img {:margin-left "1rem"}]
        [:.text-container {:display "flex"
                           :flex-direction "column"
                           :margin-left "1rem"}
         [:.maintext {:font-size "1.3rem"}]
         [:.subtext {:font-size "1rem"
                     :font-weight "normal"
                     :color "#888"}]]]
       [:input {:display "none"}]]
      [:.tickbox {:cursor "pointer"
                  :display "flex"
                  :align-items "center"}
       [:input {:margin "0"}]
       [:span {:margin-left "0.5rem"}]]
      [:textarea.text {:resize "none"
                       :overflow "hidden"}]
      [:.text
       :.year {:width "100%"
               :margin "0 0 0.5rem"
               :font-size "1.8rem"
               :border "none"
               :border-bottom "2px solid #CCC"}
       [:&:focus {:outline "none"
                  :border-bottom "2px solid #6AD4BA"}]]
      [:.text-year {:display "flex"
                    :align-items "flex-end"}
       [:.text {:width "65%"}]
       [:.year {:width "30%"
                :margin-left "5%"}]]
      [:button.save {:border "none"
                     :background-color "#6AD4BA"
                     :font-weight "bold"
                     :margin-top "1rem"
                     :padding "1.2rem 3.5rem"}
       [:&:disabled {:background-color "#CCC"}]]]
     [:&.inputs--show {:width "39.85%"
                       :min-width "50rem"
                       :border "3px solid #6AD4BA"}
      [:.display {:width "50%"}]
      [:.inputs-wrap {:display "block"}]]
     [:&.unsaved-changes--show
      [:.unsaved-changes {:display "block"}]]]]

  ])
