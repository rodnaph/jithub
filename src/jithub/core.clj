
(ns jithub.core)

(def ^:dynamic *repo* nil)

(defmacro with-repo [repo & body]
    "Binds the repo for subsequent API calls"
    `(binding [*repo* ~repo]
        ~@body))

(defmacro defapi [name comment args & body]
    "Defines an API function that required *repo* to be bound"
    `(defn ~name
        [~@args]
        (if (nil? *repo*)
            (throw (Exception. "*repo* needs to be bound"))
            (do ~@body))))

(defn apiurl
    "Returns the URL to the API"
    []
    "https://api.github.com")

(defn basic-auth
    "Helper for creating map with basic-auth data"
    ([] (basic-auth {}))
    ([data] (merge {:basic-auth [(:user *repo*) (:password *repo*)]} 
                   data)))

