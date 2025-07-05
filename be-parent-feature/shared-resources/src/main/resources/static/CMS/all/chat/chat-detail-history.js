$(function(){
	$('#cancel,.backlist').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "chat/history";
		
		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	function setConditionSearch() {
		var condition = {};
		condition["fieldSearch"] = $("#fieldSearch").val();
		condition["fieldValues"] = $("#fieldValues").val();
		condition["fromDateSearch"] = $("#fromDate").val();
		condition["toDateSearch"] = $("#toDate").val();
		return condition;
	}
})