$(document).ready(function(){
	
	$('#fieldSearch, #fieldValues').keypress(function(event){
        var keycode = (event.keyCode ? event.keyCode : event.which);
        if(keycode == '13'){
            onClickSearch(this, event);
        }
    });
    
	// init date picker
	/*$('.date').datepickerUnit({
		format: 'dd/mm/yyyy',
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : APP_LOCALE.toLowerCase(),
		todayHighlight : true,
		onRender : function(date) {
		}
	})*/
	fromDateToDateConfig();
	
	$('select[multiple]').multiselect({
	    columns: 1,
	    placeholder: SEARCH_LABEL,
	    search: true
	});
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
	
	$("#btnSearch").on("click", () => {
		console.log('hide alert 4');
		$('.alert').hide();
	});
	
	//highlight row clicked
	$('#tableList tr').on('click',function(){
        $("tr").removeClass("highlight");
        $(this).addClass("highlight");
	})
	
});



function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	condition["toDate"] = $("#toDate").val();
	condition["fromDate"] = $("#fromDate").val();
	condition["sendStatus"] = $("#sendStatus").val();
	condition["companyId"] = $("#companyId").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}

function onClickSearch(element, event) {

	setDataSearchHidden();
	console.log(setConditionSearch());
	ajaxSearch("email/ajaxList", setConditionSearch(), "tableList", element, event);
}

function reSend(emailId, $this){
	var checked = $($this.parentElement).find("input:checkbox").is(":checked");
	
	var ajaxDetail =  $.ajax({
        url: BASE_URL + "email/email-detail",
        type: "POST",
        data : {emailId : emailId}
    })
    .done(function(emailDto){
    	emailDto.sendDate = new Date(emailDto.sendDate);
    	emailDto.statusSendMail = 'Resend'; // status resend
    	if(checked){
    		emailDto.sendEmailType = 1; // send not save
    	}
    	$.ajax({
			 url: BASE_URL + "email/send",
	         type: "POST",
	         contentType : "application/json",
	         data : JSON.stringify(emailDto) 	
		 })
		 .then(function(response){
		 	 $("#btnSearch").trigger("click");
			 showMessage(response);	
			 goTopPage();
		 })	
	})
	
}

function cancel(emailId){
	var ajaxDetail =  $.ajax({
        url: BASE_URL + "email/email-detail",
        type: "POST",
        data : {emailId : emailId},
    })
    .done(function(emailDto){
    	emailDto.sendDate = new Date(emailDto.sendDate);
    	emailDto.sendEmailType = 3; //type send by batch
    	emailDto.statusSendMail = 'Cancel';//status Cancel
    	
    	$.ajax({
			 url: BASE_URL + "email/send",
	         type: "POST",
	         contentType : "application/json",
	         data : JSON.stringify(emailDto)
		 })
		 .then(function(response){
			
			 $("#btnSearch").trigger("click");
		 })	
	})
}

function showMessage(response){
	$('#send_msg').html('');
	if(response.status == 'Success'){
		$('#send_msg').html('');
		var msg_success = '<div class="alert alert-success alert-dismissible">'
			+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
			+'<div/>Email sent successfully</div>';
		$('#send_msg').html(msg_success);	
	}else{
		$('#send_msg').html('');
		var msg_fail = '<div class="alert alert-error alert-dismissible">'
			+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
			+'<div/>'+response.message+'</div>';
		$('#send_msg').html(msg_fail);
	}
	/*window.setTimeout(function() {
	    $("#send_msg").fadeTo(500, 0).slideUp(500, function(){
	        $(this).remove(); 
	    });
	}, 2000);*/
}
