$(document).ready(function() {
	$('#categoryList').combotree({
		editable : false,
		onChange: function (dataValue) {
			$('#categoryId').val(dataValue);
			 
			$.ajax({
                type  : "POST",
                data  : {'categoryId' : dataValue},
                url   : BASE_URL + "introduction/changeCategory",
                complete: function (data) {
                	$("#sort").val(data.responseText);
                }
            }); 
        
        },
        onHidePanel: function(){
        	$("div[tabindex=3]").focus();
        }
    });
	
	if( CATEGORY_LIST != null && CATEGORY_LIST != '' ) {
		$('#categoryList').combotree('loadData', jQuery.parseJSON(CATEGORY_LIST));
	}
	
	initImageUploader();
	initImageUploader_1();
	
	signatureImage();
	
	//on click add
	$("#addNew").on("click", function(event) {
		var url = BASE_URL + "introduction/edit";
		// Redirect to page detail
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	$("#listIntroduction").on("click", function(event) {
		var url = BASE_URL + "introduction/list";
		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	$("#btnCancel").on("click", function(event) {
		var url = BASE_URL + "introduction/list";
		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	initLinkAliasSelectorEvent($('#name'), $('#link-alias'));
	
	// tabindex
	var lstTextInput = $("input[type=text][name$='.title']");
	var lstLinkAlias = $("input[type=text][name$='.linkAlias']");	
	if (lstLinkAlias.length > 0) {
		initLinkAliasForListEvent($(lstTextInput[0]), lstLinkAlias);
	}
	
	var lstkeyword = $("input[type=text][name$='.keyword']");
	for (var i = 0; i < lstTextInput.length; i++) {
		initTagSelectorEvent($(lstTextInput[i]), $(lstkeyword[i]));
	}
	
	var lstTextSub = $("textarea[name$='.shortContent']");
	var lstEditor = $("textarea[name$='.content']");
	var lstTabLanguage = $("li[role=presentation][id^=tabLanguage]");
	var tabindex = 12;
	for (var i = 0; i < lstTextInput.length; i++) {
		$(lstTextInput[i]).attr("tabindex", tabindex++);
		$(lstTextSub[i]).attr("tabindex", tabindex++);
		$(lstEditor[i]).parent().attr("tabindex", tabindex++);
		
		// validate on blur
		$(lstTextInput[i]).blur(function(){
			$(this).valid();
		});		
	}
	
	// validate on blur and change tab
	var keyTab = '9';
	$(lstEditor[0]).parent().keydown(function(e){	
		var code = e.keyCode || e.which;
	    if (code == keyTab) {	  
	    	$(lstTabLanguage[1]).find('a').tab('show');		
	    }
	});
	$(lstTabLanguage[1]).on('shown.bs.tab', function (e) {
		$(lstTextInput[1]).focus();
	})
	
	// validate on blur
	$("#name, #categoryId, #link-alias, #sortOrder, #subTitle").blur(function(){
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
	    	$("#categoryId").valid();
	    	$("#categoryList").combotree('hidePanel');		    	
	    }		
	});

	// VIDEO signature
	signatureVideo();
	// init video uploader
	initVideoUploader();
	// init by language list
	initByLanguageList();
	
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
		e.preventDefault();
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
			var url = "introduction/edit";
			var condition = $("#id-form-intro-edit").serialize();

			ajaxSubmit(url, condition, event);
		} else {
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
		}
		
	});
	
	$("#categoryId, #beforeId").select2({ allowClear : false});
});

/**
 * update element editor
 */
function updateElementEditor() {
	for (instance in CKEDITOR.instances) {
		CKEDITOR.instances[instance].updateElement();
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
		signatureImage_1(key);
		// init image uploader
		initImageUploader(key);		
		initImageUploader_1(key);		
	});
}

/**
 * IMAGE signature
 */
function signatureImage(key) {

	var image = $("#physicalImg" + key).val();
	if (image != "") {
		$("#img_banner" + key).attr("src", BASE_URL + "banner/download?fileName=" + image);
		$("#img_banner" + key).removeClass('hide');
	}
}
function signatureImage_1(key) {

	var image = $("#physicalImg_1" + key).val();
	if (image != "") {
		$("#img_banner_1" + key).attr("src", BASE_URL + "banner/download?fileName=" + image);
		$("#img_banner_1" + key).removeClass('hide');
	}
}

/**
 * init image uploader
 */
function initImageUploader(key) {
	var requestToken =  $("#requestToken").val();
	var uploadUrl = BASE_URL + "banner/uploadTemp?requestToken=" + requestToken;
	var imagePickfiles = 'imgPickfiles' + key;
	var imageContainer = 'imageContainer' + key;
	var imageMaxFileSize = '7mb';
	var imageMimeTypes = [ {
		title : "Image files",
		extensions : "jpg,bmp,png,jpeg,jfif"
	} ];
	var resize = {
		width : 1366,
		height : 320
	};
	var imageFileList = 'imageFilelist' + key;
	var imageConsole = 'imageConsole' + key;
	var imageFileUploaded = function(up, file, info) {
		$("#bannerImg" + key).val(cutString(file.name));
		$("#physicalImg" + key).val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#physicalImg" + key).val();
		$("#img_banner" + key).attr("src", BASE_URL + "banner/download?fileName=" + lstImg);
		$("#img_banner" + key).removeClass('hide');
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};
	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
			imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL);
}
function initImageUploader_1(key) {
	var requestToken =  $("#requestToken").val();
	var uploadUrl = BASE_URL + "banner/uploadTemp?requestToken=" + requestToken;
	var imagePickfiles = 'imgPickfiles_1' + key;
	var imageContainer = 'imageContainer_1' + key;
	var imageMaxFileSize = '7mb';
	var imageMimeTypes = [ {
		title : "Image files",
		extensions : "jpg,bmp,png,jpeg,jfif"
	} ];
	var resize = {
		width : 1366,
		height : 320
	};
	var imageFileList = 'imageFilelist_1' + key;
	var imageConsole = 'imageConsole_1' + key;
	var imageFileUploaded = function(up, file, info) {
		$("#bannerImg_1" + key).val(cutString(file.name));
		$("#physicalImg_1" + key).val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#physicalImg_1" + key).val();
		$("#img_banner_1" + key).attr("src", BASE_URL + "banner/download?fileName=" + lstImg);
		$("#img_banner_1" + key).removeClass('hide');
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};
	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
			imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL);
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

