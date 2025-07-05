$(function(){
	/*$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});*/
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'asset-profile/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	// init date picker
	/*
	$('.date').datepicker({
		format: 'dd/mm/yyyy',
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : '${sessionScope.localeSelect}',
		todayHighlight : true,
		onRender : function(date) {
		}
	});
	*/

	// on click detail
	$(".j-btn-detail").off('click').on("click", function(event) {
		detailItem(this, event);
	});

	$('#btnExport').click(function() {
		var linkExport = BASE_URL + "asset-profile/export-excel";
		doExportExcelWithToken(linkExport, "token", setConditionSearch());
		//doExportExcel(linkExport);
	});
})

function detailItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	var url = BASE_URL + "asset-profile/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

