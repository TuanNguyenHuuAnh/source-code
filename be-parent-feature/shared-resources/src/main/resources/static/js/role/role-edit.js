$(document).ready(function() {
	$("#linkList").on("click", function(event) {
		var url = BASE_URL + "role/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$("#btnAddRole").on("click", function(event) {
		var url = BASE_URL + "role/add";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$("#btnCancel").on("click", function(event) {
		var url = BASE_URL + "role/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$(".btnSaveUpdate").on('click', function() {
		if($(".j-form-validate").valid()){
			var values = $("#id-form-edit").serializeArray();
			console.log(values);
			var id = $("#id").val();
			var url = "role/edit?id=" + id;
			ajaxSubmit(url, values, event);
		}
		
	});
	
    //on click add
    $("#addNew").on("click", function(event) {
        var url = BASE_URL + "role/add";
        // Redirect to page detail
        ajaxRedirect(url);
    });
});