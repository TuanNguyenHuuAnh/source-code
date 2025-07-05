$(document).ready(function($) {
    $('#fieldSearch').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    });
    
	// on click delete
	$(".j-district-delete").on("click", function( event ) {
		deleteDistrict(this, event);
	});
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'district/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "district/edit";
		// Redirect to page add
		ajaxRedirectWithCondition(url, setConditionSearch());
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editDistrict(this, event);
	});
	/*
	$(".trEdit").on("dblclick", function(event) {
		editDistrict(this, event, $(this));
	});
	*/
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailDistrict(this, event);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
		$('.close').trigger('click');
	});
});
/**
 * deleteDistrict
 * @param element
 * @param event
 * @returns
 */
function deleteDistrict(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var districtId = row.data("district-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "district/delete";
			var condition = {
				"id" : districtId
			}
			
			ajaxSubmit(url, condition, event);			
		}			
	});		
}
/**
 * editDistrict
 * @param element
 * @param event
 * @returns
 */
function editDistrict(element, event, row) {
	event.preventDefault();

	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	} 
	var id = row.data("district-id");
	var url = BASE_URL + "district/edit?id=" + id;

	// Redirect to page detail
	//ajaxRedirect(url);
	ajaxRedirectWithCondition(url, setConditionSearch());
}
/**
 * detailDistrict
 * @param element
 * @param event
 * @returns
 */
function detailDistrict(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("district-id");
	var url = BASE_URL + "district/detail?id=" + id;

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

	ajaxSearch("district/ajaxList", setConditionSearch(), "tableList", element, event);
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