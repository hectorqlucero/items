(ns sk.routes
  (:require [compojure.core :refer [defroutes GET POST]]
            [cheshire.core :refer [generate-string]]
            [sk.handlers.home.handler :as home]
            [sk.handlers.registrar.handler :as registrar]
            [sk.handlers.tref.handler :as table_ref]
            [sk.handlers.emergencies.handler :as emergencies]
            [sk.handlers.wishlist.handler :as wishlist]
            [sk.handlers.items.handler :as items]))

(defroutes open-routes
  ;; Start table_ref
  (GET "/table_ref/get_users" [] (generate-string (table_ref/get-users)))
  (GET "/table_ref/validate_email/:email" [email] (generate-string (table_ref/get-users-email email)))
  (GET "/table_ref/months" [] (generate-string (table_ref/months)))
  (GET "/table_ref/years/:pyears/:nyears" [pyears nyears] (generate-string (table_ref/years pyears nyears)))
  (GET "/table_ref/levels" [] (generate-string (table_ref/level-options)))
  (GET "/table_ref/get-levels" [] (generate-string (table_ref/get-levels)))
  (GET "/table_ref/get-category" [] (generate-string (table_ref/get-category)))
  ;; End table_ref

  ;; Start home
  (GET "/" request [] (home/main request))
  (GET "/home/login" request [] (home/login request))
  (POST "/home/login" [username password] (home/login! username password))
  (GET "/home/logoff" [] (home/logoff))
  ;; End home

  ;; Start registrar
  (GET "/register" request [] (registrar/registrar request))
  (POST "/register" request [] (registrar/registrar! request))
  (GET "/rpaswd" request [] (registrar/reset-password request))
  (POST "/rpaswd" request [] (registrar/reset-password! request))
  (GET "/reset_password/:token" [token] (registrar/reset-jwt token))
  (POST "/reset_password" request [] (registrar/reset-jwt! request))
  ;; End registrar

  ;; Start emergencies
  (GET "/emergencies" request [] (emergencies/emergencies request))
  (GET "/emergencies/pending" request [] (emergencies/pending-emergencies request))
  (GET "/emergencies/completed" request [] (emergencies/completed-emergencies request))
  ;; End emergencies

  ;; Start wishlist
  (GET "/wishlist" request [] (wishlist/wishlist request))
  (GET "/wishlist/pending" request [] (wishlist/pending-wishlist request))
  (GET "/wishlist/completed" request [] (wishlist/completed-wishlist request))
  ;; End wishlist

  ;; Start items
  (GET "/items" request [] (items/items request))
  (GET "/items/pending" request [] (items/pending-items request))
  (GET "/items/completed" request [] (items/completed-items request))
  ;; End items
  )
