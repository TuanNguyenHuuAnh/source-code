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
        url: BASE_URL + "jpm-process-deploy/edit",
        type: 'POST',
        dataType: 'text/html;charset=UTF-8',
        formAcceptCharset: 'utf-8',
        limitMultiFileUploadSize: 1000000, // 10M
        limitMultiFileUploadSizeOverhead: 1000000, // 10M
        multipart: true,
        maxChunkSize: 10000000,
        sequentialUploads: true,
        fileExt: '*.xml;',
        singleFileUploads: false,
        add: function (e, data) {
        	//console.log(data.paramName);
            var imageConsoleEl = $(this).find('.alert-' + data.paramName);
            $(imageConsoleEl).hide();
            var acceptFileTypes = /(\.|\/)(xml)$/i;
            if (data.originalFiles[0]['type'].length && !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                $(imageConsoleEl).text('Not an accepted file type')
                $(imageConsoleEl).show();
                return;
            }
            if (data.originalFiles[0]['size'].length && data.originalFiles[0]['size'] > 5000000) {
                $(imageConsoleEl).text('Filesize is too big')
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
    });
	
    // Back
    $('#btnBack, #btnCancel').click(function () {
        back();
    });

    // ========== BEGIN INIT PARAM ==========
    // Button add
    $('#btn-add-param').click(function() {
        sendAjaxAndUpdatePartial("jpm-process-deploy/edit-param", "GET", {processId: $('#txtProcessId').val()}, null, "param-modal-content", setSubmitParamEvent);
    });

    // Reload data when close popup
//    $('#param-modal').on('hidden.bs.modal', function() {
//        loadParamTable("jpm-process-deploy/param","GET", {processId: $('#txtProcessId').val()});
//    });
    loadParamTable("jpm-process-deploy/param","GET", {processId: $('#txtProcessId').val()});
    // ========== END INIT PARAM ==========

    // ========== BEGIN INIT STATUS ==========
    // Button add
    $('#btn-add-status').click(function() {
        sendAjaxAndUpdatePartial("jpm-process-deploy/edit-status", "GET", {processId: $('#txtProcessId').val()}, null, "status-modal-content", setSubmitStatusEvent);
    });

//    // Reload data when close popup
//    $('#status-modal').on('hidden.bs.modal', function() {
//        loadStatusTable("jpm-process-deploy/status","GET", {processId: $('#txtProcessId').val()});
//    });
    loadStatusTable("jpm-process-deploy/status","GET", {processId: $('#txtProcessId').val()});
    // ========== END INIT STATUS ==========

    // ========== BEGIN INIT FUNCTION ==========
    // Button add
    $('#btn-add-function').click(function() {
        sendAjaxAndUpdatePartial("jpm-process-deploy/edit-function", "GET", {processId: $('#txtProcessId').val()}, null, "function-modal-content", setSubmitFunctionEvent);
    });

//    // Reload data when close popup
//    $('#function-modal').on('hidden.bs.modal', function() {
//        loadFunctionTable("jpm-process-deploy/function","GET", {processId: $('#txtProcessId').val()});
//    });
    loadFunctionTable("jpm-process-deploy/function","GET", {processId: $('#txtProcessId').val()});
    // ========== END INIT FUNCTION ==========

    // ========== BEGIN INIT BUTTON ==========
    // Button add
    $('#btn-add-button').click(function() {
        sendAjaxAndUpdatePartial("jpm-process-deploy/edit-button", "GET", {processId: $('#txtProcessId').val()}, null, "button-modal-content", setSubmitButtonEvent);
    });

//    // Reload data when close popup
//    $('#button-modal').on('hidden.bs.modal', function() {
//        loadButtonTable("jpm-process-deploy/button","GET", {processId: $('#txtProcessId').val()});
//    });
    loadButtonTable("jpm-process-deploy/button","GET", {processId: $('#txtProcessId').val()});
    // ========== END INIT BUTTON ==========

    // ========== BEGIN INIT TEST ==========
    $('#btnTest').click(function() {
        var elementData = {
            processId: $('#txtProcessId').val(),
            stepId: '1'
        }
        sendAjaxAndUpdatePartial("jpm-process-deploy/edit-step", "GET", elementData, null, "step-modal-content", setSubmitStepEvent);
    })
    // ========== END INIT TEST ==========
//    
//    $('#step-modal').on('hidden.bs.modal', function() {
//        loadStepTable("jpm-process-deploy/step","GET", {processId: $('#txtProcessId').val()});
//    });
    loadStepTable("jpm-process-deploy/step","GET", {processId: $('#txtProcessId').val()});
});

