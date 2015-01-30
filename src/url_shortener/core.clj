(ns url-shortener.core
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer [GET POST defroutes]]
            [net.cgrand.enlive-html :as enlive]))

(enlive/deftemplate tmpl-index "templates/index.html" [])

(def root-url "http://localhost:3000/")

(def key-length 7)

;The map which holds the keys, and the URLs they resolve to
(defonce link-map (atom {}))

(defn put-url
  "Puts the URL and key into the link map"
  [url short-key]
  (swap! link-map assoc short-key url))

(defn md5
  "Generate a md5 checksum for the given string"
  [token]
  (let [hash-bytes
        (doto (java.security.MessageDigest/getInstance "MD5")
          (.reset)
          (.update (.getBytes token)))]
    (.toString
     (new java.math.BigInteger 1 (.digest hash-bytes)) ; Positive and the size of the number
     16))); Use base16 i.e. hex

(defn generate-key [url]
  (take key-length (md5 url)))

(defroutes my-app-routes
  (GET "/" []
       {:status 200
        :headers {"Content-Type" "text/html"}
        :body (apply str (tmpl-index))})
  (GET "/shorten" []
       {:status 501
        :headers {"Content-Type" "text/html"}
        :body "<h1>Not Implemented Yet</h1>"})
  (POST "/shorten" [url]
        (let
          [short-key (generate-key url)]
          (put-url url short-key)
          {:status 200
           :headers {"Content-Type" "text/html"}
           :body (apply str root-url short-key)})
        )
  (route/not-found "Page not found"))

(def app (handler/site my-app-routes))
