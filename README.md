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

## Examples

For the source, see the [examples](examples) directory.

### Sort requires

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

### Find comments

``` clojure
$ find /code/app | grep -e "\.clj$" | xargs examples/find_comments.clj | head
/code/app/test/dre/yada/error_renderers_test.clj:87:
(comment
  (schema-validation-error-test)
  (errors-test)
  (t/run-tests))

/code/app/test/dre/yada/util_test.clj:13:
(comment
  (t/run-tests))
```

### Zipper

Walk all the nodes and print them, except when it's a whitespace node.

``` clojure
$ examples/zipper.clj "^:foo ^:bar {:a 1}"
"^:foo ^:bar {:a 1}"
"^:foo"
":foo"
"^:bar"
":bar"
"{:a 1}"
":a"
"1"
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
