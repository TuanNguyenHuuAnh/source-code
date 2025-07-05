$(document).ready(function () {
	
	$('.date').datepickerUnit({
		format: DATE_FORMAT,
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : APP_LOCALE.toLowerCase(),
		todayHighlight : true,
		onRender : function(date) {
		}
	});
	
	var filesUpload = {};
    var file_upload = $('#form-detail').fileupload({
        url: BASE_URL + "jpm-process/edit",
        type: 'POST',
        dataType: 'text/html;charset=UTF-8',
        formAcceptCharset: 'utf-8',
        limitMultiFileUploadSize: 1000000, // 10M
        limitMultiFileUploadSizeOverhead: 1000000, // 10M
        multipart: true,
        maxChunkSize: 10000000,
        sequentialUploads: true,
        singleFileUploads: false,
        add: function (e, data) {
        	//console.log(data.paramName);
            var imageConsoleEl = $(this).find('.alert-' + data.paramName);
            $(imageConsoleEl).hide();
            var acceptFileTypes = /([a-zA-Z0-9\s_\\.\-\(\):])+(.bpmn|.bpmn20.xml)$/i;
            if (data.originalFiles[0].name.length && !acceptFileTypes.test(data.originalFiles[0].name)) {
                $(imageConsoleEl).text(NOT_AN_ACCEPTED_FILE_TYPE + ' (*.bpmn; *.bpmn20.xml)');
                $(imageConsoleEl).show();
                return;
            }
            if (data.originalFiles[0]['size'] > 5000000) {
                $(imageConsoleEl).text(FILESIZE_IS_BIGGER + ' 5MB');
                $(imageConsoleEl).show();
                return;
            }
            
            $('#fileNameBpmn').val(data.files[0].name);

            filesUpload[e.delegatedEvent.target.name] = data.files[0];
        },
        done: function (e, data) {
        	console.log("done");
        	console.log(data.result);
        },
        fail: function (e, data) {
        	console.log("fail");
        	console.log(data.result);
        }
    }).on('fileuploadprocessalways', function (e, data) {
        var currentFile = data.files[data.index];
        if (data.files.error && currentFile.error) {
          // there was an error, do something about it
          console.log(currentFile.error);
        }
      });
	
    // Back
    $('#btnBack, #btn-cancel').click(function () {
        back();
    });

    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });
    
    // Save
    $('#btnSaveHead, #btnSave').click(function (event) {
    	if ($(".j-form-validate").valid()) {
	    	var dataForm = $("#form-detail").serializeArray();
	    	var filesList = [];
	    	$.each(filesUpload, function( key, value ) {
	    		dataForm.push({"name": key,"value": value});
	    		filesList.push(value);
	    	});

	    	if(filesList.length > 0){
	    		saveDataAndFile(dataForm);
	    	} else {
	    		saveData(dataForm,event);
	    	}
    	}
    });

    // ========== BEGIN INIT PARAM ==========
    // Button add
    $('#btn-add-param').click(function() {
        sendAjaxAndUpdatePartial("jpm-process/edit-param", "GET", {processId: $('#txtProcessId').val(),businessId: $('#txtBusinessId').val()}, null, "param-modal-content", setSubmitParamEvent);
    });

    // Reload data when close popup
    $('#param-modal').on('hidden.bs.modal', function() {
        loadParamTable("jpm-process/param","GET", {processId: $('#txtProcessId').val()});
    });
    loadParamTable("jpm-process/param","GET", {processId: $('#txtProcessId').val()});
    // ========== END INIT PARAM ==========

    // ========== BEGIN INIT STATUS ==========
    // Button add
    $('#btn-add-status').click(function() {
        sendAjaxAndUpdatePartial("jpm-process/edit-status", "GET", {processId: $('#txtProcessId').val()}, null, "status-modal-content", setSubmitStatusEvent);
    });

    // Reload data when close popup
    $('#status-modal').on('hidden.bs.modal', function() {
        loadStatusTable("jpm-process/status","GET", {processId: $('#txtProcessId').val()});
    });
    loadStatusTable("jpm-process/status","GET", {processId: $('#txtProcessId').val()});
    // ========== END INIT STATUS ==========

    // ========== BEGIN INIT FUNCTION ==========
    // Button add
    $('#btn-add-function').click(function() {
        sendAjaxAndUpdatePartial("jpm-process/edit-function", "GET", {processId: $('#txtProcessId').val()}, null, "function-modal-content", setSubmitFunctionEvent);
    });

    // Reload data when close popup
    $('#function-modal').on('hidden.bs.modal', function() {
        loadFunctionTable("jpm-process/function","GET", {processId: $('#txtProcessId').val()});
    });
    loadFunctionTable("jpm-process/function","GET", {processId: $('#txtProcessId').val()});
    // ========== END INIT FUNCTION ==========

    // ========== BEGIN INIT BUTTON ==========
    // Button add
    $('#btn-add-button').click(function() {
        sendAjaxAndUpdatePartial("jpm-process/edit-button", "GET", {processId: $('#txtProcessId').val()}, null, "button-modal-content", setSubmitButtonEvent);
    });

    // Reload data when close popup
    $('#button-modal').on('hidden.bs.modal', function() {
        loadButtonTable("jpm-process/button","GET", {processId: $('#txtProcessId').val()});
     
    });
    loadButtonTable("jpm-process/button","GET", {processId: $('#txtProcessId').val()});
    // ========== END INIT BUTTON ==========
