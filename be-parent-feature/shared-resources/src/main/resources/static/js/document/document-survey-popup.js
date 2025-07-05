$(document).ready(function(){
	$('#btnSurvey').on('click', function(event) {
		if ($(".j-formSurveyPopup").valid()) {
			$('#btnSurvey').prop('disabled', 'disabled');
	    	var url = "document/saveSurvey";
	    	var values = $(".j-formSurveyPopup").serializeArray();
	    	var actTaskId = $('input[name="actTaskId"]').val();
	    	values.push({name:'actTaskId', value:actTaskId});
			$.ajax({
				type : "POST",
				url : BASE_URL + url,
				data : values,

				success : function(response) {
					$("#popupDetailSurvey").html(response);
				},
				error : function(e) {
					alert('Error: ' + e);
					unblockbg();
				}
			});
			$('#btnSurvey').prop('disabled', '');
		}
	});
	
	// Reload data when close popup
    $('#surveyPopupConfirm').on('hidden.bs.modal', function() {
    	var comId = $("#companyId").val();
    	var processId = $("#processDeployId").val();
    	var docId = $("#id").val();
    	var actId = $('#actTaskId').val();
    	var url = "document/loadSurveyTable?comId=" + comId + "&processId=" + processId + "&docId=" + docId + "&actTaskId=" + actId;
		$.ajax({
			type : "GET",
			url : BASE_URL + url,
			data : null,

			success : function(response) {
				$("#surveys").html(response);
			},
			error : function(e) {
				alert('Error: ' + e);
				unblockbg();
			}
		});
    });
    
    $('#actionId').on('change', function(event) {
    	var text = $("#actionId option:selected").text();
    	$('#actionName').val(text);
    });
	
});