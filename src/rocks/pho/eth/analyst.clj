(ns rocks.pho.eth.analyst
  (:gen-class)
  (:require [mount.core :as mount]
            [clojure.tools.logging :as log]
            [clojure.tools.cli :refer [parse-opts]]

            [rocks.pho.eth.analyst.config :refer [env]]
            [rocks.pho.eth.analyst.utils :as utils]
            [rocks.pho.eth.analyst.analysis :as analysis]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
