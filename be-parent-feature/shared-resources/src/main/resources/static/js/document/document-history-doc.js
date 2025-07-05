/**
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */
$(document).ready(function(){
	
	$("#activity-history-document").on("click", ".widgetHistoryDocToggleLog",
		function(event){
			event.preventDefault();
			
			var isFirst = $(this).attr("isFirstClickHistoryDoc");
			if(isFirst != null){
				ajaxLoadHistoryDoc(false);
				$(this).removeAttr("isFirstClickHistoryDoc");
				$('.widgetHistoryDocToggleLog').toggle();
			}else{
				toggleHistoryDoc();
			}
	});	
	
	// button view history.
	$("#activity-history-document").on("click", "#table-history-doc .btnViewHistoryDoc",
		function(){
			var mainFileHistiryId = $(this).closest('tr').find('input').val();
			viewHistoryDocument(mainFileHistiryId);
	});
	
	// button revert
	$("#activity-history-document").on("click", "#table-history-doc .btnRevertDocument",
		function(){
			var mainFileHistoryId = $(this).closest('tr').find('input').val();
			var version = $(this).closest('tr').find('td:eq(1)').html();
			$("#revertToVersion").html(version);
			$("#mainFileHistoryId").val(mainFileHistoryId);
	});
	
	// submit revert form
	$("#activity-history-document").on("click", "#btn-revert-submit",
		function(){	
			var condition = $('#revert-document-form').serializeArray();
			condition.push({name: 'formId', value: FORM_ID});
			condition.push({name: 'id', value: DOC_ID});
				
			var url = "oz-doc-main-file/revert";
	    	ajaxSubmitPopup(url, condition,"#revert-modal" ,event);
	    	goTopPage();
	});
	
	// button download
	$("#activity-history-document").on("click", "#table-history-doc .btnDownloadHistoryDoc",
		function(){
			var mainFileHistiryId = $(this).closest('tr').find('input').val();
			downloadHistoryDocument(mainFileHistiryId);
	});
	
	$(".see-more").on('click', function() {
		var parent = $(this).closest('.task-comment-div');
		parent.find('.task-comment-fully').slideToggle("slow");

		parent.find('.task-comment-not-full').slideToggle("slow");
	});
	
	$('.task-comment-fully').slideToggle("slow");
	
});

function toggleHistoryDoc() {
	$(".widgetHistoryDocHiddenHistoryDoc").toggle("fast", "swing");
	$('.widgetHistoryDocToggleLog').toggle();
}

function ajaxLoadHistoryDoc(isPaging){

	var id =  $("#form-detail #id").val();
	if(id != ''){
		var condition = [];
		condition = {
			"id" : $("#form-detail #id").val(),
			"isPaging" : isPaging
		}
		var table = "#table-history-doc";
		
		$.ajax({
			type: "POST",
			url: BASE_URL + "document/ajax-get-all-history-doc",
			data: condition,
			global: false,
			beforeSend: function(xhrObj) {
		    	var token = $("meta[name='_csrf']").attr("content");
			  	var header = $("meta[name='_csrf_header']").attr("content");
			  	xhrObj.setRequestHeader(header, token);
			},
			success: function(data){
				$("#activity-history-document").html(data);
				if(!isPaging){
					$(".widgetHistoryDocToggleLog").removeAttr("isFirstClickHistoryDoc");
					$('.widgetHistoryDocToggleLog').toggle();
					$(".widgetHistoryDocHiddenHistoryDoc").toggle("fast", "swing");
				}
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});
	}
}
 
 function viewHistoryDocument(id){
 	var url = BASE_URL + "oz-doc-main-file/view-history-file?id=" + id;
 	$.ajax({
			type: "GET",
			url:  url,
			global: false,
		    beforeSend: function(xhrObj) {
		    	var token = $("meta[name='_csrf']").attr("content");
			  	var header = $("meta[name='_csrf_header']").attr("content");
			  	xhrObj.setRequestHeader(header, token);
		    },
			success : function(data, textStatus, request) {
				$("#popup-mainfile").find(".modal-body").html(data);
				$("#popup-mainfile-filenameview").html(FILE_NAME_VIEW);
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});	
 	$('#popup-mainfile').modal('show');
	 
//	 var url = BASE_URL + "oz-doc-main-file/view-history-file?id=" + id;
//	
//	 popupMainFile = window.open(url, 'popup','width='+ screen.availWidth +',height='+ screen.availHeight +',toolbar=no,scrollbars=yes,directories=no, statusbar=no,menubar=no,resizable=no');
//	 
//	 popupMainFile.focus();
 }
 
 function downloadHistoryDocument(id){
	 var condition = {};
     condition["docMainFileHistoryId"] = id;
     doExport("document/download?action=downloadPDF", condition);
 }