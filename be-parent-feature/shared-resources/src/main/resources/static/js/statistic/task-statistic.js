$(document).ready(function() {
	
	$('div.bg-dashboard-1').click(function(event) {
		create($(this), event);
	});
	
	$("div.bg-dashboard-2").click(function(){
		redirectToDoList('1');
	});
	
	$("div.bg-dashboard-3").click(function(){
		redirectToDoList('2');
	});
	
	$("div.bg-dashboard-4").click(function(){
		redirectToDoList('3');
	});
	$("div.bg-dashboard-5").click(function(){
		redirectToDoList('4');
	});
	
	$("#approved-doc-statistic").click(function(){	
		redirectDocList('2');
	});
	
	$("#rejected-doc-statistic").click(function(){
		redirectDocList('3');
	});
	
	$("#unapproved-doc-statistic").click(function(){
		redirectDocList('1');
	});
	
	$("#return-doc-statistic").click(function(){
		redirectDocList('4');
	});
	
	$("#completed-doc-statistic").click(function(){
		redirectDocList('5');
	});
	
	$("#all-doc-statistic").click(function(){
		redirectDocList('');
	});
	
});

function create() {
    var url = BASE_URL + "document/detail?";
    
    var form = -1;
    url += "formId=" + form;
    var pageMode = '1';
    url += "&mode=" + pageMode;
    
    ajaxRedirect(url);
}

function redirectDocList(statusCode){
	 var displayCompanyId = null == $("#companyId").val() ? '' : $("#companyId").val();
	 var displayOrgId = null == $("#orgId").val() ? '' : $("#orgId").val();
	 var companyId = '';
	 var orgId = '';
	 
	 var url = BASE_URL + "document/list?";
	 
	 var fromDate = $("#fromDate").val();
	 url += "fromDate=" + fromDate;
	 var toDate = $("#toDate").val();
	 url += "&toDate=" + toDate;
	 
	 if(displayCompanyId === undefined){
		 companyId = $("#companyIdHidden").val();
	 }else{
		 companyId = displayCompanyId;
	 }
	 
	 url += "&companyId=" + companyId;
	 
	 if(displayOrgId === undefined){
		 orgId = $("#orgIdHidden").val();
	 }else{
		 orgId = displayOrgId;
	 }
	 
	 url += "&orgId=" + orgId;
	 
	 url += "&statusCode=" + statusCode;
	 
	 ajaxRedirect(url);
}

function redirectToDoList(statusCode){
	 var url = BASE_URL + "todo/list";
	 /*var obj = {
				'statusCode' : statusCode
		}
	 $.ajax({
			type : "POST",
			url : url,
			data : obj,
			beforeSend: blockbg(),
			success : function(data) {
				var content = $(data).find('.body-content');
				$(".main_content").html(content);
				window.history.pushState('', '', url);
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
	});*/
	switch(statusCode) {
		case "2":
	    	url += "/outcome";
	    	break;
	  	case "3":
	    	url += "/draft";
	    	break;
	 	case "4":
	    	url += "/related";
	    	break;
	  	default:
	    	break;
	}
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
	ajaxRedirect(url);
}

