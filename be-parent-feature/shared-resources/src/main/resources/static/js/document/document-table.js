$(document).ready(function () {
    // Datatable
    $("#tableList").datatables({
        url: BASE_URL + 'document/ajaxList',
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
    
    // show popup history process
    $(".btn-process-his").on("click", function (event) {
    	showPopupProcessHistory(this, event);
    });
    
    // Export pdf
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

    // export detail to excel
	$('#btnExportExcel').on('click', function(event) {

		// condition
		var condition = setConditionSearch();
		condition["exportType"] = "excel";
		// do export excel
		doExport("document/export", condition);
	});
	
    // export detail to csv
	$('#btnExportCsv').on('click', function(event) {

		// condition
		var condition = setConditionSearch();
		condition["exportType"] = "csv";
		// do export excel
		doExport("document/export", condition);
	});
	
	//Update data sort search hidd
	updateDataSortSearchHidd();
});

function deleteById(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("dto-id");
    var data = setConditionSearch();
    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            var url = BASE_URL + "document/delete";
            var data =setConditionSearch();
            data["id"] = id;
            makePostRequest(url, data);
        }
    });
}

function editById(element, event) {
    event.preventDefault();
    // Prepare data
    var row = $(element).parents("tr");
    
    var id = row.data("dto-id");
    var url = BASE_URL + "document/detail?id=" + id;
    
    var formId = row.data("dto-form-id");
    if( formId ) {
    	url = url + "&formId=" + formId;
    }
    
    var docType = row.data("dto-doc-type");
    if( docType ) {
    	url = url + "&docType=" + docType;
    }
    
    url = url + "&mode=2";
    
    // Redirect to page detail
    ajaxRedirect(url);
}

function showPopupProcessHistory(element, event) {
	event.preventDefault();
	
	var row = $(element).parents("tr");
    var docUUID = row.data("dto-doc-uuid");
	$.ajax({
        type  : "GET",
        url   : BASE_URL + "document/show-process-history",
        data  : {
        	"docUUID" : docUUID
        },
        global :  false,
        success: function (data) {
        	$('#popupProcessHistory').html(data);
        	$("#popupProcessHistoryModal").modal('show');
        }
    });
}

/**
 * setDataSearchHidd
 */
function updateDataSortSearchHidd() {
	$("#sortNameSearchHidden").val($("#sortNameTableHidden").val());
	$("#sortTypeSearchHidden").val($("#sortTypeTableHidden").val());
}
