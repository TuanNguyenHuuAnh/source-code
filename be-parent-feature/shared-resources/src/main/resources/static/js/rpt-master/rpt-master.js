

function getBaseDataForGridSearch() {
	var fieldValuesTemp = [];
	var fieldDatasTemp = [];
	if (FIELD_SELECT != undefined) {
		for (var i = 0; i < FIELD_SELECT.length; i++) {
			fieldDatasTemp.push(FIELD_SELECT[i].searchCode);
			var valueOfControl = '';
			if (FIELD_SELECT[i].inputType == 'T') {
				if ($("input[name='" + FIELD_SELECT[i].searchCode + "']") != undefined) {
					valueOfControl = $("input[name='" + FIELD_SELECT[i].searchCode + "']").val();
				}
			} else if (FIELD_SELECT[i].inputType == 'C') {
				if ($("select[name='" + FIELD_SELECT[i].searchCode + "']") != undefined) {
					valueOfControl = $("select[name='" + FIELD_SELECT[i].searchCode + "']").val();
				}
			} else if (FIELD_SELECT[i].inputType == 'S') {
				if ($("select[name='" + FIELD_SELECT[i].searchCode + "']") != undefined) {
					valueOfControl = $("select[name='" + FIELD_SELECT[i].searchCode + "']").val();
				}
			} else if (FIELD_SELECT[i].inputType == 'D') {
				if ($("input[name='" + FIELD_SELECT[i].searchCode + "']") != undefined) {
					valueOfControl = $("input[name='" + FIELD_SELECT[i].searchCode + "']").val();
				}
			} else if (FIELD_SELECT[i].inputType == 'M') {
				if ($("input[name='" + FIELD_SELECT[i].searchCode + "']") != undefined) {
					valueOfControl = $("input[name='" + FIELD_SELECT[i].searchCode + "']").val();
				}
			} else if (FIELD_SELECT[i].inputType == 'X') {
				if ($("select[name='" + FIELD_SELECT[i].searchCode + "']") != undefined) {
					valueOfControl = $("select[name='" + FIELD_SELECT[i].searchCode + "']").val().toString().split(',').join('~');
				}
			}
			if (valueOfControl == '') {
				valueOfControl = ' '
			}
			fieldValuesTemp.push(valueOfControl);
		}
	}
	var condition = {};
	condition['fieldDatas'] = fieldDatasTemp;
	condition['fieldValues'] = fieldValuesTemp;
	return condition;

}

function getBaseDataSearch() {
	var fieldValuesTemp = [];
	var fieldDatasTemp = [];
	if ($("#fieldSearch").val() != null && $("#fieldSearch").val() != '') {
		var fieldValues = $("#search-filter").val();
		var dataSearch = $("#fieldSearch").val();
		if (fieldValues.length > 0) {
			for (var i = 0; i < fieldValues.length; i++) {
				fieldDatasTemp.push(fieldValues[i]);
				fieldValuesTemp.push(dataSearch);
			}
		} else {
			if (FIELD_SELECT != null) {
				for (var i = 0; i < FIELD_SELECT.length; i++) {
					fieldDatasTemp.push(FIELD_SELECT[i].value);
					fieldValuesTemp.push(dataSearch);
				}
			}

		}
	} else {

		var fieldValues = $("#search-filter").val();
		var dataSearch = '%%';
		if (fieldValues.length > 0) {

			for (var i = 0; i < fieldValues.length; i++) {
				fieldDatasTemp.push(fieldValues[i]);
				fieldValuesTemp.push(dataSearch);
			}
		} else {
			if (FIELD_SELECT != null) {
				for (var i = 0; i < FIELD_SELECT.length; i++) {
					fieldDatasTemp.push(FIELD_SELECT[i].value);
					fieldValuesTemp.push(dataSearch);
				}
			}
		}
	}
	var condition = {};
	condition['fieldDatas'] = fieldDatasTemp;
	condition['fieldValues'] = fieldValuesTemp;
	return condition;
}

function setBaseDataSearchHidden() {
	var condition = getBaseDataSearch();
	$("#inptFieldDatasHidden").val(condition.fieldDatas);
	$("#inptFieldValuesHidden").val(condition.fieldValues);
	$("#fieldDatas").val(condition.fieldDatas);
	$("#fieldValues").val(condition.fieldValues);
}
function setBaseDataForGridSearchHidden() {
	var condition = getBaseDataForGridSearch();
	$("#inptFieldDatasHidden").val(condition.fieldDatas);
	$("#inptFieldValuesHidden").val(condition.fieldValues);
	$("#fieldDatas").val(condition.fieldDatas);
	$("#fieldValues").val(condition.fieldValues);
}

