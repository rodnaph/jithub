
(ns jithub.test.core
    (:use [jithub.core])
    (:use [clojure.test]))

(defapi needs-repo "" [] true)

(defn- has-repo [] (with-repo {} (needs-repo)))
(defn- no-repo [] (needs-repo))

(deftest test-exception-when-repo-not-bound
    (is (has-repo))
    (is (thrown? Exception (no-repo))))

