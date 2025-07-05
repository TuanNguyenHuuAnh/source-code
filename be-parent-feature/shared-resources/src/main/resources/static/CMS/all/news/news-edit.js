var customerImageId;
var PAGE_URL = CUSTOMER_ALIAS + '/promotion';


$(document).ready(function($) {
	init();
	$.each(LANGUAGE_LIST, function(key, val) {
		$("#newsLanguageList"+key+"\\.title").keyup(function() {
			if($("#newsLanguageList"+key+"\\.keyWord").val() != ""){
				$("#newsLanguageList"+key+"\\.keyWord-error").hide();
				$("#newsLanguageList"+key+"\\.keyWord").removeClass('error');
			}
			
		});
	});
	$.each(LANGUAGE_LIST, function(key, val) {
		$("#imgPickfilesUrl"+key).on('click', function() {
			if ($("#imageConsoleUrl" + key).text() != null) {
				$("#physicalImgUrl"+key+"-error").hide();
				$("#physicalImgUrl"+key+"-error").removeClass('error');
			}
			
		});
	});
	// tabLanguage click
	$('#tabLanguage a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}
	
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
	
	$("#typeId").change(function() {
		newTypeChange(this);
	});
	
	//on click cancel
	$('#cancel, #linkList').on('click', function(event) {
		event.preventDefault();
		
		popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				var url = BASE_URL + PAGE_URL + "/list";
				// Redirect to page list
				ajaxRedirectWithCondition(url, setConditionSearch());
			}
		});
	});
	
	// on click saveDraft
	$('#btnSave').on('click', function(event) {
		updateElementEditor();
		if ($("#promotion").is(":checked")) {
			$(".tab-content").find(".gift-message").find("input").val();
		}else{
			$(".tab-content").find(".gift-message").find("input").val('');
		}

		if ($(".j-form-validate").valid()) {
			var url = PAGE_URL + "/edit";
			var condition = $("#newsForm").serialize();

			ajaxSubmit(url, condition, event);
		} else {
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
			
			rollToError('j-form-validate');
		}
		
		return false;
	});
	
	var idEffectedDate = $("#effectiveDate").val();
	var idExpiredDate = $("#expirationDate").val();
	changeDatepicker(idEffectedDate, idExpiredDate);
	
	// tabindex button
	if ($("#saveDraft").length > 0) {
		$("#saveDraft").attr("tabindex", tabindex++);
		$("#sendRequest").attr("tabindex", tabindex++);
	}
	
	// validate on blur
	$("#name, #custTypeId, #link-alias, #typeId").blur(function(){
		$(this).valid();
	});
	
	//check press enter
	$("input[type=text]").keypress("keydown",function(e){
		if (e.keyCode == 13) {
			e.preventDefault();
		}
	});
	
		
	var icon = $("#physicalImgUrl0").val();
	if (icon == "") {
		deleteImageUrl(0);
	}
	
	if ($('#hasEdit').val() == 'false') {
		$("input[type=checkbox]").prop("disabled", true);
	}
});

