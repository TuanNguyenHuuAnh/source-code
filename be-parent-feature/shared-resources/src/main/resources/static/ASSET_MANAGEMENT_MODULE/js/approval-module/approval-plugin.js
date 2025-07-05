$(document).ready(function() {
	//phatvt: update comment Reject and Return
	hightLightComment();
	$(".widgetApprovalHiddenApprover").toggle("fast", "swing");
	$('.widgetApprovalToggleLog').toggle();
	$('.button-approval-module').unbind('click').on('click',function(e){
		e.preventDefault();
		/*if($("#enableHiddenBeforeSend").val() != null  && $("#enableHiddenBeforeSend").val() != "")
			$('input:disabled, select:disabled, textarea:disabled, checkbox:disabled').removeAttr('disabled');*/
		var functionName = $("#funtion-validate-id").val();
		var flag = true;
		if(functionName){
			flag = window[functionName](this);
		}
		
		var functionCallBack = $("#funtion-call-back").val();
		if(flag) {
			approvalModule(this, functionCallBack);
		}/* else {
			goTopPage();
		}*/
	});
	
	$('#widgetApprovalOnwerId').autocomplete({
        serviceUrl: BASE_URL +'approval-module/find-account',
        paramName: 'inputQuery',
        delimiter: ",",
        onSelect: function(suggestion) {

            $("#ownerUsernameId").val(suggestion.id);
            $("#ownerId").val(suggestion.id);
            $("#label-owner-department-name").val(suggestion.departmentName);
            $("#label-owner-branch-name").val(suggestion.branchName);
           
            return false;
        },
        transformResult: function(response) {
            return {
                suggestions: $.map($.parseJSON(response), function(item) {
                    return {
                        value: item.fullname + ' - '+ item.email,
                        id : item.username,
                        departmentName : item.departmentName,
                        branchName : item.branchName
                    };
                })
            };
        }
    });
	$('#widgetApprovalAssigneeId').autocomplete({
        serviceUrl: BASE_URL +'approval-module/find-account',
        paramName: 'inputQuery',
        delimiter: ",",
        onSelect: function(suggestion) {

            $("#assigneeUsernameId").val(suggestion.id);
            $("#assigneeId").val(suggestion.id);
            $("#label-assignee-department-name").val(suggestion.departmentName);
            $("#label-assignee-branch-name").val(suggestion.branchName);
           
            return false;
        },
        transformResult: function(response) {
            return {
                suggestions: $.map($.parseJSON(response), function(item) {
                    return {
                        value: item.fullname + ' - '+ item.email,
                        id : item.username,
                        departmentName : item.departmentName,
                        branchName : item.branchName
                    };
                })
            };
        }
    });
	
	//chặn enter cho button save và submit
	$('#button-approval-module-save-draft-id, #button-approval-module-submit-id').on('keydown', function (e) {
	    if (e.keyCode == 13) {
	        return false;
	    }
	});
	
});

function checkComment(obj) {
	var classList = document.getElementById($(obj).attr('id')).className.split(/\s+/);
	
	if((classList.indexOf('j-comment') > -1) && !$("#widgetApprovalComment").val().trim()){
		$("#widgetApprovalCommentRequired").html('<span class="help-block error">Comments is required for reason Reject/Return/Cancel</span>');
		return false;
	}
	return true;
}

function updateNotiToDo(){
	//console.log(' debug update  TODO List');
	countAllTask();
}

function approvalModule(obj, functionCallBack) {
	var msg = "";
	var buttonText = obj.textContent;
	msg = MSG_CONFIRM_CONTENT + buttonText + ' ?';
	if (!checkComment(obj)) {
		return;
	}
	popupConfirm(msg, function(result) {
		if (result) {
			if($("#enableHiddenBeforeSend").val() != null  && $("#enableHiddenBeforeSend").val() != "") {
				$('input:disabled, select:disabled, textarea:disabled, checkbox:disabled').removeAttr('disabled');
			}
			$('#widgetApprovalAction').val($(obj).attr('id'));
			$('#widgetApprovalStepId').val($(obj).val());
			$("#abtract-business-code").val($("#approval-module-business-code-id").val());
			$("#commentsId").val($("#widgetApprovalComment").val());
			$("#abtract-step-id").val($("#widgetApprovalStepId").val());
			$.ajax({
				url : BASE_URL + $("#approval-module-business-url").val()+'/do-action-process',
				cache : true,
				type : "POST",
				async : true,
				data: $("#"+$("#approval-module-form-id").val()).serialize()+"&referenceId="+$("#abtract-reference-id").val(), 
				success : function(data, textStatus, request) {
					var msgFlag = request.getResponseHeader('msgFlag');
					if( "1" == msgFlag ) {
//						$("#message").html(data);
						$(".message-info").html(data);
						if (functionCallBack) {
							window[functionCallBack](obj);
						}
						
					} else {
						var content = $(data).find('.body-content');
						$(".main_content").html(content);
					}
					
					var urlPage = $(data).find('#url').val();
					if (urlPage != null && urlPage != '') {
						window.history.pushState('', '', BASE_URL + urlPage);
					}
					goTopPage();
					updateNotiToDo();
					unblockbg();
				},
			});
		} 
	});
}

var ajaxUtilForWidgetApproval = {};
ajaxUtilForWidgetApproval.objMaskAjax = $("#ebody");
ajaxUtilForWidgetApproval.isSuccess = function(resp) {
	if (resp && resp.status == "success") {
		return true;
	} else {
		return false;
	}
}

ajaxUtilForWidgetApproval.showMsg = function(resp) {
	console.log(resp.message);
	if (resp && resp.status) {

		$(".messages:first")
				.append(
						'<div class="alert alert-'
								+ resp.status
								+ ' fade in">'
								+ '<button class="close" data-dismiss="alert" type="button">×</button> '
								+ resp.message + '</div>');
	}
}
function showErrMsg(thisMsg) {
	$(".messages:first")
			.append(
					'<div class="alert alert-error fade in">'
							+ '<button class="close" data-dismiss="alert" type="button">×</button> '
							+ thisMsg + '</div>');
}
function showOkMsg(thisMsg) {
	$(".messages:first")
			.append(
					'<div class="alert alert-success fade in">'
							+ '<button class="close" data-dismiss="alert" type="button">×</button> '
							+ thisMsg + '</div>');
}


$("#widgetApprovalViewAllApprover").click(function() {
	$(".widgetApprovalHiddenApprover").toggle("fast", "swing");
	$('.widgetApprovalToggleLog').toggle();
	hightLightComment();
});

$("#widgetApprovalShowApproverPopup").click(function() {
	$('#widgetApprovalApproverPopupDiv').modal('show');
});

function gotop() {
	window.scrollTo(0, 0);
}
function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function hightLightComment(){
	$(".processStatusName").each(function(){
		let processStatus = $(this).text();
		if(processStatus === 'Rejected' || processStatus.indexOf('Return') !== -1 ||  processStatus === 'Cancelled'){
			let $element = $($(this).closest('h5').parent());
			let elementComment = $element.find('.comment-process');
			$(elementComment).attr('style','font-weight: bolder;font-style: italic');
			let comment = elementComment.attr('value');
			let commentValue = '"' + comment + '"';
			$(elementComment).text(commentValue);
		}
	});
}
