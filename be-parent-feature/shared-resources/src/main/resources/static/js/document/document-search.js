$(document).ready(function () {
	// Init search agent code
	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : false,
		searchOptions : {
            'default'    : 'Search',             // search input placeholder text
            showOptGroups: true,                // show option group titles if no options remaining
            onSearch     : function( element ){} // fires on keyup before search on options happens
        }
	});

	$('.ms-options-wrap ul').find('label').css("padding-left", "21px");
    $('.ms-options-wrap button').css('height', '27px');
    $('.ms-options-wrap button').text('     ');	

//	$("select[id='companyId']").selectpicker({
//        placeholder : COMPANY_PLACEHOLDER,
//        minimumInputLength : 0,
//        allowClear : false
//    });
	
	/*$("select[id='priority']").select2({
        //placeholder : PRIORITY_PLACEHOLDER,
        minimumInputLength : 0,
        allowClear : false
    });*/
	
	$("select[id='statusCode']").select2({
        //placeholder : STATUS_PLACEHOLDER,
        minimumInputLength : 0,
        allowClear : false
    });
	
	var companyId = $('#companyId').val();
	var formId = $('#formId').val();
	var processId = $('#processId').val();
   
    initSelectForm(companyId);
    
    initSelectProcess(formId);
    
    //initSelectStatus(processId);
    
    
    // Change company
   /* $('#companyId').change(function(event) {
    	$(".eform").val(" ");
    	$('.proc').val(" ");
    	$('.status').val(' ');
    	initSelectStatus(null);
   	 	initSelectProcess(null);
    	var companyId = $(this).val();
    	initSelectForm(companyId);
    	if (companyId != null && companyId != ''){
    		$(".eform").removeAttr("disabled");
    	} else {
    		$(".eform").attr("disabled","true");
    	}
    	$("#btnCreate").hide();
    });*/
    
    //Change process
    $('#processId').change(function(event) {
    	var processId = $(this).val();
    	if(processId === undefined || processId === null || processId === '') {
    		
    	}
    	
    	if(processId !== undefined && processId !== null && processId !== '') {
    		$('#statusCode').val("").trigger('change');
    		initSelectStatus(processId);
    	} else {
    		initSelectStatus(null);
    		$('#statusCode').val("0").trigger('change');
    	}
    });
    
    
    // Change form
    $('#formId').change(function(event) {   	
    	$('#processId').val("").trigger('change');
    	var formId = $(this).val();
    	if(formId === undefined || formId === null || formId === '') {
    		initSelectStatus(null);
    		$('#statusCode').val("0").trigger('change');
    		$('#processId').prop('disabled', 'disabled');
    	} else {
    		$('#processId').prop('disabled', '');
    		initSelectProcess(formId);	
    	}
   	 	var companyId = $('#companyId').val();
    	if(formId !== undefined && formId !== null && formId !== '' && companyId == USER_LOGIN_COMPANY_ID) {
    		$("#btnCreate").show();
    	} else {
    		$("#btnCreate").hide();
    	}
    	
    });
    
  //Change company
   $('#companyId').change(function(event) {
    	var comId = $(this).val();
    	$('#formId').val('').trigger('change');
    	if(comId != undefined && comId != null && comId != "") {
    		$('#formId').removeAttr("disabled");
    		initSelectForm(comId, event);
    	} else {
    		$('#formId').prop('disabled', 'disabled');
    	}
    });
    
    // Search
    $('#btnSearch').click(function (event) {
    	$('#error-message').html("");
        search($(this), event);
    });
    
    
    fromDateToDateConfig();
    $('.datepicker-from-date, .datepicker-to-date').datepickerUnit('setEndDate', new Date());

    $(document).keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if( keycode == '13' && $("#btnSearch").length ) {
        	
        	if( $("#btnSearch").length > 0 ) {
        		$('#error-message').html("");
                search($(this), event);
        	}
        }
    });
    
	$('.selectpicker').selectpicker();
});

function search(element, event) {
	setDataSearchHidd();
    var data = setConditionSearch();
    ajaxSearch("document/ajaxList", data, 'tableList', element, event);
}

function setConditionSearch() {
    var condition = {};
    
    condition["searchValue"] = $("#fieldSearchHidd").val();
	condition["searchKeyIds"] = $("#fieldValuesHidd").val();
	condition["priority"] = $("#priority").val();
    condition["companyId"] = $("#companyId").val();
    condition["formId"] = $("#formId").val();
	condition["statusCode"] = $("#statusCode").val();
	condition["processId"] = $("#processId").val();
	condition["docTitle"] = $("#docTitle").val();
	condition["fromDate"] = $("#fromDate").val();
	condition["toDate"] = $("#toDate").val();
	condition["sortName"] = $("#sortNameSearchHidden").val();
	condition["sortType"] = $("#sortTypeSearchHidden").val();
//	condition["hasArchive"] = $("#hasArchive")[0].checked;
    return condition;
}

function initSelectStatus(processDeployId) {
    searchCombobox('#statusCode', '', 'jpm-process-deploy/ajax/getListStatusByProcessDeployId',
    	    function data(params) {
    	        var obj = {
    	        	keySearch : params.term,	
    	            processDeployId: processDeployId,
    	            isPaging : true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function initSelectProcess(formId) {
	searchCombobox('#processId', '', 'jpm-process-deploy/ajax/getListProcessDeployByFormId',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            formId: formId,
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function initSelectForm(companyId){
	 searchCombobox('#formId', '', 'form/get-list-for-select',
	    	    function data(params) {
	    	        var obj = {
	    	            keySearch: params.term,
	    	            companyId: companyId,
	    	            isPaging: true
	    	        };
	    	        return obj;
	    	    }, function dataResult(data) {
	    	    	console.log(data);
	    	        return data;
	    	    }, true);
}


/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	$("#fieldSearchHidd").val($("#searchValue").val());
	$("#fieldValuesHidd").val($("select[name=searchKeyIds]").val());
}
