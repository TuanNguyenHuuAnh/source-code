$(function(){
	var total; 
	$('#upload').fileupload({
	    url: '',
	    type: 'POST',
	    dataType: 'json',
	    formAcceptCharset: 'utf-8',
	    limitMultiFileUploadSize: 1000000,
	    limitMultiFileUploadSizeOverhead: 1000000,
	    multipart: true,
	    maxChunkSize :10000000,
	    sequentialUploads: false, 
	    fileExt : '*.html;*.txt',
	    dropZone: $('#fileName'),
	    add: function (e, data) {
	    	var uploadErrors = [];
	    	$('#msg').html('');
	    	var acceptFileTypes =/^html|^text/;
            if(data.originalFiles[0]['type'].length && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                uploadErrors.push(NOT_ACCEPT_FILE_TYPE);
            }
            if(data.originalFiles[0]['size'].length && data.originalFiles[0]['size'] > 5000000) {
                uploadErrors.push(FILE_SIZE_BIG);
            }
            if(uploadErrors.length > 0) {
                //alert(uploadErrors.join("\n"));
            	var data = {status : 'fail', message : uploadErrors.join("\n")}
            	showAlert(data);
                return;
            }
	    	var totalName = data.files[0].name ;
	    	
	    	if (totalName.length > 29) {
		    	let extent = totalName.split('.')[1];
	    	    totalName = totalName.substring(0, 29).split('.')[0] + '...' + extent
	    	}
	    	
	    	$('#fileName').html("<span class='file-name-box'>" + totalName + "</span>");
	    	var nameFile = totalName.split(/\.(?=[^\.]+$)/);
	    	if($('#nameFile').val() == ''){
	    		$('#nameFile').val(nameFile[0]);
	    	}
	    	
	    	//import template main
	    	$('#btnImport').unbind('click').bind('click',function(e) {
	    		e.preventDefault(e);
	    		var nameFile = $('#nameFile').val();
	    		if(nameFile == ''){
	    			$('#nameFileError').html('<label class="error"> File name is not null</label>');
	    			return;
	    		}else{
	    			$('#nameFileError').html('');
	    		}
	    		if(data.files != null){
	    			data.url= BASE_URL + 'popup/import';
	    			data.submit();
	    			
                    data.files = null;
                    $("#fileName").html('');
	    		}
	        });
	    },
	    done: function (e, data) {
    	    var templateContent = data.result.templateContent;
            $('#summernote').summernote('code', templateContent);
	    },
	    fail: function (e, result){
            
            // $('#nameFileError').html('<label class="error">' + FILE_NOT_CORECT + '</label>');
            var data = {status : 'fail', message : FILE_NOT_CORECT}
            showAlert(data);
	    	console.log(result);
	    }
	});
	
})
function showAlert(data){
	if(data.status == 'success'){
		$('#msg').html('<div class="alert alert-success alert-dismissible">'
						+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
						+'<div/>'+data.message+'</div>');
	}else{
		$('#msg').html('<div class="alert alert-error alert-dismissible">'
						+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
						+'<div/>'+data.message+'</div>');
	}
	
}
