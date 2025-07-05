$(document).ready(function() {
	//filter
	$('#stepCommentTableId').tableFilterable({
        filters: [
            {
                 filterSelector: '#stepName',
                 event: 'keyup',
                 filterCallback: function($tr, filterValue) {
                     // filterValue is the content of the name input
                     return  $tr.children('td[data-tfilter1="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase()) != -1;
                 }
             }
        ]
    });
	
	$('.check-all-comment').on('click', function() {
		var checked = $(this).is(':checked');
        $(this).closest('tbody').find('.check-comment').prop('checked', checked);
    });
	
	$('.check-all-opinion').on('click', function() {
		var checked = $(this).is(':checked');
        $(this).closest('tbody').find('.check-opinion').prop('checked', checked);
    });
	
	$('.check-comment, .check-opinion').on('click', function() {
		checkAll();
	});
});