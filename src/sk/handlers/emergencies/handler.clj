(ns sk.handlers.emergencies.handler
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.emergencies.view :refer [emergencies-view
                                                  emergencies-scripts]]))

(def head-sql
  "
  SELECT
  emergencies.id as id,
  emergencies.description as description,
  DATE_FORMAT(emergencies.sdate,'%m/%d/%Y') AS sdate,
  DATE_FORMAT(emergencies.edate,'%m/%d/%Y') AS edate,
  CONCAT('$', format(emergencies.cost, 2)) as cost,
  category.description as category_id,
  levels.description as levels_id
  FROM emergencies
  JOIN category on category.id = emergencies.category_id
  JOIN levels on levels.id = emergencies.levels_id
  ")

(defn emergencies-sql
  [& args]
  (let [head head-sql
        body (if (nil? args)
               nil
               (str " WHERE emergencies.edate " (first args)))
        foot " ORDER BY emergencies.sdate,emergencies.description"]
    (str head body foot)))

(defn emergencies
  [request]
  (try
    (let [title "Emergencies"
          ok (get-session-id)
          js (emergencies-scripts)
          rows (Query db (emergencies-sql))
          content (emergencies-view title rows 0)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn pending-emergencies
  [request]
  (try
    (let [title "Emergencies"
          ok (get-session-id)
          js (emergencies-scripts)
          rows (Query db (emergencies-sql "IS NULL"))
          content (emergencies-view title rows 1)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn completed-emergencies
  [request]
  (try
    (let [title "Emergencies"
          ok (get-session-id)
          js (emergencies-scripts)
          rows (Query db (emergencies-sql "IS NOT NULL"))
          content (emergencies-view title rows 2)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))
