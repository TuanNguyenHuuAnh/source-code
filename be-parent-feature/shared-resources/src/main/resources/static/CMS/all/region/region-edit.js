$(document).ready(function() {
	// set readonly code
	if ($("#regionId").val() != "") {
		$("#regionCode").attr('readonly', 'readonly');
	}		
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "region/list";
		
		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "region/list";

		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "region/edit";
		// Redirect to page add
		ajaxRedirectWithCondition(url, setConditionSearch());
	});	
	// Post edit save job
	$('#btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "region/edit";
			var condition = $("#form-region-edit").serialize();

			ajaxSubmit(url, condition, event);
			
		}else{
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
		}			
	});
	
	function setConditionSearch() {
		var condition = {};
		condition["fieldSearch"] = $("#fieldSearch").val();
		condition["fieldValues"] = $("#fieldValues").val();
		return condition;
	}
	
	// show tab if exists error
	showTabError(LANGUAGE_LIST);
});