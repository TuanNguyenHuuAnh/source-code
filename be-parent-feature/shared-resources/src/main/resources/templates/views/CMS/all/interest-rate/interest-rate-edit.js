$(document).ready(function($) {
	
	$('#tabLanguage a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	
	
	//on click cancel
	$('#cancel').on('click', function(event) {
		event.preventDefault();
		var cityId = $("#cityId").val();
		var url = BASE_URL + "interest-rate/list?cityId=" + cityId;

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click list
	$('#linkList').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "interest-rate/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click save button
	$('#btnSave').on('click', function(event) {
		updateElementEditor();
		event.preventDefault();
		if ($(".j-form-validate").valid()) {
			
			var url = "interest-rate/edit";
			var condition = $(".j-form-validate").serialize();
			console.log(condition);

			ajaxSubmit(url, condition, event);
		}
	});
	
	initFileUploader();
	function initFileUploader() {
		var uploadUrl = BASE_URL + "interest-rate/upload-excel";
		var files = 'filePick';
		var fileContainer = 'fileContainer';
		var fileMaxFileSize = '100mb';
		var fileMimeTypes = [ {
			title : "Image files",
			extensions : "*"
		} ];
		var resize = {
			width : 1366,
			height : 320
		};
		var fileFileList = 'fileFilelist';
		var fileConsole = 'fileConsole';
		var fileFileUploaded = function(up, file, info) {
			//console.log(info.response);
        	//$('#tabExchangeRate a[href="#updateExchangRate"]').tab('show');
        	var content = $(info.response).find('.body-content');
    		$(".main_content").html(content);
    		
    		var urlPage = $('#url').val();
    		console.log(urlPage);
    		if (urlPage != null && urlPage != '') {
    			window.history.pushState('', '', BASE_URL + urlPage);
    		}
		};

		var fileUploadComplete = function(up, files) {
		};
		InitPlupload(files, fileContainer, uploadUrl, false, fileMaxFileSize, fileMimeTypes, fileFileList,
				fileConsole, fileFileUploaded, fileUploadComplete, BASE_URL);
	}
	
	
	$(".j-required-number").keypress(function(e){
		  return isNumber(e,this);
	});

});


/**
 * update element editor
 */
function updateElementEditor() {
	for (instance in CKEDITOR.instances) {
		CKEDITOR.instances[instance].updateElement();
	}
}
