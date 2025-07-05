$(document).keypress(function(event){

    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){
    	onClickSearch(this, event);
    }
    
});

$(document).ready(function($) {	
	$('.datepicker').datepicker({
		format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
        keyboardNavigation : true
	});
	
//	$(".ms-options-wrap input").unbind('keypress').bind('keypress', function(event) {
//	    if(event.keyCode == 13){
//	    	ajaxSearch("chat/ajax-history-list", setConditionSearch(), "tableList", this, event);
//	    }
//	});
	
	$('#fieldSearch').unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	ajaxSearch("chat/ajax-history-list", setConditionSearch(), "tableList", this, event);
	    }
	});
	
	$('#fromDate').unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	ajaxSearch("chat/ajax-history-list", setConditionSearch(), "tableList", this, event);
	    }
	});
	
	$('#toDate').unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	ajaxSearch("chat/ajax-history-list", setConditionSearch(), "tableList", this, event);
	    }
	});

	var idEffectedDate = $("#fromDateSearch").val();
	var idExpiredDate = $("#toDateSearch").val();
	changeDatepicker(idEffectedDate, idExpiredDate);
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'chat/ajax-history-list',
		type : 'POST',
		setData : setConditionSearch,
		"scrollX": true,
		"autoWidth": true,
		"scrollCollapse": true,
	});
	
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : "Điều kiện tìm kiếm",
		search : true
	});
	
	//on click detail
	$(".j-btn-detail").on("click", function(event) {
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		var agent = row.data("agent");
		var clientid = row.data("clientid");
		
		var url = BASE_URL + "chat/history/detail?agent=" + agent + "&clientid=" + clientid;
		
		// Redirect to page detail
		redirect(url);
	});
	
	//on click edit
	$(".j-btn-edit").on("click", function(event) {
		event.preventDefault();
		
//		// Prepare data
		var row = $(this).parents("tr");
		var id = row.data("clientid");
		var url = BASE_URL + "chat/edit-customer?clientid=" + id;		
//		// Redirect to page detail
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		
		onClickSearch(this, event);
		
	});
	
	$(".j_branchname").each(function(){
		var ele = $(this).text();
		$(this).html(ele);
	});
	
	$("#exportHistory").click(function(){
		event.preventDefault();		
		$("#formSearch").attr("action", BASE_URL + "chat/export-history");
		$("#formSearch").submit();	
	});
	
	// refesh click
	$("#btnRefresh").unbind('click').bind('click', function(event) {
		refresh();
	});
	
});

function setDataSearchHidd() {
	$("#fieldSearchHidd").val($("#fieldSearch").val());
	$("#fieldValuesHidd").val($("select[name=fieldValues]").val());
	$("#fromDateSearch").val($("#fromDate").val());
	$("#toDateSearch").val($("#toDate").val());
	
}

function setConditionSearch() {
	setDataSearchHidd();
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidd").val();
	condition["fieldValues"] = $("#fieldValuesHidd").val();
	condition["fromDate"] = $("#fromDateSearch").val();
	condition["toDate"] = $("#toDateSearch").val();
	return condition;
}

function onClickSearch(element, event) {
	setDataSearchHidd();
	ajaxSearch("chat/ajax-history-list", setConditionSearch(), "tableList", this, event);
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