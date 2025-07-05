//
//opener.onLoadPopupMainFile();
//opener.blockbg();
//var INPUT_JSON = window.parent.INPUT_JSON
$(document).ready(function(){
	$('#btnMainFileSave').click(function () {
		blockbg();
    	setTimeout(saveMainFile, 10);
    });
//	var isCompleted = $("#isCompleted").val();
//	var formFileName = $("#formFileName").val();
//	var docId = $("#docId").val();
//	var urlOzViewer = BASE_URL + "oz-doc-main-file/oz-viewer";
//	urlOzViewer += "?formFileName=" + formFileName;
//	urlOzViewer += "&docId=" + docId;
//	urlOzViewer += "&isCompleted=" + isCompleted;
//	$("#ozViewerIframe").attr("src", urlOzViewer);
//	
//	$.datepicker.setDefaults({
//		changeMonth: true,
//        changeYear: true,
//        showAnim: "slideDown",
//        autoclose: false
//	});
})