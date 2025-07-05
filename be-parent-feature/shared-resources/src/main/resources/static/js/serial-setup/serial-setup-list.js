$(document).ready(function($) {
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'serial-setup/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event){
		event.preventDefault();
		showEditSerialSetup(this);
	});
	
	$(".j-btn-delete").on("click", function(event){
		event.preventDefault();
		cancelEditSerialSetup(this);
	});
	
	$(".j-btn-save").on("click", function(event){
		event.preventDefault();
		saveSerialSetup(this);
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
});

function showEditSerialSetup(e){
	var row = $(e).parents("tr");
	
	$(row).find("select.j-serial-info-id").removeClass("hidden");
	$(row).find("span.j-text-serial-info-id").addClass("hidden");
	
	$(row).find(".j-group-btn-edit").removeClass("hidden");
	$(row).find(".j-group-btn-action").addClass("hidden");
}

function cancelEditSerialSetup(e){
	var row = $(e).parents("tr");
	
	$(row).find("select.j-serial-info-id").addClass("hidden");
	$(row).find("span.j-text-serial-info-id").removeClass("hidden");
	
	$(row).find(".j-group-btn-edit").addClass("hidden");
	$(row).find(".j-group-btn-action").removeClass("hidden");
}

function saveSerialSetup(e){
	var row = $(e).parents("tr");
	var serialSetupId = row.data("serial-setup-id");
	var serialInfoId = $(row).find("select.j-serial-info-id").val();
	
	var data = {
		'serialSetupId' : serialSetupId,
		'serialInfoId'  : serialInfoId
	}
	
	var url = BASE_URL + "serial-setup/edit";
	$.ajax({
        url : url,
        type : "POST",
        dateType:"text",
        data : data,
        success : function (result, textStatus, request){
        	var status = request.getResponseHeader('status');
        	$("#messageInfoId").html(result);
        	if(status == 'success'){
        		$(row).find("span.j-text-serial-info-id").text(serialInfoId);
        		cancelEditSerialSetup(e);
        	}
        }
    });
}

/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {

	setDataSearchHidden();

	ajaxSearch("serial-setup/ajaxList", setConditionSearch(), "tableList", element, event);
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