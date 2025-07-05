$(document).ready(function() {
	$("#userList").datatables({
		url : BASE_URL + 'organization/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
});
