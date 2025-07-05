$(document).ready(function() {
	$("#userList").datatables({
		url : BASE_URL + 'position-tree/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
});
