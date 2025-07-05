$(document).keypress(function(event){

    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){
    	onClickSearch(this, event);
    	$('.close').trigger('click');
    }

});

$(document).ready(function($) {
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'introduction/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	//on click add
	$("#addIntroduction").on("click", function(event) {
		var url = BASE_URL + "introduction/edit";
		// Redirect to page detail
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	//on click edit
	$(".j-btn-edit").on("click", function(event) {
		editIntroduction(this, event);
	});
	
	//on click delete
	$(".j-btn-delete").on("click", function(event) {
		deleteIntroduction(this, event);
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
		$('.close').trigger('click');
	});
	
	$('#btnSort').on('click', function(event){
		var url = BASE_URL + "introduction/list/sort";
		// Redirect to page detail
		ajaxRedirect(url);
	});
	
	//double click
	/*
	$(".trEdit").on("dblclick", function(event) {
		editIntroduction(this, event, $(this));
	});	
	*/
	$("#btnRefresh").on('click', function(event) {
		refresh();
	});
	
	//on click edit
	
	$("#statusText").on("select2:close", function(){
		$("#statusText").focus();
	});
	$("#categoryId").on("select2:close", function(){
		$("#categoryId").focus();
	});
	
	$("#categoryId, #statusText, #code, #title").on('keypress', function(e) {		
		if((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)){
			onClickSearch(this, event);
		}	
	});
	
	$('#btnExport').click(function() {
		var linkExport = BASE_URL +  "introduction/export-excel";
		doExportExcel("#formSearch", linkExport);
	});
	
	$("#status, #categoryId, #enabled").select2({ allowClear : false});
});

/**
 * editIntroduction
 * 
 * @param element
 * @param event
 * @returns
 */
function editIntroduction(element, event, row) {
	event.preventDefault();
	
	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}
	
	var id = row.data("introduction-id");
	var url = BASE_URL + "introduction/edit?id=" + id;
	
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
function deleteIntroduction(element, event) {
	event.preventDefault();
	popupConfirmWithButtons(MSG_DEL_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
		if (result) {
			var row = $(element).parents("tr");
			var id = row.data("introduction-id");
			var url = "introduction/delete?id=" + id;
			
			var values = setConditionSearch();
			ajaxSubmit(url, values, event);		
		}
	});
}

/**
 * on click button search
 */
function onClickSearch(element, event) {
	
	setDataSearchHidd();
	
	ajaxSearch("introduction/ajaxList", setConditionSearch(), "tableList", element, event);
}

/**
 * setConditionSearch
 */
function setConditionSearch() {
	var condition = {};
	condition["code"] = $("#code").val();
	condition["name"] = $("#name").val();
	condition["status"] = $("#status").val();
	condition["enabled"] = $("#enabled").val();
	condition["categoryId"] = $("#categoryId").val();
	return condition;
}

/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	$("#fieldSearchHidd").val($("#searchValue").val());
	$("#fieldValuesHidd").val($("select[name=searchKeyIds]").val());
	
	var condition = {};
	condition["title"] = $("#title").val();
	condition["code"] = $("#code").val();
	condition["statusText"] = $("#statusText").val();
	condition["categoryId"] = $("#categoryId").val();
	condition["enabled"] = $("#enabled").val();
	$("#conditionHidd").val(JSON.stringify(condition));
}

function refresh() {
	$("#code").val('');
	$("#name").val('');
	$("#status").val("").change();
	$("#enabled").val("").change();
	$("#categoryId").val("").change();
	
	$('#btnSearch').trigger('click');
}