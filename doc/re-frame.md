## The app-db

All runtime data in this app is stored in the app-db (which is just a map).

**IMPORTANT:** avoid putting JS objects in the app-db, they are mutable and not serializable.

### app-db structure

```clojure
{:ls      {                   ;; local-storage map, synced using interceptor
           :panel   :panel-id ;; current panel ID
           :user-id ""        ;; current user ID (or nil)
           :app-id  ""}       ;; current app-id (or nil)
 :modals  []                  ;; stack of modal IDs
 }
```

In addition, each panel / modal also have a key in the `app-db` that's the
same as the panel's ID storing the panel-specific data.


## Terminology

- `panel` defines a \"page\" in this app.
- `modal` defines an interactive layer on top of a panel. (can be stacked)
- `overlay` defines a non-interactive layer on top of a panel. (can be stacked)
- `component` defines a UI component used by panels or modals.


## Panels

Each `panel` has it's own namespace in the project under re-frame.
They should have atleast a view called `panel` defineing it's DOM structure
and a `:<panel>/initialize` event handler to set up the panel.

e.g. for the `project` panel, there exists:

- `shotbot.re-frame.project.views/panel`, a reagent component.
- a registered event called `:project/initialize`


## Routing

To add a new panel / modal / overlay, you need to update the views routing registry
in `shotbot.re-frame.views`.

To change panels use the `[:shotbot/switch-panel panel-id]` event.

To push/pop modals use the `[:shotbot/modal-push modal-id]` and `[:modal-pop]` events

When adding a new panel you'll also need to update the URL routing registry
in `shotbot.re-frame.utils`, add your subs namespace to `shotbot.re-frame.subs`
(if you need subs) and add your events namespace to `shotbot.re-frame.events`.

In addition, the `:shotbot/switch-panel` event will check the
`:<current-panel>/confirm-leave?` sub before leaving the current panel.
If this sub exists, it's expected to have a value of either `nil` or a string of
the message to be shown when the user is prompted to confirm the action.


## Registering Events

This project uses custum implementations of re-frame's event registry functions:
`(:require [shotbot.re-frame.utils :refer [reg-event-fx reg-event-db]])`

These functions have the same calling semantics as their original `re-frame`
conterparts. They have 3 interceptors pre-injected:

- `ga-tracking` google analytics tracking
- `trim-v` removes the first element in the argument vector - the event ID.
- [re-frame-storage][] Everything inside the `:ls` key in the `app-db` is
    persisted to local storage. It must be serializable.


## Custom FX

This project uses a few custum FXs.

- `:skygear` See library [homepage][re-frame-skygear].

------------------------------------------------------------------

- `:dispatch-promise`
    Dispatch an event on Promise resolution.
    Implemented in `shotbot.re-frame.utils`.

Usage:
```
:dispatch-promise [:event-id promise-obj & event-args]

(reg-event-db
  :event-id
  (fn [db [promise-result & event-args]]
    ... ))
```

------------------------------------------------------------------

- `:load-shots`
    Composes the specified shots and load all remote dependencies (i.e. images).
    Implemented in `shotbot.re-frame.utils`.

Usage:
```
:load-shots [{:template-id      ""
              :template-index   0
              :user-data        {}
              :success-dispatch [:shot-loaded shot-id]}
             {...}
             {...}]

;; The shot's render-data is added onto the end of the :success-dispatch vec
(reg-event-db
  :shot-loaded
  (fn [db [shot-id render-data]]
    ... ))
```

------------------------------------------------------------------

- `:save-blob`
    Save (Download) a blob object in the browser using [filesaver.js][]

Usage:
```
:save-blob [blob-object filename]
```

------------------------------------------------------------------

- `:ga`
    Calls the google analytics `ga` function. This fx takes a vec of calls.

Usage:
```
:ga [["set" "userId" user-id]
     ["send" "event" "auth" "login"]]
```

------------------------------------------------------------------

- `:enable-history`
    Enable goog.History events (only used once after initializing the app).

Usage:
```
:enable-history true
```

------------------------------------------------------------------

- `:to-route`
    Sets the URL fragment (used by routing events only, panels should use
    the `shotbot/switch-panel` event to navigate).

Usage:
```
:to-route [:edit app-id]

;; => app.html/#/edit/<app-id>
```


## Custom CoFX

- `:modernizr`
    Feature detection test results from [modernizr][]. This project uses a
    custom build of modernizr in `resources/public/js/modernizr-custom.js`,
    You can build a new one and replace that file to add additional tests.

Usage:
```
(get-in cofx [:modernizr :localstorage])
;; => true
```

[re-frame-storage]: https://github.com/akiroz/re-frame-storage
[re-frame-skygear]: https://github.com/akiroz/re-frame-skygear
[filesaver.js]: https://github.com/eligrey/FileSaver.js
[modernizr]: https://modernizr.com/ 
