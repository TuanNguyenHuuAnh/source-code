$(document).ready(function($) {
    $('#fieldSearch').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    });
    
	// on click delete
	$(".j-country-delete").on("click", function( event ) {
		deleteCountry(this, event);
	});
	
	// multiple select search
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'country/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "country/edit";
		// Redirect to page add
		ajaxRedirectWithCondition(url, setConditionSearch());
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editCountry(this, event);
	});
	/*
	$(".trEdit").on("dblclick", function(event) {
		editCountry(this, event, $(this));
	});
	*/
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailCountry(this, event);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
		$('.close').trigger('click');
	});
});
/**
 * deleteCountry
 * @param element
 * @param event
 * @returns
 */
 function deleteCountry(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var countryId = row.data("country-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "country/delete";
			var condition = {
				"id" : countryId
			}
			
			ajaxSubmit(url, condition, event);
			
		}				
	});
}

 /**
  * editCountry
  * @param element
  * @param event
  * @returns
  */
function editCountry(element, event, row) {
	event.preventDefault();

	// Prepare data
	if(row == null){
		row = $(element).parents("tr");
	} 
	var id = row.data("country-id");
	var url = BASE_URL + "country/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirectWithCondition(url, setConditionSearch());
}

/**
 * detailCountry
 * @param element
 * @param event
 * @returns
 */
function detailCountry(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("country-id");
	var url = BASE_URL + "country/detail?id=" + id;

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

	ajaxSearch("country/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}