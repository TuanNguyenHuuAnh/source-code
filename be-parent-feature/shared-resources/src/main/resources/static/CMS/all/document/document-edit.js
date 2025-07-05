$(document).ready(function() {

	init();



	$("#linkList").on("click", function(event) {
		event.preventDefault();

		popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				var url = BASE_URL + PAGE_URL + "/list";
				// Redirect to page list
				ajaxRedirectWithCondition(url, setConditionSearch());
			}
		})
	});

	//on click add
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

	$('.document-file').on('click', function(event) {
		let value = $(this).attr('src');
		window.location.href = value;
	})

	$(".versionDownloadLink").on("click", function(event) {
		exportExcelDocumentVersion(this, event);
	});
	
	$('select[multiple]').multiselect({
		columns: 1,
		search: false,
		minHeight: 100,
		maxHeight: 155
	});
	
	if ($('#hasEdit').val() == 'false') {
		$("input[type=checkbox]").prop("disabled", true);
	}

});

function init() {
	// var isDisabled = $('#hasEdit').val() == 'false';
	// if (isDisabled) {
	// 	$('#fileContainer').addClass('not-active');
	// }
	// disabledAllField('formEdit', isDisabled);
	// //kiểm trả user có phân quyền admin hay không
	// if(isDisabled && $('#roleAdmin').val() != undefined && $('#roleAdmin').val() == 1){
	// 	$('#expirationDate').removeAttr('disabled')
	// 	$('#postedDate').removeAttr('disabled')
	// 	$('#enabled1').removeAttr('disabled')
	// }
	if( $('#hasEdit').val() == 'false'){
		disabledAllField('formEdit', true);
		$('#fileContainer').addClass('not-active');
	}
	if( (EDIT_DTO.statusCode == '999') || EDIT_DTO.statusCode == '994'){
		disabledAllField('formEdit', true);
		$('#fileContainer').addClass('not-active');
		if(EDIT_DTO.createBy === USER_LOGIN){
			$('input[name=enabled]').removeAttr('disabled');
			$('#postedDate').removeAttr('disabled');
			$('#expirationDate').removeAttr('disabled');
		}
		if($('#roleAdmin').val() != undefined && $('#roleAdmin').val() == 1 && $('#hasEdit').val() == 'true'){
			$('input[name=enabled]').removeAttr('disabled');
			$('#postedDate').removeAttr('disabled');
			$('#expirationDate').removeAttr('disabled');
		}
	}
	if( (EDIT_DTO.statusCode == 'taskWaitingApprove')){
		disabledAllField('formEdit', true);
		$('#confirmNote').removeClass('not-active')
		$('#fileContainer').addClass('not-active');
	}
	// if(EDIT_DTO.createBy !== USER_LOGIN && $('#roleAdmin').val() != 1){
	// 	disabledAllField('formId', true);
	// 	$('#confirmNote').removeClass('not-active')
	// }
	initFileUploader();

	//initImageUploader();
	//signatureImage();

	changeDatetimepicker('postedDate', 'expirationDate');

	// datepicker

	$('.datepicker-time').datetimepicker({
		format: 'DD/MM/yyyy HH:mm:ss',

	});

	$("#categoryId").select2({ allowClear: true });

	initLinkAliasSelectorEvent($('#name'), $('#link-alias'));

	// tabindex
	$("#tagBox input.type-zone").attr("tabindex", 9);
	var lstTextInput = $("input[type=text][name$='.title']");
	var lstTextSub = $("input[type=text][name$='.subTitle']");
	var lstTabLanguage = $("li[role=presentation][id^=tabLanguage]");
	var tabindex = 11;
	for (var i = 0; i < lstTextInput.length; i++) {
		$(lstTextInput[i]).attr("tabindex", tabindex++);
		$(lstTextSub[i]).attr("tabindex", tabindex++);

		// validate on blur
		$(lstTextInput[i]).blur(function() {
			$(this).valid();
		});
	}

	// tabindex button
	if ($("#addSaveDraft").length > 0) {
		$("#addSaveDraft").attr("tabindex", tabindex++);
		$("#addSubmit").attr("tabindex", tabindex++);
	} else {
		$("#editSaveDraft").attr("tabindex", tabindex++);
		$("#editSubmit").attr("tabindex", tabindex++);
	}

	// validate on blur and change tab
	var keyTab = '9';
	$(lstTextSub[0]).keydown(function(e) {
		var code = e.keyCode || e.which;
		if (code == keyTab) {
			$(lstTabLanguage[1]).find('a').tab('show');
		}
	});

	$(lstTabLanguage[1]).on('shown.bs.tab', function(e) {
		$(lstTextInput[1]).focus();
	})

	// hide combotree
	$("div[tabindex=3]").keydown(function(e) {
		var code = e.keyCode || e.which;
		if (code == keyTab) {
			$("#categoryId").valid();
			$("#categoryList").combotree('hidePanel');
		}
	});

	// show multiselect
	$('#link-alias').keydown(function(e) {
		var code = e.keyCode || e.which;
		if (code == keyTab) {
			$("div[tabindex=5]").find("div.ms-options").show();
		}
	});

	// hide multiselect	
	$("div[tabindex=5]").keydown(function(e) {
		var code = e.keyCode || e.which;
		if (code == keyTab) {
			$("#selViewAuthorities").valid();
			$(this).find("div.ms-options").hide();
		}
	});

	var lstTextInput = $("input[type=text][name$='.title']");
	var lstLinkAlias = $("input[type=text][name$='.linkAlias']");
	if (lstLinkAlias.length > 0) {
		initLinkAliasForListEvent($(lstTextInput[0]), lstLinkAlias);
	}

	var lstkeyword = $("input[type=text][name$='.keyword']");
	for (var i = 0; i < lstTextInput.length; i++) {
		initTagSelectorEvent($(lstTextInput[i]), $(lstkeyword[i]));
	}

	//check press enter
	$("input[type=text]").keypress("keydown", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
		}
	});

	//check press space
	$(lstLinkAlias[0]).keypress("keydown", function(e) {
		if (e.keyCode == 32) {
			var txtName = $(lstLinkAlias[0]).val();
			for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
				linkAlias = nameToLinkAlias(txtName);
				linkAlias = linkAlias + "-";
				$(lstLinkAlias[i]).val(linkAlias);
			}
			e.preventDefault();
		}
	});
	

	//check focusout
	/*
	$($("input[type=text][name$='.linkAlias']")).focusout(function(e) {
		var txtName = $(lstLinkAlias[0]).val();
		for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName);
			$(lstLinkAlias[i]).val(linkAlias);
		}
		e.preventDefault();
	});
*/
	$(lstLinkAlias[0]).on("change", function(e) {
		var txtName = $(lstLinkAlias[0]).val();
		for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName);
			linkAlias = linkAlias + "-";
			$(lstLinkAlias[i]).val(linkAlias);
		}
		e.preventDefault();
	});

	$(lstkeyword[0]).on("change", function(e) {
		var txtName = $(lstkeyword[0]).val();
		for (var i = 0, sz = lstkeyword.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName).replaceAll('-', '');
			//linkAlias = i == 0 ? "#" + linkAlias : + linkAlias;
			$(lstkeyword[i]).val(linkAlias);
		}
		e.preventDefault();
	});

	clearErrorWhenMove('j-required', 'form-control');
	clearErrorWhenMove('datepicker-time', 'form-control');
	clearErrorWhenMove('form-control', 'form-control');

	createJValidate();
}

