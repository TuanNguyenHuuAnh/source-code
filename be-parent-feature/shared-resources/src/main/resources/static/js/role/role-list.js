$(document).ready(function($) {
    $('#searchValue').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    });
    
	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	
	//on click add
	$("#addNew").on("click", function(event) {
		var url = BASE_URL + "role/add";
		// Redirect to page detail
		ajaxRedirect(url);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
});

function deleteRole(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("role-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if( result ){
			var url = BASE_URL + "role/delete";
			var data = {
				"id" : id
			}
			makePostRequest( url, data );
		}	
	});
}

function editRole(element, event) {
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("role-id");
	var url = BASE_URL + "role/edit?id=" + id;
	
	// Redirect to page detail
	ajaxRedirect(url);
}


function viewRole(element, event) {
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("role-id");
	var url = BASE_URL + "role/detail?id=" + id;
	
	// Redirect to page detail
	ajaxRedirect(url);
}

/**
 * deleteRole
 * 
 * @param element
 * @param event
 * @returns
 */
function deleteRole(element, event) {
	event.preventDefault();
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("role-id");

	popupConfirm(MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var row = $(element).parents("tr");
			var id = row.data("role-id");
			var url = "role/delete?id=" + id;
			var condition = [];
			// Redirect to page detail
			ajaxSubmit(url, condition, event);
		}
	});
}

/**
 * on click button search
 */
function onClickSearch(element, event) {
	
	setDataSearchHidd();
	
	ajaxSearch("role/ajaxList", setConditionSearch(), "tableList", element, event);
}

/**
 * setConditionSearch
 */
function setConditionSearch() {
	var condition = {};
	condition["searchValue"] = $("#fieldSearchHidd").val();
	condition["searchKeyIds"] = $("#fieldValuesHidd").val();
	condition["companyId"] = $("#companyId").val();
	return condition;
}

/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	$("#fieldSearchHidd").val($("#searchValue").val());
	$("#fieldValuesHidd").val($("#search-filter").val().join(','));
}

