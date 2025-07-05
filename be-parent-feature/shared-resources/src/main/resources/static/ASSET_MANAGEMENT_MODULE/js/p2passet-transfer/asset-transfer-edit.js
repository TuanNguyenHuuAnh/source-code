var listCurrentAsset = []; //list asset hien tai (profile)
var warehouseExisted =''; // warehouse đang được chọn
var validReceiver = true;  // dùng để validate receiver
$( document ).ready(function() {
	
	let CURRENT_URL = window.location.href;
	let findex = CURRENT_URL.indexOf(BASE_URL);
	let lindex = CURRENT_URL.lastIndexOf('?id');
	let urlType = CURRENT_URL.substring(findex, lindex);
	if(urlType == BASE_URL + 'asset-transfer/detail') {
		$("#widgetApprovalComment").attr('readonly','readonly');
	}
	
	$('#desLocation').val($('#toLocationName').val());
	$('#contactPerson').val($('#toCustodian').val());
	//readonly Des Location field when have no choice in vendor select
	var desLocation = $('#toLocationName').val();
	var toWarehouseName =$('#toWarehouseName').val();
	if (!desLocation && !toWarehouseName) {
		$('#desLocation').attr('readonly','');
	}
	
	// thêm history confirm
	if ($('#confirmedFlag').val() == 1) {
		var comment = $('.divComment').first().clone();
		comment.find('span, i').remove();
		let html ="<span style='font-weight: bold;'>"+$('#receiverFullName').val()+"</span> <span>Confirmed</span> <span>on</span> <i style='font-weight: normal; color: #3097b6;'>"+$('#updatedDatee').val()+"</i> <span>and status was changed to </span><span class='processStatusName' style='font-weight: normal; color: red;'>Finished</span>";
		comment.find('h5').append(html);
		comment.prependTo($('.row_fluid').first());
		//console.log(comment.html());
	}
	
	// xoá list asset khi propertyOf thay đổi
	 $("#propertyOf").on('change', function (){
		 dellAllAsset();
		 
		 $('#fromLocationCode').trigger('change');
		 $('#toLocationCode').trigger('change');
     });
	 
	//trigger asset sub category change
    $("#assetCategory").on('change',function(){
    	$.ajax(
				{
					url : BASE_URL
							+ "asset-transfer/getListSubCategory",
					data : {
						code : this.value
					},
					type : 'GET',
					global : false
				}).done(function(result) {
			if (result != null && result.length > 0) {
				let html = '<option value="">--</option>';
				result.forEach(function(item) {
								html += '<option value="'
										+ item.id+'">'
										+ item.name
										+ '</option>';

						});
				$('#assetSubCategory')[0].options.length = 0;
				$('#assetSubCategory').append(html);
			} else {
				$('#assetSubCategory')[0].options.length = 1;
			}
		})
    });
	
	$('#vendorSelect').select2();

	 $("#transferType").on('change', function (){
         var value = $("#transferType").val();
         if (value == "Stock"){
             $("#stock").css('display', "block");
             $("#vendor").css('display', "none");
             
             //add required

             addOrRemoveClassRequired();

         } else if (value = "Vendor") {
             $("#stock").css('display', "none");
             $("#vendor").css('display', "block");
             //remove required

             addOrRemoveClassRequired();
         }
     }).change();
	 
	 
	 
	 	
		$('#vendorSelect').on('change',function() {
				var contactPerson = $(this).children("option:selected").data('person');
				var desLocation = $(this).children("option:selected").data('deslocation');
				$('#desLocation').val(desLocation);
					$('#contactPerson').val(contactPerson);
					$('#desLocation').prop('readonly',false);

				});

		$('#fromLocationCode').on('change',function() {
			dellAllAsset();
			warehouseExisted = $('option:selected', $('#warehouseto')).val();
			$('#fromLocationName').val($('option:selected', this).data('locationname'));
			$('#custodianfrom').val('');
			var propertyOf = $('#propertyOf').val();
			$.ajax({
								url : BASE_URL + "asset-transfer/getListWarehouse",
								data : {
									'code' : this.value,
									'propertyOf' : propertyOf
								},
								type : 'GET',
								global : false
							}).done(
							function(result) {
								if (result != null && result.length > 0) {
									let html = '<option value="">--</option>';
									result.forEach(function(item) {
										if (warehouseExisted == item.id) {
											html += '<option disabled="disabled" value="'  //disabled option
												+ item.id
												+ '" data-custodian="'
												+ item.text
												+ '">'
												+ item.name
												+ '</option>';
										}
										else if (item.id == $('#fromWarehouseCode').val()) {
													html += '<option value="'
															+ item.id
															+ '" selected data-custodian="'
															+ item.text
															+ '">'
															+ item.name
															+ '</option>';
													$('#custodianfrom').val(item.text);
												} else {
													html += '<option value="'
															+ item.id
															+ '" data-custodian="'
															+ item.text
															+ '">'
															+ item.name
															+ '</option>';
												}
											});
									$('#warehousefrom')[0].options.length = 0;
									$('#warehousefrom').append(html);
									
								} else {
									$('#warehousefrom')[0].options.length = 1;
								}
							})
							if($('#toLocationCode').val()!=$('#fromLocationCode').val()){ //check khác location thì sẽ bỏ disabled warehouse truoc đó
								$("#warehouseto option").each(function() {
									 $(this).removeAttr('disabled');
								});
							}
		}).change().select2();
	 
	 
		$('#toLocationCode').on('change',function() {
			warehouseExisted = $('option:selected', $('#warehousefrom')).val();
			$('#custodianto').val('');
			var propertyOf = $('#propertyOf').val();
			$.ajax({
								url : BASE_URL + "asset-transfer/getListWarehouse",
								data : {
									'code' : this.value,
									'propertyOf' : propertyOf
								},
								type : 'GET',
								global : false
							}).done(
							function(result) {
								if (result != null && result.length > 0) {
									let html = '<option value="">--</option>';
									result.forEach(function(item) {
												if (warehouseExisted == item.id) {
													html += '<option disabled="disabled" value="'
														+ item.id
														+ '" data-custodian="'
														+ item.text
														+ '">'
														+ item.name
														+ '</option>';
												}
												else if (item.id == $('#toWarehouseCode').val()) {
													html += '<option  value="'
															+ item.id
															+ '" selected data-custodian="'
															+ item.text
															+ '">'
															+ item.name
															+ '</option>';
													$('#custodianto').val(item.text);
												} else {
													html += '<option value="'
															+ item.id
															+ '" data-custodian="'
															+ item.text
															+ '">'
															+ item.name
															+ '</option>';
												}
											});
									$('#warehouseto')[0].options.length = 0;
									$('#warehouseto').append(html);
						
								} else {
									$('#warehouseto')[0].options.length = 1;
								}
							})
							if($('#toLocationCode').val()!=$('#fromLocationCode').val()){
								$("#warehousefrom option").each(function() {
								   $(this).removeAttr('disabled');
								});
							}
		}).change().select2();
	 
		$('#warehousefrom').on(
				'change',
				function() {
					$('#fromWarehouseCode').val(
							$('option:selected', this).val());
					$('#fromWarehouseName').val(
							$('option:selected', this).text());
					$('#fromCustodian').val(
							$('option:selected', this).data(
									'custodian'));
					$('#custodianfrom').val(
							$('option:selected', this).data(
									'custodian'));
					warehouseExisted = $('#fromWarehouseCode').val();
					$('#toLocationCode').trigger('change');
				}).select2();
		$('#warehouseto').on(
				'change',
				function() {
					$('#toWarehouseCode').val(
							$('option:selected', this).val());
					$('#custodianto').val(
							$('option:selected', this).data(
									'custodian'));
					warehouseExisted = $('option:selected', this).val();
					$('#fromLocationCode').trigger('change');
					
				}).select2();
	 
		$('#assetSelected > tr').each(function() {  //get list asset dang duoc chon
			listCurrentAsset.push($(this).data('asset-tag'));
		});
		// on delete click
		$(".j-btn-delete").on("click", function(event) {
			delAsset(this);
		});
		if ($("#receiverFullName").attr("readonly") != "readonly") {
			autoReceiver();
		}

		// on click list
		$('#linkList').on('click', function(event) {
			event.preventDefault();
			var url = BASE_URL + "asset-transfer/list";

			// Redirect to page list
			ajaxRedirect(url);
		});
		// on cancel click
		$("#cancel").on('click', function(event) {
			var url = BASE_URL + "asset-transfer/list";
			ajaxRedirect(url);
		});

		// on click add
		$("#add").on("click", function(event) {
			var url = BASE_URL + "asset-transfer/edit";
			// Redirect to page add
			ajaxRedirect(url);
		});
		// on btnAdd click (thêm asset)
		$("#btnAdd").on('click', function(event) {
			if (validateConditionForAddAsset()) {
				resetSearchField();
				onClickSearch(this, event);
				showModal();
			}else{$("html").animate({scrollTop: 0 }, 150);}
		});
		// btn confirm process
		$('#button-approval-module-confirm-id').on('click',function(event) {
					$("html").scrollTop(0);
					var transferId = $('#id').val();
					

							var url = "asset-transfer/confirm";
							var condition = {
								"id" : transferId
							}
							ajaxSubmit(url, condition, event);
							
							
					
				});
		// btn Save process
		$('#button-approval-module-save-draft-id').on('click',function(event) {
					$("html").scrollTop(0);
					
						resetIndex();
						
							wrapDataForSave();
							var url = "asset-transfer/edit";
							$("#commentsId").val($("#widgetApprovalComment").val());
							var condition = $(
									"#form-assettransfer-edit")
									.serialize();

							ajaxSubmit(url, condition, event);
					
				});
		// btn submit process
		$('#button-approval-module-submit-id').on('click',function(event) {
			if (!$(".j-form-validate").valid()) {
				$("html").animate({scrollTop: 0 }, 150);
				return;
			}
			
			if (!validateListAsset() ){
				$("html").animate({scrollTop: 0 }, 150);
				return;
			}
			
			if (!validateReceiver() ){
				$("html").animate({scrollTop: 0 }, 150);
				return;
			}
			
			if (! validateAssetQuantityWhenLoad()) {
				scrollToAssetTable();
				return;
			}
			
			$("html").animate({scrollTop: 0 }, 150);
			resetIndex();
			if ($(".j-form-validate").valid()) {
				popupConfirm(MSG_CONFIRM, function(result) {
					if (result) {
						wrapDataForSave();
						var url = "asset-transfer/submit";
						var condition = $(
								"#form-assettransfer-edit").serialize();
						ajaxSubmit(url, condition, event);
					}
				});
			}
		});
		
		// TRITV add PDF
		$('#btnExportPdf').on('click', function(event) {
	    	event.preventDefault();
			var linkExport = "asset-transfer/export-pdf";
			
			var condition = {};
			condition["id"] = $("#abtract-reference-id").val();
			doExportExcelWithToken(BASE_URL + linkExport, "token", condition);
		});

});

