(ns pod.babashka.parcera-test
  (:require [clojure.test :refer [deftest is]]
            [babashka.pods :as pods]))

(if(= "native" (System/getenv "POD_TEST_ENV"))
  (do (pods/load-pod "./pod-babashka-parcera")
      (println "Testing native version"))
  (do (pods/load-pod ["lein" "run" "-m" "pod.babashka.parcera"])
      (println "Testing JVM version")))

(require '[pod.babashka.parcera :as parcera])

(deftest parcera-test
  (is (= "(ns foo)"
         (parcera/code (parcera/ast "(ns foo)")))))