function createJValidate() {
	/*createFunctionValidate("validatePostedDate", function(value, element) {
		let today = new Date();
		let todayForMat = moment(today).format('DD/MM/YYYY HH');
		moment(value).format('DD/MM/YYYY HH');

		if (todayForMat <= value) {
			return true;
		}
	}, MSG_ERROR_POSTED_DATE);*/

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

function exportExcelDocumentVersion(element, event, row) {
	event.preventDefault();

	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}
	var id = $("#id").val();
	var version = row.data("version-id");

	var url = BASE_URL + PAGE_URL + "/download/file?id=" + id + '&version=' + version;

	//href
	window.location.href = url;
}

/**
 * init file uploader
 */
function initFileUploader() {
	var uploadUrl = BASE_URL + PAGE_URL + "/uploadTemp";
	var files = 'filePick';
	var fileContainer = 'fileContainer';
	var fileMaxFileSize = '15mb';

	var fileMimeTypes = [{
		title: "Document and PowerPoint files",
		extensions: "doc,docx,xls,xlsx,pdf,ppt,pptx"
	}];

	var fileFileList = 'fileFilelist';
	var fileConsole = 'fileConsole';

	var fileFileUploaded = function(up, file, info) {
		$("#fileName").val(cutString(file.name));
		$("#fileSize").val(cutString(file.size));
		let names = cutString(file.name).split('.');
		let type = names[names.length - 1];
		$("#fileType").val(type);
		$("#physicalFileName").val(cutString(info.response));
		$("#viewFileName").val(file.name);
		$("#tempFileUrl").val(cutString(info.response));
	};

	var fileUploadComplete = function(up, files) {
		var physicalFileName = $("#physicalFileName").val();
		$("#fileUrl").attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + physicalFileName);
		$("#fileId").val('');
		$('#fileUrl').find('span').remove();
		let html = '<span class="title-file">' + $("#fileName").val() + '</span>'
		$("#fileUrl").append(html);
		$("#" + fileConsole).hide();
		$("#" + fileFileList).hide();
	};

	InitPlupload(files, fileContainer, uploadUrl, false, fileMaxFileSize, fileMimeTypes, fileFileList,
		fileConsole, fileFileUploaded, fileUploadComplete, BASE_URL);
}

