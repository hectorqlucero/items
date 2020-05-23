(ns sk.handlers.admin.levels.view
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

    (build-field
      {:id "weight"
       :name "weight"
       :class "easyui-numberbox"
       :prompt "Weight: ex 1..10"
       :data-options "label:'Weight:',labelPosition:'top',required:true,min:1,max:10,width:'100%'"})))

(defn levels-view [title]
  (list
   (anti-forgery-field)
   (build-table
     title 
     "/admin/levels" 
     (list
       [:th {:data-options "field:'description',sortable:true,fixed:true,width:100"} "Level"]
       [:th {:data-options "field:'weight',sortable:true,width:100"} "Weight"]))
   (build-toolbar)
   (build-dialog title dialog-fields)
   (build-dialog-buttons)))

(defn levels-scripts []
  (include-js "/js/grid.js"))
