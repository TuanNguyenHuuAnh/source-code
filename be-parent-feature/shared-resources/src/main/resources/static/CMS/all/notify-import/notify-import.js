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
		var isMultiple = $('#radio4').is(":checked") ? "true" : "";
	    let condition = {};
        condition["isMultiple"] = isMultiple;
		makePostRequest(uploadUrl, condition);
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
    
    // Event listeners for radio buttons
    $('#radio2, #radio4').change(function() {
        renderTableImport();
        if ($('#ch').val() === "tr"){
           clearTableRowsExceptHeader();
        }
        $('#ch').val("");
     
       
    });

    function clearTableRowsExceptHeader() {
          
          $('#fileConsole').text('');
          $('#btnErrorDownload').text('');
    }
    
  
});


function initFileUploader(CONTROLLER_URL, boxMain, filePick, fileContainer, fileFileList, fileConsole, callback) {
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
		var content = $(info.response).find('.body-content').find('.import-content');
		
		$(".import-content-box").html(content);
		$('#breadcrumb').append('<span>' + breadcrumb + '</span>');
        if ($("#error").val() !== ""){
            $('#' + fileConsole).html($("#error").val());
            $("#" + fileConsole).show();
            var errorMes = $("#error").val();
			console.log(errorMes);
			console.log(ERROR_FILE_NOT_TEMPLATE);
            if (errorMes == ERROR_FILE_NOT_TEMPLATE || errorMes == ERROR_FILE_NOT_DATA
                || errorMes == ERROR_IMPORT_DEADLOCK || errorMes == ERROR_IMPORT_TIMEOUT){
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
	var fileUploadComplete = function(up, files, info, object) {
        
       // window.history.pushState('', '', BASE_URL + CONTROLLER_URL + "/list");
       if($('#radio2').is(":checked")) {
			 $('#radio2').prop('checked', true).trigger('change');
		} else {
			 $('#radio4').prop('checked', true).trigger('change');			
		}
		$('#ch').val("tr");
		
		unblockbg();
	};

	
	var uploadFile = function (up, file){
		up.setOption("params",{
			"fieldSearch" : $('#fieldSearchHidd').val() !== undefined ? $('#fieldSearchHidd').val() : "",
			"fieldValues" : $('#fieldValuesHidd').val() !== undefined ? $('#fieldValuesHidd').val() : "",
			"sessionKey" : $('#sessionKey').val() !== undefined ? $('#sessionKey').val() : "",
			"isMultiple": $('#radio4').is(":checked"),
			"searchDto"	  : $('#' + boxMain).serialize()
		}
	)};

	InitImportFileByChunKForMulti(files, fileContainer, uploadUrl, false, fileMaxFileSize, fileMimeTypes, fileFileList,
			fileConsole, fileFileUploaded, fileUploadComplete, BASE_URL, fileUploadProgress, boxMain, uploadFile);
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
			"isMultiple": $('#radio4').is(":checked"),
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

             BeforeUpload: function (up, file) {               
                up.setOption('multipart_params', {
                    ...up.settings.multipart_params,
                    "isMultiple" : $('#radio4').is(":checked")		
                 });

            },
            
			FileUploaded: FileUploaded,
			UploadComplete: UploadComplete
		}
	});

	uploader.init();
	unblockbg();
}

function renderTableImport(){	  
	if ($('#radio2').is(":checked")) {
        // Hide the Title and Content columns
        $("#tblDataTable th:nth-child(4), #tblDataTable th:nth-child(5)").hide(); // Hide header
        $("#tblDataTable td:nth-child(4), #tblDataTable td:nth-child(5)").hide(); // Hide body cells
        if(!$('#btnSave').is(':disabled')
         	&& $('tbody td[colspan="9"]').length > 0) {
			$('#btnSave').prop('disabled', 'disabled');
		}
    } else if ($('#radio4').is(":checked")) {
        // Show the Title and Content columns        
        $("#tblDataTable th:nth-child(4), #tblDataTable th:nth-child(5)").show(); // Show header
        $("#tblDataTable td:nth-child(4), #tblDataTable td:nth-child(5)").show(); // Show body cells
        if(!$('#btnSave').is(':disabled')
         	&& $('tbody td[colspan="9"]').length > 0) {
			$('#btnSave').prop('disabled', 'disabled');
		}
    }
}

