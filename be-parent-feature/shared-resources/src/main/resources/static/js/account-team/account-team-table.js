$(document).ready(function($) {
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'account-team/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	$(".j-team-delete").on("click", function( event ) {
		deleteTeam(this, event);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editTeam(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailTeam(this, event);
	});	
});
