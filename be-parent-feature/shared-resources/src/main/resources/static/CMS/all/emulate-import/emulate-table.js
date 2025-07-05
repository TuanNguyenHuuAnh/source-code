$(function(){

    if(isDelete) {
        notActive();
    }

    $("#btnExportExcel").unbind().bind('click', function(e){
        var linkExport = BASE_URL + CONTROLLER_URL + "/export-excel";

        doExportExcelWithToken(linkExport, "token", setConditionSearchImport());
    })

    // datatable load
    $("#tableList").datatables({
        url : BASE_URL + CONTROLLER_URL + '/ajaxList',
        type : 'POST',
        setData : setConditionSearchImport
    });
    if ((typeof IS_SUBMIT !== 'undefined' && IS_SUBMIT) || IS_ERROR || sessionKey == '' || sessionKey == null ) {
        $('#btnSave').unbind();
        $('#btnSave').hide();
    } else {
        $('#btnSave').unbind().bind('click', function(e) {
            e.preventDefault();
            let url = CONTROLLER_URL + "/save-data-import";
            ajaxSubmit(url, setConditionSearchImport(), e)
        })
    }
    $('#channel').change(
        () => {
            ajaxRedirect(BASE_URL + CONTROLLER_URL + '/list' +'?channel=' + $('#channel').val());

            /*ajaxSearch(BASE_URL + CONTROLLER_URL + '/list', {'channel': $('#channel').val()}, "tableList", this, event);*/
        }
    );
})

function notActive() {
    if (!$('#btnDelete').hasClass('not-active')){
        $('#btnDelete').addClass('not-active');
    }
    if (!$('#btnUpdate').hasClass('not-active')){
        $('#btnUpdate').addClass('not-active');
    }
    isDelete = true;
}
function setConditionSearchImport(){
    let condition = {};
    condition["sessionKey"] = $('#sessionKey').val();
    return condition;
}
function detailMessageImport(e) {
    var msg = $(e).find('input').val();
    $('#import-message-error-detail').addClass('error');
    var modalContent = $('#import-message-error-detail');
    modalContent.html(msg);

    // Show modal
    if (msg != '' && msg != undefined) {
        $('#modal-import-message-error').modal({
            "show": true
        });
    }
};
