$(document).keypress(function(event){

    var keycode = (event.keyCode ? event.keyCode : event.which);
    if(keycode == '13'){
    	onClickSearch(this, event);
    }

});

$(document).ready(function($) {
	// datatable load
	$(".unit-bg-table").datatables({
		url : BASE_URL + 'chat/ajax-list-user-product',
		type : 'POST',
		setData : setConditionSearch
	});
	
	$(".j-btn-edit").on("click", function(event) {
		event.preventDefault();
		
		// Prepare data
		var row = $(this).parents("tr");
		
		var roleName = row.find(".role-name").text();
		
		$("#user").val('');
		
		if (roleName != undefined){

			$("#roleChat").val(roleName);
			
			if ($("#roleChat").val() != undefined && $("#roleChat").val() != ""){
				$("#roleChat").trigger('change');
			}
		}
		
		$("#roleChat").val(roleName);
		
		checkShowBtnSave();
	});

	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
	
	$("#roleChat").on("change", function(){
		var role = $("#roleChat").val();
		$("#user").val("").change();
		getUserExistInProduct(role);
		checkShowBtnSave();
	});

	function getUserExistInProduct(role){
		$.ajax({                
		     dataType : "json",
		     type  : "POST",
		     data : 
		     	{
		    	 	"role" : role
		    	},
		     url   : BASE_URL + "chat/get-list-user-role",
		     success: function (data) {          
		    	 // get value
		    	 var listUser = $("#user").val();
		    	 if(listUser == null){
		    		 listUser = [];
		    	 }
		    	 if(data.length > 0){
		    		 for(var i=0; i<data.length; i++){
			    		 if(checkUserExists(listUser, data[i].username)==false){
			    			 listUser.push(data[i].username);
			    		 }	
			    	 }	 
		    	 }
		    	 else{
		    		 listUser = [];
		    	 }
		    	 
		    	 // reset control
		    	 $("#user").val(listUser).change();
		     }
		});
	}
	
	function checkUserExists(list, user){
		for(var i=0; i<list.length; i++){
			if(list[i] == user){
				return true;
			}
		}
		return false;
	}
	
	function getProductId(){
		var product = '';
		$(".textbox-value").each(function(e){
			product += $(this).val();
			product += ';';
		});
		var productId = '';
		if(product != ''){
			var arr = product.split(';');
			for(var i = 0; i < arr.length; i++){
				var arr1 = arr[i].split('_');
				if(arr1[0] == 'PDT'){
					productId += arr1[1];
					productId += ';';
				}
			}
		}
		return productId;
	}
	
	$("#btn-save").unbind('click').bind('click', function(e){
		e.preventDefault();
		var user = $("#user").val();
		if(user == null || user.length == 0){
			alert(MESSAGE_CHAT_USER_PRODUCT_ALRET_1);
			return;
		}
		var userData = JSON.stringify(user);
		
		blockbg();
		
		$.ajax({
			url   : BASE_URL + "chat/save-user-product",
		    type : "POST",
		    data : {
		    	 "role": $("#roleChat").val(),
		    	 "user": userData
		    },
		    success: function (data) {
		    if(data == '1'){
		    		 $("#boxAlert").css('display','none');
		    		 $("#boxSuccess").css('display','block');
		    		 
		    		 $("#btnSearch").trigger("click");
		    	 }
		    	 else{
		    		 $("#boxAlert").css('display','block');
		    		 $("#boxSuccess").css('display','none');
		    	 }
		     },
		     error : function(xhr, textStatus, error) {
					console.log(xhr);
					console.log(textStatus);
					console.log(error);
		     },
		     complete : function(result) {
		    	 unblockbg();
		     }
		});
	});
	
	$("#btnRefresh").on('click', function(){
		$("#roleChat").val('');
		$("#user").val('');
		
		$("#roleChat").trigger('change');
		
		checkShowBtnSave();
		
		$(".close").trigger('click');
	});
	
	$(".close").on('click', function(){
		 $("#boxAlert").css('display','none');
		 $("#boxSuccess").css('display','none');
	});
	
	checkShowBtnSave();
	
	$('.select2').select2({	
		separator: '|',
		placeholder : "User",
		minimumInputLength : 0,
		allowClear:true,
	});
});

function checkShowBtnSave(){
	if ($("#roleChat").val() != undefined && $("#roleChat").val() != ''){
		$("#btn-save").removeClass("hidden");
	}else{
		$("#btn-save").addClass("hidden");
	}
}

function blockbg(){
	$.isLoading({ text: "Loading" });
}
function unblockbg(){
	$.isLoading( "hide" );
}

function onClickSearch(element, event) {
	ajaxSearch("chat/ajax-list-user-product", setConditionSearch(), "tableList", this, event);
}

/**
 * setConditionSearch
 */
function setConditionSearch() {
	setDataSearchHidd();
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearchHidd").val();
	condition["fieldValues"] = $("#fieldValuesHidd").val();
	return condition;
}

/**
 * setDataSearchHidd
 */
function setDataSearchHidd() {
	$("#fieldSearchHidd").val($("#fieldSearch").val());
	$("#fieldValuesHidd").val($("select[name=fieldValues]").val());
}
