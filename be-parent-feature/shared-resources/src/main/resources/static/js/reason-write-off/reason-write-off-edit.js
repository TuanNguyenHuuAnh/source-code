$(document).ready(function() {	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "reason-write-off/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "reason-write-off/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "reason-write-off/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	
	// Post edit save job
	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "reason-write-off/edit";
			var condition = $("#form-reason-edit").serialize();
			ajaxSubmit(url, condition, event);
		}
	});
});