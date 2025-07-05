$(function(){

	var boxMain = 'fmUpload';
	var fileFilelist = 'fileFilelist';
	var fileConsole = 'fileConsole';
	var filePick = 'filePick';
	var fileContainer = 'fileContainer';
	
	initFileUploader(CONTROLLER_URL, boxMain, filePick, fileContainer, fileFilelist, fileConsole, function(out){
	})
    $("#btnTemplateDownload").unbind().bind('click', function(e){
      var uploadUrl = BASE_URL + CONTROLLER_URL + "/download-template-excel";
      makePostRequest(uploadUrl, {});
    })
	$('#cancel, #backTo').on('click', function(event) {
        event.preventDefault();

        popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
            if (result) {
                var url = BASE_URL  + "personal/document/list";
                // Redirect to page list
                ajaxRedirectWithCondition(url, setConditionSearchImport());
            }
        })
    });
	let pathname = window.location.pathname + window.location.search;
   	pathname = pathname.replace("aa-edit-movement-import", "aa-search-movement-import");
    $.ajax({
    	type  : "GET",
    	url   : BASE_URL + "menu/breadcrumb",
    	data  : {'pathname' : pathname},
   		complete: function (data) {
   			$("#breadcrumb").html(data.responseText);
       }
    });
    $("#btnErrorDownload").unbind().bind('click', function(e){
        e.preventDefault();
        let fileName = $('#fileName').val();
        if (fileName === undefined || fileName == null || fileName === ""){
            var linkExport = BASE_URL + CONTROLLER_URL + "/export-excel";
            let condition = {};
            condition["sessionKey"] = $('#sessionKey').val();
            doExportExcelWithToken(linkExport, "token", condition);
        }else{
            var linkExport = BASE_URL + CONTROLLER_URL + "/download-template-error-excel-with-file-name";
            let condition = {};
            condition["fileName"] = fileName;
            condition["token"] = "token";
            makePostRequest(linkExport, condition);
        }
    })
});

function initFileUploader(CONTROLLER_URL, boxMain, filePick, fileContainer, fileFileList, fileConsole, callback) {
	var uploadUrl = BASE_URL + CONTROLLER_URL + "/upload-template-excel-check-data";
   // var uploadUrl = BASE_URL + CONTROLLER_URL + "/upload-template-excel";
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
		console.log($("#error").val())
        if ($("#error").val() !== ""){
            $('#' + fileConsole).html($("#error").val());
            $("#" + fileConsole).show();
            var errorMes = $("#error").val();
            if (errorMes == ERROR_FILE_NOT_TEMPLATE || errorMes == ERROR_FILE_NOT_DATA){
                $('#form-download-template').remove();
            }
            $('#btnSave').hide();
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
			"fieldSearch" : $('#fieldSearchHidd').val() !== undefined ? $('#fieldSearchHidd').val() : "",
			"fieldValues" : $('#fieldValuesHidd').val() !== undefined ? $('#fieldValuesHidd').val() : "",
			"channel" : $('#fieldValuesHidd').val() !== undefined ? $('#channel').val() : "",
			"sessionKey" : $('#sessionKey').val() !== undefined ? $('#sessionKey').val() : "",
			"searchDto"	  : $('#' + boxMain).serialize()
		});
	};

	InitImportFileByChunKForMulti(files, fileContainer, uploadUrl, false, fileMaxFileSize, fileMimeTypes, fileFileList,
			fileConsole, fileFileUploaded, fileUploadComplete, BASE_URL, fileUploadProgress, boxMain, uploadFile);
}
function setConditionSearchImport(){
    let condition = {};
    condition["sessionKey"] = $('#sessionKey').val();
    return condition;
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

		multipart_params : {
			"fieldSearch" : "",
			"fieldValues" : "",
			"channel": "",
			"sessionKey": "",
			"searchDto" : ""
		},

		chunk_size: "3mb",

		filters: {
			max_file_size: max_file_size,
			mime_types: mime_types
		},

		// Flash settings
		flash_swf_url: url + 'static/js/plupload-3.1.2/Moxie.swf',

		// Silverlight settings
		silverlight_xap_url: url + 'static/js/plupload-3.1.2/Moxie.xap',

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
						
						break;
					default:
						document.getElementById(console).innerHTML = "\nError #" + err.code + ": " + err.message;
						$("#" + console).show();
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