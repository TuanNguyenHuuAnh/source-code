$(document).ready(function() {
	var opt = [];
    opt["rendering_mode"] = "svg";
    opt["use_repository_cache"] = true;
    opt["use_mstyle_pagenumber_display"] = true;
    //for printing
    opt["print_exportfrom"] = "server"; 
  	
 	// for exporting to pdf file
    opt["save_exportfrom"] = {"pdf" : "server"}; 
	start_ozjs("OZViewer", STATIC_OZJSV, opt);
	
	var ozlog = document.getElementById("ozlog");
	if(ozlog != undefined) {
		ozlog.innerHTML += ("<br>" + navigator.userAgent);
	}

	// Event PDF event
	$("#pdf_attachment").on('change', pdf_attachment_click);
	$("#pdf_attachment").on('click', onClickPDF);
	$("#pdf_remove").on('click', pdf_remove);
	
	// Show full screen
	$("#full-screen").on('click', function () {
		$(this).closest(".document").addClass('full-viewer');
		//$('.oz-viewer-plugin').addClass('full-viewer');
		$('#OZViewer').removeClass('ozviewer');
		$('#OZViewer').addClass('ozviewer-full-screen');
		$(this).hide();
		$("#normal-screen").show();
	});
	
	// Show normal screen
	$("#normal-screen").on('click', function () {
		$(this).closest(".document").removeClass('full-viewer');
		//$('.oz-viewer-plugin').removeClass('full-viewer');
		$('#OZViewer').removeClass('ozviewer-full-screen');
		$('#OZViewer').addClass('ozviewer');
		$(this).hide();
		$("#full-screen").show();
	});
});

//Attachment
function pdf_attachment_click() {
    var files = this.files;
    for (var i = 0, f; f = files[i]; i++) {
		var reader = new FileReader();
        reader._filename_ = f.name;
        reader.onloadend = function(evt) {
            if (evt.target.readyState == 2 && evt.target.result != null) {
				var oz;
                oz = document.getElementById("OZViewer");
                var form_name = oz.MakeOZBinaryURL(evt.target.result);
                if (form_name != null && form_name.length > 0) {
					var ozsep = "\n";
                    var ozparam = "connection.reportname=attachment.ozr";
                    ozparam += ozsep;
                    ozparam += "repository_agent.ozserver.servlet=" + BASE_REPO_URL + "server";
                    ozparam += ozsep;
                    ozparam += "repository_agent.item_fetch_src.pcount=1";
                    ozparam += ozsep;
					ozparam += "repository_agent.item_fetch_src.args1=ozp:///attachment.ozr=" + form_name;
                    ozparam += ozsep;
                    
                    // Viewer
					ozparam += "viewer.viewmode=fittowidth";
					ozparam += ozsep;
					ozparam += "viewer.pagedisplay=singlepagecontinuous";
					ozparam += ozsep;
					ozparam += "viewer.showtree=false";
					ozparam += ozsep;
					ozparam += "viewer.showthumbnail=false";
					ozparam += ozsep;
					ozparam += "viewer.usetoolbar=true";
					ozparam += ozsep;
					ozparam += "viewer.usestatusbar=false";
					ozparam += ozsep;
					// ozparam += "viewer.useinborder=false";
					// ozparam += ozsep;
					ozparam += "viewer.useoutborder=false";
					ozparam += ozsep;
					
					// Fix load URL -- hidden popup loading
					ozparam += "viewer.useprogressbar=false";
					ozparam += ozsep;
					
					// Open
					ozparam += "toolbar.open=false";
					ozparam += ozsep;
					
					// Save
					ozparam += "toolbar.save=false";
					ozparam += ozsep;
					
					// Print
					ozparam += "toolbar.print=false";
					ozparam += ozsep;
					
					// Add memo
					ozparam += "toolbar.addmemo=true";
					ozparam += ozsep;
					
					// Save data
					ozparam += "toolbar.savedm=false";
					ozparam += ozsep;
					
					// Page
					ozparam += "toolbar.page=true";
					ozparam += ozsep;
					
					// Zoom
					ozparam += "toolbar.zoom=true";
					ozparam += ozsep;
					
					// Singlepage
					ozparam += "toolbar.singlepage_fittoframe=false";
					ozparam += ozsep;
					ozparam += "toolbar.singlepagecontinuous_fittowidth=false";
					ozparam += ozsep;
					ozparam += "toolbar.inversepaper=false";
					ozparam += ozsep;
					
					// Find
					ozparam += "toolbar.find=false";
					ozparam += ozsep;
					
					// Etcmenu
					// ozparam += "etcmenu.open=false";
					// ozparam += ozsep;
					// ozparam += "etcmenu.close=false";
					// ozparam += ozsep;
					ozparam += "etcmenu.closeall=false";
					ozparam += ozsep;
					
					// Export and Save
					ozparam += "export.applyformat=ozd,pdf";
					ozparam += ozsep;
					ozparam += "export.saveonefile=true";
					ozparam += ozsep;
					ozparam += "ozd.saveall=true";
					ozparam += ozsep;
					ozparam += "pdf.savecomment=true";
					ozparam += ozsep;
					    
					// Font embedding
					ozparam += "pdf.fontembedding=true";
					ozparam += ozsep;
					
					// Comment
					ozparam += "comment.all=true";
					ozparam += ozsep;
					ozparam += "comment.selectedpen=highlightpen";
					ozparam += ozsep;
					
					// Memo
					ozparam += "pdf.savecomment=true"
					ozparam += ozsep;
					ozparam += "export.savememo=all"
					ozparam += ozsep;
					ozparam += "memo.exportoption=all"
					ozparam += ozsep;
					
					// Eform
					ozparam += "eform.signpad_type=dialog";
					ozparam += ozsep;
					ozparam += "eform.inputeventcommand=true";
					ozparam += ozsep;
					
					// Debug
					ozparam += "information.debug=true";
					ozparam += ozsep;
				
					oz.CreateReportEx(ozparam, ozsep);
				}                                                                                    
            } else {
            }
        };
        reader.readAsArrayBuffer(f);
    }
}