//
//    // ========== BEGIN INIT RULE ==========
//    // Button add
//    $('#btn-add-rule').click(function() {
//    	sendAjaxAndUpdatePartial("jpm-process/edit-rule", "GET", {processId: $('#txtProcessId').val(), routerId: $('#routerRuleControl').val() }, null, "rule-modal-content", setSubmitRuleEvent);
//    });
//    // Reload data when close popup
//    $('#rule-modal').on('hidden.bs.modal', function() {
//        loadRuleTable("jpm-process/rule","GET", {processId: $('#txtProcessId').val(), routerId: $('#routerRuleControl').val()});
//    });
//    loadRuleTable("jpm-process/rule","GET", {processId: $('#txtProcessId').val(), routerId: $('#routerRuleControl').val()});
//    $('#routerRuleControl').on('change', function(){
//    	loadRuleTable("jpm-process/rule","GET", {processId: $('#txtProcessId').val(), routerId: $('#routerRuleControl').val()});
//    });
//    // ========== END INIT RULE ==========
//    
//    // ========== BEGIN INIT ROUTER ==========
//    $('#router-modal').on('hidden.bs.modal', function() {
//        loadRouterTable("jpm-process/router","GET", {processId: $('#txtProcessId').val()});
//    });
//    loadRouterTable("jpm-process/router","GET", {processId: $('#txtProcessId').val()});
    // ========== END INIT ROUTER ==========
    
    // ========== BEGIN INIT TEST ==========
    // Reload data when close popup
    $('#step-modal').on('hidden.bs.modal', function() {
        loadStepTable("jpm-process/step","GET", {processId: $('#txtProcessId').val()});
    });
    loadStepTable("jpm-process/step","GET", {processId: $('#txtProcessId').val()});
    // ========== END INIT TEST ==========

