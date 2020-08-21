(ns sk.handlers.items.handler
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.items.view :refer [items-view items-scripts]]))

(def head-sql
  "
  SELECT
  items.id as id,
  items.description as description,
  DATE_FORMAT(items.sdate,'%m/%d/%Y') AS sdate,
  DATE_FORMAT(items.edate,'%m/%d/%Y') AS edate,
  CONCAT('$', format(items.cost, 2)) as cost,
  category.description as category_id,
  levels.description as levels_id
  FROM items
  JOIN category on category.id = items.category_id
  JOIN levels on levels.id = items.levels_id
  ")

(defn items-sql
  [& args]
  (let [head head-sql
        body (if (nil? args)
               nil
               (str " WHERE items.edate " (first args)))
        foot " ORDER BY items.sdate,items.description"]
    (str head body foot)))

(defn items
  [_]
  (try
    (let [title "Items"
          ok (get-session-id)
          js (items-scripts)
          rows (Query db (items-sql))
          content (items-view title rows 0)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn pending-items
  [_]
  (try
    (let [title "Items"
          ok (get-session-id)
          js (items-scripts)
          rows (Query db (items-sql "IS NULL"))
          content (items-view title rows 1)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn completed-items
  [_]
  (try
    (let [title "Items"
          ok (get-session-id)
          js (items-scripts)
          rows (Query db (items-sql "IS NOT NULL"))
          content (items-view title rows 2)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))
