$(document).ready(function($) {
	// on click delete
	$(".j-position-delete").on("click", function( event ) {
		deletePosition(this, event);
	});
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'position/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editPosition(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailPosition(this, event);
	});
});
