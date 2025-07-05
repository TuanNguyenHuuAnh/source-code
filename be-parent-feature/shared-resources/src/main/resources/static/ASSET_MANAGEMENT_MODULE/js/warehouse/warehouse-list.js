$(document).ready(function($) {
	
	//press enter search
	$("#inputSearch").unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	onClickSearch(this, event);
	    }
	});
	// on click delete
	$(".j-btn-delete").on("click", function( event ) {
		deleteWarehouse(this, event);
	});
	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editWarehouse(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailWarehouse(this, event);
	});
	 // datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'warehouse/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		
		onClickSearch(this, event);
	});
	/*
	// multiple select
    $('select[multiple]').multiselect({
        columns : 1,
        placeholder : "search filter",
        search : true
    });

    $('.j-btn-edit').on('click', function(event) {
        event.preventDefault();
        var url = BASE_URL + "mockup?view-id=mockup.warehouse.edit";

        // Redirect to page list
        ajaxRedirect(url);
    });
    */
    $('#add').on('click', function(event) {
        event.preventDefault();
        var url = BASE_URL + "warehouse/edit";

        // Redirect to page list
        ajaxRedirect(url);
    });
    

});
function onClickSearch(element, event) {


	ajaxSearch("warehouse/ajaxList", setConditionSearch(), "tableList", element, event);
}
function setConditionSearch() {
	
	var condition = {};
	condition["inputSearch"] = $.trim($("#inputSearch").val());
	//console.log(condition);
	return condition;
}


//deleteWarehouse
function deleteWarehouse(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var warehouseId = row.data("warehouse-id");

	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "warehouse/delete";
			var condition = {
				"id" : warehouseId
			}
			ajaxSubmit(url, condition, event);
			
		}			
	});
}


// edit Warehouse
function editWarehouse(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("warehouse-id");
	var url = BASE_URL + "warehouse/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}


function detailWarehouse(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("warehouse-id");
	var url = BASE_URL + "warehouse/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

function doExportExcel() {
	
    var linkExport = BASE_URL + "warehouse/export-excel";
    console.log(linkExport);
    doExportExcelWithToken(linkExport, "token", setConditionSearch());
}

