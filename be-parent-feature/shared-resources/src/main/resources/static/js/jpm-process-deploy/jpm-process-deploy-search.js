$(document).ready(function () {
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	$(".select-company").select2({
        placeholder : COMPANY_FILTER,
        minimumInputLength : 0,
        allowClear : true
    });
	$("#businessId").select2({
        placeholder : BUSINESS_FILTER,
        minimumInputLength : 0,
        allowClear : true
    });


	/*$('.date').datepickerUnit({
		format: DATE_FORMAT,
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : APP_LOCALE.toLowerCase(),
		todayHighlight : true,
		onRender : function(date) {
		}
	});*/
	
    fromDateToDateConfig();
    $('.datepicker-from-date, .datepicker-to-date').datepickerUnit('setEndDate', new Date());
	
    // Search
    $('#btnSearch').click(function (event) {
        search($(this), event);
    });
    
    $('#businessSearch').change(function (event){
    	chooseBusinessSearch($(this),event);
    	search($(this), event);
    });
    
    $('#processSearch').change(function (event) {
        search($(this), event);
    });
    
    $('#companyId').on("change", function (event){
    	var companyId = this.value;
    	ajaxLoadBusinessListByCompanyId(companyId, event);
    });
    
    $(document).keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if( keycode == '13' && $("#btnSearch").length ){
        	search($(this), event);
        }
    });
    
});

function search(element, event) {
    var data = setConditionSearch();
    ajaxSearch("jpm-process-deploy/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
    var condition = {};

    condition["companyId"] = $("#companyId").val();
	condition["businessId"] = $("#businessId").val();
	condition["fromDate"] = $("#fromDate").val();
	condition["toDate"] = $("#toDate").val();
	condition["fieldSearch"] = $("#fieldSearch").val();
	condition["fieldValues"] = $("select[name=fieldValues]").val().join(",");
	
    return condition;
}


function chooseBusinessSearch(business, event){
	var val = $(business).children("option:selected").val();
	if(val!=="000"){
		$(business).closest('#search-box').find('#processSearch').removeAttr('disabled');
		ajaxLoadProcessListByBusinessId(val,event);
	}else{
		$(business).closest('#search-box').find('#processSearch').val("000");
		$(business).closest('#search-box').find('#processSearch').attr('disabled','true');
	}
}

function ajaxLoadProcessListByBusinessId(businessId, event) {
	var url = "jpm-process-deploy/ajax/load-process";
	var condition = {};
	condition['businessId'] = businessId;
	var process = "#processSearch";
}

function ajaxLoadBusinessListByCompanyId(companyId, event) {
	var url = "jpm-process-deploy/ajax/load-business";
	var condition = {};
	condition["companyId"] = companyId;
	
	loadDataCombobox(url, condition, "#businessId", event);
}