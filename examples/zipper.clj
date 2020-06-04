#!/usr/bin/env bb

(require '[babashka.pods :as pods])
(pods/load-pod "pod-babashka-parcera")
(require '[pod.babashka.parcera :as parcera])

(def parsed (let [expr (first *command-line-args*)]
              (parcera/ast expr)))

(require '[clojure.zip :as zip])

(def zloc
  (zip/zipper (fn [obj]
                (and (seq? obj)
                     (some-> obj second first keyword?)))
              rest
              (fn [node children]
                (list* (first node) children))
              (second parsed)))

(loop [zloc zloc]
  (when-not (zip/end? zloc)
    (let [node (zip/node zloc)]
      (when-not (= :whitespace (first node))
        (prn node)
        (prn (parcera/code node))))
    (recur (zip/next zloc))))
