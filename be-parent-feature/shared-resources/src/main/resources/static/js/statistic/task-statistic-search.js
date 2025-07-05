$(document).ready(function() {
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	
	var companyId = $('#companyIdHidden').val();
	
	initSelectOrg(companyId);
	
	
	$('#companyId').change(function(event) {
	   $('#orgId').val(" ");
	   var companyId = $(this).val();
	   initSelectOrg(companyId)
	});
	
	// Search
	$('#btnSearch').click(function(event) {
		search($(this), event);
	});

	fromDateToDateConfig();
	
	$('.datepicker-from-date, .datepicker-to-date').datepickerUnit('setEndDate', new Date());
	
	$(document).keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if( keycode == '13' && $("#btnSearch").length ){
        	search($(this), event);
        }
    });
	
});

function initSelectOrg(companyId) {
	searchCombobox('#orgId', '', 'jpm-process-deploy/ajax/getListOrgByCompanyId',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: companyId,
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function search(element, event) {
	getCountForToDoStatistic();
	var data = setConditionSearch();
	ajaxSearch("task-statistic/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["companyId"] = $("#companyId").val();
	condition["orgId"] = $("#orgId").val();
	condition["fromDate"] = $("#fromDate").val();
	condition["toDate"] = $("#toDate").val();
//	condition["hasArchive"] = $("#hasArchive")[0].checked;
	return condition;
}
