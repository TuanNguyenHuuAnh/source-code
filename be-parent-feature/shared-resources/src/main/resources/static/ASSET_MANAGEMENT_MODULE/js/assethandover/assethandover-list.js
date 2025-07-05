$(document).ready(function($) {
	
	getListWarehouse();
	getListReceiver();
});


function getListWarehouse(){
		$.ajax({
			url : BASE_URL + "asset-handover/getListWarehouse",
			type : 'GET',
			global:false
		})
		.done(function(result){
			if(result != null && result.length > 0){		
				let html = '<option value="">--</option>';
				result.forEach(function(item){
					if (selectedWarehouse == item.name){
						 html += '<option value="'+item.name+'" selected>'+item.name+'</option>';
					}else{
						 html += '<option value="'+item.name+'">'+item.name+'</option>';
					}
				});

				$('#warehouseName').append(html);
				$('#warehouseName').select2({});
			}	
		})
	}

function getListReceiver(){
		$.ajax({
			url : BASE_URL + "asset-handover/getListReceiver",
			type : 'GET',
			global:false
		})
		.done(function(result){
			if(result != null && result.length > 0){		
				let html = '<option value="">--</option>';
				result.forEach(function(item){
					if (selectedReceiver == item.name){
						 html += '<option value="'+item.name+'" selected>'+item.name+'</option>';
					}else{
						 html += '<option value="'+item.name+'">'+item.name+'</option>';
					}
				});

				$('#receiverFullname').append(html);
				$('#receiverFullname').select2({});
			}	
		})
	}