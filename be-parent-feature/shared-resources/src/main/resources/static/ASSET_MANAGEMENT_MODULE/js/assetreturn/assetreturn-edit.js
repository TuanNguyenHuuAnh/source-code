$(document).ready(function() {
	
	let CURRENT_URL = window.location.href;
	let findex = CURRENT_URL.indexOf(BASE_URL);
	let lindex = CURRENT_URL.lastIndexOf('?id');
	let urlType = CURRENT_URL.substring(findex, lindex);
	if(urlType == BASE_URL + 'asset-return/detail') {
		$("#widgetApprovalComment").attr('readonly','readonly');
	}
	
	//getListCategory();
	//getListLocation();
	getListLocationByUsername('', '');

	$(".date").datepicker({
		autoclose : true,
		format : DATE_FORMAT,
		todayHighlight : true
	});
	
	// datatable load
	$("#tableListAssetProfile").datatables({
		url : BASE_URL + 'asset-return/ajaxListProfile',
		type : 'POST',
		setData : setConditionSearch
	});
	
//	$("#assetOwnerFullname").change(function(event) {
//		onClickSearch(this, event);
//	});

	$('#chkActive').attr('checked', true);
	// set readonly code
	/*
	 * if ($("#assetTypeId").val() != "") { $("#countryCode").attr('readonly',
	 * 'readonly'); }
	 */
	// on click cancel
	$('#cancel').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "asset-return/list";

		// Redirect to page list
		ajaxRedirect(url);
	});

	// function validate when click into the button add
	$('#btnAddHiden').on('click', function(event) {
		validate();
	});

	// on click list return to list
	$('#linkList').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "asset-return/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$(window).on("popstate", function () {
		// if the state is the page you expect, pull the name and load it.
		let url = BASE_URL + "asset-return/list";
		if (url === window.location.pathname.split('?')[0]) {
	  		ajaxRedirect(url);
		}
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-return/edit";
		// getValueUsingClass();
		// Redirect to page add
		ajaxRedirect(url);
	});

	// Post edit save job
	$('#button-approval-module-save-draft-id').on('click', function(event) {
		// if ($(".j-form-validate").valid()) {
		var url = "asset-return/edit";
		$("#commentsId").val($("#widgetApprovalComment").val());
		var condition = $("#form-assetreturn-edit").serialize();
		//console.log(condition);
		ajaxSubmit(url, condition, event);
		// }
	});
	$('#button-approval-module-submit-id').click(function(e) {
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
				var url = "asset-return/editsubmit";
				var condition = $("#form-assetreturn-edit").serialize();
				//console.log(condition);
				ajaxSubmit(url, condition, event);
			}
		});
	});

	$("#assetCategoryName").change(function() {
		// $('#assetSubCategoryName').find('option').remove();
		var subCategoryCode = $("#assetCategoryName option:selected").val();
		getListSubCategory(subCategoryCode);
	});

	$("#location").change(function() {
		// $('#assetSubCategoryName').find('option').remove();
		var locationCode = $("#location option:selected").val();
		getListWarehouseCode(locationCode);
	});
	
	$("#location").change(function() {
		onClickSearch(this, event);
	});
	
	$("#warehouse").change(function() {
		onClickSearch(this, event);
	});
	
	$("#assetOwnerFullname").change(function(event) {
		checkShowButtonAdd2();
	}).change();
	
	$("#assetOwnerFullname").change(function(event) {
		deleteAll();
	});
	
	// on click add, check luon dieu kien null owner
	$("#btnAdd").on("click", function(event) {
		var checker = $("#assetOwnerFullname").val();
		$('#assetCategoryName').empty();
		var propertyOf = $('#propertyOf').val();
		getListCategory(propertyOf);
		
	});
	
	getListAccount();

	myFunction();
});


// function receive message display on screen
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
// validate truyen message vao de hien thi
function validate() {
	$("#message-list").html('');
	addMessage(msgAssetAdd);
}

