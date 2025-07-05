/**
 * unit-layout progress JS
 */
$(document).ready(function() {
	// config datatable -->
	$.extend( true, $.fn.dataTable.defaults, {
		"language": {
		  	"emptyTable"    :     	/*[[#{table.emptyTable.label}]]*/'',
		  	"lengthMenu"    :     	/*[[#{table.lengthMenu.label}]]*/'',   
		  	"info"          :     	/*[[#{table.info.label}]]*/'',
		  	"infoEmpty"     :     	"",
		  	"infoFiltered"  :     	"",
		  	"loadingRecords":     	/*[[#{table.loadingRecords.label}]]*/'',
			"processing"    :     	/*[[#{table.processing.label}]]*/'',
		  	"search"		:     	/*[[#{table.search.label}]]*/'' + ":",
		  	"zeroRecords"   :     	/*[[#{table.zeroRecords.label}]]*/'',
		  	"paginate": {
				"first"		:     	/*[[#{table.page.first}]]*/'',
				"last"		:		/*[[#{table.page.last}]]*/'',
				"next"		:       "»",
				"previous"	:   	"«"
	  		}       
		}
	});
          
	$.extend( true, $.fn.datatables.defaults, {
		"language": {
			"emptyTable"    :     	/*[[#{table.emptyTable.label}]]*/''
		}
	});
          
	$.extend( true, $.validator.messages, {
		required			: /*[[#{NotEmpty.java.lang.String}]]*/'',
		remote				: /*[[#{data.validate.remote}]]*/'',
		email				: /*[[#{data.validate.email}]]*/'',
		url					: /*[[#{data.validate.url}]]*/'',
		date				: /*[[#{data.validate.date}]]*/'',
		dateISO				: /*[[#{data.validate.dateISO}]]*/'',
		number				: /*[[#{data.validate.number}]]*/'',
		digits				: /*[[#{data.validate.digits}]]*/'',
		creditcard			:  /*[[#{data.validate.creditcard}]]*/'',
		equalTo				: /*[[#{data.validate.equalTo}]]*/'',
		accept				: /*[[#{data.validate.accept}]]*/'',
		maxlength			: jQuery.validator.format(/*[[#{data.validate.maxlength}]]*/''),
		minlength			: jQuery.validator.format(/*[[#{data.validate.minlength}]]*/''),
		rangelength			: jQuery.validator.format(/*[[#{Size.java.lang.String}]]*/''),
		range				: jQuery.validator.format(/*[[#{data.validate.range}]]*/''),
		max					: jQuery.validator.format(/*[[#{data.validate.max}]]*/''),
		min					: jQuery.validator.format(/*[[#{data.validate.min}]]*/''),
		code				: /*[[#{data.validate.code}]]*/'',
		currency			: /*[[#{data.validate.currency}]]*/'',
		multiemail			: /*[[#{data.validate.multiemail}]]*/'',
		specialCharacters	:/*[[#{data.validate.specialCharacters}]]*/'',
		phone				:/*[[#{data.validate.phone}]]*/'',
		domain				:/*[[#{data.validate.domain}]]*/''
	}); 
        	
	/*var pathname = window.location.pathname;
	$.ajax({
		type  : "GET",
		url   : BASE_URL + "menu/breadcrumb",
		data  : {'pathname' : pathname},
		complete: function (data) {
			$("#breadcrumb").html(data.responseText);
		}
	});*/

	$('.redirectToLogin').click(function() {
		disconnect();
		var functionCode = "LO0#S00_Login";
		var logSummary= "Session Timeout";
		var logType = "200";
		var logDetail = "Session Timeout";
		var username =  $(this).data("systemlogs-username");;
		// Write system logs
		writeSystemLogs(functionCode, logSummary, logType, logDetail, username);
		
		window.location = BASE_URL + "login";
	});
				
   /* Auto check timeout from client
   /* checkTimeout = setTimeout(checkSession, 61000); */
	
	function sessionTimeOutModal() {
		$('#sessionTimeOutModal').modal('show');
		$('#sessionTimeOutModal').css('z-index',5000);
	}

   function checkSession() {
       $.ajax({
           url: BASE_URL + "check-timeout",
           type: "GET",
           global : false,
           beforeSend: function(request){
              var token = $("meta[name='_csrf']").attr("content");
   		  	  var header = $("meta[name='_csrf_header']").attr("content");
   		  	  request.setRequestHeader(header, token);
           },
           success: function (result) {
           	//console.log(">>>>> check-timeout: " + result.message);
           },
           complete: function () {
               setupSessionTimeoutCheck();
           }
       });
   }

   function setupSessionTimeoutCheck() {
       clearTimeout(checkTimeout);
       checkTimeout = setTimeout(checkSession, 61000);
   }

   !function($){
     	$.ajaxSetup({
     		statusCode: {
              901: sessionTimeOutModal // Khi có mã lỗi 901 gọi hàm show dialog
     		}
     	});
    } (window.jQuery);

    /*window.addEventListener("popstate", function(e) {
    	window.location.href = location.href;
    });*/

});    
