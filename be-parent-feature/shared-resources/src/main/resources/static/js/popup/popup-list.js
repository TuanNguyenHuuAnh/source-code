$(function(){

	$('#fieldSearch').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    });
    
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'popup/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	// init date picker
	$('.date').datepicker({
		format: 'dd/mm/yyyy',
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : '${sessionScope.localeSelect}',
		todayHighlight : true,
		onRender : function(date) {
		}
	})
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		$("#msg").html("");
		onClickSearch(this, event);
	});
	
	$('#saveTemplate').unbind("click").bind("click", function(event){
	    event.preventDefault();
		var nameFile = $('#nameFileModal').val();
		var code = $('#code').val();
		if(nameFile == ''){
			$('#nameFileErrorModal').html('<span class="error">'+NAME_NULL+'</span>');
			return;
		}
		if(code == ''){
			$('#codeError').html('<span class="error">'+CODE_NULL+'</span>');
			return;
		}
		$('#nameFileErrorModal').html('');
		$('#codeError').html('');
		saveTemplate();
		$('#fileNameModal').html('');
	})
	
	//add new
	$('#add').on('click', function(){
		/*$('#modalContent').summernote('codeview.deactivate');
		$('modalContent').summernote('code','');
		$('#fileNameModal').html('');
		$('#nameFileErrorModal').html('');
		$('#codeError').html('');
		callModalNew();*/
		event.preventDefault();
		var url = BASE_URL + "popup/edit";
		// Redirect to page
		ajaxRedirect(url);
	})

	//upload file in popup
	uploadTemplateModal();

	//on change company
	$("#companyIdDetail").on('change', function(event) {
		$('#companyIdTemplate').val($('#companyIdDetail').val());
	});

	$('#checkParam').unbind("click").bind("click", function(event){
	    event.preventDefault();
	    checkParam();
	});
})

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	condition["toDate"] = $("#toDate").val();
	condition["fromDate"] = $("#fromDate").val();
	condition["companyId"] = $("#companyId").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}

function onClickSearch(element, event) {

	setDataSearchHidden();
	ajaxSearch("popup/ajaxList", setConditionSearch(), "tableList", element, event);
}

function callModal(templateId, $this, edit){
	$('#nameFileErrorModal').html('');
	$('#codeError').html('');
	$.ajax({
        url: BASE_URL + "popup/template-detail",
        type: "POST",
        data : {templateId : templateId},
        success:function(data) {
        	//set id
	        $('#templateId').val(data.id);

	        //set fileName
	        $('#nameFileModal').val(data.fileName);
	        //set subject
	        $('#subjectModal').val(data.subject);
	        //set company
	        $('#companyIdDetail').val(data.companyId);
	        //set notification
	        $('#mobileNotification').val(data.mobileNotification);
	        //set notification
	        $('#code').val(data.code);
	        //set content
	        $('#modalContent').summernote('codeview.deactivate');
	        $('#modalContent').summernote('code', data.templateContent);
	    	$('#modalContent').summernote();

	    	setPopupModal();
	    	if(edit){
	    		setEdit();
	    	}else{
	    		setView();
	    	}
	        $('#detailModal').modal('show');

        },
        error:function(e) {
        	console.log(e);
        }
    });
}
function saveTemplate(){
	var templateId = $('#templateId').val();
	var content = $('#modalContent').summernote('code').replace(/&nbsp;|<\/?[^>]+(>|$)/g, "").trim()==""?"":$('#modalContent').summernote('code');
	content = '<html><body>'+content+'</body></html>';
	var fileName = $('#nameFileModal').val();
	var subject = $('#subjectModal').val();
	var url = $('#url').val();
	var companyId = $('#companyIdDetail').val() != undefined ? $('#companyIdDetail').val() :'';
	var mobileNotification = $('#mobileNotification').val();
	var code = $('#code').val();
	var ajax = $.ajax({
        url: BASE_URL + "popup/save-template",
        type: "POST",
        data : {templateId : templateId,
        		content : content,
        		fileName : fileName,
        		subject : subject,
        		companyId : companyId,
        		mobileNotification: mobileNotification,
        		code: code,
        		url: url},
        global : false,
        beforeSend: function(request){
            var token = $("meta[name='_csrf']").attr("content");
            var header = $("meta[name='_csrf_header']").attr("content");
            request.setRequestHeader(header, token);
         }
    })
    .done(function(data){
    	if(data.status!=null){
    		$('#codeError').html('<span class="error">'+data.messages[0].content+'</span>');
    		return;
    	}

    	$( "#btnSearch" ).trigger( "click" );
    	$('#msg').html('<div class="alert alert-success alert-dismissible">'
				+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
				+'<div/>Save successfully</div>');
    	$('#detailModal').modal('hide');

    })
	.fail(function(){
		$( "#btnSearch" ).trigger( "click" );
		$('#msg').html('<div class="alert alert-error alert-dismissible">'
				+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
				+'<div/>Save failed</div>');
		$('#detailModal').modal('hide');
	})
}

