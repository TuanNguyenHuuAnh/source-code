$(document).ready(function() {
	var fileConsole = 'fileConsole';
	
	$("#btnTemplateDownload").unbind().bind('click', function(e){
		e.preventDefault();
    	var linkExport = BASE_URL + CONTROLLER_URL + "/download-template-excel";
    	makePostRequest(linkExport, {});
	})
	
	$("#btnErrorDownload").unbind().bind('click', function(e){
		e.preventDefault();
		let fileName = $('#fileName').val();
		if (fileName === undefined || fileName == null || fileName === ""){
	    	var linkExport = BASE_URL + CONTROLLER_URL + "/download-template-error-excel";
	    	doExportExcelWithToken(linkExport, "token", setConditionSearch());
		}else{
	    	var linkExport = BASE_URL + CONTROLLER_URL + "/download-template-error-excel-with-file-name";
	    	let condition = {};
	    	condition["fileName"] = fileName;
	    	condition["token"] = "token";
	    	makePostRequest(linkExport, condition);
		}
	})
	
	$("#btnExportExcel").unbind().bind('click', function(e){
		e.preventDefault();
    	var linkExport = BASE_URL + CONTROLLER_URL + "/export-excel";
    	doExportExcelWithToken(linkExport, "token", setConditionSearch());
	})
		if ((typeof IS_SUBMIT !== 'undefined' && IS_SUBMIT) || IS_ERROR || sessionKey == '' || sessionKey == null ) {
			$('#btnSave').unbind()
		} else {
			$('#btnSave').unbind().bind('click', function(e) {
				e.preventDefault();
				let url = CONTROLLER_URL + "/save-data-import";
				ajaxSubmit(url, setConditionSearch(), e)
			})
		}
//	$('#btnSave').unbind().bind('click', function(e){
//		e.preventDefault();
//		let url = CONTROLLER_URL + "/save-data-import";
//		ajaxSubmit(url, setConditionSearch(), e)
//	})
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + CONTROLLER_URL + "/ajaxList",
		type : 'POST',
		setData : setConditionSearch
	});
	if(sessionKey != '' && sessionKey != null){
		if(IS_ERROR || (typeof IS_SUBMIT !== 'undefined' && IS_SUBMIT)){
			$('#btnSave').removeClass('not-active').addClass('not-active');
		} else {
			$('#btnSave').removeClass('not-active');
		}
	} 
	//check valid form before choose file
	$('#filePickTmp').on('click',function(){
		if($('#fmUpload').valid()){
			 $('div.moxie-shim input[type=file]').trigger('click');
		} else {
			
		}
	})
})

function setConditionSearch(){
	let condition = {};
	condition["sessionKey"] = $('#sessionKey').val();
	condition["fileName"] = $('#fileName').val();
	condition["isError"] = $('#isError').val();
	if($('#hasEdit').val() != undefined){
		condition["hasEdit"] = $('#hasEdit').val();
	}
	return condition;
}

function detailMessageImport(e) {
	var msg = $(e).find('input').val();
	$('#import-message-error-detail').addClass('error');
	var modalContent = $('#import-message-error-detail');
	modalContent.html(msg);

	// Show modal
	if (msg != '' && msg != undefined) {
		$('#modal-import-message-error').modal({
			"show": true
		});
	}
};

function detailMessageWarningImport(e) {
	var msg = $(e).find('input').val();
	$('#import-message-warning-detail').addClass('warning');
	var modalContent = $('#import-message-warning-detail');
	modalContent.html(msg);

	// Show modal
	if (msg != '' && msg != undefined) {
		$('#modal-import-warning-error').modal({
			"show": true
		});
	}
};