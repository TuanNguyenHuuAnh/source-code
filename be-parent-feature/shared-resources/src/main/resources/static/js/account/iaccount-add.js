$(document).ready(function() {
	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : GROUP_LABEL,
		search : true
	});
	
	GROUP_LIST_DISABLE.forEach(function(element) {
		var checkBoxGroup = $("input[type='checkbox'][value = " + element.id + "]");
		var checked = checkBoxGroup.is(':checked');
		if(checked) {
			checkBoxGroup.prop("disabled", true);
		} else {
			if(!IS_ADMIN) {
				checkBoxGroup.parents('li').remove();
			}
		}
	});
	
});

