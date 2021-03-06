(defproject url-shortener "0.1.0-SNAPSHOT"

  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :plugins [[lein-ring "0.8.13"]]

  :dependencies [
                 [org.clojure/clojure "1.6.0"]
                 [ring "1.3.2"]
                 [compojure "1.3.0"]
                 [enlive "1.1.5"]]

  :profiles {:dev {
                    :source-paths ["dev"]
                    :dependencies [
                                   ;[watchtower "0.1.1"]
                                   [hawk "0.1.1"]
                                   [org.clojure/tools.nrepl "0.2.6"]]
                    :ring {
                           :handler url-shortener.core/app
                           :init utils/init}
                    }
              }

  :ring {:handler url-shortener.core/app})


