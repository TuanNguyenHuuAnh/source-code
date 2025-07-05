$(document).ready(function() {	

	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'jsla/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "jsla/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});
	
	//double click row
	$( ".db-click" ).dblclick(function(event) {
		var id = $(this).data("j-sla-id");
		editData(id, event);
	});
	
	//on click edit
	$(".j-btn-edit").on("click", function(event) {
		var row = $(this).parents("tr");
		var id = row.data("j-sla-id");
		
		editData(id, event);
	});

	//on click delete
	$(".j-btn-delete").on("click", function(event) {
		var row = $(this).parents("tr");
		var id = row.data("j-sla-id");
		
		deleteData(id, event);
	});
});

function editData(id, event) {
	event.preventDefault();

	var url = BASE_URL + "jsla/edit?id=" + id;
	ajaxRedirect(url);
}

function deleteData(id, event){
	event.preventDefault();
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if( result ){
			var url = BASE_URL + "jsla/delete";
			var data = {
				"id" : id
			}
			makePostRequest( url, data );
		}	
	});
}

/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {
	ajaxSearch("jsla/ajaxList", setConditionSearch(), "tableList", element, event);
}



function setConditionSearch() {
	var condition = {};
	condition["processCode"] = $("#processCode").val();
	condition["businessCode"] = $("#businessCode").val();
	condition["name"] = $("#name").val();
	return condition;

}