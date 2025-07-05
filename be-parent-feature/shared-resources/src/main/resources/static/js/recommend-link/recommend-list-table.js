$(document).ready(function($) {
	// on click delete
	$(".j-btn-delete").on("click", function( event ) {
		deleteCountry(this, event);
	});
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'recommend-link/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		event.preventDefault();
		// Prepare data
		var row = $(this).parents("tr");
		var id = row.data("recommend-link-id");
		var url = BASE_URL + "recommend-link/edit?id=" + id;

		// Redirect to page detail
		ajaxRedirect(url);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		event.preventDefault();

		// Prepare data
		var row = $(this).parents("tr");
		var id = row.data("recommend-link-id");
		var url = BASE_URL + "recommend-link/detail?id=" + id;

		// Redirect to page detail
		ajaxRedirect(url);
	});
	
});
/**
 * deleteCountry
 * @param element
 * @param event
 * @returns
 */
 function deleteCountry(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("recommend-link-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "recommend-link/delete";
			var condition = {
				"id" : id
			}
			
			ajaxSubmit(url, condition, event);
			
		}				
	});
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}