function init(){
	var isDisabled = $('#hasEdit').val() == 'false';
	disabledAllField('newsForm', isDisabled);
	
	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}
		
	$("#typeId, #categoryId").select2({ allowClear : true });
	$('#typeId').on("select2:close", function (e) {
		let id = $(this).attr('id');
		$(this).removeClass('error');
		findAndRemoveError(id);
	});
	$('#categoryId').on("select2:close", function (e) {
		let id = $(this).attr('id');
		$(this).removeClass('error');
		findAndRemoveError(id);
	});
	
	// init by language list
	initByLanguageList();
	
	// show tab if exists error
	showTabError(LANGUAGE_LIST);
	
	// datepicker
	$('.datepicker-time').datetimepicker({
		format: 'DD/MM/yyyy HH:mm:ss',	
	});

	/*$('.expirationDate').datetimepicker({
		format: 'DD/MM/yyyy HH:mm:ss',
	});
	
	$('.postedDate').datetimepicker({
		format: 'DD/MM/yyyy HH:mm:ss',
		defaultDate: new Date(),
	});
	*/
	$('select[multiple]').multiselect({
		columns: 1,
		search: false,
		minHeight: 100,
		maxHeight: 155
	});

	initLinkAliasSelectorEvent($('#name'), $('#link-alias'));
	
	// tabindex
	var lstTextInput = $("input[type=text][name$='.title']");
	var lstLinkAlias = $("input[type=text][name$='.linkAlias']");
	if (lstLinkAlias.length > 0) {
		initLinkAliasForListEvent($(lstTextInput[0]), lstLinkAlias);
	}
	
	
	var lstkeyword = $("input[type=text][name$='.keyWord']");
	for (var i = 0; i < lstTextInput.length; i++) {
		initTagSelectorEvent($(lstTextInput[i]), $(lstkeyword[i]));
	}
	
	// tabindex
	var lstTextInput = $("input[type=text][name$='.title']");
	var lstTextSub = $("textarea[name$='.shortContent']");
	var lstEditor = $("textarea[id^='editor']");
	var lstGiftMessage = $("input[type=text][name$='.giftMessage']");
	var lstTabLanguage = $("li[role=presentation][id^=tabLanguage]");
	var tabindex = 17;
	for (var i = 0; i < lstTextInput.length; i++) {
		$(lstTextInput[i]).attr("tabindex", tabindex++);
		$(lstTextSub[i]).attr("tabindex", tabindex++);
		$(lstEditor[i]).closest("div.editorDiv").attr("tabindex", tabindex++);
		$(lstGiftMessage[i]).attr("tabindex", tabindex++);
	}
	
	// validate on blur and change tab
	var keyTab = '9';
	$(lstGiftMessage[0]).keydown(function(e){	
		var code = e.keyCode || e.which;
	    if (code == keyTab) {
	    	$(lstTabLanguage[1]).find('a').tab('show');
	    }
	});
	
	$(lstTabLanguage[1]).on('shown.bs.tab', function (e) {
		$(lstTextInput[1]).focus();
	})
	
	//check press space
	$(lstLinkAlias[0]).keypress("keydown",function(e){
		if (e.keyCode == 32) {
			var txtName = $(lstLinkAlias[0]).val();
			for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
				linkAlias = nameToLinkAlias(txtName);
				//linkAlias = linkAlias + "-";
				$(lstLinkAlias[i]).val(linkAlias);
			}
			e.preventDefault();
		}
	});
	
	//check focusout
	$($("input[type=text][name$='.linkAlias']")).focusout(function(e) {
		var txtName = $(lstLinkAlias[0]).val();
		for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName);
           //linkAlias = linkAlias + "-";
			$(lstLinkAlias[i]).val(linkAlias);
		}
		e.preventDefault();
	});

	$(lstLinkAlias[0]).on("change",function(e){
		var txtName = $(lstLinkAlias[0]).val();
		for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName);
			//linkAlias = linkAlias + "-";
			$(lstLinkAlias[i]).val(linkAlias);
		}
		e.preventDefault();
	});
	
	$(lstkeyword[0]).on("change",function(e){
		var txtName = $(lstkeyword[0]).val();
		for (var i = 0, sz = lstkeyword.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName).replaceAll('-', '');
			//linkAlias = i == 0 ? "#" + linkAlias : + linkAlias;
			$(lstkeyword[i]).val(linkAlias);
		}
		e.preventDefault();
	});
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

	}, MSG_ERROR_EXPIRATION_DATE_NEWS);
}

/**
 * init by language list
 */
function initByLanguageList() {
	
	// initVideoUploader();
	
	// signatureVideo();
	
	// lặp language list
	$.each(LANGUAGE_LIST, function(key, val) {
		// IMAGE signature
		signatureImage(key);
		
		initImageUploader(key);
		
		// init image uploader url
		initImageUploaderUrl(key);
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
		$("#img_banner" + key).addClass('wrap_img');
	}
	
	var imageMobile = $("#physicalImgMobile" + key).val();
	if (imageMobile != "") {
		$("#img_bannerMobile" + key).attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + imageMobile);
		$("#img_bannerMobile" + key).removeClass('hide');
		$("#img_bannerMobile" + key).addClass('wrap_img');
	}
	
	var imageUrl = $("#physicalImgUrl" + key).val();
	if (imageUrl != "") {
		$("#img_bannerUrl" + key).attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + imageUrl);
		$("#img_bannerUrl" + key).removeClass('hide');
		$("#img_bannerUrl" + key).addClass('wrap_img');
	}
}

/**
 * init image uploader
 */
