$(document).ready(function(){
	// Save list authority
	$('#btnSaveStatus').on('click', function () {
		var form = $("#form-edit-status-authority");
        var divBody = form.find(".modal-body");
        
        divBody.find(".alert").remove();
        
        $.ajax({
              type  : form.attr('method'),
              url   : form.attr('action'),
              data  : form.serialize(),
              success: function( data ) {
            	  divBody.prepend( data );
              }
        });
    });
	
	$('#popupProcessId').on('change', function () {
		
		var params = [];
		
		var divStatusTable = $("#popupDivStatusTable");
		params.push({
            name : "roleId",
            value : divStatusTable.find("#popupRoleId").val()
        });
		params.push({
            name : "itemId",
            value : divStatusTable.find("#popupItemId").val()
        });
		params.push({
            name : "processId",
            value : $("#popupProcessId").val()
        });
		
		$.ajax({
            type  : "GET",
            url   : BASE_URL + "authority/ajax/status/process",
            data  : params,
            success: function( data ) {
                $("#popupDivStatusTable").html(data);
            }
        });
    });
});