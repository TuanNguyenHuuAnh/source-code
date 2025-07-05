$(document).keypress(function(event){
    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){
    	onClickSearch(this, event);
    }
});

$(document).ready(function($) {
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'homepage-setting/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	$('.datepicker').datepicker({
		format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
        keyboardNavigation : true,        
	});
	var idEffectedDate = $("#startDate").val();
	var idExpiredDate = $("#endDate").val();
	// changeDatepicker(idEffectedDate, idExpiredDate);
	changeDatepickerSearch(idEffectedDate, idExpiredDate);

	//on click add
	$("#addHomepageSetting").on("click", function(event) {
		var url = BASE_URL + "homepage-setting/edit";
		// Redirect to page detail
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	//on click edit
	$(".j-btn-edit").on("click", function(event) {
		editHomepage(this, event);
	});
	
	//on click delete
	$(".j-btn-delete").on("click", function(event) {
		deleteHomepage(this, event);
	});
	
	$(".j-btn-detail").on("click", function(event) {
		viewHomepageSettingDetail(this, event);
	});
	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
	$("#bannerPage, #startDate, #status, #endDate").on('keypress', function(e) {		
		if((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)){
			onClickSearch(this, event);
		}	
	});
	
	// refesh click
	$("#btnRefresh").unbind('click').bind('click', function(event) {
		refresh();
	});
	
	//double click
	/*
	$(".trEdit").on("dblclick", function(event) {
		editHomepage(this, event, $(this));
	});	
	*/
	$("#bannerPage").select2({ allowClear : false});
	$("#status").select2({ allowClear : false});
});

/**
 * Edit Homepage Setting
 * @param element
 * @param event
 * @returns
 */
function editHomepage(element, event, row) {
	event.preventDefault();
	
	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}
	
	var id = row.data("homepage-setting-id");
	var url = BASE_URL + "homepage-setting/edit?id=" + id;
	
	// Redirect to page detail
	ajaxRedirectWithCondition(url, setConditionSearch());
}


function viewHomepageSettingDetail(element, event) {
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("homepage-setting-id");
	var url = BASE_URL + "homepage-setting/detail?id=" + id;
	
	// Redirect to page detail
	ajaxRedirect(url);
}

/**
 * deleteAccount
 * 
 * @param element
 * @param event
 * @returns
 */
function deleteHomepage(element, event) {
	event.preventDefault();	
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("homepage-setting-id");
	
	popupConfirmWithButtons(MSG_DEL_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
		if (result) {
			var url = "homepage-setting/delete";
			var condition = {
				"id" : id,
				"startDate": $("#startDate").val(),
				"endDate" : $("#endDate").val(),
				"status": $("#status").val(),
				"bannerPage": $("#bannerPage").val()
			}
			
			ajaxSubmit(url, condition, event);
			
		}			
	});
}

/**
 * on click button search
 */
function onClickSearch(element, event) {
	
	setDataSearchHidd();

	ajaxSearch("homepage-setting/ajaxList", setConditionSearch(), "tableList", element, event);
}

/**
 * setConditionSearch
 */
function setConditionSearch() {
	var condition = JSON.parse($("#conditionHidd").val());
	return condition;
}

/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	var condition = {};
	condition["startDate"] = $("#startDate").val();
	condition["endDate"] = $("#endDate").val();
	condition["status"] = $("#status").val();
	condition["bannerPage"] = $("#bannerPage").val();
	
	$("#conditionHidd").val(JSON.stringify(condition));
}

/**
 * clear form
 */
function refresh() {
	$("#startDate").val('');
	$("#endDate").val('');
	$("#status").val('');
	$("#bannerPage").val('');
	
	$('#btnSearch').trigger('click');
}