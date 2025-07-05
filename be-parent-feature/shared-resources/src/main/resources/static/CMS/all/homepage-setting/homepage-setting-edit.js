$(document).ready(function($) {
	
	/*$("#btn-common").on("click", () => {
		$('#close').trigger('click');
	});*/
	
	var value = $('option:selected',this).val();
	var kind;
	if (value != null && value != undefined && value != ""){
		kind = value.split(',')[1];
	}
	var prevIdTop;
	var prevIdMobile;

	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		search : true,
		selectAll: true
	});
	
	if (kind != undefined){
		loadBannerPC(kind, prevIdTop);
		loadBannerMobile(kind, prevIdMobile);
	}
	
	// event for banner for page
	$('#bannerPageSelect').on('change', function(){
		kind = $('option:selected',this).val().split(',')[1];
		loadBannerPC(kind, prevIdTop);
		loadBannerMobile(kind, prevIdMobile);
	});
	
	selectBanner("#search-filter-top-pc", ".banner-top-list", "banner-image");
	$('#search-filter-top-pc').on('change', function(){
		selectBanner("#search-filter-top-pc", ".banner-top-list", "banner-image");
	});
	
	selectBanner("#search-filter-top-mobile", ".banner-top-moblie-list", "banner-moblie-top-image");
	$('#search-filter-top-mobile').on('change', function(){
		selectBanner("#search-filter-top-mobile", ".banner-top-moblie-list", "banner-moblie-top-image");
	});
	
	$('#bannerFix').combobox({
		onSelect: function(param){
			var url = "homepage-setting/ajax/loadBannerImage?id=" + param.value;
			ajaxLoadImage(url, "banner-fix-image", this);
		}
	});
	
	$('#bannerFixMobile').combobox({
		onSelect: function(param){
			var url = "homepage-setting/ajax/loadBannerImage?id=" + param.value;
			ajaxLoadImage(url, "banner-mobile-fix-image", this);
		}
	});
	
	$(".bannerTopList input[type=checkbox]").click(function(){
		  var id = $(this).attr('value');
		  var title = $(this).attr('title');
		 
		  var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + id + ">"
		  + "<div style='cursor: pointer;'> <span>"+ title + "</span>" + "</div></li>";
		  
		  var type = $(this).parent().parent().attr("class");
		  if (type == 'selected' || type == 'default selected') {
			  $(".banner-top-list").find('li#' + id).remove();
		  } else {
			  $("ul.banner-top-list").append(item);
			  $("ul.banner-top-list").find("li:first").trigger("click");
		  }
		  var numberOfChecked = $(".bannerTopList input[type=checkbox]").filter(":checked").size();		  
		  if (numberOfChecked == 0 || $('.banner-top-list li.active').size() == 0){
			  $("#banner-image").html("");
		  }
	});
	
	$(".bannerTopMoblieList input[type=checkbox]").click(function(){
		  var id = $(this).attr('value');
		  var title = $(this).attr('title');
		  

		  var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + id + ">"
		  + "<div style='cursor: pointer;'> <span>"+ title + "</span>" + "</div></li>";
		  
		  var type = $(this).parent().parent().attr("class");
		  
		  if (type == 'selected' || type == 'default selected') {			 
			  $(".banner-top-moblie-list").find('li#' + id).remove();
		  } else {
			  $("ul.banner-top-moblie-list").append(item);
			  $("ul.banner-top-moblie-list").find("li:first").trigger("click");
		  }
		  var numberOfChecked = $(".bannerTopMoblieList input[type=checkbox]").filter(":checked").size();
		  if (numberOfChecked == 0 || $('.banner-top-moblie-list.active').size() == 0){
			  $("#banner-moblie-top-image").html("");
		  }
	});
	
	/*var idEffectedDate = $("#effectiveDateFrom").val();
	if (idEffectedDate == ""){
		idEffectedDate = new Date();
		idEffectedDate.setDate(idEffectedDate.getDate());
	}*/
	
	/*$('.datepicker-to').datepicker({
    	format: DATE_FORMAT
    });
	
	var idExpiredDate = $("#effectiveDateTo").val();
	changeDatepicker(idEffectedDate, idExpiredDate);*/
	$('.datepicker > input').attr("placeholder", COMMON_DATE_PICKER_DATE_FORMAT);
	$('.date').datepicker({
		format: 'dd/mm/yyyy',
		changeMonth : true,
		changeYear : true,
		forceParse: false,
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : '${sessionScope.localeSelect}',
		todayHighlight : true,
		keyboardNavigation : true, 
		onRender : function(date) {
		}
	});
	
	
	validDatePicker2('#effectiveDateFrom', 1000, 9999);
	validDatePicker2('#effectiveDateTo', 1000, 9999);
	changeDatepicker3('#effectiveDateFrom', '#effectiveDateTo');

	// on click cancel
	$('#cancel').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "homepage-setting/list";

		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});

	// on click list
	$('#linkList').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "homepage-setting/list";

		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	var description =  $("textarea[id^='editor']");
	
	// on click button save, or do process
	$(".process-btn").on('click', function(event) {
		event.preventDefault();
		$('.close').trigger('click');
		
		// set button id
		$("#buttonId").val(this.id);
		
		// set button action
		$("#buttonAction").val($(this).find('span').text());
		
		// set items role
		$("#currItem").val($(this).find('span').data("curritem"));
		
		updateElementEditor();
		
		if ($(".j-form-validate").valid()) {
			var bannerTopIds = "";
			
			$('.banner-top-list li').each(function(index, element) {
				bannerTopIds = bannerTopIds + "&bannerTop="+ $(element).attr("id");
			});
			
			var bannerTopMobileIds = "";
			
			$('.banner-top-moblie-list li').each(function(index, element) {
				bannerTopMobileIds = bannerTopMobileIds + "&bannerTopMobile="+ $(element).attr("id");
			});
			
			
			$("#action").val(false);
			var url = "homepage-setting/edit";
			var condition = $("#homepageForm").serialize();
			
			condition = condition + bannerTopIds;
			condition = condition + bannerTopMobileIds;
			
			ajaxSubmit(url, condition, event);
		}
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

	//on click add
	$("#addHomepageSetting").on("click", function(event) {
		var url = BASE_URL + "homepage-setting/edit";
		// Redirect to page detail
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	loadBannerImagePCUpdated();
	
	loadBannerImageMobileUpdated();
});

/**
 * update element editor
 */
function updateElementEditor() {
	for (instance in CKEDITOR.instances) {
		CKEDITOR.instances[instance].updateElement();
	}
}

function loadBannerImage(element, tabId){	
	var id = $(element).attr("id");	
	var url = "homepage-setting/ajax/loadBannerImage?id=" + id;	
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

/**
 * @param
 *  bannerName
 *  bannerTopList
 *  bannerImages
 *  ids
 * @Example:
 *   selectBanner("#search-filter-top-pc", ".banner-top-list", "banner-image", 100062)
 * */
function selectBanner(bannerName, bannerTopList, bannerImages, ids){
	if($(bannerName).css('display') != 'none'){
		$(bannerTopList).find(".list-group-item").remove();
		$(bannerTopList).find("." + bannerImages).remove();
		if (ids != null || ids != ""){
			$(bannerTopList).find('li#' + ids).remove();
		}
		var id = $(bannerName +" option:selected").attr('value');
		var title = $(bannerName + " option:selected").text();
				
		setDataSelected(id, title, bannerTopList, bannerImages);
	}
}

function setItems(bannerName, bannerTopList, bannerImages, prevId){
	var id = $(bannerName + " option:selected").attr('value');
	var title = $(bannerName + " option:selected").text();
	setDataSelected(id, title, bannerTopList, bannerImages);
}

function setItemsMulti(bannerName, bannerTopList, bannerImages, prevId){
	var ids = $(bannerName).val();
	if (ids != null){
		for (var i = 0; i < ids.length; i++) {
			var tmp = $(bannerName  + " option:selected")[i];
			var id = ids[i];
			var title = $.trim($(tmp)[0].innerHTML);
			(id, title, bannerTopList, bannerImages);
		}
	}
}

function setDataSelected(id, title, bannerTopList, bannerImages){
	var item = "<li class='ui-state-default list-group-item list-group-item-info ui-sortable-handle' id=" + id + ">"
	+ "<div style='cursor: pointer;'> <span>"+ title + "</span>" + "</div></li>";
	  
	$("ul"+ bannerTopList).append(item);
	
	var numberOfChecked = $(bannerTopList + " input[type=checkbox]").filter(":checked").size();
		if (numberOfChecked == 0){
		  $(bannerImages).html("");
		}
		
	$("ul.banner-top-list").find("li:first").trigger("click");
	
	$("ul.banner-top-moblie-list").find("li:first").trigger("click");

}

function loadBannerPC(kind, prevId){
	loadBanner(kind, prevId, "#search-filter-top-pc", ".banner-top-list", ".bannerTopList", "#banner-image");
}

function loadBannerMobile(kind, prevId){
	loadBanner(kind, prevId, "#search-filter-top-mobile", ".banner-top-moblie-list", ".bannerTopMoblieList", "#banner-moblie-top-image");
}

function loadBanner(kind, prevId, searchFilter, bannerTopList, banner, bannerImage){
	if (kind == 'M'){
		$(bannerTopList).find(".list-group-item").remove();
		$(searchFilter).find(".ms-options-wrap button").text("");
		$(searchFilter).find(".ms-options-wrap input[type=checkbox]").prop('checked', false);
		$(searchFilter).find(".ms-options-wrap li").removeClass("selected");
		$(searchFilter).attr('multiple','multiple');
		$(searchFilter).hide();
		$(".ms-options-wrap").show();
		var numberOfChecked = $(banner + " input[type=checkbox]").filter(":checked").size();
		if (numberOfChecked <=0){
			  $(bannerImage).html("");
		}
		
		setItemsMulti(searchFilter, bannerTopList, bannerImage, prevId);
	}else if (kind =='L'){
		$(bannerTopList).find(".list-group-item").remove();
		$("body").find(".ms-options-wrap button").text("");
		$("body").find(".ms-options-wrap input[type=checkbox]").prop('checked', false);
		$("body").find(".ms-options-wrap li").removeClass("selected");
		$(".ms-options-wrap").hide();
		$(searchFilter).removeAttr('multiple');
		$(searchFilter).show();
		setItems(searchFilter, bannerTopList, bannerImage, prevId);
	}else{
		$(searchFilter).removeAttr('multiple');
		$(searchFilter).show();
	}
}

function setConditionSearch() {
	var condition = {};
	condition["startDate"] = $("#startDateSearch").val();
	condition["endDate"] = $("#endDateSearch").val();
	condition["status"] = $("#statusSearch").val();
	condition["bannerPage"] = $("#bannerPageSearch").val();
	
	return condition;
}
function loadBannerImagePCUpdated(){
	$(".bannerTopList input[type=checkbox]").filter(":checked").trigger('click').trigger('click');
	$("ul.banner-top-list").find("li:first").trigger("click");
}

function loadBannerImageMobileUpdated(){
	$(".bannerTopMoblieList input[type=checkbox]").filter(":checked").trigger('click').trigger('click');
	$("ul.banner-top-moblie-list").find("li:first").trigger("click");
}