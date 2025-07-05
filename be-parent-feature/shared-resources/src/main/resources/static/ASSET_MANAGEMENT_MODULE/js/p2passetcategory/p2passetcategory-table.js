
$(function(){
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'asset-category/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	// init date picker
	$('.date').datepicker({
		format: 'dd/mm/yyyy',
		autoclose : true,
		todayHighlight : true,
		onRender : function(date) {
		}
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editItem(this, event);
	});
	
	// on click delete
	$(".j-btn-delete").on("click", function( event ) {
		deleteItem(this, event);
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-category/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailItem(this, event);
	});

	$('#btnExport').click(function() {
		var linkExport = BASE_URL + "asset-category/export-excel";
		doExportExcelWithToken(linkExport, "token", setConditionSearch());
	});
	
	initData();
})

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	//condition["toDate"] = $("#toDate").val();
	//condition["fromDate"] = $("#fromDate").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}

function onClickSearch(element, event) {
	setDataSearchHidden();
	ajaxSearch("asset-category/ajaxList", setConditionSearch(), "tableList", element, event);
}

function editItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	var url = BASE_URL + "asset-category/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

function deleteItem(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "asset-category/delete";
			var condition = {
				"id" : id
			}
			ajaxSubmit(url, condition, event);
		}			
	});
}

function detailItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	var url = BASE_URL + "asset-category/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

function initData() {
	$("#fieldSearch").unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	onClickSearch(this, event);
	    }
	});
}
