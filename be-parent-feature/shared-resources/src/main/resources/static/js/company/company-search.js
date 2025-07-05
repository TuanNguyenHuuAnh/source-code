$(document).ready(function () {
    $('#fieldSearch').unbind('keypress').bind('keypress', function(event) {
        if(event.keyCode == 13){
            search(this, event);
        }
    });
    
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
    // Search
    $('#btnSearch').click(function (event) {
        search($(this), event);
    });

});

function search(element, event) {
    var data = setConditionSearch();
    ajaxSearch("company/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
    var condition = {};
    condition["fieldSearch"] = $("#fieldSearch").val();
	condition["fieldValues"] = $("select[name=fieldValues]").val().join(",");
    return condition;
}