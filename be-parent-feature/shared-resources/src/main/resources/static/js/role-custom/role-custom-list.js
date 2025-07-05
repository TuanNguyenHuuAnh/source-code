$(document).ready(function() {
	loadRoleCustomList();		
	// Save list authority
	$("#btnSaveAutho").on("click", function(e) {
		var rowRoleSelected = $("#roleTableId").find("tr.highlight");
		if(rowRoleSelected.length>0){
			e.preventDefault();
			ajaxSaveAuthority();
		}
		//ajaxDisplayOrder();
	});
	$('#roleTableId > tbody  > tr').each(function(i, value) {$(value).find("td:first").html(i+1);$(value).addClass("text-center");});
});

/**@function showMenuForRole**/
/*function showMenusForRole(e) {
	// $(e).parents("table").find(".highlight").find("div.btn-group
	// div").addClass("hide");
	$(e).parents("table").find(".highlight").removeClass("highlight");
	var rowRoleSelected = $(e).parents("tr");
	ajaxLoadAuthorityByRole(rowRoleSelected);
	rowRoleSelected.addClass("highlight");
	//rowRoleSelected.find("div.btn-group div.hide").removeClass("hide");
}*/

$(".select-company").on('change', function(event) {
	loadRoleCustomList();
});

function ajaxLoadAuthorityByRole(rowRoleSelected, companyId){
	var roleId = rowRoleSelected.length > 0? rowRoleSelected.data("role-id"): null;
	$.ajax({
		async : false,
		type  : "POST",
		url : BASE_URL + "custom/role/ajax/list",
		data  : {
		"roleId" : roleId,
		"companyId" : companyId
		},
		success: function( data ) {
			$("#boxMenuByFunction").html(data);
		}
		
	});
}

function ajaxSaveAuthority() {
	//console.log(getAuthorityJson());
	$(".j-message-alert").html("");
	var form = $("#formAuthority");
	blockbg();
	$.ajax({
		type : 'post',
		contentType : "application/json",
		url : form.attr('action'),
		data : getAuthorityJson(),
		success : function(data) {
			unblockbg();
			$(".j-message-alert").prepend(data);
		}
	});
}


/**function displayorder**/
function ajaxDisplayOrder() {
	$.ajax({
		type : 'POST',
		contentType : "application/json",
		url : BASE_URL + "custom/role/updateDisplay",
		data : getAuthorityJson(),
		
	});
}

function up() {
	var $tr = $("#roleTableId").find("tr.highlight");
	$tr.prev().before($tr);
	order_number();
};

function down() {
	var $tr = $("#roleTableId").find("tr.highlight");
	$tr.next().after($tr);
	order_number();
};

function order_number() {
	$('#roleTableId tbody tr').each(function(i) {
		$(this).find('td:first').text(i + 1)
	})
};

/**function**/
function getAuthorityJson() {
	var formArray = {};
	$.each($("form").serializeArray(), function(i, field) {
		formArray[field.name] = field.value;
	});
	var authority = [];
	$('#roleTableId tbody tr').each(function(key, val) {
		if (val.dataset.roleId == formArray["roleId"]) {
			authority.push({
				"roleId" : val.dataset.roleId,
				"menuId" : formArray["menuId"],
				"displayOrder" : $(val).find("td:first").text(),
				"companyId" : $(val).find("td:first").data('company-id')
			});
		} else {
			authority.push({
				"roleId" : val.dataset.roleId,
				"displayOrder" : $(val).find("td:first").text(),
				"companyId" : $(val).find("td:first").data('company-id')
			});
		}
	});
	return JSON.stringify(authority);
}

function loadRoleByCompany(companyId){
	$.ajax({
		  async : false,
	      type  : "GET",
	      url : BASE_URL + "custom/role/get-company",
	      data  : {
	    	  "companyId" : companyId
	      },
	      success: function(data) {
	          $("#comRoleTable").html(data);
	      }
	});
}
function loadRoleCustomList() {
	$('#msg').html("");
	var companyId = $("#company-id").val();
	if(companyId!=undefined){
		loadRoleByCompany(companyId);
		var rowRoleSelected = $("#roleTableId").find("tr.highlight");
		ajaxLoadAuthorityByRole(rowRoleSelected, companyId);
	}
	//show msg
	if(messageList != null){
		$('#msg').html('<div class="alert alert-'+messageList.messages[0].status+' alert-dismissible">'
				+'<button aria-hidden="true" data-dismiss="alert" class="close" type="button">×</button>'
				+'<h4><i class="icon fa fa-'+messageList.messages[0].status+'"></i>Alert!</h4>'
				+'<div/>'+messageList.messages[0].content+'</div>');
	}
}

