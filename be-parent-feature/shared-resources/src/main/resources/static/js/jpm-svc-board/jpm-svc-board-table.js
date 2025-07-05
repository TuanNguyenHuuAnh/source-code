$(document).ready(function () {
    // List
    $('.btn-list').click(function (event) {
    	listById(this, event);
    });

    // Click write button
    $(".btn-write").click(function (event) {
    	writeById(this, event);
    });

});

function listById(element, event) {
    event.preventDefault();

    // Prepare data
    var div = $(element).parents(".serviceBtnArea");
    var id = div.data("dto-id");
    var companyId = $(element).parents(".serviceItem").find("#itemCompanyId").val();
    var url = BASE_URL + "document/list?formId=" + id + "&companyId=" + companyId;

    // Redirect to page detail
    //ajaxRedirect(url);
    window.location.href = url;
}

function writeById(element, event) {
    event.preventDefault();
    // Prepare data
    var div = $(element).parents(".serviceBtnArea");
    var id = div.data("dto-id");
    var url = "";
    var formType = $(element).parents(".serviceItem").find('#formType').val();
    var processType = $(element).parents(".serviceItem").find("#processType").val();
    if(processType == "INTEGRATE"){
    	url = $('#integUrl').val();
    	var win = window.open(url, '_blank');
    	win.focus();
    }else{
    	if(id == 0 || formType == 2){
        	url = BASE_URL + "document/detail?formId=" + id +"&docType=2&mode=4"; //free form
        }else{
        	url = BASE_URL + "document/detail?formId=" + id + "&mode=4";
        }
        // Redirect to page detail
        window.location.href = url; 
    }
}