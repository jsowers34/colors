(defproject colors "0.1.0"
  :description "Colors: Utility tools for extraction of the classic Unix colors"
  :url "http://www.gethub.com/jsowers/colors"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"] [seesaw "1.5.0"]]
  :plugins [[lein-codox "0.10.8"]]
  :main ^:skip-aot colors.core
  :target-path "target/%s"
  :java-source-paths ["src/java"]
  :profiles {:uberjar {:aot :all}})
