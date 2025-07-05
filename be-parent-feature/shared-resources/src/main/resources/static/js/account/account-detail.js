$(document).ready(function() {
	var accountid = $("#id-account-id").val();
	
	//loadRoleForAccount(accountid);
	
	loadOrgForAccount(accountid);
	
	$("#linkList").on("click", function(event) {
		var url = BASE_URL + "account/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click edit
	$("#btnEditUser").on("click", function(event) {
		var id = $("#id-account-id").val();
		var url = BASE_URL + "account/edit?id=" + id;
		// Redirect to page edit
		ajaxRedirect(url);
	});
	
	//on click delete
	$("#btnDeleteUser").on("click", function(event) {
		// Redirect to page delete
		deleteShareholder(event);
	});
	
	$("#btnCancel").on("click", function(event) {
		var url = BASE_URL + "account/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : GROUP_LABEL,
		search : true
	});
	
	$("input[type=checkbox]").prop("disabled", true);
});

/* Load list role for account */ 
function loadRoleForAccount(accountId) {
    $.ajax({
        type  : "POST",
        url   : BASE_URL + "account/build_role_for_account_detail",
        data  : {
        	"accountId" : accountId
        },
        success: function (data , textStatus, resp ) {
            $("#id-role-for-account").html(data);
        }
    });
}

function deleteShareholder(event) {
	event.preventDefault();
	// Prepare data
	var id = $("#id-account-id").val();

	popupConfirm(MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = BASE_URL + "account/delete?id=" + id;
			
			// Redirect to page detail
			ajaxRedirect(url);
		}
	});
}

/* Load list org for account */ 
function loadOrgForAccount(accountId) {
    $.ajax({
        type  : "POST",
        url   : BASE_URL + "account/build_org_for_account_detail",
        data  : {
        	"accountId" : accountId
        },
        success: function (data ) {
        	$("#id-org-for-account").html(data);
        }
    });
}