function getDetailByteOut(templateId, $this){
	$.ajax({
        url: BASE_URL + "popup/template-email-detail",
        type: "POST",
        data : {templateId : templateId},
        global : false
    })
    .done(function(result){
    	console.log('getDetailByteOut '+result);
    	//load content to modal email
    	$('#modalContent').summernote('code', result);
    	$('#modalContent').summernote();
    	//show modal
    	$('#detailModal').modal('show');
        $('#detailModal').css('z-index',5000);
        $("tr").removeClass("highlight");
        $($this).addClass("highlight");
        //set id
        $('#templateId').val(templateId);
    });
}
function deleteTemplate(templateId){
	popupConfirm(MSG_DEL_CONFIRM, function (result) {
	    if (result) {
	    	// Redirect to page detail
			ajaxSubmit("popup/delete", {templateId : templateId}, event);

	    	/*$.ajax({
	            url: BASE_URL + "popup/delete",
	            type: "POST",
	            data : {templateId : templateId}
	        })
        	.done(function(data){
        		if(data == "1"){
        			$( "#btnSearch" ).trigger( "click" );
    		    	$('#msg').html('<div class="alert alert-success alert-dismissible">'
    						+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
    						+'<div/>Delete successfully</div>');
        		}else{
        			$('#msg').html('<div class="alert alert-error alert-dismissible">'
    						+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
    						+'<div/>Delete failed</div>');
        		}

		    })*/
	    }
    });
}

function callModalNew(){
	//set new
	setEdit();
	$('#companyIdDetail').val(companyId);
	$('#modalContent').summernote('code','');
	$('#nameFileModal').val('');
	$('#templateId').val('');
	$('#subjectModal').val('');
	$('#detailModal').modal('show');
	$('#detailModal').on('shown.bs.modal',function() {
		$('#nameFileModal').focus();
	})
    $('#detailModal').css('z-index',5000);
	$('#action').css('display','');
	$('#mobileNotification').val('');
	$('#code').val('');
}

