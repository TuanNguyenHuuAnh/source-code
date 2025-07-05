$(document).ready(function($) {
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + CUSTOMER_ALIAS + '/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	//on click add
	$("#addNew").on("click", function(event) {
		var url = BASE_URL + CUSTOMER_ALIAS + "/edit";
		// Redirect to page detail
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	//on click edit
	$(".j-btn-edit").on("click", function(event) {
		editCustomer(this, event);
	});
	/*
	$(".trEdit").on("dblclick", function(event) {
		editCustomer(this, event, $(this));
	});	
	*/
	//on click delete
	$(".j-btn-delete").on("click", function(event) {
		deleteCustomer(this, event);
	});
	
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detail(this, event);
	});

	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true,
		showCheckbox: false,
		multiple: false
	});
	
	// on click search
	setDataSearchHidd();
	
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
		$('.close').trigger('click');
	});
	
	$("#status, #code, #title").on('keypress', function(e) {		
		if((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)){
			onClickSearch(this, event);
			$('.close').trigger('click');
		}	
	});
	
	// refesh click
	$("#btnRefresh").unbind('click').bind('click', function(event) {
		refresh();
	});
	
	$('#btnExport').click(function() {
		var linkExport = BASE_URL + CUSTOMER_ALIAS  + "/export-excel";
		doExportExcel("#formSearch", linkExport);
	});
	
});

/**
 * editCustomer
 * 
 * @param element
 * @param event
 * @returns
 */
function editCustomer(element, event, row) {
	event.preventDefault();
	
	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}
	var id = row.data("product-id");
	var url = BASE_URL + CUSTOMER_ALIAS + "/edit?id=" + id;
	
	// Redirect to page detail
	ajaxRedirectWithCondition(url, setConditionSearch());
}

/**
 * deleteCustomer
 * 
 * @param element
 * @param event
 * @returns
 */
function deleteCustomer(element, event) {
	event.preventDefault();
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("product-id");
	popupConfirmWithButtons(MSG_DEL_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result){
		if (result) {
			var url = CUSTOMER_ALIAS + "/delete";
			var data = {
				"id" : id
			}
			ajaxSubmit(url, data, event);
		}
	});
}

/**
 * on click button search
 */
function onClickSearch(element, event) {
	
	setDataSearchHidd();
	
	event.preventDefault();
	blockbg();	
	var me = $(element);
	if (me.data('requestRunning')) {
		return;
	}
	me.data('requestRunning', true);
	
	$.ajax({
		type : "POST",
		url : BASE_URL + CUSTOMER_ALIAS + "/ajaxList",
		data : setConditionSearch(),
		success : function(data) {
			$("#tableList").html(data);			
		},
		complete : function(result) {
			me.data('requestRunning', false);
	        	unblockbg();
	        },
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

/**
 * setConditionSearch
 */
function setConditionSearch() {
	return JSON.parse($("#conditionHidd").val());
}

/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	var condition = {};
	condition["status"] = $("#status").val();
	condition["code"] = $("#code").val();
	condition["title"] = $("#title").val();
	condition["enabled"] = $("#enabled").val();
	
	$("#conditionHidd").val(JSON.stringify(condition));
}

/**
 * clear form
 */
function refresh() {
	$("#status").val('');
	$("#code").val('');
	$("#title").val('');
	$("#microsite").val('');
	$("#enabled").val('');
	
	$('#btnSearch').trigger('click');
}