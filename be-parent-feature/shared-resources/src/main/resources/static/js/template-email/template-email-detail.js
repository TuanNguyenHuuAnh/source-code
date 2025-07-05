$(document).ready(function(){
	var content = $('#templateContent').val();
	$('#content').summernote('code', content);
	$('#content').summernote('disable');
    //on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "email/template/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
});
