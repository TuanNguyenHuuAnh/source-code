$(document).ready(function() {
	// Post edit save job
	$('#button-approval-module-save-draft-id').on('click', function(event) {
		blockbg();
		setTimeout(saveDocForm, 50);
	});
	
	$('#btnSaveHead').on('click', function(event) {
		blockbg();
		setTimeout(saveDocForm, 50);
	});
	
	$('#btnCreate').on('click', function(event) {
		createDocForm();
	});
	
	$('#btnSubmit').on('click', function(event) {
		//ajaxSubmit("document/testFile", formDoc, event, true);
	});
	
	$('#btnBack').on('click', function(event) {
		back();
	});
});

function back() {
	var pageMode = $("#pageMode").val();
	
	var pageUrl = "doc/list";
	if( pageMode == 1 ) {
		pageUrl = "svc-board/list";
	}
	
    var url = BASE_URL + pageUrl;
    ajaxRedirect(url);
}

function ajaxSubmitDoc() {
    ajaxSubmit("doc/ajax/submit", formDoc, event, true);
}

function createDocForm() {
    var url = BASE_URL + "doc/ozr-view?id=" + $("#ozDocFormId").val();
    ajaxRedirect(url);
}

function submitDocForm() {
	return new Promise(function(resolve, reject) {
		validDocForm()
			.then(checkValidateLimitNumberTransaction)
			.then(saveOzd)
			.then(uploadFileOzd)
			.then(getDataOzFormDoc)
			.then(updateDoc)
			.then(function() {
				
				ajaxSubmitDoc();
				//console.log("Process complete");
				unblockbg();
            	return resolve('Process complete');
            })
			.catch(function(error) {
				//console.log("Error::");
				unblockbg();
                reject(error);
            });
     });
}

function saveDocForm() {
	
	if( !$("#alertErrorId").hasClass("hidden") ) {
		$("#alertErrorId").addClass("hidden");
	}
	
	return new Promise(function(resolve, reject) {
		validDocForm()
			.then(checkValidateLimitNumberTransaction)
			.then(saveOzd)
			.then(uploadFileOzd)
			.then(getDataOzFormDoc)
			.then(updateDoc)
			.then(showAlertMsg)
			.then(function() {
				//console.log("Process complete");
				unblockbg();
            	return resolve('Process complete');
            })
			.catch(function(error) {
				//console.log("Error::");
				unblockbg();
                reject(error);
            });
     });
}

function validDocForm() {
	//console.log("validDocForm");
	if ($(".j-form-validate").valid()) {
        return Promise.resolve('true');
    } else {
        return Promise.reject('Params invalid');
    }
}

function checkValidateLimitNumberTransaction() {
	//console.log("checkValidateLimitNumberTransaction");
	
	return new Promise(function(resolve, reject) {
		$.ajax({
			type : "GET",
			url : BASE_URL + "doc/ajax/validate-limit-transaction",
			global: false,
			success : function(data, textStatus, request) {
				var status = request.getResponseHeader('status');
				if(status == "error") {
					$("#alertId").html(data);
					goTopPage();
					return reject("Limit transaction");
				} else {
					return resolve("true");
				}
			},
			error : function(xhr, textStatus, error) {
				var reason = new Error(error);
				return reject(reason);
			}
		});
	});
}