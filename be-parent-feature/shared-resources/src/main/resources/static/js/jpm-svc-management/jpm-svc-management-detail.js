$(document).ready(function () {
	
	$('#formFileName').select2({
        placeholder : '',
        allowClear : false,
        language: {
  	      noResults: function() {
  	        return SELECT2_NO_RESULTS;
  	      },
  	    }
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
    searchCombobox('.service-business', '', 'jpm-business/get-jpm-business-unregistered',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $(this).parents("#form-detail").find('#companyId').val(),
    	            formType: $(this).parents("#form-detail").find('#formType').val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
    
    // Init service business
    searchCombobox('.service-function', '', 'jpm-svc-management/get-item',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $(this).parents("#form-detail").find('#companyId').val(),
    	            businessId: $(this).parents("#form-detail").find('#businessId').val(),
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
            
            var fileExtend = data.originalFiles[0].name.split('.').pop().toLowerCase();
            var fileName = data.files[0].name;
            if (fileExtend !== 'jpg' && fileExtend !== 'gif' && fileExtend !== 'jpe' && fileExtend !== 'jpg' && fileExtend !== 'png') {
                $(imageConsoleEl).text(NOT_AN_ACCEPTED_FILE_TYPE)
                $(imageConsoleEl).show();
                return;
            }
            if (data.originalFiles[0]['size'] > 20971520) {
                $(imageConsoleEl).text(FILESIZE_IS_BIGGER + ' 20MB')
                $(imageConsoleEl).show();
                return;
            }
            if(fileName.length > 100) {
            	var msgFileName = FILENAME_IS_MAX_LENGTH;
            	 $(imageConsoleEl).text(msgFileName)
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
        	console.log("done");
        	console.log(data.result);
        	/*getMessage($(this), e, data.result);*/
        },
        fail: function (e, data) {
        	console.log("not done");
        	console.log(data.result);
        	/*getMessage($(this), e, data.result);*/
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
    $('.btn-save-2').click(function (event) {
        //save();
    	if ($(".j-form-validate").valid()) {
    		var url = "jpm-svc-management/save";
			var condition = $("#form-detail").serialize();
			ajaxSubmit(url, condition, event);
		}else{
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
		}	
    });
    
    $('.service-business').on('change.select2', function (e) {
    	$('.service-function').val('').trigger('change');
        checkRequiredFunction();
        defaultFunctionByBusiness($(this));
    });
    
    
	// show tab if exists error
	showTabError(LANGUAGE_LIST);
});

function back() {
    var url = BASE_URL + "jpm-svc-management/list";
    ajaxRedirect(url);
}

function create() {
    var url = BASE_URL + "jpm-svc-management/add";
    ajaxRedirect(url);
}

function save() {
    if ($(".j-form-validate").valid()) {
        var values = $("#form-detail").serialize();
        var url = "jpm-svc-management/save";
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
	var condition = setCondition(data.active, data.message)
	ajaxSearch("jpm-svc-management/detail", condition, 'msg-error', element, event);
}

function setCondition(status, message) {
	var condition = {};
	condition["id"] = $("#id").val();
	condition["message"] = message;
	condition["status"] = status;
	return condition;
}

function checkRequiredFunction(){
	$.ajax({
	      type  : "POST",
	      url   : BASE_URL + "jpm-svc-management/get-item",
	      data  : {
	    	  	keySearch: null,
	            companyId: $('#companyId').val(),
	            businessId: $('#jpmBusinessId').val(),
	            isPaging: true
	      },
	      success: function(data) {
	          if(data.total==0){
	        	  $('#labelFunction').removeClass('required');
	        	  $('.service-function').removeClass('j-required');
	          }else{
	        	  $('#labelFunction').addClass('required');
	        	  $('.service-function').addClass('j-required');
	          }
	      }
	});
}

function defaultFunctionByBusiness(element){
	var businessId = $(element).val();
	var companyId = $('#companyId').val();
	$.ajax({
		type : "POST",
		url : BASE_URL + 'jpm-svc-management/get-item-default',
		data : {companyId: companyId, businessId: businessId},
		success : function(data) {
			$('.service-function').prop('disabled', false);
			$('#functionCodeIp').prop('disabled', true);
			if(null != data.id){
				var newOption = new Option(data.text, data.id, false, true);
				$('.service-function').append(newOption).trigger('change');
			}
			if(data.name == "2"){
				$('.service-function').prop('disabled', true);
				$('#functionCodeIp').val(data.id);
				$('#functionCodeIp').prop('disabled', false);
			}
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}
