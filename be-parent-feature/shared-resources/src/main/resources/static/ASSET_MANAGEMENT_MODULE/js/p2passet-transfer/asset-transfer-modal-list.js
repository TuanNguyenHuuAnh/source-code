$(document).ready(
		function($) {

				
			// on click search
		    $('#btnSearchModal').unbind('click');
			$("#btnSearchModal").on('click', function(event) {
				onClickSearch(this, event);
			});
	

			var listAssetChecked=[];
			
			$('#tableListAsset').datatables({
				url : BASE_URL + 'asset-transfer/ajaxListAsset',
				type : 'POST',
				setData : setConditionSearch
			});	
		
			
			$("#checkAll").on(
						'click',
						function() {
							if ($("#checkAll").is(":checked")) {
								$("#tblDataTable > tbody > tr").find(
										"input[type=checkbox]").prop("checked", true);
							} else {
								$("#tblDataTable > tbody > tr").find(
										"input[type=checkbox]").prop("checked", false);
							}
						});
			$('#btnSubmitAsset').unbind('click');
			$('#btnSubmitAsset').on('click', function(event) {
				if (typeof listAssetChecked !== 'undefined' && typeof listAsset !== 'undefined') {
					 listAssetChecked = $('.checkboxes:checkbox:checked');
//					 var selected = [];
//					 $('#checkboxes input:checked').each(function() {
//					     selected.push($(this).id);
//					 });
					 //console.log(listAssetChecked);
					 let html='';
					 listAssetChecked.each(function(item, value){

						 listCurrentAsset.push(value.value);
						 let temp = listAsset[value.id];
						 html+= 	'<tr data-asset-tag="'+temp.assetTag+'">'	+
									'<td class="td-action">'  +
										'<div class="align-center">' +
										'<a id="'+temp.assetTag+'" class="glyphicon glyphicon-remove-sign font-size-20 red j-btn-delete" onclick="delAsset(this)"></a>'    +
										'</div>'	+
									'</td>'       +
									'<td  class="text-left">   <input hidden="hidden" 	name="lstData[#].assetTypeName" 		value="'+temp.assetTypeName			+'"/>'+temp.assetTypeName+'</td>'	+
									'<td  class="text-left">   <input hidden="hidden" 	name="lstData[#].assetName" 			value="'+temp.assetName				+'"/>'+temp.assetName+'</td>'   +
									'<td  class="text-left">   <input hidden="hidden" 	name="lstData[#].assetSubCategoryName" 	value="'+temp.assetSubCategoryName	+'"/>'+temp.assetSubCategoryName+'</td>'		+
									'<td  class="text-left">   <input hidden="hidden" 	name="lstData[#].assetCategoryName" 	value="'+temp.assetCategoryName		+'"/>'+temp.assetCategoryName+'</td>'		+
									'<td  class="text-left">   <input hidden="hidden" 	name="lstData[#].serialNo" 				value="'+temp.serialNumber			+'"/>'+temp.serialNumber+'</td>'		+
									'<td  class="text-left">   <input hidden="hidden" 	name="lstData[#].assetTag" 				value="'+temp.assetTag				+'"/>'+temp.assetTag+'</td>'		+
									'<td  class="text-center"> <input hidden="hidden" 	name="lstData[#].quantity" 				value="'+temp.quantity				+'"/>'+temp.quantity+'</td>'	+
									'<td  class="text-left">   <input hidden="hidden" 	name="lstData[#].uomCode" 				value="'+temp.uomCode				+'"/>'+temp.uomName+'</td>'		+
									'<td hidden="hidden">	   <input 					name="lstData[#].assetTypeCode" 		value="'+temp.assetTypeCode			+'"/></td>'+
									'<td hidden="hidden">	   <input 					name="lstData[#].assetDescription" 		value="'+temp.assetDescription		+'"/></td>'+
									'<td hidden="hidden">      <input 					name="lstData[#].assetOwnerUsername" 	value="'+temp.assetOwnerUsername	+'"/></td>'+
									'<td hidden="hidden">      <input 					name="lstData[#].assetOwnerFullname" 	value="'+temp.assetOwnerFullname	+'"/></td>'+
									'<td hidden="hidden">      <input 					name="lstData[#].assetOwnerDept" 		value="'+temp.assetOwnerDept		+'"/></td>'+
									'<td hidden="hidden">      <input 					name="lstData[#].assetSubCategoryCode" 	value="'+temp.assetSubCategoryCode	+'"/></td>'+
									'<td hidden="hidden">      <input 					name="lstData[#].assetCategoryCode" 	value="'+temp.assetCategoryCode		+'"/></td>'+
									'<td hidden="hidden">      <input 					name="lstData[#].assetStatus" 			value="'+temp.assetStatus			+'"/></td>'+
									'<td hidden="hidden">      <input 					name="lstData[#].assetRegisterDate" 	value="'+moment(temp.registrationDate).format('DD/MM/YYYY')		+'"/></td>'+
									'<td hidden="hidden">      <input 					name="lstData[#].companyCode" 			value="'+temp.companyCode			+'"/></td>'+
									'<td><input type="number" class="form-control j-required" 	min="1" max="'+temp.quantity+'"	name="lstData[#].transferQuantity" 		value="'+temp.quantity				+'"/></td>'		+
									'</tr>';
					 });
				
					 $('#assetSelected').append(html);
					 
				}

				//console.log(listCurrentAsset);
			});	


		});
/*function setConditionSearch() {
	var condition = {};
	condition['transferId']=  $.trim($('#transferId').val());
	condition['assetName'] = $.trim($('#assetName').val());
	condition['serialNumber']=$.trim($('#serialNumber').val());
	condition['assetTag']=$.trim($('#assetTag').val());
	condition['imei']=$.trim($('#imei').val());
	condition['assetCategory']=$.trim($('#assetCategory').val());
	condition['assetSubCategory']=$.trim($('#assetSubCategory').val());
	condition['officeLocation']= $.trim($('#fromLocationCode').val());
	console.log(condition);
	return condition;
}
function onClickSearch(element, event) {



	ajaxSearch("asset-transfer/ajaxListAsset", setConditionSearch(), "tableListAsset", element, event);
}*/