$(document).ready(function($) {
	


	// on click add
	$("#addNew").unbind().bind("click", function(event) {

		let url = BASE_URL + PAGE_URL + "/edit";

		// Redirect to page add
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
    $("#importDoc").on("click", function(event) {
        event.preventDefault();
                let url = BASE_URL  + "document-import/list";
                // Redirect to page add
                ajaxRedirectWithCondition(url, setConditionSearch());
             
    });


});

