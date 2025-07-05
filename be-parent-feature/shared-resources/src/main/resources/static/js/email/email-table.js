$(document).ready(function () {
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'email/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
});

function callModal(emailId, $this){
	$("#listAttach").html('');
	var ajaxDetail =  $.ajax({
        url: BASE_URL + "email/email-detail",
		//contentType: "application/json",
		//dataType: "JSON",
        type: "POST",
        data : {emailId : emailId},
		/*success: function (data){
			console.log(data);
			debugger;
		},
		error: function (e){
			debugger;
		}*/
    })
    .done(function(emailDto){
    	//console.log(emailDto);
		//console.log(htmlDecode(emailDto.emailContent))
    	//load content to modal email
    	$("#modalTo").val(emailDto.toString);
    	$("#modalCC").val(emailDto.ccString);
    	$("#modalBCC").val(emailDto.bccString);
    	$("#modalSubject").val(emailDto.subject);
    	$("#modalTo").prop('readonly', true);
    	$("#modalCC").prop('readonly', true);
    	$("#modalBCC").prop('readonly', true);
    	$("#modalSubject").prop('readonly', true);
    	$('#modalContent').summernote('code', htmlDecode(emailDto.emailContent));
    	$('#modalContent').summernote();
    	$('#modalContent').summernote('disable');
    	//list file attach
    	var listAtt = emailDto.listAttach;
    	if(listAtt != null){
    		var fileAtt = '';
    		$.each( listAtt, function( index, value ){
    			fileAtt += '  <a><i class="fa fa-paperclip" aria-hidden="true"></i>'+value.fileName+'</a>';
    		});
    		$("#listAttach").html(fileAtt);
    	}
    	//show modal
    	$('#detailModal').modal('show');
        $('#detailModal').css('z-index',5000);
    })
	
}