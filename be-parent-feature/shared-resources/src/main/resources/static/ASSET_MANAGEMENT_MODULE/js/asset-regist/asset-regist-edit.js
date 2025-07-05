
$(function() {
	
	let CURRENT_URL = window.location.href;
	let findex = CURRENT_URL.indexOf(BASE_URL);
	let lindex = CURRENT_URL.lastIndexOf('?id');
	let url = CURRENT_URL.substring(findex, lindex);
	if(url == BASE_URL + 'asset-regist/detail') {
		$("#widgetApprovalComment").attr('readonly','readonly');
	}
	
	loadWarehouseByLocation();
	//onNoPO();
	//initCategory();
	
//	$("#propertyOf").on("change",function(){
//		var prop = $(this).val();
//		$(".assetCategory").each(function(){
//			loadCategory(prop, this, loadByCategory);
//		});
//	});
	
//	$(".assetCategory").on("change",function(){
//		//var cateCode = $(this).val();
//		//var element = $(this).closest("tr").find(".assetSubCategoryCode");
//		loadByCategory(this);
//	});
	
	
	
	
//	$('.quantity, .unitPrice').on('keyup', function(){
//		var $tr = $(this).closest('tr');
//		reCalculateRow($tr);
//	});
	
	

	
	var isEnable = $("#isEnabled").val();
	if(isEnable == 'false' || isEnable == false){
		$("input, select, textarea, checkbox").prop("disabled","disabled");
		$("#widgetApprovalComment").removeAttr("disabled");
		$("#functionTableDetailEdit").hide();
	}
	
	// khi load lan dau tien thi load warehouse dua theo item dau tien cua location
//	var defaultLocation = $("#assetLocationCode").val();
//	loadWarehouseByLocation(defaultLocation);
	//setHiddenSubmit();
	
	
	$("#assetLocationCode").on("change", function(){
		loadWarehouseByLocation();
//		var location =	$(this).val();
//		var locationName = $(this).find("option:selected").text();
//		$("#assetLocationName").val(locationName);
//		loadWarehouseByLocation(location);
	});
	
	$("#assetWarehouseCode").on("change", function(){
		var firstName =   $(this).find("option:selected").text();
		$("#assetWarehouseName").val(firstName);
	});
	
	
	
	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-regist/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-regist/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-regist/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	
	
	
	
	// Post edit save
	$('#button-approval-module-save-draft-id').unbind('click').bind('click', function(event) {
		// luu nhap thi khong can phai validate
//		if ($(".j-form-validate").valid()) { 
		//if(validateRequiredForm()== false){
			$('input:disabled, select:disabled, textarea:disabled, checkbox:disabled').removeAttr('disabled');
			setHiddenSubmit();
			var url = "asset-regist/edit";
			$("#commentsId").val($("#widgetApprovalComment").val());
			var condition = $("#form-asset-regis").serialize();
			ajaxSubmit(url, condition, event);
		//}
			
//		}			
		goTopPage();
	});
	
	$('#button-approval-module-submit-id').unbind('click').bind('click', function(event) {
		if(validateRequiredForm()== false){
			popupConfirm(MSG_CONFIRM, function(result) {
				if (result) {
					if(validateRequiredForm()== false){
						$('input:disabled, select:disabled, textarea:disabled, checkbox:disabled').removeAttr('disabled');
						setHiddenSubmit();
						var url = "asset-regist/submit";
						$("#commentsId").val($("#widgetApprovalComment").val());
						var condition = $("#form-asset-regis").serialize();
						//condition.push({ name: "comments", value: $("#widgetApprovalComment").val() });
						ajaxSubmit(url, condition, event);
					}
				}
			});
		}
		goTopPage();
	});
	
	
	
	
	
	
    $("#btnAdd").on('click', function(event){
    	if($("#isNoPO").is(":checked")){
    		
    		showModalPayment(); // tam thoi chua lam phan nay cho Q&A
    	}
    	else{
    		

    		showModal();
    	}
        
    });

//    $("#tempTable").on('click','.j-btn-delete', function(event){
//    	//deleteRow(this);
//    	$(this).parents("tr").remove();
//    	var rowTableDetail = $("#tempTable > tbody > tr").length;
//    	if(rowTableDetail > 0){
//    		resetRowIndex();
//    	}
//    	else{
//    		$("#isNoPO").prop("disabled", false);
//    	}
//    });
    
//    $(".j-btn-copy").on('click', function(event){
//    	var id = $(this).parents("tr").data("index");
//    	addRow(id);
//    });
    $("#btnClearData").unbind('click').bind("click", function(){
    	$("#tempTable").find("tbody > tr").each(function(){ 
    		$(this).remove();
    	});
    	$("#isNoPO").prop("disabled", false);
    });
//    $("#tempTable").on('click','.j-btn-copy', function(event){
//    	$(".select2-hidden-accessible").select2("destroy");
//        var htmlInvoceRow = "<tr>" + $(this).parents("tr").html() + "</tr>";
//        var htmlAppend = $(htmlInvoceRow);
//        htmlAppend.find('.id-detail').val(null);
//		$('#tempTable>tbody').append("<tr>"+htmlAppend.html()+"</tr>");
//		//last tr
//		let lastTr = $('#tempTable>tbody>tr').length - 1;
//		let elementTr = $('#tempTable>tbody>tr')[lastTr];
//		let $elementTr = $(elementTr);
//
//		$elementTr.find('.number').text(lastTr + 1);
//		$elementTr.find("td input:text,input:hidden,textarea,select").each(function() {
//			 var name = $(this).attr('name');
//			 if(typeof name !== 'undefined'){
//				 var indexNeedToReplace = name.substr(0, name.lastIndexOf("."));
//				 var indexReplace = name.substr(0, name.lastIndexOf("[")) + "["+lastTr+"]";
//				 name = name.replace(indexNeedToReplace, indexReplace);
//				 $(this).attr('name',name);
//			 }
//			 
//		 });
//		
//		getScript();
//		$(".select-table-detail").each(function(){
//			setWidthAllRow(this);
//		});
//		setWidthHeader();
//    });

    // date picker
	// $(".date").datepicker({autoclose : true,format: 'dd/mm/yyyy'});
	var idEffectedDate = $("#fromDate").val();
	var idExpiredDate = $("#toDate").val();
	changeDatepickerById(idEffectedDate, idExpiredDate,'#fromDate','#toDate');

	onLoadSetMaxWidthSelect();
	setWidthHeader();
	
	// TRITV add PDF
	$('#btnExportPdf').on('click', function(event) {
		event.preventDefault();
		var linkExport = "asset-regist/export-pdf";

		var condition = {};
		condition["id"] = $("#abtract-reference-id").val();
		doExportExcelWithToken(BASE_URL + linkExport, "token", condition);
	});
});


