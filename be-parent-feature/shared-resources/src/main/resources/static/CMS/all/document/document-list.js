$(document).keypress(function(event){

    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){
    	onClickSearch(this, event);
    	$('.close').trigger('click');
    }

});
$(document).ready(function($) {
//	$('#categoryList').combotree({
//		editable : false,
//		onChange: function (dataValue) {
//			$('#categoryId').val(dataValue);			
//        }
//    });	
//	if( CATEGORY_LIST != null && CATEGORY_LIST != '' ) {
//		$('#categoryList').combotree('loadData', jQuery.parseJSON(CATEGORY_LIST));
//	}	
//
//	var treeTmp = $('#categoryList').combotree('tree');
//	if ($('#categoryId').val() != null && $('#categoryId').val() != '' ) {
//		var node = treeTmp.tree('find', $('#categoryId').val());
//		treeTmp.tree('select', node.target);
//		$('#categoryList').combotree('setText', node.text);		
//	} else {
//		var node = treeTmp.tree('find', -1);
//		treeTmp.tree('select', node.target);
//		$('#categoryList').combotree('setText', node.text);
//		$('#categoryId').val("");
//	}
	
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + CUSTOMER_ALIAS + '/document/ajax/list',
		type : 'POST',
		setData : setConditionSearch
	});
	
	//on click add
	$("#addDocument").on("click", function(event) {
		// var url = BASE_URL + CUSTOMER_ALIAS + "/document/add";
		var url = BASE_URL + CUSTOMER_ALIAS + "/document/edit";
		// Redirect to page detail
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	//on click edit
	$(".j-btn-edit").on("click", function(event) {
		editDocument(this, event);
	});
	/*
	$(".trEdit").on("dblclick", function(event) {
		editDocument(this, event, $(this));
	});
	*/
	//on click delete
	$(".j-btn-delete").on("click", function(event) {
		deleteDocument(this, event);
	});
	
	$(".j-btn-detail").on("click", function(event) {
		viewDocument(this, event);
	});

	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	
	// on click search
	setDataSearchHidd();
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
		$('.close').trigger('click');
	});
	$("#btnRefresh").unbind('click').bind('click', function(event) {
		refresh();
	});
	
	$('#btnExport').click(function() {
		var linkExport = BASE_URL + CUSTOMER_ALIAS  + "/document/export-excel";
//		 condition
		var condition = setConditionSearch();
//		 do export excel
		doExportFileExcel (linkExport, "token", condition);
	});
	
	
	$("#status, #enabled, #typeId").select2({ allowClear : false});
	
	$('#btnSort').on('click', function(event){
		var url = BASE_URL + CUSTOMER_ALIAS + "/document/list/sort";
//		// Redirect to page detail
		ajaxRedirect(url);
	});
});

/**
 * editDocument
 * 
 * @param element
 * @param event
 * @returns
 */
function editDocument(element, event, row) {
	event.preventDefault();
	
	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}  
	var id = row.data("document-id");
	var url = BASE_URL + CUSTOMER_ALIAS + "/document/edit?id=" + id;
	
	// Redirect to page detail
	ajaxRedirectWithCondition(url, setConditionSearch());
}


function viewDocument(element, event) {
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("document-id");
	var url = BASE_URL + CUSTOMER_ALIAS + "/document/detail?id=" + id;
	
	// Redirect to page detail
	ajaxRedirect(url);
}

/**
 * deleteDocument
 * 
 * @param element
 * @param event
 * @returns
 */
function deleteDocument(element, event) {
	event.preventDefault();
	popupConfirmWithButtons(MSG_DEL_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result){
		if (result) {
			var row = $(element).parents("tr");
			var id = row.data("document-id");
			var url = CUSTOMER_ALIAS + "/document/delete?id=" + id;
			
			var values = [];
			ajaxSubmit(url, values, event);		
		}
	});
}

/**
 * on click button search
 */
function onClickSearch(element, event) {
	
	setDataSearchHidd();
	
	ajaxSearch(CUSTOMER_ALIAS + "/document/ajax/list", setConditionSearch(), "tableList", element, event);
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
	$("#fieldSearchHidd").val($("#searchValue").val());
	$("#fieldValuesHidd").val($("select[name=searchKeyIds]").val());
	
	var condition = {};
	condition["name"] = $("#name").val();
	condition["code"] = $("#code").val();
	condition["status"] = $("#status").val();
	condition["enabled"] = $("#enabled").val();
	condition["typeId"] = $("#typeId").val();
	$("#conditionHidd").val(JSON.stringify(condition));
}

/**
 * clear form
 */
function refresh() {
	$("#code").val('');
	$("#name").val('');
	$("#status").val('').change();
	$("#enabled").val('').change();
	$("#typeId").val('').change();

	/*var t = $('#categoryList').combotree('tree');
	var node = t.tree('find', -1);
	t.tree('select', node.target);
	$('#categoryList').combotree('setText', node.text);
	$('#categoryId').val("");*/
	
	$('#btnSearch').trigger('click');
}