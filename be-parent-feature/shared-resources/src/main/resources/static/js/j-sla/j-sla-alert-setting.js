$(document).ready(function() {
	initJSForAddReminder();
	initJSForAddEscalate();
	
	$("#linkList").on("click", function(event) {
		
		var id = $("#slaId").val();
		var url = BASE_URL + "jsla/edit?id=" + id;
		ajaxRedirect(url);
	});
	
	$('#btn-save').on('click', function(event) {
		var values = $("#form-j-edit").serialize();
		
		var id = $("#slaStepId").val();
		var url = "jsla/alert-setting?slaStepId=" + id;
		ajaxSubmit(url, values, event);
	});
	
	$("#btn-cancel").on("click", function(event) {
		var id = $("#slaId").val();
		var url = BASE_URL + "jsla/edit?id=" + id;
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$("#addReminder").on("click", function(event) {
		blockbg();
		$.ajax({
			type : "POST",
			url  : BASE_URL + 'jsla/add-reminder',
			data : $("#form-j-edit").serialize(),
			
			success : function(response) {
				$("#reminderList").html(response);
				
				initJSForAddReminder();
				
				unblockbg();
			},
			error : function(e) {
				alert('Error: ' + e);
				unblockbg();
			}
		});
	});
	
	$("#addEscalate").on("click", function(event) {
		blockbg();
		$.ajax({
			type : "POST",
			url  : BASE_URL + 'jsla/add-escalate',
			data : $("#form-j-edit").serialize(),
			
			success : function(response) {
				$("#escalateList").html(response);
				
				initJSForAddEscalate();
				
				unblockbg();
			},
			error : function(e) {
				alert('Error: ' + e);
				unblockbg();
			}
		});
	});
});

function initJSForAddReminder() {
	$(".remove-reminder").on('click', function() {
		var $trParent = $(this).closest('div.reminder-row');
		var index = $trParent.data("index");
		$("#deleteFlagR"+index).val(true);
		$trParent.hide();
	});
	
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
}

function initJSForAddEscalate() {
	$(".remove-escalate").on('click', function() {
		var $trParent = $(this).closest('div.escalate-row');
		var index = $trParent.data("index");
		$("#deleteFlagE"+index).val(true);
		$trParent.hide();
	});
	
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
}