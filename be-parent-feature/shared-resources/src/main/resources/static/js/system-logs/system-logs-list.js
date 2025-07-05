$(document).ready(function() {

	$('#fieldSearch').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    });
    
	// init date picker
	/*$('.date').datepickerUnit({
		format: DATE_FORMAT,
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : '${sessionScope.localeSelect}',
		todayHighlight : true,
		onRender : function(date) {
		}
	})*/
	initRangedDate();
	//fromDateToDateConfig();
	
	// multiple select search
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	var $form = $(".j-formSearch");
	$('#btnSearch').click(function(e){
		onClickSearch(this, e);
	})
	
	// on click popup excel
	$("#tableList").on("click","#btnDownload",function(event) {
		event.preventDefault();
		var linkExport = $('#linkExport').val();
		doExportExcelWithToken(linkExport);
	});

	// enter input
	$("#passExportPopup").keyup(function(event) {
		if (event.keyCode === 13) {
			$("#btnExport").click();
		}
	});

	$('#btnExport').click(function() {
		var linkExport = $('#linkExport').val();
		doExportExcelWithToken(linkExport);
	});
	
	// Init company
    searchCombobox('.select-company', SEARCH_LABEL, 'company/get-company',
	    function data(params) {
	        var obj = {
	            keySearch: params.term,
	            isPaging: true
	        };
	        return obj;
	    }, function dataResult(data) {
	        return data;
	    }, false);
});

function onClickSearch(element, event) {
	setDataSearchHidden();
	ajaxSearch("system-logs/list", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["fromDate"] = $('#fromDate').val();
	condition["toDate"] = $('#toDate').val();
	condition["fieldSearch"] = $('#fieldSearchHidden').val();
	condition["fieldValues"] = $('#fieldValuesHidden').val();
	condition["companyId"] = $('#companyId').val();
	condition["page"] = 1;
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}

function doExportExcelWithToken(linkExport) {
	var timerToken;
	var tokenid = getTokenExport();
	timerToken = setInterval(function() {
		unblockbg();
		clearInterval(timerToken);
	}, 1000);

	$("#token").val(tokenid);
	$("#passExport").val($("#passExportPopup").val());
	$("#formSearch").attr("action", BASE_URL + linkExport);
	$("#formSearch").submit();
	blockbg();
}
function getTokenExport() {
	var date = new Date();
	var day = date.getDate();
	var month = date.getMonth();
	var year = date.getFullYear();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();
	var millisecond = date.getMilliseconds();
	var id = day + '' + month + '' + year + '' + hour + '' + minute + ''
			+ second + '' + millisecond;
	return id;
}

function initRangedDate() {
	var idEffectedDate = $('#fromDate').val();
	var idExpiredDate = $('#toDate').val();
	changeDatepicker(idEffectedDate, idExpiredDate);
}

function changeDatepicker(idEffectedDate, idExpiredDate){
	var today = new Date();
	if(idEffectedDate === undefined || idEffectedDate === null || idEffectedDate === '') {
		$("#fromDate").datepickerUnit({autoclose : true, format: DATE_FORMAT}).on('changeDate', function (selected) {
	        var minDate = new Date(selected.date.valueOf());
	        $('#toDate').datepickerUnit({autoclose : true, format: DATE_FORMAT}).datepickerUnit('setStartDate', minDate).datepickerUnit('setEndDate', today);
	        //initTime();
	    }).on('clearDate', function() {
			$('#toDate').datepickerUnit('setStartDate', null);
			//initTime();
		}).datepickerUnit('setDate', 'today').datepickerUnit('setEndDate', new Date());
	} else {
		$("#fromDate").datepickerUnit({autoclose : true,format: DATE_FORMAT}).on('changeDate', function (selected) {
	        var minDate = new Date(selected.date.valueOf());
	        $('#toDate').datepickerUnit({autoclose : true, format: DATE_FORMAT}).datepickerUnit('setStartDate', minDate).datepickerUnit('setEndDate', today);
	        //initTime();
	    }).on('clearDate', function() {
			$('#toDate').datepickerUnit('setStartDate', null);
			//initTime();
		}).datepickerUnit('setDate', idEffectedDate).datepickerUnit('setEndDate', today);
	}

    $("#toDate").datepickerUnit({autoclose : true, format: DATE_FORMAT}).on('changeDate', function (selected) {
    	var maxDate = new Date(selected.date.valueOf());
      	$('#fromDate').datepickerUnit('setEndDate', maxDate);
      	//initTime();
    }).on('clearDate', function() {
    	$('#fromDate').datepickerUnit('setEndDate', today);
    	//initTime();
	});
}