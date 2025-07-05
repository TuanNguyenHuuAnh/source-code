var PAGE_URL = CUSTOMER_ALIAS + '/news';

$(document).ready(function($) {
	// viewBtnSave
	viewBtnSave();
	
	$('#btnSave').unbind('click').bind('click', function() {
		var url = PAGE_URL + "/list/sort";
		
		$.ajax({
			type : "POST",
			url : BASE_URL + url,
			contentType : "application/json",
			data : getDataJson(),
			success : function(data) {
				var content = $(data).find('.body-content');
				if (content.length == 0){ // nếu cập nhật thành công --> trả về message
					$('html,body').animate({
					scrollTop: $("#viewMessage").offset().top - 130}, 0);
					$("#viewMessage").html(data);
					
				}else{ // xử lý nếu không có quyền
					$(".main_content").ready(function() {
						$('.content').scrollTop();
					});

					$(".main_content").html(content);
					var urlPage = $(data).find('#url').val();
					if (urlPage != null && urlPage != '') {
						window.history.pushState('', '', BASE_URL + urlPage);
					}
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
	$('#linkList').unbind('click').bind('click', function(event) {
		event.preventDefault();
		
		popupConfirmWithButtons(MSG_BACK_CONFIRM, LIST_BUTTON_CONFIRM_DELETE, function(result) {
			if (result) {
				var url = BASE_URL + PAGE_URL + "/list";
		
				// Redirect to page list
				ajaxRedirect(url);
			}
		})
	});
	
	$("#newsTypeId, #categoryId").select2({ allowClear : true});
	
	// type select box on change
	$("#newsTypeId").change(function() {
		newTypeChange(this);
	});
	
	$('#categoryId').change(function() {
		newsCategoryChange(this);
    });
});

/**
 * getDataJson
 * @returns
 */
function getDataJson() {
	
	var result = {};
	
	var sortOderList = [];

	// nếu là tin tức về hdbank thì sort giảm, ngược lại thì sort tăng
	if (CUSTOMER_ALIAS = 'about-hdbank') {
		$('#tb-sort-list tbody tr').each(function(key, val) {
			sortOderList.push({
				"objectId" : $(this).data("row-id"),
				"sortValue" : key + 1,
			});
		});
	} else {
		
		$('#tb-sort-list tbody tr').each(function(key, val) {
			sortOderList.push({
				"objectId" : $(this).data("row-id"),
				"sortValue" : key + 1,
			});
		});
	}
	
	result["sortOderList"] = sortOderList;
	result["newsTypeId"] = $("#newsTypeId").val();
	result["categoryId"] = $("#categoryId").val();
	
	return JSON.stringify(result);
}

function newTypeChange(element) {
	var typeId = $(element).val();
	
	$.ajax({
		type : "GET",
		url : BASE_URL + PAGE_URL + "/type/change",
		data : {
			"typeId" : typeId,
		},
		success : function(data) {
			$('#categoryId').find('option:not(:first)').remove();
			
			$.each(jQuery.parseJSON(data), function(key, val) {
				$('#categoryId').append('<option value="' + val.id + '">' + val.label + '</option>');
			});
			
			$("#tableList").find('tbody').html('');
		}
	});
}

/**
 * setListSort
 */
function setListSort() {
	var categoryId = $("#categoryId").find(":selected").val();
	
	var typeId = $("#typeId").val();
	
	$.ajax({
		type : "POST",
		url : BASE_URL + PAGE_URL + "/search/list/sort",
		data : {
			"typeId" : typeId,
			"categoryId" : categoryId,
			"custTypeId" : $("#customerIdHidd").val(),
			"promotion"  : $("#promotion").is(":checked"),
			"headlines"  : $("#headlines").is(":checked")
			
		},
		success : function(data) {
			$("#tableList").html(data);
		}
	});
}

function viewBtnSave() {
	if ($('#tb-sort-list tbody tr').length > 0) {
		$('#btnSave').removeAttr("disabled");
	} else {
		$('#btnSave').attr("disabled", "disabled");
	}
}

/**
 * product category sub select box on change
 */
function newsCategoryChange(element) {
	var newsCategoryId = $(element).val();
	var newsTypeId = $('#newsTypeId').val();
	
	var condition = {};
	condition["newsTypeId"] = newsTypeId;
	condition["categoryId"] = newsCategoryId;
	
	let url = PAGE_URL + '/search/list/sort';
	
	ajaxSearch(url, condition, 'tableList', element, null);
}