$(document).ready(function() {
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "role/add";
		// Redirect to page add
		ajaxRedirect(url);
	});
	
	$("#linkList").on("click", function(event) {
		var url = BASE_URL + "role/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$("#btnEditRole").on("click", function(event) {
		var id = $("#id").val();
		var url = BASE_URL + "role/edit?id=" + id;
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
	
	$("#btnDeleteRole").on('click', function(event) {
		popupConfirm(MSG_DEL_CONFIRM, function(result) {
			if (result) {
				var values = [];
				console.log(values);
				var id = $("#id").val();
				var url = "role/delete?id=" + id;
				ajaxSubmit(url, values, event);
			}
		});
	});
});





