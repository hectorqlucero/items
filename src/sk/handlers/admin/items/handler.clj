(ns sk.handlers.admin.items.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.admin.items.view :refer [items-view items-scripts]]))

(defn items
  [_]
  (try
    (let [title "Items Maintenance"
          ok (get-session-id)
          js (items-scripts)
          content (items-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn items-grid
  [{params :params}]
  (try
    (let [table "items"
          args {:sort-extra "sdate,description"}]
      (build-grid params table args))
    (catch Exception e (.getMessage e))))

(defn items-form
  [id]
  (try
    (let [table "items"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn items-save
  [{params :params}]
  (try
    (let [table "items"]
      (build-form-save params table))
    (catch Exception e (.getMessage e))))

(defn items-delete
  [{params :params}]
  (try
    (let [table "items"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
