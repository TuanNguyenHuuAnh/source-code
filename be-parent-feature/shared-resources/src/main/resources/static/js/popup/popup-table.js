$(document).ready(function() {
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'popup/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
})