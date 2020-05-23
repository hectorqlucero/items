(ns sk.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [sk.models.crud :refer [config]]
            [sk.models.util :refer [user-level]]))

(defn build-admin
  []
  (cond 
    (= (user-level) "S")
    (list
      [:a.dropdown-item {:href "/admin/users"} "Users"]
      [:a.dropdown-item {:href "/admin/levels"} "Levels"]
      [:a.dropdown-item {:href "/admin/category"} "Category"]
      [:a.dropdown-item {:href "/admin/wishlist"} "Wishlist"]
      [:a.dropdown-item {:href "/admin/emergencies"} "Emergencies"]
      [:a.dropdown-item {:href "/admin/items"} "Items"])
    (= (user-level) "A")
    (list
      [:a.dropdown-item {:href "/admin/levels"} "Levels"]
      [:a.dropdown-item {:href "/admin/category"} "Category"]
      [:a.dropdown-item {:href "/admin/wishlist"} "Wishlist"]
      [:a.dropdown-item {:href "/admin/emergencies"} "Emergencies"]
      [:a.dropdown-item {:href "/admin/items"} "Items"])
    (= (user-level) "U")
    (list
      [:a.dropdown-item {:href "/admin/wishlist"} "Wishlist"]
      [:a.dropdown-item {:href "/admin/emergencies"} "Emergencies"]
      [:a.dropdown-item {:href "/admin/items"} "Items"])
    :else "Ni madre"))

(defn menus-private []
  (list
    [:nav.navbar.navbar-expand-sm.navbar-dark.bg-primary.fixed-top
     [:a.navbar-brand {:href "/"} (:site-name config)]
     [:button.navbar-toggler {:type "button"
                              :data-toggle "collapse"
                              :data-target "#collapsibleNavbar"}
      [:span.navbar-toggler-icon]]
     [:div#collapsibleNavbar.collapse.navbar-collapse
      [:ul.navbar-nav
       [:li.nav-item [:a.nav-link {:href "/emergencies"} "Emergencies"]]
       [:li.nav-item [:a.nav-link {:href "/wishlist"} "Wishlist"]]
       [:li.nav-item [:a.nav-link {:href "/items"} "Items"]]
       [:li.nav-item.dropdown
        [:a.nav-link.dropdown-toggle {:href "#"
                                      :id "navdrop"
                                      :data-toggle "dropdown"} "Admin menu"]
        [:div.dropdown-menu
         (build-admin)]]
       [:li.nav-item [:a.nav-link {:href "/home/logoff"} "Logoff"]]]]]))

(defn menus-public []
  (list
    [:nav.navbar.navbar-expand-sm.navbar-dark.bg-primary.fixed-top
     [:a.navbar-brand {:href "/"} (:site-name config)]
     [:button.navbar-toggler {:type "button"
                              :data-toggle "collapse"
                              :data-target "#collapsibleNavbar"}
      [:span.navbar-toggler-icon]]
     [:div#collapsibleNavbar.collapse.navbar-collapse
      [:ul.navbar-nav
       [:li.nav-item [:a.nav-link {:href "/emergencies"} "Emergencies"]]
       [:li.nav-item [:a.nav-link {:href "/wishlist"} "Wishlist"]]
       [:li.nav-item [:a.nav-link {:href "/items"} "Items"]]
       [:li.nav-item [:a.nav-link {:href "/home/login"} "Login"]]
       ]]]))

(defn app-css []
  (list
    (include-css "/bootstrap/css/bootstrap.min.css")
    (include-css "/bootstrap/css/lumen.min.css")
    (include-css "https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css")
    (include-css "/easyui/themes/metro-blue/easyui.css")
    (include-css "/easyui/themes/icon.css")
    (include-css "/easyui/themes/color.css")
    (include-css "/css/main.css")
    (include-css "/RichText/src/richtext.min.css")))

(defn app-js []
  (list
    (include-js "/easyui/jquery.min.js")
    (include-js "/popper/popper.min.js")
    (include-js "/bootstrap/js/bootstrap.min.js")
    (include-js "/easyui/jquery.easyui.min.js")
    (include-js "/easyui/jquery.edatagrid.js")
    (include-js "/easyui/datagrid-detailview.js")
    (include-js "/easyui/datagrid-groupview.js")
    (include-js "/easyui/datagrid-bufferview.js")
    (include-js "/easyui/datagrid-scrollview.js")
    (include-js "/easyui/datagrid-filter.js")
    (include-js "/easyui/locale/easyui-lang-en.js")
    (include-js "/RichText/src/jquery.richtext.min.js")
    (include-js "/js/main.js")))

(defn application [title ok js & content]
  (html5 {:ng-app (:site-name config) :lang "en"}
         [:head
          [:title (:site-name config)]
          [:meta {:charset "UTF-8"}]
          [:meta {:name "viewport"
                  :content "width=device-width, initial-scale=1"}]
          (app-css)]
         [:body
          (cond
            (= ok -1) nil
            (= ok 0) (menus-public)
            (> ok 0) (menus-private))
          [:div#content.container-fluid.easyui-panel {:style "margin-top:75px;border:none;"
                                                      :data-options "closed:false"} 
           content]
          (app-js)
          js]))

(defn error-404 [error return-url]
  [:div
   [:p [:h3 [:b "Error: "]] error]
   [:p [:h3 [:a {:href return-url} "Clic here to " [:strong "Return"]]]]])