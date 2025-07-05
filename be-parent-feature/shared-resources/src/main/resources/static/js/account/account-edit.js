$(document).ready(function() {
	// Init upload
	var filesImage = {};
    file_upload = $('#id-form-account').fileupload({
        url: BASE_URL + "account/edit",
        type: 'POST',
        dataType: 'text/html;charset=UTF-8',
        formAcceptCharset: 'utf-8',
        limitMultiFileUploadSize: 1000000,
        limitMultiFileUploadSizeOverhead: 1000000,
        multipart: true,
        maxChunkSize: 10000000,
        sequentialUploads: true,
        fileExt: '*.jpg;*.jpeg;*.gif;*.png;',
        singleFileUploads: false,
        add: function (e, data) {
            var imageConsoleEl = $(this).find('.imageConsole-' + data.paramName);
            $(imageConsoleEl).hide();
            var acceptFileTypes = /^image\/(gif|jpe?g|png)$/i;
            if (data.originalFiles[0]['type'].length <= 0|| !acceptFileTypes.test(data.originalFiles[0]['type'])) {
                $(imageConsoleEl).text(NOT_AN_ACCEPTED_FILE_TYPE)
                $(imageConsoleEl).show();
                return;
            }
            if (data.originalFiles[0]['size'].length > 0 && data.originalFiles[0]['size'] > 5000000) {
                $(imageConsoleEl).text(FILESIZE_IS_BIGGER)
                $(imageConsoleEl).show();
                return;
            }
            var totalName = data.files[0].name;
            var fileName = totalName.split(/\.(?=[^\.]+$)/);
            if ( $(this).find('.' + data.paramName) == '') {
            	 $(this).find('.' + data.paramName).val(fileName[0]);
            }

            var reader = new FileReader();
            reader.onload = (function (imageElm) {
                return function (e) {
                    $(imageElm).attr("src", e.target.result);
                }
            })($(this).find('.image-' + data.paramName));
            reader.readAsDataURL(data.files[0]);
            filesImage[e.delegatedEvent.target.name] = data.files[0];
        },
        done: function (e, data) {
        	console.log("done");
        	console.log(data.result);
        },
        fail: function (e, data) {
        	console.log("fail");
        	console.log(data.result);
        }
    });
    
	var accountid = $("#id-account-id").val();
	
	//loadRoleForAccount(accountid);
	
	loadOrgForAccount(accountid);
	
	$("#linkList").on("click", function(event) {
		var url = BASE_URL + "account/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	//on click add
	$("#btnAddUser").on("click", function(event) {
		var url = BASE_URL + "account/add";
		// Redirect to page detail
		ajaxRedirect(url);
	});
	
	$("#btnCancel").on("click", function(event) {
		var url = BASE_URL + "account/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click saveDraft
	$('.btnSave').on('click', function(event) {
		event.preventDefault();
		if ($(".j-form-validate").valid()) {
			var url = "account/edit";
			var condition = $(".j-form-validate").serializeArray();
			var filesList = [];
			$.each(filesImage, function( key, value ) {
				condition.push({"name": key,"value": value});
	    		filesList.push(value);
	    	});
			
			if(filesList.length > 0) {
				// Init form data
				var formData = new FormData();
				$.map(condition, function(n, i) {
			        formData.append(n['name'], n['value']);
			    });
				
				// Call ajax to save
				$.ajax({
					type : "POST",
					enctype: 'multipart/form-data',
					url : BASE_URL + url,
					data : formData,
					// prevent jQuery from automatically transforming the data into a query string
			        processData: false,
			        contentType: false,
			        cache: false,
			        timeout: 1000000,
					success : function(data, textStatus, request) {
						var content = $(data).find('.body-content');
						$(".main_content").html(content);
						
						var urlPage = $(data).find('#url').val();
						if (urlPage != null && urlPage != '') {
							window.history.pushState('', '', BASE_URL + urlPage);
						}
						goTopPage();
					},
					error : function(xhr, textStatus, error) {
						console.log(error);
					}
				});
			} else {
				ajaxSubmit(url, condition, event);
			}
			filesImage = {};
		}
	});
	
	/*$('#branchId').combotree({
		editable : true,
		onSelect : function(node) {
			ajaxLoadDep($('#companyId').val(), node.id)
        }
    });
	
	if( BRANCH_LIST != null && BRANCH_LIST != '' ) {
		$('#branchId').combotree('loadData', jQuery.parseJSON(BRANCH_LIST));
	}
	
	$('#departmentId').combotree({
		editable : true
    });
	
	if( DEPARTMENT_LIST != null && DEPARTMENT_LIST != '' ) {
		$('#departmentId').combotree('loadData', jQuery.parseJSON(DEPARTMENT_LIST));
	}*/
	
	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : GROUP_LABEL,
		search : true
	});
	
	//on change company
	$('#companyId').on('change', function(event) {
		/*$("[name='branchId']").val("");
		$("[name='departmentId']").val("");*/
		ajaxLoadPosition_Group($('#companyId').val());
		/*ajaxLoadBranch($('#companyId').val());
		ajaxLoadDep($('#companyId').val(), null);*/
		$('#positionId').val('').trigger('change');
	});
	
	GROUP_LIST_DISABLE.forEach(function(element) {
		var checkBoxGroup = $("input[type='checkbox'][value = " + element.id + "]");
		var checked = checkBoxGroup.is(':checked');
		if(checked) {
			checkBoxGroup.prop("disabled", true);
		} else {
			if(!IS_ADMIN) {
				checkBoxGroup.parents('li').remove();
			}
		}
	});
	
	$(function() {
		var selectedChannel = $('#channelId').val();
		if (!selectedChannel.includes('AD')) {
		$('#partnerId').prop('disabled', true);
	}});
	
	$('#channelId').on('change', function(event) {
		var selectedChannel = $(this).val();
		if (selectedChannel.includes('AD')) {
			$('#partnerId').prop('disabled', false);
			$('#partnerId').val('').trigger('change');
			//loadPartnerData();
		} else {
			$('#partnerId').prop('disabled', true);
			$('#partnerId').val('').trigger('change');
			$('#partnerId').empty(); 
		}
	});
 
	
	
	// Init positionId
    searchCombobox('#positionId', '', 'position/get-position-by-company',
    	    function data(params) {
    	        var obj = {
    	            keySearch: params.term,
    	            companyId: $('#companyId').val(),
    	            isPaging: true
    	        };
    	        return obj;
    	    }, function dataResult(data) {
    	        return data;
    	    }, true);
});

/* Load list role for account */ 
function loadRoleForAccount(accountId) {

    $.ajax({
        type  : "POST",
        url   : BASE_URL + "account/build_role_for_account",
        data  : {
        	"accountId" : accountId
        },
        global: false,
	    beforeSend: function(xhrObj) {
	    	var token = $("meta[name='_csrf']").attr("content");
		  	var header = $("meta[name='_csrf_header']").attr("content");
		  	xhrObj.setRequestHeader(header, token);
	    },
        success: function (data , textStatus, resp ) {
        	$("#id-role-for-account").text('');
            $("#id-role-for-account").html(data);
            $("#id-cancel-role-for-account").hide();
            $("#id-add-role-for-account").show();
            $('.datepicker').datepickerUnit({
        		format: DATE_FORMAT,
                changeMonth: true,
                changeYear: true,
                autoclose: true,
        		language : APP_LOCALE.toLowerCase(),
                keyboardNavigation : true
        	});
        }
    });
}

function ajaxLoadPosition_Group(companyId) {
	$.ajax({
		  async : false,
	      type  : "GET",
	      url   : BASE_URL + "account/get_position_group_by_company",
	      data  : {
	    	  "companyId" : companyId
	      },
	      success: function( data ) {
	          $("#position_group").html(data);
	      }
	});
}

/*function ajaxLoadBranch(companyId){
	$.ajax({
		async : false,
		dataType : "JSON",
        type  : "POST",
        url   : BASE_URL + "account/get_branch_by_company",
        data  : {
        	"companyId": $('#companyId').val()
        },
        success: function (data) {
        	console.log(data);
        	$('#branchId').combotree('clear');
        	$('#branchId').combotree('loadData', data);
        }
    });
}

function ajaxLoadDep(companyId, branchId){
	$.ajax({
		async : false,
		dataType : "JSON",
        type  : "POST",
        url   : BASE_URL + "account/get_dep_by_company",
        data  : {
        	"branchId" : branchId
        	,"companyId": companyId
        },
        success: function (data) {
        	console.log(data);
        	$('#departmentId').combotree('clear');
        	$('#departmentId').combotree('loadData', data);
        }
    });
}*/

/* Load list org for account */ 
function loadOrgForAccount(accountId) {
    /*$.ajax({
        type  : "POST",
        url   : BASE_URL + "account/build_org_for_account",
        data  : {
        	"accountId" : accountId
        },
        global: false,
	    beforeSend: function(xhrObj) {
	    	var token = $("meta[name='_csrf']").attr("content");
		  	var header = $("meta[name='_csrf_header']").attr("content");
		  	xhrObj.setRequestHeader(header, token);
	    },
        success: function (data , textStatus, resp ) {
        	$("#id-org-for-account").text('');
            $("#id-org-for-account").html(data);
            $("#id-add-org-for-account").show();
            $("#id-update-org-for-account").show();
            $("#id-cancel-org-for-account").hide();
            $('.datepicker').datepickerUnit({
        		format: DATE_FORMAT,
                changeMonth: true,
                changeYear: true,
                autoclose: true,
        		language : APP_LOCALE.toLowerCase(),
                keyboardNavigation : true
        	});
        }
    });*/
}

function loadPartnerData() {
	$.ajax({
		type: "POST",
		url: BASE_URL + "account/get-list-partner-by-channel",
		data: {
			"channel": "AD"
		},
		success: function(data) {
			if (data && data.length > 0) {
				console.log("Data: ", data);/*
				$.each(data, function(index, partner) {
					$('#partnerId').append($('<option></option>')
											.val(partner.code)
											.text(partner.code + ' - ' + partner.name));
				});
			} else {
				$('#partnerId').append('<option value="${NULL}">Hiiii</option>');*/
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching partners: ", error);
		}
	});
}