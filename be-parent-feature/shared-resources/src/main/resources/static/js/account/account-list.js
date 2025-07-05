$(document).ready(function($) {
    $('#searchValue').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    });
    
	//on click add
	$("#addNew").click(function(event) {
		event.preventDefault();
		var url = BASE_URL + "account/add";
		// Redirect to page detail
		ajaxRedirect(url);
	});

	// multiple select
	$('select[multiple]').multiselect({
		columns: 1,
		placeholder: SEARCH_LABEL,
		search: true
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
	$("#id-sync-ldap").on("click", function(event) {
		syncAccount(this, event);
	});
	
	/*$("#id-sync-ldap").on('click', function(event) {
		var url = BASE_URL + "account/synchronize-ldap";
		$.ajax({
			type: "POST",
			url: url
		})
			.done(function(result) {
				console.log(result);
				$('#msg').html('');
				$('#msg').html(result);
				$("#btnSearch").trigger('click');
			})
	});*/

});

/**
 * 
 * @param element
 * @param event
 * @returns
 */
function editAccount(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("account-id");
	var url = BASE_URL + "account/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}


function viewAccount(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("account-id");
	var url = BASE_URL + "account/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

/**
 * deleteAccount
 * 
 * @param element
 * @param event
 * @returns
 */
function deleteAccount(element, event) {
	event.preventDefault();
	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("account-id");

	popupConfirm(MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var row = $(element).parents("tr");
			var id = row.data("account-id");
			var url = "account/delete?id=" + id;
			var condition = [];
			// Redirect to page detail
			ajaxSubmit(url, condition, event);
		}
	});
}

function syncAccount(element, event) {
	event.preventDefault();

	var url = "account/synchronize-ldap";
	var condition = [];
	// Redirect to page detail
	ajaxSubmit(url, condition, event);
}

/**
 * on click button search
 */
function onClickSearch(element, event) {

	setDataSearchHidd();

	ajaxSearch("account/ajaxList", setConditionSearch(), "tableList", element, event);
}

/**
 * setConditionSearch
 */
function setConditionSearch() {
	var condition = {};
	condition["searchValue"] = $("#fieldSearchHidd").val();
	condition["searchKeyIds"] = $("#fieldValuesHidd").val();
	condition["companyId"] = $("#companyId").val();
	condition["enabled"] = $("#enabled").is(':checked');
	condition["locked"] = $("#locked").is(':checked');
	condition["searchKeyIds"] = $("#search-filter").val().join(',');
	//	condition["unknownOrg"] = $("#unknownOrg1").is(':checked');
	//	condition["unknownPosition"] = $("#unknownPosition1").is(':checked');
	//	condition["emptyOrg"] = $("#emptyOrg1").is(':checked');
	//	condition["emptyPosition"] = $("#emptyPosition1").is(':checked');
	//	condition["BOD"] = $("#BOD1").is(':checked');
	//	condition["sentBOD"] = $("#sentBOD1").is(':checked');
	return condition;
}

/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	$("#fieldSearchHidd").val($("#searchValue").val());
	$("#fieldValuesHidd").val($("select[name=searchKeyIds]").val());
}