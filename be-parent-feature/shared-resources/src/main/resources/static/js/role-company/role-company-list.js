$(document).ready(function() {
	loadRoleList();
	
	//on change company
	$(".select-company").on('change', function(event) {
		loadRoleList();
	});
	
	// Save list role-company
	$("#btnSaveAutho").unbind().bind('click', function (e) {
		e.preventDefault();
		ajaxSaveAuthority();
    });
	
});

 function ajaxLoadCompanyByRole(rowRoleSelected){
	var roleId = rowRoleSelected.length > 0? rowRoleSelected.data("role-id"): null;
	//blockbg();
	$.ajax({
		  //async : false,
	      type  : "GET",
	      /*global: false,
	      beforeSend: function(xhrObj) {
		    	var token = $("meta[name='_csrf']").attr("content");
			  	var header = $("meta[name='_csrf_header']").attr("content");
			  	xhrObj.setRequestHeader(header, token);
	      },*/
	      url   : BASE_URL + "role-company/get-list-company-for-role",
	      data  : {
	    	  "role_id" : roleId
	      },
	      success: function( data ) {	    	  
	          $("#companyList").html(data);
	      }/*,
	      complete: function() {	    	  
	    	  loadData()
	      }*/
	      
	});
	
}

 function ajaxSaveAuthority(){
	var rowRoleSelected = $("#roleTableId").find("tr.highlight");
	if(rowRoleSelected.length > 0) {
		//blockbg();
		$(".j-message-alert").html("");	
		$.ajax({
		      type  : 'POST',	      
		      url   : BASE_URL + "role-company/post-list-company-for-role",
		      data  : {'roleCompanyJson': getAuthorityJson(), roleId: $("#roleId").val()},
		      /*success: function( data ) {
		    	  $(".j-message-alert").prepend( data );
		      },
		      complete: function(data){
		    	  if($('.alert-danger').length==0){
			  		  ajaxLoadCompanyByRole(rowRoleSelected);
		    	  }
		    	  unblockbg();
		      }*/
		    success: function( data ) {	    	  
		          $("#companyList").html(data);
		    },
		    complete: function() {	    	  
		    	//buildOrgAll();
		    	loadMessage(messageListEdit);
		    	//loadData();
		    	//unblockbg();
		   }
		});
	}
}
 
 /**
  * getAuthorityJson
  * @returns
  */
 function getAuthorityJson() {
	 var resultList = {};
	 var authority = [];

	 $('#companyTableId tbody tr').each(function(key, val) {
		 if(key == 0 && !($(val).hasClass('hidden'))) {
			 authority.push({
				 "id" 					:   0,
				 "companyId" 			:   $("#companyId0").val(),			
				 "flgChecked" 			:	true,
				 "orgId" 			 	:   $("#listOrg0").combotree('getText')== ""? null : $("#listOrg0").combotree('getValue'),
				 "isAdmin" 			    :   $("#isAdmin").is(":checked"),
				 "roleId"				:   $("#roleId").val()
			 })
		 } else if(key != 0) {
			 authority.push({
				 "companyId" 			:   $(val).data("companyid"),
				 "orgId" 				:   $(val).data("orgid"),
				 "isAdmin" 			    :   $("#isAdmin" + key).is(":checked")
			  });
		 }
	 });

	resultList["roleId"] = parseFloat($("#roleId").val());
	resultList["data"] = authority;
	return JSON.stringify(resultList);
}
 
 function ajaxLoadAuthority(companyId) {
		return $.ajax({
			  //async : false,
		      type  : "GET",
		      url   : BASE_URL + "role-company/ajax/list",
		      data  : {
		    	  "companyId" : companyId
		      },
		      success: function( data ) {
		          $("#roleList").html(data);
		      }
		});
}
function loadRoleList(){
		$('#msg').html("");
		$('#companyList').html("");
		var companyId = $('#companyId').val();
		if(companyId != undefined){
			ajaxLoadAuthority(companyId).done(function() {
				var rowRoleSelected = $("#roleTableId").find("tr.highlight");
				ajaxLoadCompanyByRole(rowRoleSelected);
				//show msg
				loadMessage(messageList);
			});
		}
}
/*function buildOrgAll(){
	for (i = 0; i< $('#result_size').val(); i++) {
		$('#listOrg'+i).combotree({
			editable : true,
	    });
		
		companyId = $('#companyId'+i).val();
		buildOrg(companyId, i);
		
		$('#companyId'+i).on('change', function(event) {
			companyId = this.value;
			index = $(this).parents("tr").find("#index").val();
			$('#listOrg'+index).combotree('setValue', '');
		});
	}
}

function buildOrg(companyId, index){
	var ORG_LIST = null;
	$.ajax({
		  async : false,
	      type  : "POST",
	      url   : BASE_URL + "organization/get_org_by_company",
	      data  : {
	    	  "companyId" : companyId
	      },
	      success: function( data ) {
	    	  ORG_LIST = data;
	      }
	});
	
	if( ORG_LIST != null && ORG_LIST != '' ) {
		$('#listOrg'+index).combotree('loadData', jQuery.parseJSON(ORG_LIST));
	}
}*/

function loadData(){
	for (i = 0; i< $('#result_size').val(); i++) {
		const prom = new Promise(function(resolve, reject){
			$('#listOrg'+i).combotree({
				editable : true,
				loader: function(param, success){
					if(param.id!=null){
						$.ajax({
							url: BASE_URL + 'organization/get-node',
							type  : "POST",
							global: false,
							beforeSend: function(xhrObj) {
							    	var token = $("meta[name='_csrf']").attr("content");
								  	var header = $("meta[name='_csrf_header']").attr("content");
								  	xhrObj.setRequestHeader(header, token);
							},
							data: param,
							dataType: 'json',
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
			var companyId = $('#companyId'+i).val();
			var orgId = $('#listOrg'+i).val();
			if(orgId == null || orgId == ''){
				orgId = 0;
			}
			 
			$.ajax({
		        type  : "POST",
		        url   : BASE_URL + "organization/build-tree-by-node-select",
				global: false,
				beforeSend: function(xhrObj) {
				    	var token = $("meta[name='_csrf']").attr("content");
					  	var header = $("meta[name='_csrf_header']").attr("content");
					  	xhrObj.setRequestHeader(header, token);
				},
		        data  : {
		        	"orgId" : orgId,
		        	"companyId" : companyId
		        },
		        success: function (data) {
		        	if( data != null && data != '' ) {
		    			$('#listOrg'+i).combotree('loadData', jQuery.parseJSON(data));
		    		}
		        }
		    });
			
			$('#companyId'+i).on('change', function(event) {
				var companyId = this.value;
				index = $(this).parents("tr").find("#index").val();
				$('#listOrg'+index).combotree('setValue', '');
				
				var orgId = $('#listOrg'+i).val();
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
			        success: function (data) {
			        	if( data != null && data != '' ) {
			    			$('#listOrg'+i).combotree('loadData', jQuery.parseJSON(data));
			    		}else{
			    			$('#listOrg'+i).combotree('loadData', []);
			    		}
			        }
			    });
			});
		})
	}
}

function loadMessage(message){
	if(message != null){
		$('#msg').html('<div class="alert alert-'+message.messages[0].status+' alert-dismissible">'
				+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
				+'<h4><i class="icon fa fa-'+message.messages[0].status+'"></i>Alert!</h4>'
				+'<div/>'+message.messages[0].content+'</div>');
	}
}