function initCategory(){
		var prop = $("#propertyOf").val();
		$(".assetCategory").each(function(){
			loadCategory(prop, this, loadByCategory);
		});
}


function loadWarehouseByLocation(){
	var location = $("#assetLocationCode").val();
	var option = '<option value="">--</option>';
	var jsonByLocation = JSON.parse(MAPBYLOCATION);
	var lstWarehouse = jsonByLocation[location];
	var propertyOf = $("#propertyOf").val();
	
	for(let ware in lstWarehouse){
		if (propertyOf == lstWarehouse[ware].propertyOf) {
			var warehouse = lstWarehouse[ware];
			option += '<option value="' + warehouse.code + '">' + warehouse.name +"</option>";
		}
	}
	$("#assetWarehouseCode").html(option);
	$("#assetWarehouseCode").val($("#assetWarehouseCodeHidden").val());
	
}

function loadCategory(propOf, element, callback){
	var option = '<option value="">--</option>'; 
	var jsonByProp = JSON.parse(MAPBYPROP);
	var lstCate = jsonByProp[propOf];
	for(let cat in lstCate){
		var cat_ = lstCate[cat]
		option += '<option value="' + cat_.code + '" >' + cat_.name + '</option>';
	}
	
	$(element).html(option);
//	var valueAfterAppend = $(element).val();
//	var elementSub = $(element).closest("tr").find(".assetSubCategoryCode");
	var valueHidden = $(element).closest("td").find(".assetCategoryHidden").val();
	$(element).val(valueHidden);
	callback(arguments[1]);
	//loadByCategory(valueAfterAppend, elementSub);
}

