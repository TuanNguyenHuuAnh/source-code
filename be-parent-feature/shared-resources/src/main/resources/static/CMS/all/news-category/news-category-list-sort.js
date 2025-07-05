var PAGE_URL = CUSTOMER_ALIAS + '/news-category';

$(document).ready(function($) {
	viewBtnSave('tb-sort-list');
	
	$("#newsTypeId").unbind('change').on('change', function() {
		var url = PAGE_URL + "/sort/type/change";
		
		$.ajax({
			type : "POST",
			url : BASE_URL + url,
			data : {"newsTypeId": $("#newsTypeId").val()},
			success : function(data) {
				$("#tableList").html(data);
				// viewBtnSave
				viewBtnSave();
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});
	}).trigger('change');
	
	$('#btnSave').unbind('click').bind('click', function(e) {
		var url = PAGE_URL + "/list/sort";
		
		$.ajax({
			type : "POST",
			url : BASE_URL + url,
			contentType : "application/json",
			data : getDataJson(),
			success : function(data) {
				var content = $(data).find('.body-content');
				$(".main_content").ready(function() {
					$('.content').scrollTop();
				});

				$(".main_content").html(content);
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
		
		popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				var url = BASE_URL + PAGE_URL + "/list";
		
				// Redirect to page list
				ajaxRedirect(url);
			}
		})
	});
	
	$("#newsTypeId").select2({allowClear : true});
});

/**
 * getDataSubmit
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
	result["newsTypeId"] = $('#newsTypeId').val();
	return JSON.stringify(result);
}

function viewBtnSave() {
	if ($('#tb-sort-list tbody tr').length > 0) {
		$('#btnSave').removeAttr("disabled");
	} else {
		$('#btnSave').attr("disabled", "disabled");
	}
}
