var PAGE_URL = CUSTOMER_ALIAS + '/e-card';

$(document).ready(function($) {
	$('#label').val(8);
	init();
	
	$('#tabLanguage a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
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
	
	$('#type').on('change', function(){
		$(this).valid();
		loadDataType();
	})

	// on click button save
	$('#btnSave').on('click', function(event) {
		var validImage = $('#imageConsole').attr('style');
		if ($(".j-form-validate").valid() && validImage != ''){
			var url = PAGE_URL + "/edit";
			updateElementEditor();
			$('#label').removeAttr('disabled');
			var condition = $("#formId").serialize();
			ajaxSubmit(url, condition, event);
		} else {
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
			
			rollToError('j-form-validate');
		}
	});
	validatePositionValue()
});
function init(){
	var isDisabled = $('#hasEdit').val() == 'false';
	disabledAllField('formId', isDisabled);

	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}
	
	$('#type').select2({ allowClear: true });
	
	loadDataType();
	
	// show tab if exists error
	showTabError(LANGUAGE_LIST);
	
	//tabindex
	var lstTextInput = $("input[type=text][name$='.title']");
	var lstLinkAlias = $("input[type=text][name$='.keywordsSeo']");	
	if (lstLinkAlias.length > 0) {
		initLinkAliasForListEvent($(lstTextInput[0]), lstLinkAlias);
	}

	var lstkeyword = $("input[type=text][name$='.keywords']");
	for (var i = 0; i < lstTextInput.length; i++) {
		initTagSelectorEvent($(lstTextInput[i]), $(lstkeyword[i]));
	}
	
	//check press enter
	$("input[type=text]").keypress("keydown",function(e){
		if (e.keyCode == 13) {
			e.preventDefault();
		}
	});
	
	//check press space
	$(lstLinkAlias[0]).keypress("keydown",function(e){
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
	
	$(lstLinkAlias[0]).on("change",function(e){
		var txtName = $(lstLinkAlias[0]).val();
		for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName);
			linkAlias = linkAlias + "-";
			$(lstLinkAlias[i]).val(linkAlias);
		}
		e.preventDefault();
	});
	
	$(lstkeyword[0]).on("change",function(e){
		var txtName = $(lstkeyword[0]).val();
		for (var i = 0, sz = lstkeyword.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName).replaceAll('-', '');
			linkAlias = i == 0 ? "#" + linkAlias : + linkAlias;
			$(lstkeyword[i]).val(linkAlias);
		}
		e.preventDefault();
	});
	
	//check focusout
	$($("input[type=text][name$='.linkAlias']")).focusout(function(e) {
		var txtName = $(lstLinkAlias[0]).val();
		for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName);
			$(lstLinkAlias[i]).val(linkAlias);
		}
		e.preventDefault();
	});
	loadImage();
	initImageUploader();
}
function validatePositionValue(){
	createFunctionValidate("position-validate", function(value, element) {
		if( value <= 7){
			return true;
		}
	}, ERROR_POSITION_VALUE);
}
function loadDataType(){
	let value = $('#type').find('option:selected').val();
	let text = $('#type').find('option:selected').text();
	$('#typeName').val(text);
	
	$('.img_banner_big_e_card').find('img').attr('src', BASE_URL + 'static/images/' + value + '.png');
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
/**
 * init image uploader
 */
function initImageUploader() {
	var requestToken = $("#requestToken").val();
	var uploadUrl = BASE_URL + PAGE_URL + "/uploadTemp?requestToken=" + requestToken;
	var imagePickfiles = 'imgPickfiles';
	var imageContainer = 'imageContainer';
	var imageMaxFileSize = '3mb';
	var imageMimeTypes = [{
		title: "Image files",
		extensions: "jpg,png,jpeg,jfif"
	}];

	var resize = {
		width: 300,
		height: 200
	};

	var validateSize = function (up,file){
		if(imageMaxFileSize < uploadUrl){
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
		if ($("#mobileEnable").is(':checked')) {
			up.settings.filters.min_width_x_min_height = {
				width: 1536,
				height: 1110
			}
			MSG_ERROR_MIN_SIZE_COMMON = MSG_ERROR_MIN_SIZE_MOBILE;
		} else {
			up.settings.filters.min_width_x_min_height = resize;
			up.settings.filters.max_file_size = imageMaxFileSize;
			ERROR_IMG_SIZE = ERROR_IMG_SIZE_3MB;
			MSG_ERROR_MIN_SIZE_COMMON = MSG_ERROR_MIN_SIZE_ECARD;
		}
	};

	var imageFileList = 'imageFilelist';
	var imageConsole = 'imageConsole';
	var imageFileUploaded = function(up, file, info) {
		$("#eCardImg").val(cutString(file.name));
		$("#physicalImg").val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#physicalImg").val();
		$("#img_banner").attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + lstImg);
		$("#img_banner").removeClass('hide');
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};

	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
		imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL, null, resize, BeforeUpload, Browse);
}

function loadImage(){
	if(!isBlank($("#physicalImg").val())){
		var lstImg = $("#physicalImg").val();
		$("#img_banner").attr("src", BASE_URL + PAGE_URL + "/download?fileName=" + lstImg);
		$("#img_banner").removeClass('hide');
	}
}