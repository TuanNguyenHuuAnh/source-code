$(document).ready(function() {
	//on click list
	$("#listBooking").on("click", function(event) {
		var url = BASE_URL + "contact/email/list";
		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	$("#btn-cancel").on("click", function(event) {
		var url = BASE_URL + "contact/email/list";
		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	//on click edit
	$("#editBooking").on("click", function(event) {
		var id = $("#id").val();
		var url = BASE_URL + "contact/email/edit?id=" + id;
		// Redirect to page edit
		ajaxRedirect(url);
	});
	
	//on click delete
	$("#deleteBooking").on("click", function(event) {
		// Redirect to page delete
		deleteBooking(event);
	});
	
	$("#cancel").on("click", function(event) {
		var url = BASE_URL + "contact/email/list";
		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	$('#btn-email-processing-update').on("click", function(event){
		updateEmailProcessed(event);
	});
	
	$('#btn-email-done-update').on("click", function(event){
		updateEmailDone(event);
	});
	
	$('#btn-email-reject').on("click", function(event){
		rejectEmail(event);
	});
	
	$('#selComment').on('change', function(){
    	var selectedComment = $(this).val();
    	if(selectedComment === '0'){
    		$('#textComment').attr('style', 'display:block');
    	}else{
    		$('#textComment').attr('style', 'display:none');
    	}
	 })
});

/**
 * 
 * @param element
 * @param event
 * @returns
 */
function deleteBooking(event) {
	event.preventDefault();
	// Prepare data
	var id = $("#id").val();

	popupConfirmWithButtons(MSG_DEL_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
		if (result) {
			var url = "contact/email/detail/delete?id=" + id;
//			var values = $('#form-contact-booking-detail').serialize();
			// Redirect to page detail
			ajaxSubmit(url, values, event);	
		}
	});
}

function updateEmailProcessed(event) {
	event.preventDefault();

	var id = $('#id').val();
	var url = "contact/email/process?id=" + id;
	var values = $('#form-contact-email-detail').serialize();
	
	ajaxSubmit(url, values, event);	
}



function updateEmailDone(event) {
	event.preventDefault();

	var id = $('#id').val();
	var url = "contact/email/done?id=" + id;
	var values = $('#form-contact-email-detail').serialize();
	ajaxSubmit(url, values, event);
}

function rejectEmail(event) {
	event.preventDefault();
	
	var id = $('#id').val();
	var url = "contact/email/reject?id=" + id;
	var values = $('#form-contact-email-detail').serialize();
	ajaxSubmit(url, values, event);		
}

function setConditionSearch() {
	
	var condition = {};
	condition["fromDate"] = $("#fromDate").val();
	condition["toDate"] = $("#toDate").val();
	condition["processingStatus"] = $("#processingStatus").val();
	condition["service"] = $("#service").val();
	condition["motive"] = $("#motive").val();
	condition["emailSubject"] = $("#emailSubject").val();
	condition["fullName"] = $("#fullName").val();
	condition["customerId"] = $("#customerId").val();
	return condition;
}