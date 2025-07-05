$(document).ready(function($) {
	init();
	$('.maxLengthTextArea').hide();

	// var editAbstract=CKEDITOR.instances.editor;
	//
	// editAbstract.on("key",function(e) {
	//
	// 	var maxLength=e.editor.config.maxlength;
	//
	// 	e.editor.document.on("keyup",function() {KeyUp(e.editor,maxLength,"letterCount");});
	// 	e.editor.document.on("paste",function() {KeyUp(e.editor,maxLength,"letterCount");});
	// 	e.editor.document.on("blur",function() {KeyUp(e.editor,maxLength,"letterCount");});
	// },editAbstract.element.$);


//	if ($('#id').val() == '') {
//		$('#radio1').prop('checked', true);
//	}
	
	if ($('#territory').data('territory') != undefined && $('#territory').data('territory') != '') {
		var lstTerritory = ''+$('#territory').data('territory');
	}
	if ($('#region').data('region') != undefined && $('#region').data('region') != '') {
		var lstRegion =  ''+$('#region').data('region');
	}
	if ($('#area').data('area') != undefined && $('#area').data('area') != '') {
		var lstArea =  ''+$('#area').data('area');
	}
	if ($('#office').data('office') != undefined && $('#office').data('office') != '') {
		var lstOffice =  ''+$('#office').data('office');
	}
	if ($('#type').data('type') != undefined && $('#type').data('type') != '') {
		var lstType =  ''+$('#type').data('type');
	}
	var PAGE_URL = "notify"
	$('#code').val("default");
	initialSelect2();
	initialDatepicker();
	if (isBlank($('#id').val())) {
		$('#active1').prop('checked', true);
	}
	$('#sendImmediately1').on('change', function() {
		if ($(this).is(":checked")) {
			var date = currentDate();
			$('#sendDate').val(date).trigger('change').prop('disabled', true);
		} else {
			$('#sendDate').prop('disabled', null);
		}
	})
	$('#cancel, #linkList').on('click', function(event) {
		event.preventDefault();

		popupConfirm(MSG_BACK_CONFIRM, function(result) {
			if (result) {
				var url = BASE_URL + PAGE_URL + "/list";
				// Redirect to page list
				ajaxRedirect(url);
			}
		});
	});
	
	// on click add
	$("#addNew").on("click", function(event) {
		event.preventDefault();
		
		popupConfirmWithButtons(MSG_ADD_NEW_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				let url = BASE_URL + PAGE_URL + "/edit";
				// Redirect to page add
				ajaxRedirectWithCondition(url, setConditionSearch());
			}
		})
	});

	$('input[name="applicableObject"]').on('change', function() {		
		if ($('#radio3').is(":checked")) {
			$('#conditionalView').prop('hidden', null);
			$('#btnSave').prop('disabled', null);
		} else {
			$('#conditionalView').prop('hidden', 'hidden');
		}
		
		if ($('#radio2').is(":checked") ||  $('#radio4').is(":checked")) {
			
			$('#fmUpload').prop('hidden', null);
			if(IS_ERROR){
				$('#btnSave').prop('disabled', 'disabled');
			}
		} else {
			$('#fmUpload').prop('hidden', 'hidden');
			$('#btnSave').prop('disabled', null);
		}
		
	    handleRadio4Selected($('#radio4').is(":checked"));
	    renderTableImport();
	   
	}).trigger('change');

	$('#btnSave').on('click', function(event) {
		updateElementEditor();
		if ($(".j-form-validate").valid()) {
			var url = PAGE_URL + "/submit-import";
			$('#sendDate').prop('disabled', false);
			var condition = $(".j-form-validate").serialize();
			$('#sendDate').prop('disabled', true);
			ajaxSubmit(url, condition, event);
			
		}else {
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
			
			rollToError('j-form-validate');
		}
	});

	$('#territory').on('change', function() {
		$('#region').val('').trigger('change');
		$('#area').val('').trigger('change');
		$('#office').val('').trigger('change');
		aJaxRegion(lstRegion);
		aJaxArea(lstArea);
		aJaxOffice(lstOffice)
	});

	$('#region').on('change', function() {
		$('#area').val('').trigger('change');
		$('#office').val('').trigger('change');
		aJaxArea(lstArea)
		aJaxOffice(lstOffice)
	});

	$('#area').on('change', function() {
		$('#office').val('').trigger('change');
		aJaxOffice(lstOffice)
	});

	if ($('#territory').data('territory') != undefined && $('#territory').data('territory') != '') {
		$('#territory').val($('#territory').data('territory').split(","));
		$('#territory').trigger('change');
	}
	if ($('#region').data('region') != undefined && $('#region').data('region') != '') {
		$('#region').val($('#region').data('region').split(","));
		$('#region').trigger('change');
	}
	if ($('#area').data('area') != undefined && $('#area').data('area') != ''){
		$('#region').val($('#area').data('area').split(","));
		$('#area').trigger('change');
	}
	if ($('#type').data('type') != undefined && $('#type').data('type') != '') {
		$('#type').val($('#type').data('type').split(","));
		$('#type').trigger('change');
	}

	if(IS_ERROR){
		$('#btnSave').prop('disabled', 'disabled');
	} else {
		$('#btnSave').prop('disabled', null);
	}

	createJValidate();
	// select All radio onloadPage
	setTimeout(selectAllOnLoadpage(), 1000);
	setTimeout(setBindingForSendDate(), 1500);
});

