$(document).ready(function() {
	loadData();
	//-------------- Action list org for account ----------------------------------- 
	// Add a org for account
	$('#id-add-org-for-account').on('click', function() {
		$.ajax({
	        type  : "POST",
	        url   : BASE_URL + "rule/add-rule-org",
	        data  : {
	        	"businessId" : $(this).parents("#id-main-account").find("#businessId").val()
	        },
	        success: function (data , textStatus, resp ) {
	        	$("#id-org-for-account").text('');
	            $("#id-org-for-account").html(data);
	            $("#id-update-org-for-account").show();
	            $("#id-cancel-org-for-account").show();
	            $("#id-add-org-for-account").hide();
	        }
	    });
    });
	
	$('#id-cancel-org-for-account').on('click', function() {
		loadOrgForAccount($(this).parents("#id-main-account").find("#businessId").val());
	});
	
	// Delete a org for account
	$('.delete-org-for-account').on('click', function (event) {
		event.preventDefault();
	    // Prepare data
	    var accountDto = $(this).parents("#id-form-account").serializeArray();
	    var index = $(this).parents("tr").find("#index").val();
	    accountDto.push({name: 'index', value: index});
		popupConfirm(MSG_DEL_CONFIRM, function(result) {
			if( result ){
				$.ajax({
			        type  : "POST",
			        url   : BASE_URL + "rule/delete-rule-org",
			        data  : accountDto,
			        success: function (data , textStatus, resp ) {
			        	$("#id-org-for-account").html(data);
			        	$("#id-add-org-for-account").show();
			            $("#id-cancel-org-for-account").hide();
			        }
			    });
			}
		});
    });
	
	//on change position
	$('.list-position').on('change', function(event) {
		var index = $(this).parents("tr").find("#index").val();
		var text1 = this.options[this.selectedIndex].text;
		$('input[name="listRuleExceptionDto['+index+'].positionName"').val(text1);
	});
	
	// update 
	$('#id-update-org-for-account').unbind('click').bind('click', function (event) {
		var datas = $(this).parents("#id-form-account").serializeArray();
		var businessId = $("#businessId").val();
		datas.push({name:'businessId', value:businessId});
		$.ajax({
	        type  : "POST",
	        url   : BASE_URL + "rule/update-rule-org",
	        data  : datas, 
	        success: function (data , textStatus, resp ) {
	            //$("#id-org-for-account").text('');
	            $("#id-org-for-account").html(data);
	            $("#id-add-org-for-account").show();
	            $("#id-cancel-org-for-account").hide();
	        }
	    });
    });
	
	//init position
	searchCombobox('.list-position', '', 'position-tree/get-position-by-company',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $("#companyId").val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
	
});

function loadData(){
	for (i = 0; i< $('#result_size').val(); i++) {
		const prom = new Promise(function(resolve, reject){
			$('#listOrg'+i).combotree({
				editable : true,
				onChange:function(newValue){
					$(this).parents("tr").find("#org-for-account-id").val(newValue);
				},
				loader: function(param, success){
					if(param.id!=null){
						$.ajax({
							url: BASE_URL + 'organization/get-node',
							type  : "POST",
							data: param,
							dataType: 'json',
							global: false,
						    beforeSend: function(xhrObj) {
						    	var token = $("meta[name='_csrf']").attr("content");
							  	var header = $("meta[name='_csrf_header']").attr("content");
							  	xhrObj.setRequestHeader(header, token);
						    },
							success: function(data){
								success(data);
							}
						});
					}
				}
			});
			resolve(i);
		});
		
		prom.then(function(i){
			var companyId = $('#listOrg'+i).parents("#id-main-account").find("#companyId").val();
			var orgId = $('#listOrg'+i).parents("tr").find("#org-for-account-id").val();
			if(orgId == null || orgId == ''){
				orgId = 0;
			}
			 
			$.ajax({
		        type  : "POST",
		        url   : BASE_URL + "organization/build-tree-by-node-select",
		        data  : {
		        	"orgId" : orgId,
		        	"companyId" : companyId
		        },
		        global: false,
			    beforeSend: function(xhrObj) {
			    	var token = $("meta[name='_csrf']").attr("content");
				  	var header = $("meta[name='_csrf_header']").attr("content");
				  	xhrObj.setRequestHeader(header, token);
			    },
		        success: function (data) {
		        	if( data != null && data != '' ) {
		    			$('#listOrg'+i).combotree('loadData', jQuery.parseJSON(data));
		    		}
		        }
		    });
		})
		
	}
}