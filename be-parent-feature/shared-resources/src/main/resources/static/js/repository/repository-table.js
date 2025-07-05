$(document).ready(function($) {
	// on click delete
	$(".j-btn-delete").on("click", function( event ) {
		deleteRepository(this, event);
	});
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'repository/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editRepository(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailRepository(this, event);
	});
	
	$('#btnExport').click(function() {
		var linkExport = "repository/export-excel";
		doExportExcel(linkExport);
	});
});

function doExportExcel(linkExport) {
	setDataSearchHidden();
	doExportExcelWithToken(BASE_URL + linkExport, "token", setConditionSearch())
}