function setConditionSearch() {

	var condition = {};

	condition['assetName'] = $.trim($('#assetName').val());
	condition['serialNumber'] = $.trim($('#serialNumber').val());
	condition['assetTag'] = $.trim($('#assetTag').val());
	condition['imei'] = $.trim($('#imei').val());
	condition['assetCategory'] = $.trim($('#assetCategory').val());
	condition['assetSubCategory'] = $.trim($('#assetSubCategory').val());
	/*condition['officeLocation'] = $.trim($('#fromLocationCode').val());*/
	condition['officeLocation'] = $.trim($('#warehousefrom').val());
	condition['propertyOf'] = $.trim($('#propertyOf').val());
	condition['listChecked'] = listCurrentAsset.toString();
	//console.log(condition);
	return condition;
}
function onClickSearch(element, event) {

	ajaxSearch("asset-transfer/ajaxListAsset", setConditionSearch(),
			"tableListAsset", element, event);
}
// xoá tat ca asset dang duoc chon khi thay doi propertyOf hoac from location
function dellAllAsset(){
	if (($('#assetSelected').data('location')!=$('#fromLocationCode').val()) || ($('#assetSelected').data('propertyof')!=$('#propertyOf').val())) {
		var allRow = $('tbody > tr');
		allRow.remove();
		listCurrentAsset.length=0;
		$('#assetSelected').data('location',$('#fromLocationCode').val());
		$('#assetSelected').data('propertyof',$('#propertyOf').val());
		//console.log(listCurrentAsset);
	}
}

