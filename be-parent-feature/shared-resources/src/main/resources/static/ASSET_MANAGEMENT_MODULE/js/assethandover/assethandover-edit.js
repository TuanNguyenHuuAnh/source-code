// All of VUNH

$(document).ready(function(e) {

	let CURRENT_URL = window.location.href;
	let findex = CURRENT_URL.indexOf(BASE_URL);
	let lindex = CURRENT_URL.lastIndexOf('?id');
	let urlType = CURRENT_URL.substring(findex, lindex);
	if(urlType == BASE_URL + 'asset-handover/detail') {
		$("#widgetApprovalComment").attr('readonly','readonly');
	}
	
	// getListCategory();
	getListDept();

	/*
	$(".date").datepicker({
		autoclose : true,
		format : DATE_FORMAT,
		todayHighlight : true
	});
	*/

	// datatable load
	$("#tableListAssetProfile").datatables({
		url : BASE_URL + 'asset-handover/ajaxListProfile',
		type : 'POST',
		setData : setConditionSearch
	});

	$('#chkActive').attr('checked', true);
	// set readonly code
	/*
	 * if ($("#assetTypeId").val() != "") { $("#countryCode").attr('readonly',
	 * 'readonly'); }
	 */
	// on click cancel
	$('#cancel').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "asset-handover/list";

		// Redirect to page list
		ajaxRedirect(url);
	});

	// on click list return to list
	$('#linkList').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "asset-handover/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$(window).on("popstate", function () {
		// if the state is the page you expect, pull the name and load it.
		let url = BASE_URL + "asset-handover/list";
		if (url === window.location.pathname.split('?')[0]) {
	  		ajaxRedirect(url);
		}
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-handover/edit";
		// getValueUsingClass();
		// Redirect to page add
		ajaxRedirect(url);
	});

	$("#assetCategoryName").change(function() {
		// $('#assetSubCategoryName').find('option').remove();
		var subCategoryCode = $("#assetCategoryName option:selected").val();
		getListSubCategory(subCategoryCode);
	});

	// Post edit save job
	$('#button-approval-module-save-draft-id').on('click', function(event) {
		// clear all message 
		 $("#message-list").html('');
		 $(".message-info").html('');
		// if ($(".j-form-validate").valid()) {
		var url = "asset-handover/edit";
		$("#commentsId").val($("#widgetApprovalComment").val());
		var condition = $("#form-assethandover-edit").serialize();
		//console.log(condition);
		ajaxSubmit(url, condition, event);
		// }
	});
	$('#button-approval-module-submit-id').click(function(e) {
		// clear all message 
		 $("#message-list").html('');
		 $(".message-info").html('');
		// enable warehouse
		$('#warehouse').removeAttr('disabled');
		// enable location
		$('#location').removeAttr('disabled');

		if (!$(".j-form-validate").valid()) {
			$("html").animate({scrollTop: 0 }, 150);
			return;
		}
		
		var $rowsAsset = $("#tblDataTable1 > tbody > tr");
		if ($rowsAsset.length == 0) {
			addMessage(msgAssetDetail);
			$("html").animate({scrollTop: 0 }, 150);
			return;
		}
		popupConfirm(MSG_CONFIRM, function(result) {
			if (result) {
				var url = "asset-handover/editsubmit";
				var condition = $("#form-assethandover-edit").serialize();
				ajaxSubmit(url, condition, event);
			}
		});
	});
	
	$(".inputSearchByPr").change(function(event) {
		getListWarehouseCode(checkShowButtonAdd1);
		
		var id = $('#assetHandoverId').val();
		onClickSearch(this, event, id);
	}).change();

	$('#btnAddHiden').on('click', function(event) {
		// clear all message 
		 $("#message-list").html('');
		 $(".message-info").html('');
		var checker = $("#warehouse option:selected").val();
		if (checker === null | checker === '' | checker === undefined) {
			validate();
			// scroll to Top
			var $container = $("html, body");	
			$container.animate({ scrollTop: 0 }, "1");
		} else {
			checkShowButtonAdd1();
		}
		//validate();
	});

	$("#warehouse").change(function(event) {
		checkShowButtonAdd1();
	});//.change();
	
	$("#warehouse").change(function(event) {
		//console.log('on change')
		deleteAll();
	});


	$("#assetOwnerFullname").change(function(event) {
		deleteAll();
	});

	// TRITV add PDF
	$('#btnExportPdf').on('click', function(event) {
		event.preventDefault();
		var linkExport = "asset-handover/export-pdf";

		var condition = {};
		condition["id"] = $("#abtract-reference-id").val();
		doExportExcelWithToken(BASE_URL + linkExport, "token", condition);
	});
	$("#receiverDeptFullOption").change(function() {
		// $('#assetSubCategoryName').find('option').remove();
		var receiverCode = $("#receiverDeptFullOption option:selected").val();
		$('#receiverDept').val(receiverCode);
		
	});
	// on click add
	$("#btnAdd").on("click", function(event) {
		clearSearchModal();
		var id = $('#assetHandoverId').val();
		var assetOwnerFullname = $('#assetOwnerFullname').val();
		if(assetOwnerFullname === null | assetOwnerFullname === '' | assetOwnerFullname === undefined){
			$("#assetOwnerUsername").val('');
		}
		onClickSearch(this, event, id);
		$('#assetCategoryName').empty();
		var propertyOf = $('#propertyOf').val();
		getListCategory(propertyOf);
	});

	getListAccountReceiver();
	getListAccountOwner()
	getListWarehouseCode(checkShowButtonAdd1);
	myFunction();
	checkShowButtonAddAfterSubmit();
});
function clearSearchModal(){
	$('#assetName').val(null);
	$('#serialNumber').val(null);
	$('#assetTag').val(null);
	$('#imei').val(null);
	$('#assetCategoryName').val(null);
	$('#assetSubCategoryName').val(null);
}
function addMessage(msgTxt) {

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

function checkShowButtonAdd1() {
	var isSelected = false;
	var checker = $("#warehouse option:selected").val();

	if (checker === null | checker === '' | checker === undefined) {
		isSelected = false;
	} else {
		isSelected = true;
	}

	if (isSelected) {
		$("#btnAdd").removeClass('hide');
		if (!$("#btnAddHiden").hasClass('hide')) {
			$("#btnAddHiden").addClass('hide');
		}
		$("#message-list").html('');
	} else {
		if (!$("#btnAdd").hasClass('hide')) {
			$("#btnAdd").addClass('hide');
		}
		$("#btnAddHiden").removeClass('hide');
	}
}
function checkShowButtonAddAfterSubmit() {
	var isSelected = false;
	var checker1 = $("#assetHandoverId").val();
	if (checker1 === null | checker1 === '' | checker1 === undefined) {
		isSelected = false;
	} else {
		isSelected = true;
	}

	if (isSelected) {
		$("#btnAdd").removeClass('hide');
		if (!$("#btnAddHiden").hasClass('hide')) {
			$("#btnAddHiden").addClass('hide');
		}
		$("#message-list").html('');
	} else {
		if (!$("#btnAdd").hasClass('hide')) {
			$("#btnAdd").addClass('hide');
		}
		$("#btnAddHiden").removeClass('hide');
	}
}
// function delete all detail
function deleteAll() {
	$("#body").html('');
}
function validate() {
	$("#message-list").html('');
	addMessage(msgAssetAdd);
}

function getListCategory(propertyOf) {
	$.ajax(
			{
				url : BASE_URL + "asset-handover/getListCategory?propertyOf="
						+ propertyOf,
				type : 'GET',
				global : false
			}).done(
			function(result) {
				if (result != null && result.length > 0) {
					let html = '<option value="">--</option>';
					result.forEach(function(item) {
						if (selectedProfileCategory == item.text) {
							html += '<option value="' + item.text
									+ '" selected>' + item.name + '</option>';
						} else {
							html += '<option value="' + item.text + '">'
									+ item.name + '</option>';
						}
					});

					$('#assetCategoryName').append(html);

					// $('#assetCategoryName').select2({});
				}
			})

}

function deletePr(id) {
	// delete row in PR list
	var row = document.getElementById("pr_" + id);
	row.parentNode.removeChild(row);
	resetIndex();
}
// bỏ một cột trong table
function myFunction() {
	// Prepare data
	var row = $(".j-btn-delete").parents("tr");
	var process = row.data("process");
	if (process === 0 | process === undefined) {

	} else {
		$("#tblDataTable1").each(function() {
			$(this).find('th').first().remove();
		})
		$("#tblDataTable1 > tbody > tr").each(function() {
			$(this).find('td').first().remove();
		})
	}

}
// reset index
function resetIndex() {
	var rowNum = 0;
	$("#tblDataTable1 > tbody > tr").each(
			function() {
				$(this).find("td input:text,input:hidden,textarea").each(
						function() {
							var name = $(this).attr('name');
							if (typeof name !== 'undefined') {
								var indexNeedToReplace = name.substr(0, name
										.lastIndexOf("."));
								var indexReplace = name.substr(0, name
										.lastIndexOf("["))
										+ "[" + rowNum + "]";
								name = name.replace(indexNeedToReplace,
										indexReplace);
								$(this).attr('name', name);
							}
						});
				rowNum++;
			});
}

// author: VUNH
function getListAccountReceiver() {
	// autocomplete
	$('#receiverFullname').autocomplete({
		serviceUrl : BASE_URL + 'asset-handover/getListAccountReceiver',
		paramName : 'inputQuery',
		// delimiter: ",",
		ajaxSettings : {
			global : false
		},
		triggerSelectOnValidInput : false,
		onSelect : function(suggestion) {		
			$("#receiverFullname").val(suggestion.value);
			$("#receiverUsername").val(suggestion.id);
			// hamf load theo account
			getEmailByUsername(suggestion.id);
			getPhoneByUsername(suggestion.id);
			getCostCenterByUsername(suggestion.id);
			return false;
		},
		onInvalidateSelection : function(suggestion) {
			getListDept();
			$("#receiverUsername").val('');
			$("#receiverDept").val('');
			$("#receiverDeptFullOption").val('');
			$("#receiverEmail").val('');
			$("#receiverPhoneNumber").val('');
			
			return false;
		},
		transformResult : function(response) {
			return {
				suggestions : $.map($.parseJSON(response), function(item) {
					return {
						value : item.name,
						id : item.text,
					};
				})
			};
		}
	});
}

function getListDept() {
	$.ajax({
		url : BASE_URL + "asset-handover/getListDept",
		type : 'GET',
		global : false
	}).done(
			function(result) {
				if (result != null && result.length > 0) {
					let html = '<option value="">--</option>';
					result.forEach(function(item) {
						if (selectedReceiverDept == item.name) {
							html += '<option value="' + item.name
									+ '" selected>' + item.name + ' - '
									+ item.text + '</option>';
						} else {
							html += '<option value="' + item.name + '">'
									+ item.name + ' - ' + item.text
									+ '</option>';
						}
					});
					$('#receiverDeptFullOption')[0].options.length = 0;
					$('#receiverDeptFullOption').append(html);
					$('#receiverDeptFullOption').select2({});
					
				} else {
					$('#receiverDeptFullOption')[0].options.length = 1;

				}

			});
}

function getEmailByUsername(username) {
	$.ajax(
			{
				url : BASE_URL + "asset-handover/getEmailByUsername?username="
						+ username,
				type : 'GET',
				global : false
			}).done(function(result) {
		if (result != null && result.length > 0) {
			result.forEach(function(item) {
				$("#receiverEmail").val(item.name);
			})
		}
	});
}
function getPhoneByUsername(username) {
	$.ajax(
			{
				url : BASE_URL + "asset-handover/getPhoneByUsername?username="
						+ username,
				type : 'GET',
				global : false
			}).done(function(result) {
		if (result != null && result.length > 0) {
			result.forEach(function(item) {
				$("#receiverPhoneNumber").val(item.name);
			})
		}
	});
}
function getCostCenterByUsername(username) {
	$.ajax(
			{
				url : BASE_URL + "asset-handover/getCostCenterByUsername?username="
				+ username,
				type : 'GET',
				global : false
			}).done(function(result) {
				if (result != null && result.length > 0) {
					result.forEach(function(item) {
						$("#receiverDeptFullOption").val(item.name);
						$("#receiverDeptFullOption").trigger('change');
					})
				}
			});
}
function getReceiverFullname(username) {
	$.ajax(
			{
				url : BASE_URL + "asset-handover/getReceiverFullname?username="
						+ username,
				type : 'GET',
				global : false
			}).done(function(result) {
		if (result != null && result.length > 0) {
			result.forEach(function(item) {
				$("#receiverFullname").val(item.name);
			})
		}
	});
}
function getListAccountOwner() {
	// autocomplete
	$('#assetOwnerFullname').autocomplete({
		serviceUrl : BASE_URL + 'asset-handover/getListAccountOwnerProfile',
		paramName : 'inputQuery',
		// delimiter: ",",
		ajaxSettings : {
			global : false
		},
		triggerSelectOnValidInput : false,
		onSelect : function(suggestion) {
			$("#assetOwnerFullname").val(suggestion.value);
			$("#assetOwnerUsername").val(suggestion.id);
			getDeptOwnerByUsername(suggestion.id);
			deleteAll();
			var id = $('#assetHandoverId').val();
			onClickSearch(this, event, id);
			return false;
		},
		onInvalidateSelection : function(suggestion) {
			$("#assetOwnerFullname").val('');
			$("#assetOwnerUsername").val('');
			$("#assetOwnerDept").val('');
			$('#assetOwnerDept').removeAttr('readonly');
			deleteAll();
			var id = $('#assetHandoverId').val();
			onClickSearch(this, event, id);
			return false;
		},
		transformResult : function(response) {
			return {
				suggestions : $.map($.parseJSON(response), function(item) {
					return {
						value : item.name,
						id : item.text,
					};
				})
			};
		}
	});
}

function getDeptOwnerByUsername(username) {
	$.ajax(
			{
				url : BASE_URL
						+ "asset-handover/getDeptOwnerByUsername?username="
						+ username,
				type : 'GET',
				global : false
			}).done(function(result) {
		if (result != null && result.length > 0) {
			result.forEach(function(item) {
				$("#assetOwnerDept").val(item.name);
				$('#assetOwnerDept').attr('readonly', 'readonly');
			})
		}
	});
}
function getListWarehouseCode(callback) {
	var propertyOf = $('#propertyOf').val();
	$.ajax({
		url : BASE_URL + "asset-handover/getListWarehouseCode?propertyOf=" + propertyOf,
		type : 'GET',
		global : false
	}).done(
			function(result) {
				let html = '<option value="">--</option>';
				if (result != null && result.length > 0) {
					result.forEach(function(item) {
						if (selectedWarehouse == item.text) {
							html += '<option value="' + item.text
									+ '" selected>' + item.name + '</option>';
						} else {
							html += '<option value="' + item.text + '">'
									+ item.name + '</option>';
						}
					});
				}
				$('#warehouse').html(html);
				$('#warehouse').select2({});
				callback();
			})
			
}

function getListSubCategory(subCategoryCode) {
	$.ajax(
			{
				url : BASE_URL
						+ "asset-return/getListSubCategory?subCategoryCode="
						+ subCategoryCode,
				type : 'GET',
				global : false
			}).done(
			function(result) {
				if (result != null && result.length > 0) {
					let html = '<option value="">--</option>';
					result.forEach(function(item) {
						if (selectedSubCategory == item.name) {
							html += '<option value="' + item.name
									+ '" selected>' + item.name + '</option>';
						} else {
							html += '<option value="' + item.name + '">'
									+ item.name + '</option>';
						}
					});
					$('#assetSubCategoryName')[0].options.length = 0;
					$('#assetSubCategoryName').append(html);
					// $('#assetSubCategoryName').select2({});
				} else {
					$('#assetSubCategoryName')[0].options.length = 1;
				}
			})
}

/**
 * onClickSearch
 * 
 * @param element
 * @param event
 * @returns
 */
// có thêm id truyển về để check trùng popup phía server
function onClickSearch(element, event, id) {

	setDataSearchHidden();
	if (id === null | id === undefined | id === '') {
		ajaxSearch("asset-handover/ajaxListProfile", setConditionSearch(),
				"tableListAssetProfile", element, event);
	} else {
		ajaxSearch("asset-handover/ajaxListProfile?id=" + id,
				setConditionSearch(), "tableListAssetProfile", element, event);
	}
	hashMapAdd = new Map();
	if (!$("#btnAddPrToPo").hasClass('hide')) {
		$("#btnAddPrToPo").addClass('hide');
		$("#btnAddHide").removeClass('hide');
	}
}


function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $.trim($("#fieldSearchHidden").val());
	condition["assetName"] = $.trim($("#assetName").val());
	condition["assetTag"] = $.trim($("#assetTag").val());
	condition["assetCategoryCode"] = $.trim($("#assetCategoryName").val());
	condition["serialNumber"] = $.trim($("#serialNumber").val());
	condition["imei"] = $.trim($("#imei").val());
	condition["assetHandoverId"] = $.trim($("#assetHandoverId").val());
	condition["propertyOf"] = $.trim($("#propertyOf").val());
	condition["warehouse"] = $.trim($("#warehouse").val());
	condition["warehouseCode"] = $.trim($("#warehouseCode").val());
	condition["assetOwnerUsername"] = $.trim($("#assetOwnerUsername").val());
	condition["assetSubCategoryName"] = $.trim($("#assetSubCategoryName").val());
	//console.log(condition);
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}
