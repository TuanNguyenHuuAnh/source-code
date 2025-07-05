$(document).ready(function() {
	var initialState = 'collapsed';
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'user-login/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
    
});
