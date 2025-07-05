$(document).ready(function() {
    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

});

function create() {
    var url = BASE_URL + "category/add";
    ajaxRedirect(url);
}

function deleteById(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("dto-id");

    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            var url = BASE_URL + "category/delete";
            var data = {
                "id": id,
                "search": setConditionSearch()
            }
            makePostRequest(url, data);
        }
    });
}

function editById(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("dto-id");
    var url = BASE_URL + "category/detail?id=" + id;

    // Redirect to page detail
    ajaxRedirect(url);
}