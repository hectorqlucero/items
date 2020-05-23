(ns sk.handlers.admin.levels.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id
                                    user-email
                                    user-level]]
            [sk.layout :refer [application]]
            [sk.handlers.admin.levels.view :refer [levels-view levels-scripts]]))

(defn levels
  [_]
  (try
    (let [title "Levels Maintenance"
          ok (get-session-id)
          js (levels-scripts)
          content (levels-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn levels-grid
  [{params :params}]
  (try
    (let [table "levels"
          args {:sort-extra "weight"}]
      (build-grid params table args))
    (catch Exception e (.getMessage e))))

(defn levels-form
  [id]
  (try
    (let [table "levels"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn levels-save
  [{params :params}]
  (try
    (let [table "levels"]
      (build-form-save params table))
    (catch Exception e (.getMessage e))))

(defn levels-delete
  [{params :params}]
  (try
    (let [table "levels"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
