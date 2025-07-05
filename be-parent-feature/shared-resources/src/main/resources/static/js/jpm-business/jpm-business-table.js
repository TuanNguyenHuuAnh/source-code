$(document).ready(function () {
    // Datatable
    $("#tableList").datatables({
        url: BASE_URL + 'jpm-business/ajaxList',
        type: 'POST',
        setData: setConditionSearch,
        "scrollX": true,
        "sScrollXInner": "100%"
    });

    // Delete
    $('.btn-delete').click(function (event) {
        deleteById(this, event);
    });

    //on click edit
    $(".btn-edit").on("click", function (event) {
        editById(this, event);
    });

});

function deleteById(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("dto-id");

    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            var url = BASE_URL + "jpm-business/delete";
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
    var url = BASE_URL + "jpm-business/edit?id=" + id;

    // Redirect to page detail
    ajaxRedirect(url);
}