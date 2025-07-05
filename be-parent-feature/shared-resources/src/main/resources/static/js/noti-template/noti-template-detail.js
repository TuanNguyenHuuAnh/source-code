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
    
    showTabError(LANGUAGE_LIST);
    
    if(tabError!=""){
    	$('#tabLanguage a[href="#language' + tabError +'"]').tab('show');
    }
});

function back() {
    var url = BASE_URL + "noti-template/list";
    ajaxRedirect(url);
}

function create() {
    var url = BASE_URL + "noti-template/add";
    ajaxRedirect(url);
}

function save() {
    if ($(".j-form-validate").valid()) {
        var values = $("#form-detail").serialize();
        var url = "noti-template/save";
        ajaxSubmit(url, values, event);
    }else{
		// show tab if exists error
		showTabError(LANGUAGE_LIST);
	}
    
}

