$(document).ready(function($) {
	// on click delete
	$(".j-serial-info-delete").on("click", function( event ) {
		deleteSerialInfo(this, event);
	});
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'serial-info/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "serial-info/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editSerialInfo(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailSerialInfo(this, event);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
});
/**
 * deleteSerialInfo
 * @param element
 * @param event
 * @returns
 */
function deleteSerialInfo(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var serialInfoId = row.data("serial-info-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "serial-info/delete";
			var condition = {
				"serialInfoId" : serialInfoId
			}
			
			ajaxSubmit(url, condition, event);
			
		}			
	});
}
/**
 * editSerialInfo
 * @param element
 * @param event
 * @returns
 */
function editSerialInfo(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("serial-info-id");
	var url = BASE_URL + "serial-info/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * detail serial
 * @param element
 * @param event
 * @returns
 */
function detailSerialInfo(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("serial-info-id");
	var url = BASE_URL + "serial-info/detail?id=" + id;

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

	ajaxSearch("serial-info/ajaxList", setConditionSearch(), "tableList", element, event);
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