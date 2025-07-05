$(document).ready(function($) {
	// datatable load
	$(".history-process-table").datatables({
		url : BASE_URL + $("#ajaxHistoryUrl").val(),
		type : 'POST',
		setData : setConditionHistory
	});

});

/**
 * setConditionSearch
 */
function setConditionHistory() {
	var condition = {};
	condition["referenceId"] = $("#referenceId").val();
	condition["referenceType"] = $("#referenceType").val();
	return condition;
}
