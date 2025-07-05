//let MSG_ERROR_MIN_SIZE_COMMON = '';
var PAGE_URL = 'order';
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


	initByLanguageList();

	createJValidate();
}

function createJValidate() {
//	createFunctionValidate("vietnamese-characters", function(value, element) {
//		var regex = new RegExp("^[0-9 aAàÀảẢãÃáÁạẠăĂằẰẳẲẵẴắẮặẶâÂầẦẩẨẫẪấẤậẬbBcCdDđĐeEèÈẻẺẽẼéÉẹẸêÊềỀểỂễỄếẾệỆfFgGhHiIìÌỉỈĩĨíÍịỊjJkKlLmMnNoOòÒỏỎõÕóÓọỌôÔồỒổỔỗỖốỐộỘơƠờỜởỞỡỠớỚợỢpPqQrRsStTuUùÙủỦũŨúÚụỤưƯừỪửỬữỮứỨựỰvVwWxXyYỳỲỷỶỹỸýÝỵỴzZaàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]*$");
//		if (regex.test(value)) {
//			return true;
//		}
//	}, ERROR_SPECIAL_CHARACTERS);
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
//		initImageUploader(key);

	});

}
function signatureImage(key) {

	var image = $("#physicalImg" + key).val();
	if (image != "") {
		$("#img_banner" + key).attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + image);
		$("#img_banner" + key).removeClass('hide');
	}
}
/**
 * IMAGE signature
 */

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



