var ERROR_FILE_EXTENSTION = '[[#{message.error.file.extension.error}]]';
var ERROR_FILE_NAME_EMPTY = '[[#{message.error.file.name.is.empty}]]';
var ERROR_FILE_NOT_TEMPLATE = '[[#{message.error.not.template}]]';
var ERROR_FILE_NOT_DATA = '[[#{message.error.row.is.empty}]]';
var ERROR_IMPORT_DEADLOCK = '[[#{message.error.deadlock}]]';
var ERROR_IMPORT_TIMEOUT = '[[#{message.error.timeout}]]';
var MSG_CONFIRM_BACK = '[[#{F001.N008}]]';
var MSG_CONFIRM_BACK_POLICY_TRANSFER  = '[[#{policy.transfer.F002.N001}]]';
var MSG_EMPTY_TABLE = '[[#{table.emptyTable.label}]]';  
var ERROR_TYPE_EXCEL = '[[#{excel.file.invalid}]]';
$(document).ready(function() {
	var boxMain = 'fmUpload';
	var fileFilelist = 'fileFilelist';
	var fileConsole = 'fileConsole';
	var filePick = 'filePick';
	var fileContainer = 'fileContainer';
	
	initFileUploader(CONTROLLER_URL, boxMain, filePick, fileContainer, fileFilelist, fileConsole, function(out){
	})
})

function initFileUploader(CONTROLLER_URL, boxMain, filePick, fileContainer, fileFileList, fileConsole, callback) {
	//var uploadUrl = BASE_URL + CONTROLLER_URL + "/upload-template-excel";
	var uploadUrl = BASE_URL + CONTROLLER_URL + "/upload-template-excel-check-data";
	var files = filePick;
	var fileContainer = fileContainer;
	var fileMaxFileSize = '100mb';
	var fileMimeTypes = [ {
		title : "Excel files",
		extensions : "xlsx,xls,xlsm"
	} ];
	var resize = {
		width : 1366,
		height : 320
	};
	var fileFileList = fileFileList;
	var fileConsole = fileConsole;
	
	// file upload
	var fileFileUploaded = function(up, file, info) {
		let breadcrumb = $('#breadcrumb').find('span').text();
		var content = $(info.response).find('.body-content');
		
		$(".main_content").html(content);
		$('#breadcrumb').append('<span>' + breadcrumb + '</span>');
		
		if ($('#error').val() !== ""){
			$('#' + fileConsole).html($('#error').val());
			$("#" + fileConsole).show();
			
            var errorMes = $("#error").val();
            if (errorMes == ERROR_FILE_NOT_TEMPLATE || errorMes == ERROR_FILE_NOT_DATA
                || errorMes == ERROR_IMPORT_DEADLOCK || errorMes == ERROR_IMPORT_TIMEOUT
				|| errorMes == ERROR_TYPE_EXCEL){
                $('#form-download-template').remove();
            }
		}
		
		unblockbg();
	};

	// file upload process
	var fileUploadProgress = function(up, files) {
		$("#" + boxMain).find("." + fileConsole).hide();
	    blockbg();
	};
	
	// file upload complete
	var fileUploadComplete = function(up, files, info) {
        
        window.history.pushState('', '', BASE_URL + CONTROLLER_URL + "/list");
        
		unblockbg();
	};
	
	var uploadFile = function (up, file){
		  
		
		up.setOption("params",{
			"fieldSearch" : $('#fieldSearch').val() !== undefined ? $('#fieldSearch').val() : "",
			"fieldValues" : $('#fieldValues').val() !== undefined ? $('#fieldSearch').val() : "",
			"transactionNo" : $('#transactionNo').val() !== undefined ? $('#transactionNo').val() : "",
            "jsonSearch" : getConditionJson('#' + boxMain),
			"searchDto": $('#' + boxMain).serialize()
		});
	}
	InitImportFileByChunKForMulti(files, fileContainer, uploadUrl, false, fileMaxFileSize, fileMimeTypes, fileFileList,
			fileConsole, fileFileUploaded, fileUploadComplete, BASE_URL, fileUploadProgress, boxMain, uploadFile);
}

function getConditionJson(boxMain){
    var condition = $(boxMain).serializeArray();
    
    return JSON.stringify(condition);
}

function InitImportFileByChunKForMulti(browse_button, container, uploadUrl, multi_selection, max_file_size, mime_types,
	filelist, console, FileUploaded, UploadComplete, url, uploadProgress, boxTable, UploadFile) {
	var token = $("meta[name='_csrf']").attr("content");
	var uploader = new plupload.Uploader({
		runtimes: 'html5,flash,silverlight,html4',

		browse_button: browse_button, // id
		container: container,
		// itself

		url: uploadUrl,

		// Resize images on client-side if we can
		resize: {},

		headers: {
			'X-CSRF-TOKEN': token
		},

		multi_selection: multi_selection,

		chunk_size: "3mb",

		filters: {
			max_file_size: max_file_size,
			mime_types: mime_types
		},

		// Flash settings
		flash_swf_url: url + 'static/js/plupload-3.1.2/Moxie.swf',

		// Silverlight settings
		silverlight_xap_url: url + 'static/js/plupload-3.1.2/Moxie.xap',
		
		multipart_params : {
			"fieldSearch" : "",
			"fieldValues" : "",
            "jsonSearch" : "",
			"searchDto" : ""
		},

		init: {
			PostInit: function () {
				// $("#" + boxTable).find("." + filelist).innerHTML = '';
                unblockbg();
			},

			FilesAdded: function (up, files) {
				
				$.ajax({
		              url: BASE_URL + "check-timeout",
		              type: "GET",
		              global : false,
		              async:false,
		              beforeSend: function(request){
		                 var token = $("meta[name='_csrf']").attr("content");
		      		  	  var header = $("meta[name='_csrf_header']").attr("content");
		      		  	  request.setRequestHeader(header, token);
		              },
		              success: function (result) {
		              	//console.log(">>>>> check-timeout: " + result.message);
		              },
		              complete: function (e) {
		              }
		          });
				// multi_selection is false
				if (!multi_selection) {
					// set empty fileList
					$("#" + boxTable).find("." + filelist).empty();
				}
				plupload.each(files, function (file) {
					$("#" + boxTable).find("." + filelist).innerHTML += '<div id="' + file.id + '">' + file.name + ' ('
						+ plupload.formatSize(file.size) + ') <b></b></div>';
				});

				$("#" + boxTable).find("." + filelist).show();
				uploader.start(); // auto start when FilesAdded
			},

			UploadProgress: uploadProgress,

			Error: function (up, err) {
				switch (err.code) {
					case plupload.FILE_EXTENSION_ERROR:
						document.getElementById(console).innerHTML = ERROR_FILE_EXTENSTION;
						$("#" + console).show();
						
						$('#form-download-template').remove();
						
                        unblockbg();
                        
						break;
					default:
						document.getElementById(console).innerHTML = "\nError #" + err.code + ": " + err.message;
						$("#" + console).show();
                        
                        unblockbg();
                        
						break;
				}
			},
			
			UploadFile : UploadFile,
			
			FileUploaded: FileUploaded,
			UploadComplete: UploadComplete
		}
	});

	uploader.init();
}