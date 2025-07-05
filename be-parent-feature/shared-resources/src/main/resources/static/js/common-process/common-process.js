var VALUE_RETURN = 0;
var ACTION_PROCESS_URL = '/do-action-process';
var ACTION_UPDATE_URL = '/do-update-date';
var ACTION_CLONE_URL = '/clone-data';

$(document).ready(function() {
	$('.btn-process-group').unbind().bind('click',function(e){
        e.preventDefault();

        $('#confirmReview-error').css('display', 'none');
        $('#confirmNote-error').css('display', 'none');
        
		var button = $(this);
        $('#confirmNote').removeAttr('disabled');

        /*var actButtonType = $(button).data("button-type");
        $('#confirmNote').val('');
        
        if (actButtonType == 'RETURN' && $(this).val() == VALUE_RETURN){
            $('#confirmNote').attr('disabled', 'disabled');
            
            $('#processPopupConfirm').find('.btn-process-confirm').removeAttr('data-dismiss');
        }else{
            $('#processPopupConfirm').find('.btn-process-confirm').attr('data-dismiss', 'modal');
        }*/
        
        $('.confirmNote').val($('#confirmNote').val());
        
        let attachment = true;
        
        if (!attachment) {
            popupAlert(MES_SUBMIT_HASNT_FILE, function(result) {
                if (result) {
                    initValueAction(button, e);
                }
            });
        } else {
            initValueAction(button, e);
        }
	});
	
    // Action process
    $('.btn-process-confirm').unbind('click').bind('click', function(e) {
        actionProcess(e);
    });

    $('.btn-update-data').unbind('click').bind('click', function(e) {

        e.preventDefault();

        $('#confirmReview-error').css('display', 'none');
        $('#confirmNote-error').css('display', 'none');

        var button = $(this);
        $('#confirmNote').removeAttr('disabled');

        $('.confirmNote').val($('#confirmNote').val());

        let attachment = true;

        if (!attachment) {
            popupAlert(MES_SUBMIT_HASNT_FILE, function(result) {
                if (result) {
                    initValueAction(button, e);
                }
            });
        } else {
            initValueAction(button, e);
        }
    });

    $('.btn-clone-process').unbind('click').bind('click', function(e) {
		updateElementEditor();
		
		let url = CONTROLLER_URL + ACTION_CLONE_URL;
        postData(url, e);
    });
});

function initValueAction(button, e){
    $('#actButtonType').val($(button).data("button-type"));
    $('#actButtonName').val($(button).data("button-name"));
    $('#actButtonId').val($(button).data("button-id"));
    
	updateElementEditor();

    let actButtonType = $('#actButtonType').val();
    if (actButtonType === 'SAVE') {
        let url = CONTROLLER_URL + ACTION_PROCESS_URL;
        postData(url, e);
    } else if ($(".j-form-validate").valid()) {
        if (actButtonType == 'SUBMIT') {
            popupConfirmWithButtons(MSG_CONFIRM_SUBMIT, LIST_BUTTON_CONFIRM_PROCESS, function(result) {
                if (result) {
                    actionProcess(e);
                }
            })
        }
        else {
            let btnName = $('#actButtonName').val();
            $("#processPopupConfirm").find(".actionName").html("<b>[" + btnName + "]</b>");
    
            $("#processPopupConfirm").modal();
        }
    } else {
        $('html, body').animate({
            scrollTop: ($(".j-form-validate").find(":input.error:first").parent().offset().top - 100)
        }, 1000);
    }
}

function actionProcess(e){
    let actButtonType = $('#actButtonType').val();
    $('msgErr').modal('hide');
    if(actButtonType === 'UPDATE'){
        blockbg();
        updateElementEditor();

        let url = CONTROLLER_URL + ACTION_UPDATE_URL;

        postData(url, e);

        $('#reviewPopupConfirm').modal('hide');
        $('#processPopupConfirm').modal('hide');
    } else if ($(".j-form-validate").valid()) {
        blockbg();
        let url = CONTROLLER_URL + ACTION_PROCESS_URL;
    
        postData(url, e);
    
        $('#reviewPopupConfirm').modal('hide');
        $('#processPopupConfirm').modal('hide');
    } else {
        $('html, body').animate({
            scrollTop: ($(".j-form-validate").find(":input.error:first").parent().offset().top - 100)
        }, 1000);
    }
}

function postData(url, e) {
    let condition = getDataForSubmit();
    ajaxSubmit(url, $.param(condition), e);
}

function getDataForSubmit() {
    let condition = [];

    /*if (hasEdit()) {
        $("#isSave").val(true);
    };*/

    $('#docInputJson').val(JSON.stringify($('.j-form-validate').serializeArray()));

    $('#docInputJson').attr('name', 'docInputJson');
    
    $(".j-form-validate").find('input[name=confirmNote]').val($('#confirmNote').val());
    
    // Xoá các input, select bị disabled
    $('input:disabled, select:disabled').addClass('attr-disabled');
    
    $('input:disabled, select:disabled').each(function () {
       $(this).removeAttr('disabled');
    });
    
    var dataArray = $(".j-form-validate").serializeArray();
    
    $(dataArray).each(function (i, field) {
        if (field.value != null && field.value != '') {
            let obj = {};
            obj["name"] = field.name;

			if (DATE_FORMATE_TYPE == undefined || DATE_FORMATE_TYPE == '') {
				obj["value"] = field.value;
			} else {
				obj["value"] = field.value;
				if (DATE_FORMATE_TYPE == 'FULL_DATE'){
					
					let objString = {};
					
					objString["name"] = field.name + "String";
					objString["value"] = field.value;
					condition.push(objString);
					
					if (isValidDateFull(field.value)){
						obj["value"] = field.value + " 00:00:00";
					}
				}
			}
            condition.push(obj);
		}
	});

	setDataProcess(condition);

	$('attr-disabled').attr('disabled', 'disabled');
	$('attr-disabled').removeClass('attr-disabled');

	return condition;
}

function setDataProcess(condition){
    if (condition == undefined || condition == null){
        condition = [];
    }
    
    setDataButtonProcess(condition);
    
    // Comments
    let comments = {
        "name": "comments"
        , "value": $('#confirmNote').val()
    };
    condition.push(comments);

    let note = {
        "name": "note"
        , "value": $('#confirmNote').val()
    };
    condition.push(note);

    // process type
    let processType = {
        "name": "processType"
        , "value": 3
    };
    
    condition.push(processType);
}

function setDataButtonProcess(condition) {
/*    // Control action
    $("#action").val("submit");*/
    
    var actButtonType = $('#actButtonType').val();
    if (actButtonType == 'SUBMIT') {
        $("#isSubmit").val(true);
    }

    // Button ID
    let buttonId = {
        "name": "buttonId"
        , "value": $('#actButtonId').val()
    };
    condition.push(buttonId);
    
    let buttonText = {
        "name": "buttonText"
        , "value": $('#actButtonType').val()
    };
    condition.push(buttonText);
}