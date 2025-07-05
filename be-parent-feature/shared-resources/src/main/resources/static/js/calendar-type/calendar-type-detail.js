$(document).ready(function () {
    // Back
    $('#btnBack, #btnCancel').click(function () {
        back();
    });

    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

    // Save
    $('#btnSaveHead, #btnSave').click(function () {
        save();
    });
    
    $('#startMorningTime').datetimepicker({
		format : "HH:mm"
	}).on("dp.change", function (e) {
		var date = $("#startMorningTime").data("DateTimePicker").date();
		$('#endMorningTime').data("DateTimePicker").minDate(date);
	});
    
    $('#endMorningTime').datetimepicker({
    	format : "HH:mm"
	});
	
    $('#startAfternoonTime').datetimepicker({
		format : "HH:mm"
	}).on("dp.change", function (e) {
		var date = $("#startAfternoonTime").data("DateTimePicker").date();
		$('#endAfternoonTime').data("DateTimePicker").minDate(date);
	});
    
    $('#endAfternoonTime').datetimepicker({
    	format : "HH:mm"
	});
    
    initWorkingTime();
});

function back() {
    var url = BASE_URL + "calendar-type/list";
    ajaxRedirect(url);
}

function create() {
    var url = BASE_URL + "calendar-type/add";
    ajaxRedirect(url);
}

function save() {
    if ($(".j-form-validate").valid()) {
        var values = $("#form-detail").serialize();
        var url = "calendar-type/save";
        ajaxSubmit(url, values, event);
    }
}

function initWorkingTime(){
	var minDate = new Date(); minDate.setHours(0,0,0,0);
    var maxDate = new Date(); maxDate.setHours(12,0,0,0);
    var startDate = $("#startMorningTime").data("DateTimePicker").date();
    $('#startMorningTime').data("DateTimePicker").minDate(minDate);
    $('#startMorningTime').data("DateTimePicker").maxDate(maxDate);
    if(startDate != null){
    	$('#endMorningTime').data("DateTimePicker").minDate(startDate);
    }
    $('#endMorningTime').data("DateTimePicker").maxDate(maxDate);

	var minDate1 = new Date(); minDate1.setHours(12,0,0,0);
    var maxDate1 = new Date(); maxDate1.setHours(23,59,59,0);
    var startDate1 = $("#startAfternoonTime").data("DateTimePicker").date();
    $('#startAfternoonTime').data("DateTimePicker").minDate(minDate1);
    $('#startAfternoonTime').data("DateTimePicker").maxDate(maxDate1);
    if(startDate1 != null){
    	$('#endAfternoonTime').data("DateTimePicker").minDate(startDate1);
    }
    $('#endAfternoonTime').data("DateTimePicker").maxDate(maxDate1);
}
