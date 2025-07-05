$(document).keypress(function(event){

    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){
    	onClickSearch(this, event);
    }
});

$(document).ready(function($) {
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'chat/ajaxList-offline',
		type : 'POST',
		setData : setConditionSearch
	});
	
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	$("#configLink").on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "chat/config-offline";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	//on click detail
	$(".j-btn-detail").on("click", function(event) {
		event.preventDefault();
		
//		// Prepare data
		var row = $(this).parents("tr");
		var id = row.data("id");
		var url = BASE_URL + "chat/detail-offline?id=" + id;		
//		// Redirect to page detail
		ajaxRedirect(url);
	});

	//on click detail
	$(".j-btn-mail").on("click", function(event) {
		event.preventDefault();
		
//		// Prepare data
		var row = $(this).parents("tr");
		var id = row.data("id");
		var url = BASE_URL + "chat/edit-offline?id=" + id;		
//		// Redirect to page detail
		ajaxRedirect(url);
	});
	
	$(".j-btn-delete").on("click", function(event) {
		event.preventDefault();

//		// Prepare data
//		var row = $(this).parents("tr");
//		var id = row.data("recruitment-id");
//		popupConfirm(MSG_DEL_CONFIRM, function(result) {
//			if (result) {
//				var url = "recruitment/delete";
//				var data = {
//					"id" : id
//				}
//				ajaxSubmit(url, data, event);
//			}
//		});
	});
	
	
	// on click add
	$("#add").on("click", function(event) {
		event.preventDefault();
		var url = BASE_URL + "chat/edit-offline";
		// Redirect to page add
		ajaxRedirect(url);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
	
	// refesh click
	$("#btnRefresh").unbind('click').bind('click', function(event) {
		refresh();
	});
	
	$("#exportHistory").click(function(){
		event.preventDefault();		
		$("#formSearch").attr("action", BASE_URL + "chat/export-list-offline");
		$("#formSearch").submit();	
	});
	
	$('.datepicker').datepicker({
		format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
        keyboardNavigation : true
	});
	
	var idEffectedDate = $("#fromDate").val();
	var idExpiredDate = $("#toDate").val();
	changeDatepicker(idEffectedDate, idExpiredDate);
});

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidd").val();
	condition["fieldValues"] = $("#fieldValuesHidd").val();
	condition["createdDate"] = $("#createdDate").val();
	condition["fromDate"] = $("#fromDate").val();
	condition["toDate"] = $("#toDate").val();
	return condition;
}

function setDataSearchHidd() {
	$("#fieldSearchHidd").val($("#fieldSearch").val());
	$("#fieldValuesHidd").val($("select[name=fieldValues]").val());
}

function onClickSearch(element, event) {
	setDataSearchHidd();
	ajaxSearch("chat/ajaxList-offline", setConditionSearch(), "tableList", this, event);
}

function refresh() {
	$("#fieldSearch").val('');
	$("select[name=fieldValues]").val('');
	$('.selected').removeClass("selected");
	$('#selectDiv input[type="checkbox"]').prop('checked', false);
	$('.ms-options-wrap').find('button').text(SEARCH_LABEL);
	$("#createdDate").val('');
	$("#fromDate").val('');
	$("#toDate").val('');
	
	$('#btnSearch').trigger('click');
}