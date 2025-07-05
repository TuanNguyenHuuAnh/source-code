$(document).ready(function($) {
	// multiple select search
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	//getListCategory();
});
