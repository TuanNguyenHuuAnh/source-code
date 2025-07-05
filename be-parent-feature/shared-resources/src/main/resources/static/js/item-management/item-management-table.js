$(document).ready(function () {
    // datatable load
    $("#itemTableList").datatables({
        url : BASE_URL + 'item/ajax/list',
        type : 'POST',
        setData : setConditionSearch
    });

    $(".j-btn-edit").on('click', function (event) {
    	editById(this, event);
    });
    
    $(".j-btn-delete").on('click', function (event) {
        deleteItem(this, event);
    });
    
 // on click detail
	$(".j-btn-detail").on("click", function(event) {
		detailItem(this, event);
	});
    
});
