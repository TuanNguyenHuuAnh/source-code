$(function(){
	$('#cancel,.backlist,#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "chat/list-user-product";
		
		// Redirect to page list
		ajaxRedirectWithCondition(url, setConditionSearch());
	});
	$('.select2').select2({		
		tokenSeparators: [',', ' '],
		allowClear: true,
		onselect: function(){
			
		}
	});
	
	$("#roleChat").on("change", function(){
		var role = $("#roleChat").val();
		$("#user").val("").change();
		getUserExistInProduct(role);
	});
	
	if ($("#roleChat").val() != undefined && $("#roleChat").val() != ""){
		$("#roleChat").trigger('change');
	}
	
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
	
	$("#btn-save").click(function(e){
		e.preventDefault();
		var user = $("#user").val();
		if(user == null || user.length == 0){
			alert(MESSAGE_CHAT_USER_PRODUCT_ALRET_1);
			return;
		}
		var userData = JSON.stringify(user);
		
//		var pdata = {
//				"role" : $("#roleChat"),
//				"user" : userData
//		}
		
		blockbg();
		
		debugger;
		
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
	})
})

function blockbg(){
	$.isLoading({ text: "Loading" });
}
function unblockbg(){
	$.isLoading( "hide" );
}

function setConditionSearch() {
	var condition = {};
	condition["fieldSearch"] = $("#fieldSearch").val();
	condition["fieldValues"] = $("#fieldValues").val();
	return condition;
}