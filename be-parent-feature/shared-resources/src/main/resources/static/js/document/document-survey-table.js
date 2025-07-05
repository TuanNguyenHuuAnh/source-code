$(document).ready(function(){
	//on click edit survey
    $('.j-btn-survey-edit').on("click", function (event) {
    	initPopupData(this, event);
    });	
});

function initPopupData(element, event) {
	event.preventDefault();
	var row = $(element).parents("tr");
	var id = row.data("survey-id") === undefined ? row.prevObject.attr('data-survey-id') : row.data("survey-id");
	if(id === undefined) {
		id = "0";
	}
	var comId = $("#companyId").val();
	var actTaskId = $('input[name="actTaskId"]').val();
	var url = BASE_URL + "document/survey?id=" + id + "&comId=" + comId + "&actTaskId=" + actTaskId;
	$.ajax({
		type : "GET",
		url : url,
		data : null,

		success : function(response) {
			$("#surveyPopup").html(response);
			$("#surveyPopupConfirm").modal();
			unblockbg();
		},
		error : function(e) {
			alert('Error: ' + e);
			unblockbg();
		}
	});
}