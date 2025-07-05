$(document).ready(function($) {
    $('#fieldSearch').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    });    
	// on click delete
	$(".j-region-delete").on("click", function( event ) {
		deleteRegion(this, event);
	});
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'region/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "region/edit";
		// Redirect to page add
		ajaxRedirectWithCondition(url, setConditionSearch());
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editRegion(this, event);
	});
	/*
	$(".trEdit").on("dblclick", function(event) {
		editRegion(this, event, $(this));
	});
	*/
	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailRegion(this, event);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
		$('.close').trigger('click');
	});
});
/**
 * deleteRegion
 * @param element
 * @param event
 * @returns
 */
function deleteRegion(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var regionId = row.data("region-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "region/delete";
			var condition = {
				"regionId" : regionId
			}
			
			ajaxSubmit(url, condition, event);
			
		}			
	});
}
/**
 * editRegion
 * @param element
 * @param event
 * @returns
 */
function editRegion(element, event, row) {
	event.preventDefault();

	// Prepare data
	if (row == null) {
		row = $(element).parents("tr");
	}
	var id = row.data("region-id");
	var url = BASE_URL + "region/edit?id=" + id;

	// Redirect to page detail
	//ajaxRedirect(url);
	ajaxRedirectWithCondition(url, setConditionSearch());
}
/**
 * detailRegion
 * @param element
 * @param event
 * @returns
 */
function detailRegion(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("region-id");
	var url = BASE_URL + "region/detail?id=" + id;

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

	ajaxSearch("region/ajaxList", setConditionSearch(), "tableList", element, event);
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