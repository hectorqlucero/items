(ns sk.handlers.admin.wishlist.view
  (:require [ring.util.anti-forgery :refer [anti-forgery-field]]
            [hiccup.page :refer [include-js]]
            [sk.models.util :refer [build-table
                                    build-dialog
                                    build-dialog-buttons
                                    build-field
                                    build-toolbar]]))

(def dialog-fields
  (list
    [:input {:type "hidden" :id "id" :name "id"}]
    (build-field
      {:id "description"
       :name "description"
       :class "easyui-textbox"
       :prompt "Description here..."
       :data-options "label:'Description:',labelPosition:'top',required:true,width:'100%'"})
    (build-field
      {:id "sdate"
       :name "sdate"
       :class "easyui-datebox"
       :prompt "mm/dd/yyyy"
       :data-options "label:'Start Date:',labelPosition:'top',required:true,width:'100%'"})
    (build-field
      {:id "edate"
       :name "edate"
       :class "easyui-datebox"
       :prompt "mm/dd/yyyy"
       :data-options "label:'End Date:',labelPosition:'top',required:false,width:'100%'"})
    (build-field
      {:id "cost"
       :name "cost"
       :class "easyui-numberbox"
       :prompt "Cost of this item..."
       :data-options "label:'Cost:',labelPosition:'top',min:1,precision:2,prefix:'$',width:'100%'"})
    (build-field
      {:id "category_id"
       :name "category_id"
       :class "easyui-combobox"
       :data-options "label:'Category:',
                     labelPosition:'top',
                     url:'/table_ref/get-category',
                     method:'GET',
                     required:true,
                     width:'100%'"})
    (build-field
      {:id "levels_id"
       :name "levels_id"
       :class "easyui-combobox"
       :data-options "label:'Level:',
                     labelPosition:'top',
                     url:'/table_ref/get-levels',
                     method:'GET',
                     required:true,
                     width:'100%'"})))

(defn wishlist-view [title]
  (list
    (anti-forgery-field)
    (build-table
      title 
      "/admin/wishlist" 
      (list
        [:th {:data-options "field:'description',sortable:true,fixed:false,width:100"} "Description"]
        [:th {:data-options "field:'sdate_formatted',sortable:true,fixed:false,width:100"} "Start Date"]
        [:th {:data-options "field:'edate_formatted',sortable:true,fixed:true,width:100"} "End Date"]
        [:th {:data-options "field:'cost_formatted',sortable:true,fixed:true,width:100"} "Cost"]))
    (build-toolbar)
    (build-dialog title dialog-fields)
    (build-dialog-buttons)))

(defn wishlist-scripts []
  (include-js "/js/grid.js"))
