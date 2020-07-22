(defproject babashka/pod-babashka-parcera
  #=(clojure.string/trim
     #=(slurp "resources/POD_VERSION"))
  :description "babashka parcera pod"
  :url "https://github.com/borkdude/pod-babashka-parcera"
  :scm {:name "git"
        :url "https://github.com/borkdude/pod-babashka-parcera"}
  :license {:name "Eclipse Public License 1.0"
            :url "http://opensource.org/licenses/eclipse-1.0.php"}
  :source-paths ["src"]
  :resource-paths ["resources"]
  :dependencies [[org.clojure/clojure "1.10.2-alpha1"]
                 [carocad/parcera "0.11.1"]
                 [org.antlr/antlr4-runtime "4.7.1"]
                 [nrepl/bencode "1.1.0"]]
  :profiles {:uberjar {:global-vars {*assert* false}
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"
                                  "-Dclojure.spec.skip-macros=true"]
                       :aot :all
                       :main pod.babashka.parcera}}
  :deploy-repositories [["clojars" {:url "https://clojars.org/repo"
                                    :username :env/clojars_user
                                    :password :env/clojars_pass
                                    :sign-releases false}]])
