var formdata = new FormData();
var fileNameOzd = "";
var formDoc = {};
var ozDocId = $("#ozDocId").val();

function saveOzd() {
	//console.log("saveOzd");
	
	return new Promise(function (resolve, reject) {
		// OZ parameter splitter
		var ozparam_splitter = "&";
		
//		var exportparams = "viewer.exportcommand=true";
//		exportparams += ozparam_splitter + "export.mode=silent";
//		exportparams += ozparam_splitter + "export.confirmsave=false";
//		exportparams += ozparam_splitter + "export.path=/sdcard";
//		exportparams += ozparam_splitter + "ozd.filename=export_temp.ozd";
//		exportparams += ozparam_splitter + "export.format=ozd";
		
		var exportparams = "export.format=ozd";
		exportparams += ozparam_splitter + "ozd.saveall=true";
		exportparams += ozparam_splitter + "export.path=/sdcard";
		exportparams += ozparam_splitter + "export.mode=silent";
		exportparams += ozparam_splitter + "export.path=/sdcard";
		exportparams += ozparam_splitter + "export.filename=temp";
		exportparams += ozparam_splitter + "export.confirmsave=false";
		
		// save memory stream
		OZViewer.ScriptEx("save_memorystream", exportparams, ozparam_splitter);
		
		// export memory stream call back
		OZExportMemoryStreamCallBack_OZViewer = function(outputdata) {
			
			if(outputdata == "{}" ){
				//console.log("Fail to Export Memory Stream ");
				reject('Fail to Export Memory Stream');
			} else {
				var obj = eval('(' + outputdata + ')');
				var value = null;
				formdata = new FormData();
				var index = 1;
				for(var key in obj){
					value = obj[key];
					formdata.append("file_name_" + index, key.replace("/sdcard/", ""));
					formdata.append("file_stream_" + index, value);
					index++;
				}
				
				formdata.append("ozdFileName", $("#ozdFileName").val());
				
				/*for (var pair of formdata.entries()) {
				    console.log(pair[0]+ ', ' + pair[1]); 
				}*/
				resolve("true");
			}
		};
	});
}

function uploadFileOzd() {
	//console.log("uploadFileOzd");
	
	return new Promise(function(resolve, reject) {
		$.ajax({
	        type: "POST",
	        url: BASE_REPO_URL + "api/v1/doc/report/update",
	        processData: false,
	        contentType: false,
	        global: false,
			data : formdata,
	        success : function(data) {
	        	data = JSON.parse(data);
	        	if( data.status == 'SUCCESS' ) {
		        	fileNameOzd = data.resultObj.file_name_1;
	        	}
	        	
	        	//console.log(fileNameOzd);
	        	resolve("true");
			},
			error : function(xhr, textStatus, error) {
				reject(error);
			}
	    });
	});
}

function getDataOzFormDoc() {
	//console.log("getDataOzFormDoc");
	//console.log(fileNameOzd);
	
	return new Promise(function(resolve, reject) {
		formDoc = {};
		
		// get json data
		var inputJson = OZViewer.GetInformation("INPUT_JSON_ALL", function(json_data){
		
		});
		
		formDoc["id"] = $("#ozDocId").val();
		formDoc["formId"] = $("#ozDocFormId").val();
		formDoc["docTitle"] = $("#ozDocTitle").val();
		formDoc["formFileName"] = fileNameOzd;
		formDoc["inputJson"] = inputJson;
		formDoc["stepNo"] = $("#ozdStepNo").val();
		formDoc["comments"] = $("#widgetApprovalComment").val();
		formDoc["processId"] = $("#processId").val();
		
		resolve("true");
	});
}

function updateDoc() {
	//console.log("updateDoc");
	//console.log(formDoc);
	
	return new Promise(function(resolve, reject) {
		$.ajax({
			type: "POST",
			url: BASE_URL + 'doc/ajax/edit',
			contentType : "application/json",
			data: JSON.stringify(formDoc),
			success: function(data) {
				data = JSON.parse(data);
	    		window.history.pushState('', '', BASE_URL + data.urlRedirect);

	    		formDoc["id"] = data.id;
	    		
	    		$("#ozDocId").val(formDoc["id"]);
	    		
	    		resolve("true");
			},
			error : function(xhr, textStatus, error) {
				reject(error);
			}
	    });
	});
}

function showAlertMsg() {
	//console.log("showAlertMsg");
	
	var statusMsg = 1;
	if( ozDocId ) {
		statusMsg = 2;
	}
	ozDocId = formDoc["id"];
	
	return new Promise(function(resolve, reject) {
		$.ajax({
			type : "POST",
			url : BASE_URL + "doc/ajax/alert",
			data : {
				"statusMsg" : statusMsg
			},
			success : function(data) {
				$("#alertId").html(data);
				goTopPage();
				resolve("true");
			},
			error : function(xhr, textStatus, error) {
				reject(error);
			}
		});
	});
}