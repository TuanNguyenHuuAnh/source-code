var PAGE_URL = CUSTOMER_ALIAS + "/document";

$(document).ready(function($) {
	
	viewBtnSave('tb-sort-list');
	
	$('#btnSave').unbind('click').bind('click', function() {
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
				
				var urlPage = $(data).find('#url').val();
				if (urlPage != null && urlPage != '') {
					window.history.pushState('', '', BASE_URL + urlPage);
				}
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
	
	$("#categoryId").unbind('change').bind('change', function() {
		initTblListData();
	});
	
	$("#categoryId").select2({ allowClear : true });
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
	result["categoryId"] = $("#categoryId").val();
	return JSON.stringify(result);
}

function initTblListData(){
	$.ajax({
		type : "POST",
		url : BASE_URL + PAGE_URL + "/ajax/list/sort",
		data : 
			{
				"categoryId" : $('#categoryId').val()
			},
		success : function(data) {
			$("#tableList").html(data);
		},
		error : function(xhr, textStatus, error) {
			console.log(xhr);
			console.log(textStatus);
			console.log(error);
		}
	});
}