/*    $('#btnRevert').click(function(event) {
        popupConfirm("A du sua quan to rì vợt?", function (result) {
            if (!result) return;

            ajaxSubmit("jpm-process/revert", {processId: $('#txtProcessId').val(), majorVersion: 1, minorVersion: 0}, event)
        });
    });*/
    
    $('#btnDeploy').click(function(event) {
    	if($(this).attr("disabled")!="disabled") {
    		if ($(".j-form-validate").valid()) {
    			$('#deploy-modal').modal("show");
    		}
    	}
    });
    
    $('#btn-deploy-process-submit').click(function(event) {
    	$('#deploy-modal').on('hidden.bs.modal', function() {
	    	var dataForm = $("#form-detail").serializeArray();
	    	var filesList = [];
	    	$.each(filesUpload, function( key, value ) {
	    		dataForm.push({"name": key,"value": value});
	    		filesList.push(value);
	    	});
	    	
	    	// deploy
	    	var isCloneRole =  $('#isCloneRole').is(':checked');
	    	var isCloneSLA =  $('#isCloneSLA').is(':checked');
	    	var processDeployOldId =  $('#processDeployOldId').val();
	    	
	    	dataForm.push({"name": "isDeployed","value": true});
	    	dataForm.push({"name": "isCloneRole","value": isCloneRole});
	    	dataForm.push({"name": "isCloneSLA","value": isCloneSLA});
	    	dataForm.push({"name": "processDeployOldId","value": processDeployOldId});
	    	
	    	if(filesList.length > 0){
	    		saveDataAndFile(dataForm);
	    	} else {
	    		saveData(dataForm, event);
	    	}
	    });

    	$('#deploy-modal').modal("hide");
    });
    
    $('#process-type').change(function(event){
    	var type = $(this).val();
 
    	if(type==="internal"){
    		$("#btn-add-step").removeClass("hidden");
    	}else{

    		$("#btn-add-step").addClass("hidden");
    	}
    });
    
    //clone-get-modal
    $('#btn-clone-process').click(function(event) {
    	var businessId = $("#businessId").val();
    	var companyId = $("#companyId").val();
    	var id = $("#txtProcessId").val();
    	ajaxGetCloneModal(companyId,businessId, id);
    	$('#clone-modal').modal("show");
    });
    
    $("#companyId").on('change', function(event){
    	var val = $(this).val();
    	if(val == ""){
    		$("#businessId").val("");
    		$("#businessId").attr('disabled','true');
    	}else{
    		$("#businessId").removeAttr('disabled');
        	ajaxLoadBusinessListByCompanyId(val, event);
    	}
    });
    
    $("#businessId").on('change', function(event){
    	var val = $(this).val();
    	if(val != ""){
        	ajaxGetBusiness(val);
    	}
    });

	$('#dmn-upload-form').fileupload({
		url: BASE_URL + "jpm-process/upload-dmn",
		type: 'POST',
		dataType: 'json',
		formAcceptCharset: 'utf-8',
		limitMultiFileUploadSize: 3000000, // 10M
		limitMultiFileUploadSizeOverhead: 3000000, // 10M
		limitMultiFileUploads: 10,
		multipart: true,
		maxChunkSize: 10000000,
		sequentialUploads: true,
		singleFileUploads: false,
		add: function(e, data) {
			console.log("data : ",data);
			var formData =  new FormData();
			formData.append('processId', $("#txtProcessId").val());
//			formData.append('dmnFiles', data.files);
			$.each(data.files, function( key, value ) {
				formData.append('dmnFiles', value);
			});
			$.ajax({
				type: 'POST',
				url: BASE_URL + "jpm-process/upload-dmn",
				data: formData,
        		processData: false,
		        contentType: false,
		        async: false,
				beforeSend: function(xhrObj){
					blockbg();
				},
		        cache: false,
		        timeout: 1000000,
				success: function(data, textStatus, request) {
					$('#tableProcessDmn').html(data);
				},
				error: function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				},
		  		complete: function(data) { 
			        unblockbg();
			    }
			});
		},
	});
	
	$('#tableProcessDmn').on('click', '.btn-delete', function(e){
		var dmnId = $(this).closest('tr').data('process-dmn-id');
		popupConfirm(MSG_DEL_CONFIRM, function (result) {
	        if (result) {
	            $.ajax({
					type: 'POST',
					url: BASE_URL + "jpm-process/delete-dmn",
					data: {
						processId: $("#txtProcessId").val(),
						dmnId: dmnId
					},
			        timeout: 1000000,
					success: function(data, textStatus, request) {
						$('#tableProcessDmn').html(data);
					},
					error: function(xhr, textStatus, error) {
						console.log(xhr);
						console.log(textStatus);
						console.log(error);
					}
				});
			}
	    });
	});
	
	$('#tableProcessDmn').on('click', '.btn-view', function(e){
		var dmnId = $(this).closest('tr').data('process-dmn-id');
	    popupConfirm("Tính năng chưa có " + dmnId, function (result) {
	        if (result) {
	            
			}
	    });        
	});
});

// ========== BEGIN FUNCTIONS PARAM ==========
function loadParamTable(url, methodType, data) {
    sendAjaxAndUpdatePartial(url, methodType, data, null, "tableProcessParam", function() {
        // Event click btn edit each row
        $('#' + 'tableProcessParam').find('.btn-edit').each(function() {
            $(this).click(function() {
                var elementData = {
                    processId: $('#txtProcessId').val(),
                    businessId: $('#txtBusinessId').val(),
                    paramId: $(this).closest('tr').attr('data-jpm-param-id')
                }
                sendAjaxAndUpdatePartial("jpm-process/edit-param", "GET", elementData, null, "param-modal-content", setSubmitParamEvent);
            });
        })

        // Event click btn delete each row
        $('#' + 'tableProcessParam').find('.btn-delete').each(function() {
            $(this).click(function() {
                deleteParam($(this).closest('tr').attr('data-jpm-param-id'));
            });
        })
    });
    
}

