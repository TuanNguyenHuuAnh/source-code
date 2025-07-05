$(document).ready(function() {
    
	var businessId = $("#businessId").val();
	
	loadOrgForAccount(businessId);
	
	//on change business
	$('#businessId').on('change', function(event) {
		loadOrgForAccount($("#businessId").val());
	});

	// Init positionId
    searchCombobox('#positionId', '', 'position-tree/get-position-by-company',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $('#companyId').val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
});

/* Load list org for account */ 
function loadOrgForAccount(businessId) {
    $.ajax({
        type  : "POST",
        url   : BASE_URL + "rule/build-rule-org",
        data  : {
        	"businessId" : businessId
        },
        global: false,
	    beforeSend: function(xhrObj) {
	    	var token = $("meta[name='_csrf']").attr("content");
		  	var header = $("meta[name='_csrf_header']").attr("content");
		  	xhrObj.setRequestHeader(header, token);
	    },
        success: function (data , textStatus, resp ) {
        	$("#id-org-for-account").text('');
            $("#id-org-for-account").html(data);
            $("#id-add-org-for-account").show();
            $("#id-update-org-for-account").show();
            $("#id-cancel-org-for-account").hide();
        }
    });
}

function clearCombobox(element) {
    $(element).find('option').remove().end();
}

function loadBusiness() {
	clearCombobox('#businessId');
	var companyId = $('#companyId').val();
	var data = {
			"companyId" : companyId
		}
	blockbg();
	$.ajax({
	    url: BASE_URL + 'rule/getListBuesiness',
	    data: data,
	    type: 'post',
	    success: function (data) {
	      	unblockbg();
	       	$('#businessId').select2({data: data});
	       	$("#id-org-for-account").text('');
	       	if(data != null && data.length > 0) {
	       		loadOrgForAccount(data[0].id);
	       	}
	    }
	});
}