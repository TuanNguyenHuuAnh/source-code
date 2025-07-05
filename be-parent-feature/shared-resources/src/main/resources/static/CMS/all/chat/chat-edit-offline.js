$(function(){
	//on click cancel
	$('#cancel,.backlist').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "chat/list-offline";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList,.backlist').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "chat/list-offline";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click button save
	$('#btnSave').on('click', function(event) {
		if ($(".j-form-validate").valid()) {

			var url = "chat/edit-offline";
			var condition = $("#typeForm").serialize();

			ajaxSubmit(url, condition, event);
		}
	});
})