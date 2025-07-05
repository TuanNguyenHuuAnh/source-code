$(document).ready(function() {
	if ($('#fieldSearch').val() != null && $('#fieldSearch').val() != '') {
		initialState = 'expanded';
	}

	// datatable load
	/*
	$("#tableList").datatables({
		url : BASE_URL + 'menu/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	*/

	// multiple select search
	$('select[multiple]').multiselect({
		columns: 1,
		placeholder: SEARCH_LABEL,
		search: true
	});

	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});

	// on click add
	//	$("#add").on("click", function(event) {
	//		var url = BASE_URL + "document-management/edit";
	//		// Redirect to page add
	//		ajaxRedirect(url);
	//	});

	$(".ms-options-wrap input").unbind('keypress').bind('keypress', function(event) {
		if (event.keyCode == 13) {
			onClickSearch(this, event);
		}
	});

	$('#fieldSearch').unbind('keypress').bind('keypress', function(event) {
		if (event.keyCode == 13) {
			onClickSearch(this, event);
		}
	});

	// Init company
	searchCombobox('.select-company', SEARCH_LABEL, 'company/get-company',
		function data(params) {
			var obj = {
				keySearch: params.term,
				isPaging: true
			};
			return obj;
		}, function dataResult(data) {
			return data;
		}, false);

});
/**
 * onClickSearch
 * @param element
 * @param event
 * @returns
 */
function onClickSearch(element, event) {

	setDataSearchHidden();

	ajaxSearch("menu/ajaxList", setConditionSearch(), "tableList", element, event);
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidden").val();
	condition["fieldValues"] = $("#fieldValuesHidden").val();
	condition["companyId"] = $("#companyId").val();
	return condition;
}

function setDataSearchHidden() {
	$("#fieldSearchHidden").val($("#fieldSearch").val());
	$("#fieldValuesHidden").val($("select[name=fieldValues]").val());
}
/**
 * editMenu
 * @param element
 * @param event
 * @returns
 */
function editMenu(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("menu-id");
	var url = BASE_URL + "menu/edit?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}
/**
 * detail
 * @param element
 * @param event
 * @returns
 */
function detail(element, event) {
	event.preventDefault();

	// Prepare data
	var row = $(element).parents("tr");
	var id = row.data("menu-id");
	var url = BASE_URL + "menu/detail?id=" + id;

	// Redirect to page detail
	ajaxRedirect(url);
}

function createFolderDocumentManagement(element, event) {
	event.preventDefault();

	// Prepare data
	let row = $(element).parents("tr");
	let documentId = row.data("document-id");

	$('#parentIdCreateFolder').val(documentId);

	$('#documentManagementModal').modal('show');

	/*	popupConfirm(MSG_DEL_CONFIRM, function(result) {
			if (result) {
				let url = "document-management/delete";
				let condition = {
					"id" : documentId,
					"name": 
				}
	
				ajaxSubmit(url, condition, event);
			}
		});*/
}

function uploadFileDocumentManagement(element, event) {
	event.preventDefault();

	// Prepare data
	let row = $(element).parents("tr");
	let documentId = row.data("document-id");

	$('#parentIdUploadFile').val(documentId);

	$('#documentManagementUploadFileModal').modal('show');

	/*	popupConfirm(MSG_DEL_CONFIRM, function(result) {
			if (result) {
				let url = "document-management/delete";
				let condition = {
					"id" : documentId,
					"name": 
				}
	
				ajaxSubmit(url, condition, event);
			}
		});*/
}

function deleteDocumentManagement(element, event) {
	event.preventDefault();

	// Prepare data
	let row = $(element).parents("tr");
	let documentId = row.data("document-id");

	popupConfirm(MSG_DEL_CONFIRM, function(result) {
		if (result) {
			$('.alert').hide();
			blockbg();

			let url = "document-management/delete";
			let condition = {
				"id": documentId
			}

			// ajaxSubmit(url, condition, event);

			$.ajax({
				type : "POST",
				url : BASE_URL + url,
				data: condition,
				success: function(data) {
					$(".title-head").after($(data).find('.alert'));
		
					let oldTree = $('#documentManagementTreeGrid').parent().clone();
					let newTree = $(data).find('#documentManagementTreeGrid');
		
					$('#documentManagementTreeGrid').parent().html(newTree);
		
					createDocumentManagementTree('collapsed');
		
					Array.from($(oldTree).find('[data-document-id]')).forEach((element, index) => {
						try {
							let odlDocumentId = $(element).data("document-id");
							let newElement = newTree.find(`[data-document-id=${odlDocumentId}]`);
		
							if ($(element).hasClass('treegrid-expanded')) {
								$(`[data-document-id=${odlDocumentId}]`).treegrid('expand')
							}
						} catch(error) {}
					});
				},
				cache: false,
				// contentType: false,
				// processData: false
			});
		}
	});
}

$("form.ajax-form-submit").submit(function(e) {
	e.preventDefault();

	$('.alert').hide();
	blockbg();

	let formData = new FormData(this);

	// let id = $(this).find("[name='id']").val();

	$.ajax({
		url: $(this).attr("action"),
		type: 'POST',
		data: formData,
		success: function(data) {
			$(".title-head").after($(data).find('.alert'));

			let oldTree = $('#documentManagementTreeGrid').parent().clone();
			let newTree = $(data).find('#documentManagementTreeGrid');

			$('#documentManagementTreeGrid').parent().html(newTree);

			createDocumentManagementTree('collapsed');

			Array.from($(oldTree).find('[data-document-id]')).forEach((element, index) => {
				try {
					let odlDocumentId = $(element).data("document-id");
					let newElement = newTree.find(`[data-document-id=${odlDocumentId}]`);

					if ($(element).hasClass('treegrid-expanded')) {
						$(`[data-document-id=${odlDocumentId}]`).treegrid('expand')
					}
				} catch(error) {}
			});

		},
		cache: false,
		contentType: false,
		processData: false
	});

	unblockbg();
	
	$('.modal').modal('hide');
});

