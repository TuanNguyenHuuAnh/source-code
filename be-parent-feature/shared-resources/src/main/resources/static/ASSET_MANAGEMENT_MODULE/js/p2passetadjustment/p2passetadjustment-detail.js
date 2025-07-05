
$(function(){
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-adjust/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-adjust/list";
		// Redirect to page list
		ajaxRedirect(url);
	});

if (EDIT_DTO.prId != null){
		
		$("#refPr").attr('href', BASE_URL + 'pr/detail?id=' + EDIT_DTO.prId);
		$("#refPr").attr('target', '_blank');
		
		$('#prNo').css('color', '#3c8dbc');
		$('#prNo').css('cursor', 'pointer');
		
		$("#prId").val(EDIT_DTO.prId);
	}
	
	if (EDIT_DTO.poId != null){
		
		$("#refPo").attr('href', BASE_URL + 'po/detail?id=' + EDIT_DTO.poId);
		$("#refPo").attr('target', '_blank');
		
		$('#poNo').css('color', '#3c8dbc');
		$('#poNo').css('cursor', 'pointer');
		
		$("#poId").val(EDIT_DTO.poId);
	}
	
	if (EDIT_DTO.paymentId != null){
		var url = 'payment-ge';
		if (EDIT_DTO.paymentType == 0){
			url = 'payment-pr-po';
		}
		$("#refPayment").attr('href', BASE_URL + url + '/detail?id=' + EDIT_DTO.paymentId);
		$("#refPayment").attr('target', '_blank');
		
		$('#paymentNo').css('color', '#3c8dbc');
		$('#paymentNo').css('cursor', 'pointer');
		
		$("#paymentId").val(EDIT_DTO.paymentId);
	}
});
