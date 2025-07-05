$(function(){
	$('#cancel,.backlist').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "chat/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
})