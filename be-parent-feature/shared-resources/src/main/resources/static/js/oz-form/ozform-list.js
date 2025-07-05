$(document).ready(function($) {
	
	//on click button Register Service
	$("#btnRegisterService").on("click", function(event) {
		var url = BASE_URL + "api/v1/form/reports?companyId=145&companyName=Khoa";
		// Redirect to page detail
		ajaxRedirect(url);
	});
	
	//on click button Service List
	$("#btnServiceList").on("click", function(event) {
		var url = BASE_URL + "api/v1/form/servicelist?companyId=145&companyName=Khoa";
		// Redirect to page detail
		ajaxRedirect(url);
	});
	
	//on click button Service List
	$("#btnBack").on("click", function(event) {
		var url = BASE_URL + "account/list";
		// Redirect to page detail
		window.location = url;
	});
	
	//on click button Service List
	$("#btnBackToSeServiceList").on("click", function(event) {
		var url = BASE_URL + "api/v1/form/servicelist?companyId=145&companyName=Khoa";
		// Redirect to page detail
		window.location = url;
	});
	
	//on click edit
	$(".j-btn-edit").on("click", function(event) {
		// Prepare data
		var row = $(this).parents("tr");
		var reportPath = row.data("report-path");
		var reportName = row.data("report-name");
		
		var serviceName = row.data("service-name");
		var deviceType = row.data("device-type");
		
		
		var formTO = {
			formCategoryCode: "34",
			formCategoryName: "NewCategory",
			formId:0,
			companyId:"145" ,
			formName: serviceName,
			formFileName:reportPath,
			deviceType: deviceType,
			formOzCategory: "34"			
        }
		
		var url = BASE_URL + "api/v1/form/create";
		//var url = "api/v1/form/create";
		$.ajax({
		  method: "POST",
		  url:  url,
		  dataType: 'json',
          contentType: 'application/json',
          data: JSON.stringify(formTO)
		}).done(function( msg ) {	   
		    window.location = BASE_URL + msg.url ;
		}).fail(function (jqXHR, textStatus) {
			alert( "Error: " + textStatus );
		});      
		
	});
	
	
	$(".j-btn-detail").on("click", function(event) {
		// Prepare data
		var row = $(this).parents("tr");
		var reportPath = row.data("report-path");
		//var reportName = row.data("report-name");
		
		//var serviceName = row.data("service-name");
		//var deviceType = row.data("device-type");
		
		
		/*var formTO = {
			formCategoryCode: "34",
			formCategoryName: "NewCategory",
			formId:0,
			companyId:"145" ,
			formName: serviceName,
			formFileName:reportPath,
			deviceType: deviceType,
			formOzCategory: "34"			
        }*/
		
		var url = BASE_URL + "api/v1/form/viewer?" + "path=" + reportPath;
		
		window.location = url;

		/*//var url = "api/v1/form/create";
		$.ajax({
		  method: "GET",
		  url:  url
		}).done(function( msg ) {	   
		    //window.location = BASE_URL + msg.url ;
		}).fail(function (jqXHR, textStatus) {
			alert( "Error: " + textStatus );
		});*/      
		
	});

});
