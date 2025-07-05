$(document).ready(function () {
	
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// Init search agent code
    searchCombobox('#categoryId', SEARCH_CATEGORY_LABEL, 'category/get-category',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $('#companyId').val()
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
	
    // Search
    $('#btnSearch').click(function (event) {
    	$('#error-message').html('');
        search($(this), event);
    });
    
    //on change company
	$(".select-company").on('change', function(event) {
		//console.log('zo');
		$('#categoryId').val('').trigger('change');
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
    ajaxSearch("jpm-svc-management/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
    var condition = {};
    condition["fieldSearch"] = $("#fieldSearch").val();
	condition["fieldValues"] = $("select[name=fieldValues]").val().join(",");
	condition["companyId"] = $('#companyId').val();
	condition["categoryId"] = $('#categoryId').val();
	condition["formType"] = $('#formType').val();
    return condition;
}