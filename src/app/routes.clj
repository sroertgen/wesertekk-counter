(ns app.routes
  (:require [cheshire.core :as json]
            [clojure.java.io :as io]))

(def counter-atom (atom 0))
(def went-atom (atom 0))

(defn current [] (- @counter-atom @went-atom))

(defn resp []
  (json/generate-string  {:counter (current)
                          :total @counter-atom}))

(defn home [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (slurp (io/resource "public/index.html"))})

(defn counter [request]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body ( resp )})

(defn increment [request]
  (swap! counter-atom inc)
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (resp)})

(defn decrement [request]
  (swap! went-atom inc)
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (resp)})

(defn reset [request]
  (reset! counter-atom 0)
  (reset! went-atom 0)
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (resp)})

(defn routes [config]
  #{["/" :get home :route-name :home]
    ["/counter" :get counter :route-name :counter]
    ["/increment" :post increment :route-name :increment]
    ["/decrement" :post decrement :route-name :decrement]
    ["/reset" :post reset :route-name :reset]})


