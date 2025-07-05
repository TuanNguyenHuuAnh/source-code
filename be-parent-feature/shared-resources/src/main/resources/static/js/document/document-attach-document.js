$(document).ready(function () {
	blockDropFile();
	$('#attach-file-list').on('click', '.close', function(event) {
		$("#alertAttachedFileClientId").addClass("hidden");
	});
	
	$(".j-attach-list").each(function(index, item) {
		var attachDiv = $(item).closest(".j-attach");
    	var attachReference = $(attachDiv).find("input[name='reference']").val();
    	var attachReferenceId = $(attachDiv).find("input[name='referenceId']").val();
    	var isAttachFile = $(attachDiv).find("input[name='isAttachFile']").val();
    	var listAttachFileId = $('#listIdAttachFiles').val();
    	var attachDto = createAttachDto(null, attachReference, attachReferenceId, listAttachFileId,isAttachFile);
    	//console.log(attachDto);
		loadDataAttachFile("#attach-file-list", attachDto);
	});

	var filesUpload = {};
	var totalSize = 0;
	 var  max_file_number = 10;
	fileList = $('#form-attach-file').fileupload({
    	url: BASE_URL+ "attachfile/ajax/add",
        type: 'POST',
        dataType: 'json',
        formAcceptCharset: 'utf-8',
        limitMultiFileUploadSize: 3000000, // 10M
        limitMultiFileUploadSizeOverhead: 3000000, // 10M
        limitMultiFileUploads: 10,
        multipart: true,
        maxChunkSize: 10000000,
        sequentialUploads: true,
        fileExt: '*.docx;',
        singleFileUploads: false,
        add: function (e, data) {
        	var dataForm =  $("#form-attach-file").serializeArray();
        	totalSizeTemp = data.files[0].size;
        	fileName = data.files[0].name;
        	
        	// 20 * 1024 * 1024
        	if( !$("#alertAttachedFileClientId").hasClass("hidden") ) {
        		$("#alertAttachedFileClientId").addClass("hidden");
        	}
        	if(totalSizeTemp > MAX_SIZE_ATTACH_FILE) {
        		var msgFileSize20M = FILESIZE_IS_BIGGER + ' ' + (MAX_SIZE_ATTACH_FILE/1048576).toFixed(2) +'MB!';
        		$("#alertAttachedFileClientId").removeClass("hidden");
        		
        		$("#msgAlertAttachedFileClientId").html(msgFileSize20M);
        		return;
        	}
        	if(fileName.length > 100) {
        		var msgFileName = FILENAME_IS_MAX_LENGTH;
        		$("#alertAttachedFileClientId").removeClass("hidden");
        		
        		$("#msgAlertAttachedFileClientId").html(msgFileName);
        		return;
        	}
        	
        	var uploadErrors = [];
            	filesUpload[e.delegatedEvent.target.name] = data.files[0];            	
            		submitAttach(filesUpload, dataForm);
                	$(".text-war-attach").remove();
        },
    });
	
	$('#attach-file-list').on('click', '.j-btn-attach-delete', function(event) {
	    event.preventDefault();
	    var row = $(this).closest('tr.media-attach');
	    var id = row.data("id");

	    var attachDiv = $('.j-attach-list').closest(".j-attach");
    	var attachReference = $(attachDiv).find("input[name='reference']").val();
    	var attachReferenceId = $(attachDiv).find("input[name='referenceId']").val();
    	var isAttachFile = $(attachDiv).find("input[name='isAttachFile']").val();
    	var listAttachFileId = $('#pluginAttachFiles').val();
    	var attachDto = createAttachDto(id, attachReference, attachReferenceId, listAttachFileId,isAttachFile);

    	var item = $('#attach-file-list');
		deleteAttachById(item,attachDto);
	});
	
    // Export pdf
    $('#attach-file-list').on("click",'.j-btn-download-attach' ,function (event) {
    	event.preventDefault();
	    var row = $(this).closest('tr.media-attach');
	    var id = row.data("id");
	    var attachDiv = $('.j-attach-list').closest(".j-attach");
    	var attachReference = $(attachDiv).find("input[name='reference']").val();
    	var attachReferenceId = $(attachDiv).find("input[name='referenceId']").val();
    	var isAttachFile = $(attachDiv).find("input[name='isAttachFile']").val();
    	var attachDto = createAttachDto(id, attachReference, attachReferenceId, null,isAttachFile);
    	console.log(attachDto);
    	exportFileByFileId(attachDto);
    });
	
});

