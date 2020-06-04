#!/usr/bin/env bb

(require '[babashka.pods :as pods])
(pods/load-pod "pod-babashka-parcera")
(require '[pod.babashka.parcera :as parcera])

(doseq [file *command-line-args*]
  (let [parsed (parcera/ast (slurp file))]
    (loop [nodes (rest parsed)]
      (when-first [n nodes]
        (when (and (= :list (first n))
                   (= '(:symbol "comment") (second n)))
          (let [loc (-> (meta n) :parcera.core/start)]
            (println (str file ":" (:row loc) ":" (:col loc)))
            (println (parcera/code n))
            (println)))
        (recur (rest nodes))))))
