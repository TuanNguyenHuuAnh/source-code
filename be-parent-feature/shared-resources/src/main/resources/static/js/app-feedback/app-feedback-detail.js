var popupMainFie = null;

$(document).ready(function () {

	$('.date').datepickerUnit({
		format: DATE_FORMAT,
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : APP_LOCALE.toLowerCase(),
		todayHighlight : true,
		onRender : function(date) {
		}
	});
	
    // Back
    $('#btnBack').click(function () {
        back();
    });

    // Add
    $('#btnCreate').click(function(e) {
    	create();
    });

    // Save
    $('.btnSave').click(function () {
    	if($(".j-form-validate").valid()){
			var values = $("#feedback-detail").serializeArray();
			console.log(values);
			var id = $("#id").val();
			var url = "feedback/detail?id=" + id;
			ajaxSubmit(url, values, event);
		}
		
    });
    $("#btnCancel").on("click", function(event) {
		var url = BASE_URL + "feedback/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
    

});

function back() {
    var url = BASE_URL + "feedback/list";
    
    ajaxRedirect(url);
}

function create() {
    var url = BASE_URL + "feedback/detail";
  
    ajaxRedirect(url);
}

