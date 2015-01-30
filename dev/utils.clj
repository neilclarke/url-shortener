(ns utils
  (:require 
    ;[watchtower.core :as watcher]
    [hawk.core :as hawk]
            [clojure.tools.nrepl.server :refer [start-server stop-server]]))

(defn- handle-template-file-change [files]
  (use 'url-shortener.core :reload))

;(defn init
;  "Reload this namespace and its templates when one of the templates changes."
;  []
;  (watcher/watcher
;   ["resources"]
;   (watcher/rate 50);50ms
;   (watcher/file-filter (watcher/extensions :html))
;   (watcher/on-change handle-template-file-change)))

(defn init
  "Reload this namespace and its templates when one of the templates changes."
  []
  (hawk/watch! 
    [{:paths ["resources"]
      :filter #(.endsWith ".html")
      :handler handle-template-file-change}]))

(defonce server
  (start-server :port 3001))
