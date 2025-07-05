$(document).ready(function() {
//	signatureImage();
	
	//on click list
	$("#listIntroduction").on("click", function(event) {
		var url = BASE_URL + "introduction/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click edit
	$("#btnEditIntroduction").on("click", function(event) {
		var id = $("#id").val();
		var url = BASE_URL + "introduction/edit?id=" + id;
		// Redirect to page edit
		ajaxRedirect(url);
	});
	
	$("#btnCancel").on("click", function(event) {
		var url = BASE_URL + "introduction/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click delete
	$("#btnDeleteIntroduction").on("click", function(event) {
		// Redirect to page delete
		deleteIntroduction(event);
	});
	
	$('#btnApprove').on('click', function(event) {
		event.preventDefault();
		$("textarea[id='comment']").removeClass("j-required");
		if ($("#validateform").valid()) {

			var values = $("#validateform").serializeArray();
			var url = "introduction/detail/approve";
			ajaxSubmit(url, values, event);
		}
	});
	
	$('#btnSubmit').on('click', function(event) {
		event.preventDefault();		
		if ($("#validateform").valid()) {

			var values = $("#validateform").serializeArray();
			var url = "introduction/detail/submit";
			ajaxSubmit(url, values, event);
		}
	});
	
	$('#btnReject').on('click', function(event) {
		event.preventDefault();
		$("textarea[id='comment']").addClass("j-required");
		if ($("#validateform").valid()) {

			var values = $("#validateform").serializeArray();
			var url = "introduction/detail/reject";
			ajaxSubmit(url, values, event);
		}
	});

});

/**
 * 
 * @param element
 * @param event
 * @returns
 */
function deleteIntroduction(event) {
	event.preventDefault();
	// Prepare data
	var id = $("#id").val();

	popupConfirm(MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = BASE_URL + "introduction/delete?id=" + id;
			
			// Redirect to page detail
			ajaxRedirect(url);
		}
	});
}

/**
 * IMAGE signature
 */
//function signatureImage() {
//
//	var image = $("#imageUrl").val();
//	if (image != "") {
//		$("#image_preview").attr("src", BASE_URL + "ajax/download?fileName=" + image);
//		$("#image_preview").removeClass('hide');
//	}
//}




