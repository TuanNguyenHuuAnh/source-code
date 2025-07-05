$(document).ready(function() {
	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}	
	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "recommend-link/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "recommend-link/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "recommend-link/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	
	// Post edit save job
	$('.btn-save').on('click', function(event) {		
		if ($(".j-form-validate").valid()) {	
			var url = "recommend-link/edit";
			var condition = $("#form-recommend-link-edit").serialize();			
			ajaxSubmit(url, condition, event);								
		}else{
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
		}			
	});
	// show tab if exists error
	showTabError(LANGUAGE_LIST);
});