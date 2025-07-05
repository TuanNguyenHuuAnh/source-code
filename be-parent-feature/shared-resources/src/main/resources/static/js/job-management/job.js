$(document).ready(function() {
	$("#btnCreate").on('click', function(event) {
		createJobSchedule(event);
	});
})

function createJobSchedule(event) {
	event.preventDefault();
	var url = BASE_URL + "quartz/job/upsert";
	// Redirect to page add
	ajaxRedirect(url);
}

function deleteJob(element, event) {
	event.preventDefault();
	var row = $(element).parents("tr");
	var id = row.data("job-id");
	popupConfirm(MSG_DEL_CONFIRM, function(result) {
        if (result) {
        	var url = "quartz/job/delete";
			var condition = {
				"id" : id
			}
			ajaxSubmit(url, condition, event);
        }
    });
}
