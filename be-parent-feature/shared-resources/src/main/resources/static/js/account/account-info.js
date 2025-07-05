$(document).ready(function() {
	// Init upload
	var filesImage = {};
    file_upload = $('#id-form-account').fileupload({
        url: BASE_URL + "account/info",
        type: 'POST',
        dataType: 'text/html;charset=UTF-8',
        formAcceptCharset: 'utf-8',
        limitMultiFileUploadSize: 1000000,
        limitMultiFileUploadSizeOverhead: 1000000,
        multipart: true,
        maxChunkSize: 10000000,
        sequentialUploads: true,
        fileExt: '*.jpg;*.jpeg;*.gif;*.png;',
        singleFileUploads: false,
        add: function (e, data) {
            var imageConsoleEl = $(this).find('.imageConsole-' + data.paramName);
            $(imageConsoleEl).hide();
            var acceptFileTypes = /^image\/(gif|jpe?g|png)$/i;
            if (data.originalFiles[0]['type'].length <= 0|| !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                $(imageConsoleEl).text(NOT_AN_ACCEPTED_FILE_TYPE)
                $(imageConsoleEl).show();
                return;
            }
            
            if (data.originalFiles[0]['size'].length > 0 && data.originalFiles[0]['size'] > 5000000) {
                $(imageConsoleEl).text(FILESIZE_IS_BIGGER)
                $(imageConsoleEl).show();
                return;
            }
            
            var totalName = data.files[0].name;
            var fileName = totalName.split(/\.(?=[^\.]+$)/);
            if ( $(this).find('.' + data.paramName) == '') {
            	 $(this).find('.' + data.paramName).val(fileName[0]);
            }

            var reader = new FileReader();
            reader.onload = (function (imageElm) {
                return function (e) {
                    $(imageElm).attr("src", e.target.result);
                }
            })($(this).find('.image-' + data.paramName));
            reader.readAsDataURL(data.files[0]);
            filesImage[e.delegatedEvent.target.name] = data.files[0];
        },
        done: function (e, data) {
        	console.log("done");
        	console.log(data.result);
        },
        fail: function (e, data) {
        	console.log("fail");
        	console.log(data.result);
        }
    });
    
	var accountid = $("#id-account-id").val();
	
	$("#linkList").on("click", function(event) {
		var url = BASE_URL + "account/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click saveDraft
	$('.btnSave').on('click', function(event) {
		event.preventDefault();
		if ($(".j-form-validate").valid()) {
			var url = "account/info";
			var condition = $(".j-form-validate").serializeArray();
			var filesList = [];
			$.each(filesImage, function( key, value ) {
				condition.push({"name": key,"value": value});
	    		filesList.push(value);
	    	});
			
			if(filesList.length > 0) {
				// Init form data
				var formData = new FormData();
				$.map(condition, function(n, i) {
			        formData.append(n['name'], n['value']);
			    });
				
				// Call ajax to save
				$.ajax({
					type : "POST",
					enctype: 'multipart/form-data',
					url : BASE_URL + url,
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
			} else {
				ajaxSubmit(url, condition, event);
			}
			filesImage = {};
		}
	});
	
	$('.datepicker').datepickerUnit({
		format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
        keyboardNavigation : true
	});
	
	$('#togglePushNotification').on('click', function(){
		$(this).find('i').toggleClass('fa-toggle-on fa-toggle-off');
		$('#pushNotification').prop('checked', !$('#pushNotification').is(':checked'));
	});
	
	$('#togglePushEmail').on('click', function(){
		$(this).find('i').toggleClass('fa-toggle-on fa-toggle-off');
		$('#pushEmail').prop('checked', !$('#pushEmail').is(':checked'));
	});
	
	$('#toggleArchiveFlag').on('click', function(){
		$(this).find('i').toggleClass('fa-toggle-on fa-toggle-off');
		$('#archiveFlag').prop('checked', !$('#archiveFlag').is(':checked'));
	});
});