// ========== BEGIN FUNCTIONS PARAM ==========
function loadParamTable(url, methodType, data) {
    sendAjaxAndUpdatePartial(url, methodType, data, null, "tableProcessParam", function() {
        // Event click btn edit each row
        $('#' + 'tableProcessParam').find('.btn-edit').each(function() {
            $(this).click(function() {
                var elementData = {
                    processId: $('#txtProcessId').val(),
                    paramId: $(this).closest('tr').attr('data-jpm-param-id')
                }
                sendAjaxAndUpdatePartial("jpm-process-deploy/edit-param", "GET", elementData, null, "param-modal-content", setSubmitParamEvent);
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
        sendAjaxAndUpdatePartial("jpm-process-deploy/edit-param", "POST", $('#param-form').serialize(), event, "param-modal-content", setSubmitParamEvent);
    });
}

function deleteParam(paramId) {
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            loadParamTable("jpm-process-deploy/delete-param", "POST", {processId: $('#txtProcessId').val(), paramId: paramId});
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
                sendAjaxAndUpdatePartial("jpm-process-deploy/edit-status", "GET", elementData, null, "status-modal-content", setSubmitStatusEvent);
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
        sendAjaxAndUpdatePartial("jpm-process-deploy/edit-status", "POST", $('#status-form').serialize(), event, "status-modal-content", setSubmitStatusEvent);
    });
}

function deleteStatus(statusId) {
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            loadStatusTable("jpm-process-deploy/delete-status", "POST", {processId: $('#txtProcessId').val(), statusId: statusId});
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
                sendAjaxAndUpdatePartial("jpm-process-deploy/edit-function", "GET", elementData, null, "function-modal-content", setSubmitFunctionEvent);
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
        sendAjaxAndUpdatePartial("jpm-process-deploy/edit-function", "POST", $('#function-form').serialize(), event, "function-modal-content", setSubmitFunctionEvent);
    });
}

function deleteFunction(functionId) {
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            loadFunctionTable("jpm-process-deploy/delete-function", "POST", {processId: $('#txtProcessId').val(), functionId: functionId});
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
                sendAjaxAndUpdatePartial("jpm-process-deploy/edit-button", "GET", elementData, null, "button-modal-content", setSubmitButtonEvent);
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
        sendAjaxAndUpdatePartial("jpm-process-deploy/edit-button", "POST", $('#button-form').serialize(), event, "button-modal-content", setSubmitButtonEvent);
    });
}

function deleteButton(buttonId) {
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            loadButtonTable("jpm-process-deploy/delete-button", "POST", {processId: $('#txtProcessId').val(), buttonId: buttonId});
        }
    });
}
// ========== END FUNCTIONS BUTTON ==========

// ========== BEGIN TEST BUTTON ==========
function setSubmitStepEvent() {
    $('#btn-step-submit').click(function(event) {
        if (event != null && event != undefined)
            event.preventDefault();
        if (!$("#step-form").valid())
            return;
        sendAjaxAndUpdatePartial("jpm-process-deploy/edit-step", "POST", $('#step-form').serialize(), event, "step-modal-content", setSubmitStepEvent);
    });
    $('#child-data-button').find('.btn-delete').click(deleteRowButtonForStep);

    $('#btn-add-button-for-step').click(function(event) {
        var newRow = $('#row-button-hidden').clone().removeAttr("style");
        var tbody = $('#row-button-hidden').closest("tbody");
        var arrayIndex = tbody.find("tr:not([style])").length;

        updateRowButton(newRow, arrayIndex);

        newRow.appendTo(tbody);
        newRow.find('.btn-delete').click(deleteRowButtonForStep);
    });
}

function updateRowButton(row, index) {
    $(row.find("td:first")).text(index + 1);
    $(row.find("td:nth-child(2)").find('select')).attr('name', 'listJpmButton[' + index + '].buttonId');
    $(row.find("td:nth-child(3)").find('input').attr('name', 'listJpmButton[' + index + '].isSave'))
    $(row.find("td:nth-child(4)").find('select').attr('name', 'listJpmButton[' + index + '].functionId'))
}

function deleteRowButtonForStep() {
    var tbody = $(this).closest("tbody");
    if ($(this).closest("tr").attr("child-data-jpm-button-id") != "0") {
        $(this).closest("tr").attr("style", "display: none");
        $($(this).closest("tr").find("input[type='hidden']:first")).val('true');
    } else {
        $(this).closest("tr").remove();
    }
    var remaining = tbody.find("tr:not([style])");
    for (var i = 0; i < remaining.length; i++) {
        updateRowButton($(remaining[i]), i);
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
    var url = BASE_URL + "jpm-process-deploy/list";
    ajaxRedirect(url);
}

//========== BEGIN TEST BUTTON ==========
function loadStepTable(url, methodType, data) {
    sendAjaxAndUpdatePartial(url, methodType, data, null, "tableProcessStep", function() {
        // Event click btn edit each row
        $('#' + 'tableProcessStep').find('.btn-edit').each(function() {
            $(this).click(function() {
                var elementData = {
                    processId: $('#txtProcessId').val(),
                    stepId: $(this).closest('tr').attr('data-jpm-step-id')
                }
                sendAjaxAndUpdatePartial("jpm-process-deploy/edit-step", "GET", elementData, null, "step-modal-content", setSubmitStepEvent);
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
