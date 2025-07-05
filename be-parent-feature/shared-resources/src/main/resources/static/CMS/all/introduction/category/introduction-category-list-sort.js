$(document).ready(function($) {
	$('#btnSave').unbind('click').bind('click', function() {
		var url = "introduction-category/list/sort";

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
		var url = BASE_URL + "introduction-category/list";

		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
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
	result["codeSearch"] = $("#code").val();
	result["nameSearch"] = $("#name").val();
	result["statusSearch"] = $("#status").val(); 
	result["enabledSearch"] = $("#enabled").val();
	result["typeOfMainSearch"] = $("#typeOfMain").val();
	result["pictureIntroductionSearch"] = $("#pictureIntroduction").val();

	return JSON.stringify(result);
}

function setConditionSearch() {
	var condition = {};
	condition["code"] = $("#code").val();
	condition["name"] = $("#name").val();
	condition["status"] = $("#status").val(); 
	condition["enabled"] = $("#enabled").val();
	condition["typeOfMain"] = $("#typeOfMain").val();
	condition["pictureIntroduction"] = $("#pictureIntroduction").val();
	return condition;
}
