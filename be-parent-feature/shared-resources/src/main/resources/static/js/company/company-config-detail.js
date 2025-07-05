$(document).ready(function () {
	file_upload = $('#form-detail').fileupload({
        url: BASE_URL + "company/save",
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
        },
        fail: function (e, data) {
        	getMessage($(this), e, data.result);
        }
    });
	
});
