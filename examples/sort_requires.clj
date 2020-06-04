#!/usr/bin/env bb

(require '[babashka.pods :as pods])
(pods/load-pod "pod-babashka-parcera")
(require '[pod.babashka.parcera :as parcera])

(def parsed (parcera/ast (slurp *in*)))

;; we're not using postwalk since it doesn't preserve metadata
(defn transform-node [node]
  (if (seq? node)
    (if (and (= :list (first node))
             (= '(:keyword ":require") (second node)))
      (let [children (nnext node)
            first-whitespace (some #(when (= :whitespace (first %)) %) children)
            ;; _ (prn first-whitespace)
            children (remove #(= :whitespace (first %)) children)
            loc (meta (first children))
            start (-> loc :parcera.core/start :column)
            whitespace (str (apply str "\n" (repeat start " ")))
            children (sort-by (comp str parcera/code) children)
            children (butlast (interleave children (repeat (list :whitespace whitespace))))]
        (cons :list (cons '(:keyword ":require") (cons first-whitespace children))))
      (cons (first node) (map transform-node (rest node))))
    node))

(def processed (transform-node parsed))

(println (parcera/code processed))
