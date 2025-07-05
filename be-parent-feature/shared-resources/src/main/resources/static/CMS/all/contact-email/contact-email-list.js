$(document).keypress(function(event){

    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){
    	onClickSearch(this, event);
    	$('.close').trigger('click');
    }

});

$(document).ready(function() {
	
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'contact/email/list',
		type : 'POST',
		setData : setConditionSearch
	});
	
	//on click delete
	$(".j-btn-delete").on("click", function(event) {
		deleteCategory(this, event);
	});
	
	$(".j-btn-edit").on("click", function(event) {
		viewCategory(this, event);
	});

	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	
	/*$('.datepicker').datepicker({
		format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
        keyboardNavigation : true
	});
	
	var idEffectedDate = $("#fromDateSearch").val();
	var idExpiredDate = $("#toDateSearch").val();
	changeDatepickerSearch(idEffectedDate, idExpiredDate);*/
	
	//$('.datepicker > input').attr("placeholder", COMMON_DATE_PICKER_DATE_FORMAT);
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
	
	
	validDatePicker2('#fromDateSearch', 1000, 9999);
	validDatePicker2('#toDateSearch', 1000, 9999);
	changeDatepicker3('#fromDateSearch', '#toDateSearch');
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
		$('.close').trigger('click');
	});
	
	$("#btn-export").click(function(e){
		event.preventDefault();		

		var linkExport = BASE_URL + "contact/email/exportList";
//		 condition
		var condition = setConditionSearch();
		
		doExportExcel("#id-form-search", linkExport);
	})
	
	// on click search
	$("#btnRefresh").on('click', function(event) {
		refresh();
	});
	
	$("#customerId").unbind('change').bind('change', function(){
		var customerId = $("#customerId").val(); 
		
		$.ajax({
			type	: 'POST',
			url		: BASE_URL + 'contact/email/customerChange',
			data	:
			{
				customerId:	customerId
			}
		}).done(function(data){
			console.log(data);
			
			$("#service").find('option:not(:first)').remove();
			
			$.each(jQuery.parseJSON(data), function(key, val) {
				$('#service').append('<option value="' + val.linkAlias + '">' + val.title + '</option>');
			});
		});
	});
	/*
	$(".trEdit").on("dblclick", function(event) {
		viewCategory(this, event, $(this));
	});	
	*/
	
	$("#processingStatus, #customerId, #service, #motive").select2({ allowClear : false});
	
	
});

function viewCategory(element, event, row) {
	event.preventDefault();
	
	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}
	
	var id = row.data("email-id");
	
	var url = BASE_URL + "contact/email/detail?id=" + id;
	
	// Redirect to page detail
	ajaxRedirectWithCondition(url, setConditionSearch());
}

/**
 * deleteIntroduction
 * 
 * @param element
 * @param event
 * @returns
 */
function deleteCategory(element, event) {
	event.preventDefault();
	popupConfirm(MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var row = $(element).parents("tr");
			var id = row.data("email-id");

			var url ="contact/email/list/delete?id=" + id;
			var values = [];
			ajaxSubmit(url, values, event);	
		}
	});
}

/**
 * on click button search
 */
function onClickSearch(element, event) {
	event.preventDefault();
	
	ajaxSearch("contact/email/list", setConditionSearch(), "tableList", element, event);
}

/**
 * setConditionSearch
 */
function setConditionSearch() {
	setDataSearchHidd();
	
	var condition = {};
	condition["searchValue"] = $("#fieldSearchHidd").val();
	condition["searchKeyIds"] = $("#fieldValuesHidd").val();
	condition["fromDate"] = $("#fromDateHidd").val();
	condition["toDate"] = $("#toDateHidd").val();
	condition["processingStatus"] = $("#processingStatusHidd").val();
	condition["service"] = $("#serviceHidd").val();
	condition["motive"] = $("#motiveHidd").val();
	condition["emailSubject"] = $("#emailSubjectHidd").val();
	condition["fullName"] = $("#fullNameHidd").val();
	condition["customerId"] = $("#customerIdHidd").val();
	return condition;
}

/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	$("#fieldSearchHidd").val($("#searchValue").val());
	$("#fieldValuesHidd").val($("select[name=searchKeyIds]").val());
	$("#fromDateHidd").val($("#fromDateSearch").val());
	$("#toDateHidd").val($("#toDateSearch").val());
	$("#processingStatusHidd").val($("#processingStatus").val());
	$("#serviceHidd").val($("#service").val());
	$("#motiveHidd").val($("#motive").val());
	$("#emailSubjectHidd").val($("#emailSubject").val());
	$("#fullNameHidd").val($("#fullName").val());
	$("#customerIdHidd").val($("#customerId").val());
}

function refresh(){
	$("#fromDateSearch").val('');
	$("#toDateSearch").val('');
	$("#processingStatus").val('');
	$("#service").val('');
	$("#motive").val('');
	$("#emailSubject").val('');
	$("#fullName").val('');
	$("#customerId").val('');
	
	$('#btnSearch').trigger('click');
}

function viewCategory1(id) {
	
	debugger;
	
//	var element = $($this);
//	
//	var id = element.data("email-id");
	
	var url = BASE_URL + "contact/email/detail?id=" + id;
	
	// Redirect to page detail
	ajaxRedirectWithCondition(url, setConditionSearch());
}