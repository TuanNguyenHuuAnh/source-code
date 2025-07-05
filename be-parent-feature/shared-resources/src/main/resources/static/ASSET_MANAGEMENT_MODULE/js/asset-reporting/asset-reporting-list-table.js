$(function(){
	/*$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});*/
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'asset-reporting/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	// init date picker
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

	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailItem(this, event);
	});

	$('#btnExport').unbind().bind('click',function() {
		var linkExport = BASE_URL + "asset-reporting/export-excel";
		//doExportExcel(linkExport);
		doExportExcelWithToken(linkExport, "token", setConditionSearch());
	});
})

function detailItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	var url = BASE_URL + "asset-reporting/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

