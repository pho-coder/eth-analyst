(ns rocks.pho.eth.analyst.analysis
  (:require [clojure.tools.logging :as log]
            [mount.core :as mount]
            [clojure.data.json :as json]

            [rocks.pho.eth.analyst.utils :as utils]))

(mount/defstate raw-data-queue :start (java.util.concurrent.LinkedBlockingQueue. 10000))

(defn analysis-some [start-time end-time]
  (loop [one (.poll raw-data-queue 10 (java.util.concurrent.TimeUnit/SECONDS))]
    (if (nil? one)
      (log/info "NO Got! finish!")
      (let [one (json/read-str one
                               :key-fn keyword)]
        (log/info (:ch one))
        (recur (.poll raw-data-queue 10 (java.util.concurrent.TimeUnit/SECONDS)))))))

(mount/defstate analysis-task :start (future (analysis-some 1 2)))
