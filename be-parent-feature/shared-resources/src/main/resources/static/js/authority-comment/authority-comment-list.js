$(document).ready(function() {
    // Load function
	var companyId = $('#companyId').val();
	ajaxLoadFuction(null, null, companyId);
	
	//onchanege company
	$('#companyId').on('change', function() {
		companyId = $(this).val();
		ajaxLoadFuction(null, null, companyId);
	});
	
	// Save list authority
	$("#btnSave").on("click", function (e) {
		e.preventDefault();
		ajaxSaveAuthority();
    });
});

function ajaxLoadFuction(businessId, processId, companyId) {
	$('#msg').html('');
	$.ajax({
		async : false,
		type  : "GET",
		url   : BASE_URL + "authority-comment/ajax/list",
		global: false,
		beforeSend: function(xhrObj) {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			xhrObj.setRequestHeader(header, token);
		},
		data  : {
		    "businessId" : businessId,
		    "processId" : processId,
		    "companyId"	: companyId
		},
		success: function(data) {
			$("#boxFunction").html(data);
		},
		complete: function() {
			//load step
			var functionSelected = $("#functionTableId").find("tr.highlight");
			ajaxLoadAuthorityByFunction(functionSelected);
			//show msg
			if(messageList != null){
				$('#msg').html('<div class="alert alert-'+messageList.messages[0].status+' alert-dismissible">'
						+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
						+'<h4><i class="icon fa fa-'+messageList.messages[0].status+'"></i>Alert!</h4>'
						+'<div/>'+messageList.messages[0].content+'</div>');
			}
		}
	});
}

function ajaxLoadAuthorityByFunction(functionSelected){
	$.ajax({
		async : false,
		type  : "GET",
		url   : BASE_URL + "authority-comment/ajax/edit",
		/*global: false,
		beforeSend: function(xhrObj) {
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			xhrObj.setRequestHeader(header, token);
		},*/
		data  : {
		    "businessId" : $("#businessId").val(),
		    "processId" : $("#processId").val(),
		    "functionId" : functionSelected.data("function-id"),
		    "companyId"	: $('#companyId').val()
		},
		success: function( data ) {
			$("#boxStep").html(data);
		},
		complete: function() {
			 checkAll();
		}
	});
}

function getAuthorityJson() {
	 var authority = [];
	 $('#stepCommentTableId tbody tr').each(function(key, val) {
		 var row = $(this);
		 if(row.hasClass("item-tr-data")){
			 authority.push({
				 "id" 				:	row.data("id"),
				 "businessId" 		:   $("#businessId").val(),
				 "processDeployId" 	:	$("#processId").val(),
				 "functionId" 		:	row.data("function-id"),
				 "stepDeployId" 	:	row.data("step-id"),
				 "commentFlag" 		:	row.find(".check-comment").is(":checked"),
				 "opinionFlag" 		:	row.find(".check-opinion").is(":checked")
			  });
		 }
	 });
	return JSON.stringify(authority);
}

function ajaxSaveAuthority() {
	var functionSelected = $("#functionTableId").find("tr.highlight");
	if(functionSelected.length > 0) {
		$(".j-message-alert").html("");
		$.ajax({
		      type  : 'POST',
		      contentType : "application/json",
		      url   : BASE_URL + "authority-comment/ajax/edit",
		      data  : getAuthorityJson(),
		      success: function( data ) {
		    	  //load step
		    	  ajaxLoadAuthorityByFunction(functionSelected);
		    	  //show message
		    	  $(".j-message-alert").prepend( data );
		      }
		});
	}
}

function checkAll(){
	var chkAllComment = true;
	var chkAllOpinion = true;
	$('#stepCommentTableId > tbody').find('tr').each(function(e){
		var display = $(this).css('display');
		if(display != 'none'){
			var chkComment = $(this).find('.check-comment').prop('checked');
			if(chkComment == false){
				chkAllComment = false;
			}
			var chkOpinion = $(this).find('.check-opinion').prop('checked');
			if(chkOpinion == false){
				chkAllOpinion = false;
			}
		}			
	});
	$(".check-all-comment").prop('checked', chkAllComment);
	$(".check-all-opinion").prop('checked', chkAllOpinion);
}