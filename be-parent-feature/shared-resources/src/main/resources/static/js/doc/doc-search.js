$(document).ready(function () {
	// Init search agent code
    searchCombobox('#formId', '', 'form/get-list-for-select',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $('#companyId').val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
    //on change company
	$("#companyId").on('change', function(event) {
		$('#formId').val('').trigger('change');
		$('#statusCode').val('').trigger('change');
	});
    
	searchCombobox('#statusCode', '', 'form/status/get-list-for-select',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $('#companyId').val(),
    	            formId: $('#formId').val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
    
    // Change form
    $('#formId').change(function(event) {
    	$('#statusCode').val('').trigger('change');
    	
    	if( $('#formId').val() ) {
    		$("#btnCreate").show();
    	} else {
    		$("#btnCreate").hide();
    	}
    });
    
    // Search
    $('#btnSearch').click(function (event) {
        search($(this), event);
    });

    $('.date').datepickerUnit({
		format: DATE_FORMAT,
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : APP_LOCALE.toLowerCase(),
		todayHighlight : true,
		onRender : function(date) {
		}
	});
});

function search(element, event) {
    var data = setConditionSearch();
    ajaxSearch("doc/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
    var condition = {};

    condition["companyId"] = $("#companyId").val();
    condition["formId"] = $("#formId").val();
	condition["statusCode"] = $("#statusCode").val();
	condition["docTitle"] = $("#docTitle").val();
	condition["fromDate"] = $("#fromDate").val();
	condition["toDate"] = $("#toDate").val();
    return condition;
}
