$(document).ready(function(){
	file_upload = $('#form-config').fileupload({
        url: BASE_URL + "system-config/save",
        type: 'POST',
        dataType: 'json',
        formAcceptCharset: 'utf-8',
        limitMultiFileUploadSize: 1000000,
        limitMultiFileUploadSizeOverhead: 1000000,
        multipart: true,
        maxChunkSize: 10000000,
        sequentialUploads: true,
        fileExt: '*.jpg;*.jpeg;*.gif;*.png;*.icon;',
        singleFileUploads: false,
        add: function (e, data) {
        	//debugger;
            var imageConsoleEl = $(this).find('.imageConsole-' + data.paramName);
            $(imageConsoleEl).hide();
            var acceptFileTypes = /^image\/(gif|jpe?g|png|x-icon)$/i;
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
            if ( $(this).find('.' + data.paramName) == '') {
            	 $(this).find('.' + data.paramName).val(fileName[0]);
            }

            var reader = new FileReader();
            reader.onload = (function (imageElm) {
                return function (e) {
                    $(imageElm).attr("src", e.target.result);
                    $(imageElm).parent().find('.is-null').val('false');
                }
            })($(this).find('.image-' + data.paramName));
            reader.readAsDataURL(data.files[0]);
            filesImage[e.delegatedEvent.target.name] = data.files[0];
        },
        done: function (e, data) {
			getMessage($(this), e, data.result);
			console.log(data);
        },
        fail: function (e, data) {
			console.log(data);
			getMessage($(this), e, data.result);
        }
    });
	$('select[multiple]').multiselect({
	    columns: 1
	});


	$('#syncTime').datetimepicker({
		format : "HH:mm",
		showClear : true,
		showClose : true,
		allowInputToggle: true,
		sideBySide: true
	});
	$("#syncTime").keydown(false);
	var checked = $('#usedECM').is(':checked');
	onCheckECM(checked);
	
	/*var pushNotif = $('#pushNotif').is(':checked');
	onCheckPushNotif(pushNotif);*/
	
	var flagFirebase = $('#flagFirebase').is(':checked');
	onCheckFirebase(flagFirebase);
	
	$(document).on("keypress", ":input:not(textarea)", function(event) {
	    if (event.keyCode == 13) {
	        event.preventDefault();
	    }
	});
	
	var typeSendEmail = $('#typeSendEmail').val();
	if(typeSendEmail == 'AWS') {
		$('#radio1').trigger("click");
	} else {
		$('#radio2').trigger("click");
	}
	
	$('#radio2').on('change', function() {
		if ($("#radio2").prop("checked")) {
			$('#typeSendEmail').val("SMTP");
	   		$('#boxAWS').hide();
			$('#boxSMTP').show();
		}
		
	});
	
	$('#radio1').on('change', function() {
		if ($("#radio1").prop("checked")) {
			$('#typeSendEmail').val("AWS");
	   		$('#boxSMTP').hide();
	   		$('#boxAWS').show();

		}
	});
	
})
	function getMessage(element, event, data) {
		var condition = setCondition(data.status, data.message, data.id)
		console.log('condition', data);
		ajaxSearch("system-config/config", condition, 'msg-error', element, event);
	}
	
function setCondition(status, message, id) {
	var condition = {};
	condition["id"] = id;
	condition["message"] = message;
	condition["status"] = status;
	return condition;
}