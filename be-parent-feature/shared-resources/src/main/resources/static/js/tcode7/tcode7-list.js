$(document).ready(function($) {
    // on click delete
    $(".j-tcode7-delete").on("click", function(event) {
        deleteReason(this, event);
    });
    $('select[multiple]').multiselect({
        columns : 1,
        placeholder : SEARCH_LABEL,
        search : true
    });
    // datatable load
    $("#tableList").datatables({
        url : BASE_URL + 'tcode7/ajaxList',
        type : 'POST',
        setData : setConditionSearch
    });

    // on click add
    $("#add").on("click", function(event) {
        var url = BASE_URL + "tcode7/edit";
        // Redirect to page add
        ajaxRedirect(url);
    });

    // on click edit
    $(".j-btn-edit").on("click", function(event) {
        editTcode7(this, event);
    });

    // on click detail
    // $(".j-btn-detail").on("click", function(event) {
    // detailPosition(this, event);
    // });

    // on click search
    $("#btnSearch").on('click', function(event) {
        onClickSearch(this, event);
    });
});
/**
 * @param element
 * @param event
 * @returns
 */
function deleteReason(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var reasonId = row.data("tcode7-id");

    popupConfirm(MSG_DEL_CONFIRM, function(result) {
        if (result) {
            var url = "tcode7/delete";
            var condition = {
                "id" : reasonId
            }

            ajaxSubmit(url, condition, event);

        }
    });
}
/**
 * @param element
 * @param event
 * @returns
 */
function editTcode7(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("tcode7-id");
    var url = BASE_URL + "tcode7/edit?id=" + id;
    ;
    // Redirect to page detail
    ajaxRedirect(url);
}
/**
 * 
 * @param element
 * @param event
 * @returns
 */
// function detailReason(element, event) {
// event.preventDefault();
//
// // Prepare data
// var row = $(element).parents("tr");
// var id = row.data("reason-id");
// var url = BASE_URL + "reason-write-off/detail?id=" + id;
//
// // Redirect to page detail
// ajaxRedirect(url);
// }
/**
 * onClickSearch
 * 
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {

    setDataSearchHidden();

    ajaxSearch("tcode7/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
    var condition = {};
    condition["fieldSearch"] = $("#fieldSearchHidden").val();
    condition["fieldValues"] = $("#fieldValuesHidden").val();
    return condition;
}

function setDataSearchHidden() {
    $("#fieldSearchHidden").val($("#fieldSearch").val());
    $("#fieldValuesHidden").val($("select[id=search-filter]").val());
}