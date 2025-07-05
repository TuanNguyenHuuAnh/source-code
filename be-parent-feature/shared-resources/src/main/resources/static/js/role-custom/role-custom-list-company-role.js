/**
 * @author trieuvd
 */
$(document).ready(function() {
	$('#roleTableId').tableFilterable({
		filters : [ 
			{
		       filterSelector: '#companyName',
		       event: 'keyup',
		       filterCallback: function($tr, filterValue) {
		       // filterValue is the content of the name input
		    	   return  $tr.children('td[data-tfilter1="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase()) != -1;
		       }
			},
			{
				filterSelector : '#roleName',
				event : 'keyup',
				filterCallback : function($tr, filterValue) {
				// filterValue is the content of the name input
					return $tr.children('td[data-tfilter2="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase()) != -1;
				}

			}
		]
	});
	
	$("#roleTableId .j-role-edit").on("click", function () {
		$(this).parents("table").find(".highlight").removeClass("highlight");
		var rowRoleSelected = $(this).parents("tr");
		var companyId = $("#company-id").val();
		if(companyId!=undefined){
			ajaxLoadAuthorityByRole(rowRoleSelected, companyId);
		}
		rowRoleSelected.addClass("highlight");
    });
});
