/**phatvt:handle ajax global*/

// shared-resources - static/js/common_unit.js

$(document).bind("ajaxSend", function(){
    blockbg();
 }).bind("ajaxComplete", function(xhr, textStatus, error){
	//console.log(textStatus.status);
	unblockbg();
	appendTitle();
 }).bind("ajaxSuccess", function(xhr, textStatus, error){
	 //console.log("ajaxSuccess");
	 unblockbg();
 }).bind("ajaxError", function(xhr, textStatus, error){
//	 console.log("ajaxError");
	 unblockbg();
//	 console.log(xhr);
//	 console.log(textStatus);
//	 console.log(error);
 });



$(document).ready(function(){
	appendTitle();
	//28/02/2020  start
	//connect();
	//28/02/2020  end
	// BaoHG - 20190627 - Begin - Auto Remove space start and end when input
	$(function(){
	    $(document).on('change', 'input[type="text"]',function(){
	        this.value = $.trim(this.value);
	    });
	});
	// BaoHG - 20190627 - End - Auto Remove space start and end when input
	
	 $(document).on('change', ':file', function() {
	     var input = $(this),
	         numFiles = input.get(0).files ? input.get(0).files.length : 1,
	         label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
	     input.trigger('fileselect', [numFiles, label]);
	  });

	    $(':file').on('fileselect', function(event, numFiles, label) {
	        var input = $(this).parents('.input-group').find(':text'),
	            log = numFiles > 1 ? numFiles + ' files selected' : label;
	        if( input.length ) {
	            input.val(log);
	        } else {
	            if( log ) console.log(log);
	          }
	    });
	 
	$("#btnSearch").on("click", () => {
		// console.log('hide alert 1');
		$('.alert').hide();
	});
	 
	setTimeout(
		() => $("#btnSearch").on("click", () => {
			// console.log('hide alert 2');
			$('.alert').hide();
		})
	, 500);
    
	$(window).on("popstate", function () {
		// if the state is the page you expect, pull the name and load it.
		
		// console.log(window.location.pathname.split('?')[0]);
		
	  	ajaxRedirect(window.location.pathname.split('?')[0]);
	});
    
});

/**
 * Redirect url method GET
 * 
 * @param url
 * @returns
 */
function redirect(url) {
    var ua        = navigator.userAgent.toLowerCase(),
        isIE      = ua.indexOf('msie') !== -1,
        version   = parseInt(ua.substr(4, 2), 10);

    // Internet Explorer 8 and lower
    if (isIE && version < 9) {
        var link = document.createElement('a');
        link.href = url;
        document.body.appendChild(link);
        link.click();
    }

    // All other browsers can use the standard window.location.href (they don't lose HTTP_REFERER like IE8 & lower does)
    else { 
        window.location.href = url; 
    }
}

// CMS
function ajaxRedirectWithCondition(url, condition) {
 // blockbg();
  $.ajax({
    type: "GET",
    url: url,
    data: condition,
    success: function (data) {
      var content = $(data).find(".body-content");
      $(".main_content").html(content);
      window.history.pushState("", "", url);
    },
    error: function (xhr, textStatus, error) {
      console.log(xhr);
      console.log(textStatus);
      console.log(error);
    },
    complete: function (result) {
      unblockbg();
    },
  });
}

/**
 * Open popup confirm
 * 
 * @param msgConfirm
 * @param methodCallback
 * @returns
 */
function popupConfirm( msgConfirm, methodCallback) {
	return bootbox.confirm({
		message: msgConfirm,
		locale: APP_LOCALE.toLowerCase(),
	    callback: methodCallback
	});
}

/**
 * Open popup confirm
 * 
 * @param msgConfirm
 * @param methodCallback
 * @returns
 */
function popupConfirmWithButtons( msgConfirm, buttons, methodCallback) {	
	return bootbox.confirm({
		message: msgConfirm,
		locale: APP_LOCALE.toLowerCase(),
	    buttons: buttons,
	    callback: methodCallback
	}); 
}

/**
 * Open popup alert
 * 
 * @param msgAlert
 * @returns
 */
function popupAlert( msgAlert ) {
	return bootbox.alert({
		message: msgAlert,
		locale: APP_LOCALE.toLowerCase(),
	});
}

/**
 * Submit form with url and data
 * @param url
 * @param data
 * @returns
 */
function makePostRequest( url, data ) {
	var newForm = jQuery("<form>", {
        'action' : url,
        'method' : 'POST'
    });
	
	// add fields
	for (name in data) {
		newForm.append(jQuery("<input>", {	
	        "name" : name,
	        "value": data[name],
	        "type" : "hidden"
	    }));
	}
	
	// _csrf must be add in <head>
    var token = $("meta[name='_csrf']").attr("content");
    var csrfToken = $(document.createElement('input'));
    $(csrfToken).attr("type", "hidden");
    $(csrfToken).attr("name", "_csrf");
    $(csrfToken).val(token);
    $(newForm).append($(csrfToken));
	
	newForm.appendTo(document.body);
	newForm.submit();
}

/**
 * Validation checked list
 * @param elementList
 * @returns boolean
 */
function validationCheckedList( elementList ) {
	var isChecked = true;
	
	$(elementList).each(function() {
    	if( $(this).is(':checked') == false ) {
    		isChecked = false;
    		return false;
    	};
	});
	
	return isChecked;
}

function parseNumber2(valStr) {
	if ($.trim(valStr).length > 0) {
		valStr = $.parseNumber(valStr, {
			format : FORMAT_NUMBER,
			locale : APP_LOCALE
		});
		return parseFloat(valStr);
	} else {
		return 0;
	}
}

function openPopup( id, url, typeSubmit , data, callBack ) {
	var idRandom = Date.parse(new Date());
	var idPopup = "popup-" + id;
	var idModal = "popup-modal-" + id;
	
	$.ajax({
        url : url,
        type: typeSubmit,
        data : data,
        success:function(data, textStatus, jqXHR) {
        	
        	if( $("body").has("div#"+idPopup).length == 0 ) {
        		
        		var divPopup = $("<div>", {	
                    "id" : idPopup
                });
            	
            	divPopup.append($("<div>", {
            		"id"       : idModal,
                    "class"    : "modal modal-custom fade",
                    "tabindex" : "-1",
                    "role"     : "dialog"
                }));
            	
            	$("body").append(divPopup);
        	}
        	
        	$("#"+idModal).html(data);
        	$("#"+idModal).modal('show');
        	
        	if ( $.isFunction( callBack ) ) {
        		callBack( idModal );
            }
        },
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
    });
	
	return [idPopup, idModal];
}

function formatNumber(element, formatNumber) {
	if( formatNumber == null || formatNumber == '' ) {
		formatNumber = FORMAT_NUMBER;
	}
	
    $(element).parseNumber({format: formatNumber, locale: APP_LOCALE});
    $(element).formatNumber({format: formatNumber, locale: APP_LOCALE});
}

function openProcessImage(processId, processIntanceId, borrowRequestId, isProcessCurrent) {
	var ajaxUrl = BASE_URL + 'jbpm/deployment/processes/model';
	var data= {
		'processId'    : processId,
		'processIntanceId': processIntanceId,
		'borrowRequestId' : borrowRequestId,
		'isProcessCurrent': isProcessCurrent
	}
	openPopup( "popupProcessModal", ajaxUrl, "GET", data, null );
}

function openHistoryApprove(referenceId, referenceType) {
	var ajaxUrl = BASE_URL + 'popup/history_approve/view';
	var data= {
		'referenceId'    : referenceId,
		'referenceType'  : referenceType,
	}
	openPopup( "popupHistoryAprove", ajaxUrl, "GET", data, null );
}

function changeLang(url, lang) {
	var pathname = $(location).attr('pathname');
	var param = $(location).attr('search');
	
	if(param.length > 0){
		if(param[0] == "?"){
			param = param.substring(1, param.length);
 		}
		if(param.lastIndexOf("lang=") != -1){
			param = param.substring(0, param.lastIndexOf("lang="));
		}

		while(param.lastIndexOf("&") != -1){
			param = param.replace('&',',');
		};
 		
	}
	window.location = BASE_URL+"change/lang?lang=" + lang + "&url=" + pathname + "&param=" + param;
}

function changeStyle(url, style) {
	var pathname = $(location).attr('pathname');
	var param = $(location).attr('search');
	
	if(param.length > 0){
		if(param[0] == "?"){
			param = param.substring(1, param.length);
 		}
		if(param.lastIndexOf("lang=") != -1){
			param = param.substring(0, param.lastIndexOf("lang="));
		}

		while(param.lastIndexOf("&") != -1){
			param = param.replace('&',',');
		};
 		
	}
	window.location = BASE_URL+"change/style?style=" + style + "&url=" + pathname + "&param=" + param;
}

/**
 *  InitPlupload
 * @param browse_button
 * @param container
 * @param uploadUrl 
 * @param multi_selection
 * @param max_file_size
 * @param mime_types
 * @param filelist
 * @param console
 * @param FileUploaded
 * @param UploadComplete
 * @param url
 * @returns
 */
function InitPlupload(browse_button, container, uploadUrl, multi_selection, max_file_size, mime_types, filelist, console, FileUploaded, UploadComplete, url) {
	var token = $("meta[name='_csrf']").attr("content");
	var uploader = new plupload.Uploader({
		runtimes : 'html5,flash,silverlight,html4',

		browse_button : browse_button, // you can pass in id...
		container : document.getElementById(container), // ... or DOM Element
		// itself

		url : uploadUrl,

		// Resize images on client-side if we can
		resize : {},
		
		// Add token csrf
		headers : {
			'X-CSRF-TOKEN' : token
		},

		multi_selection : multi_selection,

		filters : {
			max_file_size : max_file_size,
			mime_types : mime_types
		},

		// Flash settings
		flash_swf_url : url + 'static/js/plupload-2.1.2/Moxie.swf',

		// Silverlight settings
		silverlight_xap_url : url + 'static/js/plupload-2.1.2/Moxie.xap',

		init : {
			PostInit : function() {
				document.getElementById(filelist).innerHTML = '';
			},

			FilesAdded : function(up, files) {
				// multi_selection is false
				if (!multi_selection) {
					// set empty fileList
					$("#" + filelist).empty();
				}
				plupload.each(files, function(file) {
					document.getElementById(filelist).innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
				});

				$("#" + filelist).show();
				uploader.start(); // auto start when FilesAdded
			},

			UploadProgress : function(up, file) {
				document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
			},

			Error : function(up, err) {
				document.getElementById(console).innerHTML = "\nError #" + err.code + ": " + err.message;
				$("#" + console).show();
			},

			FileUploaded : FileUploaded,
			UploadComplete : UploadComplete
		}
	});

	uploader.init();
}

/**
 * cut string
 * @param str
 * @returns str
 */
function cutString(str) {
	if (str != null && str != '') {
		if (str.length > 2) {
			if (str.indexOf('["') == 0) {
				str = str.substring(2, str.length);
			}
			if (str.indexOf('"]') == str.length - 2) {
				str = str.substring(0, str.length - 2);
			}
		}
	}

	return str;
}

/**
 * ajax search method post
 * @param url
 * @param condition
 * @param tableId
 * @param element
 * @param event
 */