function delAsset(self) {
	var row = $(self).parents("tr");
	var assetTag = row.data("asset-tag");
	listCurrentAsset
			.splice($.inArray(assetTag.toString(), listCurrentAsset), 1);
	//console.log(assetTag);
	//console.log(listCurrentAsset);
	row.remove();
}

// sắp xếp lại index cho list asset
function resetIndex() {
	var num = 0;
	$('#assetSelected > tr').each(
			function() {
				$(this).find("td input:hidden, input:text, :input").each(
						function() {
							var name = $(this).attr('name');
							if (typeof name !== 'undefined') {
								var indexNeedToReplace = name.substr(0, name
										.lastIndexOf("."));
								var indexReplace = name.substr(0, name
										.lastIndexOf("["))
										+ "[" + num + "]";
								name = name.replace(indexNeedToReplace,
										indexReplace);
								$(this).attr('name', name);
								//console.log($(this).attr('name'));
							}
						});
				num++;
			});
}
function setParamForForm(e) {
	// validate GUI
	if ($(".j-form-validate").valid()) {
		// remove all disabled
		//$('input:disabled, select:disabled, textarea:disabled').removeAttr('disabled');

	} else {
		setTimeout(function() {
			$(".j-form-validate").find(":input.error:first").focus();
		}, 500);

		return false;
	}

	return true;
}
// validate co duoc mở popup hay khong
function validateConditionForAddAsset(){
	$("#message-list").html('');
	$('.checkError').remove();

	if($('option:selected', $('#warehousefrom')).val()=='' ){
		addMessage(msgAssetAdd);
		return false;
	}
	return true;
}
// validate asset detail information
function validateListAsset() {
	// remove all old validate
	$("#message-list").html('');
	$('.checkError').remove();
	var $rowsAsset = $("#assetSelected > tr");
	if ($rowsAsset.length == 0) {
		// let htmlput = '<label class="checkError" style="color:red"
		// id="checkError_paymentTerm">'+msgPaymemtTerm+'</label>';
		// $("#tblDataTable1").after(htmlput);

		addMessage(msgAssetDetail);
		return false;
	}
	return true;
}
function validateReceiver(){
	if (validReceiver == false && $('#transferType').val()=="Stock") {
	$("#message-list").html('');
	$('.checkError').remove();
	addMessage(msgInvalidReceiver);
	return false;
}
	return true;
}
function addMessage(msgTxt) {
	/*
	 * $("#message-list").append( '<div class="alert alert-danger
	 * alert-dismissible">'+ ' <button aria-hidden="true" data-dismiss="alert"
	 * class="close" type="button">×</button><div >' + msgTxt+'</div></div>');
	 */
	if ($.trim($("#message-list").html()).length == 0) {
		$("#message-list")
				.append(
						'<div class="alert alert-danger alert-dismissible">'
								+ '<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
								+ '<h4>'
								+ '    <i class="icon fa fa-ban"></i>Alert!'
								+ '</h4>' + '  <div id="message-list-parent"> '
								+ '       <div>' + msgTxt + '</div>'
								+ '  </div>' + ' </div>');
	} else {
		$("#message-list-parent").append('<div>' + msgTxt + '</div>');
	}
}
function autoReceiver() {
	// autocomplete
	$('#receiverFullName').autocomplete({
		serviceUrl : BASE_URL + 'asset-transfer/find-receiver',
		paramName : 'inputQuery',
		// delimiter: ",",
		ajaxSettings : {
			global : false
		},
		triggerSelectOnValidInput : true,
		onSelect : function(suggestion) {
			$("#receiverFullName").val(suggestion.value);
			$("#receiverUserName").val(suggestion.id);
			validReceiver=true;
			return false;
		},
		onInvalidateSelection : function(suggestion) {
			$("#receiverUserName").val('');
			validReceiver=false;
			return false;
		},
		transformResult : function(response) {
			return {
				suggestions : $.map($.parseJSON(response), function(item) {
					return {
						value : item.name,
						id : item.id,
					};
				})
			};
		}
	});
}
function showModal() {
	$('#myModal').modal('show');
	$('#myModal').css('z-index', 5000);
}

