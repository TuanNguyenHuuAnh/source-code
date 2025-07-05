var PARAM_STATUS = "status";
var STATUS_INCOMING = "incoming";
var STATUS_OUTCOME = "outcome";
var STATUS_DRAFT = "draft";
var STATUS_RELATED = "related";
	
$(document).ready(function() {
	
	$('div.bg-dashboard-1').click(function(event) {
		create($(this), event);
	});
	
	$('div.bg-dashboard-2').click(function(event) {
		editParamStatus(STATUS_INCOMING);
		activeMenuByStatus(STATUS_INCOMING);
		$("#statusCode").val(1).trigger('change');
		$('#btnSearch').trigger('click');
	});
	
	$('div.bg-dashboard-3').click(function(event) {
		editParamStatus(STATUS_OUTCOME);
		activeMenuByStatus(STATUS_OUTCOME);
		$("#statusCode").val(2).trigger('change');
		$('#btnSearch').trigger('click');
	});
	
	$('div.bg-dashboard-4').click(function(event) {
		editParamStatus(STATUS_DRAFT);
		activeMenuByStatus(STATUS_DRAFT);
		$("#statusCode").val(3).trigger('change');
		$('#btnSearch').trigger('click');
	});
	
	$('div.bg-dashboard-5').click(function(event) {
		editParamStatus(STATUS_RELATED);
		activeMenuByStatus(STATUS_RELATED);
		$("#statusCode").val(4).trigger('change');
		$('#btnSearch').trigger('click');
	});
	
});

function buildUrlByStatus(status) {
	return "todo/list/" + status;
}

function activeMenuByStatus(status) {
	var url = (status === STATUS_INCOMING) ? "todo/list" : buildUrlByStatus(status);
	$("#sidebar-menu").find('a').each(function() {
		var href = $(this).prop('href');
		if (href && href.indexOf(url) === href.length - url.length) {
			$(this).addClass('active');
			$(this).parent('li').addClass('menu-open');
		} else {
			$(this).removeClass('active');
			$(this).parent('li').removeClass('menu-open');
		}
	});
}

function editParamStatus(status) {
	var url = BASE_URL + buildUrlByStatus(status);
	window.history.pushState(null, null, url);
}

function create() {
    var url = BASE_URL + "document/detail?";
    
    var form = -1;
    url += "formId=" + form;
    var pageMode = '1';
    url += "&mode=" + pageMode;
    
    ajaxRedirect(url);
}