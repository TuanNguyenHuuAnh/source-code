$(document).ready(function () {
    // Back
    $('#btnBack, #btnCancel').click(function () {
        back();
    });

    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });
    
   /* $('.date').datepickerUnit({
		format: DATE_FORMAT,
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : APP_LOCALE.toLowerCase(),
		todayHighlight : true,
		onRender : function(date) {
		}
	});*/
	fromDateToDateConfig();
    $('.datepicker-from-date').datepickerUnit('setEndDate', new Date());
    
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

function back() {
    var url = BASE_URL + "company/list";
    ajaxRedirect(url);
}

function create() {
    var url = BASE_URL + "company/add";
    ajaxRedirect(url);
}

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
	ajaxSearch("company/detail", condition, 'msg-error', element, event);
	$('#id').val(data.id);
}

function setCondition(status, message, id) {
	var condition = {};
	condition["id"] = id;
	condition["message"] = message;
	condition["status"] = status;
	return condition;
}
