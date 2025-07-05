$(document).ready(function($) {
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "account-team/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
	$(".ms-options-wrap input").bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	onClickSearch(this, event);
	    }
	});
	
	$('#fieldSearch').unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	onClickSearch(this, event);
	    }
	});
});
/**
 * deleteCity
 * @param element
 * @param event
 * @returns
 */
 function deleteTeam(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("team-id");

	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "account-team/delete";
			var condition = {
				"id" : id
			}
			
			ajaxSubmit(url, condition, event);
			
		}			
	});
}
/**
 * edit
 * @param element
 * @param event
 * @returns
 */
function editTeam(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("team-id");
	var url = BASE_URL + "account-team/edit?id=" + id;
	console.log(url);
	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * detailCity
 * @param element
 * @param event
 * @returns
 */
function detailTeam(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("team-id");
	var url = BASE_URL + "account-team/team-view?id=" + id;

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
	event.preventDefault();
	setDataSearchHidden();

	ajaxSearch("account-team/ajaxList", setConditionSearch(), "tableList", element, event);
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
