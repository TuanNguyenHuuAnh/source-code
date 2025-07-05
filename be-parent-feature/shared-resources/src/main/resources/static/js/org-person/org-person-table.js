$(document).ready(function() {
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'organization-person/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});	
	//double click row
	$('.db-click').on("dblclick", function(event) {
		editData(this, event);
	});
	// Delete
    $('.j-od-delete').on("click", function (event) {
    	deleteData(this, event);
    });

    //on click edit
    $('.btn-edit').on("click", function (event) {
    	editData(this, event);
    });

});

function editData(element, event) {
	event.preventDefault();
	var row = $(element).parents("tr");
	var id = row.data("org-person-id") === undefined ? row.prevObject.attr('data-org-person-id') : row.data("org-person-id");
	if(id === undefined) {
		id = "0";
	}
	var url = BASE_URL + "organization-person/edit?id=" + id;
	ajaxRedirect(url);
}

/**
 * @param element
 * @param event
 * @returns
 */
function deleteData(element, event) {
	event.preventDefault();
	var row = $(element).parents("tr");
	var id = row.data("org-person-id") === undefined ? row.prevObject.attr('data-org-person-id') : row.data("org-person-id");
	popupConfirm(MSG_DEL_CONFIRM, function(result) {
        if (result) {
            blockbg();
            $.ajax({
                url: BASE_URL + "organization-person/delete",
                type: "POST",
                data: {
                    "id" : id
                },
                success: function (response) {
                	$("#messages").html(response);
                    unblockbg();
                }
            });
        }
    });
}