function selectAllOnLoadpage() {	
	if ($('#id').val() == '') {		
		if($('button.close[data-dismiss="alert"]').is(':visible')) {
			$('#radio4').prop('checked', true);
		} else {			
			$('#radio1').prop('checked', true);
		}
	}
}

 function handleRadio4Selected(mode) {
	if (mode) {  
		if(editor && editor.status === 'ready') {			
			editor.setReadOnly(true);                         		
		} 
		$('#content-label').removeClass('required');
		$('#editor').removeClass('j-required');
		editor.setData('');
		$('#tie').removeClass('required');
		$('#titless').removeClass('j-required');
		$('#titless').val("");
		$('#titless').prop('disabled', true);
	} else {
		if(editor && editor.status === 'ready') {			
			editor.setReadOnly(false);                         		
		} 
		$('#content-label').addClass('required');
		$('#editor').addClass('j-required');
		$('#tie').addClass('required');
		$('#titless').addClass('j-required');
		$('#titless').prop('disabled', false);
	}	
	
	
}

function resetGridDataUpload() {
	$.ajax({
        url : BASE_URL + 'notify-import-controller/reset-grid-data-upload',
        type: 'POST',
        data : setConditionSearchImport,
        success:function(data) {			
        	$("#tableList").html(data);   
        	renderTableImport();     	
        }
    });
	
}


function createJValidate() {
	createFunctionValidate("validateExpirationDate", function(value, element) {
		let postedDate = $('#postedDate').val();
		if (!isNull(postedDate)) {
			if (value != '' && postedDate != '' && compareDate(postedDate, value) == -1) {
				return true;
			}
		}

		return this.optional(element);

	}, MSG_ERROR_EXPIRATION_DATE);
}

function setConditionSearch(){
	var condition = {};
	let value = $("#searchDto").val();

	if (!isNull(value)){
		var tmp = JSON.parse($("#searchDto").val());
		condition["code"] = tmp.code;
		condition["title"] = tmp.title
		condition["status"] = tmp.status
		condition["enabled"] = tmp.enabled
		condition["faqsTypeId"] = tmp.faqsTypeId
	}
	return condition;
}
function init(){
	//view edit
	if($('#sendImmediately1').is(":checked")){
		$('#sendDate').prop('disabled', true);
	}
	if(!$('#sendImmediately1').is(":checked")){
		$('#sendDate').prop('disabled', false);
	}
	//view detail
	var isDisabled = $('#hasEdit').val() == 'false';
	disabledAllField('newsForm', isDisabled);

	// show tab if exists error
	showTabError(LANGUAGE_LIST);

	createJValidate();
}
function createJValidate() {
	/*createFunctionValidate("validateSendDate", function(value, element) {
		var toDay = new Date();
		var toDayString=setTime(toDay);
		var toDayDate = converToDate(toDayString);
		
		var valueDate01 = converToDate(value);
		var valueString=setTime(valueDate01);
		var valueDate = converToDate(valueString);
		
		if(valueDate >= toDayDate ){
			return true;
		}
	}, MSG_ERROR_SEND_DATE);*/
}

function initialDatepicker() {
	// $('.datepicker').datepickerUnit({
	// 	format: DATE_FORMAT,
	// 	viewMode : "days",
	// 	minViewMode : "days",
	// 	autoclose : true,
	// 	language : APP_LOCALE.toLowerCase(),
	// 	todayHighlight : true,
	// 	startDate : new Date()
	// });
	$('#sendDate').datetimepicker({
		format: 'DD/MM/YYYY HH:mm:ss',
		minDate : new Date()
	});	
	
	$('.datepicker > input').attr("placeholder", "dd/mm/yyyy HH:mm:ss");
}
function setBindingForSendDate() {	
	 if($("#sendDateHidd").val() !== '') {		   
		const formattedDate = formatDateFromString($('#sendDateHidd').val());
		$("#sendDate").val(formattedDate);
	}
}

function formatDateFromString(dateStr) {
    // Parse the date string into components
    const dateParts = dateStr.split(' ');
    const monthMap = {
        Jan: '01', Feb: '02', Mar: '03', Apr: '04', May: '05', Jun: '06',
        Jul: '07', Aug: '08', Sep: '09', Oct: '10', Nov: '11', Dec: '12'
    };

    const day = dateParts[2];
    const month = monthMap[dateParts[1]];
    const year = dateParts[5];
    const time = dateParts[3];

    // Format the date as DD/MM/YYYY HH:mm:ss
    return `${day}/${month}/${year} ${time}`;
}


