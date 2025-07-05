$(document).ready(function() {
	$('#clearButton').on('click', function(event) {
		   $('#endTimeMorning').val('');
	       $('#startTimeMorning').val('');
	       $('#startTimeEvening').val('');
	       $('#endTimeEvening').val('');
	})
	$('#submitHoliday').on('click', function(event) {
		event.preventDefault();
		saveHoliday(event);
	});
	$('#add').on('click', function() {
		$('#modalContent').summernote('codeview.deactivate');
		$('modalContent').summernote('code', '');
		$('#fileNameModal').html('');
		$('#nameFileErrorModal').html('');
		$('#codeError').html('');
		callModalNew();
	})
	
});

function callModalNew() {
	//set new 
	//setEdit();
	$('#companyIdDetail').val($('#companyId').val());
	$('#modalContent').summernote('code', '');
	$('#nameFileModal').val('');
	$('#templateId').val('');
	$('#subjectModal').val('');
	$('#detailModal').modal('show');
	$('#detailModal').on('shown.bs.modal', function() {
		$('#nameFileModal').focus();
	})
	$('#detailModal').css('z-index', 5000);
	$('#action').css('display', '');
	$('#mobileNotification').val('');
	$('#code').val('');
}

function saveHoliday(event) {
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
		$('#submitHoliday').attr("data-dismiss",'modal')
		event.preventDefault();
		var url = 'holidays/save';
		var condition = $('#frmHoliday').serialize();
		ajaxSubmit(url, condition, event);
	}else{
			$('#submitHoliday').removeAttr("data-dismiss")
	
	}
}
