$(document).ready(function () {
	$("#import-attached-file").on('click',function(e){
		e.preventDefault();
		doImport();
	});
	$(".attached-file-td").on('click',function(e){
		var $tr = $(this).closest('td').closest('tr');
		var id = $tr.data('attached-file-id');
		$("#attachedFileId").val(id);
		window.location.href="download-attached-file?id="+id;
		
	});
	$(".j-delete-file").on('click',function(e){
		var $tr = $(this).closest('td').closest('tr');
		var id = $tr.data('attached-file-id');
		$("#attachedFileId").val(id);
		console.log(id);
		bootbox.confirm({ 
			  size: "small",
			  title: "Alert",
			  message: 'Are you sure?', 
			  callback: function(result){ 
				  if(result){
					  $.ajax({
				            type: "POST",
				            url: "delete-attached-file?id=" + id,
				            success : function(response) {
				            	console.log(response);
				    			unblockbg();
				    			if(response.status=='success'){
				    				$tr.remove();
					      	    	bootbox.alert({ 
					      			  size: "small",
					      			  title: "Notification",
					      			  message: '<div class="alert alert-success alert-dismissible">'+
										 '<div >'
										+ response.message+'</div></div>', 
					      			  callback: function(){ 
					      			  }
					      	    	});
				      	    	}else{
				      	    		bootbox.alert({ 
				  	      			  size: "small",
				  	      			  title: "Notification",
				  	      			  message: '<div class="alert alert-danger alert-dismissible">'+
										 '<div >'
				 						+ response.message+'</div></div>', 
				  	      			  callback: function(){ 
				  	      				 
				  	      			  }
				      	    		});
				      	    	}
				    			
				    			$(".attached-file-td").on('click',function(e){
				    				var $tr = $(this).closest('td').closest('tr');
				    				var id = $tr.data('attached-file-id');
				    				$("#attachedFileId").val(id);
				    				window.location.href = "download-attached-file?id="+id;
				    				
				    			});
				    		},
				    		error : function(xhr, textStatus, error) {
				    			console.log(xhr);
				    			console.log(textStatus);
				    			console.log(error);
				    			unblockbg();
				    		}
				        });
				  }
			  }
		});
		
		
	});
	
    $("#btnImportPopup").click(function (event) {
    	event.preventDefault();
        blockbg();
        if(validateFormPopup()){
        	$("#btnImportPopup").removeAttr('data-dismiss');
            unblockbg();
            return;
        }else{
        	$("#btnImportPopup").attr('data-dismiss','modal');
        }
        //stop submit the form, we will post it manually.
        event.preventDefault();

        // Get form
        var form = $('#attachedFileUploadForm')[0];

        // Create an FormData object
        var data = new FormData(form);
        console.log(data);
        $.ajax({
            type: "POST",
            enctype: 'multipart/form-data',
            url: "upload-attached-file",
            data: data,
            processData: false,
            contentType: false,
            cache: false,
            success : function(data) {
    			$("#tableList").html(data);
    			unblockbg();
    			$(".attached-file-td").on('click',function(e){
    				var $tr = $(this).closest('td').closest('tr');
    				var id = $tr.data('attached-file-id');
    				$("#attachedFileId").val(id);
    				window.location.href="download-attached-file?id="+id;
    				
    			});
    			$(".j-delete-file").on('click',function(e){
    				var $tr = $(this).closest('td').closest('tr');
    				var id = $tr.data('attached-file-id');
    				$("#attachedFileId").val(id);
    				console.log(id);
    				bootbox.confirm({ 
    					  size: "small",
    					  title: "Alert",
    					  message: 'Are you sure?', 
    					  callback: function(result){ 
    						  if(result){
    							  $.ajax({
    						            type: "POST",
    						            url: "delete-attached-file?id=" + id,
    						            success : function(response) {
    						            	console.log(response);
    						    			unblockbg();
    						    			if(response.status=='success'){
    						    				$tr.remove();
    							      	    	bootbox.alert({ 
    							      			  size: "small",
    							      			  title: "Notification",
    							      			  message: '<div class="alert alert-success alert-dismissible">'+
    												 '<div >'
    												+ response.message+'</div></div>', 
    							      			  callback: function(){ 
    							      			  }
    							      	    	});
    						      	    	}else{
    						      	    		bootbox.alert({ 
    						  	      			  size: "small",
    						  	      			  title: "Notification",
    						  	      			  message: '<div class="alert alert-danger alert-dismissible">'+
    												 '<div >'
    						 						+ response.message+'</div></div>', 
    						  	      			  callback: function(){ 
    						  	      				 
    						  	      			  }
    						      	    		});
    						      	    	}
    						    			
    						    			$(".attached-file-td").on('click',function(e){
    						    				var $tr = $(this).closest('td').closest('tr');
    						    				var id = $tr.data('attached-file-id');
    						    				$("#attachedFileId").val(id);
    						    				window.location.href = "download-attached-file?id="+id;
    						    				
    						    			});
    						    		},
    						    		error : function(xhr, textStatus, error) {
    						    			console.log(xhr);
    						    			console.log(textStatus);
    						    			console.log(error);
    						    			unblockbg();
    						    		}
    						        });
    						  }
    					  }
    				});
    				
    				
    			});
    		},
    		error : function(xhr, textStatus, error) {
    			console.log(xhr);
    			console.log(textStatus);
    			console.log(error);
    			unblockbg();
    		}
        });
    });

    $("#templateUlPopup a").click(function(){
        $("#templatePopup .text-drop").text();
        var text_sub = $(this).text();
        $("#templatePopup .text-drop").text(text_sub);
    });
});

$(function() {
    $(document).on('change', ':file', function() {
        var input = $(this),
            numFiles = input.get(0).files ? input.get(0).files.length : 1,
            label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [numFiles, label]);
    });

    $(':file').on('fileselect', function(event, numFiles, label) {
        var input = $(this).parents('.input-group').find(':text'),
            log = numFiles > 1 ? numFiles + ' files selected' : label;
        if( input.length ) {
            input.val(log);
        } else {
            if( log ) alert(log);
        }
    });
});

function validateFormPopup(){
    if($("#fileUploadPopup").val() == ""){
        return true;
    }
    return false;
}
function doImport(){
    $('#fileUploadPopup').val("");
    $("#attachedFileUploadForm").find('input[name=uploadingFiles]').val("");
    
    $("#template-report-import-popup").find('.modal-dialog').css('width', '700px');
    $("#template-report-import-popup").modal('show');
}