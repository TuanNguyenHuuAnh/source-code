$(document).ready(function() {
	$('#positionGrid').tableFilterable({
        filters: [
        	{
                filterSelector: '#companyName',
                event: 'keyup',
                filterCallback: function($tr, filterValue) {
                    // filterValue is the content of the name input
                    return  $tr.children('td[data-tfilter1="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase()) != -1;
                }
            },
            {
                 filterSelector: '#positionName',
                 event: 'keyup',
                 filterCallback: function($tr, filterValue) {
                     // filterValue is the content of the name input
                     return  $tr.children('td[data-tfilter="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase()) != -1;
                 }
             }
        ]
    });
	
	//button edit
	$("#positionGrid .j-btn-edit").on("click", function () {
		$(this).parents("table").find(".highlight").removeClass("highlight");
		
		var rowSelected = $(this).parents("tr");
		var id = rowSelected.data("position-id");
		ajaxLoadPositionAuthorityByPositionId(id);
		rowSelected.addClass("highlight");
    });
});