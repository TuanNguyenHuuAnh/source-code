var PAGE_URL = CUSTOMER_ALIAS + '/faqs-category';

$(document).ready(function($) {

	init();
	$('#itemFunctionCode').on("select2:close", function (e) {		

		let id = $(this).attr('id');
		$(this).removeClass('error');
		findAndRemoveError(id);
	});
	$('#_easyui_textbox_input1').blur(function (e) {
			let id = $(this).attr('id');
			$(this).removeClass('error');
			findAndRemoveError(id);
		});
	
	$('#tabLanguage a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});

	//on click cancel
	$('#cancel, #linkList').on('click', function(event) {
		event.preventDefault();
		
		popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				var url = BASE_URL + PAGE_URL + "/list";
				// Redirect to page list
				ajaxRedirectWithCondition(url, setConditionSearch());
			}
		});
	});
	
	// on click add
	$("#addNew").on("click", function(event) {
		event.preventDefault();
		
		popupConfirmWithButtons(MSG_ADD_NEW_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				let url = BASE_URL + PAGE_URL + "/edit";
				// Redirect to page add
				ajaxRedirectWithCondition(url, setConditionSearch());
			}
		})
	});

	// on click button save
	$('#btnSave').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = PAGE_URL + "/edit";
			updateElementEditor();
			
			var condition = $("#categoryForm").serialize();
			ajaxSubmit(url, condition, event);
		} else {
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
			
			rollToError('j-form-validate');
		}
	});
		
	// show tab if exists error
	showTabError(LANGUAGE_LIST);
	
	//tabindex
	var lstTextInput = $("input[type=text][name$='.title']");
	var lstLinkAlias = $("input[type=text][name$='.keywordsSeo']");	
	if (lstLinkAlias.length > 0) {
		initLinkAliasForListEvent($(lstTextInput[0]), lstLinkAlias);
	}

	var lstkeyword = $("input[type=text][name$='.keywords']");
	for (var i = 0; i < lstTextInput.length; i++) {
		initTagSelectorEvent($(lstTextInput[i]), $(lstkeyword[i]));
	}
	
	$("#typeId, #faqsTypeList").select2({ allowClear : true});
	
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
});

function init(){
	var isDisabled = $('#hasEdit').val() == 'false';
	disabledAllField('categoryForm', isDisabled);

	if ($("#id").val() != "") {
		$("#code").attr('readonly', 'readonly');
	}
	
	$('#itemFunctionCode').select2({ allowClear: true });
	
	$('#listTree').combotree({
		editable : true,
    });

	if( LIST_TREE_JSON != null && LIST_TREE_JSON != '' ) {
		$('#listTree').combotree('loadData', jQuery.parseJSON(LIST_TREE_JSON));
	}
	
	$('input[name=faqsCategoryParentId]').addClass('j-required');
}

function setConditionSearch(){
	var condition = {};
	let value = $("#searchDto").val();
	
	if (!isNull(value)){
		var tmp = JSON.parse($("#searchDto").val());
		condition["code"] = tmp.code;
		condition["title"] = tmp.title
		condition["status"] = tmp.status
		condition["enabled"] = tmp.enabled
		condition["faqsTypeId"] = tmp.faqsTypeId
	}
	return condition;
}
function nameToLinkAlias(strName){
    strName = strName.substring(0, 1000);
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
function nameToTag(strName){
    strName = strName.substring(0, 1000);
    var keyword = removeDiacritics(strName.toLowerCase()).replace(/[^a-zA-Z\d\s-]+/gi,'').replace(/[^a-zA-Z\d\s]+/gi,' ');
    keyword = keyword.replace(/ /g,"");
    return (keyword == null || keyword == "") ? "" : "#" + keyword;
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