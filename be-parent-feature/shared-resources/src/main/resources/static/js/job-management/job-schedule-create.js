$(document).ready(function() {
	$("#linkList").on("click", function(event) {
		cancel(event);
	});
	initScheduleCombobox();
	initJobCombobox();
	initStartDate();
	initRadioSection();
	//initCompanyCombobox();

	$('#time').datetimepicker({
		format : "HH:mm:ss",
		//minDate : new Date(),
		//defaultDate:moment(new Date())
	});

	$("#btn-cancel, #btnBack").on('click', function(event) {
        event.preventDefault();
		cancel(event);
	});

	$('.btn-save').on('click', function(event) {
		var oneTime = $("#rdOneTime").is(":checked");
		if (oneTime){
			var startTime = $('#date').val() + ' ' + $('#time').val();
			$('input[name=startTime]').val(startTime);
		}
		if ($(".j-form-validate").valid()) {
			var form = $('#form-j-edit').serializeArray();
			ajaxSubmit('quartz/job-schedule/save', form, event);
		}
	});
	
	$('#companyId').on('change', function() {
		var comId = $(this).val();
		if(comId === undefined || comId === null | comId === "") {
			$('#select-jobId').prop('disabled', true);
			$("div#rowRecurring *").prop('disabled', true);
		} else {
			$('#select-jobId').prop('disabled', false);
			$("div#rowRecurring *").prop('disabled', false);
		}
		$('#select-jobId').val('').trigger('change');
    	$('#select-scheduleCode').val('').trigger('change');
		//initJobCombobox(comId);
		//initScheduleCombobox(comId);
	});

	$("#rdOneTime").on('click change', function(e) {
		initOneTime();
	});

	$("#rdRecurring").on('click change', function(e) {
		initRecurring();
	});

	$("#rdImmediate").on('click change', function(e) {
		initImmediate();
	});
	
	$("#btnCreate").unbind().bind('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "quartz/job-schedule/upsert";
		// Redirect to page add
		ajaxRedirect(url);
	});

})

function cancel(event) {
	var url = BASE_URL + "quartz/job-schedule/list";
	ajaxRedirect(url);
}

/*function initCompanyCombobox() {
	var link = 'quartz/getListCompany';
	searchCombobox('#comId', '', link,
    	    function data(params) {
    	        var obj = {
    	        		term: ""
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}
*/
function initScheduleCombobox() {
	var link = 'quartz/getListSchedule';
	searchCombobox('#select-scheduleCode', '', link,
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

function initStartDate() {
	$('.date').datepickerUnit({
		format : DATE_FORMAT,
		changeMonth : true,
		changeYear : true,
		autoclose : true,
		keyboardNavigation : true,
		weekStart : 1,
		startDate : new Date(),
		todayHighlight : true,
		language : APP_LOCALE.toLowerCase(),
		autoclose : true
	}).datepickerUnit('setDate', 'today');
}

function initRadioSection() {
	var oneTime = $("#rdOneTime").is(":checked");
	var recurring = $("#rdRecurring").is(":checked");
	if(oneTime === true) {
		if ($('#startTime').val() != '') {
			var date = $('#startTime').val().split(' ');
			var time = $('#startTimeString').val();
			$('#date').val(date[0]);
			$('#time').val(time);
		}
		initOneTime();
	} else if(recurring === true) {
		initRecurring();
	} else {
		initImmediate();
	}
}

function initImmediate() {
	$('#isImmediate').val("true");
	$('#isOneTime').val("false");
	$('#isdRecurring').val("false");
	$('div#rowOneTime').hide();
	$("div#rowOneTime *").prop('disabled', true);
	$('div#rowRecurring').hide();
	$("div#rowRecurring *").prop('disabled', true);
}

function initOneTime() {
	$('#isImmediate').val("false");
	$('#isOneTime').val("true");
	$('#isdRecurring').val("false");
	$('div#rowOneTime').show();
	$("div#rowOneTime *").addClass("j-required");
	$("div#rowOneTime *").prop('disabled', false);
	$('div#rowRecurring').hide();
	$("div#rowRecurring *").prop('disabled', true);
}

function initRecurring() {
	$('#isImmediate').val("false");
	$('#isOneTime').val("false");
	$('#isdRecurring').val("true");
	$("div#rowOneTime *").removeClass("j-required");
	$('div#rowOneTime').hide();
	$("div#rowOneTime *").prop('disabled', true);
	$('div#rowRecurring').show();
	var object = $('#companyId').length;
	if(object === 0) {
		$("div#rowRecurring *").prop('disabled', false);
	} else {
		var comId = $('#companyId').val();
		if(comId === undefined || comId === null | comId === "") {
			$("div#rowRecurring *").prop('disabled', true);
		} else {
			$("div#rowRecurring *").prop('disabled', false);
		}
	}
}