function initImageUploader(key) {
	var requestToken =  $("#requestToken").val();
	var uploadUrl = BASE_URL + CUSTOMER_ALIAS + "/news/uploadTemp?requestToken=" + requestToken;
	var imagePickfiles = 'imgPickfiles' + key;
	var imageContainer = 'imageContainer' + key;
	var imageMaxFileSize = '7mb';
	var imageMimeTypes = [ {
		title : "Image files",
		extensions : "jpg,png,jpeg,jfif"
	} ];

	var resize = {
		width : 2580,
		height : 680
	};		
	var Browse = function(up) {		
		MSG_ERROR_MIN_SIZE_COMMON = MSG_ERROR_MIN_NEWS_SIZE_DESKTOP;
	};
	
	var imageFileList = 'imageFilelist' + key;
	var imageConsole = 'imageConsole' + key;
	var imageFileUploaded = function(up, file, info) {
		$("#bannerImg" + key).val(cutString(file.name));
		$("#physicalImg" + key).val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#physicalImg" + key).val();
		var lstLinkbannerDesktop = $("[name$='.bannerDesktopPhysicalImg']");
		if(key == 0){
			for(var i = 0; i < lstLinkbannerDesktop.length; i ++){
				$("#img_banner" + i).attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + lstImg);
				$("#img_banner" + i).removeClass('hide');
				$("#img_banner" + i).addClass('wrap_img');
				$("#physicalImg" + i).val(lstImg);
			}
		} else {
			$("#img_banner" + key).attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + lstImg);
			$("#img_banner" + key).removeClass('hide');
			$("#img_banner" + key).addClass('wrap_img');
		}
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};
	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
			imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL, null, resize, null, Browse);
}

/**
 * init image uploader
 */
function initImageUploaderMobile(key) {
	var requestToken =  $("#requestToken").val();
	var uploadUrl = BASE_URL + PAGE_URL + "/uploadTemp?requestToken=" + requestToken;
	var imagePickfiles = 'imgPickfilesMobile' + key;
	var imageContainer = 'imageContainerMobile' + key;
	var imageMaxFileSize = '7mb';
	var imageMimeTypes = [ {
		title : "Image files",
		extensions : "jpg,png,jpeg,jfif"
	} ];
	
	var resize = {
		width : 1536,
		height : 1110
	};		
	var Browse = function(up) {		
		MSG_ERROR_MIN_SIZE_COMMON = MSG_ERROR_MIN_NEWS_SIZE_MOBILE;
	};
	
	var imageFileList = 'imageFilelistMobile' + key;
	var imageConsole = 'imageConsoleMobile' + key;
	var imageFileUploaded = function(up, file, info) {
		$("#bannerImgMobile" + key).val(cutString(file.name));
		$("#physicalImgMobile" + key).val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#physicalImgMobile" + key).val();
		var lstLinkbannerMobile = $("[name$='.bannerMobilePhysicalImg']");
		if(key == 0){
			for(var i = 0; i < lstLinkbannerMobile.length; i ++){
				$("#img_bannerMobile" + i).attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + lstImg);
				$("#img_bannerMobile" + i).removeClass('hide');
				$("#img_bannerMobile" + i).addClass('wrap_img');
				$("#physicalImgMobile" + i).val(lstImg);
			}
		} else {
			$("#img_bannerMobile" + key).attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + lstImg);
			$("#img_bannerMobile" + key).removeClass('hide');
			$("#img_bannerMobile" + key).addClass('wrap_img');
		}
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};
	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
			imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL, null, resize, null, Browse);
}

/**
 * init image uploader
 */
function initImageUploaderUrl(key) {
	var requestToken =  $("#requestToken").val();
	var uploadUrl = BASE_URL + PAGE_URL + "/uploadTemp?requestToken=" + requestToken;
	
	var imagePickfiles = 'imgPickfilesUrl' + key;
	var imageContainer = 'imageContainerUrl' + key;
	var imageMaxFileSize = '7mb';
	var imageMimeTypes = [ {
		title : "Image files",
		extensions : "jpg,png,jpeg,jfif"
	} ];
	
	var resize = {
				width : 330,
				height : 174
				
		};
	var Browse = function(up) {
		if (customerImageId == 11) {
			var temp = $('#newsTypeCategory :selected')[0];
			var type = $(temp).attr('type');
			if(type == 1){
				MSG_ERROR_MIN_SIZE_COMMON = MSG_ERROR_MIN_NEWS_LIBRARY_SIZE;
			}else {
				MSG_ERROR_MIN_SIZE_COMMON = MSG_ERROR_MIN_NEWS_NOT_LIBRARY_SIZE;
			}
			
		} else {
			MSG_ERROR_MIN_SIZE_COMMON = MSG_ERROR_MIN_NEWS_SIZE;
		}
	};
	
	var imageFileList = 'imageFilelistUrl' + key;
	var imageConsole = 'imageConsoleUrl' + key;
	var imageFileUploaded = function(up, file, info) {
		$("#imgUrl" + key).val(cutString(file.name));
		$("#physicalImgUrl" + key).val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#physicalImgUrl" + key).val();
		var lstLinkImgUrl = $("[name$='.physicalImgUrl']");
		if(key == 0){
			for(var i = 0; i < lstLinkImgUrl.length; i ++){
				$("#img_bannerUrl" + i).attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + lstImg);
				$("#img_bannerUrl" + i).removeClass('hide');
				$("#img_bannerUrl" + i).addClass('wrap_img');
				$("#physicalImgUrl" + i).val(lstImg);
			}
		} else {
			$("#img_bannerUrl" + key).attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + lstImg);
			$("#img_bannerUrl" + key).removeClass('hide');
			$("#img_bannerUrl" + key).addClass('wrap_img');
		}
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};
	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
			imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL, null, resize, null, Browse);
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

