$(document).ready(function () {
    $('#fieldSearch').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    });
    
	searchCombobox("#businessCode", BUSINESS_FILTER, "item/getBusinessByCompanyId", function data(
            params) {
            var obj = {
                term : params.term,
                companyId : $("#companyId").val(),
                functionType: $('#functionType').val()
            };
            return obj;
        }, function dataResult(data) {
    		return data;
        }, true);
    
    $("#companyId").change(function(e) {
        $('#businessCode').val('').trigger('change');
    });

    $('select[multiple]').multiselect({
        columns: 1,
        placeholder: SEARCH_LABEL,
        search: true
    });
    
    // on click search
    $("#btnSearchItem").on('click', function(event) {
        onClickSearch(this, event);
    });
});

function editById(element, event) {
    event.preventDefault();

    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("item-id");
    var url = BASE_URL + "item/edit?id=" + id;

    // Redirect to page detail
    ajaxRedirect(url);
}

function redirectEdit(id, functionCode, action){
	var url = BASE_URL + 'item/edit';
	  $.ajax({
			type : "GET",
			url : url,
			data : {id: id, functionCode: functionCode,action : action},
			beforeSend: blockbg(),
			success : function(res) {
				var content = $(res).find('.body-content');
				$(".main_content").html(content);
				window.history.pushState('', '', url);
				unblockbg();
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
				unblockbg();
			}
			
		});
}

function onClickSearch(element, event) {
    event.preventDefault();
    setDataSearchHidden();
    ajaxSearch("item/ajax/list", setConditionSearch(), "itemTableList", element, event);
}

function setConditionSearch() {
    var condition = {};
    condition["fieldSearch"] = $("#fieldSearchHidden").val();
    condition["fieldValues"] = $("#fieldValuesHidden").val();
    condition['businessCode'] = $("#businessCode").val();
    condition["companyId"] = $("#companyId").val();
    return condition;
}

function setDataSearchHidden() {
    $("#fieldSearchHidden").val($("#fieldSearch").val());
    $("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}

function deleteItem(element, event) {
    event.preventDefault();
    // Prepare data
    var row = $(element).parents("tr");
    var id = row.data("item-id");
    popupConfirm(MSG_DEL_CONFIRM, function(result) {
        if (result) {
            blockbg();
            $.ajax({
                url: BASE_URL + "item/delete",
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

function detailItem(element, event) {
	event.preventDefault();
	// Prepare data
	var row = $(element).parents("tr");
    var id = row.data("item-id");
	var url = BASE_URL + "item/detail?id=" + id;
	// Redirect to page detail
	ajaxRedirect(url);
}