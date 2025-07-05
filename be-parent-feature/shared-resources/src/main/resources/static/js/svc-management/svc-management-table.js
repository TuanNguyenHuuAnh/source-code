$(document).ready(function () {
    // Datatable
    $("#tableList").datatables({
        url: BASE_URL + 'svc-management/ajaxList',
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
