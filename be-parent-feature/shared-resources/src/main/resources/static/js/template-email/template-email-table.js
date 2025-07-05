$(document).ready(function() {
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'email/template/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
})