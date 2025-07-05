$(document).ready(function() {
	$("#btnCreate").on('click', function(event) {
        event.preventDefault();
		createJobSchedule(event);
	});
})

function createJobSchedule(event) {
	event.preventDefault();
	var url = BASE_URL + "quartz/job-schedule/upsert";
	// Redirect to page add
	ajaxRedirect(url);
}

function setConditionSearch() {
	var condition = {};
	condition["startTime"] = $('[name=startTime]').val();
	condition["endTime"] = $('[name=endTime]').val();
	condition["status"] = $('#statusList').val().toString();
	condition["schedId"] = $('[name=schedId]').val();
	condition["jobId"] = $('[name=jobId]').val();
	condition["companyId"] = $("#companyId").val();
	//condition["companyIdList"] = $("#companyIdList").val();
	return condition;
}
