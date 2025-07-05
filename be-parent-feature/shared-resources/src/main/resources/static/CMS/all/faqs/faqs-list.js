$(document).ready(function($) {


	// on click add
		$("#addNew").unbind().bind("click", function(event) {

		let url = BASE_URL + PAGE_URL + "/edit";

		// Redirect to page add
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
    $("#importFaqs").on("click", function(event) {
        event.preventDefault();
                let url = BASE_URL  + "faqs-category-import/list";
                // Redirect to page add
                ajaxRedirectWithCondition(url, setConditionSearch());
             
    });
});

