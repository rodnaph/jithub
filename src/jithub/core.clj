
(ns jithub.core)

(def ^:dynamic *repo* nil)

(defmacro with-repo [repo & body]
    "Binds the repo for subsequent API calls"
    `(binding [*repo* ~repo]
        ~@body))

