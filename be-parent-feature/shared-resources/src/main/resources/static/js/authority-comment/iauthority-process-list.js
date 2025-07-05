$(document).ready(function() {
	//onchange business
	$('#businessId').on('change', function() {
		var businessId = $(this).val();
		var companyId = $('#companyId').val();
		ajaxLoadFuction(businessId, null, companyId);
	});
	//onchange process
	$('#processId').on('change', function() {
		var businessId = $('#businessId').val();
		var processId = $(this).val();
		var companyId = $('#companyId').val();
		ajaxLoadFuction(businessId, processId, companyId);
	});
});