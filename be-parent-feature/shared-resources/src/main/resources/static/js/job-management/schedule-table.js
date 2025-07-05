$(document).ready(function() {
	$("#tableList").datatables({
		url : BASE_URL + 'quartz/schedule/ajaxList',
		type : 'POST',
		setData : setConditionSearch,
	});

	$('.j-btn-edit').click(function(e) {
        e.preventDefault();
		updateSchedule(this, e);
	});

	$('.j-btn-delete').click(function(e) {
        e.preventDefault();
		deleteSchedule(this, e);
	});

});

function updateSchedule(element, event) {
	event.preventDefault();
	var row = $(element).parents("tr");
	var id = row.data("schedule-id") === undefined ? row.prevObject.attr('data-schedule-id') : row.data("schedule-id");
	if(id === undefined) {
		id = "0";
	}
	var url = BASE_URL + 'quartz/schedule/upsert?id=' + id;
	ajaxRedirect(url);
}

function deleteSchedule(element, event) {
	event.preventDefault();
	var row = $(element).parents("tr");
	var id = row.data("schedule-id");
	popupConfirm(MSG_DEL_CONFIRM, function(result) {
        if (result) {
        	var url = "quartz/schedule/delete";
			var condition = {
				"id" : id
			}
			ajaxSubmit(url, condition, event);
        }
    });
}

function setTableConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}