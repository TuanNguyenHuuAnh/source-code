$(document).ready(function() {
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	
	$("#linkList").on("click", function(event) {
		var url = BASE_URL + "jsla/list";
		ajaxRedirect(url);
	});

	$('#btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var values = $("#form-j-edit").serializeArray();
			
			var id = $("#id").val();
			var url = "jsla/edit?id=" + id;
			ajaxSubmit(url, values, event);
		}
	});
	
	$("#btn-cancel").on("click", function(event) {
		var url = BASE_URL + "jsla/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$(".j-stf-delete").on('click', function() {
		var row = $(this).parents("tr");
		var id = row.data("sla-step-id");
		
		popupConfirm( MSG_DEL_CONFIRM, function(result) {
			if( result ){
				var url = BASE_URL + "jsla/delete-step";
				var data = {
					"id" 	: id,
					"slaId" : $("#id").val()
				}
				makePostRequest( url, data );
			}	
		});
	});
	
	$('.j-stf-edit').on('click', function(event) {
		var row = $(this).parents("tr");
		var id = row.data("sla-step-id");
		
		var url = BASE_URL + "jsla/alert-setting?slaStepId="+id;
		ajaxRedirect(url);
	});
});