function checkShowButtonAdd2() {
	var isSelected = false;
	var checker = $("#assetOwnerFullname").val();

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

// function delete all detail
function deleteAll() {
	$("#body").html('');
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
function getListAccount() {
	// autocomplete
	$('#assetOwnerFullname').autocomplete({
		serviceUrl : BASE_URL + 'asset-return/getListAccountOwnerProfile',
		paramName : 'inputQuery',
		// delimiter: ",",
		ajaxSettings : {
			global : false
		},
		triggerSelectOnValidInput : false,
		onSelect : function(suggestion) {
			$("#assetOwnerFullname").val(suggestion.value);
			$("#assetOwnerUsername").val(suggestion.id);
			// hamf load theo account
			getListDeptByUsername(suggestion.id);
			$('#location').empty();
			getListLocationByUsername(suggestion.id, suggestion.value);
			onClickSearch(this, event);
			
			return false;
		},
		onInvalidateSelection : function(suggestion) {
			checkShowButtonAdd2();
			$("#assetOwnerUsername").val('');
			$("#assetOwnerDept").val('');
			$("#assetOwnerDeptFullOption").val('');
			$('#assetOwnerDeptFullOption').removeAttr('readonly');		
			deleteAll();
			$('#location').empty();
			getListLocation();
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
// ham done() thay the cho ham success()
function getListDeptByUsername(username) {
	$.ajax(
			{
				url : BASE_URL + "asset-return/getListDeptByUsername?username="
						+ username,
				type : 'GET',
				global : false
			}).done(
			function(result) {
				if (result != null && result.length > 0) {
					result.forEach(function(item) {
						$("#assetOwnerDept").val(item.name);
						if (item.text === null | item.text === undefined
								| item.text === '') {
							$("#assetOwnerDeptFullOption").val(item.name);
						} else {
							$("#assetOwnerDeptFullOption").val(
									item.name + " - " + item.text);
						}
						// $('#assetOwnerDeptFullOption').attr('readonly',
						// 'readonly');
					})
				}
				// else {
				// $('#assetOwnerDeptFullOption').attr('readonly', 'readonly');
				// }
			});
}

function getListWarehouseCode(locationCode) {
	$.ajax(
			{
				url : BASE_URL
						+ "asset-return/getListWarehouseCode?locationCode="
						+ locationCode,
				type : 'GET',
				global : false
			}).done(
			function(result) {
				if (result != null && result.length > 0) {
					let html = '<option value="">--</option>';
					result.forEach(function(item) {
						if (selectedWarehouse == item.text | result.length === 1) {
							html += '<option value="' + item.text
									+ '" selected>' + item.name + '</option>';
						} else {
							html += '<option value="' + item.text + '">'
									+ item.name + '</option>';
						}
					});
					$('#warehouse')[0].options.length = 0;
					$('#warehouse').append(html);
					$('#warehouse').select2({});
				} else {
					$('#warehouse')[0].options.length = 1;
				}
			})
}
function getListLocation() {
	$.ajax({
		url : BASE_URL + "asset-return/getListLocation",
		type : 'GET',
		global : false
	}).done(
			function(result) {
				if (result != null && result.length > 0) {
					let html = '<option value="">--</option>';
					result.forEach(function(item) {
						if (selectedLocation == item.text) {
							html += '<option value="' + item.text
									+ '" selected>' + item.name + '</option>';
						} else {
							html += '<option value="' + item.text + '">'
									+ item.name + '</option>';
						}
					});

					$('#location').append(html);
					$('#location').select2({});
					getListWarehouseCode($("#location option:selected").val());
				}
			})
}

function getListLocationByUsername(username, fullname) {
	$.ajax({
		url : BASE_URL + "asset-return/getListLocationByUsername?username=" + username + "&fullname=" + fullname,
		type : 'GET',
		global : false
	}).done(
			function(result) {
					if (result != null && result.length > 0) {
						let html = '<option value="">--</option>';
						result.forEach(function(item) {
							if (selectedLocation == item.text | result.length === 1) {
								html += '<option value="' + item.text
								+ '" selected>' + item.name + '</option>';
							} else {
								html += '<option value="' + item.text + '">'
								+ item.name + '</option>';
							}
						});
						
						$('#location').append(html);
						$('#location').select2({});
						getListWarehouseCode($("#location option:selected").val());
					}
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

function onClickSearch(element, event) {

	setDataSearchHidden();

	ajaxSearch("asset-return/ajaxListProfile", setConditionSearch(),
			"tableListAssetProfile", element, event);
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
	condition["assetReturnId"] = $.trim($("#assetReturnId").val());
	condition["assetOwnerFullname"] = $.trim($("#assetOwnerFullname").val());
	condition["location"] = $.trim($("#location").val());
	condition["warehouse"] = $.trim($("#warehouse").val());
	condition["assetSubCategoryName"] = $
			.trim($("#assetSubCategoryName").val());
	//console.log(condition);
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}