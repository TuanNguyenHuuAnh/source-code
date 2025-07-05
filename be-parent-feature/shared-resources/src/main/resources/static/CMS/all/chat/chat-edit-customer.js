$(function(){
	//on click cancel
	$('#cancel,.backlist').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "chat/history";
		
		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	//on click list
	$('#linkList,.backlist').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "chat/history";

		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	// on click button save
	$('#btnSave').on('click', function(event) {
		if ($(".j-form-validate").valid()) {

			var url = "chat/eidt-customer";
			var condition = $("#typeForm").serialize();
			condition["fieldSearch"] = $("#fieldSearch").val();
			condition["fieldValues"] = $("#fieldValues").val();
			condition["fromDateSearch"] = $("#fromDate").val();
			condition["toDateSearch"] = $("#toDate").val();
			ajaxSubmit(url, condition, event);
//			ajaxRedirectWithCondition(url, setConditionSearch());
		}
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