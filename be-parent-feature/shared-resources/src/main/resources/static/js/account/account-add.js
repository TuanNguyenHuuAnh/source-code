$(document).ready(function() {
    jQuery.validator.addMethod("validPassword", function (value, element) {
        var re = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$/;

        // allow any non-whitespace characters as the host part
        return this.optional(element) || re.test(value);
    }, ERROR_REGEX);
    
	// Init upload
	var filesImage = {};
    file_upload = $('#newsForm').fileupload({
        url: BASE_URL + "account/add",
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
                $(imageConsoleEl).text('Not an accepted file type')
                $(imageConsoleEl).show();
                return;
            }
            
            if (data.originalFiles[0]['size'].length > 0 && data.originalFiles[0]['size'] > 5000000) {
                $(imageConsoleEl).text('Filesize is too big')
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
            // debugger
        	console.log("done");
        	console.log(data.result);
        },
        fail: function (e, data) {
            // debugger
        	console.log("fail");
        	console.log(data.result);
        }
    });
    
	$("#linkList").on("click", function(event) {
		var url = BASE_URL + "account/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$("#btnCancel").on("click", function(event) {
		var url = BASE_URL + "account/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$('.datepicker').datepickerUnit({
		format: DATE_FORMAT,
        changeMonth: true,
        changeYear: true,
        autoclose: true,
		language : APP_LOCALE.toLowerCase(),
        keyboardNavigation : true
	});
	
	
//	$('#avatarFile').fileupload({
//        done : function(e, data) {
//        	// After call upload file temp -- /ajax/uploadTemp
//        	if( data.result ) {
//        		var avatarName = data.result;
//        		$("#avatar").val(avatarName);
//        		$("#imgAccount").attr("src", BASE_URL + "ajax/download?fileName=" + avatarName);
//        	}
//        }
//    });
	
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
	
	// on click saveDraft
	$('.btnSave').on('click', function(event) {
		event.preventDefault();
		if ($(".j-form-validate").valid()) {
			var url = "account/add";
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
	
	//ajaxLoadPosition_Group($('#companyId').val());
	// multiple select
	$('select[multiple]').multiselect({
		columns : 1,
		placeholder : GROUP_LABEL,
		search : true,
		minHeight: 100,
		maxHeight: 155
	});

	setTimeout(() => {
		$("html, body").css({"height": '', "min-height": ''});
	}
	, 500);

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
	
	//on change company
	$('#companyId').on('change', function(event) {
		/*$("[name='branchId']").val("");
		$("[name='departmentId']").val("");*/
		ajaxLoadPosition_Group($('#companyId').val());
		/*ajaxLoadBranch($('#companyId').val());
		ajaxLoadDep($('#companyId').val(), null);*/
		$('#positionId').val('').trigger('change');
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
	$("#accountType").on('change',function (e){
			e.preventDefault();
			if($(this).val()=='LDAP'){
				$("#div-sync").removeClass("hide");
				$('#password').addClass('hide');
				$('#password').removeClass('j-required');
				$("#fullname").attr("readOnly","readOnly");
				$("#email").attr("readOnly","readOnly");
			}else{
				$("#div-sync").addClass("hide");
				$("#fullname").removeAttr("readOnly");
				$("#email").removeAttr("readOnly");
				$('#password').removeClass('hide');
				$('#password').addClass('j-required');
			}
	});
	$(".btn-sync").on('click',function(e){
		e.preventDefault();
		var username = $("#username").val();
		$.ajax({
			url: BASE_URL + 'account/find-account-ldap',
			type:'POST',
			data:{
				'username' : username,
			},
			success: function(data){
				clearData();
				if(data.status=='success'){
					$("#sync-message").html('<label  class="success">'+data.message+'</label>');
					$("#fullname").val(data.retObj.fullname);
					$("#email").val(data.retObj.email);
				} else {
					$("#sync-message").html('<label  class="error">'+data.message+'</label>');
				}
			
			},
			error: function(err){
				console.log(err)
			}
		});
		
		
	});
});
function clearData(){
	$("#fullname").val('');
	$("#password").val('');
	$("#birthday").val('');
	$("#email").val('');		
	$("#phone").val('');
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
        	$('#departmentId').combotree('loadData', data);
        }
    });
}*/

function loadPartnerData() {
	$.ajax({
		type: "POST",
		url: BASE_URL + "account/get-list-partner-by-channel",
		data: {
			"channel": "AD"
		},
		success: function(data) {
			if (data && data.length > 0) {
				console.log("Data: ", data);
				$.each(data, function(index, partner) {
					console.log("Partner item: ", partner);
					$('#partnerId').append($('<option></option>')
											.val(partner.code)
											.text(partner.code + ' - ' + partner.name));
				});
			} else {
				$('#partnerId').append('<option value="${NULL}">Hiiii</option>');
			}
		},
		error: function(xhr, status, error) {
			console.error("Error fetching partners: ", error);
		}
	});
}