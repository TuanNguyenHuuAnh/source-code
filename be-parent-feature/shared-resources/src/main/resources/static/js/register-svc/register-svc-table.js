var formData;
$(document).ready(function () {
	
	$('.btn-register').unbind('click').bind('click', function (event) {
    	registerService($(this), event);
    });

    // Upload service image
    $('.register-form').fileupload({
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
            
            $(this).find('.btn-file-register').unbind('click').bind('click', function (event) {
            	$(this).removeClass("btn-register");
            	formData = data;
                event.preventDefault();
                var form = $(this).parents(".register-form");
                if ($(form).valid()) {	
	                popupConfirm(MSG_REGISTER_CONFIRM.replace('{0}', $(form).find('.service-name').val()), function (result) {
	                    if (result) {
	                    	formData.submit();
	                    }
	                });
                }
            });
        },
        done: function (e, data) {
        	search($(this), e, data.result);
        },
        fail: function (e, data) {
        	search($(this), e, data.result);
        }
    });
    
    // Init service category
    searchCombobox('.service-category', '', 'category/get-category',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $(this).parents(".register-form").find('.company-id').val(),
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
    	            companyId: $(this).parents(".register-form").find('.company-id').val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
    
    // Init service function
    searchCombobox('.service-function', '', 'svc-management/get-item',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $(this).parents(".register-form").find('.company-id').val(),
    	            isPaging: true,
    	            mode: 1
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
});

// Register service with image default
function registerService(element, event) {
	event.preventDefault();
	var form = $(element).parents(".register-form");
	if ($(form).valid()) {
		popupConfirm(MSG_REGISTER_CONFIRM.replace('{0}', $(form).find('.service-name').val()), function (result) {
			if (result) {
				var form = $(element).parents(".register-form");
				var data = $(form).serialize();
				var url = $(form).attr('action');
				$.ajax({
					type : "POST",
					url : BASE_URL + url,
					data : data,
					success : function(data, textStatus, request) {
						search($(form), event, data);
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
}