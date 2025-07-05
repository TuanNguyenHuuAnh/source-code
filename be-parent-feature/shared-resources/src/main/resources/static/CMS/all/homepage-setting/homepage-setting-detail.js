$(document).ready(function($) {
	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : "Banner",
		search : true
	});
	console.log("DDD");

	// on click cancel
	$('#cancel').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "homepage-setting/list";

		// Redirect to page list
		ajaxRedirect(url);
	});

	// on click list
	$('#linkList').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "homepage-setting/list";

		// Redirect to page list
		ajaxRedirect(url);
	});

	$(document).on('click',".banner-top-list li", function(event){
		event.preventDefault();
		$('.banner-top-list li').each(function(index, element) {
			if($(element).hasClass("active")){
				$(element).removeClass("active");
			}
		});
		$(this).addClass("active");
		loadBannerImage(this, "banner-image");
	});
	
	$(document).on('click',".banner-top-moblie-list li", function(event){
		event.preventDefault();
		$('.banner-top-moblie-list li').each(function(index, element) {
			if($(element).hasClass("active")){
				$(element).removeClass("active");
			}
		});
		$(this).addClass("active");
		loadBannerImage(this, "banner-moblie-top-image");
	});
	
	$('.banner-top-list li').each(function(){
		var id = $(this).attr('id');
		var url = "homepage-setting/ajax/loadBannerImage?id=" + id;		
		ajaxLoadImage(url, "banner-image", this);
		return false;
	})
	
	$('.banner-top-moblie-list li').each(function(){
		var id = $(this).attr('id');
		var url = "homepage-setting/ajax/loadBannerImage?id=" + id;		
		ajaxLoadImage(url, "banner-moblie-top-image", this);
		return false;
	})
	// load image banner middle
	$('#bannerFix').combobox({		
		onSelect: function(param){
			var url = "homepage-setting/ajax/loadBannerImage?id=" + param.value;	
			console.log(url);
			ajaxLoadImage(url, "banner-fix-image", this);
		}
	})
	// load image banner middle mobile
	$('#bannerFixMobile').combobox({
		onSelect: function(param){
			var url = "homepage-setting/ajax/loadBannerImage?id=" + param.value;
			console.log(url);
			ajaxLoadImage(url, "banner-mobile-fix-image", this);
		}
	})
	
	// Post approve homePageSetting	
	$('#btn-approve').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			$("#action").val(true);
			
			var url = "homepage-setting/detail";
			var condition = $("#homepageForm").serialize();
			
			ajaxSubmit(url, condition, event);
		}	
	});
	// Post reject job	
	$('#btn-return').on('click', function(event) {
		$("textarea[id='comment']").addClass("j-required");
		if ($(".j-form-validate").valid()) {	
			$("#action").val(false);
			
			var url = "homepage-setting/detail";
			var condition = $("#homepageForm").serialize();
			
			ajaxSubmit(url, condition, event);
		}			
	});	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "homepage-setting/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "homepage-setting/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});
	// on click cancel
	$('#cancel').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "homepage-setting/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
});

function loadBannerImage(element, tabId){
	var url = "homepage-setting/ajax/loadBannerImage?id=" + $(element).attr("id");
	console.log(url);
	ajaxLoadImage(url, tabId, element);
};

function ajaxLoadImage(url, tableId, element) {
	

	var me = $(element);
	if (me.data('requestRunning')) {
		return;
	}
	me.data('requestRunning', true);

	$.ajax({
		type : "GET",
		url : BASE_URL + url,
		success : function(data) {
			console.log(data);
			$("#" + tableId).html(data);
		},
		complete : function(result) {
			me.data('requestRunning', false);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}