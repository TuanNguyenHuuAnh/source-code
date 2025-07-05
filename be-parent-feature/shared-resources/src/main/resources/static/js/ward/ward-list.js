$(document).ready(function($) {		
	
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "ward/edit";
		// Redirect to page add
		ajaxRedirect(url);
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
});

/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {
	event.preventDefault();
	setDataSearchHidden();

	ajaxSearch("ward/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}