$(document).ready(function($) {	
	// Downlines datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'report-auth/list',
		type : 'POST',
		setData : setConditionSearch,
		"scrollX" : true,
		"sScrollXInner" : "100%",
	});
});

function setApproveConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $('#fieldSearchHidden').val();
	condition["fieldValues"] = $('#fieldValuesHidden').val();
	condition["companyId"] = $('#companyId').val();
	condition["page"] = 1;
	return condition;
}