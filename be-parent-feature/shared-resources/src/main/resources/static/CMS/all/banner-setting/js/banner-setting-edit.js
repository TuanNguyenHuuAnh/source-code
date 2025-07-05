var PAGE_URL = 'banner/homepage-setting';
var BANNER_TYPE_IMAGE = '1';
var BANNER_TYPE_VIDEO = '2';
var BANNER_DEVICE_PC = '1';
var BANNER_DEVICE_MOBILE = '2';

$(document).ready(function($) {
	init();

	/*$('#startDate').blur(function(e) {
		let id = $(this).attr('id');
		$(this).removeClass('error');
		findAndRemoveError(id);
	});*/
	
		$('select[multiple]').multiselect({
		columns: 1,
		search: false,
		minHeight: 100,
		maxHeight: 155
	});

	$('#bannerPageName').on("select2:close", function(e) {
		let id = $(this).attr('id');
		$(this).removeClass('error');
		findAndRemoveError(id);
	});

	$("#mBannerTopId").change(function() {
		$('#mBannerTopId-error').empty();
	});
	$("#mBannerTopMobileId").change(function() {
		$('#mBannerTopMobileId-error').empty();
	});
	$("#type").change(function() {
		$('#type-error').empty();
	});

	// tabLanguage click
	$('#tabLanguage a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
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

		let bannerAfterSort = getSortable();
		let bannerMobileAfterSort = getSortabeMobile();

		if ($(".j-form-validate").valid()) {
			var url = PAGE_URL + "/edit";
			if (Object.keys(bannerAfterSort).length === 0) {

				// save Single
				var condition = $(".j-form-validate").serialize();
				ajaxSubmit(url, condition, event);

			} else {
				// save multi
				let conditions = getFormObject('#bannerForm');
				conditions['mBannerTopId'] = bannerAfterSort.toString();
				conditions['mBannerTopMobileId'] = bannerMobileAfterSort.toString();

				ajaxSubmit(url, conditions, event);

			}
		} else {
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
			rollToError('j-form-validate');
		}
	});

	$('.checkLink').on('change', function() {
		if ($('.checkLink').val() == '1') {
			$('.youtube').val('');
		}
	});

	let bannerCode = $('#bannerPageName').val();
	let bannerTypeValue = $('#bannerType').val();
	$('#bannerTypeDto').on('change', function(e) {

		$('#bannerType').val(e.target.value);
		if (e.target.value == BANNER_TYPE_IMAGE) {

			$('#pro').hide();
			$('#divVideoPhysical').show();
			$('#devslideTime').show();
			$('#eff').show();
			newTypeChange(this);
		} else {
			$('#pro').show();
			$('#devslideTime').hide();
			$('#eff').hide();
		}

		var url = PAGE_URL + "/change/banner-type";

		// load banner hình ảnh/video cho máy tính
		$.ajax({
			type: "GET",
			url: BASE_URL + url,
			data: {
				"bannerDevice": BANNER_DEVICE_PC,
				"bannerType": e.target.value
			},
			success: function(data) {
				$('#mBannerTopId').find('option').remove();
				$("#banner-image").find("video").remove();
				$('#mBannerTopId').append('<option value="' + '">' + '</option>');
				$.each(jQuery.parseJSON(data), function(key, val) {
					$('#mBannerTopId').append('<option value="' + val.id + '">' + val.name + '</option>');
				});
			},
			error: function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});

		$.ajax({
			type: "GET",
			url: BASE_URL + url,
			data: {
				"bannerDevice": BANNER_DEVICE_MOBILE,
				"bannerType": e.target.value
			},
			success: function(data) {
				$('#mBannerTopMobileId').find('option').remove();
				$("#banner-moblie-top-image").find("video").remove();
				$('#mBannerTopMobileId').append('<option value="' + '">' + '</option>');
				$.each(jQuery.parseJSON(data), function(key, val) {
					$('#mBannerTopMobileId').append('<option value="' + val.id + '">' + val.name + '</option>');
				});
			},
			error: function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});
		checkShowImgAndVideo();
	});

	// check banner type 
	checkBannerType();

	$("#bannerPageName").change(function() {

		checkBannerType();

		newTypeChange(this);

	});

	// check multi select
	checkSelectBanner(bannerCode, bannerTypeValue);

	//desktop
	$(document).on('click', ".banner-top-list li", function(event) {

		event.preventDefault();
		$('.banner-top-list li').each(function(index, element) {
			if ($(element).hasClass("active")) {
				$(element).removeClass("active");
			}
		});
		$(this).addClass("active");

		loadBannerImage(this, "banner-image");
	});

	//mobile
	$(document).on('click', ".banner-top-moblie-list li", function(event) {
		event.preventDefault();
		$('.banner-top-moblie-list li').each(function(index, element) {
			if ($(element).hasClass("active")) {
				$(element).removeClass("active");
			}
		});
		$(this).addClass("active");
		loadBannerImage(this, "banner-moblie-top-image");
	});

	//desktop multiple
	$(".bannerTopList input[type=checkbox]").on('click', function() {
		var id = $(this).attr('value');
		var title = $(this).attr('title');

		var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + id + " data-row-id=" + id + ">"
			+ "<div style='cursor: pointer;'> <span>" + title + "</span>" + "</div></li>";

		var type = $(this).parent().parent().attr("class");
		if (type == 'selected' || type == 'default selected') {
			$(".banner-top-list").find('li#' + id).remove();
		} else {
			$("ul.banner-top-list").append(item);
			$("ul.banner-top-list").find("li:first").trigger("click");
		}


	});

	$(".bannerTopMoblieList input[type=checkbox]").click(function() {
		var id = $(this).attr('value');
		var title = $(this).attr('title');

		var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + id + " data-row-id=" + id + ">"
			+ "<div style='cursor: pointer;'> <span>" + title + "</span>" + "</div></li>";

		var type = $(this).parent().parent().attr("class");

		if (type == 'selected' || type == 'default selected') {
			$(".banner-top-moblie-list").find('li#' + id).remove();
		} else {
			$("ul.banner-top-moblie-list").append(item);
			$("ul.banner-top-moblie-list").find("li:first").trigger("click");
		}

	});


	if (!isNull($('#mBannerTopId').val()) && !isNull($('#mBannerTopMobileId').val())) {
		selectBanner("#mBannerTopId", ".banner-top-list", "banner-image");
		selectBanner("#mBannerTopMobileId", ".banner-top-moblie-list", "banner-moblie-top-image");
	}

	$('#mBannerTopId').on('change', function() {

		selectBanner("#mBannerTopId", ".banner-top-list", "banner-image");
	});

	$('#mBannerTopMobileId').on('change', function() {
		selectBanner("#mBannerTopMobileId", ".banner-top-moblie-list", "banner-moblie-top-image");
	});


	//show ul item 
	if (bannerCode == 'AGENT' || bannerCode == 'CANDIDATE' || bannerCode == 'PUBLIC') {
		if (bannerTypeValue == BANNER_TYPE_IMAGE) {
			loadBannerImagePCUpdated();
			loadBannerImageMobileUpdated();
		}
	}

});

