# pod-babashka-parcera

[Babashka](https://github.com/borkdude/babashka)
[pod](https://github.com/babashka/babashka.pods) wrapping
[parcera](https://github.com/carocad/parcera), a Clojure parser based on
Antlr4.

This is work in progress.

## Install

The following installation methods are available:

- Download a binary from Github releases
<!-- - With [brew](https://brew.sh/): `brew install borkdude/brew/pod-babashka-parcera` -->

## Compatibility

This pod requires babashka v0.0.96 or later.

## Run

``` clojure
(require '[babashka.pods :as pods])
(pods/load-pod "pod-babashka-parcera")
(require '[pod.babashka.parcera :as parcera])

(parcera/ast "(ns foo)")
;;=> (:code (:list (:symbol "ns") (:whitespace " ") (:symbol "foo")))

(parcera/code (parcera/ast "(ns foo)"))
;;=> "(ns foo)"
```

A minimal namespace require sorting demo:

``` clojure
(require '[clojure.string :as str])

(def parsed (parcera/ast (str/triml "
(ns foo
  (:require b c a))")))

;; we're not using postwalk since it doesn't preserve metadata
(defn transform-node [node]
  (if (seq? node)
    (if (and (= :list (first node))
             (= '(:keyword ":require") (second node)))
      (let [children (nnext node)
            first-whitespace (some #(when (= :whitespace (first %)) %) children)
            children (remove #(= :whitespace (first %)) children)
            loc (meta (first children))
            start (-> loc :parcera.core/start :column)
            whitespace (str (apply str "\n" (repeat start " ")))
            children (sort-by str children)
            children (butlast (interleave children (repeat (list :whitespace whitespace))))]
        (cons :list (cons '(:keyword ":require") (cons first-whitespace children))))
      (cons (first node) (map transform-node (rest node))))
    node))

(def processed (transform-node parsed))

(println (parcera/code processed))
;; =>
;; (ns foo
;;   (:require a
;;             b
;;             c))
```

## Dev

### Build

Run `script/compile`

### Test

Run `script/test`.

## License

Copyright © 2020 Michiel Borkent

Distributed under the EPL License. See LICENSE.

Parcera is licensed under the [LGPL-3.0](https://github.com/carocad/parcera/blob/master/LICENSE.md).
