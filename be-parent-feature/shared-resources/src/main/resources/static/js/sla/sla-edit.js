$(document).ready(function() {
	$('#form-j-edit').on('keyup keypress', function(e) {
		  var keyCode = e.keyCode || e.which;
		  if (keyCode === 13) { 
		    e.preventDefault();
		    return false;
		  }
		});

	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	
	$("#linkList").unbind().bind("click", function(event) {
		var url = BASE_URL + "sla/list";
		ajaxRedirect(url);
	});

	$('#btnSave, #btnSaveHead').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var values = $("#form-j-edit").serializeArray();
			
			var id = $("#id").val();
			var url = "sla/edit?id=" + id;
			ajaxSubmit(url, values, event);
		}
	});
	
	$("#btnCancel").unbind().bind("click", function(event) {
		var url = BASE_URL + "sla/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$(".j-stf-reset").on('click', function() {
		var row = $(this).parents("tr");
		var id = row.data("sla-step-id");
		
		popupConfirm( MSG_DEL_CONFIRM, function(result) {
			if( result ){
				var url = BASE_URL + "sla/reset-step";
				var data = {
					"id" 	: id,
					"slaId" : $("#id").val()
				}
				makePostRequest( url, data );
			}	
		});
	});
	
	$('.j-stf-edit').on('click', function(event) {
		var row = $(this).parents("tr");
		var id = row.data("sla-step-id");
		
		var url = BASE_URL + "sla/alert-setting?slaStepId="+id;
		ajaxRedirect(url);
	});
	
	//Change process
    $('.select-company').on('change', function(event) {
    	var comId = $(this).val();
    	$('#businessId').val('').trigger('change');
    	$('#processDeployId').val('').trigger('change');
    	$('#slaCalendarTypeId').val('').trigger('change');
    	initBusinessList(comId);
    	initslaCalendarTypeIdList(comId);
    	if(comId != undefined && comId != null && comId != "") {
    		$('#processDeployId').prop('disabled', 'disabled');
    	}	
    });
    
    $("#businessId").on('change', function() {
    	var busId = $(this).val();
    	$('#processDeployId').val('').trigger('change');
    	if(busId != undefined && busId != null && busId !== "") {  		
    		initProcessList(busId);
    	} else {
    		$('#processDeployId').prop('disabled', 'disabled');
    	}	
	});
    //check for adding screen
    initialAddScreen();

	initBusinessList($('#companyId').val());
	initslaCalendarTypeIdList($('#companyId').val());
    
});

function initBusinessList(id) {
    searchCombobox('#businessId', '', 'sla/ajax/getBusinessListByCompanyId',
    	    function data(params) {
    	        var obj = {
    	        		companyId: id
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function initProcessList(id) {
	$('#processDeployId').prop('disabled', '');
    searchCombobox('#processDeployId', '', 'sla/ajax/getProcessListByBusinessId',
    	    function data(params) {
    	        var obj = {
    	        		companyId: id
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function initslaCalendarTypeIdList(id) {
	$('#slaCalendarTypeId').prop('disabled', '');
    searchCombobox('#slaCalendarTypeId', '', 'sla/ajax/getCalendarTypeListByCompanyId',
    	    function data(params) {
    	        var obj = {
    	        		companyId: id
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function initialAddScreen() {
	var comId = $('.select-company').val();
	var name = $('.j-specialCharacters').val();
	if((comId === null || comId === "") && (name === undefined || name === null || name === "")) {
		$('#processDeployId').prop('disabled', 'disabled');
	} else {
		var busId = $("#businessId").val();
    	if(busId === undefined || busId === null || busId === "") {  		
    		$('#processDeployId').prop('disabled', 'disabled');
    	} 
	}
}
