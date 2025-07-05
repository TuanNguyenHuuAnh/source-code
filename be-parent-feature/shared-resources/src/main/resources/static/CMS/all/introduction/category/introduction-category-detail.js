$(document).ready(function() {
//	signatureImage();
	
	//on click list
	$("#listCategory").on("click", function(event) {
		var url = BASE_URL + "introduction/category/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click edit
	$("#btnEditCategory").on("click", function(event) {
		var id = $("#id").val();
		var url = BASE_URL + "introduction/category/edit?id=" + id;
		// Redirect to page edit
		ajaxRedirect(url);
	});
	
	//on click delete
	$("#btnDeleteCategory").on("click", function(event) {
		// Redirect to page delete
		deleteCategory(event);
	});
	
	$("#btnCancel").on("click", function(event) {
		var url = BASE_URL + "introduction/category/list";
		// Redirect to page list
		ajaxRedirect(url);
	});

});

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

/**
 * 
 * @param element
 * @param event
 * @returns
 */
function deleteCategory(event) {
	event.preventDefault();
	// Prepare data
	var id = $("#id").val();

	popupConfirm(MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "introduction/category/delete?id=" + id;
			
			// Redirect to page detail
			var values = [];
			ajaxSubmit(url, values, event);		}
	});
}