function setSubmitParamEvent() {
    $('#btn-param-submit').click(function(event) {
        if (event != null && event != undefined)
            event.preventDefault();
        if (!$("#param-form").valid())
            return;
        sendAjaxAndUpdatePartial("jpm-process/edit-param", "POST", $('#param-form').serialize(), event, "param-modal-content", setSubmitParamEvent);
    });
}

function deleteParam(paramId) {
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            loadParamTable("jpm-process/delete-param", "POST", {processId: $('#txtProcessId').val(), paramId: paramId});
        }
    });
}
// ========== END FUNCTIONS PARAM ==========

// ========== BEGIN FUNCTIONS STATUS ==========
function loadStatusTable(url, methodType, data) {
    sendAjaxAndUpdatePartial(url, methodType, data, null, "tableProcessStatus", function() {
        // Event click btn edit each row
        $('#' + 'tableProcessStatus').find('.btn-edit').each(function() {
            $(this).click(function() {
                var elementData = {
                    processId: $('#txtProcessId').val(),
                    statusId: $(this).closest('tr').attr('data-jpm-status-id')
                }
                sendAjaxAndUpdatePartial("jpm-process/edit-status", "GET", elementData, null, "status-modal-content", setSubmitStatusEvent);
            });
        })

        // Event click btn delete each row
        $('#' + 'tableProcessStatus').find('.btn-delete').each(function() {
            $(this).click(function() {
                deleteStatus($(this).closest('tr').attr('data-jpm-status-id'));
            });
        })
    });
}

function setSubmitStatusEvent() {
    $('#btn-status-submit').click(function(event) {
        if (event != null && event != undefined)
            event.preventDefault();
        if (!$("#status-form").valid())
            return;
        sendAjaxAndUpdatePartial("jpm-process/edit-status", "POST", $('#status-form').serialize(), event, "status-modal-content", setSubmitStatusEvent);
    });
}

function deleteStatus(statusId) {
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            loadStatusTable("jpm-process/delete-status", "POST", {processId: $('#txtProcessId').val(), statusId: statusId});
        }
    });
}
// ========== END FUNCTIONS STATUS ==========

// ========== BEGIN FUNCTIONS FUNCTION ==========
function loadFunctionTable(url, methodType, data) {
    sendAjaxAndUpdatePartial(url, methodType, data, null, "tableProcessFunction", function() {
        // Event click btn edit each row
        $('#' + 'tableProcessFunction').find('.btn-edit').each(function() {
            $(this).click(function() {
                var elementData = {
                    processId: $('#txtProcessId').val(),
                    functionId: $(this).closest('tr').attr('data-jpm-function-id')
                }
                sendAjaxAndUpdatePartial("jpm-process/edit-function", "GET", elementData, null, "function-modal-content", setSubmitFunctionEvent);
            });
        })

        // Event click btn delete each row
        $('#' + 'tableProcessFunction').find('.btn-delete').each(function() {
            $(this).click(function() {
                deleteFunction($(this).closest('tr').attr('data-jpm-function-id'));
            });
        })
    });
}

function setSubmitFunctionEvent() {
    $('#btn-function-submit').click(function(event) {
        if (event != null && event != undefined)
            event.preventDefault();
        if (!$("#function-form").valid())
            return;
        sendAjaxAndUpdatePartial("jpm-process/edit-function", "POST", $('#function-form').serialize(), event, "function-modal-content", setSubmitFunctionEvent);
    });
}

function deleteFunction(functionId) {
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            loadFunctionTable("jpm-process/delete-function", "POST", {processId: $('#txtProcessId').val(), functionId: functionId});
        }
    });
}
// ========== END FUNCTIONS FUNCTION ==========

// ========== BEGIN FUNCTIONS BUTTON ==========
function loadButtonTable(url, methodType, data) {
    sendAjaxAndUpdatePartial(url, methodType, data, null, "tableProcessButton", function() {
        // Event click btn edit each row
        $('#' + 'tableProcessButton').find('.btn-edit').each(function() {
            $(this).click(function() {
                var elementData = {
                    processId: $('#txtProcessId').val(),
                    buttonId: $(this).closest('tr').attr('data-jpm-button-id')
                }
                sendAjaxAndUpdatePartial("jpm-process/edit-button", "GET", elementData, null, "button-modal-content", setSubmitButtonEvent);
            });
        })

        // Event click btn delete each row
        $('#' + 'tableProcessButton').find('.btn-delete').each(function() {
            $(this).click(function() {
                deleteButton($(this).closest('tr').attr('data-jpm-button-id'));
            });
        })
    });
}

