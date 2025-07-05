$(document).ready(function() {
	var initialState = 'collapsed';

	// datatable load
	/*	
	$("#tableList").datatables({
		url : BASE_URL + 'menu/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
	*/

	$(document).on("click", ".btn-delete", function( event ) {		
		deleteDocumentManagement(this, event);
	});

	$(document).on("click", ".btn-create-folder", function( event ) {		
		createFolderDocumentManagement(this, event);
	});

	$(document).on("click", ".btn-upload-file", function( event ) {		
		uploadFileDocumentManagement(this, event);
	});

	// on click edit
/*	$(".j-btn-edit").on("click", function(event) {
		editMenu(this, event);
	});*/

	// on click detail
/*	$(".j-btn-detail").on("click", function(event) {
		detail(this, event);
	});
*/

	createDocumentManagementTree('collapsed');

	// click button sort
/*	$('#btnSort').on('click', function(event){
		var url = BASE_URL + "menu/list/sort";
		// Redirect to page detail
		ajaxRedirect(url);
	});*/

});

function createDocumentManagementTree(state) {
	$('#documentManagementTreeGrid').treegrid({
		expanderExpandedClass	: 'glyphicon glyphicon-minus-sign',
		expanderCollapsedClass	: 'glyphicon glyphicon-plus-sign',
		'initialState'			: state,
		'treeColumn'			: 0
		});
}
