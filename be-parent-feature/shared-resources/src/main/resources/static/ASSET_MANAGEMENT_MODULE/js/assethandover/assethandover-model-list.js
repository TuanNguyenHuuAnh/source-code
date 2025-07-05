/**
 * store id and status checked check box to Add
 */
var hashMapAdd = new Map();

$(document).ready(function($) {

	// multiple select search
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});

	// datatable load
	$("#tableListAssetProfile").datatables({
		url : BASE_URL + 'asset-handover/ajaxListProfile',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-handover/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});

	// click button add in popup
	$("#btnOK").on('click', function(event) {
		addPrToAssetHandover(hashMapAdd);
	});

	$("#checkAll").on('click', function() {
		var stateCheckedAll = $("#checkAll").is(':checked');
		$(".checkedbx").prop('checked', stateCheckedAll);
		$(".checkedbx").each(function() {
			var row = $(this).parents("tr");
			var id = row.data("id");
			hashMapAdd.set(id, this.checked);
		});

		checkShowButtonAdd();

	});

	$(".checkedbx").on('change', function() {
		var row = $(this).parents("tr");
		var id = row.data("id");
		hashMapAdd.set(id, this.checked);

		// uncheck all
		var isCheckAll = $("#checkAll").is(":checked");
		if (isCheckAll == true && this.checked == false) {
			$("#checkAll").prop('checked', false);
		}

		checkShowButtonAdd();
	});

	// setFormatCurrency();
	checkShowButtonAdd();

});

function checkShowButtonAdd() {
	var isChecked = false;
	var $rowsChild = $(".checkedbx");
	$rowsChild.each(function() {
		if ($(this).is(":checked")) {
			isChecked = true;
			return false;
		}
	});

	if (isChecked) {
		$("#btnOK").removeClass('hide');
		if (!$("#btnAddHide").hasClass('hide')) {
			$("#btnAddHide").addClass('hide');
		}

	} else {
		if (!$("#btnOK").hasClass('hide')) {
			$("#btnOK").addClass('hide');
		}
		$("#btnAddHide").removeClass('hide');
	}
}

function addPrToAssetHandover(hashMap) {
	hashMap.forEach(addPrToAsset); // tach ra fix bug IE
}

