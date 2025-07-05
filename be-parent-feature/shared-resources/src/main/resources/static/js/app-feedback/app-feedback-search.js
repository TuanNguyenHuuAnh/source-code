$(document).ready(function () {
	// Init search agent code

	$("select[id='companyId']").select2({
        placeholder : COMPANY_PLACEHOLDER,
        minimumInputLength : 0,
        allowClear : true
    });
	
	var companyId = $('#companyId').val();
   
    initSelectForm(companyId);
    
    // Change company
    $('#companyId').change(function(event) {
    	$(".eform").val(" ");
    	$('.proc').val(" ");
    	$('.status').val(' ');
    	var companyId = $(this).val();
    	initSelectForm(companyId);
    	if (companyId != null && companyId != ''){
    		$(".eform").removeAttr("disabled");
    	} else {
    		$(".eform").attr("disabled","true");
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
    
    $(document).keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if( keycode == '13' && $("#btnSearch").length ){
        	search($(this), event);
        }
    });
});

function search(element, event) {
    var data = setConditionSearch();
    ajaxSearch("feedback/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
    var condition = {};

    condition["companyId"] = $("#companyId").val();
    condition["content"] = $("#content").val();
	condition["fromDate"] = $("#fromDate").val();
	condition["toDate"] = $("#toDate").val();
    return condition;
}
function initSelectForm(companyId){
	 searchCombobox('#formId', FORM_PLACEHOLDER, 'form/get-list-for-select',
	    	    function data(params) {
	    	        var obj = {
	    	            keySearch: params.term,
	    	            companyId: companyId,
	    	            isPaging: true
	    	        };
	    	        return obj;
	    	    }, function dataResult(data) {
	    	    	console.log(data);
	    	        return data;
	    	    }, true);
}