function loadByCategory(elementEvent){
	var valueElementEvent = $(elementEvent).val();
	var withElement = $(elementEvent).closest("tr").find(".assetSubCategoryCode");
	var option = '<option value="">--</option>'; 
	var jsonByCate = JSON.parse(MAPBYCATEGORY);
	var lstSub = jsonByCate[valueElementEvent];
	for(let sub in lstSub){
		var sub_ = lstSub[sub];
		option += '<option value="' + sub_.code + '" >' + sub_.name + '</option>';
	}
	
	$(withElement).html(option);
	
	var valueHidden = $(withElement).closest("td").find(".assetSubCategoryHidden").val();
	$(withElement).val(valueHidden);
	
}



function onNoPO(){
	var isCheckNoPO = $("#isNoPO").is(":checked");
	if(isCheckNoPO == false || isCheckNoPO == 'false'){
		$(".quantity, .purchasePrice, .unitPrice").prop("disabled", "disabled");
	}
	
	
	var isEnable = $("#isEnabled").val();
	var rowTableDetail = $("#tempTable > tbody > tr").length;
	if(isEnable == 'false' || isEnable == false || rowTableDetail > 0){
		$("#isNoPO").prop('disabled','disabled');
	}
}


function reCalculateRow(element){
	var quantity =  parseNumberEn(element.find('.quantity').val());
	var unitPrice = parseNumberEn(element.find('.unitPrice').val());
	var amount = quantity * unitPrice;
	element.find('.purchasePrice').val(amount);
}

function resetRowIndex(){
	var rowNum = 1;
	//blockbg();
	$("#tempTable > tbody > tr").each(function (){
		$(this).find('td.rownum').html(rowNum);
		 
		rowNum++;
	});
	setWidthHeader();
}

function getScript(){
	/*var url = BASE_URL + "static/js/asset-regist/asset-regist-edit.js";
	$.getScript( url, function( data, textStatus, jqxhr ) {
		  console.log( data ); // Data returned
		  console.log( textStatus ); // Success
		  console.log( jqxhr.status ); // 200
		  console.log( "Load was performed." );
		});*/
	
	$('.select2').select2({		
		tokenSeparators: [',', ' '],
		width: '100%'
		//allowClear: true
	});
	$('.big-number').number(true,undefined,undefined,undefined, false);
	setWidthHeader();
}

/**
 * @author tritv
 */
function setWidthHeader() {
	// find first row
	var rowsDetail = $(".table-fixed-head-2 table tbody tr:first-child");	
	if (rowsDetail.length == 0) return;
	
	var $tdDetail = $(".table-fixed-head-2 table tbody tr:first-child td");	
	var $thDetail = $(".table-fixed-head-2 table thead tr th");	
	// loop cols
	for (var i = 0; i < $tdDetail.length; i++) {
		var width = $($tdDetail[i]).css( "width");
		$($thDetail[i]).css('min-width', width);
		var width_ = $($thDetail[i]).css('width');
		if(parseInt(width_.replace('px','')) > parseInt(width.replace('px','')) ){
			$($thDetail[i]).css('width', width);
		}
		//console.log($($thDetail[i]).css('width'));
   }
}

function onLoadSetMaxWidthSelect(){
	$(".select-table-detail").each(function(){
		setWidthAllRow(this);
	});
}



/**
 * @author phatlt
 * @param element
 * @returns
 * @description : truong hop co thay doi do rong cua cot co select thi phai set lai cac cot khac cho dong bo
 */
