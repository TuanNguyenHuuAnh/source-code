$(document).ready(function($) {
	
	if ($("#term-id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}
	
	//on click cancel
	$('#cancel').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "term/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click list
	$('#linkList').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "term/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click save button
	$('#btnSave').on('click', function(event) {
		event.preventDefault();
		
		if ($(".j-form-validate").valid()) {
			
			var url = "interest-rate/calculatingExpression/edit";
			var condition = $(".j-form-validate").serialize();
			console.log(condition);

			ajaxSubmit(url, condition, event);
		}
	});
});
