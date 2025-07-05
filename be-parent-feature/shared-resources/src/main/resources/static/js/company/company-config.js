$(document).ready(function () {
    getCompanyConfigByCompanyId();
    
    //on change company
	$(".select-company").on('change', function(event) {
		getCompanyConfigByCompanyId();
	});
	
	//Save
	$('#btnSaveHead, #btnSave').click(function () {
		if ($(".j-form-validate").valid()) {
	    	var condition = $("#form-detail").serializeArray();
	    	var filesList = [], paramNames = [];
	    	$.each(filesImage, function( key, value ) {
	    		filesList.push(value);
	            paramNames.push(key);
	    	});
	    	condition["files"] = filesList;
	    	condition["paramName"] = paramNames;
	    	if(filesList.length > 0){
	    		file_upload.fileupload('send', condition);
	    	} else {
	    		save(condition);
	    	}
		}
	});
	
});

function save(values) {
    var url = "company/save";
    $.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : values,
		success : function(data, textStatus, request) {
			getMessage($(this), event, data);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

function getMessage(element, event, data) {
	var condition = setCondition(data.status, data.message, data.id)
	ajaxSearch("company/config", condition, 'msg-error', element, event);
}

function setCondition(status, message, id) {
	var condition = {};
	condition["id"] = id;
	condition["message"] = message;
	condition["status"] = status;
	return condition;
}

function getCompanyConfigByCompanyId(){
	var companyId = $("#companyId").val();
	$.ajax({
	      type  : "GET",
	      url   : BASE_URL + "company/config/detail",
	      data  : {
	    	  "companyId" : companyId
	      },
	      success: function( data ) {
	          $("#companyDetail").html(data);
	      }
	});
}
