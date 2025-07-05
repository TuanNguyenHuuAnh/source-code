$(document).ready(function($) {
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "ecm-repository/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});

	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
});
/**
 * deleteRepository
 * @param element
 * @param event
 * @returns
 */
function deleteRepository(element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var repositoryId = row.data("repository-id");
	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "ecm-repository/delete";
			var condition = {
				"ecmRepositoryId" : repositoryId
			}
			
			ajaxSubmit(url, condition, event);
			
		}			
	});
}
/**
 * editRepository
 * @param element
 * @param event
 * @returns
 */
function editRepository(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("repository-id");
	var url = BASE_URL + "ecm-repository/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * detail repository
 * @param element
 * @param event
 * @returns
 */
function detailRepository(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("repository-id");
	var url = BASE_URL + "ecm-repository/detail?id=" + id;

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

	ajaxSearch("ecm-repository/ajaxList", setConditionSearch(), "tableList", element, event);
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