
(ns jithub.test.repos.downloads
    (:use jithub.core
          jithub.repos.downloads
          clj-http.fake
          clojure.test))

(def r {:user "foo" :name "bar"})

(deftest api-url-includes-repo-parts
    (is (.contains (with-repo r (dlurl)) "foo"))
    (is (.contains (with-repo r (dlurl)) "bar")))

;(deftest all-downloads-can-be-fetched
;    (with-fake-routes
;        {"https://api.github.com/repos/foo/bar/downloads" (fn [req] {:status 200 :headers {} :body "[]"})}
;        (with-repo r
;            (is (= [] (all))))))

