$(document).ready(function() {
	$('#slaName').on('keyup keypress', function(e) {
	    var keyCode = e.keyCode || e.which;
	   if (keyCode === 13) { 
            e.preventDefault();
            return false;
        }
	});

	// on click search
	$("#btnSearch").on('click', function(event) {
		event.preventDefault();
		onClickSearch(this, event);
	});
	
	// on click add
	$("#add").unbind().bind("click", function(event) {
        event.preventDefault();
		var url = BASE_URL + "sla/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});
	
	//Change process
    $('#companyId').change(function(event) {
    	var comId = $(this).val();
    	$('#businessId').val('').trigger('change');
    	$('#processDeployId').val('').trigger('change');
    	if(comId != undefined && comId != null && comId != "") {
    		initBusinessList(comId);
    	} else {
    		$('#businessId').prop('disabled', 'disabled');
    		$('#processDeployId').prop('disabled', 'disabled');
    	}
    });
    
    $("#businessId").on('change', function() {
    	var busId = $(this).val();
    	$('#processDeployId').val('').trigger('change');
    	if(busId != undefined && busId != null && busId != "") {
    		initProcessList(busId);
    	} else {
    		$('#processDeployId').prop('disabled', 'disabled');
    	}
	});
    
    initialSearchScreen();
    
	initBusinessList($('#companyId').val());
});

/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {
	ajaxSearch("sla/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["processDeployId"] = $("#processDeployId").val();
	condition["businessId"] = $("#businessId").val();
	condition["name"] = $("#slaName").val();
	condition["companyId"] = $("#companyId").val();
	condition["companyIdList"] = $("#companyIdList").val();
	return condition;

}

function initBusinessList(id) {
	$('#businessId').prop('disabled', '');
    searchCombobox('#businessId', '', 'sla/ajax/getBusinessListByCompanyId',
    	    function data(params) {
    	        var obj = {
    	        		companyId: id
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function initProcessList(id) {
	$('#processDeployId').prop('disabled', '');
    searchCombobox('#processDeployId', '', 'sla/ajax/getProcessListByBusinessId',
    	    function data(params) {
    	        var obj = {
    	        		companyId: id
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function initialSearchScreen() {
	var comId = $('.select-company').val();
	if((comId === null || comId === "")) {
		$('#businessId').prop('disabled', 'disabled');
		$('#processDeployId').prop('disabled', 'disabled');
	} else {
		$('#processDeployId').prop('disabled', 'disabled');
	}
}