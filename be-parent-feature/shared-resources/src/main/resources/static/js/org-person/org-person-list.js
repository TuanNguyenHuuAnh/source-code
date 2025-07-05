$(document).ready(function() {
	
	// on click add
	$("#addNew").on("click", function(event) {
		var url = BASE_URL + "organization-person/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

});

function loadTable(url, methodType, data) {
    $.ajax({
		type : methodType,
		url : BASE_URL + url,
		global : false,
		beforeSend : function(xhrObj) {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			xhrObj.setRequestHeader(header, token);
		},
		data : data,
		success : function(data) {
			var content = $(data).find('.body-content');
			$(".main_content").html(content);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}


