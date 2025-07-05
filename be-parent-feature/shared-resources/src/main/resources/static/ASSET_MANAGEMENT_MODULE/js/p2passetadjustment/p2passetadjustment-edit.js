$(function(){

	let CURRENT_URL = window.location.href;
	let findex = CURRENT_URL.indexOf(BASE_URL);
	let lindex = CURRENT_URL.lastIndexOf('?id');
	let urlType = CURRENT_URL.substring(findex, lindex);
	if(urlType == BASE_URL + 'asset-adjust/detail') {
		$("#widgetApprovalComment").attr('readonly','readonly');
	}
	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-adjust/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-adjust/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-adjust/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	
	
	// save draft
	$("#button-approval-module-save-draft-id").on("click", function(event) {
		var url = "asset-adjust/edit";
		
		// xóa required
		$(':input').removeClass('j-required');
		
		doSave(url, "save");
	});
	
	$('#button-approval-module-submit-id').click(function(e){
		var url = "asset-adjust/submit";
		doSave(url, "submit");
	});
	
	$("#assetTypeCode").on('change', function(event){
		var value = $("#select2-assetTypeCode-container").text();
		$("#assetTypeName").val(value);
	});
	
    $("#btnAdd").on('click', function(event){
        showModal();
    });
    
	initData();;
	
	if (EDIT_DTO.prId != null){
		
		$("#refPr").attr('href', BASE_URL + 'pr/detail?id=' + EDIT_DTO.prId);
		$("#refPr").attr('target', '_blank');
		
		$('#prNo').css('color', '#3c8dbc');
		$('#prNo').css('cursor', 'pointer');
		
		$("#prId").val(EDIT_DTO.prId);
	}
	
	if (EDIT_DTO.poId != null){
		
		$("#refPo").attr('href', BASE_URL + 'po/detail?id=' + EDIT_DTO.poId);
		$("#refPo").attr('target', '_blank');
		
		$('#poNo').css('color', '#3c8dbc');
		$('#poNo').css('cursor', 'pointer');
		
		$("#poId").val(EDIT_DTO.poId);
	}
	
	if (EDIT_DTO.paymentId != null){
		var url = 'payment-ge';
		if (EDIT_DTO.paymentType == 0){
			url = 'payment-pr-po';
		}
		$("#refPayment").attr('href', BASE_URL + url + '/detail?id=' + EDIT_DTO.paymentId);
		$("#refPayment").attr('target', '_blank');
		
		$('#paymentNo').css('color', '#3c8dbc');
		$('#paymentNo').css('cursor', 'pointer');
		
		$("#paymentId").val(EDIT_DTO.paymentId);
	}
	
	if (EDIT_DTO.receiptId != null){
		
		$("#refReceipt").attr('href', BASE_URL + 'p2pgrgeneral/detail?id=' + EDIT_DTO.receiptId);
		$("#refReceipt").attr('target', '_blank');
		
		$('#receiptNo').css('color', '#3c8dbc');
		$('#receiptNo').css('cursor', 'pointer');
		
		$("#receiptId").val(EDIT_DTO.receiptId);
	}
});

function initData(){

}

function doSave(url,type) {
	if ($(".j-form-validate").valid()) {
		if (type == "submit"){
			popupConfirm(MSG_CONFIRM, function(result) {
				if (result) {
					// remove all disabled
					$('input:disabled, select:disabled, textarea:disabled').removeAttr('disabled');
					
					$("#commentsId").val($("#widgetApprovalComment").val());
					
				    var condition = $("#form-edit").serializeArray();
				    
				    ajaxSubmit(url, $.param(condition), event);
				    
				    $("#message-list").html('');
				}
			});
		} else {
			// remove all disabled
			$('input:disabled, select:disabled, textarea:disabled').removeAttr('disabled');
			
			$("#commentsId").val($("#widgetApprovalComment").val());
			
		    var condition = $("#form-edit").serializeArray();
		    
		    ajaxSubmit(url, $.param(condition), event);
		    
		    $("#message-list").html('');
		}
	} else {
		setTimeout(function() {
			$(".j-form-validate").find(":input.error:first").focus();
		}, 500);
	}
}

function getHtmlSelect(text, value){
	 return '<option value="'+ value +'">' + text + '</option>';
}

function showModal() {
	var url = "asset-adjust/popup/list";
	
	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		success : function(data) {
			var content = $(data).find('.body-content');
			$("#myModal .modal-content").html(content);
			$('#myModal').modal('show');
		    $('#myModal').css('z-index', 5000);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
 }

/**
 * Set param for form
 * @returns
 */
function setParamForForm(e) {
	// validate GUI
	if ($(".j-form-validate").valid()) {
		// remove all disabled
		//$('input:disabled, select:disabled, textarea:disabled').removeAttr('disabled');
		
	} else {
		setTimeout(function() {
			$(".j-form-validate").find(":input.error:first").focus();
		}, 500);
		
		return false;
	}
	
	return true;
}