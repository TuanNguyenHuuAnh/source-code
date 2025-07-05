$(document).ready(function($) {
	// tabLanguage click
	$('#tabLanguage a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}
	
	//on click add
	$("#addNew").on("click", function(event) {
		var url = BASE_URL + CUSTOMER_ALIAS + "/edit";
		// Redirect to page detail
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	initData();
	
	//on click cancel
	$('#btnCancel').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + CUSTOMER_ALIAS + "/list";

		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	// on click list
	$('#linkList').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + CUSTOMER_ALIAS + "/list";

		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});

	// on click button save, or do process
	$('.process-btn').on('click', function(event) {
		
		updateElementEditor();
		
		// set button id
		$("#buttonId").val(this.id);
		
		// set button action
		$("#buttonAction").val($(this).find('span').text());
		
		// set items role
		$("#currItem").val($(this).find('span').data("curritem"));

		if ($(".j-form-validate").valid()) {
			var url = CUSTOMER_ALIAS + "/saveDraft";
			var condition = $("#customerForm").serialize();

			ajaxSubmit(url, condition, event);
		} else {
			
			showTabError(LANGUAGE_LIST);
			
			setTimeout(function() { 
				$(".j-form-validate").find(":input.error:first").focus();
			}, 500);
		}
	});
	
	// show tab if exists error
	showTabError(LANGUAGE_LIST);
	
	var lstShortContent = $("textarea[name$='.shortContent']");
	var lstKeyContent = $("input[type=text][name$='.keyContent']");
	var lstEditor = $("textarea[id^='editor']");
	
	// tabindex button
	if ($("#saveDraft").length > 0) {
		$("#saveDraft").attr("tabindex", tabindex++);
		$("#sendRequest").attr("tabindex", tabindex++);
	} 
	var lstTabLanguage = $("li[role=presentation][id^=tabLanguage]");
	// validate on blur and change tab
	var keyTab = '9';	
	$(lstEditor[0]).parent().keydown(function(e){	
		var code = e.keyCode || e.which;
	    if (code == keyTab) {	  
	    	$(lstTabLanguage[1]).find('a').tab('show');
	    }
	});
	
	//check press enter
	$("input[type=text]").keypress("keydown",function(e){
		if (e.keyCode == 13) {
			e.preventDefault();
		}
	});
});

function initData(){
	var size = '7mb';
	var resize = {width : 2580, height : 680};
	var message = MSG_ERROR_MIN_SIZE_PRODUCT_IMG;
	
	if (VIP == 1){
		resize = {width : 1760, height : 926};
		message = MSG_ERROR_MIN_SIZE_1760_926_IMG;
	}else if (FDI == 1){
		resize = {width : 2580, height : 680};
		message = MSG_ERROR_MIN_SIZE_PRODUCT_IMG;
	}
	
	//	IMAGE signature
	loadImage("#imgUrl", "#img_banner");
	//	init image uploader
	initImageUploader('imgPickfiles', 'imageContainer', 'imageFilelist',
			'imageConsole', "#imgUrl", "#imgName", "#img_banner", size, resize,
			message);
	
	var resizeMobile = {width : 1536, height : 810};
	var messageMobile = MSG_ERROR_MIN_SIZE_1536_1110_IMG;
	//	IMAGE Mobile signature
	loadImage("#imgMobileUrl", "#imgMobile_banner");
	//	init image mobile uploader
	initImageUploader('imgMobilePickfiles', 'imageMobileContainer', 'imageMobileFilelist',
			'imageMobileConsole', "#imgMobileUrl", "#imgMobileName", "#imgMobile_banner", size, resizeMobile,
			messageMobile);
	
	var resizeIcon = {width : 100, height : 100};
	var messageIcon = MSG_ERROR_MIN_SIZE_100_100_IMG;
	//	Icon signature
	loadImage("#iconUrl", "#icon_banner");
	//	init icon uploader
	initImageUploader('iconPickfiles', 'iconContainer', 'iconFilelist',
			'iconConsole', "#iconUrl", "#iconName", "#icon_banner", size, resizeIcon,
			messageIcon);
	
	var resizeIconActive = {width : 160, height : 160};
	var messageIconActive = MSG_ERROR_MIN_SIZE_160_160_IMG;
	//	Icon Mobile signature
	loadImage("#iconMobileUrl", "#iconMobile_banner");
	//	init icon mobile uploader
	initImageUploader('iconMobilePickfiles', 'iconMobileContainer', 'iconMobileFilelist',
			'iconMobileConsole', "#iconMobileUrl", "#iconMobileName", "#iconMobile_banner", size, resizeIconActive,
			messageIconActive);
}

/**
 * IMAGE signature
 */
function loadImage(imgUrlId, img_bannerId) {
	var image = $(imgUrlId).val();
	if (image != "") {
		$(img_bannerId).attr("src", BASE_URL + CUSTOMER_ALIAS + "/image/download?fileName=" + image);
		$(img_bannerId).removeClass('hide');
	}
}

/**
 * init image uploader
 */
function initImageUploader(imgPickfiles, imageContainer, imageFilelist,
		imageConsole, imgUrlId, imgNameId, img_bannerId, size, resize, message) {
	var requestToken = $("#requestToken").val();
	var uploadUrl = BASE_URL + CUSTOMER_ALIAS + "/uploadTemp?requestToken=" + requestToken;
	
	var imagePickfiles = imgPickfiles;
	var imageContainer = imageContainer;
	var imageMaxFileSize = size;
	var imageMimeTypes = [ {
		title : "Image files",
		extensions : "jpg,bmp,png,eps,jfif"
	} ];
	
	var Browse = function(up) {		
		MSG_ERROR_MIN_SIZE_COMMON = message;
	};
	
	var imageFileList = imageFilelist;
	var imageConsole = imageConsole;
	var imageFileUploaded = function(up, file, info) {
		$(imgUrlId).val(cutString(info.response));
		$(imgNameId).val(cutString(file.name));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $(imgUrlId).val();

		$(img_bannerId).attr("src", BASE_URL + CUSTOMER_ALIAS + "/image/download?fileName=" + lstImg);
		$(img_bannerId).removeClass('hide');
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};
	
	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
			imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL, null, resize, null, Browse);
}

/**
 * delete image
 */
function deleteImage(language) {
	$("#imgUrl").val("");
	$("#img_banner").attr("src", "");
	$("#img_banner").addClass('hide');
}

/**
 * update element editor
 */
function updateElementEditor() {
	for (instance in CKEDITOR.instances) {
		CKEDITOR.instances[instance].updateElement();
	}
}

function setConditionSearch(){
	var tmp = JSON.parse($("#searchDto").val());
	var condition = {};
	condition["status"] = tmp.status;
	condition["code"] = tmp.code;
	condition["title"] = tmp.title;
	condition["enabled"] = tmp.enabled;
	
	return condition;
}