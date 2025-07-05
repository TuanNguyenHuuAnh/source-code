$(document).ready(function($) {
	
	$('#menuList').combotree({
		editable : true,
		onChange: function (dataValue) { 
			$.ajax({
                type  : "POST",
                data  : {'parentId' : dataValue},
                url   : BASE_URL + "menu/search/list/sort",
           
                success :  function(data) {
                	$("#tableList").html(data);
                },
                complete : function(data) {
                	//unblockbg();
                }
            }); 
        }
        
    });
	
	if( MENU_LIST != null && MENU_LIST != '' ) {
		$('#menuList').combotree('loadData', jQuery.parseJSON(MENU_LIST));
	}
	
	$('#btnSave').unbind('click').bind('click', function() {
		var url = "menu/list/sort";
		
		$.ajax({
			type : "POST",
			url : BASE_URL + url,
			contentType : "application/json",
			data : getDataJson(),
			success : function(data) {
				$('html,body').animate({
			        scrollTop: $("#viewMessage").offset().top - 130}, 0);
				$("#viewMessage").html(data);
				
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});
	});
	
	//on click list
	$('#linkList').on('click', function(event) {
		event.preventDefault();
		var url = BASE_URL + "menu/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// viewBtnSave
	viewBtnSave();
	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "menu/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
});

/**
 * getDataJson
 * @returns
 */
function getDataJson() {
	
	var result = {};
	
	var sortOderList = [];

	$('#tb-sort-list tbody tr').each(function(key, val) {

		sortOderList.push({
			"objectId" : $(this).data("row-id"),
			"sortValue" : key + 1,
		});
	});
	
	result["sortOderList"] = sortOderList;

	return JSON.stringify(result);
}

function viewBtnSave() {
	if ($('#tb-sort-list tbody tr').length > 0) {
		$('#btnSave').removeAttr("disabled");
	} else {
		$('#btnSave').attr("disabled", "disabled");
	}
}
