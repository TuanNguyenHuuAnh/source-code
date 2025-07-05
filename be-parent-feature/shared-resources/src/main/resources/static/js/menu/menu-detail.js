$(document).ready(function() {
	$('#menuList').combotree({
		editable : true,
		onChange: function (dataValue) { 
			$.ajax({
                type  : "POST",
                data  : {'parentId' : dataValue},
                url   : BASE_URL + "menu/changeParent",
                complete: function (data) {
                	$("#sort").val(data.responseText);
                }
            }); 
        } 
    });
	
	if( MENU_LIST != null && MENU_LIST != '' ) {
		$('#menuList').combotree('loadData', jQuery.parseJSON(MENU_LIST));
	}
	// Post approve job
	$(document).on('click', '#btn-approve', function() {
		
		if ($(".j-form-validate").valid()) {
			$("#action").val(true);
			
			var url = "menu/detail";
			var condition = $("#form-menu-approve").serialize();
			
			ajaxSubmit(url, condition, event);
		}		
				
	});
	// Post reject job		
	$(document).on('click', '#btn-return', function() {		
		
		if ($(".j-form-validate").valid()) {	
			$("#action").val(false);
			
			var url = "menu/detail";
			var condition = $("#form-menu-approve").serialize();
			
			ajaxSubmit(url, condition, event);
		}			
	});	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "menu/list";

		// Redirect to page list
		ajaxRedirect(url);
	});	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "menu/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});
	// on click cancel
	$('#cancel').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "menu/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
});