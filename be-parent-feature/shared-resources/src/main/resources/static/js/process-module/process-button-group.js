$(document).ready(function() {
	$('.btn-process-group').on('click',function(e){
		e.preventDefault();
		var buttonType = $(this).data("button-type");
		if(buttonType === 'SUBMIT' || buttonType === 'APPROVE' || buttonType === 'RETURN' || buttonType === 'ASSIGN'){
			$("#memberAssignIds").addClass("j-required");
		}else{
			$("#memberAssignIds").removeClass("j-required");
		}
		
		if(buttonType === 'REFERENCE'){
			$("#memberReferenceIds").addClass("j-required");
		}else{
			$("#memberReferenceIds").removeClass("j-required");
		}
		
		if ($(".j-form-validate").valid()) {
			if(validOZForm()){
				var button = $(this);
				var mainFileId = $("#mainFileId").val();
				var docState = $("#docState").val();
				var docName = $("#docName").val();
				if((null == mainFileId || 'null' == mainFileId || "" == mainFileId) && docState == '000'){
					if('ACTIVITY' === $('#processType').val()){
						var validator = $(".j-form-validate").validate();
						if((null == docName || 'null' == docName || "" == docName)){
							validator.showErrors({ 'docName' : MES_ERROR_HASNT_FILE});
						}else{
							validator.showErrors({ 'docName' : MES_NOT_YET_SAVE_FILE});
						}
					}else{
						popupConfirm(MES_SUBMIT_HASNT_FILE, function (result) {
							if(result){
								initValueAction(button);
							}
						});
					}
				}else{
					initValueAction(button);
				}
			}
		}
	});
	
	$("#processPopupConfirm").on('show.bs.modal', function() {
		setTimeout(function() {
			$("#confirmNote").focus();
		}, 1000);
	});
	
//	$('#confirmNote').keyup(function(e){
//		var valConfirmNote = $(this).val();
//		var valConfirmNoteSpecial = valConfirmNote.match(/(\r\n|\n|\r)/g);
//		var lengthSpecial = 0;
//		
//		if (valConfirmNoteSpecial != null ){
//			lengthSpecial = valConfirmNoteSpecial.length;
//		}
//note		$('#confirmNote').attr('maxlength', 255 - lengthSpecial);
		
//		var lengthActual = valConfirmNote.length + lengthSpecial
//		if (lengthActual > 255 ){
//note		$(this).val(valConfirmNote.substring(0,255 - lengthSpecial));
//			$('#confirmNote-error').html(AREA_MAX_LENGTH_255);
//			$('.btn-process-confirm').attr('disabled', true);
//		}else{
//			$('#confirmNote-error').html('');
//			$('.btn-process-confirm').attr('disabled', false);
//		}
//	});
	
});

function initValueAction(button){
	console.log("process-button-group", "initValueAction");

	var btnName = $(button).data("button-name");
	$("#processPopupConfirm").find(".actionName").html("<b>[" + btnName + "]</b>");
	
	// Control action
	$("#action").val("submit");
	
	var actButtonType = $(button).data("button-type");
	if( actButtonType == 'SUBMIT' ) {
		$("#isSubmit").val(true);
	}
	
	var actButtonIdStr = $(button).data("button-id");
	$("#actButtonIdStr").val(actButtonIdStr);
	$("#buttonId").val(actButtonIdStr);
	
	$("#actButtonType").val(actButtonType);
	$("#buttonType").val(actButtonType);
	
	var actButtonValue = $(button).data("button-value");
	$("#actButtonValue").val(actButtonValue);
	$("#buttonValue").val(actButtonValue);
	
	var isSave = $(button).data("is-save");
	$("#isSave").val(isSave);
	
	var isSign = $(button).data("is-sign");
	$("#isSign").val(isSign);
	
	var isAuthenticate = $(button).data("is-authenticate");
	$("#isAuthenticate").val(isAuthenticate);
	
	var isExportPdf = $(button).data("is-export-pdf");
	$("#isExportPdf").val(isExportPdf);
	
	var fieldSign = $(button).data("field-sign");
	$("#fieldSign").val(fieldSign);
	
	var functionCode = $(button).data("function-code");
	$("#functionCode").val(functionCode);
	
	var buttonName = $(button).data("button-name");
	$("#buttonName").val(buttonName);
	
	var buttonNamePassive = $(button).data("button-name-passive");
	$("#buttonNamePassive").val(buttonNamePassive);
	
	var displayHistory = $(button).data("display-history");
	$("#displayHistory").val(displayHistory);
	
	if( actButtonType == 'REFERENCE' ) {
		blockbg();
		setTimeout(save, 10);
	}else {
		$("#processPopupConfirm").modal();
	}
	
}

function validOZForm() {
	var condition = {};

    condition["processDeployId"] = $("#processDeployId").val();
    condition["docState"] = $("#docState").val();
    condition["validJson"] = $("#validJson").val();
	var res = true;
	
    $.ajax({
		type: "POST",
		url: BASE_URL +  "oz-doc-main-file/validate",
		data: condition,
		async: false,
		success : function(data, textStatus, request) {
			var msgFlag = request.getResponseHeader("msgFlag");
			if( "1" == msgFlag ) {
				popupAlert(data);
				res = false;
			}
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});	

    return res;
}