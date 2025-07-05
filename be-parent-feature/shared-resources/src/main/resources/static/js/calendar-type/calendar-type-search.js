$(document).ready(function () {
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	
    // Search
    $('#btnSearch').click(function (event) {
        search($(this), event);
    });
    
    $(document).keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if( keycode == '13' && $("#btnSearch").length ){
        	search($(this), event);
        }
    });

    // TaiTM: Load attr dựa vào những attr định nghĩa sẵng
    ajaxHtml('item-html/select2', {"name": "companyId", "id": "companyId", "value": $('#companyId').val()}, 'GET', 'companyId');
    //ajaxHtml('item-html/input', {"name": "fieldSearch", "id": "fieldSearch", "placeholder" : SEARCH_PLACEHOLDER}, 'GET', 'fieldSearch');
    
    deleteCookie("CALENDAR_TYPE_SEARCH");
});

function search(element, event) {
	setDataSearchHidden();
    var data = setConditionSearch();
    ajaxSearch("calendar-type/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
    var condition = {};

    condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] =  $("#fieldValuesHidden").val();
	condition["companyId"] = $("#companyId").val();
	if ( $('#cbDes').is(':checked') ) {
		condition["description"] = $("#description").val();
	}
	if ( $('#cbCode').is(':checked') ) {
		condition["code"] = $("#code").val();
	}
	if ( $('#cbName').is(':checked') ) {
		condition["name"] = $("#name").val();
	}
	if ( $('#sName').is(':checked') ) {
		condition["sName"] = 1;
	}else{
		condition["sName"] = 0;
	}
	if ( $('#sWorkingHours').is(':checked') ) {
		condition["sWorkingHours"] = 1;
	}else{
		condition["sWorkingHours"] = 0;
	}
	if ( $('#sDescription').is(':checked') ) {
		condition["sDescription"] = 1;
	}else{
		condition["sDescription"] = 0;
	}
    return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}