function getBaseDataSearch2(boxMain, FIELD_SELECT) {
	var fieldValuesTemp = [];
	var fieldDatasTemp = [];
	if (FIELD_SELECT != undefined) {
		for (var i = 0; i < FIELD_SELECT.length; i++) {
			fieldDatasTemp.push(FIELD_SELECT[i].searchCode);
			var valueOfControl = '';
			if (FIELD_SELECT[i].inputType == 'T') {
				if ($("input[name='" + FIELD_SELECT[i].searchCode + "']") != undefined) {
					valueOfControl = $("#" + boxMain).find("input[name='" + FIELD_SELECT[i].searchCode + "']").val();
				}
			} else if (FIELD_SELECT[i].inputType == 'C') {
				if ($("select[name='" + FIELD_SELECT[i].searchCode + "']") != undefined) {
					valueOfControl = $("#" + boxMain).find("select[name='" + FIELD_SELECT[i].searchCode + "']").val();
				}
			} else if (FIELD_SELECT[i].inputType == 'S') {
				if ($("select[name='" + FIELD_SELECT[i].searchCode + "']") != undefined) {
					valueOfControl = $("#" + boxMain).find("select[name='" + FIELD_SELECT[i].searchCode + "']").val();
				}
			} else if (FIELD_SELECT[i].inputType == 'D') {
				if ($("input[name='" + FIELD_SELECT[i].searchCode + "']") != undefined) {
					valueOfControl = $("#" + boxMain).find("input[name='" + FIELD_SELECT[i].searchCode + "']").val();
				}
			}

			if (valueOfControl == '') {
				valueOfControl = '%%';
			}

			fieldValuesTemp.push(valueOfControl);
		}
	}
	var condition = {};
	condition['fieldDatas'] = fieldDatasTemp;
	condition['fieldValues'] = fieldValuesTemp;
	return condition;
}

function setBaseDataSearchHidden2(boxMain, FIELD_SELECT) {
	var condition = getBaseDataSearch2(boxMain, FIELD_SELECT);
	$("#" + boxMain).find(".inptFieldDatasHidden").val(condition.fieldDatas);
	$("#" + boxMain).find(".inptFieldValuesHidden").val(condition.fieldValues);
	$("#" + boxMain).find(".fieldDatas").val(condition.fieldDatas);
	$("#" + boxMain).find(".fieldValues").val(condition.fieldValues);
}
function buildBoxSearch(CONTROLLER_NAME, boxSearchId, boxMain, boxTableId, callback) {
	// blockbg();
	var url = CONTROLLER_NAME + "/buildBoxSearch";
	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : {
			'boxSearchId' : boxSearchId,
			'boxTableId' : boxTableId,
			'boxMain' : boxMain
		},
		success : function(data) {
			callback(data);
		},
		complete : function(result) {
			// unblockbg();
		},
		error : function(xhr, textStatus, error) {
			// unblockbg();
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}
function buildBoxSearchNonGrid(CONTROLLER_NAME, boxSearchId, boxMain, boxTableId, callback) {
	blockbg();
	var url = CONTROLLER_NAME + "/buildBoxSearchNonGrid";
	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : {
			'boxSearchId' : boxSearchId,
			'boxTableId' : boxTableId,
			'boxMain' : boxMain
		},
		success : function(data) {
			callback(data);
		},
		complete : function(result) {
			unblockbg();
		},
		error : function(xhr, textStatus, error) {
			unblockbg();
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

function buildBoxImport(CONTROLLER_NAME, boxMain, boxTableId, callback) {
	// blockbg();
	var url = CONTROLLER_NAME + "/buildBoxImport";
	url = url.replace("//", "/");
	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : {
			'boxTableId' : boxTableId,
			'boxMain' : boxMain
		},
		success : function(data) {
			callback(data);
		},
		complete : function(result) {
			// unblockbg();
		},
		error : function(xhr, textStatus, error) {
			// unblockbg();
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

function getUrlParameter(sParam) {
	var sPageURL = decodeURIComponent(window.location.search.substring(1)), sURLVariables = sPageURL.split('&'), sParameterName, i;

	for (i = 0; i < sURLVariables.length; i++) {
		sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] === sParam) {
			return sParameterName[1] === undefined ? true : sParameterName[1];
		}
	}
};

function confirmMessage(message, callback) {
	bootbox.confirm({
		message : message,
		buttons : {
			confirm : {
				label : 'Yes',
				className : 'btn-success'
			},
			cancel : {
				label : 'No',
				className : 'btn-danger'
			}
		},
		callback : function(result) {
			if (result == true) {
				callback();
			}
		}
	})
};

function getData(CONTROLLER_NAME, subController, boxMain, boxTable, sessionKey, functionCode, callback) {
	var url = CONTROLLER_NAME + subController;
	url = url.replace("//", "/");
	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : {
			'sessionKey' : sessionKey,
			'functionCode' : functionCode,
			'boxMain' : boxMain,
			'boxTableId' : boxTable
		},
		success : function(data) {
			callback(data);
		},
		complete : function(result) {

		},
		error : function(xhr, textStatus, error) {

			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

////////////////////////////////////////////////////////////////////////////
/**
 * Ajax with callback method
 * @param url
 * @param condition
 * @param callback
 */
function ajaxWithCallback(url, condition, element, callback) {
	var me = $(element);
	if (me.data('requestRunning')) {
		return;
	}
	me.data('requestRunning', true);

	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : condition,
		success : function(data) {
			callback(data);
		},
		complete : function(result) {
			me.data('requestRunning', false);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

function getDataImport(controller, functionCode, sessionKey, boxMain, boxTable, callback) {
	var url = controller + "/get-data-import";
	var condition = {
		'controller' : controller,
		'functionCode' : functionCode,
		'sessionKey' : sessionKey,
		'boxMain' : boxMain,
		'boxTable' : boxTable
	};
	ajaxWithCallback(url, condition, $("#" + boxMain).find("#" + boxTable), callback);
}

function buildBoxImport(controller, functionCode, sessionKey, boxMain, boxImport, boxTable, callback) {
	var url = controller + "/build-box-import";
	var condition = {
		'controller' : controller,
		'functionCode' : functionCode,
		'sessionKey' : sessionKey,
		'boxMain' : boxMain,
		'boxImport' : boxImport,
		'boxTable' : boxTable
	};
	ajaxWithCallback(url, condition, $("#" + boxMain).find("#" + boxImport), callback);
}