function deleteFile() {
	$("#viewFileName").val("");
	$("#fileName").val("");
	$("#filePhysicalName").val("");
}

function deleteImage() {
	$("#imageUrl").val("");
	$("#image_preview").attr("src", "");
	$("#image_preview").addClass('hide');
}

function processSearchTagData() {
	var tags = $('#tagBox').tagging("getTags");
	$('#hiddenSearchTag').val(tags);
}

function setConditionSearch() {
	var condition = {};
	try {
		if ($("#searchDto").val() != '') {
			var tmp = JSON.parse($("#searchDto").val());
			condition["name"] = tmp.name;
			condition["code"] = tmp.code;
			condition["status"] = tmp.status;
			condition["enabled"] = tmp.enabled;
			condition["typeId"] = tmp.typeId;
		}
	} catch (error) {
		console.error(error);
	}
	return condition;
}

function initTagSelectorEvent(nameSelector, linkAliasSelector) {
  nameSelector.keyup(function (event) {
    var k = event.which;
    // Verify that the key entered is not a special key
    if (
      k == 20 /* Caps lock */ ||
      k == 16 /* Shift */ ||
      k == 9 /* Tab */ ||
      k == 27 /* Escape Key */ ||
      k == 17 /* Control Key */ ||
      k == 91 /* Windows Command Key */ ||
      k == 19 /* Pause Break */ ||
      k == 18 /* Alt Key */ ||
      k == 93 /* Right Click Point Key */ ||
      (k >= 35 && k <= 40) /* Home, End, Arrow Keys */ ||
      k == 45 /* Insert Key */ ||
      (k >= 33 && k <= 34) /*Page Down, Page Up */ ||
      (k >= 112 && k <= 123) /* F1 - F12 */ ||
      (k >= 144 && k <= 145)
    ) {
      /* Num Lock, Scroll Lock */
	} else {
		var txtName = $(nameSelector).val();
		linkAlias = nameToTag(txtName);
		linkAliasSelector.val(linkAlias);
		
		$(nameSelector).parent().find('label.error').remove();
		
		$("input[type=text][name$='.keywordsSeo']").removeClass('error');
		$("input[type=text][name$='.keywordsSeo']").parent().find('label.error').remove();
		
		$("input[type=text][name$='.linkAlias']").removeClass('error');
		$("input[type=text][name$='.linkAlias']").parent().find('label.error').remove();
	}
  });
}

function nameToTag(strName){
    strName = strName.substring(0, 100);
    var keyword = removeDiacritics(strName.toLowerCase()).replace(/[^a-zA-Z\d\s-]+/gi,'').replace(/[^a-zA-Z\d\s]+/gi,' ');
    keyword = keyword.replace(/ /g,"");
    return (keyword == null || keyword == "") ? "" : "#" + keyword;
}
