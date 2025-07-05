$(function() {
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});

	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'asset-adjust/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editItem(this, event);
	});

	// on click delete
	$(".j-btn-delete").on("click", function(event) {
		deleteItem(this, event);
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-adjust/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailItem(this, event);
	});

	$('#btnExport').click(function() {
		var linkExport = BASE_URL + "asset-adjust/export-excel";
		doExportExcelWithToken(linkExport, "token", setConditionSearch());
	});

	initData();
})

function setConditionSearch() {
	var condition = {};
	condition["assetName"] = $("#assetName").val();
	condition["assetSerialNo"] = $("#assetSerialNo").val();
	condition["assetTag"] = $("#assetTag").val();
	condition["assetStatus"] = $("#assetStatus").val();
	condition["adjustmentDateTo"] = $("#adjustmentDateTo").val();
	condition["adjustmentDateFrom"] = $("#adjustmentDateFrom").val();
	condition["status"] = $("#status").val();
	condition["assetLocationName"] = $("#assetLocationName").val();
	return condition;
}

function setDataSearchHidden() {
	$("#assetNameHidden").val($("#assetName").val());
	$("#assetSerialNoHidden").val($("#assetSerialNo").val());
	$("#assetTagHidden").val($("#assetTag").val());
	$("#assetStatusHidden").val($("#assetStatus").val());
	$("#adjustmentDateToHidden").val($("#adjustmentDateTo").val());
	$("#adjustmentDateFromHidden").val($("#adjustmentDateFrom").val());
	$("#statusHidden").val($("#status").val());
	$("#officeLocationHidden").val($("#assetLocationName").val());
}

function onClickSearch(element, event) {
	setDataSearchHidden();
	ajaxSearch("asset-adjust/ajaxList", setConditionSearch(), "tableList",
			element, event);
}

function editItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	var url = BASE_URL + "asset-adjust/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

function deleteItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");

	popupConfirm(MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "asset-adjust/delete";
			var condition = {
				"id" : id
			}
			ajaxSubmit(url, condition, event);
		}
	});
}

function detailItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	var url = BASE_URL + "asset-adjust/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

function initData() {
	$("#fieldSearch").unbind('keypress').bind('keypress', function(event) {
		if (event.keyCode == 13) {
			onClickSearch(this, event);
		}
	});
	
	$("#assetName, #assetSerialNo, #assetTag, #imei, #adjustmentDateFrom, #adjustmentDateTo, #assetLocationName").on('keypress', function(e) {
		if((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)){
			onClickSearch(this, event);
		}
	});

/*	// date picker
	$(".date").datepicker({
		autoclose : true,
		format : 'dd/mm/yyyy'
	});*/
	
	var idEffectedDate = $("#adjustmentDateFrom").val();
	var idExpiredDate = $("#adjustmentDateTo").val();
	changeDatepickerById(idEffectedDate, idExpiredDate, '#adjustmentDateFrom',
			'#adjustmentDateTo');
}
