$(document).ready(function() {
    var filesUpload = {};
    var file_upload = $('#import-form').fileupload({
        url: BASE_URL + "jpm-process/import",
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
        	//console.log(data.paramName);
            var imageConsoleEl = $(this).find('.alert-' + data.paramName);
            $(imageConsoleEl).hide();
            var acceptFileTypes = /([a-zA-Z0-9\s_\\.\-\(\):])+(.jpm)$/i;
            if (data.originalFiles[0].name.length && !acceptFileTypes.test(data.originalFiles[0].name)) {
                $(imageConsoleEl).text(NOT_AN_ACCEPTED_FILE_TYPE + ' (*.jpm)');
                $(imageConsoleEl).show();
                return;
            }
            $('#fileImportName').val(data.files[0].name);

            filesUpload[e.delegatedEvent.target.name] = data.files[0];
        },
        done: function (e, data) {
        	console.log("done");
        	console.log(data.result);
        },
        fail: function (e, data) {
        	console.log("fail");
        	console.log(data.result);
        }
    }).on('fileuploadprocessalways', function (e, data) {
        var currentFile = data.files[data.index];
        if (data.files.error && currentFile.error) {
          // there was an error, do something about it
          console.log(currentFile.error);
        }
      });
    
    $('#btn-import-submit').click(function(e) {
    	var condition = $("#import-form").serializeArray();
		
		$.each(filesUpload, function( key, value ) {
			condition.push({"name": key,"value": value});
    	});

		var formData = new FormData();
		
		$.map(condition, function(n, i){
	        formData.append(n['name'], n['value']);
	    });
    	import_post(formData, e);
    });
});

function create() {
    var url = BASE_URL + "jpm-process/edit";
    ajaxRedirect(url);
}

function import_post(formData, event){
	event.preventDefault();
	if ($("#import-form").valid()) {

		var url = "jpm-process/import";
		
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
				var msgFlag = request.getResponseHeader("msgFlag");
				if( "1" == msgFlag ) {
					$('#messageError').html(data);
				}else{
					var content = $(data).find('.body-content');
					$('#import-modal').one('hidden.bs.modal', function () {
						$(".main_content").html(content);

						var urlPage = $(data).find('#url').val();
						if (urlPage != null && urlPage != '') {
							window.history.pushState('', '', BASE_URL + urlPage);
						}
						goTopPage();
					});
					$('#import-modal').modal('hide');
				}
			},
			error : function(xhr, textStatus, error) {
				console.log(error);
			}
		});
	}
}