function setWidthAllRow(element){
	let EMPTY = '';
	let TD = "td";
	let TR = "tr";
	let WIDTH = "width";
	let MIN_WIDTH ="min-width";
	let PX = "px";
	var tdElement = element.closest(TD);
	var currentRow = $(element).closest(TR).find(TD);
	var indexSelect = currentRow.index(tdElement);
	var width  = $(currentRow[indexSelect]).css(WIDTH);
	var witdthInt = parseInt(width.replace(PX,EMPTY));
	var rowsDetail = $(".table-fixed-head-2 table tbody tr");
	var indexRow = rowsDetail.index($(element).closest(TR));
	if(indexSelect != -1 && indexSelect !=null && rowsDetail.length >0){
		for(var i = 0 ; i < rowsDetail.length ; i++){
			if(indexRow == i)
				continue;
			var row = rowsDetail[i];
			var tdSelect = $(row).find(TD)[indexSelect];
			var widthSelect = $(tdSelect).css(WIDTH);
			var widthSelectInt  = parseInt(widthSelect.replace(PX, EMPTY));
			if(witdthInt > widthSelectInt) {
				$(tdSelect).css(MIN_WIDTH,width);
			}
			else{
				$(tdElement).css(MIN_WIDTH, widthSelect);
				return;
			}
		}
	}
}

function setHiddenSubmit(){
	if($("#assetLocationName").val() == null || $("#assetLocationName").val() == "")
		$("#assetLocationName").val($("#assetLocationCode option:selected").text());
	
	if($("#assetWarehouseName").val() == null || $("#assetWarehouseName").val() == "")
		$("#assetWarehouseName").val($("#assetWarehouseCode option:selected").text());
}

function validateQuantitySubmit(){
	var isErr = false;
	$('.label_error').remove();
	var type = $("#isNoPO").is(":checked"); // check la payment uncheck la gr
	if(type == false){
		var map = new Map();
		$("#tempTable > tbody > tr").each(function(){
			var grDetailIdHidd = $(this).find(".grDetailId");
			grDetailIdHidd.prop('disabled',false);
			var grDetailId = grDetailIdHidd.val();
			var quantity = 0;
			var quantityMap = map.get(grDetailId);
			if(quantityMap !=null)
				quantity = parseInt(quantityMap) + 1;
			else
				quantity+=1;
			map.set(grDetailId, quantity);
		});
		var div =  $("#tableListGR");
		var table = div.find("table");
		for(var [key,value] of map){ 
			var xpath = "tbody > tr > td.quantity-"+key;
			var quantityPop = table.find(xpath);
			if(quantityPop != null && parseInt(quantityPop.text())!== value){
				$(".quantity-gr-"+key).closest("td").append('<span class="label_error" style="color: red;">Total quantity not match with popup</span>'); 
				isErr = true;
			}
		}
		map.clear();
	}
	
	
	return isErr;
}

function validateRequiredForm(){
	var isErr = false;
	$('.label_error').remove();
	$(".required-custom").each(function(){
		if(validateRequired(this,"td") && !isErr){
			isErr = true;
		}
	});
	
	$(".required-custom-header").each(function(){
		if(validateRequired(this,"div") && !isErr){
			isErr = true;
		}
	});
	
//	if(!isErr){
//		isErr = validateQuantitySubmit();
//	}
	
	return isErr;
}

function validateRequired(element, parentType){
	var value = $(element).val();
	if(value == null || value == ''){
		//$("#label_"+id).show();
		$(element).closest(parentType).append('<span class="label_error" style="color: red;">Field is required.</span>'); 
		//.after('<span class="label_error" style="color: red;">Field is require.</span>');
		return true;
	}
	return false;
}


//function loadWarehouseByLocation(defaultLocation){
//	var urlGetWarehouse =  BASE_URL + "asset-regist/get-warehouse-select?locationCode=" +defaultLocation;
//	$.get( urlGetWarehouse, function( data ) {
//		  $( "#assetWarehouseCode" ).html( data );
//		  
//		  if($("#assetWarehouseName").val() == null){
//			  var firstName =  $("#assetWarehouseCode").find("option").first().html();
//			  $("#assetWarehouseName").val(firstName);
//		  }
//		  else{
//			  $("#assetWarehouseCode").find("option").each(function(){
//				  var warehouseName = $("#assetWarehouseName").val();
//				  var optionText = $(this).text();
//				  if(optionText == warehouseName){
//					  $(this).prop('selected',true);
//				  }
//			  });
//		  }
//		  
//		  
//		});
//	
//}


