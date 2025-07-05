$(document).ready(function() {
    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

    //Import
    $('#btnImport').click(function(e) {
		import_get();
    });
    
});

function create() {
    var url = BASE_URL + "jpm-process/edit";
    ajaxRedirect(url);
}

function import_get(){
	var url = BASE_URL + "jpm-process/import";
	
	$.ajax({
		type : "GET",
		url : url,
		success : function(data) {
			$("#import-modal-content").html(data);
			$('#import-modal').modal('show');
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}
