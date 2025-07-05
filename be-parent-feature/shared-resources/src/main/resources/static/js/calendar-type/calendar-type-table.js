var LIST_COLUMN = ["companyName", "code", "name", "workingHours", "description"];
var C_NAME = "CALENDAR_TYPE_SEARCH";

$(document).ready(function() {
	// Datatable
	$("#tableList").datatables({
		url: BASE_URL + 'calendar-type/ajaxList',
		type: 'POST',
		setData: setConditionSearch,
		"scrollX": true,
		"sScrollXInner": "100%"
	});


	$('#btnOrder').click(function(event) {
		order($(this), event);
	});

	// Search
	$('#btnSaveHidden').click(function(event) {
		search($(this), event);
	});

	// Delete
	$('.btn-delete').click(function(event) {
		deleteById(this, event);
	});

	//on click edit
	$(".btn-edit").on("click", function(event) {
		editById(this, event);
	});

	//Detail
	$(".btn-detail").on("click", function(event) {
		viewCalendarType(this, event);
	});

	// sắp xếp dữ liệu theo thứ tự tăng dần hoặc giảm dần
	sortColumn('#sortCompany', "companyName");
	sortColumn('#sortCode', "code");
	sortColumn('#sortCalendar', "name");
	sortColumn('#sortWorking', "workingHours");
	sortColumn('#sortDescription', "description");

	// Hiển thị icon sắp xếp
	showIconTableSort(C_NAME, 'idDivTable');

	//thay đổi thứ tự cột 
	$('.sort').sortable({
		cursor: 'move',
		axis: 'y',
		update: function(e, ui) {
			$(this).sortable('refresh');
			//debugger
			var lst = [];
			$('.sort').find('li > a > input').each(function(index, element) {
				console.log($(element).data('name'));
				lst.push($(element).data('name'));
			});
			LIST_COLUMN = lst;
			newSort();
		}

	});
});

//sắp xếp thứ tự tăng dần hoặc giảm dần
function sortColumn(idSort, name) {
	$(idSort).click(function(event) {
		arrange = !arrange;
		sort($(this), event, arrange, name);
	});
}

function sort(element, event, arrange, name) {
	var data = setConditionSearch();
	if (arrange) {
		// ajaxSearch("calendar-type/ajaxList?sort=" + name + ",asc", data, 'tableList', element, event);

		let urlSearch = constructSortAjax("calendar-type/ajaxList", name, LIST_COLUMN, "asc", C_NAME);
		ajaxSearch(urlSearch, data, 'tableList', element, event);
	} else {
		// ajaxSearch("calendar-type/ajaxList?sort=" + name + ",desc", data, 'tableList', element, event);

		$(element).removeClass("fa-sort");
		$(element).addClass("fa-angle-up");

		let urlSearch = constructSortAjax("calendar-type/ajaxList", name, LIST_COLUMN, "desc", C_NAME);
		ajaxSearch(urlSearch, data, 'tableList', element, event);
	}
}

function setConditionSearch() {
	var condition = {};

	//condition["sort"] = "company,desc";
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	condition["companyId"] = $("#companyId").val();
	if ($('#cbDes').is(':checked')) {
		condition["description"] = $("#description").val();
	}
	if ($('#cbCode').is(':checked')) {
		condition["code"] = $("#code").val();
	}
	if ($('#cbName').is(':checked')) {
		condition["name"] = $("#name").val();
	}
	if ($('#sName').is(':checked')) {
		condition["sName"] = 1;
	} else {
		condition["sName"] = 0;
	}
	if ($('#sWorkingHours').is(':checked')) {
		condition["sWorkingHours"] = 1;
	} else {
		condition["sWorkingHours"] = 0;
	}
	if ($('#sDescription').is(':checked')) {
		condition["sDescription"] = 1;
	} else {
		condition["sDescription"] = 0;
	}
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}

function deleteById(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("dto-id");

	popupConfirm(MSG_DEL_CONFIRM, function(result) {
		if (result) {
			var url = BASE_URL + "calendar-type/delete";
			var data = {
				"id": id,
				"search": setConditionSearch()
			}
			makePostRequest(url, data);
		}
	});
}

function editById(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("dto-id");
	var url = BASE_URL + "calendar-type/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}


function viewCalendarType(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("dto-id");
	var url = BASE_URL + "calendar-type/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

function newSort(event) {
	order($(this), event);
}

function order(element, event) {
	var data = setOrder();
	ajaxSearch("calendar-type/ajaxList", data, 'tableList', element, event);
}

function setOrder() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearch").val();
	condition["fieldValues"] = $("select[name=fieldValues]").val().join(",");
	condition["companyId"] = $("#companyId").val();
	if ($('#cbDes').is(':checked')) {
		condition["description"] = $("#description").val();
	}
	if ($('#cbCode').is(':checked')) {
		condition["code"] = $("#code").val();
	}
	if ($('#cbName').is(':checked')) {
		condition["name"] = $("#name").val();
	}
	if ($('#sName').is(':checked')) {
		condition["sName"] = 1;
	} else {
		condition["sName"] = 0;
	}
	if ($('#sWorkingHours').is(':checked')) {
		condition["sWorkingHours"] = 1;
	} else {
		condition["sWorkingHours"] = 0;
	}
	if ($('#sDescription').is(':checked')) {
		condition["sDescription"] = 1;
	} else {
		condition["sDescription"] = 0;
	}
	condition["orderColumn"] = LIST_COLUMN;
	return condition;
}