function addRow(id){
	var url =  "asset-regist/add-or-clone";
	var condition = $("#form-asset-regis").serialize();
	if(id != null && id != 'undefined'){
		condition+= '&id='+id;
	}
	$.ajax({
		type 	: 	"POST",
		url 	:	BASE_URL + url,
		data 	: 	condition,
		success : 	function(data) {
			$("#tableDetail").html(data);
		},
		error : function(xhr, textStatus, error) {
			unblockbg();
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
	
	
}



//function deleteRow(element){
//	var index = $(element).parents("tr").data("index");
//	$(element).parents("table").find("tr").each(function(){
//		if($(this).data("index") == index){
//			$(this).remove();
//		}
//	});
//	
//	//$(element).parents("tr").remove();
//    resetIndex();
//}

function copyRow(element){
	 var htmlInvoceRow = "<tr>" + $(element).parents("tr").html() + "</tr>";
     
		$('#tempTable>tbody').append(htmlInvoceRow);

		//last tr
		let lastTr = $('#tempTable>tbody>tr').length - 1;
		let elementTr = $('#tempTable>tbody>tr')[lastTr];
		let $elementTr = $(elementTr);

		$elementTr.find('.number').text(lastTr + 1);
}




function addActivity(){
	var newRow= "<tr>";
	
	newRow += "<td class='text-center number rownum'></td> ";
	
	newRow += "<td class='td-action'> ";
	newRow+= '<div class="align-center">';
	newRow+= '<a class="glyphicon glyphicon-remove-sign font-size-20 red j-btn-delete" onclick="deleteRow(this);"></a>';
	newRow+= '<a class="fa fa-copy icon-head-red font-size-20 red j-btn-copy" style="font-size: 17px" onclick="copyRow(this);"></a>';
	newRow+= '</div>';
	newRow += "</td>";
	
	newRow += "<td><input type='text' class='text-left' name='data[0].assetName' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].assetDescription' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].assetSubCategoryName' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].quantity' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].uomCode' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].serialNumber' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].imei' value='' /></td>";
	newRow += "<td><input type='text' class='text-left date date-picker' name='data[0].purchaseDate' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].purchasePrice' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].invoiceNumber' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].prNo' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].poNo' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].contractNo' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].paymentNo' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].paymentDate' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].receiptNo' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].receiptDate' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].recipient' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].cpu' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].ram' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].hdd' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].os' value='' /></td>";
	newRow += "<td><input type='text' class='text-left' name='data[0].remark' value='' /></td>";
	
	newRow += "</tr>";
	$("#tempTable").append(newRow);
	resetIndex();
	$('.big-number').number(true);
}



function resetIndex(){
	var rowNum = 0;
	blockbg();
	$("#tempTable > tbody > tr").each(function (){
		$(this).find('td.rownum').html(rowNum+1);
		 $(this).find("td input:text,input:hidden,textarea,select").each(function() {
			 var name = $(this).attr('name');
			 if(typeof name !== 'undefined'){
				 var indexNeedToReplace = name.substr(0, name.lastIndexOf("."));
				 var indexReplace = name.substr(0, name.lastIndexOf("[")) + "["+rowNum+"]";
				 name = name.replace(indexNeedToReplace, indexReplace);
				 $(this).attr('name',name);
			 }
		 });
		rowNum++;
	});
	unblockbg();
}

function clickImport(){
	var countRecord = 0;
	$('.not-exist-db').each(function(){
		if($(this).attr('style') != 'display: none;'){
			countRecord ++;
		}
	});
	$('.exist-db').each(function(){
		if($(this).attr('style') != 'display: none;'){
			countRecord ++;
		}
	});
    
	if(countRecord  > 0){
		var message  = $("#confirmMessage").val();
		popupConfirm( message, function(result) {
			if (result) {
				//e.preventDefault();
				$('.close').trigger('click');
				importCommon();
			}
		});
	}
	else {
		importCommon();
	}
}

function showModal(id) {
    $('#myModal').modal('show');
    $('#myModal').css('z-index',5000);
    $("#btnSearchGR").trigger("click");
 }

 function showModalPayment() {
     $('#myModalPayment').modal('show');
     $('#myModalPayment').css('z-index',5000);
     $("#btnSearchGE").trigger("click");
  }

function goTopPage(){
	$("html, body").animate({ scrollTop: 0 }, "1");
}
