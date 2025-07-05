$(document).ready(function() {
    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });
    
    var urlPage = $('#url').val();
	if (urlPage != null && urlPage != '') {
		window.history.pushState('', '', BASE_URL + urlPage);
	}

});

function create() {
    var url = BASE_URL + "jpm-register-svc/";
    ajaxRedirect(url);
}

function deleteById(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("dto-id");

    popupConfirm(MSG_DEL_CONFIRM, function (result) {
        if (result) {
            var url = BASE_URL + "jpm-svc-management/delete";
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
    var url = BASE_URL + "jpm-svc-management/detail?id=" + id;

    // Redirect to page detail
    ajaxRedirect(url);
}