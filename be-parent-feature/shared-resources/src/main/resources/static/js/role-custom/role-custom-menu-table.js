$(document).ready(function() {
    $('#menuByFunctionTableId').tableFilterable({
        filters: [
            {
                 filterSelector: '#functionName',
                 event: 'keyup',
                 filterCallback: function($tr, filterValue) {
                	 return  $tr.children('td[data-tfilter="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase().trim()) != -1;	 
                 }
             }
        ]
    });
    
    //set checked defaul
    if($("input[name='menuId']:checked").length==0)
    	$("input[name='menuId']")[0].checked = true;
});


