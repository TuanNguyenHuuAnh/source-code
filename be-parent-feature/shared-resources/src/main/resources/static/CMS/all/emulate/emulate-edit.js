$(document).ready(function($) {
	init();

	// onclick tabLanguage
	$('#tabLanguage a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	// on click cancel
	$('#cancel, #linkList').on('click', function(event) {
		event.preventDefault();
		
		popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				var url = BASE_URL + PAGE_URL + "/list";
				// Redirect to page list
				ajaxRedirectWithCondition(url, setConditionSearch());
			}
		})
	});
	
	$('select[multiple]').multiselect({
		columns: 1,
		search: true,
		minHeight: 100,
		maxHeight: 155
	});
	
	$('#subjectsApplicableAll').on('change', function(){
		$('input[name=subjectsApplicable]').val('0');
		
		$('#divRegionAgentType').css('display', 'none');
	})
	
	$('#subjectsApplicableImport').on('change', function(){
		$('input[name=subjectsApplicable]').val('1');
		
		$('#divRegionAgentType').css('display', 'none');
	})
	
	$('#subjectsApplicableRegion').on('change', function(){
		$('input[name=subjectsApplicable]').val('2');
		
		$('#divRegionAgentType').css('display', 'block');
	})
	
	checkSubjectsApplicable();
});

function init(){
	var isDisabled = $('#hasEdit').val() == 'false';
	disabledAllField('formId', isDisabled);
	
	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}
	
	// datepicker
	$('.datepicker-time').datetimepicker({
		format: 'DD/MM/yyyy HH:mm:ss',
	});
	
	$('.datepicker').datetimepicker({
		format: 'DD/MM/yyyy',
	});
	
	$('select[multiple]').multiselect({
		columns : 1,
		search : true,
		minHeight: 100,
		maxHeight: 155
	});
	
	// show tab if exists error
	showTabError(LANGUAGE_LIST);
	
	$('#tabLanguage a:first').tab('show');
	
	// tabindex
	var lstTextInput = $("input[type=text][name$='.title']");
	var lstLinkAlias = $("input[type=text][name$='.linkAlias']");	
	if (lstLinkAlias.length > 0) {
		initLinkAliasForListEvent($(lstTextInput[0]), lstLinkAlias);
	}
	
	var lstkeyword = $("input[type=text][name$='.keyword']");
	for (var i = 0; i < lstTextInput.length; i++) {
		initTagSelectorEvent($(lstTextInput[i]), $(lstkeyword[i]));
	}
	
	//check press enter
	$("input[type=text]").keypress("keydown",function(e){
		if (e.keyCode == 13) {
			e.preventDefault();
		}
	});
	
	//check press space
	$(lstLinkAlias[0]).keypress("keydown",function(e){
		if (e.keyCode == 32) {
			var txtName = $(lstLinkAlias[0]).val();
			for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
				linkAlias = nameToLinkAlias(txtName);
				linkAlias = linkAlias + "-";
				$(lstLinkAlias[i]).val(linkAlias);
			}
			e.preventDefault();
		}
	});
	
	$(lstLinkAlias[0]).on("change",function(e){
		var txtName = $(lstLinkAlias[0]).val();
		for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName);
			linkAlias = linkAlias + "-";
			$(lstLinkAlias[i]).val(linkAlias);
		}
		e.preventDefault();
	});
	
	$(lstkeyword[0]).on("change",function(e){
		var txtName = $(lstkeyword[0]).val();
		for (var i = 0, sz = lstkeyword.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName).replaceAll('-', '');
			linkAlias = i == 0 ? "#" + linkAlias : + linkAlias;
			$(lstkeyword[i]).val(linkAlias);
		}
		e.preventDefault();
	});
	
	//check focusout
	$($("input[type=text][name$='.linkAlias']")).focusout(function(e) {
		var txtName = $(lstLinkAlias[0]).val();
		for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName);
			$(lstLinkAlias[i]).val(linkAlias);
		}
		e.preventDefault();
	});
	
	// init by language list
	initByLanguageList();
}

/**
 * init by language list
 */
function initByLanguageList() {
	// lặp language list
	$.each(LANGUAGE_LIST, function(key, val) {
		// IMAGE signature
		signatureImageUrl(key, PAGE_URL);
		
		// init image uploader url
		initImageUploaderUrl(key, PAGE_URL, { width : 2580, height : 680 });
	});
}

function setConditionSearch(){
	let condition = {};
	try {
		if ($("#searchDto").val() != ''){
			let tmp =JSON.parse($("#searchDto").val());
			
			condition["status"] = tmp.status;
			condition["code"] = tmp.code;
			condition["title"] = tmp.title;
			condition["typeId"] = tmp.typeId;
			condition["enabled"] = tmp.enabled;
		}
	} catch (error) {
		console.error(error);
	}

	return condition;
}

function checkSubjectsApplicable(){
	if ($('#subjectsApplicableAll').is(':checked')){
		$('input[name=subjectsApplicable]').val('0');
		
		$('#divRegionAgentType').css('display', 'none');
	}
	
	if ($('#subjectsApplicableImport').is(':checked')){
		$('input[name=subjectsApplicable]').val('1');
		
		$('#divRegionAgentType').css('display', 'none');
	}
	
	if ($('#subjectsApplicableRegion').is(':checked')){
		$('input[name=subjectsApplicable]').val('2');
		
		$('#divRegionAgentType').css('display', 'block');
	}
}