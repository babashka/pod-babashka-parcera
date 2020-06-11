#!/usr/bin/env bb

(def file (first *command-line-args*))

(require '[babashka.pods :as pods])
(pods/load-pod "clj-kondo")
(require '[pod.borkdude.clj-kondo :as clj-kondo])
(def findings (let [{:keys [:findings]} (clj-kondo/run! {:lint [file]})]
                (filter #(= :unused-import (:type %)) findings)))
(def locations (set (map (fn [{:keys [:row :col]}]
                           {:row row
                            :column (dec col)}) findings)))

(pods/load-pod "pod-babashka-parcera")
(require '[pod.babashka.parcera :as parcera])

(def parsed (let [code (slurp (first *command-line-args*))]
              (parcera/ast code)))

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
  (if (zip/end? zloc)
    (parcera/code (zip/root zloc))
    (let [node (zip/node zloc)]
      (if-not (= :whitespace (first node))
        (if (contains? locations (:parcera.core/start (meta node)))
          ;; TODO: also remove entire import if parent is left with only one child!
          (recur (zip/remove zloc))
          (recur (zip/next zloc)))
        (recur (zip/next zloc))))))
