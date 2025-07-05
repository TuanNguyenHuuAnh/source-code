$(function(){
	$("#assetName, #serialNumber, #assetTag, #assetStatus, #registrationDateFrom, " +
			" #registrationDateTo, #vendor, #officeLocation, #owner, #costCenter," +
			" #transactionType, #transactionNo, #invoiceNo, #propertyOf," +
			" #transactionDateFrom, #transactionDateTo, " +
			" #assetCategory, #purchaseDateFrom, #purchaseDateTo, " +
			" #receiptNo, #recepient, #receiptDateFrom, #receiptDateTo, " +
			" #invoiceNumber, #prNo, #dept, #propertyOf, #serialNo, #remark" +
			" #registerDateFrom, #registerDateTo, #poNo, #assetOwner, #warehouse")
	.unbind('keypress')
	.bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	onClickSearch(this, event);
	    }
	});
	
	// init date picker
	$('.date').datepicker({
		format: 'dd/mm/yyyy',
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : '${sessionScope.localeSelect}',
		todayHighlight : true,
		onRender : function(date) {
		}
	});
	var registrationDateFrom = $("#registrationDateFrom").val();
	var registrationDateTo = $("#registrationDateTo").val();
	changeDatepickerById(registrationDateFrom, registrationDateTo,'#registrationDateFrom','#registrationDateTo');
	
	var transactionDateFrom = $("#transactionDateFrom").val();
	var transactionDateTo = $("#transactionDateTo").val();
	changeDatepickerById(transactionDateFrom, transactionDateTo,'#transactionDateFrom','#transactionDateTo');
	
	var purchaseDateFrom = $("#purchaseDateFrom").val();
	var purchaseDateTo = $("#purchaseDateTo").val();
	changeDatepickerById(purchaseDateFrom, purchaseDateTo,'#purchaseDateFrom','#purchaseDateTo');
	
	var receiptDateFrom = $("#receiptDateFrom").val();
	var receiptDateTo = $("#receiptDateTo").val();
	changeDatepickerById(receiptDateFrom, receiptDateTo,'#receiptDateFrom','#receiptDateTo');

    $("#box-advance").on('click', function(event){
        if($("#iconShow").hasClass("fa fa-plus")){
            $("#iconShow").removeClass("fa fa-plus").addClass("fa fa-minus");
        } else{
            $("#iconShow").removeClass("fa fa-minus").addClass("fa fa-plus");
        }
    });
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});

	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailItem(this, event);
	});
	
	$(".select2").select2({});
})

/**
 * setConditionSearch
 */
function setConditionSearch() {
	var condition = {};	
	condition["assetName"] 				= $("#assetName").val();
	condition["assetStatus"] 			= $("#assetStatus").val();
	condition["assetTag"] 				= $("#assetTag").val();
	condition["serialNo"] 				= $("#serialNo").val();
	condition["registerDateFrom"] 		= $("#registerDateFrom").val();
	condition["registerDateTo"] 		= $("#registerDateTo").val();
	condition["vendor"] 				= $("#vendor").val();
	condition["poNo"]					= $("#poNo").val();
	condition["receiptNo"] 				= $("#receiptNo").val();
	condition["assetOwner"]				= $("#assetOwner").val();
	condition["receiptDateFrom"] 		= $("#receiptDateFrom").val();
	condition["receiptDateTo"]			= $("#receiptDateTo").val();
	condition["officeLocation"]			= $("#officeLocation").val();
	condition["warehouse"]				= $("#warehouse").val();
	condition["transactionType"]		= $("#transactionType").val();
	condition["transactionDateFrom"]	= $("#transactionDateFrom").val();
	condition["transactionDateTo"]		= $("#transactionDateTo").val();
	condition["transactionNo"]			= $("#transactionNo").val();
	condition["assetCategory"]			= $("#assetCategory").val();
	condition["assetSubCategory"]		= $("#assetSubCategory").val();
	condition["purchaseDateFrom"]		= $("#purchaseDateFrom").val();
	condition["purchaseDateTo"]			= $("#purchaseDateTo").val();
	condition["invoiceNumber"]			= $("#invoiceNumber").val();
	condition["prNo"]					= $("#prNo").val();
	condition["dept"]					= $("#dept").val();
	condition["propertyOf"]				= $("#propertyOf").val();
	condition["remark"]					= $("#remark").val();
	
	return condition;
}

function onClickSearch(element, event) {
	ajaxSearch("asset-reporting/ajaxList", setConditionSearch(), "tableList", element, event);
}

function doExportExcel(linkExport) {
	$("#formSearch").attr("action", BASE_URL + linkExport);
	$("#formSearch").submit();
}
