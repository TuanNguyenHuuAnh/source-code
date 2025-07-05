$(document).ready(function() {
	$("#tableList").datatables({
		url : BASE_URL + 'quartz/job-schedule/ajaxList',
		type : 'POST',
		setData : setConditionSearch,
		"scrollX" : true,
		"sScrollXInner" : "100%",
		"autoWidth" : true
	});

	$('.btn-run').on("click", function(e) {
		e.preventDefault();
		runJob(this, e);
	});

	$('.btn-update').on("click", function(e) {
		e.preventDefault();
		updateJob(this, e);
	});

	$('.btn-resume').on("click", function(e) {
		e.preventDefault();
		resumeJob(this, e);
	});

	$('.btn-pause').on("click", function(e) {
		e.preventDefault();
		pauseJob(this, e);
	});

	$('.btn-stop').on("click", function(e) {
		e.preventDefault();
		deleteJob(this, e);
	});

});

function runJob(element, event) {
	popupConfirm(MSG_QRTZ_RUN_CONFIRM, function(result) {
		$('#status').val($('#statusList').val());
		if (result) {
			var row = $(element).parents("tr");
			var id = row.data("jobschedule-id") === undefined ? row.prevObject.attr('data-jobschedule-id') : row.data("jobschedule-id");
			var url = "quartz/job-schedule/run";
			var condition = {
				"id" : id
			}
			ajaxSubmit(url, condition, event);
			/*$.ajax({
				type : "POST",
				url : BASE_URL + 'quartz/job-schedule/run?id=' + id,
				success : function(response) {
					$("#tableList").html(response);
					unblockbg();
				},
				error : function(e) {
					alert('Error: ' + e);
					unblockbg();
				}
			});*/
		}
    });
}

function updateJob(element, event) {
	var row = $(element).parents("tr");
	var id = row.data("jobschedule-id") === undefined ? row.prevObject.attr('data-jobschedule-id') : row.data("jobschedule-id");
	var url = BASE_URL + 'quartz/job-schedule/upsert?id=' + id;
	ajaxRedirect(url);
}

function resumeJob(element, event) {
	$('#status').val($('#statusList').val());
	popupConfirm(MSG_QRTZ_RUN_CONFIRM, function(result) {
		$('#status').val($('#statusList').val());
		if (result) {
			var row = $(element).parents("tr");
			var id = row.data("jobschedule-id") === undefined ? row.prevObject.attr('data-jobschedule-id') : row.data("jobschedule-id");
			var url = "quartz/job-schedule/resume";
			var condition = {
				"id" : id
			}
			ajaxSubmit(url, condition, event);
			
			/*$.ajax({
				type : "POST",
				url : BASE_URL + 'quartz/job-schedule/resume?id=' + id,
				success : function(response) {
					$("#tableList").html(response);
					unblockbg();
				},
				error : function(e) {
					alert('Error: ' + e);
					unblockbg();
				}
			});*/
		}
    });
}

function pauseJob(element, event) {
	$('#status').val($('#statusList').val());
	popupConfirm(MSG_QRTZ_STOP_CONFIRM, function(result) {
		$('#status').val($('#statusList').val());
		if (result) {
			var row = $(element).parents("tr");
			var id = row.data("jobschedule-id") === undefined ? row.prevObject.attr('data-jobschedule-id') : row.data("jobschedule-id");
			var url = "quartz/job-schedule/pause";
			var condition = {
				"id" : id
			}
			ajaxSubmit(url, condition, event);
			
			/*$.ajax({
				type : "POST",
				url : BASE_URL + 'quartz/job-schedule/pause?id=' + id,
				success : function(response) {
					$("#tableList").html(response);
					unblockbg();
				},
				error : function(e) {
					alert('Error: ' + e);
					unblockbg();
				}
			});*/
		}
    });
}

function deleteJob(element, event) {
	popupConfirm(MSG_QRTZ_DEL_CONFIRM, function(result) {
		$('#status').val($('#statusList').val());
		if (result) {
			var row = $(element).parents("tr");
			var id = row.data("jobschedule-id") === undefined ? row.prevObject.attr('data-jobschedule-id') : row.data("jobschedule-id");
			var url = "quartz/job-schedule/delete";
			var condition = {
				"id" : id
			}
			
			// ajaxSubmit(url, condition, event);
			/*$.ajax({
				type : "POST",
				url : BASE_URL + 'quartz/job-schedule/delete?id=' + id,
				success : function(response) {
					$("#tableList").html(response);
					unblockbg();
				},
				error : function(e) {
					alert('Error: ' + e);
					unblockbg();
				}
			});*/
			
			
			// ajaxSubmit(url, condition, event);
			blockbg();
			event.preventDefault();
			$.ajax({
				type : "POST",
				url : BASE_URL + url,
				data : condition,
				success : function(data, textStatus, request) {
					unblockbg();
					var msgFlag = request.getResponseHeader('msgFlag');
					
					if( "1" == msgFlag ) {
						$("#message").html(data);
					} else {
						// var content = $(data).find('.body-content');
						// $(".main_content").html(content);
						
						
						var content = $(data).find('#tableList');
						// $("#tableList").replaceWith(content);

						var condition = setConditionSearch();
						console.log(condition);
						ajaxSearch("quartz/job-schedule/ajaxList", condition, "tableList", element, event);
					}
					
					
					var urlPage = $(data).find('#url').val();
					if (urlPage != null && urlPage != '') {
						window.history.pushState('', '', BASE_URL + urlPage);
					}
					
					goTopPage();

				},
				error : function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				}
			});
		}
    });
}


// common_unit.js
/*
function ajaxSubmit(url, condition, event, flagScrollTop) {
	event.preventDefault();
	
	if (flagScrollTop === undefined) {
		flagScrollTop = true;
	}

	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : condition,
		success : function(data, textStatus, request) {
			var msgFlag = request.getResponseHeader('msgFlag');
			
			if( "1" == msgFlag ) {
				$("#message").html(data);
			} else {
				var content = $(data).find('.body-content');
				$(".main_content").html(content);
			}
			
			var urlPage = $(data).find('#url').val();
			if (urlPage != null && urlPage != '') {
				window.history.pushState('', '', BASE_URL + urlPage);
			}
			
			if( flagScrollTop ) {
				goTopPage();
			}
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}
*/