/* Tranlth 27/05/2019 Thêm mới Task #23724 */
function checkStatus(event) {
	if (event.checked) {
		$('.chkStatus').prop('checked', true);
	} else {
		$('.chkStatus').prop('checked', false);
	}
}

function addOrRemoveClassRequired(){
    var value = $("#transferType").val();
    if (value == "Stock"){
    	
    	$( "#toLocationCode" ).addClass( "j-required" );
    	$( "#warehouseto" ).addClass( "j-required" );
    	$( "#custodianto" ).addClass( "j-required" );
    	
    	$( "#desLocation" ).removeClass( "j-required" );
    	$( "#vendorSelect" ).removeClass( "j-required" );
    	$( "#contactPerson" ).removeClass( "j-required" );
        //add required
        $( "#receiverFullName" ).addClass( "j-required" );
        $( "#lbReceiver" ).addClass( "required" );
    } else if (value = "Vendor") {
    	
    	$( "#toLocationCode" ).removeClass( "j-required" );
    	$( "#warehouseto" ).removeClass( "j-required" );
    	$( "#custodianto" ).removeClass( "j-required" );
    	
    	$( "#desLocation" ).addClass( "j-required" );
    	$( "#vendorSelect" ).addClass( "j-required" );
    	$( "#contactPerson" ).addClass( "j-required" );
        //remove required
        $( "#receiverFullName" ).removeClass( "j-required" );
        $( "#lbReceiver" ).removeClass( "required" );
    }
}
function wrapDataForSave(){
	var value = $("#transferType").val();
	if (value == "Stock") {
		/*$('#toLocationName').val($('option:selected', $('#toLocationCode')).data('locationname'));*/
		$('#toLocationName').val($('option:selected', $('#toLocationCode')).data('locationname'));
		$('#toWarehouseCode').val($('option:selected', $('#warehouseto')).val());
		$('#toWarehouseName').val($('option:selected', $('#warehouseto')).text());
		if ($('#toWarehouseName').val()=='--') {
			$('#toWarehouseName').val('');
		}
		$('#toCustodian').val($('#custodianto').val());
	}else if(value == "Vendor"){
		$('#toLocationCode').val('');
		$('#toLocationName').val($('#desLocation').val());
		$('#toWarehouseCode').val('');
		$('#toWarehouseName').val($('option:selected', $('#vendorSelect')).val());
		$('#toCustodian').val($('#contactPerson').val());
	}
}

