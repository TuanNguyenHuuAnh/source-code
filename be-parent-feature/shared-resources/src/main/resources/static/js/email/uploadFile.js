$(function(){

    var ul = $('#uploadAttach ul');

    $('#drop a').click(function(){
        // Simulate a click on the file input button
        // to show the file browser dialog
        $(this).parent().find('input').click();
    });

    // Initialize the jQuery File Upload plugin
    $('#uploadAttach').fileupload({
    	dataType:'json',
        // This element will accept file drag/drop uploading
        dropZone: $('#drop'),
	    sequentialUploads: true, 
        // This function is called when a file is added to the queue;
        // either via the browse button, or via drag/drop:
        add: function (e, data) {
        	totalSizeTemp = totalSize + data.files[0].size;
        	
        	$('#msgAttach').html('');
        	
        	// validation file size - BaoHG
        	var uploadErrors = [];
        	if(data.files[0].size == 0) {
            	uploadErrors.push(NOT_ATTACH_FILE_ZERO);
            }
            if(totalSizeTemp > ATTACH_FILE_SIZE) {
            	uploadErrors.push(NOT_ATTACH_FILE_BIG);
            }
            
            //check ext
            if(EXT_ATTACH_FILE_LIST.indexOf(data.files[0].name.split('.').pop())<= 0) {
            	uploadErrors.push(ATTACH_FILE_EXT);
            }
            if(uploadErrors.length > 0) {
            	var data = {status : 'fail', message : uploadErrors.join("\n")}
            	showAlert(data);
                return;
            }
        	
            var tpl = $('<li class="working"><input type="text" value="0" data-width="30" data-height="30"'+
                ' data-fgColor="#0788a5" data-readOnly="1" data-bgColor="#3e4043" class="percent-load"/><p></p><span></span></li>');

            // Append the file name and file size
            tpl.find('p').text(data.files[0].name)
                         .append('<i>' + formatFileSize(data.files[0].size) + '</i>');

            // Add the HTML to the UL element
            data.context = tpl.appendTo(ul);

            // Initialize the knob plugin
            tpl.find('input').knob();
            
            console.log('zo');

            // Listen for clicks on the cancel icon
            tpl.find('span').click(function(){

                if(tpl.hasClass('working')){
                    jqXHR.abort();
                }

                tpl.fadeOut(function(){
                	var id = tpl.find('span')[0].id;
                	//console.log(id);
                    //tpl.remove();
                	deleteAttachById(id, tpl);
                	totalSize = totalSize - data.files[0].size;
                	//remove attachId
                	var index = listAttachId.indexOf(Number(id));
                	if (index > -1) {
                		listAttachId.splice(index, 1);
                	}
                });

            });
            
            // Automatically upload the file once it is added to the queue
            var jqXHR = data.submit().done(function(result){
            	if(result[0].status == 'success'){
            		//do something success
            		tpl.find('span').prop('id', result[0].idFile);
            		totalSize = totalSizeTemp;
            		listAttachId.push(result[0].idFile);
            	}else{
            		//do something with fail
            		data.context.addClass('working error');
            		data.context.find('input').val(0).change();
            		showAlert(result[0]);
                    return;
            	}
            })
            
        },
        progress: function(e, data){
            // Calculate the completion percentage of the upload
            var progress = parseInt(data.loaded / data.total * 100, 10);
            // Update the hidden input field and trigger a change
            // so that the jQuery knob plugin knows to update the dial
            data.context.find('input').val(progress).change();

            if(progress == 100){
                data.context.removeClass('working');
            }
        },
        
        fail:function(e, data){
            // Something has gone wrong!
        	data.context.addClass('working error');
    		data.context.find('input').val(0).change();
    		var error = {status : 'fail', message : ATTACH_FILE_FAIL}
    		showAlert(error);
        }

    });

    $('#uploadAttach').bind('fileuploadsubmit', function (e, data) {
    });

    // Prevent the default action when a file is dropped on the window
    $(document).on('drop dragover', function (e) {
        e.preventDefault();
    });

    // Helper function that formats the file sizes
    function formatFileSize(bytes) {
        if (typeof bytes !== 'number') {
            return '';
        }

        if (bytes >= 1073741824) {
            return (bytes / 1073741824).toFixed(2) + ' GB';
        }

        if (bytes >= 1048576) {
            return (bytes / 1048576).toFixed(2) + ' MB';
        }

        return (bytes / 1024).toFixed(2) + ' KB';
    }
    
    function showAlert(data){
    	if(data.status == 'success'){
    		$('#msgAttach').html('<div class="alert alert-success alert-dismissible">'
    						+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
    						+'<div/>'+data.message+'</div>');
    	}else{
    		$('#msgAttach').html('<div class="alert alert-error alert-dismissible">'
    						+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
    						+'<div/>'+data.message+'</div>');
    	}
    	
    }
});

function deleteAttachById(id, tpl){
	$.ajax({
        url: BASE_URL + "email/delete_acttach_file",
        type: "POST",
        data : {id : id},
        success: function (){
        	tpl.remove();
        }
    });
}