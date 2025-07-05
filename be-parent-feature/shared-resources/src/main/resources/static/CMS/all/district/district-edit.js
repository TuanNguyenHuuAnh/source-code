$(document).ready(function() {
	// set readonly code
	if ($("#districtId").val() != "" && $("#districtId").val() != null) {
		$("#districtCode").attr('readonly', 'readonly');
	}	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "district/list";
		
		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "district/list";

		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "district/edit";
		// Redirect to page add
		ajaxRedirectWithCondition(url, setConditionSearch());
	});	
	// Post edit save job
	$('#btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {	
			var url = "district/edit";
			var condition = $("#form-district-edit").serialize();

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