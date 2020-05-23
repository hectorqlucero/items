(ns sk.handlers.admin.emergencies.handler
  (:require [sk.models.crud :refer [build-form-row
                                    build-form-save
                                    build-form-delete]]
            [sk.models.grid :refer [build-grid]]
            [sk.models.util :refer [get-session-id
                                    user-email
                                    user-level]]
            [sk.layout :refer [application]]
            [sk.handlers.admin.emergencies.view :refer [emergencies-view emergencies-scripts]]))

(defn emergencies
  [_]
  (try
    (let [title "Emergencies Maintenance"
          ok (get-session-id)
          js (emergencies-scripts)
          content (emergencies-view title)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn emergencies-grid
  [{params :params}]
  (try
    (let [table "emergencies"
          args {:sort-extra "sdate,description"}]
      (build-grid params table args))
    (catch Exception e (.getMessage e))))

(defn emergencies-form
  [id]
  (try
    (let [table "emergencies"]
      (build-form-row table id))
    (catch Exception e (.getMessage e))))

(defn emergencies-save
  [{params :params}]
  (try
    (let [table "emergencies"]
      (build-form-save params table))
    (catch Exception e (.getMessage e))))

(defn emergencies-delete
  [{params :params}]
  (try
    (let [table "emergencies"]
      (build-form-delete params table))
    (catch Exception e (.getMessage e))))
