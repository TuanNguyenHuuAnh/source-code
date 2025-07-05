$(document).ready(function(){
	
	$('#sendMail').click(function(e){
		e.preventDefault();
		if ($(".j-form-validate").valid()) {
			//set number File Attach
			var numberAttachFile = $('#uploadAttach ul li').length;
			$('#numberAttachFile').val(numberAttachFile);
			//set id template 
			var templateId = $('#teamplateDoc').val();
			if(templateId != '' && templateId != null){
				$('#templateId').val(templateId);
			}
			//set content
			var content = $("#summernote").summernote("code");
			//table style - Begin - BaoHG
			var dupStyle = $('<div></div>').append(content).find('style').html();
			if (dupStyle === undefined) {
				content = '<style> table.unit-editor { border-collapse: collapse; border: 1px solid #ddd; width: 100% } table.unit-editor td { border: 1px solid #ddd; padding: 5px } </style>' + content;
			}
			var newCode = $('<div></div>').append(content).find('table').removeAttr("class").addClass('unit-editor').end().html();
			//table style - End - BaoHG
			$('#emailContent').val('<html><body>'+newCode+'</body></html>');
			
			var data = $('#frmSendEmail').serializeArray();
			//push set listAttachId
			listAttachId.forEach(function(element) {
				data.push({name: 'attachFileId', value: element});
			});
			$('#message-list').html('');
			ajaxSendMail(data);
		}
	});
	
	$(".lb-bcc").click(function(){
		$(this).addClass("hide");
		$(".bcc-hide").removeClass("hide");
	});
	
	$('#teamplateDoc').select2({
		placeholder : CHOOSE_TEMPLATE,
		minimumInputLength : 0,
		allowClear : true,
		ajax : {
			url : BASE_URL + "email/select2/template",
			dataType : 'json',
			type : "POST",
			quietMillis : 50,
			data : function(params) {
				 var query = {
					term : params.term,
					companyId : $('#companyIdSeclect').val()
				}
				return query;
			}
		}
	}).on('change',function(){
		  var templateId = $('#teamplateDoc').val();
		  ajaxGetTemplate(templateId);
	})
	
	//set uuidEmail for file attach
	$('#emailUuid').val($('#uuidEmail').val());
	//set summernot for textarea
	$('#summernote').summernote();
	
	//on change company
	$(".select-company").on('change', function(event) {
		$('#companyId').val($('#companyIdSeclect').val());
		$('#companyIdTemplate').val($('#companyIdSeclect').val());
		var emailUuid = $('#uuidEmail').val();
		clearAttach(emailUuid);
		totalSize = 0;
		listAttachId = [];
	});
	
	totalSize = 0;
	listAttachId = [];
})

function ajaxSendMail(data){
  var ajaxSend = $.ajax({
		          url: BASE_URL + "email/send-mail",
		          type: "POST",
		          data : data
		      	});
  
  ajaxSend.done(function(result){
	  // $('#message-list').html(result);
	  
	  let messageAlert = $(result).find(".alert");

	  $('#message-list').html(messageAlert);
  })
}

function ajaxGetTemplate(templateId){
	var ajaxSend = $.ajax({
			        url: BASE_URL + "email/template/template-detail",
			        type: "POST",
			        data : {'templateId' : templateId}
			        });
	ajaxSend.done(function(result){
		var templateContent = result.templateContent;
		$('#summernote').summernote('code', templateContent);
	})
}

function clearAttach(emailUuid){
	$.ajax({
        url: BASE_URL + "email/clear_acttach_file",
        type: "POST",
        data : {emailUuid : emailUuid},
        success: function (){
        	$('#companyIdAttach').val($('#companyIdSeclect').val());
    		$('#uploadAttach').find('li').remove();
        }
    });
}