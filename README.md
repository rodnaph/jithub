
# Jithub

Jithub is a simple Clojure library for interacting with the Github API.

## Examples

```clojure
(:use jithub.core)
(:require [jithub.repos.downloads :as dl])

(def repo { :user "foo"
            :name "repo_name"
            :password "secret" })

; Wrap with repo to access
(with-repo repo

    ; List all downloads for a repo
    (dl/all)

    ; Fetch a specific download by ID
    (dl/show 12345)

    ; Create a new download
    (dl/create "/path/to/file" "name" "description")

    ; Delete a download
    (dl/delete 12345)

)
```

## Current Scope

This code was extracted from part of another project as it might be useful, but this
is not a complete API implementation.  Only downloads so far :)