function init() {


	changeDatetimepicker('startDate', 'endDate');

	initByLanguageList();

	checkShowImgAndVideo();

	createJValidate();

	disabled();

}
function disabled() {
	var isDisabled = $('#hasEdit').val() == 'false';
	disabledAllField('bannerForm', isDisabled);

	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}
}

function createJValidate() {
/*	createFunctionValidate("validateStartDate", function(value, element) {
		let today = new Date();

		if (today <=  converToDate(value)) {
			return true;
		}
	}, MSG_ERROR_POSTED_DATE);*/

	createFunctionValidate("validateEndDate", function(value, element) {
		let startDate = $('#startDate').val();
		if (!isNull(startDate)) {
			if (value != '' && startDate != '' && compareDate(startDate, value) == -1) {
				return true;
			}
		}

		return this.optional(element);

	}, MSG_ERROR_EXPIRATION_DATE);

}

function removeItem() {

	$('.banner-top-list').find(".list-group-item").remove();
	$("#banner-image").find("img").remove();

	$('.banner-top-moblie-list').find(".list-group-item").remove();
	$("#banner-moblie-top-image").find("img").remove();

}

function checkBannerType() {
	let bannerCode = $('#bannerPageName').val();

	if ( (bannerCode == 'PUBLIC') && EDIT_DTO.hasEdit) {
		$('#bannerTypeDto').removeAttr('disabled');
	}
	else {
		$('#bannerTypeDto').attr('disabled', 'disabled');

	}
}

