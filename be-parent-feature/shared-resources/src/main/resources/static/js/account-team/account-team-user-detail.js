$(document).ready(function() {
	 searchCombobox('#memberAdds', '', 'account/ajax-get-list-user-by-key',
	    	    function data(params) {
	    	        var obj = {
	    	            companyIds: $('#companyId').val(),
	    	        	key: params.term,
	    	            isPaging: true
	    	        };
	    	        return obj;
	    	    }, function dataResult(data) {
	    	        return data;
	    	    }, true);
	// Datatable
    $("#userList").datatables({
        url: BASE_URL + 'account-team/get-user-for-team-detail',
        type: 'GET',
        setData: setConditionSearchUser,
        "scrollX": true,
        "sScrollXInner": "100%"
    });
    
});


