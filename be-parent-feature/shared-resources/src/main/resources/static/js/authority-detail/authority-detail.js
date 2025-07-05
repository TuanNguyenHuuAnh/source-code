$(document).ready(function() {

	$('#fieldSearch, #fieldValues').keypress(function(event) {
		var keycode = (event.keyCode ? event.keyCode : event.which);
		if (keycode == '13') {
			onClickSearch(this, event);
		}
	});
	// init date picker
	$('.date').datepickerUnit({
		format: DATE_FORMAT,
		viewMode: "days",
		minViewMode: "days",
		autoclose: true,
		language: APP_LOCALE.toLowerCase(),
		todayHighlight: true,
		onRender: function(date) {
		}
	})

	// multiple select search
	$('select[multiple]').multiselect({
		columns: 1,
		placeholder: SEARCH_LABEL,
		search: true
	});

	var $form = $(".j-formSearch");
	$('#btnSearch').click(function(e) {
		onClickSearch(this, e);
	});

	// on click popup excel
	$("#tableList").on("click", "#btnDownload", function(event) {
		event.preventDefault();
		var linkExport = $('#linkExport').val();
		doExportExcelWithToken(linkExport);
	});

	// enter input
	$("#passExportPopup").keyup(function(event) {
		if (event.keyCode === 13) {
			$("#btnExport").click();
		}
	});

	$('#btnExport').click(function() {
		var linkExport = $('#linkExport').val();
		doExportExcelWithToken(linkExport);
	});
});

function onClickSearch(element, event) {
	setDataSearchHidden();
	ajaxSearch("report-auth/list", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $('#fieldSearchHidden').val();
	condition["fieldValues"] = $('#fieldValuesHidden').val();
	condition["companyId"] = $('#companyId').val();
	condition["page"] = 1;
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}
function doExportExcelWithToken(linkExport) {
	var timerToken;
	var tokenid = getTokenExport();
	timerToken = setInterval(function() {
		unblockbg();
		clearInterval(timerToken);
	}, 1000);

	$("#token").val(tokenid);
	$("#passExport").val($("#passExportPopup").val());
	$("#formSearch").attr("action", BASE_URL + linkExport);
	$("#formSearch").submit();
	blockbg();
}
function getTokenExport() {
	var date = new Date();
	var day = date.getDate();
	var month = date.getMonth();
	var year = date.getFullYear();
	var hour = date.getHours();
	var minute = date.getMinutes();
	var second = date.getSeconds();
	var millisecond = date.getMilliseconds();
	var id = day + '' + month + '' + year + '' + hour + '' + minute + ''
		+ second + '' + millisecond;
	return id;
}
