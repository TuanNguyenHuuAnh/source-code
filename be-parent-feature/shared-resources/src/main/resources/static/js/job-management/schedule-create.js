$(document).ready(function() {

	$("#linkList").on("click", function(event) {
        event.preventDefault();
		cancel(event);
	});
	checkParameter();
	//initRangedDate();
	//initCompanyCombobox();
	
	/*$('#time1').datetimepicker({
		format : "HH:mm:ss",
		defaultDate:moment(new Date()).hours(0).minutes(0).seconds(0).milliseconds(0)
	}).on("dp.change", function (e) {
		initTime();
	});
	$('#time2').datetimepicker({
		format : "HH:mm:ss",
		defaultDate:moment(new Date()).hours(0).minutes(0).seconds(0).milliseconds(0)
	});*/

	$('#btn-cancel,#btnBack').on('click', function(event) {
        event.preventDefault();
		cancel(event);
	});

	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var id = $("#id").val();
			var url = "quartz/schedule/save?id=" + id;
			var values = $("#form-j-edit").serializeArray();
			ajaxSubmit(url, values, event);
		}
	});

	$('#btnGetDescription').on('click', function() {
		parseName('#cronExpression', '#description');
	})

	/*$('#cronExpression').on('keyup blur change', function() {
		validate(event);
	});*/
	
	$("#btnCreate").on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "quartz/schedule/upsert";
		// Redirect to page add
		ajaxRedirect(url);
	});
	
	initRangedDate();
	//initTime();
})

function parseName(cronElement, resultElement) {
	var cron = $(cronElement).val();
	var result = cronstrue.toString(cron, {
		throwExceptionOnParseError : false
	});
	$(resultElement).val(result);
}

/*function validate() {
	var cron = $('#cronExpression').val();
	var url = BASE_URL + 'quartz/schedule/cron-check?cron=' + cron;
	$.ajax({
		type : 'GET',
		url : url,
		success : function(data) {
			var message;
			if (data == true) {
				message = 'Valid cron expression';
				$('#cronExpression-error').removeClass('error');
				$('#btnApply').prop('disabled', false);
				$('#btnGetDescription').prop('disabled', false);
			} else {
				message = 'Invalid cron expression';
				$('#cronExpression-error').addClass('error');
				$('#btnApply').prop('disabled', true);
				$('#btnGetDescription').prop('disabled', true);
			}
			$('#cronExpression-error').removeClass('hidden');
			$('#cronExpression-error').css('display', 'block');
			$('#cronExpression-error').text(message);
		},
		error : function(xhr, textStatus, error) {
			unblockbg();
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});

}*/

function checkParameter() {
	var id = getUrlParameter('id');
	if (id != null || id !== undefined) {
		$('#schedCode').prop('readonly', true);
	}
}

function cancel(event) {
	var url = BASE_URL + "quartz/schedule/list";
	ajaxRedirect(url);
}

function initRangedDate() {
	var idEffectedDate = $('#startDate').val();
	var idExpiredDate = $('#endDate').val();
	changeDatepicker(idEffectedDate, idExpiredDate);
}

function initCompanyCombobox() {
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

/*function changeDatepickerWithLimit(idEffectedDate, idExpiredDate) {

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
		todayHighlight : true,
		autoclose : true
	}).on(
			'changeDate',
			function(selected) {
				startDate = new Date(selected.date.valueOf());
				startDate.setDate(startDate.getDate(new Date(selected.date
						.valueOf())));
				$('.datepicker-to').datepickerUnit('setStartDate', startDate);
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
		todayHighlight : true,
		autoclose : true
	}).on(
			'changeDate',
			function(selected) {
				FromEndDate = new Date(selected.date.valueOf());
				FromEndDate.setDate(FromEndDate.getDate(new Date(selected.date
						.valueOf())));
				$('.datepicker-from').datepickerUnit('setEndDate', FromEndDate);
			});
}
*/
function changeDatepicker(idEffectedDate, idExpiredDate){
	if(idEffectedDate === undefined || idEffectedDate === null || idEffectedDate === '') {
		$("#startDate").datepickerUnit({format: DATE_FORMAT}).on('changeDate', function (selected) {
	        var minDate = new Date(selected.date.valueOf());
	        $('#endDate').datepickerUnit({format: DATE_FORMAT}).datepickerUnit('setStartDate', minDate);
	        //initTime();
	    }).on('clearDate', function() {
			$('#endDate').datepickerUnit('setStartDate', null);
			//initTime();
		}).datepickerUnit('setDate', 'today');
	} else {
		$("#startDate").datepickerUnit({format: DATE_FORMAT}).on('changeDate', function (selected) {
	        var minDate = new Date(selected.date.valueOf());
	        $('#endDate').datepickerUnit({format: DATE_FORMAT}).datepickerUnit('setStartDate', minDate);
	        //initTime();
	    }).on('clearDate', function() {
			$('#endDate').datepickerUnit('setStartDate', null);
			//initTime();
		}).datepickerUnit('setDate', idEffectedDate);
	}

    $("#endDate").datepickerUnit({format: DATE_FORMAT}).on('changeDate', function (selected) {
    	var maxDate = new Date(selected.date.valueOf());
      	$('#startDate').datepickerUnit('setEndDate', maxDate);
      	//initTime();
    }).on('clearDate', function() {
    	$('#startDate').datepickerUnit('setEndDate', null);
    	//initTime();
	});
}

/*function initTime(){
	var todate = new Date();
	var minDate = $('#startDate').datepickerUnit('getDate');
	if(null == minDate){
		minDate = todate;
	}
	minDate.setHours(0,0,0,0);
	var maxDate = $('#endDate').datepickerUnit('getDate');
	if(null == maxDate){
		maxDate = todate;
	}
	maxDate.setHours(0,0,0,0);
	if(minDate.getTime() === maxDate.getTime()){
		maxDate.setHours(23,59,59,0);
		var startDate = $("#time1").data("DateTimePicker").date();
	    $('#time1').data("DateTimePicker").minDate(minDate);
	    $('#time1').data("DateTimePicker").maxDate(maxDate);
	    if(startDate != null){
	    	$('#time2').data("DateTimePicker").minDate(startDate);
	    }
	    $('#time2').data("DateTimePicker").maxDate(maxDate);
	}else{
		$('#time2').data("DateTimePicker").minDate(minDate);
	}
}*/

