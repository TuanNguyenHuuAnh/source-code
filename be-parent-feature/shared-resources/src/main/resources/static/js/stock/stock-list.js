$(document).ready(function($) {
	// on click delete
	$(".j-stock-delete").on("click", function( event ) {
		deleteStock(this, event);
	});
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'stock/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "stock/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editStock(this, event);
	});
	
//	// on click detail
//	$(".j-btn-detail").on("click", function(event) {
//		detailPosition(this, event);
//	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
});
/**
 * deletePosition
 * @param element
 * @param event
 * @returns
 */
function deleteStock(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var stockId = row.data("stock-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "stock/delete";
			var condition = {
				"id" : stockId
			}
			
			ajaxSubmit(url, condition, event);
			
		}			
	});
}
/**
 * editPosition
 * @param element
 * @param event
 * @returns
 */
function editStock(element, event) {
	event.preventDefault();
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("stock-id");
	var url = BASE_URL + "stock/edit?id=" + id;;
	// Redirect to page detail
	ajaxRedirect(url);
}

/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {

	setDataSearchHidden();

	ajaxSearch("stock/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[id=search-filter]").val());
}