function setSubmitButtonEvent() {
    $('#btn-button-submit').click(function(event) {
        if (event != null && event != undefined)
            event.preventDefault();
        if (!$("#button-form").valid())
            return;
        sendAjaxAndUpdatePartial("jpm-process/edit-button", "POST", $('#button-form').serialize(), event, "button-modal-content", setSubmitButtonEvent);
    });
  //setAssign trigger on ButtonType
	$('#buttonType').change(function(event){
		chooseAssignOnTabButton($(this));
	});
}

function deleteButton(buttonId) {
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            loadButtonTable("jpm-process/delete-button", "POST", {processId: $('#txtProcessId').val(), buttonId: buttonId});
        }
    });
}
// ========== END FUNCTIONS BUTTON ==========

//========== BEGIN FUNCTIONS RULE ==========
function loadRuleTable(url, methodType, data) {
    sendAjaxAndUpdatePartial(url, methodType, data, null, "tableProcessRule", function() {
        // Event click btn edit each row
        $('#' + 'tableProcessRule').find('.btn-edit').each(function() {
            $(this).click(function() {
                var elementData = {
                    processId: $('#txtProcessId').val(),
                    ruleId: $(this).closest('tr').attr('data-jpm-rule-id')
                }
                sendAjaxAndUpdatePartial("jpm-process/edit-rule", "GET", elementData, null, "rule-modal-content", setSubmitRuleEvent);
            });
        })

        // Event click btn delete each row
        $('#' + 'tableProcessRule').find('.btn-delete').each(function() {
            $(this).click(function() {
                deleteRule($(this).closest('tr').attr('data-jpm-rule-id'));
            });
        })
    });
}

function setSubmitRuleEvent() {
    $('#btn-rule-submit').click(function(event) {
        if (event != null && event != undefined)
            event.preventDefault();
        if (!$("#rule-form").valid())
            return;
        if (!$("#ruleDataBuilder").queryBuilder('validate')) return;
        var condition = $('#rule-form').serializeArray();
        var ruleData = $("#ruleDataBuilder").queryBuilder('getRules');

		condition.push({name: 'ruleData', value: JSON.stringify(ruleData)});
		condition.push({name: 'filter', value: JSON.stringify(FILTER)});
        
        sendAjaxAndUpdatePartial("jpm-process/edit-rule", "POST", condition, event, "rule-modal-content", setSubmitRuleEvent);
    });
}

function deleteRule(ruleId) {
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            loadRuleTable("jpm-process/delete-rule", "POST", {processId: $('#txtProcessId').val(), ruleId: ruleId, routerId: $('#routerRuleControl').val()});
        }
    });
}
// ========== END FUNCTIONS RULE ==========

//========== BEGIN FUNCTIONS ROUTER ==========
function loadRouterTable(url, methodType, data) {
	sendAjaxAndUpdatePartial(url, methodType, data, null, "tableProcessRouter", function() {
		// Event click btn edit each row
		$('#' + 'tableProcessRouter').find('.btn-edit').each(function() {
			$(this).click(function() {
				var elementData = {
						processId: $('#txtProcessId').val(),
						routerId: $(this).closest('tr').attr('data-jpm-router-id')
				}
				sendAjaxAndUpdatePartial("jpm-process/edit-router", "GET", elementData, null, "router-modal-content", setSubmitRouterEvent);
			});
		})
	});
}

function setSubmitRouterEvent() {
	$('#btn-router-submit').click(function(event) {
		if (event != null && event != undefined)
			event.preventDefault();
        if (!$("#router-form").valid())
            return;
		var condition = $('#router-form').serializeArray();
		
		sendAjaxAndUpdatePartial("jpm-process/edit-router", "POST", condition, event, "router-modal-content", setSubmitRouterEvent);
	});
}

// ========== END FUNCTIONS ROUTER ==========