function loadDataAttachFile(renderDiv, attachDto) {
	$.ajax({
        type: "POST",
        url: BASE_URL + "attachfile/ajax/list",
        data: attachDto,
	        success : function(data) {
				$(renderDiv).html(data);
			},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
    });
}


function createAttachDto(id, reference, referenceId, listAttachFileId,isAttachFile) {
	var attachDto = {};
	attachDto["id"] = id;
	attachDto["reference"] = reference;
	attachDto["referenceId"] = referenceId;
	attachDto["tempAttachFileId"] = listAttachFileId;
	attachDto["isAttachFile"] = isAttachFile;
    return attachDto;
}

function submitAttach(filesUpload, dataForm){
	var filesList = [];
	$.each(filesUpload, function( key, value ) {
		dataForm.push({"name": key,"value": value});
		filesList.push(value);
	});
	if(filesList.length > 0) {
		// Init form data
		var formData = new FormData();
		$.map(dataForm, function(n, i) {
	        formData.append(n['name'], n['value']);
	    });
	var attachDiv = $('.j-attach-list').closest(".j-attach");
	var attachReference = $(attachDiv).find("input[name='reference']").val();
	var attachReferenceId = $(attachDiv).find("input[name='referenceId']").val();
	var isAttachFile = $(attachDiv).find("input[name='isAttachFile']").val();
	var listAttachFileId = $('#listIdAttachFiles').val();
	var attachDto = createAttachDto(null, attachReference, attachReferenceId, listAttachFileId,isAttachFile);
		$.ajax({
			type : "POST",
			enctype: 'multipart/form-data',
			url :  BASE_URL+ "attachfile/ajax/add" ,
			data : formData,
			// prevent jQuery from automatically transforming the data into a query string
	        processData: false,
	        contentType: false,
	        async: false,
	        cache: false,
	        timeout: 1000000,
			success : function(data) {
				var item = $('#attach-file-list');
				
				$('#attach-file-list').html(data);
			},
			error : function(xhr, textStatus, error) {
				console.log(error);
			}
		});
	}
}

function deleteAttachById(item, attachDto) {
   
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
    	if (result) {
			$.ajax({
		        type: "POST",
		        url: BASE_URL + "attachfile/ajax/delete",
		        data: attachDto,
		        success : function(data,textStatus,request) {
		        	//console.log(data);
		        	var isAlert = request.getResponseHeader('msgFlag');
		        	if( "1" == isAlert ) {
		        		popupAlert(data);
		        	}else {
			        	loadDataAttachFile(item, attachDto);
		        	}
				},
				error : function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				}
		    });
    	}
    });
}

function exportFileByFileId(attachDto){
	doExport("attachfile/ajax/downloadAttachFiles", attachDto);
}

function blockDropFile(){
	
	var dropzoneId = "form-attach-file";

	window.addEventListener("dragenter", function(e) {
	  if (e.target.id != dropzoneId) {
	    e.preventDefault();
	    e.dataTransfer.effectAllowed = "none";
	    e.dataTransfer.dropEffect = "none";
	  }
	}, false);

	window.addEventListener("dragover", function(e) {
	  if (e.target.id != dropzoneId) {
	    e.preventDefault();
	    e.dataTransfer.effectAllowed = "none";
	    e.dataTransfer.dropEffect = "none";
	  }
	});

	window.addEventListener("drop", function(e) {
	  if (e.target.id != dropzoneId) {
	    e.preventDefault();
	    e.dataTransfer.effectAllowed = "none";
	    e.dataTransfer.dropEffect = "none";
	  }
	});
}