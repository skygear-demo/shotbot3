## Shotbot 3.0 Documentation

- [Project Layout, Build Instructions, Guidelines.][1]
- [re-frame Integration (custom (co)fx, interceptor)][2]
- [Skygear Integration (records, cloud code, plugin)][3]
- [Screenshot Rendering Archetecture][4]


## Software Stack

- Backend / Static Hosting: [Skygear Cloud][skygear]
- Language: [ClojureScript][cljs]
- Build Tool: [Leiningen][lein] + [figwheel][figwheel]
- CSS Preprocessor: [Garden][garden]
- Client-Side Framework: [re-frame][re-frame] + [reagent][reagent]
- URL Routing [Secretary][secretary]


### Prerequisites

- git (deploy)
- [Leiningen][lein] (build)
  - Java 8
- [react-devtools][react-devtools] (dev)
  - Chromium
- [cljs-devtools][cljs-devtools] (dev)
  - Chromium 47+
- [rlwrap][rlwrap] (dev)
- [graphviz][graphviz] (doc gen)


## Gotchas

Please keep this list updated for anything that's counter-intuitive in this project!

**Browser Ad-Block:**

If your browser has an ad-block plugin that blocks network requests with "google analytics",
the development build will not work due to dynamic module loading.

**File Naming:**

Namespaces are in `lisp-case` but files & folders are in `snake_case`.
Google closure will throw errors if you name your file/folders with lisp-case because they
are not valid JS identifiers.

**Changes not Working:**

If you manually refresh the page instead of using hot-reload, Chrome will cache the
old JS/CSS files making the changes ineffective during development. You can open a
new private window to mitigate this.

**Adding new Fonts:**

When you add a new font that's used by the canvas, make sure you pre-load the font in
`app.html` using an off-screen element so that the font will be available during the
initial render

**Semi-Controlled Input Fields:**

Due to the way React controlled input fields work, some IMEs (mainly CJK input
methods) will not work properly. To work around this issue, we used "semi-controlled"
inputs; changes on the UI will propagate to the data store via "onChange" but changes
to the data store will *not* propagate back to the UI.
Ref: https://github.com/oursky/shotbot3/issues/179

[1]: general.md
[2]: re-frame.md
[3]: skygear.md
[4]: rendering.md

[skygear]: https://skygear.io
[goog]: https://developers.google.com/closure/library
[cljs]: https://clojurescript.org
[lein]: http://leiningen.org
[graphviz]: http://www.graphviz.org/

[figwheel]: https://github.com/bhauman/lein-figwheel
[garden]: https://github.com/noprompt/garden
[re-frame]: https://github.com/Day8/re-frame
[reagent]: https://github.com/reagent-project/reagent
[secretary]: https://github.com/gf3/secretary
[rlwrap]: https://github.com/hanslub42/rlwrap
[react-devtools]: https://github.com/facebook/react-devtools
[cljs-devtools]: https://github.com/binaryage/cljs-devtools

