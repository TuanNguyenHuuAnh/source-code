/**phatvt:handle ajax global*/
$(document).bind("ajaxSend", function(){
    blockbg();
 }).bind("ajaxComplete", function(){
	unblockbg();
 }).bind("ajaxSuccess", function(){
	 unblockbg();
 }).bind("ajaxError", function(xhr, textStatus, error){
	 unblockbg();
//	 console.log(xhr);
//	 console.log(textStatus);
//	 console.log(error);
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

/**
 * Open popup confirm
 * 
 * @param msgConfirm
 * @param methodCallback
 * @returns
 */
function popupConfirm( msgConfirm, methodCallback) {
	bootbox.setLocale( APP_LOCALE );
	return bootbox.confirm( msgConfirm, methodCallback ); 
}

/**
 * Open popup confirm
 * 
 * @param msgConfirm
 * @param methodCallback
 * @returns
 */
function popupConfirmWithButtons( msgConfirm, buttons, methodCallback) {
	bootbox.setLocale( APP_LOCALE );
	
	return bootbox.confirm({
		message: msgConfirm,
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
	bootbox.setLocale( APP_LOCALE );
	return bootbox.alert( msgAlert ); 
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

function parseNumberEn(valStr) {
	if ($.trim(valStr).length > 0) {
		valStr = $.parseNumber(valStr, {
			format : FORMAT_NUMBER,
			locale : 'en'
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
	event.preventDefault();

	var me = $(element);
	if (me.data('requestRunning')) {
		return;
	}
	me.data('requestRunning', true);
	
	condition = trimData(condition);

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

/**
 * ajax redirect method get
 * @param url
 */
function ajaxRedirect(url) {
	$.ajax({
		type : "GET",
		url : url,
		beforeSend: blockbg(),
		success : function(data, textStatus, request) {
			
			var msgFlag = request.getResponseHeader('msgFlag');
			if( "1" == msgFlag ) {
				$(".message-info").html(data);
				var html1 = $('.main_content').find('.message-info').html()
				$('.main_content .content-bottom').html(html1);
			} else {
				var content = $(data).find('.body-content');
				$(".main_content").html(content);
			}
			window.history.pushState('', '', url);
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
function ajaxSubmit(url, condition, event) {
	event.preventDefault();

	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : condition,
		success : function(data, textStatus, request) {
			var msgFlag = request.getResponseHeader('msgFlag');
			if( "1" == msgFlag ) {
				$(".message-info").html(data);
			} else {
				var content = $(data).find('.body-content');
				$(".main_content").html(content);
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

function ajaxSubmitCallBack(url, condition, event, callBack) {
	event.preventDefault();

	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : condition,
		success : function(data, textStatus, request) {
			var msgFlag = request.getResponseHeader('msgFlag');
			if( "1" == msgFlag ) {
				$(".message-info").html(data);
			} else {
				var content = $(data).find('.body-content');
				$(".main_content").html(content);
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
		},
		complete : function(result) {
			callBack();
			goTopPage();
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
		text : "Loading"
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
    months = new Array('Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec');
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

function goTopPage(){
	$("html, body").animate({ scrollTop: 0 }, "1");
}

//var stompClient = null;
//function connect() {
//    var socket = new SockJS(BASE_URL + 'websocket');
//    stompClient = Stomp.over(socket);
//    stompClient.connect({}, function (frame) {
//        stompClient.subscribe('/session/count', function (greeting) {
//        	var sessionCount = JSON.parse(greeting.body).sessionCount;
//        	$('#countSession').html(sessionCount);
//        });
//        sendMessage();
//    });
//}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function sendMessage() {
    stompClient.send("/app/session-listener", {}, JSON.stringify({}));
}


$(document).ready(function(){
//	connect();
	
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
	            if( log ) alert(log);
	          }
	    });
})

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
	//event.preventDefault();
	  
	$("#import-component-modal").modal('show');
	$("#fileList").empty();
	$("#message-upload-id").empty();
	$("#message-inprocess").empty();
	$('#sheetExcelSelectId').html('<select class="form-control" id="sheetExcelId" name="sheetNames"></select>');
	 var selectForm =  $('#sheetExcelId');
	  $(selectForm).select2({
	    
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
		
		  $('#sheetExcelId').attr("multiple","multiple");	
		  $('#sheetExcelId').select2({
			    separator: '|',
				placeholder : "Select sheet!",
				minimumInputLength : 0,
				allowClear:true,
		  });
		
	  };
	  if(rABS) reader.readAsBinaryString(file); else reader.readAsArrayBuffer(file);
	  

}

function loadFormatNumber(){
	$(document).on('input','.text-number',function () {
		$(this).parseNumber({
			format : FORMAT_NUMBER,
			locale : 'en'
		});
		$(this).formatNumber({
			format : FORMAT_NUMBER,
			locale : 'en'
		});
		
	});
}


function getMessage(status, content){
	let message ='';
	if(status === 'success'){
		message +='<div class="alert alert-success alert-dismissible">';
		message +='<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>';
        message +='<h4><i class="icon fa fa-check"></i>Successfull</h4>';
        message +='<div/>'+content+'</div>';
	}else if(status === 'error' ){
	    message +='<div class="alert alert-danger alert-dismissible">';
		message +=' <button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>';
	    message +='<h4> <i class="icon fa fa-ban"></i>Fail</h4>';
	    message +='<div/>'+content+'</div>';
	}
	return message;
}

function ajaxJson(url, data, action) {
	return $.ajax({
		url : BASE_URL + url,
		type : action,
		contentType : 'application/json; charset=utf-8',
		dataType : 'json',
		data : JSON.stringify(data),
		global : false,
	    beforeSend: function(request){
	    	setCSRFtoRequest(request);
	    }
	});
}

function converToDate(dateString){
	if(dateString == null || dateString == '')
		return;
	let part = dateString.split('/');
	let dateStringTemp = part[2] + '-' + part[1] + '-' + part[0];
	return new Date(dateStringTemp);
}

function converDateToString(date){
	var mm = date.getMonth() + 1; // getMonth() is zero-based
	var dd = date.getDate();
	// dd/mm/yyyy
	return [(dd>9 ? '' : '0') + dd,
			(mm>9 ? '' : '0') + mm,
	        date.getFullYear()
	       ].join('/');
}

function decimalFormat(numValue) {
	return jQuery.formatNumber(numValue, {
		format : FORMAT_NUMBER,
		locale : 'en'
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
	
	$(idFrom).datepicker({
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
		        $(idTo).datepicker('setStartDate', startDate);  
		    });
		$(idTo)
		    .datepicker({
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
		        $(idFrom).datepicker('setEndDate', FromEndDate);
		    });
}

function ajaxSubmit(url, condition, event, idSearch) {
	event.preventDefault();

	$.ajax({
		type : "POST",
		url : BASE_URL + url,
		data : condition,
		success : function(data, textStatus, request) {
			var msgFlag = request.getResponseHeader('msgFlag');
			if( "1" == msgFlag ) {
				$(".message-info").html(data);
			} else {
				var content = $(data).find('.body-content');
				$(".main_content").html(content);
			}
			
			var urlPage = $(data).find('#url').val();
			if (urlPage != null && urlPage != '') {
				window.history.pushState('', '', BASE_URL + urlPage);
			}
			
			$('#'+idSearch).click();
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		},
		complete : function(result) {
			goTopPage();
		}
	});
}

function wordWrap(str, maxWidth) {
    var newLineStr = "\n"; done = false; res = '';
    do {                    
        found = false;
        // Inserts new line at first whitespace of the line
        for (i = maxWidth - 1; i >= 0; i--) {
            if (testWhite(str.charAt(i))) {
                res = res + [str.slice(0, i), newLineStr].join('');
                str = str.slice(i + 1);
                found = true;
                break;
            }
        }
        // Inserts new line at maxWidth position, the word is too long to wrap
        if (!found) {
            res += [str.slice(0, maxWidth), newLineStr].join('');
            str = str.slice(maxWidth);
        }

        if (str.length < maxWidth)
            done = true;
    } while (!done);

    return res + str;
}

function testWhite(x) {
    var white = new RegExp(/^\s$/);
    return white.test(x.charAt(0));
};

function checkToolTipLength(element, lengthText) {
	var text = element.text();

	if (text.length > lengthText) {

		textTmp = text.substring(0, lengthText);

		textTmp += '...';

		element.prop("title", text);
		element.text(textTmp);
		element.tooltip({
			trigger : 'hover'
		});
	}
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
	timerToken = setInterval(function() {
		if ($.cookie(tokenid) === 'OK') {
			unblockbg();
			clearInterval(timerToken);
		}
	}, 100);

	var $formExport = jQuery("<form>", {
        'action' : url,
        'method' : 'POST'
    });
	
	// add fields
	if (pramObject != null) {
		$.each(pramObject, function(key, val) {
			$formExport.append(jQuery("<input>", {	
		        "name" : key,
		        "value": val,
		        "type" : "hidden"
		    }));
		});
	}
	
	if (tokenName == undefined || tokenName == null || tokenName == '') {
		tokenName = "tokenId";
	}
	
	$formExport.append(jQuery("<input>", {	
        "name" : tokenName,
        "value": tokenid,
        "type" : "hidden"
    }));
	
	var tokenCsrf = $("meta[name='_csrf']").attr("content");
    
	$formExport.append(jQuery("<input>", {	
        "name" : "_csrf",
        "value": tokenCsrf,
        "type" : "hidden"
    }));

	$formExport.appendTo(document.body);
	$formExport.submit().remove();
	
	blockbg();
}

/**
 * doExportExcel
 * @param url
 * @param tokenName
 * @param pramObject
 */
function doExportExcel(url, tokenName, pramObject, method) {
	
	var timerToken;
	var tokenid = getToken();
	timerToken = setInterval(function() {
		if ($.cookie(tokenid) === 'OK') {
			unblockbg();
			clearInterval(timerToken);
		}
	}, 100);

	var $formExport = jQuery("<form>", {
        'action' : url,
        'method' : method
    });
	
	// add fields
	if (pramObject != null) {
		$.each(pramObject, function(key, val) {
			$formExport.append(jQuery("<input>", {	
		        "name" : key,
		        "value": val,
		        "type" : "hidden"
		    }));
		});
	}
	
	if (tokenName == undefined || tokenName == null || tokenName == '') {
		tokenName = "tokenId";
	}
	
	$formExport.append(jQuery("<input>", {	
        "name" : tokenName,
        "value": tokenid,
        "type" : "hidden"
    }));
	
	var tokenCsrf = $("meta[name='_csrf']").attr("content");
    
	$formExport.append(jQuery("<input>", {	
        "name" : "_csrf",
        "value": tokenCsrf,
        "type" : "hidden"
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
	var id = day + '' + month + '' + year + '' + hour + '' + minute + ''+ second + '' + millisecond;
	return id;
}

function setCSRFtoRequest(request){
	var token = $("meta[name='_csrf']").attr("content");
  	var header = $("meta[name='_csrf_header']").attr("content");
  	request.setRequestHeader(header, token);
}

/**
 * change Currency -> change Exchange rate
 */
function changeCurrencyCom(currencyID, eleIdChangeByCurrency) {
	var $currencyID = $('#' + currencyID);
	// currency value
	let currency = $currencyID.val();
	// rate value tương ứng với currency selected
	let rate = $currencyID.find('option:selected').data('rate');
	
	// Trường hợp currency là VND, thực hiện fortmat exchangeRate number
	if (currency === 'VND') {
		$('#exchangeRate').attr('readonly','readonly');
		
		$(eleIdChangeByCurrency).removeClass('j-fm-no-dec');
		$(eleIdChangeByCurrency).addClass('j-fm-no');

		$('.j-fm-no').unbind();
		$('.j-fm-no').number(true);

	} else { // currency không phải là VND, thực hiện fortmat exchangeRate number , 2
		$('#exchangeRate').removeAttr('readonly');
		
		$(eleIdChangeByCurrency).removeClass('j-fm-no');
		$(eleIdChangeByCurrency).addClass('j-fm-no-dec');
		
		$('.j-fm-no-dec').unbind();
		$('.j-fm-no-dec').number(true, 2);
	}
	
	// Set giá trị rate tương ứng với currency
	$('#exchangeRate').val(rate);
}

/**
 * change Currency -> change Exchange rate
 */
function changeCurrencyComNoReadonly(currencyID, eleIdChangeByCurrency) {
	var $currencyID = $('#' + currencyID);
	// currency value
	let currency = $currencyID.val();
	// rate value tương ứng với currency selected
	let rate = $currencyID.find('option:selected').data('rate');
	
	// Trường hợp currency là VND, thực hiện fortmat exchangeRate number
	if (currency === 'VND') {
		$('#exchangeRate').attr('readonly','readonly');
		
		$(eleIdChangeByCurrency).removeClass('j-fm-no-dec');
		$(eleIdChangeByCurrency).addClass('j-fm-no');

		$('.j-fm-no').unbind();
		$('.j-fm-no').number(true,undefined,undefined,undefined, true);

	} else { // currency không phải là VND, thực hiện fortmat exchangeRate number , 2
		$('#exchangeRate').removeAttr('readonly');
		
		$(eleIdChangeByCurrency).removeClass('j-fm-no');
		$(eleIdChangeByCurrency).addClass('j-fm-no-dec');
		
		$('.j-fm-no-dec').unbind();
		$('.j-fm-no-dec').number(true,2,undefined,undefined, true);
	}
	
	// Set giá trị rate tương ứng với currency
	$('#exchangeRate').val(rate);
}

$.fn.serializeObject = function() {
	var o = {};
	var a = this.serializeArray();
	$.each(a, function() {
		if (o[this.name] !== undefined) {
			if (!o[this.name].push) {
				o[this.name] = [o[this.name]];
			}
			o[this.name].push(this.value || '');
		} else {
			o[this.name] = this.value || '';
		}
	});
	return o;
};

function changeAlias(alias) {
    var str = alias;
    str = str.toLowerCase();
    str = str.replace(/à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ/g,"a"); 
    str = str.replace(/è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ/g,"e"); 
    str = str.replace(/ì|í|ị|ỉ|ĩ/g,"i"); 
    str = str.replace(/ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ/g,"o"); 
    str = str.replace(/ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ/g,"u"); 
    str = str.replace(/ỳ|ý|ỵ|ỷ|ỹ/g,"y"); 
    str = str.replace(/đ/g,"d");
    str = str.replace(/!|@|%|\^|\*|\(|\)|\+|\=|\<|\>|\?|\/|,|\.|\:|\;|\'|\"|\&|\#|\[|\]|~|\$|_|`|-|{|}|\||\\/g," ");
    str = str.replace(/ + /g," ");
    str = str.trim(); 
    return str;
}

function trimObjectData(params) {
	try {
		if (params != undefined && params != null && params != '') {
			$.each(params, function( key, value ) {
			  if (value != undefined && value != null && value != '') {
				  value = value.trim();
			  }
			  params[key] = value;
			});
		}
	} catch(err) {
	  console.log(err);
	}

	return params;
}

function removeSpaceForInput(strName){
	strName = strName.substring(0, 255);
	var linkAlias = strName.toLowerCase().replace(/[^a-zA-Z\d\s]+/gi,'').replace(/[^a-zA-Z\d\s]+/gi,' ');
	var aliasCompArray = linkAlias.split(/ /g);
	var comps = [];
	
	for(var compIndex in aliasCompArray){
		var compString = aliasCompArray[compIndex];
		if(!(compString === "")){
			comps.push(compString);
		}
	}
	
	linkAlias = comps.join("");
	return linkAlias;
}

function removeSpaceForInputCrossbar(strName){
	strName = strName.substring(0, 255);
	var linkAlias = strName.toLowerCase().replace(/[^a-zA-Z-\d\s]+/gi,'').replace(/[^a-zA-Z-\d\s]+/gi,' ');
	var aliasCompArray = linkAlias.split(/ /g);
	var comps = [];
	
	for(var compIndex in aliasCompArray){
		var compString = aliasCompArray[compIndex];
		if(!(compString === "")){
			comps.push(compString);
		}
	}
	
	linkAlias = comps.join("");
	return linkAlias;
}