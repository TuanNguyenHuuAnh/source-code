$(document).ready(function() {
    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

});

function create() {
    var url = BASE_URL + "noti-template/add";
    ajaxRedirect(url);
}

function deleteById(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("dto-id");

    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            var url = BASE_URL + "noti-template/delete";
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
    var url = BASE_URL + "noti-template/detail?id=" + id;

    // Redirect to page detail
    ajaxRedirect(url);
}