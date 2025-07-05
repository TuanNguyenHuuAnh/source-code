$(function(){
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'asset-profile/ajaxListHistory',
		type : 'POST',
		setData : setConditionSearch
	});
});

function setConditionSearch() {
	var condition = {};	
	condition["profileId"] 				= $("#profileId").val();
	
	return condition;
}