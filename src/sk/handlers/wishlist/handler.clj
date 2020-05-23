(ns sk.handlers.wishlist.handler
  (:require [sk.models.crud :refer [db Query]]
            [sk.models.util :refer [get-session-id]]
            [sk.layout :refer [application]]
            [sk.handlers.wishlist.view :refer [wishlist-view
                                                  wishlist-scripts]]))

(def head-sql
  "
  SELECT
  wishlist.id as id,
  wishlist.description as description,
  DATE_FORMAT(wishlist.sdate,'%m/%d/%Y') AS sdate,
  DATE_FORMAT(wishlist.edate,'%m/%d/%Y') AS edate,
  CONCAT('$', format(wishlist.cost, 2)) as cost,
  category.description as category_id,
  levels.description as levels_id
  FROM wishlist
  JOIN category on category.id = wishlist.category_id
  JOIN levels on levels.id = wishlist.levels_id
  ")

(defn wishlist-sql
  [& args]
  (let [head head-sql
        body (if (nil? args)
               nil
               (str " WHERE wishlist.edate " (first args)))
        foot " ORDER BY wishlist.sdate,wishlist.description"]
    (str head body foot)))

(defn wishlist
  [request]
  (try
    (let [title "Wishlist"
          ok (get-session-id)
          js (wishlist-scripts)
          rows (Query db (wishlist-sql))
          content (wishlist-view title rows 0)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn pending-wishlist
  [request]
  (try
    (let [title "Wishlist"
          ok (get-session-id)
          js (wishlist-scripts)
          rows (Query db (wishlist-sql "IS NULL"))
          content (wishlist-view title rows 1)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))

(defn completed-wishlist
  [request]
  (try
    (let [title "Wishlist"
          ok (get-session-id)
          js (wishlist-scripts)
          rows (Query db (wishlist-sql "IS NOT NULL"))
          content (wishlist-view title rows 2)]
      (application title ok js content))
    (catch Exception e (.getMessage e))))
