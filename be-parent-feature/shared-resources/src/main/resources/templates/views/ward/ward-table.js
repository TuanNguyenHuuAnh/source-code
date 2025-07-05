$(document).ready(function($) {		
	// on click delete
	$(".j-ward-delete").on("click", function( event ) {
		deleteWard(this, event);
	});
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'ward/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editWard(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailWard(this, event);
	});
	
});
/**
 * deleteWard
 * @param element
 * @param event
 * @returns
 */
function deleteWard(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var wardId = row.data("ward-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "ward/delete";
			var condition = {
				"id" : wardId
			}
			
			ajaxSubmit(url, condition, event);			
		}			
	});		
}
/**
 * editWard
 * @param element
 * @param event
 * @returns
 */
function editWard(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("ward-id");
	var url = BASE_URL + "ward/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * detailWard
 * @param element
 * @param event
 * @returns
 */
function detailWard(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("ward-id");
	var url = BASE_URL + "ward/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}
