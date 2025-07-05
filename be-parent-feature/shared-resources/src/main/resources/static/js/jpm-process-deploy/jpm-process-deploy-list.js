$(document).ready(function() {
    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

});

function create() {
    var url = BASE_URL + "jpm-process-deploy/edit";
    ajaxRedirect(url);
}