// ========== BEGIN TEST BUTTON ==========
function loadStepTable(url, methodType, data) {
    sendAjaxAndUpdatePartial(url, methodType, data, null, "tableProcessStep", function() {
        // Event click btn edit each row
        $('#' + 'tableProcessStep').find('.btn-edit').each(function() {
            $(this).click(function() {
                var elementData = {
                    processId: $('#txtProcessId').val(),
                    stepId: $(this).closest('tr').attr('data-jpm-step-id')
                }
                sendAjaxAndUpdatePartial("jpm-process/edit-step", "GET", elementData, null, "step-modal-content", setSubmitStepEvent);
            });
        });

        // Event click btn delete each row
        // $('#' + 'tableProcessStep').find('.btn-delete').each(function() {
        //     $(this).click(function() {
        //         deleteButton($(this).closest('tr').attr('data-jpm-button-id'));
        //     });
        // })
    });
}

function setSubmitStepEvent() {
    $('#btn-step-submit').click(function(event) {
        if (event != null && event != undefined)
            event.preventDefault();
        if (!$("#step-form").valid())
            return;
        sendAjaxAndUpdatePartial("jpm-process/edit-step", "POST", $('#step-form').serialize(), event, "step-modal-content", setSubmitStepEvent);
    });
    $('#child-data-button').find('.btn-delete').click(deleteRowButtonForStep);

    $('#child-data-button').find('.isSignClass').click(function(){
    	var checked = $(this).is(':checked');
        if(checked){
        	$(this).closest('tr').find('.isExportPdfClass').prop('checked', checked);
        }
    });
    
    $('#child-data-button').find('.isExportPdfClass').click(function(){
    	var checked = $(this).is(':checked');
        if(!checked){
        	$(this).closest('tr').find('.isSignClass').prop('checked', checked);
        }
    });
    
    $('#btn-add-button-for-step').click(function(event) {
        var newRow = $('#row-button-hidden').clone().removeAttr("style");
        var tbody = $('#row-button-hidden').closest("tbody");
        var arrayIndex = tbody.find("tr").length;
        var textIndex = tbody.find("tr:not([style])").length;

        updateRowButton(newRow, arrayIndex - 1, textIndex);
        
        newRow.appendTo(tbody);
        newRow.find('.btn-delete').click(deleteRowButtonForStep);
        
        newRow.find('.isSignClass').click(function(){
        	var checked = $(this).is(':checked');
            if(checked){
            	$(this).closest('tr').find('.isExportPdfClass').prop('checked', checked);
            }
        });
        
        newRow.find('.isExportPdfClass').click(function(){
        	var checked = $(this).is(':checked');
            if(!checked){
            	$(this).closest('tr').find('.isSignClass').prop('checked', checked);
            }
        });
        
        newRow.find(".btn-show-sign-type-chooser").on("click", function(){
    		selectedObj = $(this).closest(".input-group").find("input:first-child");
    	});
    });
}

function updateRowButton(row, index, text) {
    $(row.find("td:first")).text(text + 1);
    $($(row.find("td").get(1)).find('select')).attr('name', 'listJpmButton[' + index + '].buttonId');
    $($(row.find("td").get(2)).find('input')).attr('name', 'listJpmButton[' + index + '].isSave');
    $($(row.find("td").get(3)).find('input')).attr('name', 'listJpmButton[' + index + '].isSaveEform');
    $($(row.find("td").get(4)).find('input')).attr('name', 'listJpmButton[' + index + '].isAuthenticate');
    $($(row.find("td").get(5)).find('input')).attr('name', 'listJpmButton[' + index + '].displayHistoryApprove');
    $($(row.find("td").get(6)).find('input')).attr('name', 'listJpmButton[' + index + '].isExportPdf');
    $($(row.find("td").get(7)).find('input')).attr('name', 'listJpmButton[' + index + '].isSign');
    $($(row.find("td").get(8)).find('input')).attr('name', 'listJpmButton[' + index + '].fieldSign');
//    $($(row.find("td").get(9)).find('select')).attr('name', 'listJpmButton[' + index + '].signType');
//    $($(row.find("td").get(10)).find('input')).attr('name', 'listJpmButton[' + index + '].signPosition');
    $($(row.find("td").get(9)).find('select')).attr('name', 'listJpmButton[' + index + '].functionId');
}

