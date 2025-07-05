//let MSG_ERROR_MIN_SIZE_COMMON = '';
var PAGE_URL = 'banner';
$(document).ready(function($) {
	init();

	// tabLanguage click
	$('#tabLanguage a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	$('select[multiple]').multiselect({
		columns: 1,
		search: false,
		minHeight: 100,
		maxHeight: 155
	});

	// set readonly code
	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}

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
	$("#addNew").on("click", function(event) {
		event.preventDefault();

		popupConfirmWithButtons(MSG_ADD_NEW_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				var url = BASE_URL + PAGE_URL + "/edit";

				// Redirect to page add
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
	if($('#id').val()== ''){
		$('#bannerDevice').val('1').trigger('change');
		changtext();
	}
	$('#bannerType').on('change', function() {
		checkShowImgAndVideo();
	});
	if ($('#bannerInputEnabled0').val() == true || isBlank($('#id').val())) {
		$('#bannerInputEnabled0').prop('checked', 'checked')
	}
	$('#bannerDevice').on('change', function() {
		changtext()
	});
	document.getElementById("titleImgPC").innerHTML = TEXT_DESKTOP;
	
	if ($('#hasEdit').val() == 'false') {
		$("input[type=checkbox]").prop("disabled", true);
	}

});
function changtext() {
	let bannerDevice = $('#bannerDevice').val();

	if (bannerDevice == '2') {
		document.getElementById("titleImgMobile").innerHTML = TEXT_MOBILE;
		$('#titleImgMobile').show();
		$('#titleImgPC').hide();

		document.getElementById("titleMobileApp").innerHTML = TEXT_MOBILE_APP;
		$('#titleMobileApp').show();

	}
	else {
		document.getElementById("titleImgPC").innerHTML = TEXT_DESKTOP;
		$('#titleImgPC').show();
		$('#titleImgMobile').hide();

		$('#titleMobileApp').hide();
	}
}

function init() {
	var isDisabled = $('#hasEdit').val() == 'false';
	disabledAllField('bannerForm', isDisabled);

	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}
	
	$('#bannerType, #bannerDevice').select2({ allowClear: false });

	changtext();


	initByLanguageList();

	checkShowImgAndVideo();

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

function checkShowImgAndVideo() {
	let bannerType = $('#bannerType').val();
	if (bannerType == '1') {
		$('#divImg').show();
		$('#divVideo').hide();

		$.each(LANGUAGE_LIST, function(key, val) {
			$('#physicalImg' + key).addClass('j-required');
			$('#physicalImg' + key + '-error').remove();
			$('#physicalVideo' + key).removeClass('j-required');
			$('#physicalVideo' + key + '-error').remove();

			checkShowLinkYoutube(key);
		});

	} else {
		$('#divVideo').show();
		$('#divImg').hide();

		$.each(LANGUAGE_LIST, function(key, val) {
			$('#physicalImg' + key).removeClass('j-required');
			$('#physicalImg' + key + '-error').remove();
			$('#physicalVideo' + key).addClass('j-required');
			$('#physicalVideo' + key + '-error').remove();

			checkShowLinkYoutube(key);
		});
	}
}

/**
 * init by language list
 */
function initByLanguageList() {
	// lặp language list
	$.each(LANGUAGE_LIST, function(key, val) {
		// IMAGE signature
		signatureImage(key);

		// VIDEO signature
		signatureVideo(key);

		// init image uploader
		initImageUploader(key);

		// init video uploader
		initVideoUploader(key);

		checkShowLinkYoutube(key);

		$('#bannerYoutubeVideo' + key).on('change', function() {
			changeLinkYoutube(key);
		});

		$('#bannerVideoType' + key).on('change', function() {
			checkShowLinkYoutube(key);
		});
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
 * Video signature
 */
function signatureVideo(key) {
	var fileName = $("#physicalVideo" + key).val();
	console.log(fileName);
	if (fileName != "") {
		loadVideoPLayer(fileName, key);
		$("#videoContent" + key).show();
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
 * init video uploader
 */
function initVideoUploader(key) {
	var videoPickfiles = 'videoPickfiles' + key;
	var videoContainer = 'videoContainer' + key;
	var uploadUrl = BASE_URL + "cmsAjax/uploadVideo";

	var videoMaxFileSize = '10mb';

	var videoMimeTypes = [{
		title: "Video files",
		extensions: "avi,wma,mp4,flv"
	}];
	var videoFileList = 'videoFilelist' + key;
	var videoConsole = 'videoConsole' + key;
	var videoFileUploaded = function(up, file, info) {
		$("#bannerVideo" + key).val(cutString(file.name));
		$("#physicalVideo" + key).val(cutString(info.response));
	};

	var videoUploadComplete = function(up, files) {
		// play video
		var fileName = $("#physicalVideo" + key).val();

		loadVideoPLayer(fileName, key);

		$("#" + videoConsole).hide();
		$("#" + videoFileList).hide();
		$("#videoContent" + key).show();
	};


	InitPlupload(videoPickfiles, videoContainer, uploadUrl, false, videoMaxFileSize, videoMimeTypes, videoFileList,
		videoConsole, videoFileUploaded, videoUploadComplete, BASE_URL);
}

function loadVideoPLayer(fileName, key) {
	console.log(fileName.split('.').pop());
	var htmlVideo = '<video id="videoContent' + key + '" muted class="videoContent asd" controls="controls" style="width:100%"> '
		+ '<source src="' + BASE_URL + 'cmsAjax/download?fileName=' + fileName + '" type="video/mp4" /> '
		+ '<source src="' + BASE_URL + 'cmsAjax/download?fileName=' + fileName + '" type="video/ogg" /> '
		+ '<source src="' + BASE_URL + 'cmsAjax/download?fileName=' + fileName + '" type="video/x-flv" /> '
		+ '<source src="' + BASE_URL + 'cmsAjax/download?fileName=' + fileName + '" type="video/webm" /> '
		+ '</video>';
	// set html wrap_video
	$("#wrap_video" + key).find('#wrap_object' + key).html('').html(htmlVideo);
	
	if (flvjs.isSupported() && fileName.split('.').pop() == 'flv') {
		var videoElement = document.getElementById('videoContent' + key);
		console.log('dada', videoElement);
		var flvPlayer = flvjs.createPlayer({
			type: 'flv',
			url:  BASE_URL + 'cmsAjax/download?fileName=' + fileName
		});
		flvPlayer.attachMediaElement(videoElement);
		flvPlayer.load();
		flvPlayer.pause();
	}
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

/**
 * delete video
 */
function deleteVideo(key) {
	$("#bannerVideo" + key).val("");
	$("#physicalVideo" + key).val("");
	$("#wrap_video" + key).find('object').remove();
	$("#wrap_video" + key).find('#wrap_object' + key).html('').html(
		'<div id="videoContent' + key + '" class="videoContent" controls="controls"> </div>');
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

function changeLinkYoutube(key) {
	let link = $('#bannerYoutubeVideo' + key).val();

	addYoutubeVideoToHtml('divPhysicalYoutubeVideo' + key, 'bannerPhysicalYoutubeVideo' + key, link);
	link = getEmbedFromYoutubeLink(link);
}

function checkShowLinkYoutube(key) {
	let checked = $('#bannerVideoType' + key).is(":checked");
	let style = $('#divVideo').attr('style');

	if (style != 'display: none;') {
		if (checked) {
			$('#divVideoYoutube').show();
			$('#divVideoPhysical').hide();

			$('#bannerYoutubeVideo' + key).addClass('j-required');
			$('#physicalVideo' + key).removeClass('j-required');

			$('#bannerVideoType' + key).val(2);

			changeLinkYoutube(key);
		} else {
			$('#divVideoYoutube').hide();
			$('#divVideoPhysical').show();

			$('#bannerYoutubeVideo' + key).removeClass('j-required');
			$('#physicalVideo' + key).addClass('j-required');

			$('#bannerVideoType' + key).val(1);
		}
	} else {
		$('#bannerYoutubeVideo' + key).removeClass('j-required');
		$('#physicalVideo' + key).removeClass('j-required');
		$('#bannerVideoType' + key).val('');
	}
}

