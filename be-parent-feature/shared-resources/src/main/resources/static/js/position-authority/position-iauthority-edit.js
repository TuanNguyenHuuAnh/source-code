$(document).ready(function() {
    $('#roleGrid').tableFilterable({
        filters: [
            {
                 filterSelector: '#roleName',
                 event: 'keyup',
                 filterCallback: function($tr, filterValue) {
                     // filterValue is the content of the name input
                     return  $tr.children('td[data-tfilter="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase().trim()) != -1;
                 }
             }
        ]
    });
});


