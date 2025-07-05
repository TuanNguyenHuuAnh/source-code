var element;
$(document).ready(function() {
	
	$('.js-example-basic-multiple').select2({
		minimumResultsForSearch : 0
	});
	
	$("#linkList").on("click", function(event) {

		var id = $("#jpmSlaInfoId").val();
		var url = BASE_URL + "sla/edit?id=" + id;
		ajaxRedirect(url);
	});

	$('#btn-save, #btnSaveHead').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var valid = true;
			var sla = parseFloat($("#workTime").val());
			$("input.reminder-alertTime[type=number]").each(function(index) {
				var alert = parseFloat($(this).val());
				var subType = $(this).closest(".reminder-row").find("select.reminder-unitTime").val();
				var val = $('#timeType').val();
				var value = 0;
				if(val === '2' || val === '4') {
					if(subType === '1') {
						value = 24 * sla;
					} else if(subType === '0') {
						value = 24 * 60 * sla;
					} else {
						value = sla;
					}
				} else if(val === '1' || val === '3') {
					if(subType === '0') {
						value = 60 * sla;
					} else if(subType === '1') {
						value = sla;
					}
				} else if(val === undefined || val === '') {
					value = alert;
				} else {
					value = sla;
				}
				if (alert > value) {
					$("#reminder-alertTime" + index).tooltip('show');
					valid = false;
				}
			});

			if (valid == true) {
				var values = $("#form-j-edit").serialize();

				var id = $("#slaStepId").val();
				var url = "sla/alert-setting?slaStepId=" + id;
				ajaxSubmit(url, values, event);
			}
		}
	});
	
	initActive($("#updatedBy").val());

	$("#btn-cancel").on("click", function(event) {
		var id = $("#jpmSlaInfoId").val();
		var url = BASE_URL + "sla/edit?id=" + id;
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	
	$('#timeType').on('change', function(event) {
		var val = $(this).val();
		$("select.reminder-unitTime").each(function(index) {
			if(val === '2' || val === '4') {
				 $(this).val("2").trigger("change");
			} else if(val === '1' || val === '3') {
				$(this).val("1").trigger("change");
			}else if(val === '5' || val === '6') {
				$(this).val("0").trigger("change");
			} else {
				$(this).val("").trigger("change");
			}
		});	
		$("select.escalate-unitTime").each(function(index) {
			if(val === '2' || val === '4') {
				 $(this).val("2").trigger("change");
				//$(this).val(course)
			} else if(val === '1' || val === '3') {
				$(this).val("1").trigger("change");
			}else if(val === '5' || val === '6') {
				$(this).val("0").trigger("change");
			} else {
				$(this).val("").trigger("change");
			}
		});
    });
	
	/*$('#reminderList select.reminder-unitTime').on('change', function(event) {
		var time = $(this).val();
		var val = $('#timeType').val();
		if(val === '1' || val === '3') {
			if(time === '431') {
				$(this).val("426").trigger("change");
			}
		} else if(val === '5' || val === '6') {
			if(time === '431' || time === '426') {
				$(this).val("421").trigger("change");
			}
		} else if(val === undefined || val === '') {
			$(this).val("").trigger("change");
		}
    });*/
	
	$("#addNotification").on("click", function(event) {
		blockbg();
		var values = $("#form-j-edit").serialize();
		var id = $("#slaStepId").val();
		$.ajax({
			type : "POST",
			url : BASE_URL + 'sla/add-notification?slaStepId=' + id,
			data : values,

			success : function(response) {
				$("#notificationList").html(response);
				initJSForAddNotification();
				element = $(response).find(".email-select2");				
				$(element).each(function (index, value) {
					initSelectForSetting("#" + $(this).attr('id'));
				});
				
				notiElement = $(response).find(".noti-select2");				
				$(notiElement).each(function (index, value) {
					initSelectNotiForSetting("#" + $(this).attr('id'));
				});
				
				var emailTo = $(response).find(".email-to-select2");	
				$(emailTo).each(function (index, value) {
					initSelectForEmailTo("#" + $(this).attr('id'));
				});
				var emailCC = $(response).find(".email-cc-select2");	
				$(emailCC).each(function (index, value) {
					initSelectForEmailCC("#" + $(this).attr('id'));
				});
				
				unblockbg();
			},
			error : function(e) {
				alert('Error: ' + e);
				unblockbg();
			}
		});
	});

	$("#addReminder").on("click", function(event) {
		blockbg();
		var values = $("#form-j-edit").serialize();
		var id = $("#slaStepId").val();
		$.ajax({
			type : "POST",
			url : BASE_URL + 'sla/add-reminder?slaStepId=' + id,
			data : values,

			success : function(response) {
				$("#reminderList").html(response);
				initJSForAddReminder();			
				element = $(response).find(".email-select2");				
				$(element).each(function (index, value) {
					initSelectForSetting("#" + $(this).attr('id'));
				});
				notiElement = $(response).find(".noti-select2");				
				$(notiElement).each(function (index, value) {
					initSelectNotiForSetting("#" + $(this).attr('id'));
				});
				
				var emailTo = $(response).find(".email-to-select2");	
				$(emailTo).each(function (index, value) {
					initSelectForEmailTo("#" + $(this).attr('id'));
				});
				var emailCC = $(response).find(".email-cc-select2");	
				$(emailCC).each(function (index, value) {
					initSelectForEmailCC("#" + $(this).attr('id'));
				});

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
		var values = $("#form-j-edit").serialize();
		var id = $("#slaStepId").val();
		$.ajax({
			type : "POST",
			url : BASE_URL + 'sla/add-escalate?slaStepId=' + id,
			data : values,

			success : function(response) {
				$("#escalateList").html(response);
				initJSForAddEscalate();				
				element = $(response).find(".email-select2");				
				$(element).each(function (index, value) {
					initSelectForSetting("#" + $(this).attr('id'));
				});
				
				notiElement = $(response).find(".noti-select2");				
				$(notiElement).each(function (index, value) {
					initSelectNotiForSetting("#" + $(this).attr('id'));
				});
				
				var emailTo = $(response).find(".email-to-select2");	
				$(emailTo).each(function (index, value) {
					initSelectForEmailTo("#" + $(this).attr('id'));
				});
				var emailCC = $(response).find(".email-cc-select2");	
				$(emailCC).each(function (index, value) {
					initSelectForEmailCC("#" + $(this).attr('id'));
				});

				unblockbg();
			},
			error : function(e) {
				alert('Error: ' + e);
				unblockbg();
			}
		});
	});
	
	$("#workTime").on("change", function(event) {
		var value = $(this).val();
		if(value === undefined || value === null || value === "") {
			$(this).addClass("j-required");
		} else {
			$(this).removeClass("j-required");
		}
	});
	
	$("#autoAction").on("click", function(e){
		initAction($(this));
	});
	initSelectForEmailCC(".email-cc-select2");
	initAction($("#autoAction"));
	initSelectForSetting(".email-select2");
	initSelectNotiForSetting(".noti-select2");
	initSelectForEmailTo(".email-to-select2");
	initJSForAddNotification();
	initJSForAddReminder();	
	initJSForAddEscalate();	
	initReminderAndEscalate();
	initMesNotification();
	initMesReminder();
	initMesEscalate();
});

function initJSForAddNotification() {
	$(".remove-notification").on('click', function() {
		var $trParent = $(this).closest('div.notification-row');
		$trParent.remove();
		initMesNotification();
	});
	initMesNotification();
}

function initJSForAddReminder() {
	$(".remove-reminder").on('click', function() {
		var $trParent = $(this).closest('div.reminder-row');	
		$trParent.remove();
		initMesReminder();
	});
	initMesReminder();
}

function initJSForAddEscalate() {
	$(".remove-escalate").on('click', function() {
		var $trParent = $(this).closest('div.escalate-row');
		$trParent.remove();
		initMesEscalate();
	});
	initMesEscalate();
}

function initMesNotification() {
	var record = $("#notificationList").find('.notification-row');
	if(record.length === '0' || record.length === 0) {
		$("#notification-hidden").show();
	} else {
		$("#notification-hidden").hide();
	}
}

function initMesReminder() {
	var record = $("#reminderList").find('.reminder-row');
	if(record.length === '0' || record.length === 0) {
		$("#reminder-hidden").show();
	} else {
		$("#reminder-hidden").hide();
	}
}

function initMesEscalate() {
	var record = $("#escalateList").find('.escalate-row');
	if(record.length === '0' || record.length === 0) {
		$("#escalate-hidden").show();
	} else {
		$("#escalate-hidden").hide();
	}
}

function initSelectForSetting(element) {
	searchCombobox(element, '', 'sla/ajax/getEmailTemplateByCompanyId',
    	    function data(params) {
    	        var obj = {
    	        	key: params.term, 
    	            slaId: $("#jpmSlaInfoId").val(),
    	            isPaging: true
    	            
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function initSelectNotiForSetting(element) {
	//debugger;
	searchCombobox('.noti-select2', '', 'sla/ajax/get-noti-template-by-companyid',
    	    function data(params) {
    	        var obj = {
    	        	key: params.term, 
    	            slaId: $("#jpmSlaInfoId").val(),
    	            isPaging: true
    	            
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

function initAction(element) {
	var val = element.is(":checked");
	if(val) {
		$('#action').prop('disabled', '');
		$('#action').addClass("j-required");
		$('#actionLabel').addClass("required");
	} else {
		$('#action').val('').trigger('change');
		$('#action').removeClass("j-required");
		$('#actionLabel').removeClass("required");
		$('#action').prop('disabled', 'disabled');
	}
}

function initReminderAndEscalate() {
	$("select.reminder-unitTime").each(function(index) {
		var element = $(this).closest('div').find('input:hidden');
		var val = element.val();
		$(this).val(val).trigger("change");
	});	
	$("select.escalate-unitTime").each(function(index) {
		var element = $(this).closest('div').find('input:hidden');
		var val = element.val();
		$(this).val(val).trigger("change");
	});
}

function initActive(value) {
	if(value === undefined || value === null || value === '') {
		$('#invalidCheck').prop('checked', true);
	}
}

function initSelectForEmailTo(element) {
	searchCombobox(element, '', 'sla/ajax/getEmailToListBycompanyId',
    	    function data(params) {
    	        var obj = {
    	        	key: params.term, 
    	        	busCode : $('#businessCode').val(), 
    	            isPaging: true
    	            
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, false);
}

function initSelectForEmailCC(element) {
	searchCombobox(element, '', 'sla/ajax/getEmailCCListBycompanyId',
    	    function data(params) {
    	        var obj = {
    	        	key: params.term, 
    	        	busCode : $('#businessCode').val(), 
    	            isPaging: true
    	            
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, false);
}
