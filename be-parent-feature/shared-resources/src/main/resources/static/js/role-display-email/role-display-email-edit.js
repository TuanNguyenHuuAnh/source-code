$(document).ready(function() {
    /*$('#companyTableId').tableFilterable({
    	filters: [
        	{
                filterSelector: '#companyName1',
                event: 'keyup',
                filterCallback: function($tr, filterValue) {
                    // filterValue is the content of the name input
                    return  $tr.children('td[data-tfilter="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase()) != -1;
                }
            }
        ]
    });*/
	
	//add 
	$('#id-add').on('click',function (event) {
		event.preventDefault();
		var rowRoleSelected = $("#roleTableId").find("tr.highlight");
		if(rowRoleSelected.length > 0) {
			$.ajax({
			      type  : 'POST',	      
			      url   : BASE_URL + "role-display-email/add-role-for-display-email",
			      data  : {'roleCompanyJson': getAuthorityJson()},
			      success: function( data ) {	    	  
			          $("#companyList").html(data);
			      },
			      complete: function() {	    	  
			    	  //buildOrgAll();
			    	  loadData()
			      }
			});
		}
	});
	
	//Delete role for company
	$('.delete-role-for-company').on('click', function (event) {
		console.log("zo");
		$('#msg').html("");
		event.preventDefault();
		var index = $(this).parents("tr").find("#index").val();
	    //debugger;
		popupConfirm(MSG_DEL_CONFIRM, function(result) {
			if( result ){
				$.ajax({
			        type  : "POST",
			        url   : BASE_URL + "role-display-email/delete-role-for-display-email",
			        data  : {'roleCompanyJson': getAuthorityJson(), "index" : index},
			        success: function( data ) {	    	  
				          $("#companyList").html(data);
				    },
				    complete: function() {	    	  
				    	//buildOrgAll();
				    	loadMessage(messageListEdit);
				    	loadData();
				   }
			    });
			}
		});
	});
});


