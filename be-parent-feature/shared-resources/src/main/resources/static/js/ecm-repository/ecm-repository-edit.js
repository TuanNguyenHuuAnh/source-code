$(document).ready(function() {	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "ecm-repository/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "ecm-repository/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "ecm-repository/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	
	// Post edit save
	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "ecm-repository/edit";
			var condition = $("#form-repository-edit").serialize();
			ajaxSubmit(url, condition, event);
		}			
	});
	$('#btnValidate').on('click', function() {

		var postedData = {
			id: $('#id').val(),
			host: $('#host').val(),
			username: $('#ecmUsername').val(),
			password: $('#ecmPassword').val(),
			site: $('#site').val()
		}

		$.ajax({
			type : "POST",
			url : BASE_URL + "ecm-repository/ValidateConnection/",
			data : postedData,
			success : function(data) {
				var responseData = JSON.parse(data)
				if (responseData != undefined && responseData.messages != undefined && responseData.messages.length > 0) {
					popupAlert(responseData.messages[0].content)
				} else {
					popupAlert("Error")
				}
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});
	})
});