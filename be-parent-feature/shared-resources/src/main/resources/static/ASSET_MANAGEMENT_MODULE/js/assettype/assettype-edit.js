$(document).ready(function() {
	$('#chkActive').attr('checked', true);
	// set readonly code
	/*if ($("#assetTypeId").val() != "") {
		$("#countryCode").attr('readonly', 'readonly');
	}	*/
	//on click cancel
	$('#cancel').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "asset-type/list";

		// Redirect to page list
		ajaxRedirect(url);
	});

	//on click list
	$('#linkList').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "asset-type/list";

		// Redirect to page list
		ajaxRedirect(url);
	});

	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-type/edit";
		getValueUsingClass();
		// Redirect to page add
		ajaxRedirect(url);
	});
	// Post edit save job
	$('.btn-save').on('click', function(event) {
		getValueUsingClass();
		if ($(".j-form-validate").valid()) {
			var url = "asset-type/edit";
			var condition = $("#form-assettype-edit").serialize();

			ajaxSubmit(url, condition, event);			
		}
	});
	
	getListCostCenter();

});

function getValueUsingClass() {
	if ($('.chk').is(':not(:checked)')) {
		var text = 0;
		$("#activecheckbox").val(text);
	} else {
		var text = 1;
		$("#activecheckbox").val(text);
	}
}

function getListCostCenter(){
		$.ajax({
			url : BASE_URL + "asset-type/getListCostCenter",
			type : 'GET',
			global:false
		})
		.done(function(result){
			if(result != null && result.length > 0){		
				let html = '<option value="">--</option>';
				result.forEach(function(item){
					if (selected == item.text){
						 html += '<option value="'+item.text+'" selected>'+item.name+'</option>';
					}else{
						 html += '<option value="'+item.text+'">'+item.name+'</option>';
					}
				});

				$('#costCenter').append(html);
				$('#costCenter').select2({});
			}	
		})
	}