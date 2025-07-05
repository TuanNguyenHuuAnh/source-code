$(document).ready(function () {
  //on change company
	$(".select-company").on('change', function(event) {
		loadTable();
	})
});
function search(element, event, dataResult) {
	var data = setConditionSearch(element, dataResult);
	if (dataResult.status !== undefined && dataResult.status == 1) {
		ajaxSearch("register-svc/ajaxList", data, 'tableList', element, event);
	} else {
		ajaxSearch("register-svc/ajaxList", data, 'error-message', element, event);
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
	return condition;
}

function loadTable(){
	var companyId = $("#companyId").val();
	$.ajax({
	      type  : "GET",
	      url   : BASE_URL + "register-svc/get-by-company-id",
	      data  : {
	    	  "companyId" : companyId
	      },
	      success: function( data ) {
	          $("#tableList").html(data);
	      }
	});
}