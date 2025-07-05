$(document).ready(function() {	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "unit/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "unit/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "unit/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	
	// Post edit save job
	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "unit/edit";
			var condition = $("#form-unit-edit").serialize();

			ajaxSubmit(url, condition, event);
		}			
	});
});