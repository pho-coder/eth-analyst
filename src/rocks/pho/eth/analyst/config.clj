(ns rocks.pho.eth.analyst.config
  (:require [mount.core :refer [defstate]]
            [cprop.source :as source]))

(defstate env :start (source/from-env))