function uploadTemplateModal(){
	$('#uploadModal').fileupload({
		url: '',
	    type: 'POST',
	    dataType: 'json',
	    formAcceptCharset: 'utf-8',
	    limitMultiFileUploadSize: 1000000,
	    limitMultiFileUploadSizeOverhead: 1000000,
	    multipart: true,
	    maxChunkSize :10000000,
	    sequentialUploads: false,
	    fileExt : '*.doc;*.docx;',
	    add: function (e, data) {
	    	var uploadErrors = [];
	    	var acceptFileTypes =/^application\/(msword)$|html|^doc$|^docx$|vnd.openxmlformats-officedocument.wordprocessingml.document/i;
            if(data.originalFiles[0]['type'].length && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                uploadErrors.push(NOT_ACCEPT_FILE_TYPE);
            }
            if(data.originalFiles[0]['size'].length && data.originalFiles[0]['size'] > 5000000) {
                uploadErrors.push(FILE_SIZE_BIG);
            }
            if(uploadErrors.length > 0) {
                //alert(uploadErrors.join("\n"));
            	var data = {status : 'fail', message : uploadErrors.join("\n")}
            	//showAlert(data);
            	$('#errorUpload').html('<div class="alert alert-error alert-dismissible">'
						+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
						+'<div/>'+data.message+'</div>');
                return;
            }
	    	var totalName = data.files[0].name ;
	    	$('#fileNameModal').html("<span class='file-name-box'>"+totalName+"</span>");
	    	var nameFile = totalName.split(/\.(?=[^\.]+$)/);
	    	if($('#nameFile').val() == ''){
	    		$('#nameFile').val(nameFile[0]);
	    	}
	    	//import template main
	    	$('#btnImportModal').unbind('click').bind('click',function(e) {
	    		e.preventDefault(e);
	    		var nameFile = $('#nameFile').val();
	    		if(nameFile == ''){
	    			$('#nameFileErrorModal').html('<label class="error"> File name is not null</label>');
	    			return;
	    		}else{
	    			$('#nameFileErrorModal').html('');
	    		}
	    		if(data.files != null){
	    			data.url= BASE_URL + 'popup/import-modal';
	    			data.submit();
	    			data.files = null;
	    			$("#fileNameModal").html('');
	    		}
	        });
	    },
	    done: function (e, data) {
	    	console.log(data);
	    	if(data.result.status == 'success'){
		    	$('#modalContent').summernote('code', data.result.content);
		    	$('#modalContent').summernote();
		    	$('#url').val(data.result.filePath);
	    	}else{
	    		showAlert(data.result);
	    		$('#detailModal').modal('hide');
	    	}
	    },
	    fail: function (e, data){
	    	console.log(data);
	    }
	});
}

function setPopupModal(){
    $('#detailModal').css('z-index',5000);
    $('.modal-dialog .note-editable').css('min-height','250px');
    $('.note-toolbar-wrapper').css('min-height', '35px');
    $('.note-toolbar-wrapper').css('overflow', 'auto');
    $('.modal').on('click', '[data-dismiss="modal"]', function(e) { e.stopPropagation(); });
	$('#errorUpload').html('');
	$('#listParam').html('');
}

function setEdit() {
	$('#nameFileModal').prop('readonly', false);
	$('#subjectModal').prop('readonly', false);
	$('#companyIdDetail').prop("disabled", false);
	$("input[name=file]").prop("disabled", false);
	$('#btnImportModal').prop("disabled", false);
	$('#modalContent').summernote('enable');
	$('#action').css('display','');
	if(!IS_ADMIN) {
		$('#companyIdDetail option[value=""]').attr("hidden",true);
	}
	$('#mobileNotification').prop('readonly', false);
	$('#code').prop('readonly', false);
}

function setView() {
	$('#nameFileModal').prop('readonly', true);
	$('#subjectModal').prop('readonly', true);
	$('#companyIdDetail').prop("disabled", true);
	$("input[name=file]").prop("disabled", true);
	$('#btnImportModal').prop("disabled", true);
	$('#modalContent').summernote('disable');
	$('#action').css('display','none');
	$('#companyIdDetail option[value=""]').attr("hidden",false)
	$('#mobileNotification').prop('readonly', true);
	$('#code').prop('readonly', true);
}

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

function checkParam(){
	var content = $('#modalContent').summernote('code');
	$.ajax({
        url: BASE_URL + "popup/check-params",
        type: "POST",
        data :{
        	subject: $('#subjectModal').val(),
        	notification: $('#mobileNotification').val(),
        	content: $('#modalContent').summernote('code')
        	},
        success: function( data ) {
        	$('#listParam').html(data);
	    }
    })
}

function editTemplate(id) {
	event.preventDefault();
	var url = BASE_URL + "popup/edit?id=" + id;
	// Redirect to page detail
	ajaxRedirect(url);
}

function detailTemplate(id) {
	event.preventDefault();
	var url = BASE_URL + "popup/detail?id=" + id;
	// Redirect to page detail
	ajaxRedirect(url);
}
