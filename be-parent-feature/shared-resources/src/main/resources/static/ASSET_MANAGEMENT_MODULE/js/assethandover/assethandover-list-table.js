$(document).ready(function($) {
	// on click delete
	$(".j-handover-delete").on("click", function( event ) {
		deleteAssetHandover(this, event);
	});
	
	// multiple select search
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// datatable load on search
	$("#tableList").datatables({
		url : BASE_URL + 'asset-handover/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-handover/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editAssetHandover(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailAssetHandover(this, event);
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
    var linkExport = BASE_URL + "asset-handover/export-excel";
    doExportExcelWithToken(linkExport, "token", setConditionSearch());
}

 function deleteAssetHandover(element, event){
	// ngăn chặn luồn sự kiện tiếp diễn cho đến khi tới controller
		// có nghĩa ko cho nó qua trang detail ngay lập tức nếu chưa đc sự chấp thuận controller
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var assetHandoverId = row.data("assethandover-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "asset-handover/delete";
			var condition = {
				"id" : assetHandoverId
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
function editAssetHandover(element, event) {
	// ngăn chặn luồn sự kiện tiếp diễn cho đến khi tới controller
	// có nghĩa ko cho nó qua trang detail ngay lập tức nếu chưa đc sự chấp thuận controller
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("assethandover-id");
	var url = BASE_URL + "asset-handover/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

/**
 * detailCountry
 * @param element
 * @param event
 * @returns
 */
function detailAssetHandover(element, event) {
	// ngăn chặn luồn sự kiện tiếp diễn cho đến khi tới controller
	// có nghĩa ko cho nó qua trang detail ngay lập tức nếu chưa đc sự chấp thuận controller
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("assethandover-id");
	var url = BASE_URL + "asset-handover/detail?id=" + id;

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

	ajaxSearch("asset-handover/ajaxList", setConditionSearch(), "tableList", element, event);
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
	condition["handoverNo"] = $.trim($("#handoverNo").val());
	condition["processStatusCode"] = $.trim($("#processStatusCode").val());
	condition["handoverDateFrom"] = $.trim($("#handoverDateFrom").val());
	condition["handoverDateTo"] = $.trim($("#handoverDateTo").val());
	condition["handoverFullname"] = $.trim($("#handoverFullname").val());
	condition["warehouseName"] = $.trim($("#warehouseName").val());
	condition["receiverFullname"] = $.trim($("#receiverFullname").val());
	condition["nameOfTheAsset"] = $.trim($("#nameOfTheAsset").val());
	//console.log(condition);
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}

