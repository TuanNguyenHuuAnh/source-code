
$(function(){
	$('.auto-complete-username').autocomplete({
	serviceUrl: BASE_URL + 'asset-regist/find-user-full-name',
    paramName: 'name',
    delimiter: ",",
    global   : false,
    onSelect: function(suggestion) {
    	$(this).val(suggestion.value);
    	//$("#warehouseCode").val(suggestion.code);
        return false;
    }
	,transformResult: function(response) {
        return {
        	
            suggestions: $.map($.parseJSON(response), function(item) {
                return {
                    value: item,
                    code : item,
                };
            })
        };
    }
    
});
	
	$("#registrationNo, #assetLocationName, #assetWarehouseName, #propertyOf, #registrationDateFrom, #registrationDateTo, #status, #registerBy ").unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	onClickSearch(this, event);
	    }
	});
	
	
	$('.select2').select2({		
		tokenSeparators: [',', ' '],
		//allowClear: true
	});
	
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'asset-regist/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	// init date picker
	/*
	$('.date').datepicker({
		format: 'dd/mm/yyyy',
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : '${sessionScope.localeSelect}',
		todayHighlight : true,
		onRender : function(date) {
		}
	})
	*/
	
	try {
		var idEffectedDate = $("#registrationDateFrom").val();
		var idExpiredDate = $("#registrationDateTo").val();
		changeDatepickerById(idEffectedDate, idExpiredDate,"#registrationDateFrom","#registrationDateTo");
	} catch (err) {
		console.error(err);
	}
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editItem(this, event);
	});
	
	// on click delete
	$(".j-btn-delete").on("click", function( event ) {
		deleteItem(this, event);
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-regist/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailItem(this, event);
	});

	$('#btnExport').click(function() {
		var linkExport = BASE_URL + "asset-regist/export-excel";
		//doExportExcel(linkExport);
		doExportExcelWithToken(linkExport, "token", setConditionSearch());
	});
})

/**
 * setConditionSearch
 */
function setConditionSearch() {
	var condition = JSON.parse($("#conditionHidd").val()); 
	return condition;
}

function setDataSearchHidd() {
	var condition = {};	
	condition["registrationNo"] 		= $("#registrationNo").val();
	condition["assetLocationName"] 		= $("#assetLocationCode").val();
	condition["assetWarehouseName"] 	= $("#assetWarehouseCode").val();
	condition["propertyOf"] 			= $("#propertyOf").val();
	condition["registrationDateFrom"] 	= $("#registrationDateFrom").val();
	condition["registrationDateTo"] 	= $("#registrationDateTo").val();
	condition["status"] 				= $("#status").val();
	condition["registerBy"]				= $("#registerBy").val();
	var json_ = JSON.stringify(condition);
	$("#conditionHidd").val(json_);
}

function onClickSearch(element, event) {
	setDataSearchHidd();
	ajaxSearch("asset-regist/ajaxList", setConditionSearch(), "tableList", element, event);
}

function editItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	var url = BASE_URL + "asset-regist/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

function deleteItem(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "asset-regist/delete";
			var condition = {
				"id" : id
			}
			ajaxSubmit(url, condition, event);
		}			
	});
}

function detailItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	var url = BASE_URL + "asset-regist/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

function doExportExcel(linkExport) {
	$("#formSearch").attr("action", BASE_URL + linkExport);
	$("#formSearch").submit();
}
