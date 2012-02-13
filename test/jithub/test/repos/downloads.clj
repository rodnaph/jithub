
(ns jithub.test.repos.downloads
    (:use jithub.core
          jithub.util
          jithub.repos.downloads
          clj-http.fake
          clojure.test)
    (:require [clj-http.client :as http]))

(def test-routes {:user "foo" :name "bar"})

;(deftest all-downloads-can-be-fetched
;    (with-fake-routes
;        {"https://api.github.com/repos/foo/bar/downloads" (fn [req] {:status 200 :headers {} :body "[]"})}
;        (with-repo test-routes
;            (is (= [] (all))))))

(with-fake-routes
  ; also supports regexps
  {#".*" (fn [req] {:status 200 :headers {} :body "HACKED LOL HAHA"})}
    (with-repo test-routes
            (is (= [] (all)))))

