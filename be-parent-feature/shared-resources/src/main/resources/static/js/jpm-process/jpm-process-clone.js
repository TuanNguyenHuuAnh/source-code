$(document).ready(function () {
	
	$('#btn-clone-process-submit').click(function() {
    	if ($("#clone-process-form").valid()) {
    		var url = "jpm-process/clone-process";
	    	var dataForm = $("#clone-process-form").serializeArray();
	    	ajaxSubmitPopup(url, dataForm,"#clone-modal" ,event);
    	}
    });
	$("#companyCloneCombobox").on('change', function(event){
    	var val = $(this).val();
    	if(val == ""){
    		$("#businessCloneCombobox").val("");
    		$("#businessCloneCombobox").attr('disabled','true');
    	}else{
    		$("#businessCloneCombobox").removeAttr('disabled');
    		var url = "jpm-process-deploy/ajax/load-business";
    		var condition = {};
    		condition["companyId"] = val;
    		loadDataCombobox(url, condition, "#businessCloneCombobox", event);
    	}
    });
});  