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

A minimal and probably incomplete namespace require sorting demo can be found in
`examples`.

``` clojure
$ cat foo.clj
(ns foo
  (:require
   [z.foo :as z]
   [a.foo :as a]))

(defn foo [])

$ cat foo.clj | examples/sort_requires.clj
(ns foo
  (:require
   [a.foo :as a]
   [z.foo :as z]))

(defn foo [])
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
