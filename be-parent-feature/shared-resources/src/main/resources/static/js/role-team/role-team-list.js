$(document).ready(function() {
	loadRoleTeamList();
	
	//on change company
	$(".select-company").on('change', function(event) {
		loadRoleTeamList();
	});
	// check all access
	$(document).on('click', 'table .check-all-access', function() {
        $('table .check-access').prop('checked', $(this).is(':checked'));
    });
	
	// check all disp
	$(document).on('click', 'table .check-all-disp', function() {
		var checked = $(this).is(':checked');
		
        $('table .check-disp').prop('checked', checked);
    });
	
	// check all edit
	$(document).on('click', 'table .check-all-edit', function() {
		var checked = $(this).is(':checked');
		
        $('table .check-edit').prop('checked', checked);
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
	
	// check disp
	$(document).on('click', 'table .check-disp', function() {
		var checked = $(this).is(':checked');
		if( checked ) {
			var checkAll = validationCheckedList('table .check-disp');
			$('table .check-all-disp').prop('checked', checkAll);
		} else {
			$('table .check-all-disp').prop('checked', checked);
		}
    });
	
	// check edit
	$(document).on('click', 'table .check-edit', function() {
		var checked = $(this).is(':checked');
		if( checked ) {
			var checkAllEdit = validationCheckedList('table .check-edit');
			$('table .check-all-edit').prop('checked', checkAllEdit);
		} else {
			$('table .check-all-edit').prop('checked', checked);
		}
    });
	
	// Save list authority
	$("#btnSaveAutho").on("click", function (e) {
		e.preventDefault();
		ajaxSaveAuthority();
    });
});

 function ajaxLoadAuthorityByRole(rowRoleSelected){
	var teamId = rowRoleSelected.length > 0? rowRoleSelected.data("team-id"): null;
	$.ajax({
		  async : false,
	      type  : "GET",
	      global: false,
		  beforeSend: function(xhrObj) {
		    	var token = $("meta[name='_csrf']").attr("content");
			  	var header = $("meta[name='_csrf_header']").attr("content");
			  	xhrObj.setRequestHeader(header, token);
		  },
	      url   : BASE_URL + "role-team/get-list-role-for-team",
	      data  : {
	    	  "team-id" : teamId
	      },
	      success: function( data ) {	    	  
	          $("#boxRoleByFunction").html(data);
	      }
	});
}

 function ajaxSaveAuthority(){
	var rowRoleSelected = $("#roleTableId").find("tr.highlight");
	if(rowRoleSelected.length > 0) {
		$(".j-message-alert").html("");	
		blockbg();
		$.ajax({
		      type  : 'POST',	      
		      url   : BASE_URL + "role-team/post-list-role-for-team",
		      data  : {'roleTeam': getAuthorityJson()},
		      success: function( data ) {
		    	  unblockbg();
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

	 $('#roleByFunctionTableId tbody tr').each(function(key, val) {
		 authority.push({
			 "roleId" 			:   parseInt($("#roleId" + key).val()),			
			 "flgChecked" 		:	$("#flgChecked" + key).is(":checked")
		  });
	 });

	resultList["teamId"] = parseFloat($("#teamId").val());
	resultList["data"] = authority;
	return JSON.stringify(resultList);
}
 
function ajaxLoadRoleTeam(companyId) {
		$.ajax({
			  async : false,
		      type  : "GET",
		      url   : BASE_URL + "role-team/ajax/list",
		      data  : {
		    	  "companyId" : companyId
		      },
		      success: function( data ) {
		          $("#roleTeamList").html(data);
		      }
		});
}

function loadRoleTeamList(){
		$('#msg').html("");
		$('#boxRoleByFunction').html("");
		var companyId = $('#companyId').val();
		if(companyId != undefined)
			ajaxLoadRoleTeam(companyId);
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


