$(document).ready(function() {
	
	$('.parentSelect').select2();
	
    //on click cancel
    $('#cancel').on('click', function (event) {
        event.preventDefault();
        var url = BASE_URL + "account-code/list";
        
        // Redirect to page list
        ajaxRedirect(url);
    });
    
    //on click list
    $('#linkList').on('click', function (event) {
        event.preventDefault();
        var url = BASE_URL + "account-code/list";

        // Redirect to page list
        ajaxRedirect(url);
    });
    
    // on click add
    $("#add").on("click", function(event) {
        var url = BASE_URL + "account-code/edit";
        // Redirect to page add
        ajaxRedirect(url);
    }); 
    // Post edit save job
    $('.btn-save').on('click', function(event) {        
        if ($(".j-form-validate").valid()) {    
            var url = "account-code/edit";
            var condition = $(".form-account-edit").serialize();
            ajaxSubmit(url, condition, event);
        }
    });
});

function selectedAll(tag){
	if($(tag).is(":checked")) {
    	$("#dataTable input[type='checkbox']:not(:disabled)").prop('checked', true);
    }
    else {
    	$("#dataTable input[type='checkbox']").prop('checked', false);
    }
}

function viewMessageError(){
	$("#messagesDetailModal").modal('show');
}