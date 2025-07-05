$(document).ready(function($) {
    $('#fieldSearch').keypress(function(event){
    
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    
    });
    
	$(".j-city-delete").on("click", function( event ) {
		deleteCity(this, event);
	});
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'city/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "city/edit";
		// Redirect to page add
		//ajaxRedirect(url);
		ajaxRedirectWithCondition(url, setConditionSearch());
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editCity(this, event);
	});
	/*
	$(".trEdit").on("dblclick", function(event) {
		editCity(this, event, $(this));
	});
	*/
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailCity(this, event);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
});
/**
 * deleteCity
 * @param element
 * @param event
 * @returns
 */
 function deleteCity(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var cityId = row.data("city-id");

	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "city/delete";
			var condition = {
				"id" : cityId
			}
			
			ajaxSubmit(url, condition, event);
			
		}
	});
}
/**
 * editCity
 * @param element
 * @param event
 * @returns
 */
function editCity(element, event, row) {
	event.preventDefault();

	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}
	var id = row.data("city-id");
	var url = BASE_URL + "city/edit?id=" + id;

	// Redirect to page detail
	//ajaxRedirect(url);
	// Redirect to page detail
	ajaxRedirectWithCondition(url, setConditionSearch());
}
/**
 * detailCity
 * @param element
 * @param event
 * @returns
 */
function detailCity(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("city-id");
	var url = BASE_URL + "city/detail?id=" + id;

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

	ajaxSearch("city/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {

	setDataSearchHidden();
	
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}
