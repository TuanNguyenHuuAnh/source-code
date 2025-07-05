// url payment pr po
var URL_PAYMENT = 'asset-adjust';

$(function() {
	// datatable load
	$("#tableListPopup").datatables({
		url : BASE_URL + 'asset-adjust/popup/table',
		type : 'POST',
		setData : setConditionPopupSearch
	});
	
	// key press
	$("#assetNamePopup, #assetSerialNoPopup, #assetTagPopup, #imeiPopup").on('keypress', function(e) {
		if((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)){
			onClickSearchPopup(this, event);
		}
	});
	
	// onclick search
	$("#myModal .modal-content").on('click', '#btnSearchPopup', function(event) {
		onClickSearchPopup(this, event);
	});
	
    // Click nút "Add" ở popup
	$('.modal-content').on('click', '#btnPopupAdd', function(event) {
		event.preventDefault();
		
    	loadData(event);
    });
	
	$(".item-check").on('change', function(event){
		if ($("#btnPopupAdd").hasClass("not-active")){
			$("#btnPopupAdd").removeClass("not-active");
		}
	});
	
    initData();
});

function initData(){
	/*$(".date").datepicker({
		autoclose : true,
		format : 'dd/mm/yyyy',
		todayHighlight: true,
		autoclose: true
	});
	*/
	
	setDataSearchPopupHidd();
}

/**
 * on click button search
 */
function onClickSearchPopup(element, event) {
	setDataSearchPopupHidd();
	
	ajaxSearch('asset-adjust/popup/table', setConditionPopupSearch(), "tableListPopup", element, event);
}

/**
 * setConditionPopupSearch
 */
function setConditionPopupSearch() {
	return JSON.parse($("#conditionPopupHidd").val());
}

/**
 * setDataSearchPopupHidd
 */
function setDataSearchPopupHidd() {
	var condition = {};	
	condition["assetNamePopup"] 			= $("#assetNamePopup").val();
	condition["assetSerialNoPopup"] 		= $("#assetSerialNoPopup").val();
	condition["assetTagPopup"] 				= $("#assetTagPopup").val();
	condition["imeiPopup"] 					= $("#imeiPopup").val();
	condition["assetCategoryCodePopup"] 	= $("#assetCategoryCodePopup").val();
	condition["assetSubCategoryCodePopup"] 	= $("#assetSubCategoryCodePopup").val();
	
	$("#conditionPopupHidd").val(JSON.stringify(condition));
}

function loadData(event){
	$("#tblDataTablePopup tbody tr").each(function(key, value) {
		if ($(this).find("td input:radio").is(":checked")) {
			var url = "asset-adjust/popup/getData";
            var condition = {};
            condition["id"] = $(this).find("td input:radio").val();
            
            $.ajax({
        		type : "POST",
        		url : BASE_URL + url,
        		data : condition,
        		success : function(data) {
        			if (data != null && data != undefined){
            			var item = JSON.parse(data);
            			setData(item);
        			}
        		},
        		error : function(xhr, textStatus, error) {
        			console.log(xhr);
        			console.log(textStatus);
        			console.log(error);
        		}
        	});
		}
	});
}

