$(document).ready(function() {

	$('#formSearch').on('keyup keypress', function(e) {
		var keyCode = e.keyCode || e.which;
		if (keyCode === 13) {
			e.preventDefault();
			return false;
		}
	});
	//InitDate();
	initScheduleCombobox();
	initJobCombobox();
	initMutiselect();
	initRangedDate();
	// on click search
	$("#btnSearch").on('click', function(event) {
		event.preventDefault();
		onClickSearch(this, event);
	});

	/*$('#select-scheduleId').on('change', function(event) {
		initScheduleCombobox();
	});

	$('#select-jobId').on('change', function(event) {
		initJobCombobox();
	});*/
	
	$('#companyId').on('change', function(event) {
        event.preventDefault();
		$('#select-jobId').val('').trigger('change');
    	$('#select-scheduleId').val('').trigger('change');
	});
});

/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {
	var condition = setConditionSearch();
	ajaxSearch("quartz/job-schedule/ajaxList", condition, "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["jobId"] = $("#select-jobId").val();
	condition["schedId"] = $("#select-scheduleId").val();
	condition["status"] = $("#statusList").val().join(",");
	condition["startTime"] = $("#startTime").val();
	condition["endTime"] = $("#endTime").val();
	condition["companyId"] = $("#companyId").val();
	condition["companyIdList"] = $("#companyIdList").val();
	return condition;

}

function initScheduleCombobox() {
	var link = 'quartz/getListSchedule';
	searchCombobox('#select-scheduleId', '', link,
    	    function data(params) {
    	        var obj = {
    	        		term: params.term, 
    	        		companyId: $('#companyId').val(),
    	        		isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function initJobCombobox() {
	var link = 'quartz/getListJob';
	searchCombobox('#select-jobId', '', link,
    	    function data(params) {
    	        var obj = {
    	        		term: params.term, 
    	        		companyId: $('#companyId').val(),
    	        		isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function initMutiselect() {
	// multiple select search
	$('select[multiple]').multiselect({
		selectAll : true,
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
}

function initRangedDate() {
	var idEffectedDate = $('#startTime').val();
	var idExpiredDate = $('#endTime').val();
	changeDatepickerWithLimit(idEffectedDate, idExpiredDate);
}

function changeDatepickerWithLimit(idEffectedDate, idExpiredDate) {

	var roundDay = 10000;
	var startDate = new Date('01/01/2010');

	if (idEffectedDate != null && idEffectedDate != "undefined") {
		startDate = idEffectedDate;
	}

	var FromEndDate = new Date();
	FromEndDate.setDate(FromEndDate.getDate() + roundDay);

	if (idExpiredDate != null && idExpiredDate != "undefined") {
		FromEndDate = idExpiredDate;
	}
	//	
	var ToEndDate = new Date();
	ToEndDate.setDate(ToEndDate.getDate() + roundDay);

	$('.datepicker-from').datepickerUnit({
		format : DATE_FORMAT,
		changeMonth : true,
		changeYear : true,
		autoclose : true,
		keyboardNavigation : true,
		weekStart : 1,
		endDate : FromEndDate,
		//todayHighlight : true,
		autoclose : true
	}).on(
			'changeDate',
			function(selected) {
				startDate = new Date(selected.date.valueOf());
				startDate.setDate(startDate.getDate(new Date(selected.date
							.valueOf())));
				$('.datepicker-to').datepickerUnit('setStartDate', startDate);
			}).on(
					'clearDate',
					function(selected) {
						$('.datepicker-to').datepickerUnit('setStartDate', null);
					});
	$('.datepicker-to').datepickerUnit({
		format : DATE_FORMAT,
		changeMonth : true,
		changeYear : true,
		autoclose : true,
		keyboardNavigation : true,
		weekStart : 1,
		startDate : startDate,
		endDate : ToEndDate,
		//todayHighlight : true,
		autoclose : true
	}).on(
			'changeDate',
			function(selected) {
				FromEndDate = new Date(selected.date.valueOf());
				FromEndDate.setDate(FromEndDate.getDate(new Date(selected.date
						.valueOf())));
				$('.datepicker-from').datepickerUnit('setEndDate', FromEndDate);
			}).on(
					'clearDate',
					function(selected) {
						$('.datepicker-from').datepickerUnit('setEndDate', null);
					});
}
