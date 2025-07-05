$(document).ready(function($) {
	// on click delete
	$(".j-btn-delete").on("click", function( event ) {
		deleteRepository(this, event);
	});
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'ecm-repository/ajaxList',
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
	
});
