(ns sk.handlers.admin.category.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.admin.category.view :refer [category-view category-scripts]]))

(defn category
  [_]
  (try
    (let [title "Category Maintenance"
          ok (get-session-id)
          js (category-scripts)
          content (category-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn category-grid
  [{params :params}]
  (try
    (let [table "category"
          args {:sort-extra "description"}]
      (build-grid params table args))
    (catch Exception e (.getMessage e))))

(defn category-form
  [id]
  (try
    (let [table "category"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn category-save
  [{params :params}]
  (try
    (let [table "category"]
      (build-form-save params table))
    (catch Exception e (.getMessage e))))

(defn category-delete
  [{params :params}]
  (try
    (let [table "category"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
