$(document).ready(function() {
	$('#form-j-edit').on('keyup keypress', function(e) {
		  var keyCode = e.keyCode || e.which;
		  if (keyCode === 13) { 
		    e.preventDefault();
		    return false;
		  }
		});
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : SEARCH_LABEL,
		search : true
	});
	
	$("#linkList").on("click", function(event) {
		var url = BASE_URL + "organization-person/list";
		ajaxRedirect(url);
	});

	$('#btn-save, #btnSaveHead').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var values = $(".j-form-validate").serializeArray();
			/*
			var id = $("#id").val();
			var url = "organization-person/edit?id=" + id;
			ajaxSubmit(url, values, event);
			*/
			$.ajax({
	            url: BASE_URL + "organization-person/edit",
	            type: "POST",
	            data: values,
	            success: function (response) {
	            	$("#messages").html(response);
	                unblockbg();
	            }
	        });
		}
	});
	
	$("#btn-cancel").on("click", function(event) {
		var url = BASE_URL + "sla/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//Change org
    $('.select-company').on('change', function(event) {
    	var id = $(this).val();
    	loadOrg(id, null, 'orgParentId', true, true);
    	loadOrg(id, null, 'orgId', false, true);	
    });
    
  //Change process
    $('#orgCurator').on('change', function(event) {
    	var text = $("#orgCurator option:selected").text();
    	$('#orgCuratorName').val(text);
    });
    
  //Change process
    $('#orgParentCurator').on('click, change', function(event) {
    	var text = $("#orgParentCurator option:selected").text();
    	$('#orgParentCuratorName').val(text);
    });
    
    loadOrg($('#companyId').val(), null, 'orgParentId', true, true);
	loadOrg($('#companyId').val(), null, 'orgId', false, true);
	initCurator();
	initParentOrg();	
    
});

/**
 * @param id
 * @returns
 */
function initCurator() {
    searchCombobox('#orgCurator', '', 'account/ajax-get-list-user-by-org',
    	    function data(params) {
    	        var obj = {
    	        		orgId: $("input[name=orgId]").val(),
        	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

/**
 * @param id
 * @returns
 */
function initParentOrg() {
    searchCombobox('#orgParentCurator', '', 'account/ajax-get-list-user-by-org',
    	    function data(params) {
    	        var obj = {
    	        		orgId: $("input[name=orgParentId]").val(),
        	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
}

/**
 * @param companyId
 * @param orgId
 * @param element
 * @param isParentOrg
 * @param isLoadFullOrg
 * @returns
 */
function loadOrg(companyId, orgId, element, isParentOrg, isLoadFullOrg) {
	const prom = new Promise(function(resolve, reject){
	$('#' + element).combotree(
			{
				editable : true,
				onChange : function(v) {
					if (isParentOrg) {
						loadOrg(companyId, v, 'orgId', false, false);
					}
				},
				loader : function(param, success) {
					if (param.id != null) {
						$.ajax({
							url : BASE_URL + 'organization/get-node',
							type : "POST",
							global : false,
							beforeSend : function(xhrObj) {
								var token = $("meta[name='_csrf']").attr(
										"content");
								var header = $("meta[name='_csrf_header']")
										.attr("content");
								xhrObj.setRequestHeader(header, token);
							},
							data : param,
							dataType : 'json',
							success : function(data) {
								success(data);
							}
						});
					} else {
						$.ajax({
							url : BASE_URL + 'organization/get-node',
							type : "POST",
							global : false,
							beforeSend : function(xhrObj) {
								var token = $("meta[name='_csrf']").attr(
										"content");
								var header = $("meta[name='_csrf_header']")
										.attr("content");
								xhrObj.setRequestHeader(header, token);
							},
							data : {
								'id' : orgId
							},
							dataType : 'json',
							success : function(data) {
								success(data);
							}
						});
					}
				}
			});
	resolve();
	});
	
	prom.then(function(){
	if (isLoadFullOrg) {
		if (orgId == null || orgId == '') {
			orgId = 0;
		}
		$.ajax({
			type : "POST",
			url : BASE_URL + "organization/build-tree-by-node-select",
			global : false,
			beforeSend : function(xhrObj) {
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				xhrObj.setRequestHeader(header, token);
			},
			data : {
				"orgId" : orgId,
				"companyId" : companyId
			},
			success : function(data) {
				if (data != null && data != '') {
					$('#' + element).combotree('loadData',
							jQuery.parseJSON(data));
				}
			},
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});
	};
	});
}
