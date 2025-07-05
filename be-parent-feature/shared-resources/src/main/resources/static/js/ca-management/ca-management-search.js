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

});

function search(element, event) {
    var data = setConditionSearch();
    ajaxSearch("ca-management/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
    var condition = {};
    condition["fieldSearch"] = $("#fieldSearch").val();
	condition["fieldValues"] = $("select[name=fieldValues]").val().join(",");
	condition["companyId"] = $("#companyId").val();
    return condition;
}