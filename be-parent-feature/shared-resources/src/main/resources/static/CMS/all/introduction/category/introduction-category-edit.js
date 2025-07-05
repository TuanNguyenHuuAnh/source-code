$(document).ready(
		function() {
			$.validator.setDefaults({
				ignore : ""
			});
			
			changeTypeOfIntroduction("typeOfMain", "pictureIntroduction");
			changeTypeOfIntroduction("pictureIntroduction", "typeOfMain");
			
			checkShowVideo();
			
			$("#typeOfMain").on("change", function(){
				checkShowVideo();
				
			});
			
			$('#categoryList').combotree({
				editable : true,
				onChange : function(dataValue) {
					$.ajax({
						type : "POST",
						data : {
							'parentId' : dataValue
						},
						url : BASE_URL + "introduction-category/changeParent",
						complete : function(data) {
							$("#sort").val(data.responseText);
						}
					});
				},
		        onHidePanel: function(){
		        	$("div[tabindex=3]").focus();
		        }
			});

			if (CATEGORY_LIST != null) {
				$('#categoryList').combotree('loadData', jQuery.parseJSON(CATEGORY_LIST));
			}
			
			initImageUploader('imagePickfiles', 'imageContainer', '7mb', { width: 930, height: 1134 }, 'imageFilelist', 'imageConsole', 'imageName', 'imagePhysicalName', 'image_preview', MSG_ERROR_MIN_SIZE_DESKTOP);
			signatureImage('imagePhysicalName', 'image_preview');
			
			initImageUploader('imagePickfilesMobile', 'imageContainerMobile', '7mb', { width: 1536, height: 810 }, 'imageFilelistMobile', 'imageConsoleMobile', 'imageNameMobile', 'imagePhysicalNameMobile', 'image_previewMobile', MSG_ERROR_MIN_SIZE_MOBILE);
			signatureImage('imagePhysicalNameMobile', 'image_previewMobile');
			
			$("#deleteImage").on("click", function(){
				deleteImage('imageName', 'imagePhysicalName', 'image_preview');
			});
			
			$("#deleteImageMobile").on("click", function(){
				deleteImage('imageNameMobile', 'imagePhysicalNameMobile', 'image_previewMobile');
			});
			
			$("#btnListCategory-1").on("click", function(event) {
				var url = BASE_URL + "introduction-category/list";
				// Redirect to page list
				ajaxRedirectWithCondition(url, setConditionSearch());
			});
			
			//on click add
			$("#addNew").on("click", function(event) {
				var url = BASE_URL + "introduction-category/edit";
				// Redirect to page detail
				ajaxRedirectWithCondition(url, setConditionSearch());
			});
			
			$("#btn-cancel-1").on("click", function(event) {
				event.preventDefault();
				var url = BASE_URL + "introduction-category/list";
				// Redirect to page list
				ajaxRedirectWithCondition(url, setConditionSearch());
			});
			
			// on click button save, or do process
			$('.process-btn').unbind().on('click', function(event) {
				
				updateElementEditor();
				
				// set button id
				$("#buttonId").val(this.id);
				
				// set button action
				$("#buttonAction").val($(this).find('span').text());
				
				// set items role
				$("#currItem").val($(this).find('span').data("curritem"));
				
				var condition = $("#validateform").serialize();
				
				//check tồn tại
				var url = BASE_URL + "introduction-category/checkType";
				if ($(".j-form-validate").valid()) {
					$.ajax({
						type : "POST",
						url : url,
						data : condition,
						beforeSend : function(){
							blockbg();
						},
						global: false,
						success : function(data) {
							if (data == "{}"){
								url = "introduction-category/edit";
								ajaxSubmitAlertBox(url, condition, event);
							}else{
								unblockbg();
								
								var checkedTypeOfMain = $('#typeOfMain').is(":checked");
								var checkedTPictureIntroduction = $('#pictureIntroduction').is(":checked");
								
								var message = '';
								if (checkedTypeOfMain){
									message = MSG_SAVE_MAIN_OF_TYPE_CONFIRM;
								}else if (checkedTPictureIntroduction){
									message = MSG_SAVE_PICTURE_INTRODUCTION_CONFIRM;
								}
								
								popupConfirmWithButtons(message, LIST_BUTTON_CONFIRM_DELETE, function(result) {
									if (result){
										url = "introduction-category/edit";
										ajaxSubmitAlertBox(url, condition, event);
									}
								});
							}
						},
						error : function(xhr, textStatus, error) {
							console.log(xhr);
							console.log(textStatus);
							console.log(error);
							unblockbg();
						}
					});
				}else{
					// show tab if exists error
					showTabError(LANGUAGE_LIST);
				}
			});

			initLinkAliasSelectorEvent($('#name'), $('#link-alias'));

			showTabError(LANGUAGE_LIST);
			
			// tabLanguage click
			$('#tabLanguage a').click(function(e) {
				e.preventDefault();
				$(this).tab('show');
			});
			
			// tabindex
			var lstTextInput = $("input[type=text][name$='.label']");
			var lstTextSub = $("textarea[name$='.description']");
			var lstTabLanguage = $("li[role=presentation][id^=tabLanguage]");
			var tabindex = 11;
			for (var i = 0; i < lstTextInput.length; i++) {
				$(lstTextInput[i]).attr("tabindex", tabindex++);
				$(lstTextSub[i]).attr("tabindex", tabindex++);
				
				// validate on blur
				$(lstTextInput[i]).blur(function(){
					$(this).valid();
				});		
			}
			
			// validate on blur and change tab
			var keyTab = '9';
			$(lstTextSub[0]).keydown(function(e){	
				var code = e.keyCode || e.which;
			    if (code == keyTab) {	  
			    	$(lstTabLanguage[1]).find('a').tab('show');		
			    }
			});
			$(lstTabLanguage[1]).on('shown.bs.tab', function (e) {
				$(lstTextInput[1]).focus();
			})
			
			// validate on blur
			$("#name, #link-alias, #sortOrder").blur(function(){
				$(this).valid();
			});
			
			// show combotree
			$('#name').keydown(function(e){
				var code = e.keyCode || e.which;
			    if (code == keyTab) {	 
			    	$("#categoryList").combotree('showPanel');	
			    }		
			});
			
			// hide combotree
			$("div[tabindex=3]").keydown(function(e){
				var code = e.keyCode || e.which;
			    if (code == keyTab) {	 
			    	$("#categoryList").combotree('hidePanel');
			    }		
			});
			
			// tabindex
			var lstTextInput = $("input[type=text][name$='.label']");
			var lstLinkAlias = $("input[type=text][name$='.linkAlias']");	
			if (lstLinkAlias.length > 0) {
				initLinkAliasForListEvent($(lstTextInput[0]), lstLinkAlias);
			}
			
			var lstkeyword = $("input[type=text][name$='.keyWord']");
			for (var i = 0; i < lstTextInput.length; i++) {
				initTagSelectorEvent($(lstTextInput[i]), $(lstkeyword[i]));
			}
			
			var lstShortContent = $("textarea[name$='.shortContent']");
			var lstKeyContent = $("input[type=text][name$='.keyContent']");
			var lstEditor = $("textarea[id^='editor']");
			
			var lstTabLanguage = $("li[role=presentation][id^=tabLanguage]");
			var tabindex = 20;
			for (var i = 0; i < lstTextInput.length; i++) {
				$(lstTextInput[i]).attr("tabindex", tabindex++);
				$(lstShortContent[i]).attr("tabindex", tabindex++);
				$(lstKeyContent[i]).attr("tabindex", tabindex++);
				$(lstEditor[i]).parent().attr("tabindex", tabindex++);
				
				// validate on blur
				$(lstTextInput[i]).blur(function(){
					$(this).valid();
				});		
				$(lstKeyContent[i]).blur(function(){
					$(this).valid();
				});	
			}

			initVideoUploader();
			signatureVideo();
			
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
			
			//check focusout
			$($("input[type=text][name$='.linkAlias']")).focusout(function() {
				var txtName = $(lstLinkAlias[0]).val();
				for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
					linkAlias = nameToLinkAlias(txtName);
					$(lstLinkAlias[i]).val(linkAlias);
				}
			});
			
		});

