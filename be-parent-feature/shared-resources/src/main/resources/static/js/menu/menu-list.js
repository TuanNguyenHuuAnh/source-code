$(document).ready(function() {	
	if($('#fieldSearch').val() != null && $('#fieldSearch').val() != '') {
		initialState = 'expanded';
	}
	
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'menu/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
    
    // multiple select search
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
	// on click add
//	$("#add").on("click", function(event) {
//		var url = BASE_URL + "menu/edit";
//		// Redirect to page add
//		ajaxRedirect(url);
//	});
	
	$(".ms-options-wrap input").unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	onClickSearch(this, event);
	    }
	});
	
	$('#fieldSearch').unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	    	onClickSearch(this, event);
	    }
	});
	
	// Init company
    searchCombobox('.select-company', SEARCH_LABEL, 'company/get-company',
	    function data(params) {
	        var obj = {
	            keySearch: params.term,
	            isPaging: true
	        };
	        return obj;
	    }, function dataResult(data) {
	        return data;
	    }, false);
    
});
/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {

	setDataSearchHidden();

	ajaxSearch("menu/ajaxList", setConditionSearch(), "tableList", element, event);
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
/**
 * editMenu
 * @param element
 * @param event
 * @returns
 */
function editMenu(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("menu-id");
	var url = BASE_URL + "menu/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * detail
 * @param element
 * @param event
 * @returns
 */
function detail(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("menu-id");
	var url = BASE_URL + "menu/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * 
 * @param element
 * @param event
 * @returns
 */
function deleteMenu (element, event){
	event.preventDefault();
	
	// Prepare data
	var row = $(element).parents("tr");
	var menuId = row.data("menu-id");	
	popupConfirm( MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = "menu/delete";
			var condition = {
				"menuId" : menuId
			}
			
			ajaxSubmit(url, condition, event);
			
		}			
	});
}