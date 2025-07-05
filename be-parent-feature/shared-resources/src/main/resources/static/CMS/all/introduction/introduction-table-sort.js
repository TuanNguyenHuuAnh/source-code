$(document).ready(function($) {
	$('#btnSave').unbind('click').bind('click', function() {
		var url = "introduction/list/sort";
		applySortOrder('tb-sort-list');
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
		var url = BASE_URL + "introduction/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$('#categoryId').on("change", function(){
		$.ajax({
            type  : "POST",
            data  : {'categoryId' : $("#categoryId").val()},
            url   : BASE_URL + "introduction/changeCategorySort",
            success: function (data) {
            	
            	console.log(data);
            	
            	$("#tableList").html(data);
            },
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
        }); 
	});
	
	
	if( CATEGORY_LIST != null && CATEGORY_LIST != '' ) {
		$('#categoryList').combotree('loadData', jQuery.parseJSON(CATEGORY_LIST));
	}
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

function applySortOrder(tableId){
	var table = document.getElementById(tableId);
	var sortValue = 0;
	$('#' + tableId + ' tr').each(function() {
		$(this).find('.sort-value').val(sortValue);
		sortValue = sortValue + 1;
	});
}