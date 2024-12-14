(ns app.pedestal
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]))

(defrecord Pedestal [config
                     service-map]
  component/Lifecycle

  (start [component]
    (println ";; Starting Pedestal Component")
    (let [server (-> (http/create-server  service-map)
                     (http/start))]
      (assoc component :service server)))

  (stop [component]
    (println ";; Stoppiing Pedestal Component")
    (when-let [service (:service component)]
      (http/stop service))
    (assoc component :service nil)))

(defn new-pedestal
  [config]
  (map->Pedestal {:config config}))
