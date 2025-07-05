$(document).ready(function() {
	loadAuthorityList();
	
	//on change company
	$(".select-company").on('change', function(event) {
		loadAuthorityList();
	});
	
	// check all access
	$(document).on('click', 'table .check-all-access', function() {
		var checked = $(this).is(':checked');
		$(this).closest('tbody').find('.check-access').prop('checked', checked);
        if(!checked) {
        	$(this).closest('tbody').find('.check-all-edit').prop('checked', checked);
        	$(this).closest('tbody').find('.check-edit').prop('checked', checked);
        }
    });
	
	// check all disp
	$(document).on('click', 'table .check-all-disp', function() {
		var checked = $(this).is(':checked');
		
		$(this).closest('tbody').find('.check-disp').prop('checked', checked);
    });
	
	// check all edit
	$(document).on('click', 'table .check-all-edit', function() {
		var checked = $(this).is(':checked');
		$(this).closest('tbody').find('.check-edit').prop('checked', checked);
        //if(checked) {
        	//$(this).closest('tbody').find('.check-all-access').prop('checked', checked);
        	//$(this).closest('tbody').find('.check-access').prop('checked', checked);
        //}
    });
	
	// check access
    $(document).on('click', 'table .check-access', function () {
        var checked = $(this).is(':checked');
        if (checked) {
            var checkAll = validationCheckedList('table .check-access');
            $('table .check-all-access').prop('checked', checkAll);
        } else {
        	$(this).parents('.item-tr').children().filter('.item-td-check-edit').children().prop('checked', false);
            $('table .check-all-access').prop('checked', checked);
            //var checkAllEdit = validationCheckedList('table .check-edit');
            //$('table .check-all-edit').prop('checked', checkAllEdit);
        }
    });

    // check disp
    $(document).on('click', 'table .check-disp', function () {
        var checked = $(this).is(':checked');
        if (checked) {
            var checkAll = validationCheckedList('table .check-disp');
            $('table .check-all-disp').prop('checked', checkAll);
        } else {
            $('table .check-all-disp').prop('checked', checked);
        }
    });

    // check edit
    $(document).on('click', 'table .check-edit', function () {
        var checked = $(this).is(':checked');
        if (checked) {
        	$(this).parents('.item-tr').children().filter('.item-td-check-access').children().prop('checked', true);
            var checkAllEdit = validationCheckedList('table .check-edit');
            $('table .check-all-edit').prop('checked', checkAllEdit);
            //var checkAll = validationCheckedList('table .check-access');
            //$('table .check-all-access').prop('checked', checkAll);
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

 function ajaxLoadAuthorityByRole(rowRoleSelected) {
	 var roleId = rowRoleSelected.length > 0 ? rowRoleSelected.data("role-id") : null;
		$.ajax({
			  async : false,
		      type  : "GET",
		      url   : BASE_URL + "authority/ajax/edit",
		      data  : {
		    	  "roleId" : roleId
		      },
		      success: function( data ) {
		          $("#boxRoleByFunction").html(data);
		      }
		});
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

	 $('#roleByFunctionTableId tbody tr').each(function(key, val) {
		 authority.push({
			 "roleId" 			:   $("#roleId" + key).val(),
			 "itemId" 			:	$("#itemId" + key).val(),
			 "authorityType" 	:	$("#authorityType" + key).val(),
			 "canAccessFlag" 	:	$("#canAccessFlg" + key).is(":checked"),
			 "canDispFlg" 		:	$("#canDispFlg" + key).is(":checked"),
			 "canEditFlg" 		:	$("#canEditFlg" + key).is(":checked")
		  });
	 });

	 resultList["roleId"] = $("input[name='roleId']").val();
	 resultList["data"] = authority;

	return JSON.stringify(resultList);
}
 
 function ajaxLoadAuthority(companyId) {
		$.ajax({
			  async : false,
		      type  : "GET",
		      url   : BASE_URL + "authority/ajax/list",
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
		$('#boxRoleByFunction').html("");
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


