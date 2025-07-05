$(document).ready(function () {

    // on click list, cancel
    $('#linkList, #cancel').on('click',function(){
    	back();
    });

    $(".btn-save").on('click', function(event) {
        if ($(".j-form-validate").valid()) {
            var values = $('#form-edit-item-management').serialize();
            var url = "item/doSaveItemManagement";
            ajaxSubmit(url, values, event);
        }
    });
    
    $("#companyId").change(function(e) {
        $('#businessCode').val('').trigger('change');
    });
    
    searchCombobox("#businessCode", SEARCH_LABEL, "item/getBusinessByCompanyIdAndFunctionType", function data(
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
    
    $("#functionType").change(function(e) {
        $('#businessCode').val('').trigger('change');
    });
});

function getListBusinessCode(element) {
    var key = $(element).val();
    var url = BASE_URL + "item/getBusinessCode";
    $.ajax({
        type : "POST",
        dataType : 'json',
        url : url,
        data : {
            "key" : key
        },
        async : false,
        success : function(data) {
            data.unshift({
                id : "",
                text : "",
                name : ""
            });
            $(element).select2({
                placeholder : "Select business code",
                minimumInputLength : 0,
                allowClear : true,
                data : data
            });
        }
    });
}

function back() {
    var url = BASE_URL + "item/list";
    ajaxRedirect(url);
}