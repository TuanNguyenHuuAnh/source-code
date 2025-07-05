$(document).ready(function($) {
	if ($("#keeperFullname").attr("readonly") != "readonly") {
		autoKeeperFullname();
	}
	
	$('#assetLocationCode').on('change', function() {
		assetLocationCodeSelected = $(this).val();
		
		var assetLocationName = $(this).children("option:selected").data('assetlocationname');
		$('#assetLocationName').val(assetLocationName);
		
    	var code = $('#assetLocationCode').val();
    	getAddress(code);
	});
	
	$('#chkActive').attr('checked', true);
	
    $("#linkList").on('click', function(event) {
        var url = BASE_URL + "warehouse/list";
        // Redirect to page add
        ajaxRedirect(url);
    });

    $("#cancel").on('click', function(event) {
        var url = BASE_URL + "warehouse/list";
        // Redirect to page add
        ajaxRedirect(url);
    });

    $('#add').on('click', function(event) {
        event.preventDefault();
        var url = BASE_URL + "warehouse/edit";

        // Redirect to page list
        ajaxRedirect(url);
    });
    
	
    $('.btn-save').on('click', function(event) {
		if ( $(".j-form-validate").valid() ){
			var url = "warehouse/edit";
			var condition = $("#form-warehouse-edit").serialize();

			ajaxSubmit(url, condition, event);
		}
	});
    
    getListOfficeLocation();
    
	if (assetLocationCodeSelected != null && assetLocationCodeSelected != ''){
		var inList = false;
		if (LST_WARE_HOUSE != null && LST_WARE_HOUSE.legnth != 0){
			for (var i=0; i< LST_WARE_HOUSE.length; i++){
				var val = LST_WARE_HOUSE[i];
				if (val.id == assetLocationCodeSelected){
					inList = true;
				}
			}
			
			if (!inList){
				$('#assetLocationCode').append('<option value="' + assetLocationCodeSelected + '">' + $("#assetLocationName").val() + ' - ' + assetRegionNameSelected + '</option>');
				
				$("#assetLocationCode").val(assetLocationCodeSelected).prop('selected', true);
			}
		}
	}
	
	$('#assetLocationCode').select2({});
	
	$('#warehousesParentId').select2({});
	
	$('#propertyOf').on('change', function() {
		setMaxlengthCode();
	});
	
	setMaxlengthCode();
});

function setMaxlengthCode() {
	if ($('#propertyOf').val() == 'IT') {
		$('#code').attr('maxlength', '2');
	} else {
		$('#code').attr('maxlength', '3');
	}
}

function getAddress(code) {
	$.ajax({
		url : BASE_URL + "warehouse/get/addressBy/"+code,
		type : 'GET',
		global:false}
	).done(function(result){
		$('#pac-input').val(result);
	})
}
  

function getListOfficeLocation(){
	$.ajax({
		url : BASE_URL + "warehouse/getListOfficeLocation",
		type : 'GET',
		global:false
	})
	.done(function(result){
		if(result != null && result.length > 0){
			let html = '<option value="">--</option>';
			result.forEach(function(item){
				if (assetLocationCodeSelected == item.id){
					 html += '<option value="'+item.id+'" selected data-assetlocationname="'+ item.text+'">'+item.name+'</option>';
				}else{
					 html += '<option value="'+item.id+'" data-assetlocationname="'+ item.text+'">'+item.name+'</option>';
				}
			});
			$('#assetLocationCode').append(html);
		}
	})
}

function autoKeeperFullname() {
	// autocomplete
	$('#keeperFullname').autocomplete({
		serviceUrl : BASE_URL + 'warehouse/find-keeper',
		paramName : 'inputQuery',
		// delimiter: ",",
		ajaxSettings : {
			global : false
		},
		triggerSelectOnValidInput : true,
		onSelect : function(suggestion) {
			$("#keeperFullname").val(suggestion.value);
			$("#keeperUsername").val(suggestion.id);
			return false;
		},
		onInvalidateSelection : function(suggestion) {
			$("#keeperUsername").val('');
			return false;
		},
		transformResult : function(response) {
			return {
				suggestions : $.map($.parseJSON(response), function(item) {
					return {
						value : item.name,
						id : item.id,
					};
				})
			};
		}
	});
}
  