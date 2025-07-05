$(document).ready(function() {
	
	/* -------------- Action list role for account ----------------------------------- */
	// Add a role for account
	$('#id-add-role-for-account').on('click', function() {

		$.ajax({
	        type  : "POST",
	        url   : BASE_URL + "account/add_role_for_account",
	        data  : {
	        	"accountId" : $(this).parents("#id-main-account").find("#id-account-id").val()
	        },
	        success: function (data , textStatus, resp ) {
	        	 $("#id-role-for-account").text('');
	            $("#id-role-for-account").html(data);
	            $("#id-cancel-role-for-account").show();
	            $("#id-add-role-for-account").hide();
	            $('.datepicker').datepickerUnit({
	        		format: DATE_FORMAT,
	                changeMonth: true,
	                changeYear: true,
	                autoclose: true,
	        		language : APP_LOCALE.toLowerCase(),
	                keyboardNavigation : true
	        	});
	        }
	    });
    });
	
	$('#id-cancel-role-for-account').on('click', function() {
		loadRoleForAccount($(this).parents("#id-main-account").find("#id-account-id").val());
	});
	
	// Delete a role for account
	$('.delete-role-for-account').on('click', function (event) {
		event.preventDefault();
	    // Prepare data
	    var accountId = $(this).parents("#id-main-account").find("#id-account-id").val();
	    var roleId = $(this).parents('tr').data('role-id');
		popupConfirm( MSG_DEL_CONFIRM, function(result) {
			if( result ){
				$.ajax({
			        type  : "POST",
			        url   : BASE_URL + "account/delete_role_for_account",
			        data  : {
			        	"accountId" : accountId,
			        	"roleId" : roleId
			        },
			        success: function (data , textStatus, resp ) {
			        	$("#id-role-for-account").text('');
			            $("#id-role-for-account").html(data);
			            $('.datepicker').datepickerUnit({
			        		format: DATE_FORMAT,
			                changeMonth: true,
			                changeYear: true,
			                autoclose: true,
			        		language : APP_LOCALE.toLowerCase(),
			                keyboardNavigation : true
			        	});
			            $("#id-cancel-role-for-account").hide();
			            $("#id-add-role-for-account").show();
			        }
			    });
			}
		});
    });
	
	// update a role for account
	$('#id-update-role-for-account').unbind('click').bind('click', function (event) {
		var datas = $(this).parents("#id-form-account").serializeArray();
		$.ajax({
	        type  : "POST",
	        url   : BASE_URL + "account/update_role_for_account",
	        data  : datas, 
	        success: function (data , textStatus, resp ) {
	            $("#id-role-for-account").html(data);
	            $("#id-add-role-for-account").show();
	            $("#id-cancel-role-for-account").hide();
	            $('.datepicker').datepickerUnit({
	        		format: DATE_FORMAT,
	                changeMonth: true,
	                changeYear: true,
	                autoclose: true,
	        		language : APP_LOCALE.toLowerCase(),
	                keyboardNavigation : true
	        	});
	        	/*loadRoleForAccount($("#id-account-id").val());*/
	        }
	    });
    });
});

