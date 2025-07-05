

$("#widgetCommentViewAllComment").click(
	function() {
		  $(".widgetCommentHiddenComment").toggle("fast", "swing");
		  $('.widgetApprovalToggleLog').toggle();
});
$("#widgetApprovalViewAllApprover1").click(
		function() {
			  $(".widgetApprovalHiddenApprover1").toggle("fast", "swing");
			  $('.widgetApprovalToggleLog1').toggle();
});
$(".btn-delete").click(function(){
	 $(this).closest("span").attr("style", "display: none");
});

$(".a").click(
		function() {
		   
			var url = BASE_URL + "document/form/detail/view?id=1";
			window.open(url, '_blank'); 
	});
$(".glyphicon-retweet").click(
	function(){
		var version = $(this).closest("tr").find("td").eq(1).text();
		$("#revertTitle").text("Revert to version "+version);
	});
$(".buttonSave").click(
		function(event){
			var dataForm = $("#form-detail").serializeArray();
	    	var filesList = [];
	    	$.each(filesUpload, function( key, value ) {
	    		dataForm.push({"name": key,"value": value});
	    		filesList.push(value);
	    	});
	    	
	    	// Set listIdAttachFiles to dataForm
	    	var attachDiv = $('.j-attach-list').closest(".j-attach");
	    	var attachReference = $(attachDiv).find("input[name='reference']").val();
	    	var attachReferenceId = $(attachDiv).find("input[name='referenceId']").val();
	    	var listIdAttachFiles = $('#tempAttachFileId').val();    	
	    	dataForm.push({"listIdAttachFiles": listIdAttachFiles});

	    	
	    	if(filesList.length > 0){
	    		saveDataAndFile(dataForm);
	    	} else {
	    		saveData(dataForm);
	    	}
});

function saveData(dataForm){
	 var url = "jpm-process/edit";
	    ajaxSubmit(url, dataForm, event);
}

function saveDataAndFile(dataForm) {
	var formData = new FormData();
	
	$.map(dataForm, function(n, i){
        formData.append(n['name'], n['value']);
    });
	
	$.ajax({
		type : "POST",
		enctype: 'multipart/form-data',
		url : BASE_URL + "jpm-process/edit",
		data : formData,
		// prevent jQuery from automatically transforming the data into a query string
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
		success : function(data, textStatus, request) {
			var content = $(data).find('.body-content');
			$(".main_content").html(content);
			
			var urlPage = $(data).find('#url').val();
			if (urlPage != null && urlPage != '') {
				window.history.pushState('', '', BASE_URL + urlPage);
			}
			
			goTopPage();
		},
		error : function(xhr, textStatus, error) {
			console.log(error);
		}
	});
}

			