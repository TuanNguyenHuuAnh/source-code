$(document).ready(function() {
	initCheckbox($('#cbSendNotification'));
	//initCompanyCombobox();
	jobTypeOnChange($('#jobTypes'));
	$("#linkList").on("click", function(event) {
		cancel(event);
	});

	$('#btn-cancel, #btnBack').on('click', function(event) {
		cancel(event);
	});

	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var form = $('#form-j-edit').serialize();
			ajaxSubmit('quartz/job/save', form, event);
			setJobClassItem();
		}
	});

	$('#sendStatus').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});

	$('#cbSendNotification').on('change', function() {
		initCheckbox(this);
	});

	$('#companyId').on('change', function() {
		$('#emailTemplate').val('').trigger('change');
	});

	$('#jobTypes').on('change', function() {
		$('#jobClass').val('').trigger('change');
		jobTypeOnChange($(this));
	});

	/*$('#jobClass').on('change', function() {
		var text = $(this).find("option:selected").text();
		$('#jobTypes option').each(function() {
			var val = $(this).val();
		    if(text === val) {
		        $(this).prop("selected", "selected");
		        return;
		    }
		});
	});*/
	$("#btnCreate").on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "quartz/job/upsert";
		// Redirect to page add
		ajaxRedirect(url);
	});
	$('.ms-options-wrap button').on('click', function(event) {
		event.preventDefault();		
		var isError = $('#sendStatus').hasClass("error");
		var text = $(this).text();
		if(isError) {
			$('#sendStatus-error').remove();
			$('#sendStatus').removeClass("error");
			var element = $(".ms-options-wrap").find('input[type="checkbox"]:first');
			$(element).trigger('click');
		}
	});
	
	initEmailCombobox();
})

function cancel(event) {
	var url = BASE_URL + "quartz/job/list";
	ajaxRedirect(url);
}

function initCheckbox(element) {
	var checked = $(element).is(":checked");
	if (checked) {
		$('#emailTemplate').addClass("j-required");
		$('#sendStatus').addClass("j-required");
		$('#recipientAddress').addClass("j-required");
		$('#recipientName').addClass("j-required");
		$('#group-email-config').css('display', 'block');
	} else {
		$('#emailTemplate').removeClass("j-required");
		$('#sendStatus').removeClass("j-required");
		$('#recipientAddress').removeClass("j-required");
		$('#recipientName').removeClass("j-required");
		$('#group-email-config').css('display', 'none');
	}
}

/*function initCompanyCombobox() {
	var link = 'quartz/getListCompany';
	searchCombobox('#comId', '', link, function data(params) {
		var obj = {
			term : ""
		};
		return obj;
	}, function dataResult(data) {
		return data;
	}, true);
}*/

function initEmailCombobox() {
	var link = 'quartz/getListEmailByCompanyId';
	searchCombobox('#emailTemplate', '', link, function data(params) {
		var obj = {
			key : params.term,
			id : $('#companyId').val(),
			isPaging : true
		};
		return obj;
	}, function dataResult(data) {
		return data;
	}, true);
}

function jobTypeOnChange(element) {
	var jobType = element.val();
	initJobClass($('#jobClass'));
	if (jobType == 'EXECUTE_SP' || jobType == 'REMIND_SP') {
		$('#divStoreName').show();
		$('#divStoreName *').prop('disabled', false);
		$('#divStoreName input').addClass('j-required');
	} else {
		$('#divStoreName').hide();
		$('#divStoreName *').prop('disabled', true);
		$('#divStoreName input').removeClass('j-required');
	}
	setJobClassItem();
}

function initJobClass(element) {
	var link = 'quartz/getListJobClass';
	searchCombobox(element, '', link, function data(params) {
		var obj = {
			term : params.term,
			id : $('#jobTypes').val(),
			isPaging : true
		};
		return obj;
	}, function dataResult(data) {
		return data;
	}, true);
}

function setJobClassItem() {
	$('#jobClass option').each(function() {
		var val = $('#jobClassName').val();
		var text = $(this).val();
		if (text === val) {
			$(this).prop("selected", "selected");
			return;
		}
	});
}

function ValidateEmail(inputText) {
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	if (inputText.value.match(mailformat)) {
		document.form1.text1.focus();
		return true;
	} else {
		alert("You have entered an invalid email address!");
		document.form1.text1.focus();
		return false;
	}
}
