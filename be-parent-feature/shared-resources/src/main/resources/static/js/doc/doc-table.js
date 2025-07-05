$(document).ready(function () {
    // Datatable
    $("#tableList").datatables({
        url: BASE_URL + 'doc/ajaxList',
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
    	exportPdfByFormFileName(this, event);
    });

    // export detail to excel
	$('#btnExportExcel').on('click', function(event) {

		// condition
		var condition = setConditionSearch();
		condition["exportType"] = "excel";
		// do export excel
		doExport("doc/export", condition);
	});
	
    // export detail to csv
	$('#btnExportCsv').on('click', function(event) {

		// condition
		var condition = setConditionSearch();
		condition["exportType"] = "csv";
		// do export excel
		doExport("doc/export", condition);
	});
});

function deleteById(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("dto-id");

    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            var url = BASE_URL + "doc/delete";
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
    var url = BASE_URL + "doc/ozr-view?id=" + id + "&ozdOpen=true&mode=2";

    // Redirect to page detail
    window.location.href = url; 
}

function exportPdfByFormFileName(element, event) {
    event.preventDefault();
    
    // Prepare data
    var formFileName = $(element).data("form-file-name");
    $('#form-file-name').val(formFileName);
    $("#form-pdf-export").attr('action', BASE_REPO_URL + "api/v1/doc/download/pdf"); 
	$("#form-pdf-export").submit();
}

function showPopupProcessHistory(element, event) {
	event.preventDefault();
	
	var row = $(element).parents("tr");
    var id = row.data("dto-id");
	$.ajax({
        type  : "GET",
        url   : BASE_URL + "doc/show-process-history",
        data  : {
        	"id" : id
        },
        global :  false,
        success: function (data) {
        	$('#popupProcessHistory').html(data);
        	$("#popupProcessHistoryModal").modal('show');
        }
    });
}