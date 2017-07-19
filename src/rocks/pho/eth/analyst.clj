(ns rocks.pho.eth.analyst
  (:gen-class)
  (:require [mount.core :as mount]
            [clojure.tools.logging :as log]
            [clojure.tools.cli :refer [parse-opts]]

            [rocks.pho.eth.analyst.config :refer [env]]
            [rocks.pho.eth.analyst.utils :as utils]
            [rocks.pho.eth.analyst.analysis :as analysis]))

(def cli-options
  [["-f" "--file file" "raw file"]
   ["-h" "--help"]])

(defn usage [options-summary]
  (->> ["This is my program. There are many like it, but this one is mine."
        ""
        "Usage: program-name [options] action"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  start      Start one analyst"
        ""
        "Please refer to the manual page for more information."]
       (clojure.string/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (clojure.string/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn stop-app []
  (doseq [component (:stopped (mount/stop))]
    (log/info component "stopped"))
  (shutdown-agents))

(defn start-app
  [raw-file]
  (log/info "start")
  (log/info "raw file:" raw-file)
  (utils/read-raw-data raw-file analysis/raw-data-queue))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    ;; Handle help and error conditions
    (log/info options)
    (cond
      (:help options) (exit 0 (usage summary))
      (not= (count arguments) 1) (exit 1 (usage summary))
      errors (exit 1 (error-msg errors)))
    
    (.addShutdownHook (Runtime/getRuntime) (Thread. stop-app))
    (doseq [component (-> args
                          mount/start-with-args
                          :started)]
      (log/info component "started"))
    
    ;; Execute program with options
    (let [raw-file (:file options)]
      (case (first arguments)
        "start" (start-app raw-file)
        (exit 1 (usage summary))))))
