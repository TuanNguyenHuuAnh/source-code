/**
 * CostCenter-list JavaScript file.
 */

$(document).ready(function($) {
	// on click delete
	$(".j-sub-delete").on("click", function( event ) {
		deleteSubCategory(this, event);
	});
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'sub-category/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "sub-category/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editSubCategory(this, event);
	});
	
//	// on click detail
//	$(".j-btn-detail").on("click", function(event) {
//		detailPosition(this, event);
//	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
});
/**
 * deletePosition
 * @param element
 * @param event
 * @returns
 */
function deleteSubCategory(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("sub-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "sub-category/delete";
			var condition = {
				"id" : id
			}
			
			ajaxSubmit(url, condition, event);
			
		}			
	});
}
/**
 * editPosition
 * @param element
 * @param event
 * @returns
 */
function editSubCategory(element, event) {
	event.preventDefault();
	debugger;
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("sub-id");
	var url = BASE_URL + "sub-category/edit?id=" + id;;
	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * detail position
 * @param element
 * @param event
 * @returns
 */
//function detailPosition(element, event) {
//	event.preventDefault();
//
//	// Prepare data
//	var row = $(element).parents("tr");
//	var id = row.data("const-id");
//	var url = BASE_URL + "costcenter/detail?id=" + id;
//
//	// Redirect to page detail
//	ajaxRedirect(url);
//}
/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {

	setDataSearchHidden();

	ajaxSearch("sub-category/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[id=search-filter]").val());
}