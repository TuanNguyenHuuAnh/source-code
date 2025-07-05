$(document).ready(function($) {
	$('#fieldSearch, #search-box').bind("keypress", function (event) {
		if (event.keyCode == '13') {
			return false;
		}
	});

	$("#btnSearchAll").on('click', function(event) {
		onClickSearchAll(this, event);
	});
	$(".btn-export").on("click", function(event) {
		onClickExport(this, event);
	});
	function onClickExport(element, event) {
		let linkExport = BASE_URL + PAGE_URL + "/export-excel";
		var row = $(element).parents("tr");
		var memoCode = row.data("code");
		var condition = {
			'memoNo': memoCode,
		}
		doExportFileExcel(linkExport, "token", condition);
	}

	// datatable load
    if (SEARCH_DTO.searchType == 1){ // search field
        $("#tableList").datatables({
            url: BASE_URL + PAGE_URL + '/ajaxList',
			type: 'POST',
            setData: setConditionSearch
        });
    }else{ // search all
        $("#tableList").datatables({
            url: BASE_URL + PAGE_URL + '/ajaxList',
		    type: 'POST',
            setData: setConditionSearchAll
        });
    }


	// on click search
	setDataSearchHidd();

	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
		$('.close').trigger('click');
	});

	// multiple select
	$('select[multiple]').multiselect({
		columns: 1,
		placeholder: SEARCH_LABEL,
		search: true
	});

	// on click add
	$("#addNew").on("click", function() {
		let url = BASE_URL + PAGE_URL + "-import/list";

		// Redirect to page add
		ajaxRedirectWithCondition(url, setConditionSearch());
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		onclickEdit(this, event);
	});

	// on click delete
	$(".j-btn-delete").on("click", function(event) {
		onclickDelete(this, event);
	});

	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		onClickDetail(this, event);
	});

	// refesh click
	$("#btnRefresh").unbind('click').bind('click', function(event) {
		refresh();
	});

	//double click
	//$(".trEdit").on("dblclick", function(event) {
	//	onclickEdit(this, event, $(this));
	//});

	$('#btnSort').on('click', function(event) {
		let url = BASE_URL + PAGE_URL + "/list/sort";

		// Redirect to page detail
		ajaxRedirect(url);
	});

	//Export Excel
	$('#btnExport').click(function() {
		let linkExport = BASE_URL + PAGE_URL + "/export-excel";
		//		 condition
		var condition = setConditionSearch();
		//		 do export excel
		doExportFileExcel(linkExport, "token", condition);
	});

	signatureImage();

	showIconTableSort(C_NAME, 'idDivTable');
	
	
	$(document).on('click', '.dropdown-filter-sort', function(e) {
        e.stopPropagation();
    });
});

/**
 * edit faqs
 * 
 * @param element
 * @param event
 */
function onclickEdit(element, event, row) {
	event.preventDefault();

	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}

	var id = row.data("id");
	let url = BASE_URL + PAGE_URL + "/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirectWithCondition(url, setConditionSearch());
}
function onClickSearchAll(element, event) {
	let condition = {};

	var listCheckInFilterTable = [];
	$('#tableList .dropdown-filter-sort').find('li').find('input').each(function(key, val) {
		let isChecked = $(val).is(':checked');

		if (isChecked){
			listCheckInFilterTable.push($(val).data("column"));
		}
	});
	condition["listCheckInFilterTable"] = listCheckInFilterTable;
	$("#conditionHidd").val(JSON.stringify(condition));



	condition["fieldSearch"] = $('#fieldSearch').val();
    condition["searchType"] = 0;
	ajaxSearch(PAGE_URL + "/ajaxList", condition, "tableList", element, event);
}

function setConditionSearchAll(){
    let condition = {};
    
    condition["fieldSearch"] = $('#fieldSearch').val();
    condition["searchType"] = 0;
    
    return JSON.parse(JSON.stringify(condition));
}
/**
 * delete faqs
 * 
 * @param element
 * @param event
 */
function onclickDelete(element, event) {
	var row = $(element).parents("tr");
	var id = row.data("id");
	popupConfirmWithButtons(MSG_DEL_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
		if (result) {
			let url = PAGE_URL + "/delete";
			var condition = {
				'faqsId': id,
			}

			ajaxSubmit(url, condition, event);
		}
	});
}

/**
 * detail
 * 
 * @param element
 * @param event
 */
