(ns sk.handlers.items.view)

(defn items-view
  [title rows e]
  (list
    [:div.container
     [:div.col-12.text-center
      [:h3 {:style "color:#158CBA;text-transform:uppercase;font-weight:bold;"} title]]
     [:hr]
     [:div.col-12.text-center {:style "margin-bottom:10px;"}
      [:select#items-filter
       [:option {:value ""
                 :selected (if (= e 0) "selected" nil)} "All Items"]
       [:option {:value "/pending"
                 :selected (if (= e 1) "selected" nil)} "Pending Items"]
       [:option {:value "/completed"
                 :selected (if (= e 2) "selected" nil)} "Completed Items"]]]
     [:br]
     [:table.table.table-hover.table-light
      [:thead.table-primary
       [:tr
        [:th "Description"]
        [:th "Start Date"]
        [:th "End Date"]
        [:th "Cost"]
        [:th "Category"]
        [:th "Level"]]]
      [:tbody
       (map (fn [row]
              (list
                [:tr
                 [:td (:description row)]
                 [:td (:sdate row)]
                 [:td (:edate row)]
                 [:td (:cost row)]
                 [:td (:category_id row)]
                 [:td (:levels_id row)]])) rows)]]]))

(defn items-scripts []
  [:script
   "
   $(document).ready(function() {
    $('#items-filter').change(function() {
      var url = '/items' + $(this).val();
      window.location.replace(url);
    });
   });
   "])
