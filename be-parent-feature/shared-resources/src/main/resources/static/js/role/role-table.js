$(document).ready(function($) {
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'role/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	//on click edit
	$(".j-btn-edit").on("click", function(event) {
		editRole(this, event);
	});
	
	//on click delete
	$(".j-btn-delete").on("click", function(event) {
		deleteRole(this, event);
		$(window).scrollTop(0);
	});
	
	$(".j-btn-detail").on("click", function(event) {
		viewRole(this, event);
	});
	
});