function onClickDetail(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	let url = BASE_URL + PAGE_URL + "/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

/**
 * on click button search
 */
function onClickSearch(element, event) {

	setDataSearchHidd();

	ajaxSearch(PAGE_URL + "/ajaxList", setConditionSearch(), "tableList", element, event);
}

/**
 * setConditionSearch
 */
function setConditionSearch() {
	setDataSearchHidd();
	return JSON.parse($("#conditionHidd").val());
}
function setConditionBySort(){
		setDataSearchHidd();
		return JSON.parse($("#conditionHidd").val());
}
/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	var condition = {};
	// condition["fieldSearch"] = $('#fieldSearch').val();

	$('.field-seach').each(function(key, val) {
		let $el = $(val);

		let id = $el.attr('id');
		let name = $el.attr('name');
		let idConditionSearch = id + 'Condition';
		let value = $('#' + id).val();

		let conditionSearch = $('#' + idConditionSearch).val();

		let disabledAttr = $('#' + id).attr('disabled');

		if ($el.get(0).nodeName == 'SELECT') {
			if ($('#' + id).attr('disabled') != 'disabled') {
				let nameSearch = $('#' + id).find('option:selected').text();
				condition[name] = getValueByConditionSearch(nameSearch, conditionSearch);
			}
		} else if ($el.get(0).nodeName == 'INPUT') {
			let type = $el.data('field-type');
			if (type == 'INPUT') {
				if (disabledAttr != 'disabled') {
					condition[name] = getValueByConditionSearch(value, conditionSearch);
				}
			} else if (type == 'CHECKBOX') {
				if (disabledAttr != 'disabled') {
					if ($('#' + id).is(':checked')) {
						value = 1;
					} else {
						value = 0;
					}
					
					if (!isNull(conditionSearch)){
						condition[name] = getValueByConditionSearch(value, conditionSearch);
					}
				}
			} else if (type == 'DATE') {
				if (value != '') {

					if (conditionSearch != undefined && conditionSearch.indexOf('BETWEEN') > -1) {
						let valueTo = $('#' + id + 'To').val();
						if (DATE_FORMATE_TYPE == 'FULL_DATE') {
							if (disabledAttr != 'disabled') {
								if (isValidDate(value)) {
									value = "CONVERT(DATE, '" + value + ' 00:00:00' + "', 103)";
									valueTo = "CONVERT(DATE, '" + valueTo + ' 00:00:00' + "', 103)";
								}
								let valueSearch = conditionSearch.replace('paramFrom', value).replace('paramTo', valueTo);
								condition[name] = valueSearch;
							}
						} else {
							if (disabledAttr != 'disabled') {
								let valueSearch = conditionSearch.replace('paramFrom', value).replace('paramTo', valueTo);
								condition[name] = valueSearch;
							}
						}
					} else {
						if (DATE_FORMATE_TYPE == 'FULL_DATE') {
							if (disabledAttr != 'disabled') {
								if (isValidDate(value)) {
									value = "CONVERT(DATE, '" + value + ' 00:00:00' + "', 103)";
								}

								condition[name] = conditionSearch + ' ' + value;
							}
						} else {
							if (disabledAttr != 'disabled') {
								condition[name] = conditionSearch + ' ' + value;
							}
						}
					}
				}
			}
		}
	})

	var listCheckInFilterTable = [];

	$('#tableList .dropdown-filter-sort').find('li').find('input').each(function(key, val) {
		let isChecked = $(val).is(':checked');
		
		if (isChecked){
			listCheckInFilterTable.push($(val).data("column"));
		}
	});

	if(isNull($("#fieldSearch").val())) {
		condition["searchType"] = 1;
	}else{
		condition["fieldSearch"] = $('#fieldSearch').val();
		condition["searchType"] = 0;
	}
	condition["listCheckInFilterTable"] = listCheckInFilterTable;
	condition["pageSize"] = SEARCH_DTO.pageSize;

	$("#conditionHidd").val(JSON.stringify(condition));
}

function refresh() {
	$("#fieldSearch").val('');

	$('.field-seach').each(function(key, val) {
		let $el = $(val);

		let id = $el.attr('id');

		if ($el.get(0).nodeName == 'SELECT') {
			if ($('#' + id).attr('disabled') != 'disabled') {
				$('#' + id).val('').change();
			}
		} else if ($el.get(0).nodeName == 'INPUT') {
			let type = $el.data('field-type');
			if (type == 'INPUT') {
				if ($('#' + id).attr('disabled') != 'disabled') {
					$('#' + id).val('');
				}
			} else if (type == 'CHECKBOX') {
				$('#' + id).val('').change();
			} else if (type == 'DATE') {
				$('#' + id).val('');
			}
		}
	})

	$('#btnSearch').trigger('click');
}

function getValueByConditionSearch(value, conditionSearch){
	let rs = '';
	if (isNull(conditionSearch)) {
		if (!isNull(value)){
			rs = value;
		}
	} else {
		if (conditionSearch.indexOf('LIKE') > -1) {
			if (!isNull(value)){
				value = conditionSearch.replaceAll('param', value);
				rs = value;
			}
		} else if (conditionSearch.indexOf('=') >- 1 || conditionSearch.indexOf('<>') >- 1){
			rs = conditionSearch + "N'" + value + "' ";
		} else {
			if (!isNull(value)){
				rs = conditionSearch + value;
			}
		}
	}

	return rs;
}

function refresh() {
	$("#fieldSearch").val('');

	$('.field-seach').each(function(key, val) {
		let $el = $(val);

		let id = $el.attr('id');

		if ($el.get(0).nodeName == 'SELECT') {
			if ($('#' + id).attr('disabled') != 'disabled') {
				$('#' + id).val('').change();
			}
		} else if ($el.get(0).nodeName == 'INPUT') {
			let type = $el.data('field-type');
			if (type == 'INPUT') {
				if ($('#' + id).attr('disabled') != 'disabled') {
					$('#' + id).val('');
				}
			} else if (type == 'CHECKBOX') {
				$('#' + id).val('');
			} else if (type == 'DATE') {
				$('#' + id).val('');
			}
		}
	})

	// Nút xoá
	/*$('.divSearch').each(function(key, val){
		if ($(val).attr('style') != 'display: none;'){
			$(val).find('a').trigger('click');
		}
	})*/

	$('.divSearch').each(function(key, val){
		if ($(val).attr('style') != 'display: none;'){
			let $element =  $(val).find('.field-seach-condition');
			let name = $element.attr('name').replaceAll('Condition', 'Search');
			let isChecked = $('#' + name).is(":checked");
			if (isChecked){
				$('#' + name).trigger('click');
			}
		}
	})

	$('#btnSearch').trigger('click');
}

/**
 * IMAGE signature
 */
function signatureImage() {
	$(".divIframeVideo").each(function(key, val) {
		let $div = $(val);

		var iframe = $div.find('.iframe');
		let link = $(iframe).attr('src');
		addYoutubeVideoToHtml($div.attr('id'), $(iframe).attr('id'), link, 240, 198);
	});
}