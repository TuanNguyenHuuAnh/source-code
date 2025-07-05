$(document).ready(function() {
	// check all access
	$('.check-all-access').on('click', function() {
		var checked = $(this).is(':checked');
        $(this).closest('tbody').find('.check-access').prop('checked', checked);
    });
    
    $('#roleByFunctionTableId').tableFilterable({
        filters: [
            {
                 filterSelector: '#functionName',
                 event: 'keyup',
                 filterCallback: function($tr, filterValue) {
                     // filterValue is the content of the name input
                	 return  $tr.children('td[data-tfilter="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase().trim()) != -1;
                 }
             }
        ]
    });
    
    $('#roleServiceByFunctionTableId').tableFilterable({
        filters: [
            {
                 filterSelector: '#serviceName',
                 event: 'keyup',
                 filterCallback: function($tr, filterValue) {
                     // filterValue is the content of the name input
                	 return  $tr.children('td[data-tfilter="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase().trim()) != -1;
                 }
             }
        ]
    });
    checkAllRole();
    $("#business-code").change(function(e){
    	var val = $(this).val();
    	var functionName = $("#functionName").val();
    	$('#roleByFunctionTableId > tbody').find('tr').each(function(e){
    		var show = false;
    		if(val == '000'){
    			if(functionName != ''){
    				if($(this).children('td[data-tfilter="true"]').text().toLowerCase().indexOf(functionName.toLowerCase().trim()) == -1){
            			show = false;
            		}
    				else{
    					show = true;	
    				}
    			
    			}
    			else{
    				show = true;	
    			}
    			
    		}
    		else{
    			if($(this).children('td[data-tfilter-business-code="true"]').text().toLowerCase() == val.toLowerCase()){
    				if(functionName != ''){
        				if($(this).children('td[data-tfilter="true"]').text().toLowerCase().indexOf(functionName.toLowerCase().trim()) == -1){
                			show = false;
                		}
        				else{
        					show = true;
        				}
        			
        			}
        			else{
        				show = true;
        			}
        		}
    		}
    		$(this).toggle(show);
    	});
    	//    	
    	checkAllRole();
    	
    });
    
    function checkAllRole(){
    	var chkAllAccess = true;
    	$('#roleByFunctionTableId > tbody').find('tr').each(function(e){
			var display = $(this).css('display');
			if(display != 'none'){
				var chkaccess = $(this).find('.check-access').prop('checked');
				if(chkaccess == false){
					chkAllAccess = false;
				}
			}
		});
    	$("#roleByFunctionTableId .check-all-access").prop('checked',chkAllAccess);
    	
    	chkAllAccess = true;
    	$('#roleServiceByFunctionTableId > tbody').find('tr').each(function(e){
			var display = $(this).css('display');
			if(display != 'none'){
				var chkaccess = $(this).find('.check-access').prop('checked');
				if(chkaccess == false){
					chkAllAccess = false;
				}
			}
		});
    	$("#roleServiceByFunctionTableId .check-all-access").prop('checked',chkAllAccess);
    }
    
    // edit status authority
	$(".j-status-authority-edit").on("click", function(event){
		var url = BASE_URL + "authority/ajax/status/edit";
    	var id = "authority-status-edit";
    	
    	var data = [];
    	var row = $(this).parents("tr");
    	data.push({
            name : "roleId",
            value : row.find(".j-role-id").val()
        });
    	data.push({
            name : "itemId",
            value : row.find(".j-item-id").val()
        });
    	
    	openPopup( id, url, "GET", data, null );
	});
	
	$("#business-list").change(function(event){
    	var url = "jpm-process-deploy/ajax/getListProcessDeploy";
		var condition = {};
		condition["businessId"] = this.value;
		
		loadDataCombobox(url, condition, "#process-list", event);
		
		var businessId = this.value;
    	ajaxLoadAuthorityListByBusinessId( businessId);
    	
    });
	
	$("#process-list").change(function(e){
    	var val = $(this).val();
    	var businessId = $("#business-list").val(); 
    	ajaxLoadAuthorityListByProcessId(val);
    });
});

function ajaxLoadAuthorityListByProcessId(processId,businessId){
	var companyId = $('#companyIdBusiness').val();
	$.ajax({
	      type  : "GET",
	      url   : BASE_URL + "authority-process/ajaxList-role",
	      data  : {
	    	  "roleId" : $("#roleIdSelected").val(),
	    	  "processId" : processId,
	    	  "companyId" : companyId
	    	  
	      },
	      success: function( data ) {
	    	  $("#boxAuthorithyListProcess").html(data);
	      }
	});
}

function ajaxLoadAuthorityListByBusinessId(businessId){
	var companyId = $('#companyIdBusiness').val();
		$.ajax({
		      type  : "GET",
		      url   : BASE_URL + "authority-process/ajax/edit",
		      data  : {
		    	  "roleId" : $("#roleIdSelected").val(),
		    	  "businessId" : businessId,
		    	  "companyId" : companyId
		      },
		      success: function( data ) {
		          $("#boxRoleByFunction").html(data);
		      }
		});
}
