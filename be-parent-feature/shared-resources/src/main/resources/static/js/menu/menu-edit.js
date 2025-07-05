$(document).ready(function() {
	
	$('.selectpicker').selectpicker();
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
	// set readonly code
	if ($("#menuId").val() != "") {
		$("#menuCode").attr('readonly', 'readonly');
	}
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "menu/list";
		
		// Redirect to page list
		ajaxRedirect(url);
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
	// Post edit savedraff menu
	$("#btn-save-draff").on('click', function() {		
		if ($(".j-form-validate").valid()) {			
			$("#action").val(false);	
			var url = "menu/edit";
			var condition = $("#form-menu-edit").serialize();

			ajaxSubmit(url, condition, event);
			
		}else{
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
		}		
	});
	// Post edit submit menu
	$("#btn-send-request").on('click', function() {
		if ($(".j-form-validate").valid()) {			
			$("#action").val(true);
			var url = "menu/edit";
			var condition = $("#form-menu-edit").serialize();

			ajaxSubmit(url, condition, event);
		}else{
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
		}			
	});
	// show tab if exists error
	showTabError(LANGUAGE_LIST);

	// On change menuType
	$("#menuType, #companyId").on('change', function() {
		$.ajax({
	        type  : "GET",
            contentType: "application/json; ",
	        url   : BASE_URL + "menu/changeMenuType",
	        data  : {
	        	"menuType" : $('#menuType').val(),
	        	"companyId" : $('#companyId').val()
	        },
	        success: function (data ) {
				if(data != ""){
		        	$('input[name=parentId]').val(data.rootId);
		        	$('#menuList').combotree('loadData', data.menuTree);
		        	//$('#itemId').val('0').trigger('change');
					if($('#menuList').val() != null && $('#menuList').val() != '') $('#menuList').combotree().trigger('change');
			     }
	        }
	    });
	});
	if($("#menuType").val() != null && $("#menuType").val() != '') $("#menuType").trigger('change');
	if($("#companyId").val() != null && $("#companyId").val() != '') $("#companyId").trigger('change');

	$(".btn-save").on('click', function(event) {
		if ($(".j-form-validate").valid()) {			
			$("#action").val(true);
			var url = "menu/edit";
			var condition = $("#form-menu-edit").serialize();

			ajaxSubmit(url, condition, event);
		}else{
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
		}			
	});
	
	// Init item
	searchCombobox('#itemId', '', 'item/get-item',
			function data(params) {
				var obj = {
					keySearch: params.term,
					companyId :$('#companyId').val(),
					functionTypes: ['1', '2'].toString(),
					subType: 'LINK',
					isPaging: false
				};
				return obj;
			}, function dataResult(data) {
				return data;
			}, false);
	
});