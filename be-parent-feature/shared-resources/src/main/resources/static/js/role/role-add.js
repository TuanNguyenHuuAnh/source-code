$(document).ready(function() {
	$("#linkList").on("click", function(event) {
		var url = BASE_URL + "role/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$("#btnCancel").on("click", function(event) {
		var url = BASE_URL + "role/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$(".btnSaveAdd").on('click', function(event) {
		if($(".j-form-validate").valid()){
			var values = $("#id-form-edit").serializeArray();
			var id = $("id").val();
			var url = "role/add";
			console.log(url);
			ajaxSubmit(url, values, event);
		}
	});
});