function resetSearchField(){
	$('#formSearchAsset').each(
			function() {
				$(this).find(".form-control").each(
						function() {
							$(this).val('');
						});
			});
	
	//TRITV fix load asset by propertyOf
	$('#assetCategory').empty();
	var propertyOf = $('#propertyOf').val();
	getListCategory(propertyOf);
}

//TRITV fix load asset by propertyOf
function getListCategory(propertyOf) {
	$.ajax(
	{
		url : BASE_URL + "asset-return/getListCategory?propertyOf="
				+ propertyOf,
		type : 'GET',
		global : false
	}).done(
	function(result) {
		if (result != null && result.length > 0) {
			let html = '<option value="">--</option>';
			result.forEach(function(item) {
				
				html += '<option value="' + item.text + '">'
				+ item.name + '</option>';
			});

			$('#assetCategory').append(html);
		}
	});
}


// validate list asset sau khi save và trước khi submit còn availabel hay không
function validateAssetQuantityWhenLoad(){
	var countOut =0;// result.length
	var countIn=0;// count = result.length => right (else) false
	$.ajax({
	    type: "POST",
	    url: BASE_URL + "asset-transfer/getListAssetWithQuantity",
	    dataType: "json",
	    traditional: true,
	    async: false,
	    data: {
	        tags: listCurrentAsset.toString(),
	    }
	}).done(function(result){
		if (result != null && result.length > 0) {
			countOut = result.length;
			var table = $('#assetSelected');
			var warehouseCode  = $('#warehousefrom').val();
			result.forEach(function(item){
				let row = table.find("tr[data-asset-tag='" + item.name + "']");
				let tdName = row.find("td").eq(1);
				let tdQuantity = row.find("td").eq(27);
				let quantity = parseInt(tdQuantity.find('input').val());
//				let tdQuantity = row.find("td:contains('"+item.name+"')");
				let quantityInDB = parseInt(item.text);
				if (quantity > quantityInDB || warehouseCode.localeCompare(item.id)!=0) {
					tdName.find('p[class="error"], br').remove();
					tdName.append('</br><p class="error">This asset is not available. Please choose another one</p>');
					
				}else{countIn++;}

			});
		
			}
				else{
					countIn++;
					$('#assetSelected').find('tr').each(function(){
						let tdName =$(this).find('td').eq(1);
						tdName.find('p[class="error"], br').remove();
						tdName.append('</br><p class="error">This asset is not available. Please choose another one</p>');
					});
					}
	});
	
	if (countIn==countOut) {
		return true;
	}
	return false;
}
function scrollToAssetTable(){
	$("html, body").animate({scrollTop: $('#tblDataTable').offset().top - 150 }, 150);
}
