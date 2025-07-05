var PAGE_URL = CUSTOMER_ALIAS + '/document-category';

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
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});
	});
	
	$("#listTree").unbind('change').bind('change', function() {
		let value = $("input[name=parentId]").val();
		
		initTblListData(value);
	});
	
	//on click list
	$('#linkList').unbind().bind('click', function(event) {
		event.preventDefault();
		
		popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				var url = BASE_URL + PAGE_URL + "/list";
		
				// Redirect to page list
				ajaxRedirect(url);
			}
		})
	});
	
	$("select[name$='parentId']").select2({ allowClear : true});
	
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
	result["parentId"] = $('input[name=parentId]').val();
	return JSON.stringify(result);
}

function initTblListData(dataValue){
	$.ajax({
		type : "POST",
		url : BASE_URL + PAGE_URL + "/ajax/list/sort",
		data : 
			{
				"parentId" : dataValue
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