// Pdf initialisation 
function onClickPDF(e) {      
	e.target.value = null;  
}

// Pdf remove 
function pdf_remove() {
	var currentReport;
	currentReport = OZViewer.GetInformation("CURRENT_REPORT_INDEX");
	if(currentReport != 0){
		OZViewer.Script("closereport=" + currentReport);
	}
}

// OZViewer
function OZUserEvent_ozviewer(param1, param2, param3) {
	if (param1 == "attachment") {
		var file_input = document.createElement("input");
		$(file_input).attr("type", "file").attr("accept", ".pdf").attr("multiple", false).css("width", "1px").css("height", "1px").css("position", "absolute").css("left", "-1000px").css("top", "-1000px").css("opacity", "0");
		$(file_input).on('change', pdf_attachment_click);
		$(file_input).show().focus().click().hide(); 
	}
}

function OZErrorCommand_OZViewer(code, message, detailmessage, reportname) {
    //console.log(code);
    //console.log(message);
    //console.log(detailmessage);
    //console.log(reportname);
	$("button").hide();
	$("#btnSaveHead").hide();
	unblockbg();
}

function OZProgressCommand_OZViewer(step, state, reportname) {
	//console.log(step);
	//console.log(state);
	if( state == 2 && step == 4 ) {
		unblockbg();
		// Re-init datepicker jquery UI
		$('#ui-datepicker-div').remove();
	} else if ( state == 1 && step == 0 ) {
		blockbg();
	}
}

