(ns shotbot.render.add-ons
  (:require-macros [clojure.core.strint :refer [<<]])
  [:require [shotbot.render.drawing :refer [draw-image draw-text]]])

(defn badge [{:keys [text year color show?]
              [x y w h] :placement
              :as args}]
  (let [img-url (str "data:image/svg+xml,"
                     (-> (<< "
                             <svg width='149px' height='149px' viewBox='0 0 149 149' version='1.1' xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink'>
                             <defs>
                             <filter x='-50%' y='-50%' width='200%' height='200%' filterUnits='objectBoundingBox' id='filter-1'>
                             <feOffset dx='0' dy='2' in='SourceAlpha' result='shadowOffsetOuter1'></feOffset>
                             <feGaussianBlur stdDeviation='5' in='shadowOffsetOuter1' result='shadowBlurOuter1'></feGaussianBlur>
                             <feColorMatrix values='0 0 0 0 0   0 0 0 0 0   0 0 0 0 0  0 0 0 0.5 0' type='matrix' in='shadowBlurOuter1' result='shadowMatrixOuter1'></feColorMatrix>
                             <feMerge>
                             <feMergeNode in='shadowMatrixOuter1'></feMergeNode>
                             <feMergeNode in='SourceGraphic'></feMergeNode>
                             </feMerge>
                             </filter>
                             </defs>
                             <g filter='url(#filter-1)' transform='translate(5,5)'>
                             <circle fill='~{color}' cx='64.5' cy='64.5' r='64.5'></circle>
                             <g transform='translate(30.038835, 68.553398)' fill='#FFFFFF'>
                             <polygon points='4.76451614 7.51456311 1.81988323 9.06264824 2.38225807 5.78375131 -3.88578059e-16 3.4616236 3.29219968 2.98323898 4.76451614 0 6.2368326 2.98323898 9.52903228 3.4616236 7.14677421 5.78375131 7.70914905 9.06264824'></polygon>
                             <polygon points='19.7645161 7.51456311 16.8198832 9.06264824 17.3822581 5.78375131 15 3.4616236 18.2921997 2.98323898 19.7645161 0 21.2368326 2.98323898 24.5290323 3.4616236 22.1467742 5.78375131 22.7091491 9.06264824'></polygon>
                             <polygon points='34.7645161 7.51456311 31.8198832 9.06264824 32.3822581 5.78375131 30 3.4616236 33.2921997 2.98323898 34.7645161 0 36.2368326 2.98323898 39.5290323 3.4616236 37.1467742 5.78375131 37.7091491 9.06264824'></polygon>
                             <polygon points='49.7645161 7.51456311 46.8198832 9.06264824 47.3822581 5.78375131 45 3.4616236 48.2921997 2.98323898 49.7645161 0 51.2368326 2.98323898 54.5290323 3.4616236 52.1467742 5.78375131 52.7091491 9.06264824'></polygon>
                             <polygon points='64.7645161 7.51456311 61.8198832 9.06264824 62.3822581 5.78375131 60 3.4616236 63.2921997 2.98323898 64.7645161 0 66.2368326 2.98323898 69.5290323 3.4616236 67.1467742 5.78375131 67.7091491 9.06264824'></polygon>
                             </g>
                             </g>
                             </svg>
                             ")
                         (js/encodeURIComponent)))]
    {:images #{img-url}
     :render (fn [ctx images]
               (if-not show?
                 ctx
                 (doto ctx
                   (draw-image (get images img-url)
                               (select-keys args [:placement]))
                   (draw-text {:font (str "700 " (* h 0.093) "px Arimo")
                               :color "#FFF"
                               :text text
                               :x (+ x (* w 0.47))
                               :y (+ y (* h 0.37))})
                   (draw-text {:font (str "700 " (* h 0.15) "px Arimo")
                               :color "#FFF"
                               :text year
                               :x (+ x (* w 0.47))
                               :y (+ y (* h 0.74))})
                   )))}))


(defn fingers [args]
  (let [img-url "asset/add-on/fingers.png"]
    {:images #{img-url}
     :render (fn [ctx images]
               (doto ctx
                 (draw-image (get images img-url)
                             (select-keys args [:placement]))))}))
