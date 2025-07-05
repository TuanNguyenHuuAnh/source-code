$(document).ready(function() {
	 $('#fieldSearch').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    });
	// on click search
	$("#btnSearch").on('click', function(event) {
		event.preventDefault();
		onClickSearch(this, event);
	});

	// multiple select search
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});

})

/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {
	ajaxSearch("quartz/schedule/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["companyId"] = $("#companyId").val();
	//condition["companyIdList"] = $("#companyIdList").val();
	condition["fieldSearch"] = $("#fieldSearch").val();
	condition["fieldValues"] = $("select[name=fieldValues]").val().join(",");
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}