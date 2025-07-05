$(document).ready(function($) {
	// on click delete
	$(".j-assettype-delete").on("click", function( event ) {
		deleteAssetType(this, event);
	});
	
	// multiple select search
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'asset-type/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-type/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editAssetType(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailAssetType(this, event);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
	
	
});


/**
 * deleteCountry
 * @param element
 * @param event
 * @returns
 */

function doExportExcel() {
	
    var linkExport = BASE_URL + "asset-type/export-excel";
    doExportExcelWithToken(linkExport, "token", setConditionSearch());
}

 function deleteAssetType(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var assetTypeId = row.data("assettype-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "asset-type/delete";
			var condition = {
				"id" : assetTypeId
			}
			
			ajaxSubmit(url, condition, event);
			
		}				
	});
}

 /**
  * editCountry
  * @param element
  * @param event
  * @returns
  */
function editAssetType(element, event) {
	
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("assettype-id");
	var url = BASE_URL + "asset-type/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

/**
 * detailCountry
 * @param element
 * @param event
 * @returns
 */
function detailAssetType(element, event) {
	
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("assettype-id");
	var url = BASE_URL + "asset-type/detail?id=" + id;

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

	ajaxSearch("asset-type/ajaxList", setConditionSearch(), "tableList", element, event);
}

/*function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}*/
function setConditionSearch() {
	
	var condition = {};
	condition["fieldSearch"] = $.trim($("#fieldSearchHidden").val());
	//console.log(condition);
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}