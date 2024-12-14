(ns dev
  (:require [com.stuartsierra.component.repl :as component-repl]
            [app.system :as system]))

(component-repl/set-init
 (fn [_old-system]
   (system/new-system {:service-map {:ws-port 6666}})))

(comment
  (component-repl/reset))

