$(function(){
    
    $('#linkList, #btnCancel').unbind().bind('click', function(event){
		event.preventDefault();
		
		popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				let url = BASE_URL + "emulate-result/list";
				// Redirect to page list
				ajaxRedirect(url);
			}
		})
    })
    
	$('#btnSearch').unbind().bind('click', function(e){
		var url = CONTROLLER_URL + "/ajaxList";
        
		ajaxSearch(url, setConditionSearchImport(), "tableList", this, event);
	})
	
	$('#btnClear').unbind().bind('click', function(e){
		$('#channel').val('');
        
		$('#btnSearch').trigger('click');
	})
	
    $("#btnTemplateDownload").unbind().bind('click', function(e){
        var uploadUrl = BASE_URL + CONTROLLER_URL + "/download-template-excel";
        makePostRequest(uploadUrl, {});
    })
    
    $("#btnErrorDownload").unbind().bind('click', function(e){
        let fileName = $('#fileName').val();
        if (fileName === undefined || fileName == null || fileName === ""){
            var linkExport = BASE_URL + CONTROLLER_URL + "/download-template-error-excel";
            doExportExcelWithToken(linkExport, "token", setConditionSearchImport());
        }else{
            var linkExport = BASE_URL + CONTROLLER_URL + "/download-template-error-excel-with-file-name";
            let condition = {};
            condition["fileName"] = fileName;
            condition["token"] = "token";
            makePostRequest(linkExport, condition);
        }
    })
    
    // datatable load
    $("#tableList").datatables({
        url : BASE_URL + CONTROLLER_URL + '/ajaxList',
        type : 'POST',
        setData : setConditionSearchImport
    });
    
	initData();
    
    $('#channel, #agentCode, #effectiveDateFrom').on('change', function(e){
        $('#sessionKey').val('');
    })
    
    setActiveMenu();
    setActiveMenuCustom();
});

function setActiveMenu() {

	let CURRENT_URL = window.location.href;
	
	CURRENT_URL = CURRENT_URL.replace("aa-edit-movement-import", "aa-search-movement-import");
	let findex = CURRENT_URL.indexOf(BASE_URL.replace("aa-edit-movement-import", "aa-search-movement-import"));
	let lindex = CURRENT_URL.length;
	let url = CURRENT_URL.substring(findex, lindex);
	
	

	// Phuc Nguyen
	// Auto detect active menu
	// 10/01/2018
	if (url != null && url != '') {
		
		if (!url.includes('jcanary-home')) {
			// get all link in menu
			let mostSimilar;
			let tmpSimilar = 0;
			let count;
			let countSimalar;

			// check to get the most similar url to check active
			menu_left_bar.find('a').each(function() {
				// reset counter
				count = 0;
				countSimalar = 0;
				// find the shorter length to check
				let subUrl = $(this).attr('href');
				let maxSize = subUrl != undefined ? (url.length > subUrl.length ? subUrl.length : url.length) : url.length;
				// counter number of similar char
				if (subUrl != undefined){
					while (count < maxSize && subUrl.charAt(count) == url.charAt(count)) {
						countSimalar++;
						count++;
					}
				}
				
				// check if it is the most similar url
				if (countSimalar > tmpSimilar) {
					mostSimilar = subUrl;
					tmpSimilar = countSimalar;
				}
			});
			
			// continue to get and set active
			var ele = menu_left_bar.find('a[href="' + mostSimilar + '"]').first().parents('li');
			var eleLength = $(ele).length;
			// li element will run from n->0
			var n = i = eleLength;

			$(ele).each(function() {
				$(this).addClass('menu-open');
				if (i == n) {
					$(this).find('a').addClass('active');
				} else {
					$(this).find('.menu-open').parent('ul').first().css(
							'display', 'block');
				}
				i--;
			});
		}
		
	}
}

function setActiveMenuCustom(){
	
	let CURRENT_URL = window.location.href;
	CURRENT_URL = CURRENT_URL.replace("emulate-result-import", "emulate-result");
	
	var arr_current_url = CURRENT_URL.split('#')[0].split("//")[1].split('/');
	
	var current_url = '/' +arr_current_url.slice(1,arr_current_url.length).join('/');
	
	menu_left_bar.find('a').each(function(key, val) {
		if (current_url == $(val).attr('href')) {
			$(val).addClass('active');
		} else {
			$(this).removeClass('active');
		}
	});
	
}

function initData() {
	$(".field-select2").select2({placeholder:'Select a channel'});

	$('.datepicker > input').datepicker({
		format : 'dd/mm/yyyy',
		changeMonth : true,
		changeYear : true,
		autoclose : true,
		todayHighlight: true,
		keyboardNavigation : true
	});
}

function setConditionSearchImport(){
	let condition = {};
	condition["sessionKey"] = $('#sessionKey').val();
    condition["channel"] = $('#channel').val();
    
	return condition;
}

function isNull(value){
	if (value == undefined || value == ''){
		return true;
	}
	return false;
}