$(document).ready(function() {
    $('#roleByFunctionTableId').tableFilterable({
        filters: [
            {
                 filterSelector: '#functionName',
                 event: 'keyup',
                 filterCallback: function($tr, filterValue) {
                     // filterValue is the content of the name input
                     return  $tr.children('td[data-tfilter="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase().trim()) != -1;
                 }
             }
        ]
    });
    
    // edit status authority
	$(".j-status-authority-edit").on("click", function(event){
		var url = BASE_URL + "authority/ajax/status/edit";
    	var id = "authority-status-edit";
    	
    	var data = [];
    	var row = $(this).parents("tr");
    	data.push({
            name : "roleId",
            value : row.find(".j-role-id").val()
        });
    	data.push({
            name : "itemId",
            value : row.find(".j-item-id").val()
        });
    	
    	openPopup( id, url, "GET", data, null );
	});
});


