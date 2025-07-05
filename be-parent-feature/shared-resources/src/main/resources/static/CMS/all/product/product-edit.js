//let MSG_ERROR_MIN_SIZE_COMMON = '';
var PAGE_URL = 'product';
$(document).ready(function($) {
	init();

//	// tabLanguage click
//	$('#tabLanguage a').click(function(e) {
//		e.preventDefault();
//		$(this).tab('show');
//	});
//
//	// set readonly code
//	if ($("#id").val() != "") {
//		$("#code").attr('readonly', 'readonly');
//	}
	// on click cancel
	$('#cancel, #linkList').on('click', function(event) {
		event.preventDefault();

		popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				var url = BASE_URL + PAGE_URL + "/list";

				// Redirect to page list
				ajaxRedirectWithCondition(url, setConditionSearch());
			}
		})
	});

	// on click add
//	$("#addNew").on("click", function(event) {
//		event.preventDefault();
//
//		popupConfirmWithButtons(MSG_ADD_NEW_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
//			if (result) {
//				var url = BASE_URL + PAGE_URL + "/edit";
//
//				// Redirect to page add
//				ajaxRedirectWithCondition(url, setConditionSearch());
//			}
//		})
//	});


	// on click button save
	$('#btnSave').on('click', function(event) {
		var validImage = $('#imageConsole0').attr('style');
		
		if ($(".j-form-validate").valid() && validImage != '') {
			var url = PAGE_URL + "/edit";


			//if($('.validateCharacters').valid()){

			//}
			var condition = $(".j-form-validate").serialize();
			ajaxSubmit(url, condition, event);
		} else {
			// show tab if exists error
			showTabError(LANGUAGE_LIST);

			rollToError('j-form-validate');
		}
	});
//	if($('#id').val()== ''){
//		$('#bannerDevice').val('1').trigger('change');
//		changtext();
//	}
//	$('#bannerType').on('change', function() {
//		checkShowImgAndVideo();
//	});
//	if ($('#bannerInputEnabled0').val() == true || isBlank($('#id').val())) {
//		$('#bannerInputEnabled0').prop('checked', 'checked')
//	}
//	$('#bannerDevice').on('change', function() {
//		changtext()
//	});
//	document.getElementById("titleImgPC").innerHTML = TEXT_DESKTOP;

});
function changtext() {
//	let bannerDevice = $('#bannerDevice').val();
//
//	if (bannerDevice == '2') {
//		document.getElementById("titleImgMobile").innerHTML = TEXT_MOBILE;
//		$('#titleImgMobile').show();
//		$('#titleImgPC').hide();
//
//		document.getElementById("titleMobileApp").innerHTML = TEXT_MOBILE_APP;
//		$('#titleMobileApp').show();
//
//	}
//	else {
//		document.getElementById("titleImgPC").innerHTML = TEXT_DESKTOP;
//		$('#titleImgPC').show();
//		$('#titleImgMobile').hide();
//
//		$('#titleMobileApp').hide();
//	}
}

function init() {
	var isDisabled = $('#hasEdit').val() == 'false';
	disabledAllField('bannerForm', isDisabled);

	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}

//	$('#bannerType, #bannerDevice').select2({ allowClear: false });

	changtext();
	
	changeDatetimepicker('effectiveDate', 'expiredDate');

	initByLanguageList();

	checkShowImgAndVideo();

	createJValidate();
}

function createJValidate() {
/*	createFunctionValidate("validateStartDate", function(value, element) {
		let today = new Date();

		if (today <=  converToDate(value)) {
			return true;
		}
	}, MSG_ERROR_POSTED_DATE);*/

	createFunctionValidate("validateExpiredDate", function(value, element) {
		let effectiveDate = $('#effectiveDate').val();
		if (!isNull(effectiveDate)) {
			if (value != '' && effectiveDate != '' && compareDate(effectiveDate, value) == -1) {
				return true;
			}
		}

		return this.optional(element);

	}, MSG_ERROR_EXPIRATION_DATE);

}

