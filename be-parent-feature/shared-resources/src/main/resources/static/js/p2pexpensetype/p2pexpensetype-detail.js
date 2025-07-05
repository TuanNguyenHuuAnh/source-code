
$(function(){
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "expense-type/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "expense-type/list";
		// Redirect to page list
		ajaxRedirect(url);
	});

});