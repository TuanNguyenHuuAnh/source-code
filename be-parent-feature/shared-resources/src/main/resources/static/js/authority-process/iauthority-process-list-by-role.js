$(document).ready(function() {
	// check all access
	$('.check-all-access').on('click', function() {
		var checked = $(this).is(':checked');
        $(this).closest('tbody').find('.check-access').prop('checked', checked);
    });
});