function deleteImage_1(key) {
	$("#bannerImg_1" + key).val("");
	$("#physicalImg_1" + key).val("");
	$("#img_banner_1" + key).attr("src", "");
	$("#img_banner_1" + key).addClass('hide');
}

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
	$("#wrap_video").find('#wrap_object').html('').html(
			'<div id="videoContent' + '" class="videoContent"> </div>');
}

/**
 * type select box on change
 */
function getLstSort(isChange){
	
	if(isChange){
		$('#beforeId').empty();
	}
	var id = $("#id").val();
	
	var typeId = $("#categoryId").val();
	
	$.ajax({
		  url: BASE_URL + 'introduction/getLstSort',
		  data : {
			  id: id,
			  typeId: typeId
		  },
		  type: "GET",
	      success: function(data) {
	    	  if (id == null || id == ''){
	    		  $("#beforeId").html('<option></option>');
	    	  }
	    	  $("#beforeId").select2({
	    		  placeholder: '',
	              data : data
	          });
	      }
		});
}

function setConditionSearch() {
	var condition = {};
	condition["code"] = $("#codeSearch").val();
	condition["name"] = $("#nameSearch").val();
	condition["status"] = $("#statusSearch").val();
	condition["enabled"] = $("#enabledSearch").val();
	condition["categoryId"] = $("#categoryIdSearch").val();
	return condition;
}