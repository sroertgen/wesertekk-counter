(ns app.routes
  (:require [cheshire.core :as json]))

(def counter-atom (atom 0))

(defn home [request]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (slurp "resources/public/index.html")})

(defn counter [request]
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string  {:counter @counter-atom})})

(defn increment [request]
  (swap! counter-atom inc)
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string  {:counter @counter-atom})})

(defn decrement [request]
  (swap! counter-atom dec)
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string  {:counter @counter-atom})})

(defn reset [request]
  (reset! counter-atom 0)
  {:status 200
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string  {:counter @counter-atom})})

(defn routes [config]
  #{["/" :get home :route-name :home]
    ["/counter" :get counter :route-name :counter]
    ["/increment" :post increment :route-name :increment]
    ["/decrement" :post decrement :route-name :decrement]
    ["/reset" :post reset :route-name :reset]})


