$(document).ready(function() {

	$("#tableList").datatables({
		url : BASE_URL + 'quartz/job/ajaxList',
		type : 'POST',
		setData : setConditionSearch,
	});

	$('.j-btn-edit').click(function(e) {
		updateJob(this, event);
	});

	$('.j-btn-delete').click(function(e) {
		deleteJob(this, event);
	});

});

function updateJob(element, event) {
	event.preventDefault();
	var row = $(element).parents("tr");
	var id = row.data("job-id") === undefined ? row.prevObject.attr('data-job-id') : row.data("job-id");
	if(id === undefined) {
		id = "0";
	}
	var url = BASE_URL + 'quartz/job/upsert?id=' + id;
	ajaxRedirect(url);
}

function setTableConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}