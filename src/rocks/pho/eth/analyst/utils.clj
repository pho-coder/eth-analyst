(ns rocks.pho.eth.analyst.utils
  (:require [clojure.tools.logging :as log]))

(defn read-raw-data [raw-data-file queue]
  (with-open [rdr (clojure.java.io/reader raw-data-file)]
    (doseq [line (line-seq rdr)]
      (when (not (.offer queue line 60 (java.util.concurrent.TimeUnit/SECONDS)))
        (log/error "queue offer error!")))))
