$(document).ready(function () {
    // Back
    $('#btnBack, #btnCancel').click(function () {
        back();
    });

    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

//    // Save
//    $('#btnSaveHead, #btnSave').click(function () {
//        save();
//    });
    
//    
//    if(tabError!=""){
//    	$('#tabLanguage a[href="#language' + tabError +'"]').tab('show');
//    }
    
    $("#companyId").on('change', function(event){
    	var val = $(this).val();
    	var appCode = $("#appCode").val();
        ajaxUserGuideByCompanyId(val,appCode, event);
    });
    
    $("#appCode").on('change', function(event){
    	var val = $(this).val();
    	var companyId = $("#companyId").val();
        ajaxUserGuideByCompanyId(companyId,val ,event);
    });
    
    $(".j-delete-file").click(function (event) {
    	//event.preventDefault();
    	 var id = $(this).data("file-id");
    	 var condition = {};
    	 condition["id"] = $("#idUserGuide").val();
    
        deleteUserGuide(id, condition);
    });
    // Save
    $('#btnSaveHead, #btnSave').click(function (event) {
    	if ($(".j-form-validate").valid()) {
	    	var dataForm = $("#form-detail").serializeArray();

	    	var filesList = [];
	    	$.each(filesUpload, function( key, value ) {
	    		dataForm.push({"name": key,"value": value});
	    		filesList.push(value);
	    	});
	    	saveDataAndFile(dataForm);
    	}else{
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
		}	
    });
	// show tab if exists error
	showTabError(LANGUAGE_LIST);
    
    //dowload PDF
    	$('.btn-download-user-guide').click(function (event) {
    	
    	var condition = {};
    	var id = $(this).data("file-id");
        condition["id"] = id;
        exportFileByFileId(condition);
		});	
    
	var filesUpload = {};
    var file_upload = $('.fileUploadUs').fileupload({
        url: BASE_URL + "user-guide/add",
        type: 'POST',
        dataType: 'text/html;charset=UTF-8',
        formAcceptCharset: 'utf-8',
        limitMultiFileUploadSize: 1000000, // 10M
        limitMultiFileUploadSizeOverhead: 1000000, // 10M
        multipart: true,
        maxChunkSize: 10000000,
        sequentialUploads: true,
        singleFileUploads: false,
        add: function (e, data) {
          //validation
            var acceptFileTypes = /([a-zA-Z0-9\s_\\.\-\(\):])+(.pdf)$/i;
            var fileExtend = data.originalFiles[0].name.split('.').pop();
            var fileName = data.files[0].name;
            
        	if( !$("#alertAttachedFileClientId").hasClass("hidden") ) {
        		$("#alertAttachedFileClientId").addClass("hidden");
        	}
            if (fileExtend.toLowerCase() !== 'pdf') {
            	$("#alertAttachedFileClientId").removeClass("hidden");
        		$("#msgAlertAttachedFileClientId").html(NOT_AN_ACCEPTED_FILE_TYPE+' (*.pdf)');
                return;
            }
            if (data.originalFiles[0]['size'] > 20971520) {
            	$("#alertAttachedFileClientId").removeClass("hidden");
        		$("#msgAlertAttachedFileClientId").html(FILESIZE_IS_BIGGER + ' 20MB');
                return;
            }
            if(fileName.length > 100) {
        		var msgFileName = FILENAME_IS_MAX_LENGTH;
        		$("#alertAttachedFileClientId").removeClass("hidden");
        		
        		$("#msgAlertAttachedFileClientId").html(msgFileName);
        		return;
        	}
            filesUpload[e.delegatedEvent.target.name] = data.files[0];
            $(this).find('.fileName').val( data.files[0].name);
//            $(this).parent().find('#fileUserGuideType').val(data.paramName);
        },
        done: function (e, data) {
        	console.log("done");
        	console.log(data.result);
        },
        fail: function (e, data) {
        	console.log("fail");
        	console.log(data.result);
        }
    })
});

function back() {
    var url = BASE_URL + "user-guide/detail";
    ajaxRedirect(url);
}

function create() {
    var url = BASE_URL + "user-guide/detail";
    ajaxRedirect(url);
}

//function save() {
//    if ($(".j-form-validate").valid()) {
//        var values = $("#form-detail").serialize();
//        var url = "category/save";
//        ajaxSubmit(url, values, event);
//    }else{
//		// show tab if exists error
//		showTabError(LANGUAGE_LIST);
//	}
//    
//}

function ajaxUserGuideByCompanyId(companyId,appCode,event) {
	var url = "user-guide/detail";
	var condition = {};
	condition["companyId"] = companyId;
	condition["appCode"] = appCode;
	$.ajax({
        type : "GET",
        url : BASE_URL + "user-guide/detail",
        data : {
        	'companyId': 	companyId,
        	'appCode': 		appCode,
        },
        success : function(data, textStatus, request) {
        	
        	var content = $(data).find('.body-content');
			$(".main_content").html(content);
        },
        error : function(xhr, textStatus, error) {
            console.log(xhr);
            console.log(textStatus);
            console.log(error);
        }
    });
}

function saveDataAndFile(dataForm) {
	var formData = new FormData();
	
	$.map(dataForm, function(n, i){
        formData.append(n['name'], n['value']);
    });
		$.ajax({
			type : "POST",
			enctype: 'multipart/form-data',
			url : BASE_URL + "user-guide/add",
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

function exportFileByFileId(attachDto){
	doExport("user-guide/download", attachDto);
}

function deleteUserGuide(id, condition){
	popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            var url = BASE_URL + "user-guide/delete";
            var data = {
                "id": id,
                "condition" :condition
            }
            makePostRequest(url, data);
        }
    });
}


