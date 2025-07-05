$(document).ready(function() {
    $('#roleTableId').tableFilterable({
        filters: [
            {
                 filterSelector: '#roleName',
                 event: 'keyup',
                 filterCallback: function($tr, filterValue) {
                     // filterValue is the content of the name input
                     return  $tr.children('td[data-tfilter2="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase()) != -1;
                 }
             }
        ]
    });
    
	$("#roleTableId .j-role-edit").on("click", function () {
		$(this).parents("table").find(".highlight").removeClass("highlight");
		var rowRoleSelected = $(this).parents("tr");
		ajaxLoadCompanyByRole(rowRoleSelected);
		rowRoleSelected.addClass("highlight");
    });
});