function SetOZParamters_OZViewer() {
	var oz;
	oz = document.getElementById("OZViewer");
	
	if( OZ_DOC_ID != 'null' || HISTORY_ID != 'null' ) {
		var url = "document/download?action=downloadOZD";
		
		if( HISTORY_ID != 'null' ){
			url += "&docMainFileHistoryId=" + HISTORY_ID;
		} else {
			url += "&id=" + OZ_DOC_ID;
		}
		oz.sendToActionScript("connection.openfile", window.location.origin + BASE_URL + url);
		//oz.sendToActionScript("connection.inputjson", JSON.stringify({ approverFullName_generalManagerApprove_170:  'sdfsfsdfsdfsdfsd'}));
	} else {
		oz.sendToActionScript("connection.servlet", BASE_REPO_URL + "server");
		oz.sendToActionScript("connection.reportname", OZ_DOC_FORM_FILE_NAME);
		
		var count = 0;
		
		if( JSON_DATA != '' ) {
			INPUT_JSON['p_jsonData'] = JSON_DATA;
		}
		
		for(var i in INPUT_JSON){
			count++;
			oz.sendToActionScript("connection.args"+count, i+"="+INPUT_JSON[i]);
		}
		oz.sendToActionScript("connection.pcount", count);
		
	}
	
	//check IS_SAVE_EFORM
	if( IS_SAVE_EFORM != '' && (IS_SAVE_EFORM == '1' || IS_SAVE_EFORM == 'true' || IS_SAVE_EFORM == true)) {
		oz.sendToActionScript("connection.inputjson", JSON.stringify({hide_txtIsSaveEForm : '1'}));
	}else{
		oz.sendToActionScript("connection.inputjson", JSON.stringify({hide_txtIsSaveEForm : '0'}));
	}
//	oz.sendToActionScript("connection.inputjson", JSON.stringify({documentCode : OZ_DOC_ID}));
	
	oz.sendToActionScript("global.concatpreview", "true");	
	oz.sendToActionScript("global.language","en/US");
	
	// Viewer
	oz.sendToActionScript("viewer.viewmode", "fittowidth");
    oz.sendToActionScript("viewer.pagedisplay", "singlepagecontinuous");
    oz.sendToActionScript("viewer.showtree", "false");
	oz.sendToActionScript("viewer.showthumbnail", "false");
	oz.sendToActionScript("viewer.showpagemargin", "false");
	oz.sendToActionScript("viewer.usetoolbar", "true");
	oz.sendToActionScript("viewer.usestatusbar", "false");
	// oz.sendToActionScript("viewer.useinborder", "false");
	oz.sendToActionScript("viewer.useoutborder", "false");
	
	// Fix load URL -- hidden popup loading
	oz.sendToActionScript("viewer.useprogressbar", "false");

	// OZErrorCommand
	oz.sendToActionScript("viewer.errorcommand",	"true");

	// OZProgressCommand_OZViewer
	oz.sendToActionScript("viewer.progresscommand", "true");
	
	// Open
    oz.sendToActionScript("toolbar.open", "false");
	
	// Save
    oz.sendToActionScript("toolbar.save", "false");
	
	// Print
    oz.sendToActionScript("toolbar.print", "false");
	
	// Add memo
    if(ADD_MEMO == 'true'){
    	oz.sendToActionScript("toolbar.addmemo", "true");
        oz.sendToActionScript("comment.all", "true");
    }else{
    	oz.sendToActionScript("toolbar.addmemo", "false");
        oz.sendToActionScript("comment.all", "false");
    }
	
    // Save data
    oz.sendToActionScript("toolbar.savedm", "false");
    
 	// Page
    oz.sendToActionScript("toolbar.page", "true");
 	
 	// Zoom
    oz.sendToActionScript("toolbar.zoom", "true");
 	
 	// Singlepage
    oz.sendToActionScript("toolbar.singlepage_fittoframe", "false");
    oz.sendToActionScript("toolbar.singlepagecontinuous_fittowidth", "false");
    oz.sendToActionScript("toolbar.inversepaper", "false");
    
    // Find
    oz.sendToActionScript("toolbar.find", "false");
    
    // Etcmenu
    //oz.sendToActionScript("etcmenu.open", "false");
    //oz.sendToActionScript("etcmenu.close", "false");
    oz.sendToActionScript("etcmenu.closeall", "false");
    
    // Export and Save
    oz.sendToActionScript("export.applyformat", "ozd,pdf");	
    oz.sendToActionScript("export.saveonefile", "true");		
    oz.sendToActionScript("ozd.saveall", "true");	
    oz.sendToActionScript("pdf.savecomment", "true");
    
    // Font embedding
	oz.sendToActionScript("pdf.fontembedding", "true");
    
    // Comment
    //oz.sendToActionScript("comment.selectedpen", "highlightpen");
    oz.sendToActionScript("comment.selectedpen", "pen");
    oz.sendToActionScript("comment.pen_color", "0066cc");
    oz.sendToActionScript("comment.colorbuttons", "false");
    oz.sendToActionScript("pdf.savecomment","true");
    oz.sendToActionScript("export.savememo","all");
	oz.sendToActionScript("memo.exportoption","all");
	
//	oz.sendToActionScript("global.language","vi");
    
 	// Eform
	oz.sendToActionScript("eform.signpad_type",	"dialog");
	oz.sendToActionScript("eform.inputeventcommand", "true");
	
	oz.sendToActionScript("pdf.fontembedding", "true"); // embedd all fonts
	oz.sendToActionScript("pdf.fontembedding_subset", "true"); // embedd only selected fonts
	
    // Debug
    oz.sendToActionScript("information.debug", "true");	
	return true;
}

/**
 * exportStreamOzd
 * @param formData
 * 			type FormData
 * @returns
 */
function exportStreamOzd(formData) {
	//console.log("saveOzd");
	
	return new Promise(function (resolve, reject) {
		// OZ parameter splitter
		var ozparam_splitter = "&";
		
		var exportparams = "viewer.exportcommand=true";
		exportparams += ozparam_splitter + "export.mode=silent";
		exportparams += ozparam_splitter + "export.confirmsave=false";
		exportparams += ozparam_splitter + "export.path=/sdcard";
		exportparams += ozparam_splitter + "export.useprogressbar=false";
		exportparams += ozparam_splitter + "ozd.filename=export_temp.ozd";
		exportparams += ozparam_splitter + "export.format=ozd";
		exportparams += ozparam_splitter + "pdf.savecomment=true";
		exportparams += ozparam_splitter + "export.savememo=all";
		exportparams += ozparam_splitter + "memo.exportoption=all";
		
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
				var index = 0;
				for(var key in obj){
					value = obj[key];
					formData.append("fileStreamName[" + index + "]", key.replace("/sdcard/", ""));
					formData.append("fileStream[" + index + "]", value);
					index++;
				}
				
				resolve(formData);
			}
		};
	});
}

function showMsgError( elementId, msg) {
	var alertDiv = $('<div/>', {class:'alert alert-danger alert-dismissible'});
	
	var closeBtn = $('<button/>', {type:'button', class:'close', text:"×"})
								 .attr("aria-hidden","true")
								 .attr("data-dismiss","alert");
	
	var h4 = $('<h4/>');
	var i = $('<i/>', {class:'icon fa fa-ban'});
	var msgTitle = "Alert!";
	h4.append(i);
	h4.append(msgTitle);
	
	var divContent = $('<div/>').text(msg);
	
	alertDiv.append(closeBtn);
	alertDiv.append(h4);
	alertDiv.append(divContent);
	
	$(elementId).html(alertDiv);
}