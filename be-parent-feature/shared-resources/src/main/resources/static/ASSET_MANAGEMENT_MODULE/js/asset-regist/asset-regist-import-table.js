
$(function() {
	
	//$("#button-approval-module-save-draft-id, #button-approval-module-submit-id, #btnClearData").hide();
	$("#isNoPO").prop("disabled", false);
	
	$("#tableDetail").datatables({
		url : BASE_URL + 'asset-regist/list-imported', //urlImport,
		type : 'POST',
		setData : setConditionSearchImport
	});
	
	//on click list
    $('#linkList').on('click', function (event) {
        event.preventDefault();
        var url = BASE_URL + "asset-regist/edit";
        // Redirect to page list
        ajaxRedirect(url);
    });
    
//	var isError = '';
//	var isWarn  = '';
//    //hidden btn import when error
//    if(dataImport != null && typeof dataImport !== "undefined"){
//	    dataImport.forEach(function(item){
//	    	console.log(item.messageType);
//	    	if(item.messageType == 2){
//	    		isError += 1 ;
//	    	}
//	    	if(item.messageType == 1){
//	    		isWarn += 1 ;
//	    	}
//	    })
//    }
//    if(isError > 0){
//    	$('#import').hide();
//    	return;
//    }
//    if(isWarn > 0){
//    	$("#import").removeAttr("onclick");
//    	$('#import').click(function(event){
//    		event.preventDefault();
//	    	popupConfirm( MSG_IMPORT_CONFIRM, function(result) {
//	    		if (result) {
//	    			importToMasterData();
//	    		}			
//	    	});
//    	})
//    }
});	

function setConditionSearchImport() {
	var condition = {};
	condition["importId"] = importId;
	return condition;
}

function importToMasterData() {
    // on click add
    var url = BASE_URL + "p2pcatalog/saveToMasterData?importId=" + importId;
    // Redirect to page add
    ajaxRedirect(url);
}