
$(function(){
    
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "expense-type/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "expense-type/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "expense-type/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	

	// Post edit save
	$('#btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "expense-type/edit";
			var condition = $("#form-edit").serialize();
			ajaxSubmit(url, condition, event);
		}			
		goTopPage();
	});
	
	$("#parentCodeCB").select2();

	function goTopPage(){
		$("html, body").animate({ scrollTop: 0 }, "1");
	}
	
});

function loadParentCode(){
    var level = $('#level').val();
    var condition = {};
    condition['level'] = level;
    var url = BASE_URL + "expense-type/getParentCode";
    $.ajax({
        url : url,
        type: "POST",
        data : condition ,
        success: function(data, textStatus) {
            $('#parentCodeCB option').remove();
            var lstCode = JSON.parse(data);
            for(var i = 0; i < lstCode.length; i++){
                var option = "<option value='" + lstCode[i].id + "'>" + lstCode[i].text;
                option += "</option>";
                $('#parentCodeCB').append(option);
            }
        },
        error : function(xhr, textStatus, error) {
            console.log(xhr);
            console.log(textStatus);
            console.log(error);
        }
    });
}

function selectedAll(tag){
	if($(tag).is(":checked")) {
    	$("#dataTable input[type='checkbox']:not(:disabled)").prop('checked', true);
    }
    else {
    	$("#dataTable input[type='checkbox']").prop('checked', false);
    }
}

function viewMessageError(){
	$("#messagesDetailModal").modal('show');
}