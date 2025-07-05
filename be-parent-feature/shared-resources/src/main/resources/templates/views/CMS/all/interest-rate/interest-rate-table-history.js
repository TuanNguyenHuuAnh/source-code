$(document).ready(function($) {
	
	// datatable load
	$(".tableList02").datatables({
		url : BASE_URL + 'interest-rate/ajaxListHistory',
		type : 'POST',
		setData : setConditionSearch
	});
	
	$('#tabInterestRate a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	$("#fieldCityHidden").val($("#cityId").combobox('getValue'));
	
});

/**
 * setConditionSearch
 */
function setConditionSearch() {
	var condition = {};
	condition["cityId"] = $("#fieldCityHidden").val();
	return condition;
}