function setData(data){
	$("#assetProfileId").val(data.assetProfileId);
	$("#assetName").val(data.assetName);
	$("#assetCode").val(data.assetCode);
	
	$("#assetTag").val(data.assetTag);
	$("#imei").val(data.imei);
	
	$('#imgQrCode').attr('src', BASE_URL + 'asset-profile/qrcode?tag=' + data.assetTag);
	
	$("#qrCode").val(data.qrCode);
	$("#serialNumber").val(data.serialNumber);
	$("#assetStatus").val(data.assetStatus);
	
	if (data.assetStatus == 0 || data.assetStatus == 1){
		$("#assetStatus").attr('disabled', 'disabled');
	}else{
		$("#assetStatus").removeAttr('disabled');
	}
	
	$("#assetDescription").val(data.assetDescription);
	$("#registerFullname").val(data.registerFullname);
	$("#registerUsername").val(data.registerUsername);
	$("#registrationDate").val(moment(new Date(data.registrationDate)).format('DD/MM/YYYY'));
	var selectAssetType = getHtmlSelect(data.assetTypeName, data.assetTypeCode);
	$("#assetTypeCode").val(data.assetTypeCode);
	$("#assetTypeName").val(data.assetTypeName);
	$("#assetTypeCode").html(selectAssetType);
	var selectAssetCategory = getHtmlSelect(data.assetCategoryName, data.assetCategoryCode);
	$("#assetCategoryCode").val(data.assetCategoryCode);
	$("#assetCategoryName").val(data.assetCategoryName);
	$("#assetCategoryCode").html(selectAssetCategory);
	var selectAssetSubCategory = getHtmlSelect(data.assetSubCategoryName, data.assetSubCategoryCode);
	$("#assetSubCategoryCode").val(data.assetSubCategoryCode);
	$("#assetSubCategoryName").val(data.assetSubCategoryName);
	$("#assetSubCategoryCode").html(selectAssetSubCategory);
	$("#propertyOf").val(data.propertyOf);
	$("#quantity").val(data.quantity);
	$("#uomCode").val(data.uomCode);
	$("#uomName").val(data.uomName);
	$("#purchasePrice").val(data.purchasePrice);
	$("#purchaseDate").val(moment(new Date(data.purchaseDate)).format('DD/MM/YYYY'));
	$("#cpu").val(data.cpu);
	$("#ram").val(data.ram);
	$("#hdd").val(data.hdd);
	$("#os").val(data.os);
	$("#remark").val(data.remark);
	
	$("#vendorName").val(data.vendorName);
	$("#vendorCode").val(data.vendorCode);
	$("#contractNo").val(data.contractNo);
	$("#invoiceNumber").val(data.invoiceNumber);
	$("#prNo").val(data.prNo);
	$("#poNo").val(data.poNo);
	$("#receiptNo").val(data.receiptNo);
	$("#recipient").val(data.recipient);
	$("#recipientFullname").val(data.recipientFullname);
	
	if (data.receiptDate != null && data.receiptDate != ''){
		$("#receiptDate").val(moment(new Date(data.receiptDate)).format('DD/MM/YYYY'));
	}
	
	$("#paymentNo").val(data.paymentNo);
	
	if (data.paymentDate != null && data.paymentDate != ''){
		$("#paymentDate").val(moment(new Date(data.paymentDate)).format('DD/MM/YYYY'));
	}
	
	$("#assetLocationName").val(data.assetLocationName);
	$("#assetLocationCode").val(data.assetLocationCode);
	$("#assetWarehouseName").val(data.assetWarehouseName);
	$("#assetWarehouseCode").val(data.assetWarehouseCode);
	$("#warehouseKeeper").val(data.warehouseKeeper);
	$("#contact").val(data.contact);
	$("#assetOwnerUsername").val(data.assetOwnerUsername);
	$("#assetOwnerFullname").val(data.assetOwnerFullname);
	$("#assetOwnerDept").val(data.assetOwnerDept);
	
	$("#currency").val(data.currency);

	
	if (data.prId != null){
		
		$("#refPr").attr('href', BASE_URL + 'pr/detail?id=' + data.prId);
		$("#refPr").attr('target', '_blank');
		
		$('#prNo').css('color', '#3c8dbc');
		$('#prNo').css('cursor', 'pointer');
		
		$("#prId").val(data.prId);
	}
	
	if (data.poId != null){
		
		$("#refPo").attr('href', BASE_URL + 'po/detail?id=' + data.poId);
		$("#refPo").attr('target', '_blank');
		
		$('#poNo').css('color', '#3c8dbc');
		$('#poNo').css('cursor', 'pointer');
		
		$("#poId").val(data.poId);
	}
	
	if (data.paymentId != null){
		var url = 'payment-ge';
		if (data.paymentType == 0){
			url = 'payment-pr-po';
		}
		$("#refPayment").attr('href', BASE_URL + url + '/detail?id=' + data.paymentId);
		$("#refPayment").attr('target', '_blank');
		
		$('#paymentNo').css('color', '#3c8dbc');
		$('#paymentNo').css('cursor', 'pointer');
		
		$("#paymentId").val(data.paymentId);
	}
	
	if (data.receiptId != null){
		
		$("#refReceipt").attr('href', BASE_URL + 'p2pgrgeneral/detail?id=' + data.receiptId);
		$("#refReceipt").attr('target', '_blank');
		
		$('#receiptNo').css('color', '#3c8dbc');
		$('#receiptNo').css('cursor', 'pointer');
		
		$("#receiptId").val(data.receiptId);
	}
	
	// process
	$("#stepNo").val(data.stepNo);
	$("#processStatusCode").val(data.processStatusCode);
}