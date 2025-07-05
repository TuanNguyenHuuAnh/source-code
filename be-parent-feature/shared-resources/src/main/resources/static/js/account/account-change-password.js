$(document).ready(function() {
	if(MESSAGE != null && MESSAGE.status == "success"){
		 $('#logOutModal').modal('show');
         $('#logOutModal').css('z-index',5000);
	}
	init();
	
	// on click saveDraft
	$('#btnSave').on('click', function(event) {
		event.preventDefault();
		if ($(".j-form-validate").valid()) {
			var url = "account/ajax-change-password";
			var condition = $(".j-form-validate").serialize();
			ajaxSubmit(url, condition, event);
		}
	});
	
	$('#newPassword').blur(function(e) {
        $(this).valid();
    });
	
	//
	$("#logout-changepass").on('click',function(){
		$("#logoutform-changepass").submit();
		
	});
});

function init(){
    var oldPass = ACC_DTO.oldPassword;
    var newPass = ACC_DTO.newPassword;
    var cfNewPass = ACC_DTO.confirmNewPassoword;
    $("#oldPassword").val(oldPass);
    $("#newPassword").val(newPass);
    $("#confirmNewPassoword").val(cfNewPass);
    
    jQuery.validator.addMethod("validPassword", function (value, element) {
        var re = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;

        // allow any non-whitespace characters as the host part
        return this.optional(element) || re.test(value);
    }, ERROR_REGEX);
}
