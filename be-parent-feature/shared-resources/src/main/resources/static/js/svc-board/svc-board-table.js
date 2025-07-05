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
    var url = BASE_URL + "doc/list?formId=" + id;

    // Redirect to page detail
    //ajaxRedirect(url);
    window.location.href = url;
}

function writeById(element, event) {
    event.preventDefault();
    // Prepare data
    var div = $(element).parents(".serviceBtnArea");
    var id = div.data("dto-id");
    var url = BASE_URL + "doc/ozr-view?mode=1&id=" + id;
    // Redirect to page detail
    window.location.href = url; 
}