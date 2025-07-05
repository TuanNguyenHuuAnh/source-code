$(document).ready(function() {
    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

});

function create() {
console.log(123)
    var url = BASE_URL + "calendar-type/add";
    ajaxRedirect(url);
}