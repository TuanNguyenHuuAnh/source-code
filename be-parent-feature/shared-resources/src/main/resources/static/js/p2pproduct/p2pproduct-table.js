
$(function(){
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'product/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	// init date picker
	$('.date').datepickerUnit({
		format: 'dd/mm/yyyy',
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : '${sessionScope.localeSelect}',
		todayHighlight : true,
		onRender : function(date) {
		}
	})
	
	$(document).on('keypress',function(e) {
        if(e.which == 13) {
            onClickSearch(this, event);
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
		var url = BASE_URL + "product/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailItem(this, event);
	});

	$('#btnExport').click(function() {
		var linkExport = "product/export-excel";
		doExportExcel(linkExport);
	});
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
	ajaxSearch("product/ajaxList", setConditionSearch(), "tableList", element, event);
}

function editItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	var url = BASE_URL + "product/edit?id=" + id;

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
			var url = "product/delete";
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
	var url = BASE_URL + "product/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

function doExportExcel(linkExport) {
	$("#formSearch").attr("action", BASE_URL + linkExport);
	$("#formSearch").submit();
}