function initialSelect2() {
	$('#territory').select2({
		placeholder: 'Select',
		allowClear: true
	});
	$('#region').select2({
		placeholder: 'Select',
		allowClear: true
	});
	$('#area').select2({
		placeholder: 'Select',
		allowClear: true
	});
	$('#office').select2({
		placeholder: 'Select',
		allowClear: true
	});
	$('#type').select2({
		placeholder: 'Select',
		allowClear: true
	});
	$('#reporter').select2({
		placeholder: 'Select',
		allowClear: true
	});

}


function aJaxRegion(lstRegion) {
	var optionHtml = '';
	var territory = $('#territory').data('territory') != undefined ? $('#territory').data('territory') : $('#territory').val().join(",");
	$.get(BASE_URL + 'common/region?territory=' + territory, {}, function(res) {
		$.each(res, function() {
			if (lstRegion != undefined) {
				if (lstRegion.indexOf(this.id) != -1) {
					optionHtml += '<option value="' + this.id + '" selected ="selected"' + '>' + this.name + '</option>'
				} else {
					optionHtml += '<option value="' + this.id + '">' + this.name + '</option>'
				}
			} else {
				optionHtml += '<option value="' + this.id + '">' + this.name + '</option>'
			}
		})
		$('#region').html(optionHtml);
	});
}
function aJaxArea(lstArea) {
	var optionHtml = '';
	var territory = $('#territory').data('territory') != undefined ? $('#territory').data('territory') : $('#territory').val().join(",");
	var region = $('#region').data('region') != undefined ? $('#region').data('region') : $('#region').val().join(",");
	$.get(BASE_URL + 'common/area?territory=' + territory + '&region=' + region, {}, function(res) {
		$.each(res, function() {
			if (lstArea != undefined) {
				if (lstArea.indexOf(this.id) != -1) {
					optionHtml += '<option value="' + this.id + '" selected ="selected"' + '>' + this.name + '</option>';
				} else {
					optionHtml += '<option value="' + this.id + '">' + this.name + '</option>'
				}
			} else {
				optionHtml += '<option value="' + this.id + '">' + this.name + '</option>'
			}
		})
		$('#area').html(optionHtml);
	});
}
function aJaxOffice(lstOffice) {
	var optionHtml = '';
	var territory = $('#territory').data('territory') != undefined ? $('#territory').data('territory') : $('#territory').val().join(",");
	var region = $('#region').data('region') != undefined ? $('#region').data('region') : $('#region').val().join(",");
	var area = $('#area').data('area') != undefined ? $('#area').data('area') : $('#area').val().join(",");
	$.get(BASE_URL + 'common/office?territory=' + territory + '&region=' + region + '&area=' + area, {}, function(res) {
		$.each(res, function() {
			if (lstOffice != undefined) {
				if (lstOffice.indexOf(this.id) != -1) {
					optionHtml += '<option value="' + this.id + '" selected ="selected"' + '>' + this.name + '</option>';
				} else {
					optionHtml += '<option value="' + this.id + '">' + this.name + '</option>'
				}
			} else {
				optionHtml += '<option value="' + this.id + '">' + this.name + '</option>'
			}
		})
		$('#office').html(optionHtml);
	});
}

function currentDate(){
	let currentDate = new Date();
	let date = converDateToString(currentDate);
	let hours = currentDate.getHours();
	let minutes = currentDate.getMinutes();
	let seconds = currentDate.getSeconds();
	return date + " " + hours + ":" + minutes + ":" + seconds;
}

function setTime(datetimeParam){
	let date = converDateToString(datetimeParam);
	let hours = datetimeParam.getHours();
	let minutes = 00;
	let seconds = 00;
	return date + " " + hours + ":" + minutes + ":" + seconds;
}
function updateElementEditor() {
	for (instance in CKEDITOR.instances) {
		CKEDITOR.instances[instance].updateElement();
	}
}
//function to handle the count check
// function KeyUp(editorID,maxLimit,infoID) {
//
// 	//If you want it to count all html code then just remove everything from and after '.replace...'
// 	var text=editorID.getData().replace(/<("[^"]*"|'[^']*'|[^'">])*>/gi, '').replace(/^\s+|\s+$/g, '');
// 	$("#"+infoID).text(text.length);
//
// 	if(text.length>maxLimit) {
// 		$('.maxLengthTextArea').show();
// 		editorID.setData(text.substr(0,maxLimit));
// 	} else if (text.length==maxLimit-1) {
// 		alert("WARNING:\nYou are one character away from your limit.\nIf you continue you could lose any formatting");
// 	}
// }
