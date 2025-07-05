$(document).ready(function () {
	loadTable();
	//on change company
	$("#companyId").on('change', function(event) {
		event.preventDefault();
		loadTable();
	});
	
	//on change formType
	$("#formType").change(function(event) {
		event.preventDefault();
		loadTable();
	})
});
function search(element, event, dataResult) {
	var data = setConditionSearch(element, dataResult);
	if (dataResult.status !== undefined && dataResult.status == 1) {
		ajaxSearch("jpm-register-svc/ajaxList", data, 'tableList', element, event);
	} else {
		ajaxSearch("jpm-register-svc/ajaxList", data, 'error-message', element, event);
	}
}

function setConditionSearch(element, dataResult) {
	$('#message-alert').hide();
	var condition = {};
	condition['companyId'] = $(element).find('.company-id').val();
	condition['companyName'] = $(element).find('.company-name').val();
	condition['status'] = dataResult.status === undefined ? 0
			: dataResult.status;
	condition['message'] = dataResult.message === undefined ? ''
			: dataResult.message;
	condition['serviceName'] = $(element).find('.service-name').val();
	condition['formType'] = $("#formType").val();
	return condition;
}

function loadTable(){
	var companyId = $("#companyId").val();
	var formType = $("#formType").val();
	$.ajax({
	      type  : "GET",
	      url   : BASE_URL + "jpm-register-svc/get-by-company-id",
	      data  : {
	    	  "companyId" : companyId,
	    	  "formType" : formType
	      },
	      success: function( data ) {
	          $("#tableList").html(data);
	      }
	});
}