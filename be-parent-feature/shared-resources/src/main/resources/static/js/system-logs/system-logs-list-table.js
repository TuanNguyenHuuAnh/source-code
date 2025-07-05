$(document).ready(function($) {	
	// Downlines datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'system-logs/list',
		type : 'POST',
		setData : setConditionSearch,
		"scrollX" : true,
		"sScrollXInner" : "100%"
	});
});