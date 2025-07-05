$(document).ready(function() {
	//filter
	$('#functionTableId').tableFilterable({
        filters: [
            {
                 filterSelector: '#functionName',
                 event: 'keyup',
                 filterCallback: function($tr, filterValue) {
                     // filterValue is the content of the name input
                     return  $tr.children('td[data-tfilter1="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase()) != -1;
                 }
             }
        ]
    });
    
	//click edit
	$("#functionTableId .j-function-edit").on("click", function () {
		$(this).parents("table").find(".highlight").removeClass("highlight"); 
		var functionSelected = $(this).parents("tr");
		ajaxLoadAuthorityByFunction(functionSelected);
		functionSelected.addClass("highlight");
    });
});