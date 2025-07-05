$(document).ready(function($) {
	
	$("#transferNo, #fromDate, #toDate, #fromOfficeLocation, #toOfficeLocation, #assetTransfer, #requester, #assetTag, #serialNo, #receiver").unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	onClickSearch(this, event);
	    }
	});

	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailItem(this, event);
	});

	// on click delete
	$('.j-btn-delete').unbind('click');
	$(".j-btn-delete").on("click", function( event ) {
		deleteTransfer(this, event);
	});
	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editItem(this, event);
	});
	
	// on click search
    $('#btnSearch').unbind('click');
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
		
	});
    $('#add').on('click', function(event) {
        event.preventDefault();
        var url = BASE_URL + "asset-transfer/edit";

        // Redirect to page list
        ajaxRedirect(url);
    });
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'asset-transfer/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// date picker
	// $(".date").datepicker({autoclose : true,format: 'dd/mm/yyyy'});
	var idEffectedDate = $("#fromDate").val();
	var idExpiredDate = $("#toDate").val();
	changeDatepickerById(idEffectedDate, idExpiredDate,'#fromDate','#toDate');
    
    $("#box-advance").unbind('click').on('click', function(event){
        if($("#iconShow").hasClass("fa-plus")){
            $("#iconShow").removeClass("fa-plus").addClass("fa-minus");
        } else{
            $("#iconShow").removeClass("fa-minus").addClass("fa-plus");
        }
    });
});

function setConditionSearch() {
	var condition = {};
	condition['transferNo'] = $.trim($('#transferNo').val());
	condition['fromDate']=$.trim($('#fromDate').val());
	condition['toDate']=$.trim($('#toDate').val());
	condition['status']=$.trim($('#status').val());
	condition['fromOfficeLocation']=$.trim($('#fromOfficeLocation').val());
	condition['toOfficeLocation']=$.trim($('#toOfficeLocation').val());
	condition['assetTransfer']=$.trim($('#assetTransfer').val());
	condition['requester']=$.trim($('#requester').val());
	condition['assetTag']=$.trim($('#assetTag').val());
	condition['serialNo']=$.trim($('#serialNo').val());
	condition['propertyOf']=$.trim($('#propertyOf').val());
	condition['receiver']=$.trim($('#receiver').val());

	//console.log(condition);
	return condition;
}
function onClickSearch(element, event) {



	ajaxSearch("asset-transfer/ajaxList", setConditionSearch(), "tableList", element, event);
}
//delete
function deleteTransfer(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var transferId = row.data("transfer-id");

	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "asset-transfer/delete";
			var condition = {
				"id" : transferId
			}
			ajaxSubmit(url, condition, event);
			
		}			
	});
}
function detailItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("transfer-id");
	var url = BASE_URL + "asset-transfer/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
function editItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("transfer-id");
	var url = BASE_URL + "asset-transfer/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
function doExportExcel() {
    var linkExport = BASE_URL + "asset-transfer/export-excel";
    //console.log(linkExport);
    doExportExcelWithToken(linkExport, "token", setConditionSearch());
}