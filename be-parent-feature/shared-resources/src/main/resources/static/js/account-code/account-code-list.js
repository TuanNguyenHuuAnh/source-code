$(document).ready(function($) {
    // on click delete
    $(".j-account-delete").on("click", function( event ) {
        deleteAccountCode(this, event);
    });
    
    // multiple select search
    $('select[multiple]').multiselect({
        columns: 1,
        placeholder: SEARCH_LABEL,
        search: true
    });
    
    // datatable load
    $("#tableList").datatables({
        url : BASE_URL + 'account-code/ajaxList',
        type : 'POST',
        setData : setConditionSearch
    });

    // on click add
    $("#add").on("click", function(event) {
        var url = BASE_URL + "account-code/edit";
        // Redirect to page add
        ajaxRedirect(url);
    });

    // on click edit
    $(".j-btn-edit").on("click", function(event) {
        edit(this, event);
    });
    
    // on click detail
    $(".j-btn-detail").on("click", function(event) {
        detailCountry(this, event);
    });
    
    // on click search
    $("#btnSearch").on('click', function(event) {
        onClickSearch(this, event);
    });
    
    $('.expenseTypeIdSel').select2();
    
});
/**
 * deleteCountry
 * @param element
 * @param event
 * @returns
 */
 function deleteAccountCode(element, event){
    event.preventDefault();
    
    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("account-id");
    
    popupConfirm( MSG_DEL_CONFIRM, function(result) {
        if (result) {
            var url = "account-code/delete";
            var condition = {
                "id" : id
            }
            
            ajaxSubmit(url, condition, event);
            
        }               
    });
    $(this).scrollTop(0);
}

 /**
  * edit
  * @param element
  * @param event
  * @returns
  */
function edit(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("account-id");
    var url = BASE_URL + "account-code/edit?id=" + id;

    // Redirect to page detail
    ajaxRedirect(url);
}

/**
 * detailCountry
 * @param element
 * @param event
 * @returns
 *//*
function detailCountry(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("country-id");
    var url = BASE_URL + "country/detail?id=" + id;

    // Redirect to page detail
    ajaxRedirect(url);
}
*/
/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {

    setDataSearchHidden();
    ajaxSearch("account-code/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
    var condition = {};
    condition["code"] = $("#codeHidden").val();
    condition["name"] = $("#nameHidden").val();
    condition["parentCode"] = $(".expenseTypeIdSel").children("option:selected").val();
    return condition;
}

function setDataSearchHidden() {
    $("#codeHidden").val($("#code").val());
    $("#nameHidden").val($("#name").val());
    
}