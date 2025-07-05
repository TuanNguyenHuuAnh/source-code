$(document).ready(function () {
    // Back
    $('#btnBack, #btnCancel').click(function () {
        back();
    });

    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

    // Save
    $('#btnSaveHead, #btnSave').click(function () {
        save();
    });
    
    // Init account
    searchCombobox('#accountId', '', 'ca-management/get-account',
	    function data(params) {
	        var obj = {
	            keySearch: params.term,
	            companyId: $('#companyId').val(),
	            caId: $('#id').val(),
	            isPaging: true
	        };
	        return obj;
	    }, function dataResult(data) {
	        return data;
	    }, true);
    
    //on change company
	$('#companyId').on('change', function(event) {
		$('#accountId').val('').trigger('change');
		$("#caLabel").val('');
		$("#caName").val('');
	});
	
	$('#showPass').click(function() {
		$('#caPassword').toggleClass('dotsfont');
	});
	
	$('#accountId').on('change', function(event) {
		generateCaLabel();
	});
});

function back() {
    var url = BASE_URL + "ca-management/list";
    ajaxRedirect(url);
}

function create() {
    var url = BASE_URL + "ca-management/add";
    ajaxRedirect(url);
}

function save() {
    if ($(".j-form-validate").valid()) {
        var values = $("#form-detail").serialize();
        var url = "ca-management/save";
        ajaxSubmit(url, values, event);
    }
}

function generateCaLabel(){
	var companyId = $('#companyId').val();
	var accountId = $('#accountId').val();
	if(null != companyId &&  null != accountId && "" != companyId &&  "" != accountId ){
		$.ajax({
		      type  : "GET",
		      url   : BASE_URL + "ca-management/generate-label",
		      data  : {
		    	  "companyId" : companyId,
		    	  "accountId" : accountId
		      },
		      success: function( data ) {
		    	  $("#caLabel").val(data.caLabel);
		    	  $("#caName").val(data.caName);
		      }
		});
	}
}

