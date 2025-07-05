$(document).ready(function($) {
	
	// on click search
	$("#btnSearch").on('click', function(event) {
		onClickSearch(this, event);
	});
	
	getListWarehouse();
});


/**
 * deleteCountry
 * @param element
 * @param event
 * @returns
 */

function getListWarehouse(){
		$.ajax({
			url : BASE_URL + "asset-return/getListWarehouse",
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