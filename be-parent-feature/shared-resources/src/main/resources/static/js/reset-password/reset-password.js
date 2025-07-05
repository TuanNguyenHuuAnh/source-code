var PAGE_URL = '/reset-password';
var CHANGE_PASSWORD = '/ajax-change-password';
var URL = 'account';

$(document).ready(function($) {

	init()

	$('#btnSave').unbind('click').bind('click', function() {
		$('.alert-dismissible').hide();
		if ($(".j-form-validate").valid()) {
			$('#message').val('');
			checkAgent(event);
		} else {
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
			rollToError('j-form-validate');
		}
	});




});

function init() {
	if ($('#agent').val() == '') {
		$('#radio1').prop('checked', true);
		$('#radio4').prop('checked', true);
	}
	$('#agentMess').hide();
	$('#agentExitMess').hide();
	$('#passwordGadMess').hide();
	$('#isGadMess').hide();

}

function checkAgent(event) {
	event.preventDefault();
	var url = URL + PAGE_URL;
	var condition = $(".j-form-validate").serialize();
	$.ajax({
		type: "POST",
		url: BASE_URL + url,
		data: condition,
		success: function(data, textStatus, request) {
			var IS_ERROR = data.isError;
			$('#result').val(data.result);

			if (IS_ERROR != 'true') {
				submit(event);
			}
			if (data.result == 'agent') {
				$('#agentMess').show();
			} else if (data.result == 'agentExit') {
				$('#agentExitMess').show();
			} else if (data.result == 'passwordGad') {
				$('#passwordGadMess').show();
			}else if (data.result == 'isGad') {
				$('#isGadMess').show();
			}
			// SR16656 @author lmi.quan
			//         @createdDate 2/7/2024
			//         Lỗi 2
			else if (data.result == 'agentAD'){
				popupAlert(RESET_PASSWORD_NOT_EXIST, function(result) {

					});
		    }
		    // SR16656 @author lmi.quan
			//         @createdDate 2/7/2024
			//         Lỗi 2
			else {
				$('#agentMess').hide();
				$('#agentExitMess').hide();
				$('#passwordGadMess').hide();
				$('#isGadMess').hide();

			}




		},

		error: function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});

}


function submit(event) {
	event.preventDefault();
	var agentTile = $('#agent').val();
	var TITLE = WANT_SAVE.replace('{0}', agentTile);

	if (result) {
		var url = URL + PAGE_URL + CHANGE_PASSWORD;
		var condition = $(".j-form-validate").serialize();

		$.ajax({
			type: "POST",
			url: BASE_URL + url,
			data: condition,
			success: function(data, textStatus, request) {
				var msgFlag = request.getResponseHeader('msgFlag');

				if ("1" == msgFlag) {
					$("#message").html(data);
				} else {
					var content = $(data).find('.body-content');
					$(".main_content").html(content);
				}

				var urlPage = $(data).find('#url').val();
				if (urlPage != null && urlPage != '') {
					window.history.pushState('', '', BASE_URL + urlPage);
				}

				if ($('#message').val().length > 0) {
					var addAgent = RESET_PASSWORD_SUCCESS.replace('{agentCode}', AGENT);
					var addSend = addAgent.replace('{send}', IS_SEND);
					popupAlert(addSend, function(result) {

					});
				}
			},
			error: function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});
	}
}


