var popupMainFile = null;

$(document).ready(function () {
	$('.date').datepickerUnit({
		format: DATE_FORMAT,
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : APP_LOCALE.toLowerCase(),
		todayHighlight : true,
		startDate: new Date(),
		onRender : function(date) {
		}
	});
	initFormType();
    // Back
    $('#btnBack').click(function () {
        back();
    });

    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

    // Save
    $('#btnSaveHead, #btnSave').click(function () {
    	var titleValid = $("#docTitle").valid();
    	var docNameValid = $("#docName").valid();
    	
    	var mainFileValid = true;
    	if( '1' == $('#docType').val()){
    		var mainFileId = $("#mainFileId").val();
    		if(null == mainFileId || 'null' == mainFileId || '' == mainFileId ){
    			mainFileValid = false;
    			if(docNameValid){
    				validator.showErrors({ 'docName' : MES_NOT_YET_SAVE_FILE});
    			}else{
    				validator.showErrors({ 'docName' : MES_ERROR_HASNT_FILE});
    			}
    		}
    	}
    	
    	if (titleValid && docNameValid && mainFileValid) {
	    	$("#action").val("save");
	    	blockbg();
			setTimeout(save, 10);
		}
    });
    
    // Submit
    $('.btn-process-confirm').click(function () {
    	blockbg();
		setTimeout(save, 10);
    });
    
    $("#form-detail").on("click", '#edit-main-file', function () {
    	var formId = $("#frmId").val();
    	var mainFileId = $("input[name='mainFileId']").val();
    	var name = $("input[name='docName']").val();
//		editMainFile(formId, mainFileId, name);
    	editMainFilePopup(formId, mainFileId, name);
    	
    });
    
    $('#btn-cancel').click(function(){
    	window.close();
    });
    /*
    $('#popup-mainfile').on("click", "#btnMainFileSave", function(event){
    	blockbg();
    	setTimeout(saveMainFile, 10);
    });
    */
    $('#frmId').on("select2:selecting" , function (event) {
    	popupConfirm(CONFIRM_LOAD_WORKFLOW_BY_SELECT, function(result) {
    		$('#status').val($('#statusList').val());
    		if (result) {
    			var url = BASE_URL + "document/detail?";
    			FORM_ID = $("#frmId").val();
    	        url += "formId=" + FORM_ID;
    	        var pageMode = $("#mode").val();
    	        if( pageMode ) {
    	        	url += "&mode=" + pageMode;
    	        }
    	        redirect(url);
    		}else {
       			FORM_ID = $("input[name=formId]").val(),
       			$('#frmId').val(FORM_ID).trigger('change');
    		}
    	});
    });
    
    searchCombobox('#memberAssignIds', '', 'account/ajax-get-list-user-select2-by-key',
    	    function data(params) {
    	        var obj = {
    	            key: params.term,
    	            orgId: $("#orgId").val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true, null, templateResult);
    
    searchCombobox('#memberCcIds', '', 'account/ajax-get-list-user-select2-by-key',
    	    function data(params) {
    	        var obj = {
    	            key: params.term,
    	            orgId: $("#orgId").val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true, null, templateResult);
    
    searchCombobox('#memberReferenceIds', '', 'account/ajax-get-list-user-select2-by-key',
    	    function data(params) {
    	        var obj = {
    	            key: params.term,
    	            orgId: $("#orgId").val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true, null, templateResult);
    
 
    searchComboboxExternal('#memberAssignIdsExternal', '', 'account/ajax-get-list-user-select2-by-key',
    	    function data(params) {
	        var obj = {
	            key: params.term,
	            orgId: $("#orgId").val(),
	            isPaging: true
	        };
	        return obj;
	    }, function dataResult(data) {
	        return data;
	    }, true);

    /** ATTACH MAIN FILE FREE FORM/ MULTI_RECRUITING*/
    if($('#multiRecruitingFlag').val()=="true"){
    	setUploadExcel();
    }else{
    	setUploadMainFilePdf();
    }
    
    //dowload PDF
    $("#form-detail").on("click", "#btn-download-pdf", function (event) {
    	
    	var condition = {};

        condition["id"] = $("#mainFileId").val();
    	
        $.ajax({
			type: "POST",
			url: BASE_URL + "document/async/download-pdf?action=downloadPDF",
			data: condition,
			success : function(data, textStatus, request) {
				var msgFlag = request.getResponseHeader("msgFlag");
				if( "1" == msgFlag ) {
					doExport("document/download?action=downloadPDF", condition);
				}else if( "0" == msgFlag ){
					popupAlert(data);
				}
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});	
    });
    
    $('#popup-mainfile').on("click", "#pdf_download", function(event){
    	blockbg();
    	var condition = {};

        condition["id"] = $("#mainFileId").val();
        $.ajax({
			type: "POST",
			url: BASE_URL + "document/async/download-pdf?action=downloadPDF",
			data: condition,
			success : function(data, textStatus, request) {
				var msgFlag = request.getResponseHeader("msgFlag");
				if( "1" == msgFlag ) {
					doExport("document/download?action=downloadPDFAll", condition);
				}else if( "0" == msgFlag ){
					popupAlert(data);
				}
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});	
    });
    
    //checkPopupMainFileToFocus();
    
    initDeleteMainFile();
    
	$('.selectpicker').selectpicker();
	
	//mainfile popup closing callback
	$('#popup-mainfile').on('hidden.bs.modal', function() {
		$(this).find(".modal-body").html("");
	});
	
	// fix Bug #33902
	$('#memberAssignIds').change(function(){
		if($(this).val() != '') {
			$('#memberAssignIds-error').css('display', 'none');
		}
	});

//	$('#summary').keyup(function(e){
//		var valConfirmNote = $(this).val();
//		var valConfirmNoteSpecial = valConfirmNote.match(/(\r\n|\n|\r)/g);
//		var lengthSpecial = 0;
//		
//		if (valConfirmNoteSpecial != null ){
//			lengthSpecial = valConfirmNoteSpecial.length;
//		}
//		
//		var lengthActual = valConfirmNote.length + lengthSpecial
//		//check 4000 character is entered
//		//start
//		if (lengthActual > 4000 ){AREA_MAX_LENGTH_4000
//			$('#summary-error').html(AREA_MAX_LENGTH_4000);
//		//end
//			$('.btn-process-group').attr('disabled', true);
//		}else{
//			$('#summary-error').html('');
//			$('.btn-process-group').attr('disabled', false);
//		}
//	});
	

/*	
	$('#summary').keyup(function(e){
		var valConfirmNote = $(this).val();
		var valConfirmNoteSpecial = valConfirmNote.match(/(\r\n|\n|\r)/g);
		var lengthSpecial = 0;
		
		if (valConfirmNoteSpecial != null ){
			lengthSpecial = valConfirmNoteSpecial.length;
		}
		
		var lengthActual = valConfirmNote.length + lengthSpecial
		if (lengthActual > 1000 ){
			$('#summary-error').html(AREA_MAX_LENGTH_1000);
			$('.btn-process-group').attr('disabled', true);
		}else{
			$('#summary-error').html('');
			$('.btn-process-group').attr('disabled', false);
		}
	});
	*/

//	$('#summary').keyup(function(e){
//		var valConfirmNote = $(this).val();
//		var valConfirmNoteSpecial = valConfirmNote.match(/(\r\n|\n|\r)/g);
//		var lengthSpecial = 0;
//		
//		if (valConfirmNoteSpecial != null ){
//			lengthSpecial = valConfirmNoteSpecial.length;
//		}
//		
//		var lengthActual = valConfirmNote.length + lengthSpecial
//		if (lengthActual > 1000 ){
//			$('#summary-error').html(AREA_MAX_LENGTH_1000);
//			$('.btn-process-group').attr('disabled', true);
//		}else{
//			$('#summary-error').html('');
//			$('.btn-process-group').attr('disabled', false);
//		}
//	});
});

function back() {
    var url = BASE_URL + "document/list";
	var mode = $('#mode').val();
	if(mode !== undefined && mode !== null && mode !== '' && (mode === '3' || mode === 3)) {
		url = BASE_URL + "todo/list";
	} else if (mode === '1' || mode === 1){    	
	    url = BASE_URL + "todo/list";
	} else if (mode === '2' || mode === 2){
	    url = BASE_URL + "document/list";
	}else {
	    url = BASE_URL + "jpm-svc-board/list";
    }
    
    ajaxRedirect(url);
}

function create() {
    var url = BASE_URL + "document/detail?";
    
    FORM_ID = $("#frmId").val();
    url += "formId=" + FORM_ID;
    var docType = $("#docType").val();
    url += "&docType=" + docType;
    var pageMode = $("#mode").val();
    if( pageMode ) {
    	url += "&mode=" + pageMode;
    }
    
    ajaxRedirect(url);
}


function save() {
	  	// Set listIdAttachFiles to dataForm
		var listIdAttachFiles = $('#pluginAttachFiles').val();
		$('#listIdAttachFiles').val(listIdAttachFiles);
		
		var formData = new FormData($('#form-detail')[0]);
		//formData.append("action", "save");
		
		var memberCcIds = $('#memberCcIds').val();		
		if($('#memberCcIds').is(':disabled') && memberCcIds){
			formData.append('memberCcIds', memberCcIds);
		}
		
		var memberAssignIds = $('#memberAssignIds').val();
		if($('#memberAssignIds').is(':disabled') && memberAssignIds){
			formData.append('memberAssignIds', memberAssignIds);
		}
		
		
		var memberReferenceIds = $('#memberReferenceIds').val();
		if($('#memberReferenceIds').is(':disabled') && memberReferenceIds){
			formData.append('memberReferenceIds', memberReferenceIds);
		}
		
		var summary = $('#summary').val();
		if($('#summary').is(':disabled') && summary){
			formData.append('summary', summary);
		}
	    $.ajax({
			type : "POST",
			enctype: 'multipart/form-data',
			url : BASE_URL + "document/detail",
			data : formData,
			// prevent jQuery from automatically transforming the data into a query string
	        processData: false,
	        contentType: false,
	        cache: false,
	        timeout: 1000000,
			success : function(data, textStatus, request) {
				var action = $("#action").val();
				var content = $(data).find('.body-content');
				$(".main_content").html(content);
				$('#pluginAttachFiles').val(listIdAttachFiles);
				var urlPage = $(data).find('#url').val();
				if (urlPage != null && urlPage != '') {
					window.history.pushState('', '', BASE_URL + urlPage);
				}
				goTopPage();
			},
			error : function(xhr, textStatus, error) {
				reject('Fail to ajaxSubmitOZDoc');
			}
		});
	    
    	unblockbg();

//        return new Promise(function(resolve, reject) {
//        	exportStreamOzd(formData)
//        		.then(getOzDocDto)
//        		.then(ajaxSubmitOZDoc)
//    			.then(function() {
//    				unblockbg();
//    				
//                	return resolve('Process complete');
//                })
//    			.catch(function(error) {
//    				unblockbg();
//
//    				showMsgError('#alertDiv', error);
//                    return reject(error);
//                });
//         });
}

function getOzDocDto(formData) {
	//console.log("getOzDocDto");
	
	return new Promise(function(resolve, reject) {
		// get json data
		var inputJson = OZViewer.GetInformation("INPUT_JSON_ALL", function(json_data){
		
		});
		
		var validJson = OZViewer.GetInformation("INVALID_INFO_JSON");
//		console.log(validJson);
		var unindexed_array = $("#form-mainfile").serializeArray();
		$.map(unindexed_array, function(n, i){
	        formData.append(n['name'], n['value']);
	    });
		formData.append('docInputJson', inputJson);
		//if browser is IE or else
		if(navigator.userAgent.toUpperCase().includes('.NET')) {
			formData.append('validJson', validJson);
		} else {
			formData.set('validJson', validJson);
		}
		
		resolve(formData);
	});
}

/**
 * ajax submit method POST
 * @param formData
 * 			type FormData
 */
function ajaxSubmitOZDoc(formData) {
	var token = $("meta[name='_csrf']").attr("content");
  	var header = $("meta[name='_csrf_header']").attr("content");
	formData.append('_csrf', token);
	formData.append('_csrf_header', header);

	return new Promise(function(resolve, reject) {
		$.ajax({
			type : "POST",
			enctype: 'multipart/form-data',
			url : BASE_URL + "document/detail",
			data : formData,
			// prevent jQuery from automatically transforming the data into a query string
	        processData: false,
	        contentType: false,
	        cache: false,
	        timeout: 1000000,
			success : function(data, textStatus, request) {
				var isAlert = request.getResponseHeader('isAlert');
				
				if( "1" == isAlert ) {
					$("#alertDiv").html(data);
				} else {
					var content = $(data).find('.body-content');
					$(".main_content").html(content);
					
					var urlPage = $(data).find('#url').val();
					if (urlPage != null && urlPage != '') {
						window.history.pushState('', '', BASE_URL + urlPage);
					}
				}
				
				goTopPage();
				
				resolve('AjaxSubmitOZDoc complete');
			},
			error : function(xhr, textStatus, error) {
				reject('Fail to ajaxSubmitOZDoc');
			}
		});
	});
}

function editMainFile(formId, docMainFileId, name){
	var url = BASE_URL + "oz-doc-main-file/view-file?formId=" + formId;
	if(docMainFileId != null && docMainFileId != ''){
		url += "&id="+docMainFileId;
	}
	
	popupMainFile = window.open(url, '_blank','channelmode=yes,location=no,toolbar=no,scrollbars=yes,directories=no,titlebar=no,statusbar=no,menubar=no,resizable=no');	
	onLoadPopupMainFile();
	popupMainFile.focus();
	// full-screen for chrome (don't support channelmode)
	if(/chrom(e|ium)/.test(navigator.userAgent.toLowerCase())){
		popupMainFile.moveTo(0, 0);
		popupMainFile.resizeTo(screen.availWidth, screen.availHeight);
	}
}

function editMainFilePopup(formId, mainFileId, name){
	var url = BASE_URL + "oz-doc-main-file/view-file?formId=" + formId;
	if(mainFileId != null && mainFileId != ''){
		url += "&id=" + mainFileId;
		url += "&isArchive=" + IS_ARCHIVE;
	}
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
			$('#popup-mainfile').modal('show');
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});	
}

function saveMainFile(){
	var formData = new FormData();
	return new Promise(function(resolve, reject) {
		exportStreamOzd(formData)
			.then(getOzDocDto)
	  		.then(ajaxSubmitMainFile)
			.then(function() {
				unblockbg();
				
	          	return resolve('Process complete');
	          })
			.catch(function(error) {
				unblockbg();
	
				showMsgError('#alertDiv', error);
	              return reject(error);
	          });
	   });
}

function ajaxSubmitMainFile(formData) {
	var token = $("meta[name='_csrf']").attr("content");
  	var header = $("meta[name='_csrf_header']").attr("content");
	formData.append('_csrf', token);
	formData.append('_csrf_header', header);

	return new Promise(function(resolve, reject) {
		$.ajax({
			type : "POST",
			enctype: 'multipart/form-data',
			url : BASE_URL + "oz-doc-main-file/ajax/edit",
			data : formData,
			// prevent jQuery from automatically transforming the data into a query string
	        processData: false,
	        contentType: false,
	        cache: false,
	        timeout: 1000000,
			success : function(data, textStatus, request) {
				var isAlert = request.getResponseHeader('isAlert');
				
				if( "1" == isAlert ) {
					$("#alertDiv").html(data);
				} else {
					$("#popup-mainfile").find(".modal-body").html(data);
//					var content = $(data).find('.body-content');
//					$(".main_content").html(content);
					
//					var urlPage = $(data).find('#url').val();
//					if (urlPage != null && urlPage != '') {
//						window.history.pushState('', '', BASE_URL + urlPage);
//					}
				}
				
				var mainFileId = $(data).find("input[name='id']").val();
				var fileNameView = $(data).find("input[name='fileNameView']").val();
				var mainFileMajorVer = $(data).find("input[name='majorVersion']").val();
				var mainFileMinorVer = $(data).find("input[name='minorVersion']").val();
				var validJson = $(data).find("input[name='validJson']").val();
//		    	opener.updateIdMainFile(mainFileId, fileNameView,mainFileMajorVer,mainFileMinorVer,validJson);
		    	updateIdMainFile(mainFileId, fileNameView,mainFileMajorVer,mainFileMinorVer,validJson);
		    	
				goTopPage();
				resolve('AjaxSubmitMainFile complete');
			},
			error : function(xhr, textStatus, error) {
				reject('Fail to ajaxSubmitMainFile');
				goTopPage();
			}
		});
	});
}


function updateIdMainFile(mainFileId, fileNameView ,mainFileMajorVer, mainFileMinorVer, validJson){
	$("input[name='mainFileId").val(mainFileId);
	$("input[name='docName").val(fileNameView);
	$("input[name='mainFileMajorVersion").val(mainFileMajorVer);
	$("input[name='mainFileMinorVersion").val(mainFileMinorVer);
	$("input[name='validJson").val(validJson);
	var isPaging = true;
	ajaxLoadHistoryDoc(isPaging);	
}

function checkPopupMainFileToFocus(){
	var refresh=1000; // Refresh rate in milli seconds
	mytime=setTimeout('focusPopupMainFile()',refresh)
}

function focusPopupMainFile() {
	if ( document.hasFocus() ) {
		if( popupMainFile ) {
			popupMainFile.focus();
		}
	}
	
	checkPopupMainFileToFocus();
}

function setUploadMainFilePdf(){
	 $('#form-detail').fileupload({
	    	url: BASE_URL+ "oz-doc-main-file/ajax/up-main-file-free-from",
	        type: 'POST',
	        dataType: 'json',
	        formAcceptCharset: 'utf-8',
	        limitMultiFileUploadSize: 2000000, // 10M
	        limitMultiFileUploadSizeOverhead: 2000000, // 10M
	        multipart: false,
	        maxChunkSize: 10000000,
	        sequentialUploads: true,
	        singleFileUploads: true,
	        add: function (e, data) {
	        	//validation
	        	var imageConsoleEl = $(this).find('.alert-' + data.paramName);
	            $(imageConsoleEl).hide();
	            var acceptFileTypes = /([a-zA-Z0-9\s_\\.\-\(\):])+(.pdf)$/i;
	            var fileExtend = data.originalFiles[0].name.split('.').pop();
	            var fileName = data.files[0].name;
//	            console.log(fileExtend);
	            if (fileExtend.toLowerCase() !== 'pdf') {
	                $(imageConsoleEl).text(NOT_AN_ACCEPTED_FILE_TYPE+' (*.pdf)')
	                $(imageConsoleEl).show();
	                return;
	            }
	            if (data.originalFiles[0]['size'] > MAX_SIZE_MAIN_FILE) {
	                $(imageConsoleEl).text(FILESIZE_IS_BIGGER + ' ' + (MAX_SIZE_MAIN_FILE/1048576).toFixed(2) +'MB!')
	                $(imageConsoleEl).show();
	                return;
	            }
	            if(fileName.length > 100) {
	        		var msgFileName = FILENAME_IS_MAX_LENGTH;
	        		$(imageConsoleEl).text(msgFileName)
	                $(imageConsoleEl).show();
	        		return;
	        	}
	        	
	        	var formData =  new FormData();
	        	var mainFileId = $("input[name='mainFileId']").val();
	        	
	        	formData.append('file',  data.files[0]);
	        	formData.append('mainFileId', mainFileId);
	        	if(null != DOC_ID && 'null'!=DOC_ID){
	        		formData.append('docId', DOC_ID);
	        	}
	        	
	        	var majorVersion = $("input[name='mainFileMajorVersion']").val();
	        	var minorVersion = $("input[name='mainFileMinorVersion']").val();
	        	formData.append('majorVersion', majorVersion);
	        	formData.append('minorVersion', minorVersion);
	        	//form name + process name for header + footer of file
	        	var formName = $('#frmId').text().trim();
	        	var processName = $('#processDeployName').val();
	        	formData.append('formName', formName);
	        	formData.append('processName', processName);
	        	
	        	$.ajax({
	    			type : "POST",
	    			enctype: 'multipart/form-data',
	    			url :  BASE_URL+ "oz-doc-main-file/ajax/up-main-file-free-from" ,
	    			data : formData,
	    			// prevent jQuery from automatically transforming the data into a query string
	    	        processData: false,
	    	        contentType: false,
	    	        cache: false,
	    	        timeout: 1000000,
	    			success : function(data, textStatus, request) {
	    				var msgFlag = request.getResponseHeader("msgFlag");
	    				if( "1" == msgFlag ) {
	    					$(imageConsoleEl).text(NOT_AN_ACCEPTED_FILE_TYPE+' (*.pdf)')
	    	                $(imageConsoleEl).show();
	    				}else if( "2" == msgFlag ){
	    					msgClientShow(UPLOAD_FILE_FAIL, 'error');
	    				}else if( "4" == msgFlag){
	    	                msgClientShow(MES_VERSION_OLD, 'error');
	    				}else {
		    				var oldMainFileId = $("#mainFileId").val();
		    				var mainFileId = data.id;
		    				var fileNameView = data.fileNameView;
		    				var mainFileMajorVer = data.majorVersion;
		    				var mainFileMinorVer = data.minorVersion;
		    				
		    				updateIdMainFile(mainFileId, fileNameView,mainFileMajorVer,mainFileMinorVer, "");
							
							if(oldMainFileId == ''){
								$('.btn-file-action').removeClass('hidden');
							}
	    	                
	    	                if("3" == msgFlag){
	    	                	var isPaging = true;
	    	                	ajaxLoadHistoryDoc(isPaging);	
	    	                }
	    	                msgClientShow(MES_SUCCESS_UPLOAD_FILE);
	    				}
	    			},
	    			error : function(xhr, textStatus, error) {
	    				console.log(xhr);
	    				console.log(textStatus);
	    				console.log(error);
	    			}
	    		});
	        },
	    });
}

function initDeleteMainFile(){
	$("#form-detail").on("click", "#delete-main-file", function (event) {
		popupConfirm(MSG_DEL_CONFIRM, function (result) {
			if(result){
				var imageConsoleEl = $(this).find('.alert-free-form-attach-main-file');
				$.ajax({
					type : "POST",
					enctype: 'text/plain',
					url :  BASE_URL+ "oz-doc-main-file/delete" ,
					data : {
						mainFileId : $("#mainFileId").val()
					},
					success : function(data, textStatus, request) {
						var msgFlag = request.getResponseHeader("msgFlag");
						if("1" == msgFlag){
							$(".btn-file-action").addClass("hidden");
							$("#mainFileId").val("");
							$("#docName").val("");
						}else{
							$(imageConsoleEl).text("Delete file fail");
			                $(imageConsoleEl).show();
						}
					},
					error : function(xhr, textStatus, error) {
						$(imageConsoleEl).text("Delete file fail");
		                $(imageConsoleEl).show();
					}
				});
			}
		});
	});
}

function initFormType() {
	searchCombobox('#frmId', '', 'form/get-list-for-user-ignoge-integrate',
		    function data(params) {
		        var obj = {
		            keySearch: params.term,
		            companyId: $('#companyId').val(), 
		            isPaging: true
		        };
		        return obj;
		    }, function dataResult(data) {
		        return data;
		    }, false);
}

//MultiRecruiting
function setUploadExcel() {
	$('#form-detail').fileupload({
		url: BASE_URL+ "oz-doc-main-file/ajax/up-main-file-import-excel",
	    type: 'POST',
	    dataType: 'json',
	    formAcceptCharset: 'utf-8',
	    limitMultiFileUploadSize: 2000000, // 10M
	    limitMultiFileUploadSizeOverhead: 2000000, // 10M
	    multipart: false,
	    maxChunkSize: 10000000,
	    sequentialUploads: true,
	    singleFileUploads: true,
	    add: function (e, data) {
	    	//validation
	    	var imageConsoleEl = $(this).find('.alert-' + data.paramName);
	        $(imageConsoleEl).hide();
	        var fileExtend = data.originalFiles[0].name.split('.').pop();
	        var fileName = data.files[0].name;
//	        console.log(fileExtend);
	        /*if (fileExtend.toLowerCase() !== 'pdf') {
	            $(imageConsoleEl).text(NOT_AN_ACCEPTED_FILE_TYPE+' (*.pdf)')
	            $(imageConsoleEl).show();
	            return;
	        }*/
	        var acceptFileTypes = new RegExp(EXCEL_TYPE);
            if (data.originalFiles[0]['type'].length <= 0|| !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                $(imageConsoleEl).text(NOT_AN_ACCEPTED_FILE_TYPE+' (Excel)');
                $(imageConsoleEl).show();
                return;
            }
	        if (data.originalFiles[0]['size'] > MAX_SIZE_MAIN_FILE) {
	            $(imageConsoleEl).text(FILESIZE_IS_BIGGER + ' ' + (MAX_SIZE_MAIN_FILE/1048576).toFixed(2) +'MB!');
	            $(imageConsoleEl).show();
	            return;
	        }
	        if(fileName.length > 100) {
	    		var msgFileName = FILENAME_IS_MAX_LENGTH;
	    		$(imageConsoleEl).text(msgFileName);
	            $(imageConsoleEl).show();
	    		return;
	    	}
	        
	        var formData =  new FormData();
        	var mainFileId = $("input[name='mainFileId']").val();	    	
	    	formData.append('file',  data.files[0]);
	    	formData.append('mainFileId', mainFileId);
	    	if(null != DOC_ID && 'null'!=DOC_ID){
	    		formData.append('docId', DOC_ID);
	    	}
	    	
	    	$.ajax({
				type : "POST",
				enctype: 'multipart/form-data',
				url :  BASE_URL+ "oz-doc-main-file/ajax/up-main-file-import-excel" ,
				data : formData,
				// prevent jQuery from automatically transforming the data into a query string
		        processData: false,
		        contentType: false,
		        cache: false,
		        timeout: 1000000,
				success : function(data, textStatus, request) {
					var msgFlag = request.getResponseHeader("msgFlag");
					if( "1" == msgFlag ) {
						errorAlert(data);
					}else if( "2" == msgFlag ){
						$(imageConsoleEl).text(UPLOAD_FILE_FAIL);
		                $(imageConsoleEl).show();
					}else{
						JSON_DATA = data;
						$("#upload-excel").addClass('hidden');
						$("#edit-main-file").removeClass('hidden');
				        $('#docName').val(fileName);

				        $("#edit-main-file").trigger('click');	
					}
				},
				error : function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				}
			});
	    },
	});
}

function errorAlert(msg) {
	var content = "";
	var msgs =  msg.split("\r\n");
	for (var i in msgs){
		content += "<span>" + msgs[i] + "</span>";
	}
	$('#popup-error').find('#error-content').html(content);
	$('#popup-error').modal('show');
}

function onLoadPopupMainFile() {
	$(popupMainFile).on('beforeunload',function() { 
		unblockbg();
	})
	popupMainFile.JSON_DATA = JSON_DATA;
	popupMainFile.IS_SAVE_EFORM = IS_SAVE_EFORM;
}

/** search combobox not call ajax support crnew external hdbank */
function searchComboboxExternal(element, placeholder, url, data, dataResult, allowClear) {
	if (allowClear === undefined) {
		allowClear = true;
	}
	var token = $("meta[name='_csrf']").attr("content");
  	var header = $("meta[name='_csrf_header']").attr("content");
	$(element).select2({
		placeholder : placeholder,
		minimumInputLength : 0,
		allowClear : allowClear,
		templateResult: templateResult,
		language: {
	      noResults: function() {
	        return SELECT2_NO_RESULTS;
	      },
	    },
	});
}

/** select2 templateResult*/
function templateResult(state) {
	if(state.disabled) {
		return state.name;
	}
	var positionName = state.positionName;
	if(typeof positionName === 'undefined') {
		positionName = state.element.attributes.positionname.value;
	}
	var strState = '<span><b>' + state.text + '</b></span><br/>';
	if(null != positionName && '' != positionName){
		strState += '<span><i>' + positionName + '</i></span><br/>';
	}
	var $state = $(strState);
	
	return $state;
}