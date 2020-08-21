(ns sk.models.cdb
  (:require [sk.models.crud :refer [db
                                    Query!
                                    Insert-multi]]
            [noir.util.crypt :as crypt]))


;; Start users table
(def users-sql
  "CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  lastname varchar(45) DEFAULT NULL,
  firstname varchar(45) DEFAULT NULL,
  username varchar(45) DEFAULT NULL,
  password TEXT DEFAULT NULL,
  dob date  DEFAULT NULL,
  cell varchar(45) DEFAULT NULL,
  phone varchar(45) DEFAULT NULL,fax varchar(45) DEFAULT NULL,
  email varchar(100) DEFAULT NULL,
  level char(1) DEFAULT NULL COMMENT 'A=Administrator,U=User,S=System',
  active char(1) DEFAULT NULL COMMENT 'T=Active,F=Not active'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8")

(def users-rows
  [{:lastname  "User"
    :firstname "Regular"
    :username  "user@gmail.com"
    :password  (crypt/encrypt "user")
    :dob       "1957-02-07"
    :email     "user@gmail.com"
    :level     "U"
    :active    "T"}
   {:lastname "User"
    :firstname "Admin"
    :username "admin@gmail.com"
    :password (crypt/encrypt "admin")
    :dob "1957-02-07"
    :email "admin@gmail.com"
    :level "S"
    :active "T"}])
;; End users table

;; Start levels table
(def levels-sql
  "
  CREATE TABLE levels (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  description varchar(45) DEFAULT NULL,
  weight int(11) DEFAULT NULL COMMENT '1,2,3,4,5,6,7,8,9,10'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8
  ")

(def levels-rows
  [{:description "Level 1"
    :weight 1}
   {:description "Level 2"
    :weight 2}
   {:description "Level 3"
    :weight 3}
   {:description "Level 4"
    :weight 4}
   {:description "Level 5"
    :weight 5}
   {:description "Level 6"
    :weight 6}
   {:description "Level 7"
    :weight 7}
   {:description "Level 8"
    :weight 8}
   {:description "Level 9"
    :weight 9}
   {:description "Level 10"
    :weight 10}])
;; End leverls table

;; Start category
(def category-sql
  "
  CREATE TABLE category (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  description varchar(100) DEFAULT NULL,
  active char(1) DEFAULT NULL COMMENT 'T=Active,F=Not active'
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8
  ")

(def category-rows
  [{:description "Health"
    :active "T"}
   {:description "Home"
    :active "T"}
   {:description "Transportation"
    :active "T"}
   {:description "Vacations"
    :active "T"}
   {:description "Sport"
    :active "T"}
   {:description "Savings"
    :active "T"}
   {:description "Crafts"
    :active "T"}
   {:description "Tools"
    :active "T"}
   {:description "Electronics"
    :active "T"}
   {:description "Miscelaneous"
    :active "T"}])
;; End category

;; Start wishlist
(def wishlist-sql
  "
  CREATE TABLE wishlist (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  description TEXT DEFAULT NULL,
  sdate DATE DEFAULT NULL,
  edate DATE DEFAULT NULL,
  cost decimal(15,2) DEFAULT NULL,
  category_id int(11) NOT NULL,
  levels_id int(11) NOT NULL
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8
  ")
;; End wishlist

;; Start emergencies
(def emergencies-sql
  "
  CREATE TABLE emergencies (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  description TEXT DEFAULT NULL,
  sdate DATE DEFAULT NULL,
  edate DATE DEFAULT NULL,
  cost decimal(15,2) DEFAULT NULL,
  category_id int(11) NOT NULL,
  levels_id int(11) NOT NULL
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8
  ")
;; End emergencies

;; Start items
(def items-sql
  "
  CREATE TABLE items (
  id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  description TEXT DEFAULT NULL,
  sdate DATE DEFAULT NULL,
  edate DATE DEFAULT NULL,
  cost decimal(15,2) DEFAULT NULL,
  category_id int(11) NOT NULL,
  levels_id int(11) NOT NULL
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8
  ")
;; End items

(defn create-database
  "Create database tables and default admin users
  Note: First create the database on MySQL with any client"
  []
  (Query! db users-sql)
  (Query! db levels-sql)
  (Query! db category-sql)
  (Query! db wishlist-sql)
  (Query! db emergencies-sql)
  (Query! db items-sql))

(defn reset-database
  "Removes existing tables and re-creates them"
  []
  (Query! db "DROP table IF EXISTS users")
  (Query! db "DROP table IF EXISTS levels")
  (Query! db "DROP table IF EXISTS category")
  (Query! db "DROP table IF EXISTS wishlist")
  (Query! db "DROP table IF EXISTS emergencies")
  (Query! db "DROP table IF EXISTS items")
  (Query! db users-sql)
  (Query! db levels-sql)
  (Query! db category-sql)
  (Query! db wishlist-sql)
  (Query! db emergencies-sql)
  (Query! db items-sql))

(defn migrate
  "Migrate by the seat of my pants"
  []
  (Query! db "DROP table IF EXISTS levels")
  (Query! db "DROP table IF EXISTS category")
  (Query! db levels-sql)
  (Query! db category-sql)
  (Query! db "LOCK TABLES levels WRITE;")
  (Insert-multi db :levels levels-rows)
  (Query! db "LOCK TABLES category WRITE;")
  (Insert-multi db :category category-rows)
  (Query! db "UNLOCK TABLES;"))
