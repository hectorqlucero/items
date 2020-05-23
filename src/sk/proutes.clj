(ns sk.proutes
  (:require [compojure.core :refer [defroutes GET POST]]
            [sk.handlers.admin.users.handler :as users]
            [sk.handlers.admin.levels.handler :as levels]
            [sk.handlers.admin.category.handler :as category]
            [sk.handlers.admin.wishlist.handler :as wishlist]
            [sk.handlers.admin.emergencies.handler :as emergencies]
            [sk.handlers.admin.items.handler :as items]))

(defroutes proutes
  ;; Start users
  (GET "/admin/users"  req [] (users/users req))
  (POST "/admin/users" req [] (users/users-grid req))
  (GET "/admin/users/edit/:id" [id] (users/users-form id))
  (POST "/admin/users/save" req [] (users/users-save req))
  (POST "/admin/users/delete" req [] (users/users-delete req))
  ;; End users

  ;; Start levels
  (GET "/admin/levels"  req [] (levels/levels req))
  (POST "/admin/levels" req [] (levels/levels-grid req))
  (GET "/admin/levels/edit/:id" [id] (levels/levels-form id))
  (POST "/admin/levels/save" req [] (levels/levels-save req))
  (POST "/admin/levels/delete" req [] (levels/levels-delete req))
  ;; End levels

  ;; Start category
  (GET "/admin/category"  req [] (category/category req))
  (POST "/admin/category" req [] (category/category-grid req))
  (GET "/admin/category/edit/:id" [id] (category/category-form id))
  (POST "/admin/category/save" req [] (category/category-save req))
  (POST "/admin/category/delete" req [] (category/category-delete req))
  ;; End category

  ;; Start wishlist
  (GET "/admin/wishlist"  req [] (wishlist/wishlist req))
  (POST "/admin/wishlist" req [] (wishlist/wishlist-grid req))
  (GET "/admin/wishlist/edit/:id" [id] (wishlist/wishlist-form id))
  (POST "/admin/wishlist/save" req [] (wishlist/wishlist-save req))
  (POST "/admin/wishlist/delete" req [] (wishlist/wishlist-delete req))
  ;; End wishlist

  ;; Start emergencies
  (GET "/admin/emergencies"  req [] (emergencies/emergencies req))
  (POST "/admin/emergencies" req [] (emergencies/emergencies-grid req))
  (GET "/admin/emergencies/edit/:id" [id] (emergencies/emergencies-form id))
  (POST "/admin/emergencies/save" req [] (emergencies/emergencies-save req))
  (POST "/admin/emergencies/delete" req [] (emergencies/emergencies-delete req))
  ;; End emergencies

  ;; Start items
  (GET "/admin/items"  req [] (items/items req))
  (POST "/admin/items" req [] (items/items-grid req))
  (GET "/admin/items/edit/:id" [id] (items/items-form id))
  (POST "/admin/items/save" req [] (items/items-save req))
  (POST "/admin/items/delete" req [] (items/items-delete req))
  ;; End items
  )
