$(document).ready(function() {
	$("#btnCreate").on('click', function(event) {
        event.preventDefault();
		createJobSchedule(event);
	});
})

function createJobSchedule(event) {
	event.preventDefault();
	var url = BASE_URL + "quartz/schedule/upsert";
	// Redirect to page add
	ajaxRedirect(url);
}
