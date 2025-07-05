/**
 * hangnkm
 */

$(document).ready(function() {
	 $('#selector').cron({
        onChange: function() {
            $('#cron-val').text($(this).cron("value"));
        }
    });
    translateCron();
    

    $(".linkList").on("click", function(event) {
		var url = BASE_URL + "system/quartz-job/";
		// Redirect to page list
		ajaxRedirect(url);
	});
    
    
    $("#btnOK").on("click", function(event) {
    	$("#cron_expression").val($('#cron-val').text());
    	translateCron();
    });
    
    
});

function translateCron(){
	  var cron = $("#cron_expression").val();
	  //$('#crontooltip').attr("data-tip",cronstrue.toString(cron , { dayOfWeekStartIndexZero: false }));
	  $('#crontooltip').html(cronstrue.toString(cron , { dayOfWeekStartIndexZero: false }));
}

function saveCron() {
	removeMessage();
	$.ajax({
	  url: BASE_URL + 'system/quartz-job/edit',
	  cache: false,
	  type: "POST",
	  data: $("#form-quarts").serialize(),
	      async: true,
	      success: function(data) {
	    	  appendMessage(data.status, data.message);
	      }
	    });
}

function showCronExpressionMakerModal()
{
   $('#cronExpressionMakerModal').modal('show');
   $('#cronExpressionMakerModal').css('z-index',5000);
}


function appendMessage(status, message) {
	$("#message-list")
		.append(
				'<div class="alert alert-'
						+ status
						+ ' alert-dismissible">'
						+ '<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
						+ '<div>' + message + '</div>' + '</div>');
}

function removeMessage() {
	$("#message-list").empty();
}
