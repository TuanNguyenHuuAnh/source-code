$(document).ready(function() {
	loadAuthorityList();
    // Load function by roles
	var rowRoleSelected = $("#roleTableId").find("tr.highlight");
	ajaxLoadAuthorityByRole(rowRoleSelected);
	
    $('#roleTableId').tableFilterable({
        filters: [
        	{
                filterSelector: '#companyName',
                event: 'keyup',
                filterCallback: function($tr, filterValue) {
                    // filterValue is the content of the name input
                    return  $tr.children('td[data-tfilter1="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase()) != -1;
                }
            },
            {
                 filterSelector: '#roleName',
                 event: 'keyup',
                 filterCallback: function($tr, filterValue) {
                     // filterValue is the content of the name input
                     return  $tr.children('td[data-tfilter2="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase()) != -1;
                 }
             }
        ]
    });
    
	$("#roleTableId .j-role-edit").on("click", function () {
		$(this).parents("table").find(".highlight").removeClass("highlight");
		
		var rowRoleSelected = $(this).parents("tr");
		ajaxLoadAuthorityByRole(rowRoleSelected);
		rowRoleSelected.addClass("highlight");
    });
	
	// Save list authority
	$("#btnSaveAutho").on("click", function (e) {
		e.preventDefault();
		ajaxSaveAuthority();
    });
	
	$("#companyId").on('change', function(event) {
		loadAuthorityList();
	});
	
	$("#companyIdBusiness").on('change', function(event) {
		var rowRoleSelected = $("#roleTableId").find("tr.highlight");
		ajaxLoadAuthorityByRole(rowRoleSelected);
	});
});

 function ajaxLoadAuthorityByRole(rowRoleSelected) {
	 var companyId = $('#companyIdBusiness').val();
	 //if(rowRoleSelected.length > 0) {
		$.ajax({
		      type  : "GET",
		      url   : BASE_URL + "authority-process/ajax/edit",
		      global: false,
		      beforeSend: function(xhrObj) {
			    	var token = $("meta[name='_csrf']").attr("content");
				  	var header = $("meta[name='_csrf_header']").attr("content");
				  	xhrObj.setRequestHeader(header, token);
		      },
		      data  : {
		    	  "roleId" 		: rowRoleSelected.data("role-id"),
		    	  "businessId" 	: $("#business-list").val(),
		    	  "processId" 	: $("#process-list").val(),
		    	  "companyId"	: companyId
		      },
		      success: function( data ) {
		          $("#boxRoleByFunction").html(data);
		      }
		});
	 //}
}

 function ajaxSaveAuthority() {
	var rowRoleSelected = $("#roleTableId").find("tr.highlight");
	if(rowRoleSelected.length > 0) {
		$(".j-message-alert").html("");
		
		var form = $("#formAuthority");
	
		$.ajax({
		      type  : form.attr('method'),
		      contentType : "application/json",
		      url   : form.attr('action'),
		      data  : getAuthorityJson(),
		      success: function( data ) {
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
	 
	 $('#roleServiceByFunctionTableId tbody tr').each(function(key, val) {
		 var row = $(this);
		 if(row.hasClass("item-tr-data")){
			 authority.push({
				 "roleId" 			:   row.find(".j-role-id").val(),
				 "itemId" 			:	row.find(".j-item-id").val(),
				 "authorityType" 	:	row.find(".j-authority-type").val(),
				 "canAccessFlag" 	:	row.find(".check-access").is(":checked"),
				 "canDispFlg" 		:	row.find(".check-disp").is(":checked"),
				 "canEditFlg" 		:	row.find(".check-edit").is(":checked")
			  });
		 }
	 });
	 $('#roleByFunctionTableId tbody tr').each(function(key, val) {
		 var row = $(this);
		 if(row.hasClass("item-tr-data")){
			 authority.push({
				 "roleId" 			:   row.find(".j-role-id").val(),
				 "itemId" 			:	row.find(".j-item-id").val(),
				 "authorityType" 	:	row.find(".j-authority-type").val(),
				 "canAccessFlag" 	:	row.find(".check-access").is(":checked"),
				 "canDispFlg" 		:	row.find(".check-disp").is(":checked"),
				 "canEditFlg" 		:	row.find(".check-edit").is(":checked")
			  });
		 }
	 });

	 // resultList["roleId"] = $("input[name='roleIdSelected']").val();
	 
	 resultList["roleId"] = $("#roleTableId").find("tr.highlight").data("role-id")
	 
	 // console.log('resultList["roleId"]', resultList["roleId"]);
	 
	 resultList["data"] = authority;
	 resultList["businessId"] = $("#business-list").val(); 
	 resultList["processId"] = $("#process-list").val() 

	 console.log(resultList);
	return JSON.stringify(resultList);
}

function ajaxLoadAuthority(companyId) {
		$.ajax({
			  async : false,
		      type  : "GET",
		      url   : BASE_URL + "authority-process/ajax/list",
		      data  : {
		    	  "companyId" : companyId
		      },
		      success: function( data ) {
		          $("#authorityList").html(data);
		      }
		});
} 
 
function loadAuthorityList(){
		$('#msg').html("");
		var companyId = $('#companyId').val();
		if(companyId != undefined)
			ajaxLoadAuthority(companyId);
		var rowRoleSelected = $("#roleTableId").find("tr.highlight");
		ajaxLoadAuthorityByRole(rowRoleSelected);
		//show msg
		if(messageList != null){
			$('#msg').html('<div class="alert alert-'+messageList.messages[0].status+' alert-dismissible">'
					+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
					+'<h4><i class="icon fa fa-'+messageList.messages[0].status+'"></i>Alert!</h4>'
					+'<div/>'+messageList.messages[0].content+'</div>');
		}
}

