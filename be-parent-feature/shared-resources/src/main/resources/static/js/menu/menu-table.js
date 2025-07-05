$(document).ready(function() {
	var initialState = 'collapsed';
	// datatable load
	$("#tableList").datatables({
		url : BASE_URL + 'menu/ajaxList',
		type : 'POST',
		setData : setConditionSearch
	});
    
    $(".menudelete").on("click", function( event ) {		
    	deleteMenu(this, event);
		
	});

	// on click edit
	$(".j-btn-edit").on("click", function(event) {
		editMenu(this, event);
	});

	// on click detail
	$(".j-btn-detail").on("click", function(event) {
		detail(this, event);
	});
	
	$('#menuTreeGrid').treegrid({
        expanderExpandedClass  : 'glyphicon glyphicon-minus-sign',
        expanderCollapsedClass : 'glyphicon glyphicon-plus-sign',
        'initialState'         :  initialState,
        'treeColumn'           : 1
    });
	
	// click button sort
	$('#btnSort').on('click', function(event){
		var url = BASE_URL + "menu/list/sort";
		// Redirect to page detail
		ajaxRedirect(url);
	});
	
});
