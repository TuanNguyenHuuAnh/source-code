$(document).ready(function(){
	// Datatable
    $("#tableList").datatables({
        url: BASE_URL + 'todo/ajaxList',
        type: 'POST',
        setData: setConditionSearch,
        "scrollX": true,
        "sScrollXInner": "100%"
    });
//    updateIncomingCount();
    //on click edit
    $(".btn-edit").on("click", function (event) {
        editById(this, event);
    });
    
    $(".btn-download-pdf").on("click", function (event) {
    	var condition = {};

        condition["id"] = $(this).parents("tr").data("dto-main-file-id");
    	
        $.ajax({
			type: "POST",
			url: BASE_URL + "document/async/download-pdf?action=downloadPDF",
			data: condition,
			success : function(data, textStatus, request) {
				var msgFlag = request.getResponseHeader("msgFlag");
				if( "1" == msgFlag ) {
					doExport("document/download?action=downloadPDF", condition);
				}else if( "0" == msgFlag ){
					popupAlert(data);
				}
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});
    });
    
    // Delete
    $('.btn-delete').click(function (event) {
        deleteById(this, event);
    });
    
});

function editById(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var formId = row.data("dto-form-id");
    var actTaskId = row.data("dto-act-task-id");
    var docId = row.data("dto-doc-id");
    var url = BASE_URL + "document/detail?id=" + docId + "&actTaskId=" + actTaskId + "&mode=3";

    if( formId ) {
    	url = url + "&formId=" + formId;
    }
    ajaxRedirect(url);
    // Redirect to page detail
    //window.location.href = url; TaiTT comment change call ajax replace call redirect page
}

function deleteById(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("dto-docId");
    console.log(id);
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            var url = BASE_URL + "todo/delete";
            var data =setConditionSearch();
            data["id"] = id;
            makePostRequest(url, data);
        }
    });
}

function updateIncomingCount() {
	var val = $("#statusCode").val();
	if(val === '1' || val === 1) {
		setStatistic();
	}	
}