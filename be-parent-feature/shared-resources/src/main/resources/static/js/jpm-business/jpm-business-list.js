$(document).ready(function() {
    // Add
    $('#btnCreate').click(function(e) {
    	create(e);
    });

});

function create(e) {
    e.preventDefault();
    var url = BASE_URL + "jpm-business/edit";
    ajaxRedirect(url);
}