(ns sk.handlers.admin.category.view
  (:require [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.page :refer [include-js]]
            [sk.models.util :refer [build-table
                                    build-dialog
                                    build-dialog-buttons
                                    build-field
                                    build-radio-buttons
                                    build-toolbar]]))

(def dialog-fields
  (list
    [:input {:type "hidden" :id "id" :name "id"}]
    (build-field
      {:id "description"
       :name "description"
       :class "easyui-textbox"
       :prompt "Level ex. Level 1"
       :data-options "label:'Level:',labelPosition:'top',required:true,width:'100%'"})

    (build-radio-buttons
      "Active?"
      (list
        {:id "active_no"
         :name "active"
         :class "easyui-radiobutton"
         :value "F"
         :data-options "label:'No', checked:true"}
        {:id "active_yes"
         :name "active"
         :class "easyui-radiobutton"
         :value "T"
         :data-options "label:'Yes'"}))))

(defn category-view [title]
  (list
   (anti-forgery-field)
   (build-table
     title 
     "/admin/category" 
     (list
       [:th {:data-options "field:'description',sortable:true,fixed:true,width:100"} "Level"]
       [:th {:data-options "field:'active',sortable:true,width:100"} "Active?"]))
   (build-toolbar)
   (build-dialog title dialog-fields)
   (build-dialog-buttons)))

(defn category-scripts []
  (include-js "/js/grid.js"))
