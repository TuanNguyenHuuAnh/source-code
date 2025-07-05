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
});

function search(element, event) {
    var data = setConditionSearch();
    ajaxSearch("noti-template/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
    var condition = {};
    condition["fieldSearch"] = $("#fieldSearch").val();
	condition["fieldValues"] = $("select[name=fieldValues]").val().join(",");
	condition["companyId"] = $("#companyId").val();
    return condition;
}