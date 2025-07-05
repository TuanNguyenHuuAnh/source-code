$(document).ready(function () {
    // Back
    $('#btnBack, #btn-cancel').click(function () {
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
    
    // change processType
    $("#processType").on('change', function(event){
    	var val = $(this).val();
    	if(val === "FREE"){
    		$("#isAuthorityDiv").removeClass('hidden');
    	}else{
    		$("#isAuthorityDiv").addClass('hidden');
    	}
    });
});

function back() {
    var url = BASE_URL + "jpm-business/list";
    ajaxRedirect(url);
}

function create() {
    var url = BASE_URL + "jpm-business/edit";
    ajaxRedirect(url);
}

function save() {
    if ($(".j-form-validate").valid()) {
        var values = $("#form-detail").serialize();
        var url = "jpm-business/edit";
        ajaxSubmit(url, values, event);
    }
}