$(document).ready(function() {
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	$('#formSearch').on('keyup keypress', function(e) {
		var keyCode = e.keyCode || e.which;
		if (keyCode === 13) {
			e.preventDefault();
			return false;
		}
	});
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
		$('.alert-dismissible').hide();
	});

	
    $(document).keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if( keycode == '13' && $("#btnSearch").length ){
        	event.preventDefault();
    		onClickSearch(this, event);
    		$('.alert-dismissible').hide();
        }
    });
})

/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {
	event.preventDefault();
	$('.alert').remove();
	setDataSearchHidden();
	var condition = setConditionSearch();
	ajaxSearch("organization-person/ajaxList", condition, "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	condition["companyId"] = $("#companyId").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}