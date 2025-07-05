$(document).ready(function () {
	//$('#companyId').val('').trigger('change');
	// Init search agent code
    /*searchCombobox('#service-category', SEARCH_CATEGORY_LABEL, 'jpm-svc-board/get-category',
    	    function data(params) {
    	console.log('zo');
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
    });*/
	initCategory();
	inittemplateName();
	// on click search
	$("#btnSearch").on('click', function(event) {
		event.preventDefault();
		search(this, event);
	});
  //Change category
    $('#companyId').change(function(event) {
    	var comId = $(this).val();
    	$('#service-category').val('').trigger('change');
    	$('#formId').val('').trigger('change');
    	if(comId != undefined && comId != null && comId != "") {
    		//$('#service-category').prop('disabled', '');
    		initCategory();
    	} else {
    		//$('#service-category').prop('disabled', 'disabled');
    		//$('#formId').prop('disabled', 'disabled');
    	}
    });
    
  //Change category
    $('#service-category').change(function(event) {
    	var value = $(this).val();
    	$('#formId').val('').trigger('change');
    	if(value != undefined && value != null && value != "") {
    		//$('#formId').prop('disabled', '');
    		inittemplateName();
    	} else {
    		//$('#formId').prop('disabled', 'disabled');
    	}
    });
    
    /*$(document).keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if( keycode == '13' && $("#btnSearch").length ){
        	event.preventDefault();
    		search(this, event);
        }
    });*/
});

function search(element, event) {
    var data = setConditionSearch();
    ajaxSearch("jpm-svc-board/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
    var condition = {};
    condition["companyId"] = $('#companyId').val();
    condition["categoryId"] = $('#service-category').val();
    condition["formId"] = $('#formId').val();
    return condition;
}

function initCategory() {
	searchCombobox('#service-category', '', 'jpm-svc-board/get-category',
		    function data(params) {
		        var obj = {
		            keySearch: params.term,
		            companyId: $('#companyId').val()
		        };
		        return obj;
		    }, function dataResult(data) {
		        return data;
		    }, true);
}

function inittemplateName() {
	searchCombobox('#formId', '', 'jpm-svc-board/getTemplateName',
		    function data(params) {
		        var obj = {
		            keySearch: params.term,
		            companyId: $('#companyId').val(), 
		            categoryId: $('#service-category').val(), 
		            isPaging: true
		        };
		        return obj;
		    }, function dataResult(data) {
		        return data;
		    }, true);
}
