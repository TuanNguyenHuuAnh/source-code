$(document).ready(function() {
    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

});

function create() {
    var url = BASE_URL + "ca-management/add";
    ajaxRedirect(url);
}