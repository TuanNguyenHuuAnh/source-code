$(document).ready(function() {
	$(".widgetApprovalHiddenApprover").toggle("fast", "swing");
	$('.widgetApprovalToggleLog').toggle();
	$('.button-approval-module').on('click',function(e){
		e.preventDefault();
		
		if( !$("#alertErrorId").hasClass("hidden") ) {
			$("#alertErrorId").addClass("hidden");
		}

		$('#widgetApprovalAction').val($(this).attr('id'));
		$('#widgetApprovalStepId').val($(this).val());
		$("#abtract-business-code").val($("#approval-module-business-code-id").val());
		$("#commentsId").val($("#widgetApprovalComment").val());
		$("#abtract-step-id").val($("#widgetApprovalStepId").val());
		
		var isSave = $(this).data("is-save");
		blockbg();
		if( isSave ) {
			setTimeout(saveAndSubmitDataProcess, 50);
			return true;
		} else {
			setTimeout(submitDataProcess, 50);
			return true;
		}
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

});

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
});

$("#widgetApprovalShowApproverPopup").click(function() {
	$('#widgetApprovalApproverPopupDiv').modal('show');
});

function saveAndSubmitDataProcess() {
	return new Promise(function(resolve, reject) {
		validDocForm()
			.then(checkValidateLimitNumberTransaction)
			.then(saveOzd)
			.then(uploadFileOzd)
			.then(getDataOzFormDoc)
			.then(updateDoc)
			.then(getDataProcess)
			.then(ajaxDoActionProcess)
			.then(function() {
				//console.log("Process complete");
            	return resolve('Process complete');
            })
			.catch(function(error) {
				//console.log("Error::");
				unblockbg();
				reject("Error");
            });
     });
}

function submitDataProcess() {
	return new Promise(function(resolve, reject) {
		getDataOzFormDoc($("#ozdFileName").val())
			.then(getDataProcess)
			.then(ajaxDoActionProcess)
			.then(function() {
				//console.log("Process complete");
            	return resolve('Process complete');
            })
			.catch(function(error) {
				//console.log("Error::");
				unblockbg();
                reject("Error");
            });
     });
}

function getDataProcess() {
	//console.log("getDataProcess");
	
	return new Promise(function(resolve, reject) {
		formDoc["action"] = $('#widgetApprovalAction').val();
		formDoc["stepId"] = $("#widgetApprovalStepId").val();
		formDoc["businessCode"] = $("#approval-module-business-code-id").val();
		formDoc["referenceId"] = $("#ozDocId").val();
		
		resolve("true");
	});
}

function ajaxDoActionProcess() {
	//console.log(formDoc);
	
	return new Promise(function(resolve, reject) {
		var token = $("meta[name='_csrf']").attr("content");
	  	var header = $("meta[name='_csrf_header']").attr("content");
		var url = $("#approval-module-business-url").val()+'/do-action-process';

		formDoc['_csrf'] = token;
		formDoc['_csrf_header'] = header;
		
		$.ajax({
			type : "POST",
			url : BASE_URL + url,
			global: false,
			data : formDoc,
			beforeSend: function(jqXHR, settings ) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					xhr.setRequestHeader(header, token);
				});
			},
			complete : function(result) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					blockbg();
					xhr.setRequestHeader(header, token);
				});
				unblockbg();
			},
			error : function(xhr, textStatus, error) {
				$(document).unbind("ajaxSend").bind("ajaxSend", function(e, xhr, options) {
					blockbg();
					xhr.setRequestHeader(header, token);
				});
				
				if( $("#alertErrorId").hasClass("hidden") ) {
					$("#alertErrorId").removeClass("hidden");
				}
				goTopPage();
				reject("Error: ajaxDoActionProcess");
			},
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
				
				goTopPage();
				resolve("true");
			}
		});
	});
}