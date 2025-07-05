$(document).ready(function($) {
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'account/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	//on click edit
	$(".j-btn-edit").on("click", function(event) {
		editAccount(this, event);
	});
	
	//on click delete
	$(".j-btn-delete").on("click", function(event) {
		deleteAccount(this, event);
	});
	
	$(".j-btn-detail").on("click", function(event) {
		viewAccount(this, event);
	});
	
	//on click Sync LDAP
	$("#btnSyncLDAP").click(function(event) {
		event.preventDefault();
		var url = BASE_URL + "ldap-account/list";
		// Redirect to page LDAP
		ajaxRedirect(url);
	});
});
