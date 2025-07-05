var rABS = true;
var language = APP_LOCALE;
$('#fmUpload').fileupload({
    url: '',
    type: 'POST',
    dataType: 'json',
    formAcceptCharset: 'utf-8',
    limitMultiFileUploadSize: 1000000,
    limitMultiFileUploadSizeOverhead: 1000000,
    multipart: true,
    maxChunkSize :10000000,
    sequentialUploads: false, 
    add: function (e, data) {
    	$('#validateDataImport').click(function(e) {
    		e.preventDefault(e);
    		if(data.files != null){
    		   //Disable submit button
    		   //$('#btnImport').addClass('hidden');
    			var sheet = $("#sheetExcelId").val();
    			var position =$("#positionExcel").val();
    			var password =$("#passwordExcel").val(); 
    			
    			var unknowParam = $("#isNoPO").is(":checked") ? "1" : "0";
    			var unknowParam2 = $("#propertyOf").val();
    			console.log(sheet);
    		   	if(data != null){
    		   		data.url= BASE_URL + $('#linkUploadExcelCommon').val() + "?sheetNames=" + sheet + "&startRowData=" + position + "&pazzword=" + password
    		   		+"&unknowParam=" + unknowParam + "&unknowParam2=" + unknowParam2 ;
    		   		//+ "&startRowData=" + position + "&pazzword=" + password;
        			data.submit();
    		   	}	
    			
    			data.files = null;
    		
    	}
        });
    },
    drop: function (e, data) {
    	$('#sheetExcelSelectId').html('<select class="form-control" id="sheetExcelId" name="sheetNames"></select>');
        $.each(data.files, function (index, file) {
        	$("#fileList").html('<span class="message-file-upload" style="color: blue;word-break: break-all;">'+file.name+'<br/></span>');
    		readSheet(e,file);
        });
       
    },
    change: function (e, data) {
    	$('#sheetExcelSelectId').html('<select class="form-control" id="sheetExcelId" name="sheetNames"></select>');
        $.each(data.files, function (index, file) {
        	$("#fileList").html('<span class="message-file-upload" style="color: blue;word-break: break-all;">'+file.name+'<br/></span>');
    		readSheet(e,file);
        });
      
	  	  
    },
    progress: function (e, data) {
    	var perc = Math.round((data.loaded / data.total) * 100);
	  	$('#progressBar').text(perc + '%');
	  	$('#progressBar').css('width',perc + '%');
			if(perc ==100){
		  		$("#message-inprocess").html('<span class="text-center" style="color:green;">'+ 'File upload success and processing, please wait ...'+'</span>');
		  	}
	},
	start:  function (e) {
		$('#progressBar').text('0%');
		$('#progressBar').css('width','0%');
	},
	done: function (e, data) {
		
		var response = data.result;
		$("#message-inprocess").html('<span class="text-center" style="color:green;">'+ 'Process completed'+'<span/>');
    	$('#btnImport').removeClass('hidden');
    	$('.message-file-upload').remove();
    	if(response.result=='success'){
		      /*$('#message-upload-id').html('<div class="alert alert-success alert-dismissible">'+
						 ' <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button><div >'
						+ response.result+'</div></div>');*/
    		if(response.countError > 0){ // chuyen sang trang list import 
    			var linkUploadList = $("#linkUploadExcelListCommon").val();
    			var urlError = BASE_URL;
       		  if(linkUploadList.indexOf("?") !== -1){
       			 //window.location.href = BASE_URL + linkUploadList + "&importId=" + response.importId; 
       			urlError += (linkUploadList + "&importId=" + response.importId); 
       		  }else{
       			 //window.location.href = BASE_URL + linkUploadList + "?importId=" + response.importId;
       			urlError += (linkUploadList + "?importId=" + response.importId);
       		  }	
       		getListCatalog(urlError);
       		$(".close").trigger("click");
    		}
    		else{ // neu khong co loi thi ajax table import sang man hinh edit
    			var linkUploadedList = $("#linkUploadExcelListCatalog").val();  
    			//var catalogCode =  $("#catalogCode").val();
    			var linkRedirec = linkUploadedList.indexOf("?") !== -1 
    					? BASE_URL + linkUploadedList + "&importId=" + response.importId 
    					: BASE_URL + linkUploadedList + "?importId=" + response.importId;
    			var urlAjax = linkRedirec + "&propertyOf=" + $("#propertyOf").val(); 
    			getListCatalog(urlAjax);
    			$(".close").trigger("click");
    		}
    	}else{
    	     $('#messageE').removeClass('bg-msg-err');
    	     var messageE = "'#messageE'";
    	     var bg = "'bg-msg-err'";
    		 $('#message-upload-id').html('<div class="alert alert-danger alert-dismissible">'+
					 ' <button aria-hidden="true" data-dismiss="alert" id="btnCloseError" onclick="$('+messageE+').addClass('+bg+');" class="close" type="button">×</button><div >'
					+ response.result +':'+response.exception+'</div></div>');
    	}
    	data.files = null;
      
	},
	fail: function(e,data){
		$('.message-file-upload').remove();
    	$("#message-inprocess").html('<span class="text-center" style="color:red;">'+ 'File upload fail'+'<span/>');
    	$('#message-upload-id').html('<div class="alert alert-danger alert-dismissible">'+
				 ' <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button><div >'
				+ '('+data.result+
	      		' - '+data.textStatus+')'+'</div></div>');
     
    	 $('#btnImport').removeClass('hidden');
    	data.files=null;
  
	}
});


function getListCatalog(url){
	$.post( url, function( data ) {
		$("#tableDetail").html(data);
	});
	
}


