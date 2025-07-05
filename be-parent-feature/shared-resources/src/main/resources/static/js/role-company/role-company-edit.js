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
	
	// datatable load
	$("#companyList").datatables({
		url : BASE_URL + "role-company/get-list-company-for-role",
		type : 'GET',
		setData : setConditionSearch
	});
	
	//add 
	$('#id-add').on('click',function (event) {
		event.preventDefault();
		var rowRoleSelected = $("#roleTableId").find("tr.highlight");
		if(rowRoleSelected.length > 0) {
			$('#newRowCompany').removeClass('hidden');
		}
		/*if(rowRoleSelected.length > 0) {
			$.ajax({
			      type  : 'POST',	      
			      url   : BASE_URL + "role-company/add-role-for-company",
			      data  : {'roleCompanyJson': getAuthorityJson()},
			      success: function( data ) {	    	  
			          $("#companyList").html(data);
			      },
			      complete: function() {	    	  
			    	  //buildOrgAll();
			    	  loadData()
			      }
			});
		}*/
	});
	
	//Delete role for company
	$('.delete-role-for-company').on('click', function (event) {
		var companyId = $(this).parents("tr").data("companyid");
		var orgId = $(this).parents("tr").data("orgid");
		//if(id == 0){
		//	$('#newRowCompany').addClass('hidden');
		//	return;
		//}
		$('#msg').html("");
		event.preventDefault();
		//var index = $(this).parents("tr").find("#index").val();
	    //debugger;
		popupConfirm(MSG_DEL_CONFIRM, function(result) {
			if( result ){
				$.ajax({
			        type  : "POST",
			        url   : BASE_URL + "role-company/delete-role-for-company",
			        //data  : {'roleCompanyJson': getAuthorityJson(), "index" : index, "id": id},
			        data  : {'companyId': companyId,'orgId':orgId, roleId: $("#roleId").val()},
			        success: function( data ) {	    	  
				          $("#companyList").html(data);
				    },
				    complete: function() {	    	  
				    	//buildOrgAll();
				    	loadMessage(messageListEdit);
				    	//loadData();
				   }
			    });
			}
		});
	});
	
	loadData()
});

function setConditionSearch() {
	var condition = {};
	var rowRoleSelected = $("#roleTableId").find("tr.highlight");
	var roleId = rowRoleSelected.length > 0? rowRoleSelected.data("role-id"): null;
	condition["role_id"] = roleId;
	return condition;
}


