$(document).ready(function () {
	$("#form-list").unbind('keypress').bind('keypress', function(event) {
	    if(event.keyCode == 13){
	        event.preventDefault();
	    	// onClickSearch(this, event);
	    	$("#btnSearchConstant").trigger('click');
	    	return false;
	    }
	});

    $('select[multiple]').multiselect({
        columns: 1,
        placeholder: SEARCH_LABEL,
        search: true
    });

    // on click search
    $("#btnSearchConstant").on('click', function(event) {
        onClickSearch(this, event);
    });

    $(".j-btn-delete").on('click', function (event) {
        deleteItem(this, event);
    });

    // datatable load
    $("#constantTableList").datatables({
        url : BASE_URL + 'constant-display/ajaxList',
        type : 'POST',
        setData : setConditionSearch
    });
    
    //on click Clear
    $("#btnCancel").on('click',function(event){
    	$(this).closest("form").find("input[type=text],textarea").val("");
    	onClickSearch(this, event);
    })
    
    $("#btnExport").unbind('click').bind('click', function(){
		var linkExport = BASE_URL + "constant-display/export";
		
		doExportExcelWithToken(linkExport, "token", setConditionSearch());
	});
});

function onClickSearch(element, event) {
    event.preventDefault();
    setDataSearchHidden();
    ajaxSearch("constant-display/ajaxList", setConditionSearch(), "constantTableList", element, event);
}

function setConditionSearch() {
    var condition = {};
    condition["groupCode"] = $("#groupCode").val();
    condition["kind"] = $("#kind").val();
    condition["name"] = $("#name").val();
    condition["code"] = $("#code").val();
    return condition;
}
function setDataSearchHidden() {
    $("#fieldType").val($("#type").val());
    $("#fieldGroup").val($("#kind").val());
    $("#fieldName").val($("#catOfficialName").val());
    $("#fieldValue").val($("#cat").val());
}

function deleteItem(element, event) {
    event.preventDefault();
    // Prepare data
    var condition = {};
    condition["groupCode"] = $(element).parents("tr").find("#groupCode").text();
    condition["kind"] = $(element).parents("tr").find("#kind").text();
    condition["code"] = $(element).parents("tr").find("#code").text();
    popupConfirm(MSG_DEL_CONFIRM, function(result) {
        if (result) {
            blockbg();
            $.ajax({
                url: BASE_URL + "constant-display/delete-item",
                type: "POST",
                data: condition,
                success: function (data) {
                    var content = $(data).find('.body-content');
                    $(".main_content").html(content);
                    unblockbg();
                }
            });
        }
    });
}