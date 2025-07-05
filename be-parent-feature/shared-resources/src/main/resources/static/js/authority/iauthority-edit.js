$(document).ready(function() {
    $('#roleByFunctionTableId').tableFilterable({
        filters: [
            {
                 filterSelector: '#functionName',
                 event: 'keyup',
                 filterCallback: function($tr, filterValue) {
                     // filterValue is the content of the name input
                	 /*var val = $("#business-code").val();
                	 if(val == '000'){
                		 return  $tr.children('td[data-tfilter="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase().trim()) != -1;	 
                	 }
                	 else{
                		 var indexOf = $tr.children('td[data-tfilter="true"]').text().toLowerCase().indexOf(filterValue.toLowerCase().trim());
                		 var menuType = $tr.children('td[data-tfilter-business-code="true"]').text();
                		 return (indexOf != -1 && menuType == val);
                	 }*/
                	 
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
    	var chkAllEdit = true;
    	var chkAllDisp = true;
    	$('#roleByFunctionTableId > tbody').find('tr').each(function(e){
			var display = $(this).css('display');
			if(display != 'none'){
				var chkaccess = $(this).find('.check-access').prop('checked');
				if(chkaccess == false){
					chkAllAccess = false;
				}
				var chkedit = $(this).find('.check-edit').prop('checked');
				if(chkedit == false){
					chkAllEdit = false;
				}
				var chkdisp = $(this).find('.check-disp').prop('checked');
				if(chkdisp == false){
					chkAllDisp = false;
				}
			}			
		});
    	$(".check-all-access").prop('checked',chkAllAccess);
    	$(".check-all-disp").prop('checked',chkAllDisp);
    	$(".check-all-edit").prop('checked',chkAllEdit);
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
});


