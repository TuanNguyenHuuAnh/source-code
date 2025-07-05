$(document).ready(function() {
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : false
	});
	/*$("select[id='companyId']").select2({
		placeholder : COMPANY_FILTER,
		minimumInputLength : 0,
		allowClear : true
	});*/
	/*$("select[id='formId']").select2({
		placeholder : FORM_PLACEHOLDER,
		allowClear : true
	});*/
	fromDateToDateConfig();
	ninitStatus($('#statusValue').val());
	initSelectForm(null, event);
	initSelectOrg(null, event);
	// Search
	$('#btnSearch').click(function(event) {
		var val = $("#statusCode").val();
		search($(this), event);
		if(val === '1' || val === 1) {
//			setStatistic();
		}	
	});

	//Change process
    $('#companyId').change(function(event) {
    	var comId = $(this).val();
    	$('#formId').val('').trigger('change');
    	$('#orgId').val('').trigger('change');
    	if(comId !== undefined && comId !== null && comId !== "") {
    		$('#formId').prop('disabled', '');
    		$('#orgId').prop('disabled', '');
    		initSelectForm(comId, event);
    		initSelectOrg(comId, event);
    	} else {
    		$('#formId').prop('disabled', 'disabled');
    		$('#orgId').prop('disabled', 'disabled');
    	}
    });
    
    $('.ms-options-wrap ul').find('label').css("padding-left", "21px");
    $('.ms-options-wrap button').css('height', '27px');
    $('.ms-options-wrap button').text('     ');
    
    
    $(document).keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if( keycode == '13' && $("#btnSearch").length ){
        	var val = $("#statusCode").val();
    		search($(this), event);
    		if(val === '1' || val === 1) {
//    			setStatistic();
    		}	
        }
    });

	$('.selectpicker').selectpicker();
});

function search(element, event) {
	var data = setConditionSearch();

	try {
		var statusCode = data["statusCode"];
		if (statusCode && statusCode !== '') {
			var status = STATUS_INCOMING;
			switch(statusCode) {
				case "2":
				case 2:
					status = STATUS_OUTCOME;
					break;
				case "3":
				case 3:
					status = STATUS_DRAFT;
					break;
				case "4":
				case 4:
					status = STATUS_RELATED;
					break;
				default: 
					status = STATUS_INCOMING;
					break;
			}
			editParamStatus(status);
			activeMenuByStatus(status);
		}
	} catch(e) {
		console.err(e);
	}

	ajaxSearch("todo/ajaxList", data, 'tableList', element, event);
	getCountForToDoStatistic();
	//setStatistic();
}

function setConditionSearch() {
	setDataSearchHidden();
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearch").val();
	condition["companyId"] = $("#companyId").val();
	condition["priority"] = $("#priority").val();
	condition["fromDate"] = $("#fromDate").val();
	condition["orgId"] = $("#orgId").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	condition["formId"] = $("#formId").val();
	condition["toDate"] = $("#toDate").val();
	condition["statusCode"] = $("#statusCode").val();
	condition["sortName"] = $(".sortName").val();
	condition["sortType"] = $(".sortType").val();
//	condition["hasArchive"] = $("#hasArchive")[0].checked;
	return condition;
}

function initSelectForm(companyId, event) {
	searchCombobox('#formId', '', 'todo/loadFormType',
			function data(params) {
				var obj = {
					keySearch : params.term,
					companyId : companyId,
					isPaging : true
				};
				return obj;
			}, function dataResult(data) {
				return data;
			}, true);
}

function initSelectOrg(companyId, event) {
	searchCombobox('#orgId', '', 'todo/getListOrgByCompanyId',
			function data(params) {
				var obj = {
					keySearch : params.term,
					companyId : companyId,
					isPaging : true
				};
				return obj;
			}, function dataResult(data) {
				return data;
			}, true);
}

function setDataSearchHidden() {
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}

/*function setStatistic() {
	$("#noProcessCount").text($("#noProcessing").val());
	$("#overdueCount").text($("#overdue").val());
	$("#resolvedCount").text($("#resolved").val());
}
*/

function ninitStatus(value) {
	if(value !== undefined && value !== null && value !== '') {
		$("#statusCode").val(value);
	}
}

function setStatistic() {
	$("#noProcessCount").text($("#incomming").val());
}
function xoa_dau(str) {
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g, "a");
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g, "e");
    str = str.replace(/ì|í|ị|ỉ|ĩ/g, "i");
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g, "o");
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g, "u");
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g, "y");
    str = str.replace(/đ/g, "d");
    str = str.replace(/À|Á|Ạ|Ả|Ã|Â|Ầ|Ấ|Ậ|Ẩ|Ẫ|Ă|Ằ|Ắ|Ặ|Ẳ|Ẵ/g, "A");
    str = str.replace(/È|É|Ẹ|Ẻ|Ẽ|Ê|Ề|Ế|Ệ|Ể|Ễ/g, "E");
    str = str.replace(/Ì|Í|Ị|Ỉ|Ĩ/g, "I");
    str = str.replace(/Ò|Ó|Ọ|Ỏ|Õ|Ô|Ồ|Ố|Ộ|Ổ|Ỗ|Ơ|Ờ|Ớ|Ợ|Ở|Ỡ/g, "O");
    str = str.replace(/Ù|Ú|Ụ|Ủ|Ũ|Ư|Ừ|Ứ|Ự|Ử|Ữ/g, "U");
    str = str.replace(/Ỳ|Ý|Ỵ|Ỷ|Ỹ/g, "Y");
    str = str.replace(/Đ/g, "D");
    return str;
}