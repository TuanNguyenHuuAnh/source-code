var PAGE_URL = CUSTOMER_ALIAS + '/news-category';

$(document).ready(function ($) {

    init();

    $('#keyWord').blur(function (e) {
        let id = $(this).attr('id');
        $(this).removeClass('error');
        findAndRemoveError(id);
    });
    $('#mNewsTypeIdDto').on("select2:close", function (e) {
        let id = $(this).attr('id');
        $(this).removeClass('error');
        findAndRemoveError(id);
    });

    // tabLanguage click
    $('#tabLanguage a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
    });

    // initLinkAliasSelectorEvent
    initLinkAliasSelectorEvent($('#name'), $('#link-alias'));

    //on click cancel
    $('#cancel, #linkList').on('click', function (event) {
        event.preventDefault();

        popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function (result) {
            if (result) {
                var url = BASE_URL + PAGE_URL + "/list";
                // Redirect to page list
                ajaxRedirect(url);
            }
        })
    });

    // on click add
    $("#addNew").on("click", function (event) {
        event.preventDefault();

        popupConfirmWithButtons(MSG_ADD_NEW_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function (result) {
            if (result) {
                let url = BASE_URL + PAGE_URL + "/edit";
                // Redirect to page add
                ajaxRedirectWithCondition(url, setConditionSearch());
            }
        })
    });

    //on click list
    $('').on('click', function (event) {
        event.preventDefault();
        var url = BASE_URL + PAGE_URL + "/list";

        // Redirect to page list
        ajaxRedirectWithCondition(url, setConditionSearch());
    });

    // on click button save
    $('#btnSave').on('click', function (event) {

        $('#mNewsTypeIdDto').val($('#mNewsTypeId').val())

        if ($(".j-form-validate").valid()) {
            var url = PAGE_URL + "/edit";
            var condition = $("#categoryForm").serialize();

            ajaxSubmit(url, condition, event);
        } else {
            // show tab if exists error
            showTabError(LANGUAGE_LIST);

            rollToError('j-form-validate');
        }
    });
    
	$('select[multiple]').multiselect({
		columns: 1,
		search: false,
		minHeight: 100,
		maxHeight: 155
	});
	
	if ($('#hasEdit').val() == 'false') {
		$("input[type=checkbox]").prop("disabled", true);
	}
});

function init() {

    $('#mNewsTypeIdDto').on('change', function (event) {
        if ($("#categoryId").val() == '') {
            $('#mNewsTypeId').val($('#mNewsTypeIdDto').val());
        }
    });
    $('#mNewsTypeIdDto').val($('#mNewsTypeId').val());

    var isDisabled = $('#hasEdit').val() == 'false';
    disabledAllField('categoryForm', isDisabled);

    if ($("#id").val() != "") {
        $("#code").attr('readonly', 'readonly');

    }

    $("#mNewsTypeIdDto").select2({allowClear: true});

    // show tab if exists error
    showTabError(LANGUAGE_LIST);

    // tabindex
    var lstTextInput = $("input[type=text][name$='.label']");
    var lstTabLanguage = $("li[role=presentation][id^=tabLanguage]");
    var tabindex = 9;
    for (var i = 0; i < lstTextInput.length; i++) {
        $(lstTextInput[i]).attr("tabindex", tabindex++);
    }

    // tabindex
    var lstTextInput = $("input[type=text][name$='.label']");
    var lstLinkAlias = $("input[type=text][name$='.linkAlias']");
    if (lstLinkAlias.length > 0) {
        initLinkAliasForListEvent($(lstTextInput[0]), lstLinkAlias);
        /*		for(var i=1; i< lstLinkAlias.length; i++){
                    initLinkAliasForListEvent($(lstTextInput[i]), $(lstLinkAlias[i]));
                }*/
    }

    var lstkeyword = $("input[type=text][name$='.keyWord']");
    for (var i = 0; i < lstTextInput.length; i++) {
        initTagSelectorEvent($(lstTextInput[i]), $(lstkeyword[i]));
    }

    // tabindex button
    if ($("#btnSave").length > 0) {
        $("#btnSave").attr("tabindex", tabindex++);
    }

    // validate on blur and change tab
    var keyTab = '9';
    $(lstTextInput[0]).keydown(function (e) {
        var code = e.keyCode || e.which;
        if (code == keyTab) {
            $(lstTabLanguage[1]).find('a').tab('show');
        }
    });
    $(lstTabLanguage[1]).on('shown.bs.tab', function (e) {
        $(lstTextInput[1]).focus();
    })

    // validate on blur
    $("#mNewsTypeId, #name, #link-alias").blur(function () {
        $(this).valid();
    });

    //check press enter
    $("input[type=text]").keypress("keydown", function (e) {
        if (e.keyCode == 13) {
            e.preventDefault();
        }
    });

    //check press space
    $(lstLinkAlias[0]).keypress("keydown", function (e) {
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

    $(lstLinkAlias[0]).on("change", function (e) {
        var txtName = $(lstLinkAlias[0]).val();
        for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
            linkAlias = nameToLinkAlias(txtName);
            linkAlias = linkAlias + "-";
            $(lstLinkAlias[i]).val(linkAlias);
        }
        e.preventDefault();
    });

    $(lstkeyword[0]).on("change", function (e) {
        var txtName = $(lstkeyword[0]).val();
        for (var i = 0, sz = lstkeyword.length; i < sz; i++) {
            linkAlias = nameToLinkAlias(txtName).replaceAll('-', '');
            linkAlias = i == 0 ? "#" + linkAlias : +linkAlias;
            $(lstkeyword[i]).val(linkAlias);
        }
        e.preventDefault();
    });

    //check focusout
    $($("input[type=text][name$='.linkAlias']")).focusout(function (e) {
        var txtName = $(lstLinkAlias[0]).val();
        for (var i = 0, sz = lstLinkAlias.length; i < sz; i++) {
            linkAlias = nameToLinkAlias(txtName);
            $(lstLinkAlias[i]).val(linkAlias);
        }
        e.preventDefault();
    });
    //lock characters
    $('#validateNumber').keypress(function(event) {
        if ((event.which < 48 || event.which > 57)) {
            event.preventDefault();
        }
    });
    createValidate();
}

//validate min max
function createValidate() {
    createFunctionValidate("validateMin", function(value, element) {
        if (parseInt(value) >= 1) {
            return true;
        }
    },VALIDATE_MIN);

    createFunctionValidate("validateMax", function(value, element) {
        if (parseInt(value) <= 10) {
            return true;
        }
    },VALIDATE_MAX);


}


/**
 * init select box
 */
function initSelect() {
    var typeSelect = $("#typeJsonHidden").val();
    if (typeSelect != null && typeSelect != '') {
        var $category = $('#mNewsTypeId');
        $category.find('option:not(:first)').remove();

        $.each(jQuery.parseJSON(typeSelect), function (key, val) {
            $category.append('<option value="' + val.value + '">' + val.name + '</option>');
        });

        if (TYPE_ID != null && TYPE_ID != '') {
            $('#mNewsTypeId').val(TYPE_ID).prop('selected', true);
        }
    }
}

function setConditionSearch() {
    var tmp = JSON.parse($("#searchDto").val());
    var condition = {};
    condition["customerTypeId"] = tmp.customerTypeId;
    condition["newsTypeId"] = tmp.newsTypeId;
    condition["code"] = tmp.code;
    condition["label"] = tmp.label;
    condition["status"] = tmp.status;
    condition["statusActive"] = tmp.statusActive;

    return condition;
}
