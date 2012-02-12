
(ns jithub.repos.downloads
    (:use jithub.core
          cheshire.core)
    (:require [clj-http.client :as http]
              [clojure.java.io :as io])
    (:import [java.io File]
             [java.net URLConnection]))

;; Create

(defn- dlurl
    "Returns the root downloads URL"
    []
    (format "%s/repos/%s/%s/downloads"
            (apiurl)
            (:user *repo*)
            (:name *repo*)))

(defn- create-github
    "Fetches upload request parameters, for saving to S3"
    [file title description]
    (http/post (dlurl)
        { :basic-auth [(:user *repo*) (:password *repo*)]
          :body (generate-string {
              :name title
              :size (.length file)
              :description description
              :content_type (URLConnection/guessContentTypeFromName (.getName file))
          })}))

(defn- create-s3
    "Upload a file to S3, with params from Github"
    [file params]
    (http/post (:s3_url params)
        { :multipart (array-map 
          :key (:path params)
          :acl (:acl params)
          :success_action_status "201"
          :Filename (:name params)
          :AWSAccessKeyId (:accesskeyid params)
          :Policy (:policy params)
          :Signature (:signature params)
          :Content-Type (:mime_type params)
          :file (slurp file))
        }))

;; Public

(defapi all
    "List downloads, returning data as a vector of maps"
    []
    (parse-stream (io/reader (dlurl)) true))

(defapi create
    "Upload a file to Github, this is a 2-stage process that requires
     first getting a token from Github, and then sending the file to S3.
     Returns S3 response as map"
    [path title description]
    (let [file (File. path)
          req (create-github file title description)
          params (parse-string (:body req) true)
          res (create-s3 file params)]
              (parse-string (:body res))))

(defapi show
    "Fetch a specific download, returning data as map"
    [id]
    (let [res (http/get (format "%s/%d" (dlurl) id))]
        (parse-string (:body res) true)))

(defapi delete
    "Delete a specific download, returning response as map"
    [id]
    (http/delete (format "%s/%d" (dlurl) id)
        { :basic-auth [(:user *repo*) (:password *repo*)] }))