function deleteImageMobile(key) {
	$("#bannerImgMobile" + key).val("");
	$("#physicalImgMobile" + key).val("");
	$("#img_bannerMobile" + key).attr("src", "");
	$("#img_bannerMobile" + key).addClass('hide');
}

function deleteImageUrl(key) {
	$("#imgUrl" + key).val("");
	$("#physicalImgUrl" + key).val("");
	$("#img_bannerUrl" + key).attr("src", "");
	$("#img_bannerUrl" + key).addClass('hide');
}

/**
 * init type select box
 */
function initTypeSelect() {
	var typeSelect = $("#typeJsonHidden").val();
	if (typeSelect != null && typeSelect != '') {
		var $category = $('#typeId');
		$category.empty();

		$.each(jQuery.parseJSON(typeSelect), function(key, val) {
			/*$category.append('<option value="' + val.value + '">' + val.name + '</option>');*/
		});

		if (TYPE_ID != null && TYPE_ID != '') {
			$('#typeId').val(TYPE_ID).prop('selected', true);
		}
	}

	var categorySelect = $("#categoryJsonHidden").val();
	if (categorySelect != null && categorySelect != '') {
		var $select = $('#categoryId');
		$select.find('option:not(:first)').remove();

		$.each(jQuery.parseJSON(categorySelect), function(key, val) {
			$select.append('<option value="' + val.value + '">' + val.name + '</option>');
		});

		if (CATEGORY_ID != null && CATEGORY_ID != '') {
			$('#categoryId').val(CATEGORY_ID).prop('selected', true);
		}
	}
}

/**
 * type select box on change
 */
function newTypeChange(element) {
	var typeId = $(element).val();
	
	$.ajax({
		type : "GET",
		url : BASE_URL + PAGE_URL + "/type/change",
		data : {
			"typeId" : typeId,
		},
		success : function(data) {
			$('#categoryId').find('option:not(:first)').remove();
			
			$.each(jQuery.parseJSON(data), function(key, val) {
				$('#categoryId').append('<option value="' + val.id + '">' + val.label + '</option>');
			});
		}
	});
}

/**
 * update element editor
 */
function updateElementEditor() {
	for (instance in CKEDITOR.instances) {
		CKEDITOR.instances[instance].updateElement();
	}
}

/**
 * checkEffectiveDateExpirationDate
 */
function checkEffectiveDateExpirationDate() {
	
	var effectiveDate = new Date($('#effectiveDate').val());
	alert(effectiveDate.getDate() + '/' + (effectiveDate.getMonth() + 1)+ '/' + effectiveDate.getFullYear());
	
	var expirationDate = Date.parse(new Date($('#expirationDate').val()));
	alert(expirationDate);
	
	var currentDate = new Date();
	currentDate.setHours(0, 0, 0, 0);

	alert(Date.parse(currentDate));
	if (currentDate > expirationDate || effectiveDate > expirationDate) {
		alert(MESSAGE_EFFECTIVE_EXPIRATION);
		$('#expirationDate').focus();
		return false;
	}
	return true;
}

function setConditionSearch(){
	var tmp =JSON.parse($("#searchDto").val());
	var condition = {};
	condition["status"] = tmp.status;
	condition["code"] = tmp.code;
	condition["title"] = tmp.title;
	condition["custTypeId"] = tmp.custTypeId;
	condition["typeId"] = tmp.typeId;
	condition["categoryId"] = tmp.categoryId;
	
	condition["statusActive"] = tmp.statusActive;
	condition["typeHighlight"] = tmp.typeHighlight;
	
	condition["latestNews"] = tmp.latestNews;
	condition["pressRelease"] = tmp.pressRelease;
	
	
	condition["productCategoryId"] = tmp.productCategoryId;
	condition["productCategorySubId"] = tmp.productCategorySubId;
	condition["productId"] = tmp.productId;
	
	return condition;
}

