$(document).keypress(function(event){

    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){
    	onClickSearch(this, event);
    	$('.close').trigger('click');
    }

});

$(document).ready(function() {
	$('#categoryTreeGrid').treegrid({
        expanderExpandedClass  : 'glyphicon glyphicon-minus-sign',
        expanderCollapsedClass : 'glyphicon glyphicon-plus-sign',
        'initialState'         : 'collapsed',
        'treeColumn'           : 1
    });
	
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'introduction-category/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	//on click add
	$("#addCategory").on("click", function(event) {
//		var url = BASE_URL + "introduction-category/add";
		var url = BASE_URL + "introduction-category/edit";
		// Redirect to page detail
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	//on click edit
	$(".j-btn-edit").on("click", function(event) {
		editCategory(this, event);
	});
	
	//on click delete
	$(".j-btn-delete").on("click", function(event) {
		deleteCategory(this, event);
	});
	
	$(".j-btn-detail").on("click", function(event) {
		viewCategory(this, event);
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
	/*
	$(".trEdit").on("dblclick", function(event) {
		editCategory(this, event, $(this));
	});	
	*/
	// refesh click
	$("#btnRefresh").unbind('click').bind('click', function(event) {
		refresh();
	});
	
	$("#statusText, #categoryId").select2({ allowClear : false});
	$("#statusText").on("select2:close", function(){
		$("#statusText").focus();
	});
	
	$("#categoryId, #statusText, #code, #title").on('keypress', function(e) {		
		if((e.which && e.which == 13) || (e.keyCode && e.keyCode == 13)){
			onClickSearch(this, event);
		}	
	});
	
	$('#btnSort').on('click', function(event){
		var url = BASE_URL + "introduction-category/list/sort";
		// Redirect to page detail
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	$('#btnExport').click(function() {
		var linkExport = BASE_URL  + "introduction-category/export-excel";
		doExportExcel("#formSearch", linkExport);
	});
	
	$("#status").select2({ allowClear : false});
	$("#enabled").select2({ allowClear : false});
	$("#pictureIntroduction").select2({ allowClear : false});
	$("#typeOfMain").select2({ allowClear : false});
	
});

/**
 * editCategory
 * 
 * @param element
 * @param event
 * @returns
 */
function editCategory(element, event, row) {
	event.preventDefault();
	
	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}
	
	var id = row.data("category-id");
	var url = BASE_URL + "introduction-category/edit?id=" + id;
	
	// Redirect to page detail
	ajaxRedirectWithCondition(url, setConditionSearch());
}


function viewCategory(element, event) {
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("category-id");
	var url = BASE_URL + "introduction-category/detail?id=" + id;
	
	// Redirect to page detail
	ajaxRedirect(url);
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
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("category-id");

	popupConfirmWithButtons(MSG_DEL_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
		if (result) {
			var row = $(element).parents("tr");
			var id = row.data("category-id");
			var url = "introduction-category/delete?id=" + id;
			// Redirect to page detail
			ajaxSubmit(url, setConditionSearch(), event);
		}
	});
}

/**
 * on click button search
 */
function onClickSearch(element, event) {
	
	setDataSearchHidd();
	
	ajaxSearch("introduction-category/ajaxList", setConditionSearch(), "tableList", element, event);
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
	condition["typeOfMain"] = $("#typeOfMain").val();
	condition["pictureIntroduction"] = $("#pictureIntroduction").val();
	return condition;
}

/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	
	var condition = setConditionSearch();
	
	$("#conditionHidd").val(JSON.stringify(condition));
}

/**
 * clear form
 */
function refresh() {
	$("#code").val('');
	$("#name").val('');	
	$("#enabled").val('').change();
	$("#status").val('').change();
	$("#pictureIntroduction").val('').change();
	$("#typeOfMain").val('').change();
	
	$('#btnSearch').trigger('click');
}