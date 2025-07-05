$(function(){

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
})
function setConditionSearchImport(){
    let condition = {};
    condition["sessionKey"] = $('#sessionKey').val();
    return condition;
}
function detailMessageImport(e) {
    var msg = $(e).find('input').val();
    msg = msg.replace(/;/g, '');
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