function initVideoUploader() {
	var videoPickfiles = 'videoPickfiles';
	var videoContainer = 'videoContainer';
	var uploadUrl = BASE_URL + "ajax/uploadVideo";
	
	var videoMaxFileSize = '30mb';
	
	var videoMimeTypes = [ {
		title : "Video files",
		extensions : "avi,wma,mp4,flv"
	} ];
	var videoFileList = 'videoFilelist';
	var videoConsole = 'videoConsole';
	var videoFileUploaded = function(up, file, info) {
		$("#bannerVideo").val(cutString(file.name));
		$("#physicalVideo").val(cutString(info.response));
	};
	var videoUploadComplete = function(up, files) {
		// play video
		loadVideoPLayer($("#physicalVideo").val(), BASE_URL, 'videoContent');
		$("#" + videoConsole).hide();
		$("#" + videoFileList).hide();
		$("#videoContent").show();
	};

	
	InitPlupload(videoPickfiles, videoContainer, uploadUrl, false, videoMaxFileSize, videoMimeTypes, videoFileList,
			videoConsole, videoFileUploaded, videoUploadComplete, BASE_URL);
}

function loadVideoPLayer(video, url, ele) {
	ajaxUrl = url + 'ajax/showVideo?fileName=' + video;
	$.ajax({
		url : ajaxUrl,
		cache : false,
		success : function(fileName) {
			// set html wrap_video
			var htmlVideo = '<video id="videoContent" class="videoContent" controls="controls" style="width:100%"> '
			+ '<source src="' + BASE_URL + 'ajax/download?fileName=' + fileName + '" type="video/mp4" /> '
			+ '<source src="' + BASE_URL + 'ajax/download?fileName=' + fileName + '" type="video/ogg" /> '
			+ '</video>';
			
			// set html wrap_video
			$("#wrap_video").find('#wrap_object').html('').html(htmlVideo);
		}
	});
}

function signatureVideo() {
	var video = $("#physicalVideo").val();
	if (video != "") {
		loadVideoPLayer(video, BASE_URL, 'videoContent');
		$("#videoContent").show();
	}
}

function deleteVideo() {	
	$("#bannerVideo").val("");
	$("#physicalVideo").val("");
	$("#wrap_video").find('object').remove();
	$("#wrap_video").find('#wrap_object').html('').html(
			'<div id="videoContent'+'" class="videoContent" controls="controls"> </div>');
}

function checkTypeOfLibrary(){
	var lstTextInput = $("input[type=text][name$='.title']");
	var lstEditor = $("textarea[id^='editor']");
	var lstContent = $("label[id^='contentLabel']");
	var temp = $('#newsTypeCategory :selected')[0];
	var type = $(temp).attr('type');
	if(type == 1){
		$('#newsInvestorDiv').hide();
		$('#pressReleaseDiv').hide();
		$('#newsTypeHomepageDiv').hide();
		$('#notlibraryDiv').show();
		$('#newsTypePictureIntroductionDiv').show();
		$('#videoDiv').show();
		$('#libraryDiv').hide();
		$('#contentLabel').removeClass("required");
		for (var i = 0; i < lstTextInput.length; i++) {
			var editorContent = '#editor' + i;
			$(editorContent).removeClass("j-required");
			$(lstContent[i]).removeClass("required");
		}
		
	} else {
		$('#newsInvestorDiv').show();
		$('#pressReleaseDiv').show();
		$('#newsTypeHomepageDiv').show();
		$('#notlibraryDiv').hide();
		$('#newsTypePictureIntroductionDiv').hide();
		$('#videoDiv').hide();
		$('#libraryDiv').show();
		$('#contentLabel').addClass("required");
		for (var i = 0; i < lstTextInput.length; i++) {
			var editorContent = '#editor' + i;
			$(editorContent).addClass("j-required");
			$(lstContent[i]).addClass("required");
		}
	}
}

function loadIcon() {
	var icon = $("#img_bannerUrl0").val();
	if (icon != "") {
		$("#icon_banner").removeClass('hide');
	}
}
function nameToLinkAlias(strName){
    strName = strName.substring(0, 1000);
    linkAlias = removeDiacritics(strName.toLowerCase()).replace(/[^a-zA-Z\d\s-]+/gi,'').replace(/[^a-zA-Z\d\s]+/gi,' ');
    var aliasCompArray = linkAlias.split(/ /g);
    var comps = [];
    for(var compIndex in aliasCompArray){
        var compString = aliasCompArray[compIndex];
        if(!(compString === "")){
            comps.push(compString);
        }
    }
    var linkAlias = comps.join("-");
    return linkAlias;
}