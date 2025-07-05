$(function() {
	loadWarehouseByLocation();
	
	
	$('.select2').select2({		
		tokenSeparators: [',', ' '],
		//allowClear: true
	});
	
	var isEnable = $("#isEnabled").val();
	if(isEnable == 'false' || isEnable == false){
		$("input, select, textarea, checkbox").prop("disabled","disabled");
		$("#widgetApprovalComment").removeAttr("disabled");
		$("#functionTableDetailEdit").hide();
	}

	try {
		$('.big-number').number(true,undefined,undefined,undefined, false);
	} catch(err) {
		console.error(err);
	}
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-regist/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$(window).on("popstate", function () {
		// if the state is the page you expect, pull the name and load it.
		let url = BASE_URL + "asset-regist/list";
		if (url === window.location.pathname.split('?')[0]) {
	  		ajaxRedirect(url);
		}
	});
	
	
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-regist/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

    // date picker
	// $(".date").datepicker({autoclose : true,format: 'dd/mm/yyyy'});
	var idEffectedDate = $("#fromDate").val();
	var idExpiredDate = $("#toDate").val();
	changeDatepickerById(idEffectedDate, idExpiredDate,'#fromDate','#toDate');

	$("input, select, textarea, checkbox").prop("disabled","disabled");
	$("#functionTableDetailEdit").remove();
	$("#btnClearData").remove();
	
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
for(let ware in lstWarehouse){
	var warehouse = lstWarehouse[ware];
	option += '<option value="' + warehouse.code + '">' + warehouse.name +"</option>";
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
//var valueAfterAppend = $(element).val();
//var elementSub = $(element).closest("tr").find(".assetSubCategoryCode");
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




function copyRow(element){
 var htmlInvoceRow = "<tr>" + $(element).parents("tr").html() + "</tr>";
 
	$('#tempTable>tbody').append(htmlInvoceRow);

	//last tr
	let lastTr = $('#tempTable>tbody>tr').length - 1;
	let elementTr = $('#tempTable>tbody>tr')[lastTr];
	let $elementTr = $(elementTr);

	$elementTr.find('.number').text(lastTr + 1);
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
