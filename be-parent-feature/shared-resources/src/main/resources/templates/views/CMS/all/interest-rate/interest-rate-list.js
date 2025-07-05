$(document).ready(function($) {
	$('#tabInterestRate a[href="#interestRateDetail"]').tab('show');
	
	// tabLanguage click
	$('#tabInterestRate a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	$('#tabLanguage a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	
	$('.datepicker').datepicker({
		format: "dd-mm-yyyy",
        changeMonth: true,
        changeYear: true,
        autoclose: true,
        keyboardNavigation : true
	});
	
	
	$("#cityId").combobox({
		
	})
	
	//on click add
	$("#addInterestRate").on("click", function(event) {
		var cityId = $("#cityId").combobox('getValue');
		console.log(cityId);
		var url = BASE_URL + "interest-rate/edit?cityId=" + cityId;
		// Redirect to page detail
		ajaxRedirect(url);
	});
	
		
	//on click edit
	$(".j-btn-edit").on("click", function(event) {
		editInterestRate(this, event);
	});
	
	//on click delete
	$(".j-btn-delete").on("click", function(event) {
		deleteInterest(this, event);
	});
	
	$(".j-btn-detail").on("click", function(event) {
		viewInterestRateDetail(this, event);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event,"interest-rate/ajaxList");
	});
});

/**
 * 
 * @param element
 * @param event
 * @returns
 */
function editInterestRate(element, event) {
	event.preventDefault();
	
	var rows = $(element).closest("tr");
	var id = rows.find("#interestId").text();
	var currencyId = rows.find("#currencyId").text();
	var termId = rows.find("#termId").text();
	var selectionListId = rows.find("#selectionListId").text();
	var url = BASE_URL + "interest-rate/edit?id=" + id + "&currencyId=" + currencyId + "&termId=" + termId + "&selectionListId=" + selectionListId;
	
	
	// Redirect to page detail
	ajaxRedirect(url);
}


function viewInterestRateDetail(element, event) {
	event.preventDefault();
	
	// Prepare data
	var rows = $(element).closest("tr");
	var id = rows.find("#interestId").text();
	var currencyId = rows.find("#currencyId").text();
	var termId = rows.find("#termId").text();
	var selectionListId = rows.find("#selectionListId").text();
	var url = BASE_URL + "interest-rate/detail?id=" + id + "&currencyId=" + currencyId + "&termId=" + termId + "&selectionListId=" + selectionListId;
	
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
function deleteInterest(element, event) {
	event.preventDefault();

	popupConfirm(MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var rows = $(element).closest("tr");
			var id = rows.find("#interestId").text();
			var currencyId = rows.find("#currencyId").text();
			var termId = rows.find("#termId").text();
			var selectionListId = rows.find("#selectionListId").text();
			var url = "interest-rate/delete?id=" + id + "&currencyId=" + currencyId + "&termId=" + termId + "&selectionListId=" + selectionListId;

			var condition = [];
			// Redirect to page detail
			ajaxSubmit(url, condition, event);
		}
	});
}

/**
 * on click button search
 */
function onClickSearch(element, event, url) {
	setDataSearchHidd();
	
	ajaxSearch(url, setConditionSearch(), "tableList", element, event);
}

/**
 * setConditionSearch
 */
function setConditionSearch() {
	var condition = {};
	condition["displayDate"] = $("#fieldDateHidden").val();
	condition["cityId"] = $("#fieldCityHidden").val();
	
	
	console.log($("#fieldCityHidden").val());
	return condition;
}

/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	$("#fieldDateHidden").val($("#displayDate").val());
	$("#fieldCityHidden").val($("#cityId").combobox('getValue'));
}
