$(document).ready(function(){
	$('select[multiple]').multiselect({
	    columns: 1
	});
	// Post edit save job
	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "system-config/save";
//			var condition = $("#form-config").serialize();
			var condition = new FormData($("#form-config")[0]);
			if(!(getCondidtionChecked() >= 3)){
				$('#filter').append('<label class="error">Passwords must use at least three of the four available character types: lowercase letters, uppercase letters, numbers, and symbols.</label>');
				return false;
			}
			var minLength = $('#minPasswordLength').val();
			if(minLength < 6){
				$('#minPass').append('<label class="error">Passwords must be at least 6 characters</label>');
				return false;
			}
			//ajaxSearch(url, condition, 'system-config-detail', $(this), event);
			//ajaxSubmit(url, condition, event)
			//goTopPage();
			var filesList = [], paramNames = [];
	    	$.each(filesImage, function( key, value ) {
	    		filesList.push(value);
	            paramNames.push(key);
	    	});
			console.log('filesList', filesList);
	    	condition["files"] = filesList;
	    	condition["paramName"] = paramNames;
	    	if(filesList.length > 0){
	    		file_upload.fileupload('send', condition);
	    	} else {
	    		$.ajax({
					type : "POST",
					enctype: 'multipart/form-data',
					url : BASE_URL + url,
					data : condition,
					processData: false,
			        contentType: false,
			        cache: false,
					success : function(data, textStatus, request) {
						getMessage($(this), event, data);
					},
					error : function(xhr, textStatus, error) {
						console.log(xhr);
						console.log(textStatus);
						console.log(error);
					}
				});
	    	}
			
		}			
	});
	
	$(".select-company").on('change', function(event) {
		var data = {};
		data["companyId"] = $(this).val();
		ajaxSearch("system-config/ajaxList", data, 'system-config-detail', $(this), event);
	});
	
	
	var checked = $('#usedECM').is(':checked');
	onCheckECM(checked);
	
	// check ecm
	$(document).on('click', '#usedECM', function() {
		var checked = $(this).is(':checked');
		onCheckECM(checked);
    });
	
	$(document).on('click', '#btnGenarateAuthenKey', function() {
		var key = randomString(27, null);
		$('#authenKey').val(key);
	});

	$('.input-toggle-icon').on('click', function(){
		$(this).siblings('i').toggleClass('fa-toggle-on fa-toggle-off');
//		
//		var checked = $(this).is(':checked');
//		removeRequired($(this).closest('.bg-content'), checked);
	});
	
	/*var pushNotif = $('#pushNotif').is(':checked');
	onCheckPushNotif(pushNotif);
	
	$(document).on('click', '#pushNotif', function() {
		var pushNotif = $('#pushNotif').is(':checked');
		onCheckPushNotif(pushNotif);
    });*/
	
	var flagFirebase = $('#flagFirebase').is(':checked');
	onCheckFirebase(flagFirebase);
	
	$(document).on('click', '#flagFirebase', function() {
		var flagFirebase = $('#flagFirebase').is(':checked');
		onCheckFirebase(flagFirebase);
    });
	
})

function getMessage(element, event, data) {
	var condition = setCondition(data.status, data.message, data.id)
	console.log('condition', data);
	ajaxSearch("system-config/config", condition, 'msg-error', element, event);
}
	
function setCondition(status, message, id) {
	var condition = {};
	condition["id"] = id;
	condition["message"] = message;
	condition["status"] = status;
	return condition;
}

function getCondidtionChecked(){
	return $('#search-filter').find(":selected").length;
}

function onCheckECM(checked) {
	if(!checked) {
    	$('#labelEcmRepositoryDocument').removeClass('required');
    	$('#ecmRepositoryDocument').removeClass('j-required');
    } else {
    	$('#labelEcmRepositoryDocument').addClass('required');
    	$('#ecmRepositoryDocument').addClass('j-required');
    }
}

function randomString(length, charSet) {
    charSet = charSet || 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var randomString = '';
    for (var i = 0; i < length; i++) {
        var randomPoz = Math.floor(Math.random() * charSet.length);
        randomString += charSet.substring(randomPoz,randomPoz+1);
    }
    return randomString+'=';
}

//function removeRequired(element, checked){
//	if(!checked) {
//		element.find('.form-group').each( function () {
//	        $(this).find("label").removeClass('required');
//	        $(this).find("input").removeClass('j-required');
//	    });
//	}else{
//		element.find('.form-group').each( function () {
//			$(this).find("label").addClass('required');
//			$(this).find("input").addClass('j-required');
//	    });
//	}
//}

function onCheckPushNotif(checked) {
	if(!checked) {
		$("#firebaseConfig input").removeClass('j-required');
		$("#firebaseConfig label").removeClass('required');
		$("#firebaseConfig label[class='error']").remove();
    	/*$('#labelFirebaseUrl').removeClass('required');
    	$('#firebaseUrl').removeClass('j-required');
    	$('#labelFireBaseAuthenKey').removeClass('required');
    	$('#fireBaseAuthenKey').removeClass('j-required');*/
    } else {
    	$("#firebaseConfig input").addClass('j-required');
		$("#firebaseConfig label").addClass('required');
    	/*$('#labelFirebaseUrl').addClass('required');
    	$('#firebaseUrl').addClass('j-required');
    	$('#labelFireBaseAuthenKey').addClass('required');
    	$('#fireBaseAuthenKey').addClass('j-required');*/
    }
}

function onCheckFirebase(checked){
	if(checked) {
		$("#firebaseConfig :input").addClass('j-required');
		$("#firebaseConfig").show();
    } else {
    	$("#firebaseConfig :input").removeClass('j-required');
    	$("#firebaseConfig").hide();
    }
}
