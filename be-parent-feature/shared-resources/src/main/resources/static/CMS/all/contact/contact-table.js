$(document).ready(function($) {
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'cms-contact/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
});