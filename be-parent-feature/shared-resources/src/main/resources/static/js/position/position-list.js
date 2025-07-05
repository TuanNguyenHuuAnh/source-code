$(document).ready(function($) {

	$('#fieldSearch').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    });
    
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "position/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});

	/*$(window).on("popstate", function () {
		// if the state is the page you expect, pull the name and load it.
	  	ajaxRedirect(window.location.pathname.split('?')[0]);
	});*/
	
});
/**
 * deletePosition
 * @param element
 * @param event
 * @returns
 */
function deletePosition(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var positionId = row.data("position-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "position/delete";
			var condition = {
				"positionId" : positionId
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
function editPosition(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("position-id");
	var url = BASE_URL + "position/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * detail position
 * @param element
 * @param event
 * @returns
 */
function detailPosition(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("position-id");
	var url = BASE_URL + "position/detail?id=" + id;

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
	$('.alert').remove();
	setDataSearchHidden();

	ajaxSearch("position/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	condition["companyId"] = $("#companyId").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}