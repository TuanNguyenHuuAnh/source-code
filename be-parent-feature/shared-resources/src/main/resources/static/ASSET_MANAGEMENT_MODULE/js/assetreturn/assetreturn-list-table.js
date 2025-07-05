$(document).ready(function($) {
	// on click delete
	$(".j-return-delete").on("click", function( event ) {
		deleteAssetReturn(this, event);
	});
	
	// multiple select search
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// datatable load on search
	$("#tableList").datatables({
		url : BASE_URL + 'asset-return/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-return/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editAssetReturn(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailAssetReturn(this, event);
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
    var linkExport = BASE_URL + "asset-return/export-excel";
    doExportExcelWithToken(linkExport, "token", setConditionSearch());
}

 function deleteAssetReturn(element, event){
	// ngăn chặn luồn sự kiện tiếp diễn cho đến khi tới controller
		// có nghĩa ko cho nó qua trang detail ngay lập tức nếu chưa đc sự chấp thuận controller
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var assetReturnId = row.data("assetreturn-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "asset-return/delete";
			var condition = {
				"id" : assetReturnId
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
function editAssetReturn(element, event) {
	// ngăn chặn luồn sự kiện tiếp diễn cho đến khi tới controller
	// có nghĩa ko cho nó qua trang detail ngay lập tức nếu chưa đc sự chấp thuận controller
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("assetreturn-id");
	var url = BASE_URL + "asset-return/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

/**
 * detailCountry
 * @param element
 * @param event
 * @returns
 */
function detailAssetReturn(element, event) {
	// ngăn chặn luồn sự kiện tiếp diễn cho đến khi tới controller
	// có nghĩa ko cho nó qua trang detail ngay lập tức nếu chưa đc sự chấp thuận controller
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("assetreturn-id");
	var url = BASE_URL + "asset-return/detail?id=" + id;

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

	ajaxSearch("asset-return/ajaxList", setConditionSearch(), "tableList", element, event);
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
	condition["returnNo"] = $.trim($("#returnNo").val());
	condition["processStatusCode"] = $.trim($("#processStatusCode").val());
	condition["returnDateFrom"] = $.trim($("#returnDateFrom").val());
	condition["returnDateTo"] = $.trim($("#returnDateTo").val());
	condition["returnFullname"] = $.trim($("#returnFullname").val());
	condition["warehouseName"] = $.trim($("#warehouseName").val());
	//console.log(condition);
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}