function deleteRowButtonForStep() {
    var tbody = $(this).closest("tbody");
    var id = $(this).closest("tr").attr("child-data-jpm-button-id");
    if (id != undefined && id != null && id != "0") {
        $(this).closest("tr").attr("style", "display: none");
        $($(this).closest("tr").find("input[type='hidden']:first")).val('true');
    } else {
        $(this).closest("tr").remove();
    }
    var remaining = tbody.find("tr");
    var j = 1;
    for (var i = 1; i < remaining.length; i++) {
        if ($(remaining[i]).attr('style') != undefined) {
            j++;
        }
        updateRowButton($(remaining[i]), i - 1, i - j);
    }
}
// ========== END TEST BUTTON ==========

function sendAjaxAndUpdatePartial(url, methodType, submitData, event, partialId, actionAfterDone) {
    if (event != null && event != undefined)
        event.preventDefault();

    $.ajax({
        type : methodType,
        url : BASE_URL + url,
        data : submitData,
        success : function(data, textStatus, request) {
            $('#' + partialId).html(data);

            if (actionAfterDone != null && actionAfterDone != undefined)
                actionAfterDone();
        },
        error : function(xhr, textStatus, error) {
            console.log(xhr);
            console.log(textStatus);
            console.log(error);
        }
    });
}

function back() {
    var url = BASE_URL + "jpm-process/list";
    ajaxRedirect(url);
}

function create() {
    var url = BASE_URL + "jpm-process/edit";
    ajaxRedirect(url);
}

function saveData(dataForm, event) {
    var url = "jpm-process/edit";
    ajaxSubmit(url, dataForm, event);
}

function saveDataAndFile(dataForm) {
	var formData = new FormData();
	
	$.map(dataForm, function(n, i){
        formData.append(n['name'], n['value']);
    });
	
	$.ajax({
		type : "POST",
		enctype: 'multipart/form-data',
		url : BASE_URL + "jpm-process/edit",
		data : formData,
		// prevent jQuery from automatically transforming the data into a query string
        processData: false,
        contentType: false,
        cache: false,
        timeout: 1000000,
		success : function(data, textStatus, request) {
			var content = $(data).find('.body-content');
			$(".main_content").html(content);
			var urlPage = $(data).find('#url').val();
			if (urlPage != null && urlPage != '') {
				window.history.pushState('', '', BASE_URL + urlPage);
			}
			
			goTopPage();
		},
		error : function(xhr, textStatus, error) {
			console.log(error);
		}
	});
}

function chooseAssignOnTabButton(button){
	var val = $(button).children("option:selected").val();
	if(val==="ASSIGN"){
		$(button).closest('.modal-body').find('#assignTo').removeAttr('disabled');
	}else{
		$(button).closest('.modal-body').find('#assignTo').attr('disabled','true');
	}
}

function setSubmitStatusEvent() {
    $('#btn-status-submit').click(function(event) {
        if (event != null && event != undefined)
            event.preventDefault();
        if (!$("#status-form").valid())
            return;
        sendAjaxAndUpdatePartial("jpm-process/edit-status", "POST", $('#status-form').serialize(), event, "status-modal-content", setSubmitStatusEvent);
    });
}

function ajaxGetCloneModal(companyId, businessId, id){
	$.ajax({
        type : "GET",
        url : BASE_URL + "jpm-process/clone-process",
        data : {
        	'companyId': 	companyId,
        	'businessId':	businessId,
        	'id':			id
        },
        success : function(data, textStatus, request) {
        	var content = $(data).find('.modal-content').html();
            $('#clone-process-modal-content').html(content);
        },
        error : function(xhr, textStatus, error) {
            console.log(xhr);
            console.log(textStatus);
            console.log(error);
        }
    });
}


function ajaxLoadBusinessListByCompanyId(companyId, event) {
	var url = "jpm-process-deploy/ajax/load-business";
	var condition = {};
	condition["companyId"] = companyId;
	
	loadDataCombobox(url, condition, "#businessId", event);
}

function ajaxGetBusiness(businessId){
	$.ajax({
	 	type : "POST",
        url : BASE_URL + "jpm-business/ajax-get-business",
        data : {
        	'businessId':	businessId
        },
        success : function(data, textStatus, request) {
        	var processType = data.processType;
        	
        	$('#processType').val(processType);
        	
        	if(processType == 3){
        		$('.act-process').removeClass('hidden');
        	}else{
        		$('.act-process').addClass('hidden');
        	}
        },
        error : function(xhr, textStatus, error) {
            console.log(xhr);
            console.log(textStatus);
            console.log(error);
        }
	});
}