function checkShowImgAndVideo() {

	let bannerType = $('#bannerTypeDto').val();
	let bannerCode = $('#bannerPageName').val();

	if (bannerType == BANNER_TYPE_IMAGE) {
		$('#divImg').show();
		$('#divVideo').hide();
		$('#divVideoPhysical').show();
		$('#pro').hide();
		$('#devslideTime').show();
		$('#eff').show();

		$.each(LANGUAGE_LIST, function(key, val) {

			$('#physicalVideo' + key).removeClass('j-required');
			$('#physicalVideo' + key + '-error').remove();

			checkShowLinkYoutube(key);
		});
	} else {
		$('#divImg').hide();
		$('#divVideo').show();
		$('#pro').show();
		$('#devslideTime').hide();
		$('#eff').hide();

		checkSelectBanner(bannerCode, bannerType);

		$.each(LANGUAGE_LIST, function(key, val) {
			$('#physicalImg' + key).removeClass('j-required');
			$('#physicalImg' + key + '-error').remove();



			checkShowLinkYoutube(key);
		});
	}

	removeItem();
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
	if (fileName != "") {
		loadVideoPLayer(fileName, key);
		$("#videoContent" + key).show();
	}
}


function loadVideoPLayer(fileName, key) {
	var htmlVideo = '<video id="videoContent ' + key + '" class="videoContent" controls="controls" style="width:100%"> '
		+ '<source src="' + BASE_URL + 'cmsAjax/download?fileName=' + fileName + '" type="video/mp4" /> '
		+ '<source src="' + BASE_URL + 'cmsAjax/download?fileName=' + fileName + '" type="video/ogg" /> '
		+ '</video>';

	// set html wrap_video
	$("#wrap_video" + key).find('#wrap_object' + key).html('').html(htmlVideo);
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
	//divVideoYoutube là youtube
	//divVideoPhysical là bannerPC và bannerMobile
	if (style != 'display: none;') {
		if (checked) {
			$('#divVideoYoutube').show();
			$('#divVideoPhysical').hide();
			$('#mBannerTopId').removeClass('j-required');
			$('#mBannerTopMobileId').removeClass('j-required');

			$('#bannerYoutubeVideo' + key).addClass('j-required');
			$('#physicalVideo' + key).removeClass('j-required');

			$('#bannerVideoType' + key).val(2);

			changeLinkYoutube(key);
		} else {
			$('#divVideoYoutube').hide();
			$('#divVideoPhysical').show();
			$('#mBannerTopId').addClass('j-required');
			$('#mBannerTopMobileId').addClass('j-required');


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
/**
 * type select box on change
 */
function newTypeChange(element) {


	var bannerCode = $(element).val();
	let bannerType = $('#bannerTypeDto').val();


	BANNER_PAGE_LIST.forEach(function(val, key) {

		if (val.kind == bannerCode) {

			$('#bannerType').val('1'); // Select the option with a value of '1'
			$('#bannerTypeDto').val('1');
			$('#bannerTypeDto').trigger('change'); // Notify any JS components that the value changed


			if ('M' == val.text && bannerType == BANNER_TYPE_IMAGE) {
				$('#mBannerTopId').val(null);
				$('.banner-top-list').find(".list-group-item").remove();
				$("#banner-image").find("img").remove();

				$('#mBannerTopId').find('option:first').remove();
				$('#mBannerTopId').parent().find('.select2-container').remove();

				$('#mBannerTopId').attr('multiple', 'multiple');

				// check fisrt multi
				if ($("#mBannerTopId[multiple='multiple']").length == 0) {
					$('#mBannerTopId').multiselect('unload');

				}

				$('#mBannerTopId').multiselect({
					columns: 1,
					//placeholder : GROUP_LABEL,
					search: true,
					minHeight: 100,
					maxHeight: 155
				});

				if ($(".bannerTopList input[type=checkbox]").is(":checked")) {
					$(".bannerTopList input[type=checkbox]").first().trigger('click');
				}

				$(".bannerTopList input[type=checkbox]").on('click', function() {
					var id = $(this).attr('value');
					var title = $(this).attr('title');
					var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + id + " data-row-id=" + id + ">"
						+ "<div style='cursor: pointer;'> <span>" + title + "</span>" + "</div></li>";
					var type = $(this).parent().parent().attr("class");
					if (type == 'selected' || type == 'default selected') {
						$(".banner-top-list").find('li#' + id).remove();
					} else {
						$("ul.banner-top-list").append(item);
						$("ul.banner-top-list").find("li:first").trigger("click");
					}

				});

				//mulite mobile		
				$('#mBannerTopMobileId').val(null);
				$('#mBannerTopMobileId').parent().find('.select2-container').remove();
				$('#mBannerTopMobileId').find('option:first').remove();

				$('.banner-top-moblie-list').find(".list-group-item").remove();
				$("#banner-moblie-top-image").find("img").remove();

				$('#mBannerTopMobileId').attr('multiple', 'multiple');
				if ($("#mBannerTopId[multiple='multiple']").length == 0) {
					$('#mBannerTopMobileId').multiselect('unload');
				}
				$('#mBannerTopMobileId').multiselect({

					columns: 1,
					//placeholder : GROUP_LABEL,
					search: true,
					minHeight: 100,
					maxHeight: 155
				});

				if ($(".bannerTopMoblieList input[type=checkbox]").is(":checked")) {
					$(".bannerTopMoblieList input[type=checkbox]").first().trigger('click');
				}

				$(".bannerTopMoblieList input[type=checkbox]").on('click', function() {
					var id = $(this).attr('value');
					var title = $(this).attr('title');
					var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + id + " data-row-id=" + id + ">"
						+ "<div style='cursor: pointer;'> <span>" + title + "</span>" + "</div></li>";
					var type = $(this).parent().parent().attr("class");
					if (type == 'selected' || type == 'default selected') {
						$(".banner-top-moblie-list").find('li#' + id).remove();
					} else {
						$("ul.banner-top-moblie-list").append(item);
						$("ul.banner-top-moblie-list").find("li:first").trigger("click");
					}

				});


			} else {

				$('#mBannerTopId').removeAttr('multiple');
				$('#mBannerTopId').multiselect('unload');

				$('#mBannerTopId').removeClass('select2-hidden-accessible');

				$('.banner-top-list').find('.list-group-item').remove();
				if (!isNull($('#mBannerTopId').val())) {
					$('#mBannerTopId').trigger('change');
				}

				// $('#mBannerTopId').select2('destroy');
				$('#mBannerTopId').select2({ allowClear: true });
				$('#mBannerTopId').parent().find('.select2-container').css('width', '100%');

				//mobile
				$('#mBannerTopMobileId').removeAttr('multiple');
				$('#mBannerTopMobileId').multiselect('unload');

				$('#mBannerTopMobileId').removeClass('select2-hidden-accessible');
				$('.banner-top-moblie-list').find('.list-group-item').remove();
				if (!isNull($('#mBannerTopMobileId').val())) {
					$('#mBannerTopMobileId').trigger('change');
				}

				// $('#mBannerTopId').select2('destroy');
				$('#mBannerTopMobileId').select2({ allowClear: true });
				$('#mBannerTopMobileId').parent().find('.select2-container').css('width', '100%');

			}
			removeItem();
			disabled();

		}
	})
}


function checkSelectBanner(bannerCode, bannerType) {
	BANNER_PAGE_LIST.forEach(function(val, key) {
		if (val.kind == bannerCode) {

			if ('M' == val.text && bannerType == BANNER_TYPE_IMAGE) {
				loadBanner(val.text, "#mBannerTopId", ".banner-top-list", ".bannerTopList", "#banner-image");
				loadBanner(val.text, "#mBannerTopMobileId", ".banner-top-mobile-list", ".bannerTopMobileList", "#banner-moblie-top-image");

				//$('#mBannerTopId').parent().find('.select2-container').remove();
				$('#mBannerTopId').find('option:first').remove();
				$('#mBannerTopId').find("option").each(function(key, val) {
					let value = $(val).val();
					if (EDIT_DTO.mbannerTopId.indexOf(value) > -1) {
						$(val).attr("selected", "selected");
					}
				})

				$('#mBannerTopId').attr('multiple', 'multiple');
				$('#mBannerTopId').multiselect('unload');
				$('#mBannerTopId').multiselect({
					columns: 1,
					search: true,
					minHeight: 100,
					maxHeight: 155
				});


				$(".bannerTopList input[type=checkbox]").on('click', function() {
					var id = $(this).attr('value');
					var title = $(this).attr('title');
					var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + id + " data-row-id=" + id + ">"
						+ "<div style='cursor: pointer;'> <span>" + title + "</span>" + "</div></li>";
					var type = $(this).parent().parent().attr("class");
					if (type == 'selected' || type == 'default selected') {
						$(".banner-top-list").find('li#' + id).remove();
					} else {
						let $this = $(document).find(".banner-top-list").find("li:first");

					}

				});


				//mulite mobile
				$('#mBannerTopMobileId').parent().find('.select2-container').remove();
				$('#mBannerTopMobileId').find('option:first').remove();

				$('#mBannerTopMobileId').find("option").each(function(key, val) {
					let value = $(val).val();
					if (EDIT_DTO.mbannerTopMobileId.indexOf(value) > -1) {

						$(val).attr("selected", "selected");
					}
				})

				$('#mBannerTopMobileId').attr('multiple', 'multiple');
				$('#mBannerTopMobileId').multiselect('unload');
				$('#mBannerTopMobileId').multiselect({
					columns: 1,
					search: true,
					minHeight: 100,
					maxHeight: 155
				});

				$(".bannerTopMoblieList input[type=checkbox]").on('click', function() {

					var id = $(this).attr('value');
					var title = $(this).attr('title');
					var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + id + " data-row-id=" + id + ">"
						+ "<div style='cursor: pointer;'> <span>" + title + "</span>" + "</div></li>";
					var type = $(this).parent().parent().attr("class");
					if (type == 'selected' || type == 'default selected') {
						$(".banner-top-moblie-list").find('li#' + id).remove();
					} else {
						let $this = $(document).find(".banner-top-list").find("li:first");

					}
				});


			} else {
				$('#mBannerTopId').removeAttr('multiple');
				$('#mBannerTopId').multiselect('unload');

				//$('#mBannerTopId').val(null).trigger('change');

				$('#mBannerTopId').removeClass('select2-hidden-accessible');
				$('.banner-top-list').find('.list-group-item').remove();
				if (!isNull($('#mBannerTopId').val())) {
					$('#mBannerTopId').trigger('change');
				}

				$('#mBannerTopId').select2({ allowClear: true });
				$('#mBannerTopId').parent().find('.select2-container').css('width', '100%');

				//mobile
				$('#mBannerTopMobileId').removeAttr('multiple');
				$('#mBannerTopMobileId').multiselect('unload');

				$('#mBannerTopMobileId').removeClass('select2-hidden-accessible');
				$('.banner-top-moblie-list').find('.list-group-item').remove();
				if (!isNull($('#mBannerTopMobileId').val())) {
					$('#mBannerTopMobileId').trigger('change');
				}

				$('#mBannerTopMobileId').select2({ allowClear: true });
				$('#mBannerTopMobileId').parent().find('.select2-container').css('width', '100%');
			}
		}
		disabled();
	})
}

function loadBannerImage(element, tabId) {
	var id = $(element).attr("id");
	var url = "banner/homepage-setting/ajax/loadBannerImage?id=" + id;
	ajaxLoadImage(url, tabId, element);
};


function ajaxLoadImage(url, tableId, element) {

	var me = $(element);
	if (me.data('requestRunning')) {
		return;
	}
	me.data('requestRunning', true);

	$.ajax({
		type: "GET",
		url: BASE_URL + url,
		success: function(data) {
			$("#" + tableId).html(data);
			if ($('#bannerTypeDto').val() == BANNER_TYPE_VIDEO) {
				$("#banner-image").find("img").remove();
				$("#banner-moblie-top-image").find("img").remove();
			} else {
				$("#banner-image").find("video").remove();
				$("#banner-moblie-top-image").find("video").remove();
			}
		},
		complete: function(result) {
			me.data('requestRunning', false);
		},
		error: function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

/**
 * @param
 *  bannerName
 *  bannerTopList
 *  bannerImages
 *  ids
 * @Example:
 *   selectBanner("#search-filter-top-pc", ".banner-top-list", "banner-image", 100062)
 * */
function selectBanner(bannerName, bannerTopList, bannerImages, ids) {


	if ($(bannerName).css('display') != 'none') {
		$(bannerTopList).find(".list-group-item").remove();

		$("#banner-image").find("img").remove();
		$('#banner-banner-moblie-top-image').find("img").remove();

		$("#banner-image").find("video").remove();
		$('#banner-banner-moblie-top-image').find("video").remove();
		if (ids != null || ids != "") {
			$(bannerTopList).find('li#' + ids).remove();
		}
		var id = $(bannerName + " option:selected").attr('value');
		var title = $(bannerName + " option:selected").text();
		setDataSelected(id, title, bannerTopList, bannerImages);
	}
}

function setDataSelected(id, title, bannerTopList, bannerImages) {
	var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + id + ">"
		+ "<div style='cursor: pointer;'> <span>" + title + "</span>" + "</div></li>";

	$("ul" + bannerTopList).append(item);

	var numberOfChecked = $(bannerTopList + " input[type=checkbox]").filter(":checked").length;
	if (numberOfChecked == 0) {
		$(bannerImages).html("");

		$("ul.banner-top-list").find("li:first").trigger("click");

		$("ul.banner-top-moblie-list").find("li:first").trigger("click");


	}
}



function loadBanner(kind, searchFilter, bannerTopList, banner, bannerImage) {

	if (kind == 'M') {
		$(bannerTopList).find(".list-group-item").remove();
		$(searchFilter).find(".ms-options-wrap button").text("");
		$(searchFilter).find(".ms-options-wrap input[type=checkbox]").prop('checked', false);
		$(searchFilter).find(".ms-options-wrap li").removeClass("selected");
		$(searchFilter).attr('multiple', 'multiple');
		$(searchFilter).hide();
		$(".ms-options-wrap").show();

		var numberOfChecked = $(banner + " input[type=checkbox]").filter(":checked").length;
		if (numberOfChecked <= 0) {
			$(bannerImage).html("");
		}

		setItemsMulti(searchFilter, bannerTopList, bannerImage);
	} else if (kind == 'S') {
		$(bannerTopList).find(".list-group-item").remove();
		$("body").find(".ms-options-wrap button").text("");
		$("body").find(".ms-options-wrap input[type=checkbox]").prop('checked', false);
		$("body").find(".ms-options-wrap li").removeClass("selected");
		$(".ms-options-wrap").hide();
		$(searchFilter).removeAttr('multiple');
		$(searchFilter).show();
		setItems(searchFilter, bannerTopList, bannerImage);
	} else {
		$(searchFilter).removeAttr('multiple');
		$(searchFilter).show();
	}
}
function setItems(bannerName, bannerTopList, bannerImages) {
	var id = $(bannerName + " option:selected").attr('value');
	var title = $(bannerName + " option:selected").text();
	setDataSelected(id, title, bannerTopList, bannerImages);
}

function setItemsMulti(bannerName, bannerTopList, bannerImages) {
	var ids = $(bannerName).val();
	if (ids != null) {
		for (var i = 0; i < ids.length; i++) {
			var tmp = $(bannerName + " option:selected")[i];
			var id = ids[i];
			var title = $.trim($(tmp)[0].innerHTML);
			(id, title, bannerTopList, bannerImages);
		}
	}
}
function loadBannerImagePCUpdated() {
	LIST_BANNER_TOP_SELECTED.forEach(function(valkeySelected) {
		LIST_BANNER_TOP.forEach(function(val) {
			if (valkeySelected === val.id) {
				var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + val.id + " data-row-id=" + val.id + ">"
					+ "<div style='cursor: pointer;'> <span>" + val.name + "</span>" + "</div></li>";
				$("ul.banner-top-list").append(item);
			}
		})
	});

	$("ul.banner-top-list").find("li:first").trigger("click");
}
function loadBannerImageMobileUpdated() {
	LIST_BANNER_MOBILE_SELECTED.forEach(function(valkeySelected) {
		LIST_BANNER_MOBILE.forEach(function(val) {
			if (valkeySelected === val.id) {
				var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + val.id + " data-row-id=" + val.id + ">"
					+ "<div style='cursor: pointer;'> <span>" + val.name + "</span>" + "</div></li>";
				$("ul.banner-top-moblie-list").append(item);
			}
		})
	});
	$("ul.banner-top-moblie-list").find("li:first").trigger("click");
}

function getSortable() {
	var sortOderList = [];
	$('#divVideoPhysical .banner-top-list').find('li').each(function(key, val) {
		if ($(this).data("row-id")) {
			sortOderList.push($(this).data("row-id").toString());
		}
	});

	return sortOderList;
}
function getSortabeMobile() {
	var sortOderListMobile = [];
	$('#divVideoPhysical .banner-top-moblie-list').find('li').each(function(key, val) {
		if ($(this).data("row-id")) {
			sortOderListMobile.push($(this).data("row-id").toString());
		}
	});
	return sortOderListMobile;
}
function getFormObject(formId) {
	var data = {};
	$(formId).serializeArray().map(function(x) { data[x.name] = x.value; });

	return data;
}