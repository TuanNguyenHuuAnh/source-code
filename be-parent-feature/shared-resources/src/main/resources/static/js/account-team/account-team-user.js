$(document).ready(function() {
	
	 searchCombobox('#memberAdds', '', 'account/find-select2-by-multiple-conditions',
	    	    function data(params) {
	    	        var obj = {
	    	            companyId: $('#companyId').val(),
	    	        	key: params.term,
	    	            isPaging: true
	    	        };
	    	        return obj;
	    	    }, function dataResult(data) {
	    	        return data;
	    	    }, true);

	$('.btnSearch').unbind().bind('click', function(event) {
		onClickSearch(this, event);
	});
	
	$('.btnAdd').unbind().bind('click', function(event) {
		var userIds = $('#memberAdds').val();
		if(userIds.length > 0){
			var condition = {};
			condition["userIds"] = userIds;
			condition["keySearch"] = $("#keySearch").val();
			condition["companyId"] = $("#companyId").val();
			condition["teamId"] = $("#teamId").val();
			
			$.ajax({
				type : "POST",
				url : BASE_URL + "account-team/add-user-for-team",
				data : condition,
				success : function(data, textStatus, request) {
					$("#userList").html(data);
					$('#memberAdds').val(null);
				},
				error : function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
				}
			});
		}
	});
	
    
    
    $("#chkall").on("click", function () {
		var chk = $(this).prop('checked');
		$('#listAccount > tbody').find('tr').each(function(e){
			var display = $(this).css('display');
			if(display != 'none'){
				$(this).find('.check-delete').prop('checked', chk);
			}
			
		});
	});
    
// Datatable
    $("#userList").datatables({
        url: BASE_URL + 'account-team/get-user-for-team',
        type: 'GET',
        setData: setConditionSearchUser,
        "scrollX": true,
        "sScrollXInner": "100%"
    });

    //delete user
	$('.btnDelete').on('click', function(event) {
		var userIds = getListDeleted();
		if(userIds.length > 0){
			popupConfirm(MSG_DEL_CONFIRM, function (result) {
		        if (result) {
					var condition = {};
					condition["userIds[]"] = userIds;
					condition["keySearch"] = $("#keySearch").val();
					condition["companyId"] = $("#companyId").val();
					condition["teamId"] = $("#teamId").val();
					$.ajax({
						type : "POST",
						url : BASE_URL + "account-team/delete-user-for-team",
						data : condition,
						success : function(data, textStatus, request) {
							$("#userList").html(data);
						},
						error : function(xhr, textStatus, error) {
							console.log(xhr);
							console.log(textStatus);
							console.log(error);
						}
					});
		        }
		    });
		}
		else{
			popupConfirm(MSG_DEL_ERROR_EMPTY, function (result) {
			});
		}
	});
});


