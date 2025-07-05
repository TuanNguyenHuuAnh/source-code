$(document).ready(function($) {
	// on click delete
	$(".j-unit-delete").on("click", function( event ) {
		deleteUnit(this, event);
	});
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'unit/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "unit/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editPosition(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailPosition(this, event);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
});
/**
 * 
 * @param element
 * @param event
 * @returns
 */
function deleteUnit(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var unitId = row.data("unit-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "unit/delete";
			var condition = {
				"id" : unitId
			}
			
			ajaxSubmit(url, condition, event);
			
		}			
	});
}
/**
 * 
 * @param element
 * @param event
 * @returns
 */
function editPosition(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("unit-id");
	var url = BASE_URL + "unit/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * 
 * @param element
 * @param event
 * @returns
 */
function detailPosition(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("unit-id");
	var url = BASE_URL + "unit/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {

	setDataSearchHidden();

	ajaxSearch("unit/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}