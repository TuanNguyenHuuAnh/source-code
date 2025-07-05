$(document).ready(function () {
    
    /*// Category
    $('#service-category').select2({
        placeholder : SEARCH_CATEGORY_LABEL,
        allowClear : true,
    });*/
	// Init search agent code
    searchCombobox('#service-category', SEARCH_CATEGORY_LABEL, 'svc-board/get-category',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $('#companyId').val()
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
    
    // Change category
    $('#service-category').change(function(event) {
    	search($(this), event);
    });
    
    //on change company
	$("#companyId").on('change', function(event) {
		$('#service-category').val('').trigger('change');
		search($(this), event);
	});
});

function search(element, event) {
    var data = setConditionSearch();
    ajaxSearch("svc-board/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
    var condition = {};
    condition["companyId"] = $('#companyId').val();
    condition["categoryId"] = $('#service-category').val();
    return condition;
}