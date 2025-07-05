var PAGE_URL = CUSTOMER_ALIAS + '/document-category';

$(document).ready(function($) {

	init();

	$('#tabLanguage a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
	$('select[multiple]').multiselect({
		columns: 1,
		search: false,
		minHeight: 100,
		maxHeight: 155
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
		})
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
	//lock characters
	$('#validateDate').keypress(function(event) {
		var regex = new RegExp("^[a-zA-Z0-9]+$");
		var key = String.fromCharCode(!event.charCode ? event.which : event.charCode);
		if (!regex.test(key)) {
			event.preventDefault();
			return false;
		}
		/*if ((event.which < 48)) {
			event.preventDefault();
		}*/
	});
	
	if ($('#hasEdit').val() == 'false') {
		$("input[type=checkbox]").prop("disabled", true);
	};

});

function init() {
	var isDisabled = $('#hasEdit').val() == 'false';
	disabledAllField('categoryForm', isDisabled);

	if ($("#id").val() != "") {
		$("#validateDate").attr('readonly', 'readonly');
	}
	
	$('#itemFunctionCode').select2({ allowClear: true });

	$('#listTree').combotree({
		editable: true,
	});

	if (LIST_TREE_JSON != null && LIST_TREE_JSON != '') {
		$('#listTree').combotree('loadData', jQuery.parseJSON(LIST_TREE_JSON));
	}

	$('input[name=parentId]').addClass('j-required');

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

	//check press enter
	$("input[type=text]").keypress("keydown", function(e) {
		if (e.keyCode == 13) {
			e.preventDefault();
		}
	});

	//check press space
	$(lstLinkAlias[0]).keypress("keydown", function(e) {
        console.log(123)
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

	$(lstLinkAlias[0]).on("change", function(e) {
        console.log(345)
		var txtName = $(lstLinkAlias[0]).val();
		for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName);
			$(lstLinkAlias[i]).val(linkAlias);
		}
		e.preventDefault();
	});

	$(lstkeyword[0]).on("change", function(e) {
        console.log(768)
		var txtName = $(lstkeyword[0]).val();
		for (var i = 0, sz = lstkeyword.length; i < sz; i++) {
			linkAlias = nameToTag(txtName);
			$(lstkeyword[i]).val(linkAlias);
		}
		e.preventDefault();
	});

	//check focusout
	$($("input[type=text][name$='.linkAlias']")).focusout(function(e) {
        console.log(ádasd)
		var txtName = $(lstLinkAlias[0]).val();
		for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
			linkAlias = nameToLinkAlias(txtName);
			$(lstLinkAlias[i]).val(linkAlias);
		}
		e.preventDefault();
	});

	clearErrorWhenMove('j-required', 'form-control');
	clearErrorWhenMove('textbox-text', 'form-control');
}

function setConditionSearch() {
	var tmp = JSON.parse($("#searchDto").val());
	var condition = {};
	condition["code"] = tmp.code;
	condition["title"] = tmp.title
	condition["status"] = tmp.status
	condition["enabled"] = tmp.enabled
	condition["faqsTypeId"] = tmp.faqsTypeId

	return condition;
}