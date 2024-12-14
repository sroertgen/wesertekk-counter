(ns app.system
  (:require [com.stuartsierra.component :as component]
            [io.pedestal.http :as http]
            [app.config :as config]
            [app.pedestal :as  pedestal]
            [app.routes :as routes])
  (:gen-class))

(defn new-system
  [config]
  (component/system-map
   :service-map
   {::http/routes (routes/routes (:service-map config))
    ::http/resource-path "/public" ;; This serves files from `resources/public`
    ::http/type :jetty
    ::http/port 8890
    ::http/host "0.0.0.0"
    ::http/secure-headers {:content-security-policy-settings {:object-src "none"}} ;; necessary to load tailwindcss
    ::http/join? false}

   :pedestal
   (component/using (pedestal/new-pedestal config) [:service-map])))

(defn -main
  []
  (let [system (-> (config/read-config)
                   (new-system)
                   (component/start-system))]
    (.addShutdownHook
     (Runtime/getRuntime)
     (new Thread #(component/stop-system system)))))

(defn main
  []
  (let [system (-> (config/read-config)
                   (new-system)
                   (component/start-system))]
    (.addShutdownHook
     (Runtime/getRuntime)
     (new Thread #(component/stop-system system)))))