function addPrToAsset(value, key, map) {
	// for (var [key, value] of hashMap) {
	if (value) {
		// check id da ton tai trong ds ben duoi chua, neu chua ton tai thi cho
		// copy xuong
		if ($("#pr_" + key).length == 0) {
			// get Max index
			var newID = getMaxIndexPr() + 1;

			var $row = $('#itemSelectedTable tr[data-id="' + key + '"]');

			var id = $row.data("id");
			// muon get duoc da ta da("viet thuong het")
			var assetTypeName = $row.data("assettypename");
			if (assetTypeName == undefined) {
				assetTypeName = '';
			}

			var assetName = $row.data("assetname");
			if (assetName == undefined) {
				assetName = '';
			}

			var assetDescription = $row.data("assetdescription");
			if (assetDescription == undefined) {
				assetDescription = '';
			}

			var assetCategoryName = $row.data("assetcategoryname");
			if (assetCategoryName == undefined) {
				assetCategoryName = 1;
			}

			var assetSubCategoryName = $row.data("assetsubcategoryname");
			if (assetSubCategoryName == undefined) {
				assetSubCategoryName = '';
			}

			var assetTag = $row.data("assettag");
			if (assetTag == undefined) {
				assetTag = '';
			}
			var serialNo = $row.data("serialno");
			if (serialNo == undefined) {
				serialNo = '';
			}
			var quantity = $row.data("quantity");
			if (quantity == undefined) {
				quantity = '';
			}

			var uomCode = $row.data("uomcode");
			if (uomCode == undefined) {
				uomCode = '';
			}
			
			var uomName = $row.data("uomname");
			if (uomName == undefined) {
				uomName = '';
			}

			var assetStatus = $row.data("assetstatus");
			if (assetStatus == undefined) {
				assetStatus = '';
			}
			var assetStatusName = 'New';
			if (assetStatus === 1) {
				assetStatusName = 'In-Use';
			} else if (assetStatus === 2) {
				assetStatusName = 'Re-Use';
			} else if (assetStatus === 5) {
				assetStatusName = 'Broken';
			} else if (assetStatus === 6) {
				assetStatusName = 'Repairing';
			} else if (assetStatus === 7) {
				assetStatusName = 'Obsolete';
			}

			var assetRegisterDate = $row.data("assetregisterdate");
			if (assetRegisterDate == undefined) {
				assetRegisterDate = '';
			}

			var assetSubCategoryCode = $row.data("assetsubcategorycode");
			if (assetSubCategoryCode == undefined) {
				assetSubCategoryCode = '';
			}

			var assetCategoryCode = $row.data("assetcategorycode");
			if (assetCategoryCode == undefined) {
				assetCategoryCode = '';
			}

			var assetTypeCode = $row.data("assettypecode");
			if (assetTypeCode == undefined) {
				assetTypeCode = '';
			}

			var companyCode = $row.data("companycode");
			if (companyCode == undefined) {
				companyCode = '';
			}

			var assetLocationCode = $row.data("assetlocationcode");
			if (assetLocationCode == undefined) {
				assetLocationCode = '';
			}
			var assetLocationName = $row.data("assetlocationname");
			if (assetLocationName == undefined) {
				assetLocationName = '';
			}

			// add row to Cart
			var content = $row.html();
			// thay cot checkbox bang cot action delete
			content = '<tr class="row_pr align-top" id="pr_'
					+ id
					+ '" data-index="'
					+ newID
					+ '">'
					+ '  <td class="text-center"> <a onclick="deletePr('
					+ id
					+ ')"'
					+ '       class="glyphicon glyphicon-remove-sign font-size-20 red" title="Delete"></a>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].id" value=""/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].assetProfileId" value="' + id + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].assetTypeName" value="' + assetTypeName + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].assetTypeCode" value="' + assetTypeCode + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].assetName" value="' + assetName + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].assetDescription" value="' + assetDescription + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].assetCategoryName" value="' + assetCategoryName
					+ '"/>' + '      <input type="hidden" name="lstPrApproved['
					+ newID + '].assetCategoryCode" value="'
					+ assetCategoryCode + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].assetSubCategoryName" value="' + assetSubCategoryName
					+ '"/>' + '      <input type="hidden" name="lstPrApproved['
					+ newID + '].assetSubCategoryCode" value="'
					+ assetSubCategoryCode + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].assetTag" value="' + assetTag + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].serialNo" value="' + serialNo + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].quantity" value="' + quantity + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].uomCode" value="' + uomCode + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].uomName" value="' + uomName + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].companyCode" value="' + companyCode + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].assetLocationName" value="' + assetLocationName + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].assetLocationCode" value="' + assetLocationCode + '"/>'
					+ '      <input type="hidden" name="lstPrApproved[' + newID
					+ '].assetRegisterDate" value="' + assetRegisterDate
					+ '"/>' + '      <input type="hidden" name="lstPrApproved['
					+ newID + '].assetStatus" value="' + assetStatus + '"/>'
					+ ' </td>' + '  <td>' + assetTypeName + ' </td>' + '  <td>'
					+ assetName + ' </td>' + '  <td>' + assetSubCategoryName
					+ ' </td>' + '  <td>' + assetStatusName + ' </td>'
					+ '  <td>' + serialNo + ' </td>' + '  <td>' + assetTag
					+ ' </td>' + '  <td class="text-center">' + quantity
					+ ' </td>' + '  <td>' + uomName + ' </td>'
					+ '  <td id="txtDate" class="text-center">'
					+ assetRegisterDate + '</td>' + '</tr> ';

			$("#tblDataTable1").append(content);
		}
	}
}

function getMaxIndexPr() {
	var $rows = $(".row_pr");
	var max = -1;
	for (var i = 0; i < $rows.length; i++) {
		var id = parseInt($rows[i].dataset.index);
		if (id > max) {
			max = id;
		}
	}
	return max;
}

/*
 * function setConditionSearch() { var condition = {}; condition["fieldSearch"] =
 * $("#fieldSearchHidden").val(); condition["fieldValues"] =
 * $("#fieldValuesHidden").val(); return condition; }
 */

