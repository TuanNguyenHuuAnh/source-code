$(document).ready(function() {
	$(' #saveCalendar').click(function() {
		save();
	});
	
	$('.datepicker').datepickerUnit({
		format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
		language : APP_LOCALE.toLowerCase(),
        keyboardNavigation : true
	});
	// set validate after search
	var idEffectedDate = $('#fromDate').val();
	var idExpiredDate = $('#toDate').val();
	if(idEffectedDate != ''){
		$('.datepicker-to-date').datepickerUnit('setStartDate', idEffectedDate);
	}
	if(idExpiredDate != ''){
		$('.datepicker-from-date').datepickerUnit('setEndDate', idExpiredDate);
	}
	
	$('.datepicker-from-date').on('change', function (selected) {
		$('#fromDate').valid();
    });

  $('.datepicker-to-date').on('change', function (selected) {
    	$('#toDate').valid();
    });
	
});

function clearComboboxAdd(element) {
	//$(element).find('option').remove().end();
}
/*
$(window).on('load', function () {
	loadTypeCalendar();
});
*/
function loadTypeCalendarAdd() {
	clearComboboxAdd('#calendarTypeId');
	var companyId = $('#companyAddId').val();
	var data = {
		"companyId": companyId
	}
	blockbg();
	$.ajax({
		url: BASE_URL + 'holidays/getListTypeCalendar',
		data: data,
		type: 'post',
		success: function(data) {
			unblockbg();
			// $('#calendarTypeId').select2({ data });
            let html = '';
            if (data != null && data.length > 0){
                for (let i=0;i<data.length;i++){
                    html = html + '<option value=' + data[i].id + '>' + data[i].text + '</option>';
                }
            }
            $('#calendarTypeId').html(html);
		}
	});
};
function save() {
	var startTimeMorning = $('#startTimeMorning').val();
	var endTimeMorning = $('#endTimeMorning').val();
	var startTimeEvening = $('#startTimeEvening').val();
	var endTimeEvening = $('#endTimeEvening').val();
	if (startTimeMorning.length > 0 && endTimeMorning.length <= 0) {
		$("#endTimeMorning").addClass('j-required');
	}else if(endTimeMorning.length > 0 && startTimeMorning.length <= 0) {
		$("#startTimeMorning").addClass('j-required');
	}else{
		$("#startTimeMorning").removeClass('j-required');
		$("#endTimeMorning").removeClass('j-required');
		
	}
	if (startTimeEvening.length > 0 && endTimeEvening.length <= 0) {
		$("#endTimeEvening").addClass('j-required');
	}else if (endTimeEvening.length > 0 && startTimeEvening.length <= 0) {
		$("#startTimeEvening").addClass('j-required');
	}else{
		$("#endTimeEvening").removeClass('j-required');
		$("#startTimeEvening").removeClass('j-required');
		
	}
	
	if ($(".j-form-validate").valid()) {
		var values = $("#newsForm").serialize();

		var url = "holidays/save";
		ajaxSubmit(url, values, event);
	}
}