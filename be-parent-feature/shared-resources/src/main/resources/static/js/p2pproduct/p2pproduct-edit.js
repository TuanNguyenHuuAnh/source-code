
$(function(){
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "product/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click cancel
    $('.select2').select2();
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "product/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "product/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	

	// Post edit save
	$('#btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "product/edit";
			var condition = $("#form-edit").serialize();
			ajaxSubmit(url, condition, event);
		}			
		goTopPage();
	});

	function goTopPage(){
		$("html, body").animate({ scrollTop: 0 }, "1");
	}
});