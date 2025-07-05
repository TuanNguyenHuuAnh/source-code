$(document).ready(function() {	
	if($('#fieldSearch').val() != null && $('#fieldSearch').val() != '') {
		initialState = 'expanded';
	}
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'user-login/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
    
    // multiple select search
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});

	$(".ms-options-wrap input").unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	onClickSearch(this, event);
	    }
	});
	
	$('#fieldSearch').unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	onClickSearch(this, event);
	    }
	});
	
	createJValidate();
	
	changeDatetimepicker('startTime', 'endTime');
	
	/*
	// Init company
    searchCombobox('.select-company', SEARCH_LABEL, 'company/get-company',
	    function data(params) {
	        var obj = {
	            keySearch: params.term,
	            isPaging: true
	        };
	        return obj;
	    }, function dataResult(data) {
	        return data;
	    }, false);
	 */
    
});
/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {

	setDataSearchHidden();

	ajaxSearch("user-login/ajaxList", setConditionSearch(), "tableList", element, event);
}

function createJValidate() {
	createFunctionValidate("validateEndTime", function(value, element) {
		let startTime = $('#startTime').val();
		if (!isNull(startTime)) {
			if (value != '' && startTime != '' && compareDate(startTime, value) == -1) {
				return true;
			}
		}

		return this.optional(element);

	}, MSG_ERROR_EXPIRATION_DATE);

}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	condition["startTime"] = $("#startTime").val();
	condition["endTime"] = $("#endTime").val();
	//condition["companyId"] = $("#companyId").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}
