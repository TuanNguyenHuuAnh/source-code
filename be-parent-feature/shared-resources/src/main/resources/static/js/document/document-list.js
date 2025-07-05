$(document).ready(function() {
    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });
});

function create() {
    var url = BASE_URL + "document/detail";
    
    var formId = $("#frmId").val();
    if( formId == "0" ) {
    	var url = url + "?docType=2";
    } else {
    	var url = url + "?formId=" + formId;
    }
    
    ajaxRedirect(url);
}