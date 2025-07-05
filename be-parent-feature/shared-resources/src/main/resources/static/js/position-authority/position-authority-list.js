$(document).ready(function() {
	
	loadPositionAuthorityList();
	
	//on change company
	$(".select-company").on('change', function(event) {
		loadPositionAuthorityList();
	});
	
	// check all access
	$(document).on('click', 'table .check-all-access', function() {
        $('table .check-access').prop('checked', $(this).is(':checked'));
    });
	
	// check access
	$(document).on('click', 'table .check-access', function() {
		var checked = $(this).is(':checked');
		if( checked ) {
		    var checkAll = validationCheckedList('table .check-access');
			$('table .check-all-access').prop('checked', checkAll);
		} else {
			$('table .check-all-access').prop('checked', checked);
		}
    });
	
	// Save list authority
	$("#btnSaveAutho").on("click", function (e) {
		e.preventDefault();
		ajaxSaveAuthority();
    });
	
});

 function ajaxLoadPositionAuthorityByPositionId(id){
	$.ajax({
		  async : false, 
	      type  : "GET",
	      url   : BASE_URL + "position-authority/ajax/edit",
	      data  : {
	    	  "positionId" : id
	      },
	      success: function( data ) {
	          $("#positionAuthorityBox").html(data);
	      }
	});
}

 function ajaxSaveAuthority(){
	var rowPositionSelected = $("#positionGrid").find("tr.highlight")
	if(rowPositionSelected.length > 0) {
		$(".j-message-alert").html("");
		
		var form = $("#formAuthority");
	
		$.ajax({
		      type        : form.attr('method'),
		      contentType : "application/json",
		      url         : form.attr('action'),
		      data        : getAuthorityJson(),
		      success     : function( data ) {
		    	  $(".j-message-alert").prepend( data );
		      }
		});
	}
}
 
 /**
  * getAuthorityJson
  * @returns
  */
 function getAuthorityJson() {
	 var resultList = {};
	 var authority = [];
	 
	 

	 $('#roleGrid tbody tr').each(function(key, val) {
		 authority.push({
			 "id" 	   : $("input[name=id" + key + "]").val(),
			 "roleId"  : $("input[name=roleId" + key + "]").val(),
			 "checked" : $("input[name=roleChecked" + key + "]").is(":checked")
		  });
	 });

	 resultList["positionId"] = $("input[name='positionId']").val();
	 resultList["data"] = authority;

	return JSON.stringify(resultList);
}
 
 function ajaxLoadPositionAuthority(companyId){
	$.ajax({
		async : false, 
		type  : "GET",
		url   : BASE_URL + "position-authority/ajax/list",
		data  : {
			"companyId": companyId
		},
		success: function( data ) {
			$("#positionAuthorityList").html(data);
		}
	});
}
 
 function loadPositionAuthorityList(){
	$('#msg').html("");
	$('#positionAuthorityBox').html("");
	var companyId = $('#companyId').val();
	if(companyId != undefined)
		ajaxLoadPositionAuthority(companyId);
	// Load role for position authority
	var rowPositionSelected = $("#positionGrid").find("tr.highlight");
	var positionIdSelected = rowPositionSelected.length > 0 ? rowPositionSelected.data("position-id") : null;
	ajaxLoadPositionAuthorityByPositionId(positionIdSelected);
	//show msg
	if(messageList != null){
		$('#msg').html('<div class="alert alert-'+messageList.messages[0].status+' alert-dismissible">'
				+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
				+'<h4><i class="icon fa fa-'+messageList.messages[0].status+'"></i>Alert!</h4>'
				+'<div/>'+messageList.messages[0].content+'</div>');
	}
 }


