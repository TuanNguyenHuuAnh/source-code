$(document).ready(function() {
    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });
});

function create() {
    var url = BASE_URL + "doc/ozr-view?id=" + $("#formId").val();
    ajaxRedirect(url);
}