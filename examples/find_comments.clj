#!/usr/bin/env bb

(require '[babashka.pods :as pods])
(pods/load-pod "pod-babashka-parcera")
(require '[pod.babashka.parcera :as parcera])

(doseq [file *command-line-args*
        :let [parsed (parcera/ast (slurp file))]
        node (rest parsed)]
  (when (and (= :list (first node))
             (= '(:symbol "comment") (second node)))
    (let [loc (-> (meta node) :parcera.core/start)]
      (println (str file ":" (:row loc) ":" (:col loc)))
      (println (parcera/code node))
      (println))))