/**
 * Video signature
 */
function signatureVideo() {
	var video = $("#physicalVideo").val();
	if (video != "") {
		loadVideoPLayer(video, BASE_URL, 'videoContent');
		$("#videoContent").show();
	}
}

/**
 * init video uploader
 */
function initVideoUploader() {
	var videoPickfiles = 'videoPickfiles';
	var videoContainer = 'videoContainer';
	var uploadUrl = BASE_URL + "ajax/uploadVideo";
	var videoMaxFileSize = '30mb';
	var videoMimeTypes = [ {
		title : "Video files",
		extensions : "avi,wmv,wma,mp4,flv"
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

/**
 * load video player
 */
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

/**
 * delete video
 */
function deleteVideo() {
	$("#bannerVideo").val("");
	$("#physicalVideo").val("");
	$("#wrap_video").find('object').remove();
	$("#wrap_video").find('#wrap_object').html('').html('<div id="videoContent' + '" class="videoContent"> </div>');
}


/**
 * IMAGE signature
 */
function signatureImage(imagePhysicalName, image_preview) {
	var image = $("#" + imagePhysicalName).val();
	if (image != "") {
		$("#" + image_preview).attr("src", BASE_URL + "introduction-category/image/download?fileName=" + image);
		$("#" + image_preview).removeClass('hide');
	}
}
/**
 * init image uploader
 */
function initImageUploader(imagePickfiles, imageContainer, imageMaxFileSize, resize, imageFileList, imageConsole, imageName, imagePhysicalName, image_preview, message) {
	var requestToken = $("#requestToken").val();
	var uploadUrl = BASE_URL + "introduction-category/uploadTemp?requestToken=" + requestToken;
	var imageMimeTypes = [ {
		title : "Image files",
		extensions : "jpg,bmp,png,eps,jfif"
	} ];
	
	var resize = resize;
	
	var Browse = function(up) {		
		MSG_ERROR_MIN_SIZE_COMMON = message;
	};
	
	var imageFileUploaded = function(up, file, info) {
		$("#" + imageName).val(cutString(file.name));
		$("#" + imagePhysicalName).val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#" + imagePhysicalName).val();
		$("#" + image_preview).attr("src", BASE_URL + "introduction-category/image/download?fileName=" + lstImg);
		$("#" + image_preview).removeClass('hide');
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};
	
	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
			imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL, null, resize, null, Browse);
}

function deleteImage(imageName, imagePhysicalName, image_preview) {
	$("#" + imageName).val("");
	$("#" + imagePhysicalName).val("");
	$("#" + image_preview).attr("src", "");
	$("#" + image_preview).addClass('hide');
}

/**
 * update element editor
 */
function updateElementEditor() {
	for (instance in CKEDITOR.instances) {
		CKEDITOR.instances[instance].updateElement();
	}
}

function setConditionSearch() {
	var condition = {};
	condition["code"] = $("#codeSearch").val();
	condition["name"] = $("#nameSearch").val();
	condition["status"] = $("#statusSearch").val(); 
	condition["enabled"] = $("#enabledSearch").val();
	condition["typeOfMain"] = $("#typeOfMainSearch").val();
	condition["pictureIntroduction"] = $("#pictureIntroductionSearch").val();
	
	console.log(condition);
	return condition;
}

function changeTypeOfIntroduction(idTypeOne, idTypeTwo){
	$('#'+ idTypeOne).change(function() {
        if(this.checked) {
        	var checked = $('#'+ idTypeTwo).prop('checked');
        	if (checked){
        		$('#' + idTypeTwo).prop("checked", false);
        	}
        }
    	
    	checkShowVideo();
    });
}

function hideVideo(){
	$("#hideVideo").attr("hidden", true);
}

function showVideo(){
	$("#hideVideo").removeAttr("hidden");
}

function checkShowVideo(){
	var checked = $("#typeOfMain").prop('checked');
	
	if (checked){
		showVideo();
	}else{
		hideVideo();
		deleteVideo();
	}
}