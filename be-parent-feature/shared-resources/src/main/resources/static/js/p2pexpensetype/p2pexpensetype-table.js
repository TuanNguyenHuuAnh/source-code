
$(function(){
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'expense-type/ajaxList',
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
		var url = BASE_URL + "expense-type/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailItem(this, event);
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
	ajaxSearch("expense-type/ajaxList", setConditionSearch(), "tableList", element, event);
}

function editItem(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("id");
	var url = BASE_URL + "expense-type/edit?id=" + id;

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
			var url = "expense-type/delete";
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
	var url = BASE_URL + "expense-type/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

