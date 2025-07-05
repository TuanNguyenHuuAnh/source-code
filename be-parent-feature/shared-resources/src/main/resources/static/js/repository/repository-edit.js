$(document).ready(function() {	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "repository/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "repository/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "repository/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	
	// Post edit save
	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "repository/edit";
			var condition = $("#form-repository-edit").serialize();

			ajaxSubmit(url, condition, event);
		}			
	});
	
	$('#upperCaseCode').keyup(function(){
        $(this).val($(this).val().toUpperCase());
    });
});