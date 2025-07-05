$(document).ready(function() {
	// set readonly code
	if ($("#cityId").val() != "") {
		$("#cityCode").attr('readonly', 'readonly');
	}	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "city/list";
		
		// Redirect to page list
		//ajaxRedirect(url);
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "city/list";

		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "city/edit";
		// Redirect to page add
		ajaxRedirectWithCondition(url, setConditionSearch());
	});	
	// Post edit save job
	$('#btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "city/edit";
			var condition = $("#form-city-edit").serialize();

			ajaxSubmit(url, condition, event);
			
		}else{
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
		}			
	});
	
	function setConditionSearch() {
		var condition = {};
		condition["fieldSearch"] = $("#fieldSearch").val();
		condition["fieldValues"] = $("#fieldValues").val();
		return condition;
	}
	
	// show tab if exists error
	showTabError(LANGUAGE_LIST);
	
});



function checkPhoneRegEx(){
	var phoneCode = $("#phoneCode").val();
	if(phoneCode.trim() == '' || phoneCode.trim() != ''){
		$(".phoneCodeError").remove();
	}
	var pattern = new RegExp("^[(0-9)+()-]*$");
	var res = pattern.test(phoneCode);
	if(!res && phoneCode.trim() != ''){
		$("#phoneCode").after("<span class='help-block phoneCodeError' style='color: red;'>(+)(-)(0-9)</span>");
	}
}

function checkZipRegEx(){
	var zipCode = $("#zipCode").val();
	if(zipCode.trim() == '' || zipCode.trim() != ''){
		$(".zipCodeError").remove();
	}
	var pattern = new RegExp("^[(0-9)\,;-]*$");
	var res = pattern.test(zipCode);
	if(!res && zipCode.trim() != ''){
		$("#zipCode").after("<span class='help-block zipCodeError' style='color: red;'>(0-9)(-)(,)(;)</span>");
	}
}
function checkCarRegEx(){
	var carCode = $("#carCode").val();
	if(carCode.trim() == '' || carCode.trim() != ''){
		$(".carCodeError").remove();
	}
	var pattern = new RegExp("^[(0-9A-Z)\,;-]*$");
	var res = pattern.test(carCode);
	if(!res && carCode.trim() != ''){
		$("#carCode").after("<span class='help-block carCodeError' style='color: red;'>(A-Z)(0-9)(-)(,)(;)</span>");
	}
}
function checkShipRegEx(){
	var shipCode = $("#shipCode").val();
	if(shipCode.trim() == '' || shipCode.trim() != ''){
		$(".shipCodeError").remove();
	}
	var pattern = new RegExp("^[a-zA-Z]*$");
	var res = pattern.test(shipCode);
	if(!res && shipCode.trim() != ''){
		$("#shipCode").after("<span class='help-block shipCodeError' style='color: red;'>(a-z)(A-Z)</span>");
	}
}