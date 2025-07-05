$(document).ready(function () {
	
	$('#formFileName').select2({
        placeholder : '',
        allowClear : false,
    });
	
	// Init service category
    searchCombobox('.service-category', '', 'category/get-category',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $(this).parents("#form-detail").find('#companyId').val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
    
    // Init service business
    searchCombobox('.service-business', '', 'svc-management/get-business',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $(this).parents("#form-detail").find('#companyId').val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
    
    // Init service business
    searchCombobox('.service-function', '', 'svc-management/get-item',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $(this).parents("#form-detail").find('#companyId').val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
    
    // Upload service image
    $('#form-detail').fileupload({
        url: '',
        type: 'POST',
        dataType: 'json',
        formAcceptCharset: 'utf-8',
        limitMultiFileUploadSize: 1000000,
        limitMultiFileUploadSizeOverhead: 1000000,
        multipart: true,
        maxChunkSize: 10000000,
        sequentialUploads: false,
        fileExt: '*.jpg;*.jpeg;*.gif;*.png;',
        add: function (e, data) {
            var imageConsoleEl = $(this).find('.imageConsole');
            $(imageConsoleEl).hide();
            var acceptFileTypes = /^image\/(gif|jpe?g|png)$/i;
            if (data.originalFiles[0]['type'].length && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                $(imageConsoleEl).text('Not an accepted file type')
                $(imageConsoleEl).show();
                return;
            }
            if (data.originalFiles[0]['size'].length && data.originalFiles[0]['size'] > 5000000) {
                $(imageConsoleEl).text('Filesize is too big')
                $(imageConsoleEl).show();
                return;
            }

            var totalName = data.files[0].name;
            var fileName = totalName.split(/\.(?=[^\.]+$)/);
            $(this).find('.fileName')
            if ($(this).find('.fileName').val() == '') {
                $(this).find('.fileName').val(fileName[0]);
            }

            var reader = new FileReader();
            reader.onload = (function (imageServiceElm) {
                return function (e) {
                    $(imageServiceElm).attr("src", e.target.result);
                }
            })($(this).find('.image-service'));
            reader.readAsDataURL(data.files[0]);
            data.url = BASE_URL + $(this).attr('action');
            
            $(this).find('.btn-save').unbind('click').bind('click', function (event) {
            	$('#btnSaveHead,#btnSave').removeClass("btn-save-2");
            	data.submit();
            });
        },
        done: function (e, data) {
        	getMessage($(this), e, data.result);
        },
        fail: function (e, data) {
        	getMessage($(this), e, data.result);
        }
    });

    // Back
    $('#btnBack, #btnCancel').click(function () {
        back();
    });

    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

    // Save
    $('.btn-save-2').click(function () {
        save();
    });
});

function back() {
    var url = BASE_URL + "svc-management/list";
    ajaxRedirect(url);
}

function create() {
    var url = BASE_URL + "svc-management/add";
    ajaxRedirect(url);
}

function save() {
    if ($(".j-form-validate").valid()) {
        var values = $("#form-detail").serialize();
        var url = "svc-management/save";
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
}

function getMessage(element, event, data) {
	var condition = setCondition(data.status, data.message)
	ajaxSearch("svc-management/detail", condition, 'msg-error', element, event);
}

function setCondition(status, message) {
	var condition = {};
	condition["id"] = $("#id").val();
	condition["message"] = message;
	condition["status"] = status;
	return condition;
}