function checkShowImgAndVideo() {
	$('#divImg').show();
//	let bannerType = $('#bannerType').val();
//	if (bannerType == '1') {
//		$('#divImg').show();
//		$('#divVideo').hide();
//
//		$.each(LANGUAGE_LIST, function(key, val) {
//			$('#physicalImg' + key).addClass('j-required');
//			$('#physicalImg' + key + '-error').remove();
//			$('#physicalVideo' + key).removeClass('j-required');
//			$('#physicalVideo' + key + '-error').remove();
//
//			checkShowLinkYoutube(key);
//		});
//
//	} else {
//		$('#divVideo').show();
//		$('#divImg').hide();
//
//		$.each(LANGUAGE_LIST, function(key, val) {
//			$('#physicalImg' + key).removeClass('j-required');
//			$('#physicalImg' + key + '-error').remove();
//			$('#physicalVideo' + key).addClass('j-required');
//			$('#physicalVideo' + key + '-error').remove();
//
//			checkShowLinkYoutube(key);
//		});
//	}
}

/**
 * init by language list
 */
function initByLanguageList() {
	// lặp language list
	$.each(LANGUAGE_LIST, function(key, val) {
		// IMAGE signature
		signatureImage(key);

		// init image uploader
		initImageUploader(key);

	});

}

/**
 * IMAGE signature
 */
function signatureImage(key) {

	var image = $("#physicalImg" + key).val();
	if (image != "") {
		$("#img_banner" + key).attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + image);
		$("#img_banner" + key).removeClass('hide');
	}
}

/**
 * init image uploader
 */
function initImageUploader(key) {
	var requestToken = $("#requestToken").val();
	var uploadUrl = BASE_URL + PAGE_URL + "/uploadTemp?requestToken=" + requestToken;
	var imagePickfiles = 'imgPickfiles' + key;
	var imageContainer = 'imageContainer' + key;

	var imageMaxFileSize = '3mb';
	var resize = {};

	if ($('#bannerDevice').val() == 1) {
		imageMaxFileSize = '3mb';
		resize = {
		width: 1800,
		height: 350
		};
	} else {
		imageMaxFileSize = '1mb';
		resize = {
			width: 380,
			height: 130
		};
	}

	var imageMimeTypes = [{
		title: "Image files",
		extensions: "jpg,png,jpeg,jfif"
	}];

	var validateSize = function(up, file) {
		if (imageMaxFileSize < uploadUrl) {
			up.alert("max");
		}
	}

	var BeforeUpload = function(up, file) {
		if ($("#mobileEnable").is(':checked')) {
			up.settings.url = BASE_URL + PAGE_URL + "/uploadTemp?requestToken=" + requestToken;
		} else {
			up.settings.url = BASE_URL + PAGE_URL + "/uploadTemp?requestToken=" + requestToken;
		}
	};

	var Browse = function(up) {
		$('#physicalImg0').addClass('error');
		$('#physicalImg0').attr('aria-invalid', true);
		if ($('#bannerDevice').val() != 1) {
			up.settings.filters.min_width_x_min_height = {
				width: 380,
				height: 130
			};
			up.settings.filters.max_file_size = '1mb';
			ERROR_IMG_SIZE = ERROR_IMG_SIZE_1MB;

			MSG_ERROR_MIN_SIZE_COMMON = MSG_ERROR_MIN_SIZE_MOBILE;


		} else {
			up.settings.filters.min_width_x_min_height = {
				width: 1800,
				height: 350
			};
			up.settings.filters.max_file_size = '3mb';

			ERROR_IMG_SIZE = ERROR_IMG_SIZE_3MB;
			MSG_ERROR_MIN_SIZE_COMMON = MSG_ERROR_MIN_SIZE_DESKTOP;


		}
	};

	var imageFileList = 'imageFilelist' + key;
	var imageConsole = 'imageConsole' + key;
	var imageFileUploaded = function(up, file, info) {
		$("#bannerImg" + key).val(cutString(file.name));
		$("#physicalImg" + key).val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#physicalImg" + key).val();
		$("#img_banner" + key).attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + lstImg);
		$("#img_banner" + key).removeClass('hide');
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
		$("#physicalImg" + key).removeClass('error');
	};

	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
		imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL, null, resize, BeforeUpload, Browse);
}
/**
 * delete image
 */
function deleteImage(key) {
	$("#bannerImg" + key).val("");
	$("#physicalImg" + key).val("");
	$("#img_banner" + key).attr("src", "");
	$("#img_banner" + key).addClass('hide');
}

function setConditionSearch() {
	var condition = {};
	condition["name"] = $("#nameSearch").val();
	condition["code"] = $("#codeSearch").val();
	condition["status"] = $("#statusSearch").val();
	if ($("#bannerPC").is(':checked')) {
		condition["pcEnable"] = 0;
	}
	if ($("#bannerMobile").is(':checked')) {
		condition["mobileEnable"] = 1;
	}

	return condition;
}