function ajaxSearch(url, condition, tableId, element, event) {
	if (event != undefined) {
		event.preventDefault();
	}

	var me = $(element);
	if (me.data('requestRunning')) {
		return;
	}
	me.data('requestRunning', true);

	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : condition,
		success : function(data) {
			$("#" + tableId).html(data);
		},
		complete : function(result) {
			me.data('requestRunning', false);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

/** ajax redirect method get
 * @param url
 * @param flagScrollTop
 * @returns
 */
function ajaxRedirect(url, flagScrollTop) {
	
	if (flagScrollTop === undefined) {
		flagScrollTop = true;
	}
	$.ajax({
		type : "GET",
		url : url,
		beforeSend: blockbg(),
		success : function(data) {
			var content = $(data).find('.body-content');
			$(".main_content").html(content);
			var newUrl = $(content).find("#url").val();
			if(typeof newUrl != 'undefined' && newUrl != null && newUrl != ''){
				url = BASE_URL + newUrl;
			}

			// console.log(url);
			
			try {
				if (url.startsWith('//')) {
					url = url.substring(1);
				}
			} catch(err) {
				console.log(err);
			}

			try {
				window.history.pushState('', '', url);
			} catch(err) {
				console.log(err);
			}
			
			
			if( flagScrollTop ) {
				goTopPage();
			}
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

/**
 * ajax submit method POST
 * @param url
 * @param condition
 * @param event
 */
function ajaxSubmit(url, condition, event, flagScrollTop) {
	event.preventDefault();
	
	if (flagScrollTop === undefined) {
		flagScrollTop = true;
	}

	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : condition,
		success : function(data, textStatus, request) {
			var msgFlag = request.getResponseHeader('msgFlag');
			
			if( "1" == msgFlag ) {
				$("#message").html(data);
			} else {
				var content = $(data).find('.body-content');
				$(".main_content").html(content);
			}
			
			var urlPage = $(data).find('#url').val();
			if (urlPage != null && urlPage != '') {
				window.history.pushState('', '', BASE_URL + urlPage);
			}
			
			if( flagScrollTop ) {
				goTopPage();
			}
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

/**
 * show tab if exists error
 */
function showTabError(languageList) {
	if (languageList !== undefined && languageList != null) {
		$.each(languageList, function(key) {
			var errorServer = $("#language" + key).find(".has-error").length;
			var errorClient = false;

			$("#language" + key).find("label.error").each(function() {
				if ($(this).html().length > 0) {
					errorClient = true;
					return false;
				}
			});

			if (errorClient || errorServer > 0) {
				$('#tabLanguage a[href="#language' + key +'"]').tab('show')
				return false;
			}
		});
	}

}

function isNumber(evt, element) {

    var charCode = (evt.which) ? evt.which : event.keyCode
    // only accept number in range 0->9 and . and backspace 		
    if ((charCode >= 48 && charCode <= 57) || charCode == 46 || charCode == 8){
    	if (charCode == 46) {
    		// only 1 element .
    		var text = $(element).val();
            if (text.toString().indexOf(".") != -1) {
                return false;
            }
        }
    	return true;
    }        
    return false;
}  

function blockbg() {
	$.isLoading({
		text : LOADING
	});
}
function unblockbg() {
	$.isLoading("hide");
}

function date_time(id)
{
    date = new Date;
    year = date.getFullYear();
    month = date.getMonth();
    months = new Array('January', 'February', 'March', 'April', 'May', 'June', 'Jully', 'August', 'September', 'October', 'November', 'December');
    d = date.getDate();
    day = date.getDay();
    days = new Array('Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday');
    h = date.getHours();
    if(h<10)
    {
            h = "0"+h;
    }
    m = date.getMinutes();
    if(m<10)
    {
            m = "0"+m;
    }
    s = date.getSeconds();
    if(s<10)
    {
            s = "0"+s;
    }
    result = ''+days[day]+' '+months[month]+' '+d+' '+year+' '+h+':'+m+':'+s;
    document.getElementById(id).innerHTML = result;
    setTimeout('date_time("'+id+'");','1000');
    return true;
}

function goTopPage() {
	$("html, body").animate({ scrollTop: 0 }, "1");
}

var stompClient = null;
var sessionId = "";
var socketFail = 0;
function connect() {
	disconnect();
	var useId = $("#accountid-login").val();
    var socket = new SockJS(BASE_URL + 'notifications');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;   
	var token = $("meta[name='_csrf']").attr("content");
  	var header = $("meta[name='_csrf_header']").attr("content");
	var headers = {};
	headers[header] = token;
    stompClient.connect(headers, function (frame) {
    	//console.log('STOMP: Connect success');
    	socketFail = 0;
    	var url = stompClient.ws._transport.url;
        url = url.replace("/websocket", "");
        url = url.substring(url.lastIndexOf('/')+1);
        sessionId = url;
        stompClient.subscribe('notifications/user/queue/specific-user' + '-'+ useId + '-user' + sessionId, function (msgOut) {
        	var count = JSON.parse(msgOut.body).message;
        	if(count <= 0 || count == null){
        		$("#notify-count").html(0);	
        	}else{
        		$('#notify-count').removeClass('hidden');
        		$('#notify-count').html(count);
        	}
        });
    }, stompFailureCallback);
}

var stompFailureCallback = function (error) {
    //console.log('STOMP: ' + error);
    if(socketFail < 3){
    	setTimeout(connect, 2000);
        //console.log('STOMP: Reconecting in 2 seconds');
        socketFail ++
    }else{
    	disconnect();
    }
};

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    //console.log("Disconnected");
}

function sendNotifications(functionCode, message) {
	var token = $("meta[name='_csrf']").attr("content");
  	var header = $("meta[name='_csrf_header']").attr("content");
	var headers = {};
	headers[header] = token;
	var useName = $("#username-login").val()
	//var useId = $("#accountid-login").val();
	if (stompClient != null) {
		var userFrom = 'System';
		stompClient.send("/app/notifications", {}, JSON.stringify({
			'from' : userFrom,
			'functionCode' : functionCode,
			'message' : message,
			//'userId': useId,
			'to': useName
		}));
	}
}

function sendMessage() {
    stompClient.send("/app/session-listener", {}, JSON.stringify({}));
}

function defineDuplicate(className){
	var isHaveDuplicate = false;
	$("."+className).on('input',function(){
		if(loadDuplicate(className)){
			isHaveDuplicate = true;
		}
	});
//	console.log("defineDuplicate:" + className + isHaveDuplicate);
	return isHaveDuplicate;
}
function defineDuplicate2(className){
	var isHaveDuplicate = false;
	$("."+className).each(function(){
		if(loadDuplicate(className)){
			isHaveDuplicate = true;
		}
	});
//	console.log("defineDuplicate:" + className + isHaveDuplicate);
	return isHaveDuplicate;
}
function loadDuplicate(className){
	var arr = [];
	var isHaveDuplicate = false;

	$("."+className).each(function(){
		 var value = $(this).val().trim();
//		 console.log(value);
		 var tdTag = $(this).closest('td');
		 if(value!=''){
		    if (arr.indexOf(value) == -1){
		        arr.push(value);
		        $(this).removeClass("duplicate");
		        tdTag.find("span").remove();
		    } else {
		        $(this).addClass("duplicate");
		        
		      
		        if(tdTag.find("span").length!=0){
		        	tdTag.find("span").html('<span class="help-block text-center" style="color:red;"> Duplicate value. Please change!</span>');
		        }else{
		        	tdTag.append('<span class="help-block text-center" style="color:red;"> Duplicate value. Please change!</span>');
		        }
		        isHaveDuplicate = true;
		    }
		 }
	});
	
//	console.log("loadDuplicate:" + className + isHaveDuplicate);
	return isHaveDuplicate;
}
function importCommon(urlController) {
	event.preventDefault();
	  
	$("#import-component-modal").find('.modal-dialog').css('width','800px');
	$("#import-component-modal").modal('show');
	$("#fileList").empty();
	$("#message-upload-id").empty();
	$("#message-inprocess").empty();
	$('#sheetExcelSelectId').html('<select class="form-control" id="sheetExcelId" name="sheetNames"></select>');
	 var $selectForm =  $('#sheetExcelId');
	  $selectForm.multiselect({
	    	includeSelectAllOption: true,
	    	placeholder: "Select sheet name!",
	    	search: true
	  });
	$('#progressBar').attr("aria-valuemax",0);
	$('#progressBar').attr("style","width: 0%");
	$('#progressBar').empty();
	
}
function toggleSelecAll(source) {
	 $('#sheetExcelId').prop("selected", "selected");
}
function readSheet(e,file) {
	  var reader = new FileReader();
	  reader.onload = function(e) {
		  var data = e.target.result;
		  if(!rABS) data = new Uint8Array(data);
		  var workbook = XLSX.read(data, {type: rABS ? 'binary' : 'array'});
		  var i = 0;
		  workbook.SheetNames.forEach(function(sheetName){	
			  var select = document.getElementById('sheetExcelId');
			  var opt = document.createElement('option');
			    opt.value = i;
			    opt.className = "select-sheet";
			    opt.innerHTML = sheetName;
			    select.appendChild(opt);
		        
			    i++;
		  });
		  $('#sheetExcelId').multiselect({
			  selectAll:true
		  });
		  $('#sheetExcelId').attr("multiple","multiple");	
	  };
	  if(rABS) reader.readAsBinaryString(file); else reader.readAsArrayBuffer(file);
}

/**
 * doExport
 * @param url
 * @param pramObject
 */
function doExport(url, pramObject) {
	var $formExport = jQuery("<form>", {
        'action' : BASE_URL + url,
        'method' : 'POST'
    });
	
	// add fields
	$.each(pramObject, function(key, val) {
		$formExport.append(jQuery("<input>", {	
	        "name" : key,
	        "value": val,
	        "type" : "hidden"
	    }));
	});
	
	// _csrf must be add in <head>
    var token = $("meta[name='_csrf']").attr("content");
    var csrfToken = $(document.createElement('input'));
    $(csrfToken).attr("type", "hidden");
    $(csrfToken).attr("name", "_csrf");
    $(csrfToken).val(token);
    $($formExport).append($(csrfToken));
	
	$formExport.appendTo(document.body);
	$formExport.submit().remove();
	
	unblockbg();
}

/**
 * Load data combobox.
 * Return List<Select2Dto> type Json
 */
function loadDataCombobox(url, condition, comboboxId , event, callBack) {
	event.preventDefault();
	
	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		global: false,
	    beforeSend: function(xhrObj) {
	    	var token = $("meta[name='_csrf']").attr("content");
		  	var header = $("meta[name='_csrf_header']").attr("content");
		  	xhrObj.setRequestHeader(header, token);
	    },
		data : condition,
		success : function(data, textStatus, request) {
			$(comboboxId).empty();
			if(data.length > 0) {
				var option = "<option value = ''></option>";
		        $(comboboxId).append(option);
	        	for(var i = 0; i < data.length ; i++) {
	        		var option = "<option value = " + data[i].id + ">" + data[i].text +  "</option>";
	                $(comboboxId).append(option);
	        	}
	        }
			
			if ( $.isFunction( callBack ) ) {
        		callBack();
            }
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

/**
 * Load data combobox for select2.
 * Return List<Select2Dto> type Json
 */
function searchCombobox(element, placeholder, url, data, dataResult, allowClear) {
	if (allowClear === undefined) {
		allowClear = true;
	}
	$(element).select2({
		placeholder : placeholder,
		minimumInputLength : 0,
		allowClear : allowClear,
		ajax : {
			url : BASE_URL + url,
			global: false,
		    beforeSend: function(xhrObj) {
		    	var token = $("meta[name='_csrf']").attr("content");
			  	var header = $("meta[name='_csrf_header']").attr("content");
			  	xhrObj.setRequestHeader(header, token);
		    },
			dataType : 'json',
			type : "POST",
			quietMillis : 50,
			data : data,
			processResults : dataResult
		},
	});
}

/**
 * 
 * ajaxSubmitPopup-
 * 
 * @Param url
 * @Param condition
 * @Param modalContentId
 * @Param event
 * @author KhuongTH
 */
function ajaxSubmitPopup(url, condition, modalId, event) {
	event.preventDefault();
	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : condition,
		success : function(data, textStatus, request) {
			var msgFlag = request.getResponseHeader("msgFlag");
			if( "1" == msgFlag ) {
				var contentModal = $(modalId).find('.modal-content');
				var contentData = $(data).find('.modal-content').html();
				$(contentModal).html(contentData);
			} else {
				var content = $(data).find('.body-content');
				
				$(modalId).one('hidden.bs.modal', function () {
					$(".main_content").html(content);
				});
				$(modalId).modal('hide');
			}
			
			var urlPage = $(data).find('#url').val();
			if (urlPage != null && urlPage != '') {
				window.history.pushState('', '', BASE_URL + urlPage);
			}
			
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}

function getUrlParameter(sParam) {
	var sPageURL = decodeURIComponent(window.location.search.substring(1)), sURLVariables = sPageURL.split('&'), sParameterName, i;

	for (i = 0; i < sURLVariables.length; i++) {
		sParameterName = sURLVariables[i].split('=');

		if (sParameterName[0] === sParam) {
			return sParameterName[1] === undefined ? true : sParameterName[1];
		}
	}
};

//from data - to date
function fromDateToDateConfig(startDate, endDate) {
	if(startDate == undefined){
		startDate = null;
	}
	if(endDate == undefined){
		endDate = null;
	}
	
	$('.datepicker-from-date, .datepicker-to-date').datepickerUnit({
		format: DATE_FORMAT,
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : APP_LOCALE.toLowerCase(),
		todayHighlight : true,
		startDate : startDate,
		endDate : endDate
	  });
	
	$('.datepicker-from-date').on('changeDate', function (selected) {
        var minDate = new Date(selected.date.valueOf());
        $('.datepicker-to-date').datepickerUnit('setStartDate', minDate);
    }).on('clearDate', function() {
		$('.datepicker-to-date').datepickerUnit('setStartDate', null);
	});

    $('.datepicker-to-date').on('changeDate', function (selected) {
    	var maxDate = new Date(selected.date.valueOf());
      	$('.datepicker-from-date').datepickerUnit('setEndDate', maxDate);
    }).on('clearDate', function() {
    	$('.datepicker-from-date').datepickerUnit('setEndDate', null);
	});
}

//datepicker
function initDatepicker(startDate, endDate) {
	if(startDate == undefined){
		startDate = null;
	}
	if(endDate == undefined){
		endDate = null;
	}
	
	$('.datepicker').datepickerUnit({
		format: DATE_FORMAT,
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : APP_LOCALE.toLowerCase(),
		todayHighlight : true
	});
	
	$('.datepicker').datepickerUnit('setStartDate', startDate).datepickerUnit('setEndDate', endDate);
}

function initFromDateToData(startDate, endDate) {
	
	if(startDate == undefined){
		startDate = null;
	}
	if(endDate == undefined){
		endDate = null;
	}
	
	$('.datepicker-from-date').datepickerUnit('setStartDate', startDate).datepickerUnit('setEndDate', endDate);
	$('.datepicker-to-date').datepickerUnit('setStartDate', startDate).datepickerUnit('setEndDate', endDate);
	
	$('.datepicker-from-date').on('changeDate', function (selected) {
        var minDate = new Date(selected.date.valueOf());
        var to = $('.datepicker-to-date').datepickerUnit('getDate');
        var type = jQuery.type(to);
    	if(to === null || to === undefined) {
    		$(".datepicker-to-date").datepickerUnit("setDate", minDate);
    	} else if(type === 'date' && minDate > to) {
    		$(".datepicker-to-date").datepickerUnit("setDate", minDate);
    	}
        $('.datepicker-to-date').datepickerUnit('setStartDate', minDate);
    }).on('clearDate', function() {
		$('.datepicker-to-date').datepickerUnit('setStartDate', null);
	});

    $('.datepicker-to-date').on('changeDate', function (selected) {
    	var maxDate = new Date(selected.date.valueOf());
      	$('.datepicker-from-date').datepickerUnit('setEndDate', maxDate);
    }).on('clearDate', function() {
    	$('.datepicker-from-date').datepickerUnit('setEndDate', null);
	});
	
}

function appendTitle(){
    let titleHead = $('.title-head').text().trim();
    if ('' != titleHead && typeof titleHead !== 'undefined' && null != titleHead){
		 let title = APP_NAME + ' - ' + titleHead;
		 $('title').html(title);
    }
}

function htmlDecode(value) {
	  return $("<textarea/>").html(value).text();
}

function htmlEncode(value) {
	value = $('<textarea/>').text(value).html();
	//encodeSymbol
	var result = '';
    for (var i = 0; i < value.length; i++) { 
//    	let code = value.codePointAt(i);
    	let code = value.charCodeAt(i);
        if (code > 127) {
        	result += '&#' + code + ';';
        } else {
        	result += value.charAt(i);
        }
    }
    return result;
}

/**
 * doExportExcel
 * @param url
 * @param tokenName
 * @param pramObject
 */
function doExportExcelWithToken(url, tokenName, pramObject) {

	var timerToken;
	var tokenid = getToken();
	timerToken = setInterval(function () {
		if ($.cookie(tokenid) === 'OK') {
			unblockbg();
			clearInterval(timerToken);
		}
	}, 100);

	var $formExport = jQuery("<form>", {
		'action': url,
		'method': 'POST'
	});

	// add fields
	if (pramObject != null) {
		$.each(pramObject, function (key, val) {
			if (!isBlank(val) && typeof val == "string")
				val = val.trim();
			$formExport.append(jQuery("<input>", {
				"name": key,
				"value": val,
				"type": "hidden"
			}));
		});
	}

	if (tokenName == undefined || tokenName == null || tokenName == '') {
		tokenName = "tokenId";
	}

	$formExport.append(jQuery("<input>", {
		"name": tokenName,
		"value": tokenid,
		"type": "hidden"
	}));

	var tokenCsrf = $("meta[name='_csrf']").attr("content");

	$formExport.append(jQuery("<input>", {
		"name": "_csrf",
		"value": tokenCsrf,
		"type": "hidden"
	}));

	$formExport.appendTo(document.body);
	$formExport.submit().remove();

	blockbg();
}

function getToken() {
	var date = new Date();
	var day = date.getDate();
	var month = date.getMonth();
	var year = date.getFullYear();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();
	var millisecond = date.getMilliseconds();
	var id = day + '' + month + '' + year + '' + hour + '' + minute + '' + second + '' + millisecond;
	return id;
}

function isBlank(str) {
	return !(str && (str + '').trim().length > 0);
}

function changeDatepicker2(idEffectedDate, idExpiredDate) {
	var effectedDate = $(idEffectedDate).val();
	var expiredDate = $(idExpiredDate).val();

	var roundDay = 10000;
	var startDate = new Date('01/01/2010');

	if (effectedDate != null && effectedDate != "undefined" && effectedDate != "") {
		startDate = effectedDate;
	}

	var fromEndDate = new Date();
	fromEndDate.setDate(fromEndDate.getDate() + roundDay);

	if (expiredDate != null && expiredDate != "undefined" && expiredDate != "") {
		fromEndDate = expiredDate;
	}

	var toEndDate = new Date();
	toEndDate.setDate(toEndDate.getDate() + roundDay);

	$(idEffectedDate).parent('.datepicker-from').datepicker({
		format: COMMON_DATE_PICKER_DATE_FORMAT,
		changeMonth: true,
		changeYear: true,
		autoclose: true,
		keyboardNavigation: true,
		language: '${sessionScope.localeSelect}',
		startDate: effectedDate,
		endDate: expiredDate,
		todayHighlight: true,
	}).on('change', function () {
		var strDate = $(idEffectedDate).val();
		if (!isBlank(strDate)) {
			var date = parseDateCustom(strDate);
			startDate = new Date(date);
			startDate.setDate(startDate.getDate(date));
			$(idExpiredDate).parent('.datepicker-to').datepicker('setStartDate', startDate);
		}
		else {
			$(idExpiredDate).parent('.datepicker-to').datepicker('setStartDate', null);
		}
	});
	$(idExpiredDate).parent('.datepicker-to').datepicker({
		format: COMMON_DATE_PICKER_DATE_FORMAT,
		changeMonth: true,
		changeYear: true,
		autoclose: true,
		keyboardNavigation: true,
		language: '${sessionScope.localeSelect}',
		startDate: startDate,
		endDate: toEndDate,
		todayHighlight: true,
	}).on('change', function () {
		var strDate = $(idExpiredDate).val();
		if (!isBlank(strDate)) {
			var date = parseDateCustom(strDate);
			fromEndDate = new Date(date);
			fromEndDate.setDate(fromEndDate.getDate(date));
			$(idEffectedDate).parent('.datepicker-from').datepicker('setEndDate', fromEndDate);
		}
		else {
			$(idEffectedDate).parent('.datepicker-from').datepicker('setEndDate', null);
		}
	});

	$(idEffectedDate).on('keyup', function () {
		if ($(this).val() == '') {
			$(this).trigger('changeDate');
		}
	});

	$(idExpiredDate).on('keyup', function () {
		if ($(this).val() == '') {
			$(this).trigger('changeDate');
		}
	});
}

function changeDatepickerById(idEffectedDate,idExpiredDate, idFrom, idTo, startDateParam){
    var roundDay = 10000;
    var startDate = new Date('01/01/2010');
    if(startDateParam != null && startDateParam !='undefined' && startDateParam !=''){
        startDate =  startDateParam;
        if(idEffectedDate == null || idEffectedDate == '' || idEffectedDate== 'undefined'){
            idEffectedDate = startDateParam;
        }
    }
    
    if (idEffectedDate != null && idEffectedDate != "undefined" && idEffectedDate != ""){
        startDate = idEffectedDate;
    }
    
    var FromEndDate = new Date();   
    FromEndDate.setDate(FromEndDate.getDate() + roundDay);
    
    if (idExpiredDate != null && idExpiredDate != "undefined" && idExpiredDate != ""){
        FromEndDate = idExpiredDate;
    }
//  
    var ToEndDate = new Date(); 
    ToEndDate.setDate(ToEndDate.getDate() + roundDay);
    
    $(idFrom).datepickerUnit({
        format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
        keyboardNavigation : true,
        weekStart: 1,
        startDate: idEffectedDate,
        endDate: idExpiredDate,
        todayHighlight: true,
        autoclose: true
        })
        .on('changeDate', function (selected) {
                startDate = new Date(selected.date.valueOf());
                startDate.setDate(startDate.getDate(new Date(selected.date.valueOf())));
                $(idTo).datepickerUnit('setStartDate', startDate);  
            });
        $(idTo)
            .datepickerUnit({
                format: DATE_FORMAT,
                changeMonth: true,
                changeYear: true,
                autoclose: true,
                keyboardNavigation : true,
                weekStart: 1,
                startDate: startDate,
                endDate: ToEndDate,
                todayHighlight: true,
                autoclose: true
            })
            .on('changeDate', function (selected) {
                FromEndDate = new Date(selected.date.valueOf());
                FromEndDate.setDate(FromEndDate.getDate(new Date(selected.date.valueOf())));
                $(idFrom).datepickerUnit('setEndDate', FromEndDate);
            });
}


/**
 * @author TaiTM: khởi tạo link ajax để sort các colum
 * @Paparam url: đường dẫn
 * @Paparam sortName: sortName tên cột
 * @Paparam listColumn: Danh sách cột
 * @type Loại sắp xếp [ASC /DESC]
 * @example constructSortAjax("account/ajaxList", "USERNAME", ["FULLNAME", "USERNAME"], 1)
*/
function constructSortAjax(urlAjax, sortName, listColumn, typeSort, cname){
    let url = getCookie(cname);
    
    if (url == "" || urlAjax.indexOf('ajaxList') == -1){
        url = urlAjax;
    }

    let reulst = url;
    
    let sort = "sort=" + sortName;
    if (typeSort === undefined || typeSort.toUpperCase() == "asc".toUpperCase()){
        sort = sort + ",asc";
    } else {
        sort = sort + ",desc";
    }

    // check url
    if (url.indexOf("sort=") == -1) {
        reulst = url + "?" + sort;
        
        setCookie(cname, reulst, 1);
        
        return reulst;
    } else {
        let str = url.split("?")[0];
        let sortTmp = "";
        for (let i = 0; i < listColumn.length; i++) {
            let name = listColumn[i];
            if (name === sortName) {
                sortTmp = getTextSort(sortTmp, sort);
            } else {
                let textSort = "sort=" + name;
                let index = url.indexOf(textSort);
                if (index > -1){
                    let typeSort = url.substr(index + textSort.length + 1, 3);
                    if (typeSort == "asc" || typeSort === undefined){
                        sortTmp = getTextSort(sortTmp, textSort + ",asc");
                    }else{
                        sortTmp = getTextSort(sortTmp, textSort + ",desc");
                    }
                }
            }
        }
        
        reulst = str + sortTmp;
        
        setCookie(cname, reulst, 1);
        
        return reulst;
    }
}

function showIconTableSort(cname, divParentTableId){
    let tableHeader = getCookie(cname).split("?")[1];
    if (tableHeader){
        let values = tableHeader.split("&");
        for (let i=0;i<values.length;i++){
            let data = values[i];

            let name = data.split(",")[0].replace("sort=", "");
            let type = data.split(",")[1];

			$('#' + divParentTableId + ' > table > thead > tr > th').each(function (key, val){
				let column = $(val).data('column');
				if (column == name){
		            $(val).find('i').removeClass("fa-sort");
		            if (type == "asc") {
		                $(val).find('i').addClass("fa-angle-down");
		            } else if (type == "desc") {
		                $(val).find('i').addClass("fa-angle-up");
		            }
				}
			})
			

        }
    }
}

function getTextSort(sortTmp, sort){
    let reulst = "";
    if (sortTmp === "") {
        reulst = "?" + sort
    } else {
        reulst = sortTmp + "&" + sort;
    }
    
    return reulst;
}

function setCookie(cname, cvalue, exdays) {
    const d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    let expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
}

function getCookie(cname) {
    let name = cname + "=";
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function deleteCookie(cname){
    document.cookie = cname + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
}

function deleteAllCookie(){
	var cookies = document.cookie.split(";");
    for (var c = 0; c < cookies.length; c++) {
        var d = window.location.hostname.split(".");
        while (d.length > 0) {
            var cookieBase = encodeURIComponent(cookies[c].split(";")[0].split("=")[0]) + '=; expires=Thu, 01-Jan-1970 00:00:01 GMT; domain=' + d.join('.') + ' ;path=';
            var p = location.pathname.split('/');
            document.cookie = cookieBase + '/';
            while (p.length > 0) {
                document.cookie = cookieBase + p.join('/');
                p.pop();
            };
            d.shift();
        }
    }
}

function setCSRFtoRequest(request){
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    request.setRequestHeader(header, token);
}

function ajaxHtml(url, data, action, elementId) {
    let rs = "";
    $.ajax({
        url : BASE_URL + url,
        type : action,
        global : false,
        data: data,
        beforeSend: function(request){
            setCSRFtoRequest(request);
        }
    }).done(function(data){
        rs = $(data).get(1);
        $("#" + elementId).parent().html(rs);
    });
    
    return rs;
}

function initLinkAliasForListEvent(nameSelector, listLinkAlias) {
  nameSelector.keyup(function (event) {
    var k = event.which;
    // Verify that the key entered is not a special key
    if (
      k == 20 /* Caps lock */ ||
      k == 16 /* Shift */ ||
      k == 9 /* Tab */ ||
      k == 27 /* Escape Key */ ||
      k == 17 /* Control Key */ ||
      k == 91 /* Windows Command Key */ ||
      k == 19 /* Pause Break */ ||
      k == 18 /* Alt Key */ ||
      k == 93 /* Right Click Point Key */ ||
      (k >= 35 && k <= 40) /* Home, End, Arrow Keys */ ||
      k == 45 /* Insert Key */ ||
      (k >= 33 && k <= 34) /*Page Down, Page Up */ ||
      (k >= 112 && k <= 123) /* F1 - F12 */ ||
      (k >= 144 && k <= 145)
    ) {
      /* Num Lock, Scroll Lock */
	} else {
		var txtName = $(nameSelector).val();
		linkAlias = nameToLinkAlias(txtName);
		for (var i = 0, sz = listLinkAlias.length; i < sz; i++) {
			$(listLinkAlias[i]).val(linkAlias);
		}
		
		$("input[type=text][name$='.keywords']").removeClass('error');
		$("input[type=text][name$='.keywords']").parent().find('label.error').remove();
		
		$("input[type=text][name$='.keyword']").removeClass('error');
		$("input[type=text][name$='.keyword']").parent().find('label.error').remove();
	}
  });
}

function initTagSelectorEvent(nameSelector, linkAliasSelector) {
  nameSelector.keyup(function (event) {
    var k = event.which;
    // Verify that the key entered is not a special key
    if (
      k == 20 /* Caps lock */ ||
      k == 16 /* Shift */ ||
      k == 9 /* Tab */ ||
      k == 27 /* Escape Key */ ||
      k == 17 /* Control Key */ ||
      k == 91 /* Windows Command Key */ ||
      k == 19 /* Pause Break */ ||
      k == 18 /* Alt Key */ ||
      k == 93 /* Right Click Point Key */ ||
      (k >= 35 && k <= 40) /* Home, End, Arrow Keys */ ||
      k == 45 /* Insert Key */ ||
      (k >= 33 && k <= 34) /*Page Down, Page Up */ ||
      (k >= 112 && k <= 123) /* F1 - F12 */ ||
      (k >= 144 && k <= 145)
    ) {
      /* Num Lock, Scroll Lock */
	} else {
		var txtName = $(nameSelector).val();
		linkAlias = nameToTag(txtName);
		linkAliasSelector.val(linkAlias);
		
		$(nameSelector).parent().find('label.error').remove();
		
		$("input[type=text][name$='.keywordsSeo']").removeClass('error');
		$("input[type=text][name$='.keywordsSeo']").parent().find('label.error').remove();
		
		$("input[type=text][name$='.linkAlias']").removeClass('error');
		$("input[type=text][name$='.linkAlias']").parent().find('label.error').remove();
	}
  });
}

function InitPlupload(browse_button, container, uploadUrl, multi_selection, max_file_size, mime_types, filelist, imageConsole, FileUploaded,
	UploadComplete, url, msgConfrimBeforeUpload, resize, BeforeUpload, Browse) {
	if (BeforeUpload == null) {
		BeforeUpload = function(up, file) { };
	}

	if (Browse == null) {
		Browse = function(up) { };
	}

	var token = $("meta[name='_csrf']").attr("content");

	var uploader = new plupload.Uploader({
		runtimes: "html5,flash,silverlight,html4",

		browse_button: browse_button, // you can pass in id...
		container: document.getElementById(container), // ... or DOM Element
		// itself

		url: uploadUrl,

		// Resize images on client-side if we can
		resize: {},

		// Add token csrf
		headers: {
			'X-CSRF-TOKEN': token
		},

		multi_selection: multi_selection,

		filters: {
			max_file_size: max_file_size,
			mime_types: mime_types,
			min_width_x_min_height: resize,
		},

		// Flash settings
		flash_swf_url: url + "static/js/plupload-2.1.2/Moxie.swf",

		// Silverlight settings
		silverlight_xap_url: url + "static/js/plupload-2.1.2/Moxie.xap",

		init: {
			PostInit: function() {
				document.getElementById(filelist).innerHTML = "";
			},

			FilesAdded: function(up, files) {
				if (msgConfrimBeforeUpload == null || msgConfrimBeforeUpload == "") {
					// multi_selection is false
					if (!multi_selection) {
						// set empty fileList
						$("#" + filelist).empty();
					}
					plupload.each(files, function(file) {
						document.getElementById(filelist).innerHTML +=
							'<div id="' +
							file.id +
							'">' +
							file.name +
							" (" +
							plupload.formatSize(file.size) +
							") <b></b></div>";
					});
					$("#" + filelist).show();
					uploader.start(); // auto start when FilesAdded
				} else {
					popupConfirm(msgConfrimBeforeUpload, function(result) {
						if (result) {
							// multi_selection is false
							if (!multi_selection) {
								// set empty fileList
								$("#" + filelist).empty();
							}
							plupload.each(files, function(file) {
								document.getElementById(filelist).innerHTML +=
									'<div id="' +
									file.id +
									'">' +
									file.name +
									" (" +
									plupload.formatSize(file.size) +
									") <b></b></div>";
							});
							$("#" + filelist).show();
							uploader.start(); // auto start when FilesAdded
						} else {
							plupload.each(files, function(file) {
								uploader.removeFile(file);
							});
						}
					});
				}
			},

			UploadProgress: function(up, file) {
				document
					.getElementById(file.id)
					.getElementsByTagName("b")[0].innerHTML =
					"<span>" + file.percent + "%</span>";
			},

			Error: function(up, err) {
				var type = err.file.type;
				let ERROR_IMG_MIN_WIDTH_HEIGHT = 401;
				let ERROR_IMG_SIZE_VIDEO = 500;
                let ERROR_FILE_SIZE = -600;
                let FILE_EXTENSION_ERROR = -601;
                
                $('.img_banner').removeAttr('src');
                $('.videoContent').remove();
                

				switch (err.code) {
					
                    case ERROR_FILE_SIZE:
                        if(type.includes("video"))
                        {
                            document.getElementById(imageConsole).innerHTML =
                            ERROR_VIDEO_SIZE;
                            
                        }
                        else{
                            document.getElementById(imageConsole).innerHTML =
                            ERROR_IMG_SIZE;
                            
                        }
                        $("#" + imageConsole).show();
                        break;
					case FILE_EXTENSION_ERROR:
						document.getElementById(imageConsole).innerHTML =
							ERROR_FILE_EXTENSTION;
                        $("#" + imageConsole).show();

						break;
					case ERROR_IMG_MIN_WIDTH_HEIGHT:
						document.getElementById(imageConsole).innerHTML =
							MSG_ERROR_MIN_SIZE_COMMON;
						$("#" + imageConsole).show();
                        $('.videoContent').hide();
						break;
					case ERROR_IMG_SIZE_VIDEO:
						if (
							type == "application/msword" ||
							type ==
							"application/vnd.openxmlformats-officedocument.wordprocessingml.document" ||
							type == "application/vnd.ms-excel" ||
							type ==
							"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" ||
							type == "application/pdf"
						) {
							document.getElementById(imageConsole).innerHTML = ERROR_DOCUMENT;
							$("#" + imageConsole).show();
							break;
						} else if (
							type == "video/avi" ||
							type == "video/wma" ||
							type == "video/mp4" ||
							type == "video/flv"
						) {
							document.getElementById(imageConsole).innerHTML = ERROR_VIDEO;
							$("#" + imageConsole).show();
							break;
						}

					default:
						document.getElementById(imageConsole).innerHTML =
							"\nError #" + err.code + ": " + err.message;
						$("#" + imageConsole).show();
						break;
				}
			},
			Browse: Browse,
			BeforeUpload: BeforeUpload,
			FileUploaded: FileUploaded,
			UploadComplete: UploadComplete,
		},
	});

	uploader.init();
}

function isNull(value){
    let check = false;
    if (value === undefined || value == null || value === ""){
        check = true;
    }
    return check;
}


function changeDatepickerSearch(idEffectedDate, idExpiredDate) {
  var roundDay = 10000;
  var startDate = new Date("01/01/2010");

  if (idEffectedDate != null && idEffectedDate != "undefined") {
    startDate = idEffectedDate;
  }

  var FromEndDate = new Date();
  FromEndDate.setDate(FromEndDate.getDate() + roundDay);

  if (idExpiredDate != null && idExpiredDate != "undefined") {
    FromEndDate = idExpiredDate;
  }
  //
  var ToEndDate = new Date();
  ToEndDate.setDate(ToEndDate.getDate() + roundDay);

  let oneDayInMs = 1 * 24 * 60 * 60 * 1000;

  $(".datepicker-from")
    .datepicker({
      format: "dd/mm/yyyy",
      changeMonth: true,
      changeYear: true,
      autoclose: true,
      keyboardNavigation: true,
      weekStart: 1,
      startDate: idEffectedDate,
      endDate: idExpiredDate,
      autoclose: true,
    })
    // .on('changeDate', function (selected) {
    //     startDate = new Date(selected.date.valueOf());
    //     startDate.setDate(startDate.getDate(new Date(selected.date.valueOf())));
    .on("change", function (event) {
      // console.log("$(event.target).val()", $(event.target).val());

      let dateFromString = $(event.target).val();
      //   let dateFromValue = new Date(dateFromString);

      //   if (!dateFromValue || dateFromValue == "Invalid Date") {
      let dateFromValue = new Date(
        Date.parse(
          dateFromString.replace(/\/|-/g, " ").split(" ").reverse().join(" ")
        ) + oneDayInMs
      );
      //   }

      if (!dateFromValue || dateFromValue == "Invalid Date") {
        dateFromValue = new Date(0);
      }

      // console.log(dateFromValue);
      startDate = dateFromValue;
      startDate.setDate(startDate.getDate(dateFromValue));
      // console.log('startDate', startDate);

      $(".datepicker-to").datepicker("setStartDate", startDate);
    });
  if (
    idEffectedDate != null &&
    idEffectedDate != "undefined" &&
    idEffectedDate != ""
  ) {
    // $(".datepicker-from").datepicker("setDate", idEffectedDate);
  }
  $(".datepicker-to")
    .datepicker({
      format: "dd/mm/yyyy",
      changeMonth: true,
      changeYear: true,
      autoclose: true,
      keyboardNavigation: true,
      weekStart: 1,
      startDate: startDate,
      endDate: ToEndDate,
      autoclose: true,
    })
    // .on("changeDate", function (selected) {
    //   FromEndDate = new Date(selected.date.valueOf());
    //   FromEndDate.setDate(
    //     FromEndDate.getDate(new Date(selected.date.valueOf()))
    //   );
    .on("change", function (event) {
      let dateToString = $(event.target).val();
      // let dateToValue = new Date(dateToString);

      //   if (!dateToValue || dateToValue == "Invalid Date") {
      let dateToValue = new Date(
        Date.parse(
          dateToString.replace(/\/|-/g, " ").split(" ").reverse().join("-")
        ) - oneDayInMs
      );
      //   }

      if (!dateToValue || dateToValue == "Invalid Date") {
        dateToValue = new Date(Date.parse("9999-12-31"));
      }

      let differentDate = true;

      try {
      	differentDate = !FromEndDate || !dateToValue || FromEndDate.getTime() != dateToValue.getTime();
      } catch (err) {
  	    console.log("err", err);
      }

      if (differentDate) {
        FromEndDate = dateToValue;

        FromEndDate.setDate(FromEndDate.getDate(dateToValue));

        $(".datepicker-from").datepicker("setEndDate", FromEndDate);
      }
    });
  if (
    idExpiredDate != null &&
    idExpiredDate != "undefined" &&
    idExpiredDate != ""
  ) {
    // $(".datepicker-to").datepicker("setDate", idExpiredDate);
  }
}

function initLinkAliasSelectorEvent(nameSelector, linkAliasSelector) {
  nameSelector.keyup(function (event) {
    var k = event.which;
    // Verify that the key entered is not a special key
    if (event.ctrlKey) {
    } else if (
      k == 20 /* Caps lock */ ||
      k == 16 /* Shift */ ||
      k == 9 /* Tab */ ||
      k == 27 /* Escape Key */ ||
      k == 17 /* Control Key */ ||
      k == 91 /* Windows Command Key */ ||
      k == 19 /* Pause Break */ ||
      k == 18 /* Alt Key */ ||
      k == 93 /* Right Click Point Key */ ||
      (k >= 35 && k <= 40) /* Home, End, Arrow Keys */ ||
      k == 45 /* Insert Key */ ||
      (k >= 33 && k <= 34) /*Page Down, Page Up */ ||
      (k >= 112 && k <= 123) /* F1 - F12 */ ||
      (k >= 144 && k <= 145)
    ) {
      /* Num Lock, Scroll Lock */
    } else {
      var txtName = $(nameSelector).val();
      linkAlias = nameToLinkAlias(txtName);
      linkAliasSelector.val(linkAlias);
    }
  });
}


function changeDatepicker(idEffectedDate,idExpiredDate){
    
    var roundDay = 10000;
    var startDate = new Date('01/01/2010');
    
    if (idEffectedDate != null && idEffectedDate != "undefined"){
        startDate = idEffectedDate;
    }
    
    var FromEndDate = new Date();   
    FromEndDate.setDate(FromEndDate.getDate() + roundDay);
    
    if (idExpiredDate != null && idExpiredDate != "undefined"){
        FromEndDate = idExpiredDate;
    }
//  
    var ToEndDate = new Date(); 
    ToEndDate.setDate(ToEndDate.getDate() + roundDay);      
    
    $('.datepicker-from').datepicker({
        format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
        keyboardNavigation : true,
        weekStart: 1,
        startDate: idEffectedDate,
        endDate: idExpiredDate,
        autoclose: true
        })
        .on('changeDate', function (selected) {
                startDate = new Date(selected.date.valueOf());
                startDate.setDate(startDate.getDate(new Date(selected.date.valueOf())));
                $('.datepicker-to').datepicker('setStartDate', startDate);  
            });
    if (idEffectedDate != null && idEffectedDate != "undefined" && idEffectedDate != ""){
        $('.datepicker-from').datepicker('setDate', idEffectedDate);
    }
    $('.datepicker-to')
            .datepicker({
                format: DATE_FORMAT,
                changeMonth: true,
                changeYear: true,
                autoclose: true,
                keyboardNavigation : true,
                weekStart: 1,
                startDate: startDate,
                endDate: ToEndDate,
                autoclose: true
            })
            .on('changeDate', function (selected) {
                FromEndDate = new Date(selected.date.valueOf());
                FromEndDate.setDate(FromEndDate.getDate(new Date(selected.date.valueOf())));
                $('.datepicker-from').datepicker('setEndDate', FromEndDate);
            });
    if (idExpiredDate != null && idExpiredDate != "undefined" && idExpiredDate != ""){
        $('.datepicker-to').datepicker('setDate', idExpiredDate);
    }
}

function changeDatetimepicker(idFrom,idTo){
	$('#' + idFrom).datetimepicker({
		format: 'DD/MM/YYYY HH:mm:ss',
		useCurrent: false
	});
	$('#' + idTo).datetimepicker({
		format: 'DD/MM/YYYY HH:mm:ss',
		useCurrent: false
		
		
	})
	var tmpFrom =  moment($('#' + idFrom).val(), 'DD-MM-YYYY HH:mm:ss');
	var tmpTo = moment($('#' + idTo).val(), 'DD-MM-YYYY HH:mm:ss');
	$('#' + idFrom).on('dp.change', function(e) {
		// var tmp = e.date == undefined ? tmpFrom : e.date;
		var tmp = e.date == undefined ? tmpFrom : e.date;
		if(tmp){
			var date = new Date(tmp);
			// var dateFrom = new Date(date);
			
			/**
			 * Lấy cuối ngày hôm trước vì hàm minDate không lấy giá trị bằng.
			 * Nếu lấy ngày hiện tại set giờ 00:00:00:00 sẽ bị sai
			 **/
			 date = moment(date).subtract(1, "days").toDate();
			 var dateFrom = new Date(date.setHours(23,59,0,0));
			$('#' + idTo).data("DateTimePicker").minDate(dateFrom);
		} else {
			$('#' + idTo).data("DateTimePicker").minDate(false);
		}
	})

	$('#' + idTo).on('dp.change', function(e) {
		var tmp = e.date == undefined ? tmpTo : e.date;
		if(tmp){
			var date = new Date(tmp);
			// $('#' + idFrom).data("DateTimePicker").maxDate(new Date(date.setHours(0,0,0,0)));
			$('#' + idFrom).data("DateTimePicker").maxDate(date);
		} else {
			$('#' + idFrom).data("DateTimePicker").maxDate(false);
		}
		
		let dateFromMo = moment($('#' + idFrom).val(), 'DD/MM/YYYY').toDate();
		let datetoMo = moment($('#' + idTo).val(), 'DD/MM/YYYY').toDate();
		if (!moment(dateFromMo).isSame(datetoMo)){
			$('#' + idTo).data("DateTimePicker").date($('#' + idTo).val());
		}else{
			let dateFromMoFul = moment(tmp, 'DD/MM/YYYY HH:mm:ss').toDate();
			$('#' + idTo).data("DateTimePicker").date(dateFromMoFul);
		}
	});

	if(!isBlank($('#' + idFrom).val())) $('#' + idFrom).trigger('dp.change');
	if(!isBlank($('#' + idTo).val())) $('#' + idTo).trigger('dp.change');
}
function nameToLinkAlias(strName){
    strName = strName.substring(0, 100);
    linkAlias = removeDiacritics(strName.toLowerCase()).replace(/[^a-zA-Z\d\s-]+/gi,'').replace(/[^a-zA-Z\d\s]+/gi,' ');
    var aliasCompArray = linkAlias.split(/ /g);
    var comps = [];
    for(var compIndex in aliasCompArray){
        var compString = aliasCompArray[compIndex];
        if(!(compString === "")){
            comps.push(compString);
        }
    }
    var linkAlias = comps.join("-");
    return linkAlias;
}

function removeDiacritics (str) {
   var changes = defaultDiacriticsRemovalMap;
   for(var i=0; i<changes.length; i++) {
       str = str.replace(changes[i].letters, changes[i].base);
   }
   return str;
}

function nameToTag(strName){
    strName = strName.substring(0, 1000);
    var keyword = removeDiacritics(strName.toLowerCase()).replace(/[^a-zA-Z\d\s-]+/gi,'').replace(/[^a-zA-Z\d\s]+/gi,' ');
    keyword = keyword.replace(/ /g,"");
    return (keyword == null || keyword == "") ? "" : "#" + keyword;
}

var defaultDiacriticsRemovalMap = [
                                   {'base':'A', 'letters':/[\u0041\u24B6\uFF21\u00C0\u00C1\u00C2\u1EA6\u1EA4\u1EAA\u1EA8\u00C3\u0100\u0102\u1EB0\u1EAE\u1EB4\u1EB2\u0226\u01E0\u00C4\u01DE\u1EA2\u00C5\u01FA\u01CD\u0200\u0202\u1EA0\u1EAC\u1EB6\u1E00\u0104\u023A\u2C6F]/g},
                                   {'base':'AA','letters':/[\uA732]/g},
                                   {'base':'AE','letters':/[\u00C6\u01FC\u01E2]/g},
                                   {'base':'AO','letters':/[\uA734]/g},
                                   {'base':'AU','letters':/[\uA736]/g},
                                   {'base':'AV','letters':/[\uA738\uA73A]/g},
                                   {'base':'AY','letters':/[\uA73C]/g},
                                   {'base':'B', 'letters':/[\u0042\u24B7\uFF22\u1E02\u1E04\u1E06\u0243\u0182\u0181]/g},
                                   {'base':'C', 'letters':/[\u0043\u24B8\uFF23\u0106\u0108\u010A\u010C\u00C7\u1E08\u0187\u023B\uA73E]/g},
                                   {'base':'D', 'letters':/[\u0044\u24B9\uFF24\u1E0A\u010E\u1E0C\u1E10\u1E12\u1E0E\u0110\u018B\u018A\u0189\uA779]/g},
                                   {'base':'DZ','letters':/[\u01F1\u01C4]/g},
                                   {'base':'Dz','letters':/[\u01F2\u01C5]/g},
                                   {'base':'E', 'letters':/[\u0045\u24BA\uFF25\u00C8\u00C9\u00CA\u1EC0\u1EBE\u1EC4\u1EC2\u1EBC\u0112\u1E14\u1E16\u0114\u0116\u00CB\u1EBA\u011A\u0204\u0206\u1EB8\u1EC6\u0228\u1E1C\u0118\u1E18\u1E1A\u0190\u018E]/g},
                                   {'base':'F', 'letters':/[\u0046\u24BB\uFF26\u1E1E\u0191\uA77B]/g},
                                   {'base':'G', 'letters':/[\u0047\u24BC\uFF27\u01F4\u011C\u1E20\u011E\u0120\u01E6\u0122\u01E4\u0193\uA7A0\uA77D\uA77E]/g},
                                   {'base':'H', 'letters':/[\u0048\u24BD\uFF28\u0124\u1E22\u1E26\u021E\u1E24\u1E28\u1E2A\u0126\u2C67\u2C75\uA78D]/g},
                                   {'base':'I', 'letters':/[\u0049\u24BE\uFF29\u00CC\u00CD\u00CE\u0128\u012A\u012C\u0130\u00CF\u1E2E\u1EC8\u01CF\u0208\u020A\u1ECA\u012E\u1E2C\u0197]/g},
                                   {'base':'J', 'letters':/[\u004A\u24BF\uFF2A\u0134\u0248]/g},
                                   {'base':'K', 'letters':/[\u004B\u24C0\uFF2B\u1E30\u01E8\u1E32\u0136\u1E34\u0198\u2C69\uA740\uA742\uA744\uA7A2]/g},
                                   {'base':'L', 'letters':/[\u004C\u24C1\uFF2C\u013F\u0139\u013D\u1E36\u1E38\u013B\u1E3C\u1E3A\u0141\u023D\u2C62\u2C60\uA748\uA746\uA780]/g},
                                   {'base':'LJ','letters':/[\u01C7]/g},
                                   {'base':'Lj','letters':/[\u01C8]/g},
                                   {'base':'M', 'letters':/[\u004D\u24C2\uFF2D\u1E3E\u1E40\u1E42\u2C6E\u019C]/g},
                                   {'base':'N', 'letters':/[\u004E\u24C3\uFF2E\u01F8\u0143\u00D1\u1E44\u0147\u1E46\u0145\u1E4A\u1E48\u0220\u019D\uA790\uA7A4]/g},
                                   {'base':'NJ','letters':/[\u01CA]/g},
                                   {'base':'Nj','letters':/[\u01CB]/g},
                                   {'base':'O', 'letters':/[\u004F\u24C4\uFF2F\u00D2\u00D3\u00D4\u1ED2\u1ED0\u1ED6\u1ED4\u00D5\u1E4C\u022C\u1E4E\u014C\u1E50\u1E52\u014E\u022E\u0230\u00D6\u022A\u1ECE\u0150\u01D1\u020C\u020E\u01A0\u1EDC\u1EDA\u1EE0\u1EDE\u1EE2\u1ECC\u1ED8\u01EA\u01EC\u00D8\u01FE\u0186\u019F\uA74A\uA74C]/g},
                                   {'base':'OI','letters':/[\u01A2]/g},
                                   {'base':'OO','letters':/[\uA74E]/g},
                                   {'base':'OU','letters':/[\u0222]/g},
                                   {'base':'P', 'letters':/[\u0050\u24C5\uFF30\u1E54\u1E56\u01A4\u2C63\uA750\uA752\uA754]/g},
                                   {'base':'Q', 'letters':/[\u0051\u24C6\uFF31\uA756\uA758\u024A]/g},
                                   {'base':'R', 'letters':/[\u0052\u24C7\uFF32\u0154\u1E58\u0158\u0210\u0212\u1E5A\u1E5C\u0156\u1E5E\u024C\u2C64\uA75A\uA7A6\uA782]/g},
                                   {'base':'S', 'letters':/[\u0053\u24C8\uFF33\u1E9E\u015A\u1E64\u015C\u1E60\u0160\u1E66\u1E62\u1E68\u0218\u015E\u2C7E\uA7A8\uA784]/g},
                                   {'base':'T', 'letters':/[\u0054\u24C9\uFF34\u1E6A\u0164\u1E6C\u021A\u0162\u1E70\u1E6E\u0166\u01AC\u01AE\u023E\uA786]/g},
                                   {'base':'TZ','letters':/[\uA728]/g},
                                   {'base':'U', 'letters':/[\u0055\u24CA\uFF35\u00D9\u00DA\u00DB\u0168\u1E78\u016A\u1E7A\u016C\u00DC\u01DB\u01D7\u01D5\u01D9\u1EE6\u016E\u0170\u01D3\u0214\u0216\u01AF\u1EEA\u1EE8\u1EEE\u1EEC\u1EF0\u1EE4\u1E72\u0172\u1E76\u1E74\u0244]/g},
                                   {'base':'V', 'letters':/[\u0056\u24CB\uFF36\u1E7C\u1E7E\u01B2\uA75E\u0245]/g},
                                   {'base':'VY','letters':/[\uA760]/g},
                                   {'base':'W', 'letters':/[\u0057\u24CC\uFF37\u1E80\u1E82\u0174\u1E86\u1E84\u1E88\u2C72]/g},
                                   {'base':'X', 'letters':/[\u0058\u24CD\uFF38\u1E8A\u1E8C]/g},
                                   {'base':'Y', 'letters':/[\u0059\u24CE\uFF39\u1EF2\u00DD\u0176\u1EF8\u0232\u1E8E\u0178\u1EF6\u1EF4\u01B3\u024E\u1EFE]/g},
                                   {'base':'Z', 'letters':/[\u005A\u24CF\uFF3A\u0179\u1E90\u017B\u017D\u1E92\u1E94\u01B5\u0224\u2C7F\u2C6B\uA762]/g},
                                   {'base':'a', 'letters':/[\u0061\u24D0\uFF41\u1E9A\u00E0\u00E1\u00E2\u1EA7\u1EA5\u1EAB\u1EA9\u00E3\u0101\u0103\u1EB1\u1EAF\u1EB5\u1EB3\u0227\u01E1\u00E4\u01DF\u1EA3\u00E5\u01FB\u01CE\u0201\u0203\u1EA1\u1EAD\u1EB7\u1E01\u0105\u2C65\u0250]/g},
                                   {'base':'aa','letters':/[\uA733]/g},
                                   {'base':'ae','letters':/[\u00E6\u01FD\u01E3]/g},
                                   {'base':'ao','letters':/[\uA735]/g},
                                   {'base':'au','letters':/[\uA737]/g},
                                   {'base':'av','letters':/[\uA739\uA73B]/g},
                                   {'base':'ay','letters':/[\uA73D]/g},
                                   {'base':'b', 'letters':/[\u0062\u24D1\uFF42\u1E03\u1E05\u1E07\u0180\u0183\u0253]/g},
                                   {'base':'c', 'letters':/[\u0063\u24D2\uFF43\u0107\u0109\u010B\u010D\u00E7\u1E09\u0188\u023C\uA73F\u2184]/g},
                                   {'base':'d', 'letters':/[\u0064\u24D3\uFF44\u1E0B\u010F\u1E0D\u1E11\u1E13\u1E0F\u0111\u018C\u0256\u0257\uA77A]/g},
                                   {'base':'dz','letters':/[\u01F3\u01C6]/g},
                                   {'base':'e', 'letters':/[\u0065\u24D4\uFF45\u00E8\u00E9\u00EA\u1EC1\u1EBF\u1EC5\u1EC3\u1EBD\u0113\u1E15\u1E17\u0115\u0117\u00EB\u1EBB\u011B\u0205\u0207\u1EB9\u1EC7\u0229\u1E1D\u0119\u1E19\u1E1B\u0247\u025B\u01DD]/g},
                                   {'base':'f', 'letters':/[\u0066\u24D5\uFF46\u1E1F\u0192\uA77C]/g},
                                   {'base':'g', 'letters':/[\u0067\u24D6\uFF47\u01F5\u011D\u1E21\u011F\u0121\u01E7\u0123\u01E5\u0260\uA7A1\u1D79\uA77F]/g},
                                   {'base':'h', 'letters':/[\u0068\u24D7\uFF48\u0125\u1E23\u1E27\u021F\u1E25\u1E29\u1E2B\u1E96\u0127\u2C68\u2C76\u0265]/g},
                                   {'base':'hv','letters':/[\u0195]/g},
                                   {'base':'i', 'letters':/[\u0069\u24D8\uFF49\u00EC\u00ED\u00EE\u0129\u012B\u012D\u00EF\u1E2F\u1EC9\u01D0\u0209\u020B\u1ECB\u012F\u1E2D\u0268\u0131]/g},
                                   {'base':'j', 'letters':/[\u006A\u24D9\uFF4A\u0135\u01F0\u0249]/g},
                                   {'base':'k', 'letters':/[\u006B\u24DA\uFF4B\u1E31\u01E9\u1E33\u0137\u1E35\u0199\u2C6A\uA741\uA743\uA745\uA7A3]/g},
                                   {'base':'l', 'letters':/[\u006C\u24DB\uFF4C\u0140\u013A\u013E\u1E37\u1E39\u013C\u1E3D\u1E3B\u017F\u0142\u019A\u026B\u2C61\uA749\uA781\uA747]/g},
                                   {'base':'lj','letters':/[\u01C9]/g},
                                   {'base':'m', 'letters':/[\u006D\u24DC\uFF4D\u1E3F\u1E41\u1E43\u0271\u026F]/g},
                                   {'base':'n', 'letters':/[\u006E\u24DD\uFF4E\u01F9\u0144\u00F1\u1E45\u0148\u1E47\u0146\u1E4B\u1E49\u019E\u0272\u0149\uA791\uA7A5]/g},
                                   {'base':'nj','letters':/[\u01CC]/g},
                                   {'base':'o', 'letters':/[\u006F\u24DE\uFF4F\u00F2\u00F3\u00F4\u1ED3\u1ED1\u1ED7\u1ED5\u00F5\u1E4D\u022D\u1E4F\u014D\u1E51\u1E53\u014F\u022F\u0231\u00F6\u022B\u1ECF\u0151\u01D2\u020D\u020F\u01A1\u1EDD\u1EDB\u1EE1\u1EDF\u1EE3\u1ECD\u1ED9\u01EB\u01ED\u00F8\u01FF\u0254\uA74B\uA74D\u0275]/g},
                                   {'base':'oi','letters':/[\u01A3]/g},
                                   {'base':'ou','letters':/[\u0223]/g},
                                   {'base':'oo','letters':/[\uA74F]/g},
                                   {'base':'p','letters':/[\u0070\u24DF\uFF50\u1E55\u1E57\u01A5\u1D7D\uA751\uA753\uA755]/g},
                                   {'base':'q','letters':/[\u0071\u24E0\uFF51\u024B\uA757\uA759]/g},
                                   {'base':'r','letters':/[\u0072\u24E1\uFF52\u0155\u1E59\u0159\u0211\u0213\u1E5B\u1E5D\u0157\u1E5F\u024D\u027D\uA75B\uA7A7\uA783]/g},
                                   {'base':'s','letters':/[\u0073\u24E2\uFF53\u00DF\u015B\u1E65\u015D\u1E61\u0161\u1E67\u1E63\u1E69\u0219\u015F\u023F\uA7A9\uA785\u1E9B]/g},
                                   {'base':'t','letters':/[\u0074\u24E3\uFF54\u1E6B\u1E97\u0165\u1E6D\u021B\u0163\u1E71\u1E6F\u0167\u01AD\u0288\u2C66\uA787]/g},
                                   {'base':'tz','letters':/[\uA729]/g},
                                   {'base':'u','letters':/[\u0075\u24E4\uFF55\u00F9\u00FA\u00FB\u0169\u1E79\u016B\u1E7B\u016D\u00FC\u01DC\u01D8\u01D6\u01DA\u1EE7\u016F\u0171\u01D4\u0215\u0217\u01B0\u1EEB\u1EE9\u1EEF\u1EED\u1EF1\u1EE5\u1E73\u0173\u1E77\u1E75\u0289]/g},
                                   {'base':'v','letters':/[\u0076\u24E5\uFF56\u1E7D\u1E7F\u028B\uA75F\u028C]/g},
                                   {'base':'vy','letters':/[\uA761]/g},
                                   {'base':'w','letters':/[\u0077\u24E6\uFF57\u1E81\u1E83\u0175\u1E87\u1E85\u1E98\u1E89\u2C73]/g},
                                   {'base':'x','letters':/[\u0078\u24E7\uFF58\u1E8B\u1E8D]/g},
                                   {'base':'y','letters':/[\u0079\u24E8\uFF59\u1EF3\u00FD\u0177\u1EF9\u0233\u1E8F\u00FF\u1EF7\u1E99\u1EF5\u01B4\u024F\u1EFF]/g},
                                   {'base':'z','letters':/[\u007A\u24E9\uFF5A\u017A\u1E91\u017C\u017E\u1E93\u1E95\u01B6\u0225\u0240\u2C6C\uA763]/g}
                               ];


function disabledInput($this){
    var $element = $($this);
    var id = $element.attr('id').replace('Hidd', '');
	var isChecked = $element.is(':checked');
	
	if (isChecked) {
		$('#' + id).removeAttr('disabled');
		$('#' + id + 'Condition').removeAttr('disabled');
	} else {
		$('#' + id).attr('disabled', 'disabled');
		$('#' + id + 'Condition').attr('disabled', 'disabled');
	}
}

function closeInput($this) {
    var $element = $($this);
    var id = $element.attr('id').replace('Hidd', 'CheckBox');
    if ($('#' + id).is(':checked') == false) {
        $('#' + id).trigger('click');
    }

	var nameClass = $element.attr('id').replace('Hidd', 'Search');
	// $('.' + nameClass).hide();

	$('#' + nameClass).prop('checked', false).trigger('change');
}

function checkConditionSearch($this) {
	var $element = $($this);
	var id = $element.attr('id').replace('Condition', '');
	var value = $element.val();
	if (isNull(value)) {
		$('#' + id).val('').change();
		$('#' + id).parent().hide();
	} else {
		if (value.indexOf('BETWEEN') > -1) {
			if ($('#' + id).parent().hasClass('datepicker')) {
				$('#' + id + 'To').removeAttr('disabled');
				$('#' + id + 'To').parent().show();
			}
		} else {
			if ($('#' + id).parent().hasClass('datepicker')) {
				$('#' + id + 'To').attr('disabled', 'disabled');
				$('#' + id + 'To').parent().hide();
			} else {
				$('#' + id).show();
				$('#' + id).parent().show();

				let val = $('#' + id).find('option:first').val();
				if (!isNull(val)) {
					$('#' + id).val(val).change();
				}
			}
		}
	}
}

function disabledInputSearch($this) {
    var $element = $($this);
    var id = $element.attr('id').replace('CheckBox', '');
    if ($element.is(':checked')) {
        $('#' + id).attr('disabled', false);
    } else {
		$('#' + id).attr('disabled', true);
	}
}

function cbShowHideInput($this) {
	var $element = $($this);
	var id = $element.attr('id');
	var idAttr = $element.attr('id').replace('Search', '');
	var idCheckBox = $element.attr('id').replace('Search', 'Hidd');

	if ($element.is(':checked')) {
		$('.' + id).show();
		$('#' + idAttr).attr('disabled', false);

		let isChecked = $('#' + idCheckBox).is(':checked');
		if (!isChecked) {
			$('#' + idCheckBox).prop('checked', true);
			disabledInput($('#' + idCheckBox));
		}
    } else {
        $('.' + id).hide();
        $('#' + idAttr).attr('disabled', true);
    }

	var idCondition = idAttr + ('Condition');
	checkConditionSearch($('#' + idCondition));

	let isChecked = $('#ulParentSearch').find('input[type=checkbox]').is(":checked");
	if (isChecked) {
		$('#divButtonSearch').show();
	} else {
		$('#divButtonSearch').hide();
	}
}

// show/hide column in table
function showAndHideColum($this){
	var $element = $($this);
	var showName = $element.data('name');
	if ($element.is(':checked')) {
		$('.' + showName).removeClass('hidden');
	}else{
		$('.' + showName).addClass('hidden');
	}
} 

function sortColumn($this, data, url, cName) {
	var $element = $($this);
	var column = $element.data('column');
	var sortType = "ASC";
	
	let sessionUrl = getCookie(cName);

	if (sessionUrl != undefined){
		let type = sessionUrl.split(",")[1];
		if (type == "ASC" || type == "asc"){
			sortType = "DESC";
		}
		
		if (sessionUrl.indexOf('ajaxList') > -1){
			url = sessionUrl;
		}
	}
	let urlSearch = constructSortAjax(url, column, [column], sortType, cName);
	$('#urlSearch').val(urlSearch);
	ajaxSearch(urlSearch, data, 'tableList', $element, null);
}

/**
 * doExportExcel
 */
function doExportFileExcel(url, tokenName, pramObject) {
	let token = $("meta[name='_csrf']").attr("content"); 
	let header = $("meta[name='_csrf_header']").attr("content");

  var timerToken;
  var tokenid = getToken();
  timerToken = setInterval(function () {
    if ($.cookie(tokenid) === "OK") {
      unblockbg();
      clearInterval(timerToken);
    }
  }, 100);

  var $formExport = jQuery("<form>", {
    action: url,
    method: "POST",
  });

  // add fields
  $.each(pramObject, function (key, val) {
    $formExport.append(
      jQuery("<input>", {
        name: key,
        value: val,
        type: "hidden",
      })
    );
  });

  if (tokenName == undefined || tokenName == null || tokenName == "") {
    tokenName = "tokenId";
  }

  $formExport.append(
    jQuery("<input>", {
      name: tokenName,
      value: tokenid,
      type: "hidden",
    })
  );
  
	$formExport.append(
		jQuery("<input>", {
		name: "_csrf",
		value: token,
		type: "hidden",
		})
	);

  $formExport.appendTo(document.body);
  $formExport.submit().remove();

  blockbg();
}

function getEmbedFromYoutubeLink(link){
	if (link.indexOf("embed") > -1) {
		return link;
	} else {
		let slipLink = link.split("&")[0];
		let prefix = "https://www.youtube.com/watch?v=";
		let videoName = slipLink.replace(prefix, '');
		let urlYoutube = "https://www.youtube.com/embed/" + videoName;

		return urlYoutube;
	}
}

function addYoutubeVideoToHtml(idDiv, idIframe, link, width, height) {
	if (width === undefined) {
		width = 600;
	}

	if (height === undefined) {
		height = 493;
	}

	link = getEmbedFromYoutubeLink(link);
	let html = '<iframe th:id="' + idIframe + '" width="' + width + '" height=" ' + height + '" src="' + link + '"></iframe>';
	$('#' + idDiv).html(html);
}

function updateElementEditor() {
	for (instance in CKEDITOR.instances) {
		CKEDITOR.instances[instance].updateElement();
	}
}

function disabledAllField(formId, isDisabled){
	if (isDisabled){
		$('#' + formId + ' input').attr('disabled', 'disabled');
		$('#' + formId + ' select').attr('disabled', 'disabled');
		$('#' + formId + ' a').addClass('not-active');
		//$('#' + formId + ' textarea').addClass('not-active');
		
		$('#' + formId + ' .textbox-addon-right').remove();
		$('#' + formId + ' .controls').addClass('not-active');
		
		$('#' + formId + '.u-btn-back').removeClass('not-active');
		$('#' + formId + '.u-btn-primary').removeClass('not-active');
	}
}

function viewBtnSave(tableId) {
	if ($('#' + tableId + ' tbody tr').length > 0) {
		$('#btnSave').removeAttr("disabled");
	} else {
		$('#btnSave').attr("disabled", "disabled");
	}
}

function signatureImage(key, url) {
	var image = $("#physicalImg" + key).val();
	if (image != "") {
		$("#img_banner" + key).attr("src", BASE_URL + url + "/download?fileName=" + image);
		$("#img_banner" + key).removeClass('hide');
		$("#img_banner" + key).addClass('wrap_img');
	}
}

function signatureImageUrl(key, url) {
	var imageUrl = $("#physicalImgUrl" + key).val();
	if (imageUrl != "") {
		$("#img_bannerUrl" + key).attr("src", BASE_URL + url + "/download?fileName=" + imageUrl);
		$("#img_bannerUrl" + key).removeClass('hide');
		$("#img_bannerUrl" + key).addClass('wrap_img');
	}
}

function initImageUploader(key, url, resize) {
	var requestToken =  $("#requestToken").val();
	var uploadUrl = BASE_URL + url + "/uploadTemp?requestToken=" + requestToken;
	var imagePickfiles = 'imgPickfiles' + key;
	var imageContainer = 'imageContainer' + key;
	var imageMaxFileSize = '7mb';
	var imageMimeTypes = [ {
		title : "Image files",
		extensions : "jpg,bmp,png,jpeg,jfif"
	} ];

	/*var resize = {
		width : 2580,
		height : 680
	};*/
	
	if (resize == undefined || resize == null){
		resize = {};
	}
	
	var Browse = function(up) {
		//MSG_ERROR_MIN_SIZE_COMMON = MSG_ERROR_MIN_NEWS_SIZE_DESKTOP;
	};
	
	var imageFileList = 'imageFilelist' + key;
	var imageConsole = 'imageConsole' + key;
	var imageFileUploaded = function(up, file, info) {
		$("#bannerImg" + key).val(cutString(file.name));
		$("#physicalImg" + key).val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#physicalImg" + key).val();
		var lstLinkbannerDesktop = $("[name$='.bannerDesktopPhysicalImg']");
		if(key == 0){
			for(var i = 0; i < lstLinkbannerDesktop.length; i ++){
				$("#img_banner" + i).attr("src", BASE_URL + url + "/download?fileName=" + lstImg);
				$("#img_banner" + i).removeClass('hide');
				$("#img_banner" + i).addClass('wrap_img');
				$("#physicalImg" + i).val(lstImg);
			}
		} else {
			$("#img_banner" + key).attr("src", BASE_URL + url + "/download?fileName=" + lstImg);
			$("#img_banner" + key).removeClass('hide');
			$("#img_banner" + key).addClass('wrap_img');
		}
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};
	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
			imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL, null, resize, null, Browse);
}

function initImageUploaderUrl(key, url, resize) {
	var requestToken =  $("#requestToken").val();
	var uploadUrl = BASE_URL + url + "/uploadTemp?requestToken=" + requestToken;
	
	var imagePickfiles = 'imgPickfilesUrl' + key;
	var imageContainer = 'imageContainerUrl' + key;
	var imageMaxFileSize = '7mb';
	var imageMimeTypes = [ {
		title : "Image files",
		extensions : "jpg,bmp,png,jpeg,jfif"
	} ];
	
	if (resize == undefined || resize == null){
		resize = {};
	}
		
	var Browse = function(up) {
		// MSG_ERROR_MIN_SIZE_COMMON = MSG_ERROR_MIN_NEWS_SIZE;
	};
	
	var imageFileList = 'imageFilelistUrl' + key;
	var imageConsole = 'imageConsoleUrl' + key;
	var imageFileUploaded = function(up, file, info) {
		$("#imgUrl" + key).val(cutString(file.name));
		$("#physicalImgUrl" + key).val(cutString(info.response));
	};

	var imageUploadComplete = function(up, files) {
		var lstImg = $("#physicalImgUrl" + key).val();
		var lstLinkImgUrl = $("[name$='.physicalImgUrl']");
		if(key == 0){
			for(var i = 0; i < lstLinkImgUrl.length; i ++){
				$("#img_bannerUrl" + i).attr("src", BASE_URL + url + "/download?fileName=" + lstImg);
				$("#img_bannerUrl" + i).removeClass('hide');
				$("#img_bannerUrl" + i).addClass('wrap_img');
				$("#physicalImgUrl" + i).val(lstImg);
			}
		} else {
			$("#img_bannerUrl" + key).attr("src", BASE_URL + url + "/download?fileName=" + lstImg);
			$("#img_bannerUrl" + key).removeClass('hide');
			$("#img_bannerUrl" + key).addClass('wrap_img');
		}
		$("#" + imageConsole).hide();
		$("#" + imageFileList).hide();
	};
	InitPlupload(imagePickfiles, imageContainer, uploadUrl, false, imageMaxFileSize, imageMimeTypes, imageFileList,
			imageConsole, imageFileUploaded, imageUploadComplete, BASE_URL, null, resize, null, Browse);
}

function deleteImage(key) {
	$("#bannerImg" + key).val("");
	$("#physicalImg" + key).val("");
	$("#img_banner" + key).attr("src", "");
	$("#img_banner" + key).addClass('hide');
}

function deleteImageUrl(key) {
	$("#imgUrl" + key).val("");
	$("#physicalImgUrl" + key).val("");
	$("#img_bannerUrl" + key).attr("src", "");
	$("#img_bannerUrl" + key).addClass('hide');
}

function isValidDate(s) {
    // Assumes s is "dd/mm/yyyy"
    if ( ! /^\d\d\/\d\d\/\d\d\d\d$/.test(s) ) {
        return false;
    }
    const parts = s.split('/').map((p) => parseInt(p, 10));
    parts[1] -= 1;
    const d = new Date(parts[2], parts[1], parts[0]);
    return d.getMonth() === parts[1] && d.getDate() === parts[0] && d.getFullYear() === parts[2];
}

function isValidDateFull(s) {
    // Assumes s is "dd/mm/yyyy hh:mm:ss"
    if ( ! /^([1-9]|([012][0-9])|(3[01]))-([0]{0,1}[1-9]|1[012])-\d\d\d\d (20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d$/.test(s) ) {
        return false;
    }
    const parts = s.split('/').map((p) => parseInt(p, 10));
    parts[1] -= 1;
    const d = new Date(parts[2], parts[1], parts[0]);
    return d.getMonth() === parts[1] && d.getDate() === parts[0] && d.getFullYear() === parts[2];
}

function rollToError(formClassName){
	$('html, body').animate({
		scrollTop: ($("." + formClassName).find(":input.error:first").parent().offset().top - 100)
	}, 1000);
	
	$("." + formClassName).find(":input.error:first").focus();
}

function clearErrorWhenMove(textClass, selectClass) {
	$('.' + textClass).blur(function (e) {
		let id = $(this).attr('id');
		$(this).removeClass('error');
		findAndRemoveError(id);
	});

	$('.' + selectClass).on("select2:close", function (e) {
		let id = $(this).attr('id');
		$(this).removeClass('error');
		findAndRemoveError(id);
	});
}

function findAndRemoveError(id){
	$('label[id$=-error]').each(function(key, val){
		let idLabelError = $(val).attr('id');
		if (id + '-error' == idLabelError){
			$(val).remove();
		}
	})
	
	$('label[generated="true"]').each(function(key, val){
		let idLabelError = $(val).attr('for');
		if (id == idLabelError){
			$(val).remove();
		}
		if (id.indexOf('_easyui_textbox') > -1){
			let $div = $('#' + id).parent().parent();
			let input = $div.find('input[name=' + idLabelError + ']');
			if (input.length > 0){
				$(val).remove();
			}
		}
	})
}

/**
 * @author: TaiTM
 * @description			: Bắt sự kiện khi rời khỏi 1 element thì gọi phương thức validate
 */
function requiredValidateWhenMove(textClass, selectClass) {
	$('.' + textClass).blur(function (e) {
		$(this).valid();
	});

	$('.' + selectClass).on("select2:close", function (e) {
		$(this).valid();
	});
}

/**
 * @author: TaiTM
 * @description				: Tạo ra 1 phương thức validate mới
 * @param:
 * 	functionValidateName	: tên của phương thức
 * 	functionValidate		: phương thức xử lý. 
 * 							  Trong trường hợp không viết functionValidate thì mặc định trả về false
 * 	messageValidate			: Thông báo lỗi trả về của phương thức
 */
function createFunctionValidate(functionValidateName, functionValidate, messageValidate) {
	if (functionValidate == undefined || functionValidate == null) {
		jQuery.validator.addMethod(functionValidateName, function (value, element) {
			return false;
		}, messageValidate);
	} else {
		jQuery.validator.addMethod(functionValidateName, functionValidate, messageValidate);
	}
}

/**
 * @author: TaiTM
 * @description: Tạo ra 1 phương thức validate mới cho 1 element
 * @param:
 * 	elementId				: id của element (không tính dấu #)
 * 	functionValidateName	: tên của phương thức
 * 	messageValidate			: Thông báo lỗi trả về của phương thức
 * 	functionValidate		: phương thức xử lý
 */
function addRuleValidate(elementId, functionValidateName, messageValidate, functionValidate) {
	$('#' + elementId).removeClass(functionValidateName);

	createFunctionValidate(functionValidateName, functionValidate, messageValidate);

	$('#' + elementId).addClass(functionValidateName);
	if ($('#' + elementId).valid()) {
		$('#' + elementId).removeClass(functionValidateName);
	}
}

function trimData(params) {
	try {
		if (params != undefined && params != null && params != '') {
			$.each(params, function (key, value) {
				if (value != undefined && value != null && value != '') {
					if (!(value instanceof Array)) {
						value = value.trim();
					}
				}

				params[key] = value;
			});
		}
	} catch (err) {
		console.log(err);
	}

	return params;
}

/**
 * @author TaiTM
 * @param idSelect, value
 *   setValueForSelect2('agentCode', 123456)
 * @desc : gán giá trị cho select2
 * @date : 26/08/2020
 */
function setValueForSelect2(idSelect, value) {
	let dom = $('#' + idSelect);
	var result = value;
	if (value == undefined) {
		result = '';
		value = '';
	}
	var option = new Option(result, value, true, true);
	dom.append(option);
}

function doDelete(url, event, callback) {
	event.preventDefault();

	$.ajax({
		type: "POST",
		url: BASE_URL + url,
		success: function (data, textStatus, request) {
			callback();
		},
		error: function (xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		},
		complete: function (result) {
			goTopPage();
		}
	});
}

function converToDate(dateString) {
	if (dateString == null || dateString == ''){
		return;
	}
	let parts = dateString.split(' ');
	
	let part = parts[0].split('/');
	let dateStringTemp = part[2] + '-' + part[1] + '-' + part[0];
	if (!isNull(parts[1])){
		dateStringTemp = dateStringTemp + ' ' + parts[1];
	}
	return new Date(dateStringTemp);
}

function converDateToString(date) {
	var mm = date.getMonth() + 1; // getMonth() is zero-based
	var dd = date.getDate();
	// dd/mm/yyyy
	return [(dd > 9 ? '' : '0') + dd,
	(mm > 9 ? '' : '0') + mm,
	date.getFullYear()
	].join('/');
}

function decimalFormat(numValue) {
	return jQuery.formatNumber(numValue, {
		format: FORMAT_NUMBER,
		locale: 'en'
	});
}

function compareDate(stringDate1, stringDate2){
	let date1 = converToDate(stringDate1).getTime();
	let date2 = converToDate(stringDate2).getTime();
	if (date1 == date2) {
		return 0;
	}
	if (date1 > date2) {
		return 1;
	}
	if (date1 < date2) {
		return -1;
	}
}
function isNumberKey(evt){
	var charCode = (evt.which) ? evt.which : evt.keyCode;
	if (charCode > 31 && (charCode < 48 || charCode > 57)) return false;
	return true;
}