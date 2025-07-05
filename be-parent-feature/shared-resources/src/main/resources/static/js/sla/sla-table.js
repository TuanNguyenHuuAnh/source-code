$(document).ready(function() {
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'sla/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	
	//double click row
	$('.db-click').unbind().bind("dblclick", function(event) {
		editData(this, event);
	});
	
	// Delete
    $('.j-btn-delete').unbind().bind("click", function (event) {
    	deleteData(this, event);
    });

    //on click edit
    $('.j-btn-edit').unbind().bind("click", function (event) {
    	editData(this, event);
    });

});

function editData(element, event) {
	event.preventDefault();
	var row = $(element).parents("tr");
	var id = row.data("sla-id") === undefined ? row.prevObject.attr('data-sla-id') : row.data("sla-id");
	if(id === undefined) {
		id = "0";
	}
	var url = BASE_URL + "sla/edit?id=" + id;
	ajaxRedirect(url);
}

function deleteData(element, event){
	event.preventDefault();
	var row = $(element).parents("tr");
	var id = row.data("sla-id");
	popupConfirm(MSG_DEL_CONFIRM, function(result) {
        if (result) {
            blockbg();
            $.ajax({
                url: BASE_URL + "sla/delete",
                type: "POST",
                data: {
                    "id" : id
                },
                success: function (data) {
                    var content = $(data).find('.body-content');
                    $(".main_content").html(content);
                    unblockbg();
                }
            });
        }
    });
}

