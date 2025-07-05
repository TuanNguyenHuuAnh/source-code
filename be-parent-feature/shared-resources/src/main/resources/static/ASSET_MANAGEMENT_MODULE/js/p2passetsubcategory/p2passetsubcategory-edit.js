var LIST_ASSET_CATEGORY = [];
$(function(){
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-sub-category/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-sub-category/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-sub-category/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	

	// Post edit save
	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "asset-sub-category/edit";
			var condition = $("#form-edit").serialize();
			ajaxSubmit(url, condition, event);
		}			
		goTopPage();
	});
	
	initData();
	
//	$("#assetTypeCode").on('select2:open', function(event) {
//		CATEGORY_CODE = '';
//		
//		$('#assetTypeCode').find('option:not(:first)').remove();
//		
//		if (LIST_ASSET_TYPE != null && LIST_ASSET_TYPE.length != 0){
//			for (var i=0; i<LIST_ASSET_TYPE.length; i++){
//				var val = LIST_ASSET_TYPE[i];
//				
//				if (ASSET_TYPE_CODE != null && ASSET_TYPE_CODE == val.id){
//					$('#assetTypeCode').append('<option selected="selected" value="' + val.id + '">' + val.name + '</option>');
//				}else{
//					$('#assetTypeCode').append('<option value="' + val.id + '">' + val.name + '</option>');
//				}
//			}
//		}
//		
//		$('#assetCategoryCode').find('option:not(:first)').remove();
//	});
	
	$("#assetTypeCode").on('change', function(event){
		ASSET_TYPE_CODE = $(this).val();
		
		CATEGORY_CODE = '';
		
		var value = $("#select2-assetTypeCode-container").text();
		
		$("#assetTypeName").val(value);
		$.ajax({
			type: 'GET',
			url: BASE_URL + 'asset-sub-category/getAssetCategoryData',
			global: false,
			data: {
				assetTypeCode: $("#assetTypeCode").val()
			},
			success: function(data){
				setDataAssetCategory(data);
			}
		});
	});
	
//	$("#assetCategoryCode").on('select2:open', function(event) {
//		CATEGORY_CODE = '';
//		$('#assetCategoryCode').find('option:not(:first)').remove();
//		
//		if (LIST_ASSET_CATEGORY != null && LIST_ASSET_CATEGORY != []){
//			for (var i=0; i< LIST_ASSET_CATEGORY.length; i++){
//				var val = LIST_ASSET_CATEGORY[i];
//				$('#assetCategoryCode').append('<option value="' + val.code + '">' + val.name + '</option>');
//			}
//		}
//	});
	
	$("#assetCategoryCode").on('change', function(event){
		var value = $("#select2-assetCategoryCode-container").text();
		$("#assetCategoryName").val(value);
	});
	
	function goTopPage(){
		$("html, body").animate({ scrollTop: 0 }, "1");
	}
});

function initData(){
	if (ASSET_TYPE_CODE != null && ASSET_TYPE_CODE != ''){
		var inList = false;
		if (LIST_ASSET_TYPE != null && LIST_ASSET_TYPE.length != 0){
			for (var i=0; i< LIST_ASSET_TYPE.length; i++){
				var val = LIST_ASSET_TYPE[i];
				if (val.id == ASSET_TYPE_CODE){
					inList = true;
				}
			}
		}
		
		if (!inList){
			$('#assetTypeCode').append('<option value="' + ASSET_TYPE_CODE + '">' + $("#assetTypeName").val() + '</option>');
			
			$("#assetTypeCode").val(ASSET_TYPE_CODE).prop('selected', true);
		}
		
		$.ajax({
			type: 'GET',
			url: BASE_URL + 'asset-sub-category/getAssetCategoryData',
			data: {
				assetTypeCode: ASSET_TYPE_CODE
			},
			global: false,
			success: function(data){
				setDataAssetCategory(data);
			}
		});
	}
	
//	initAssetTypeSelect();
	
	if (CATEGORY_CODE != null && CATEGORY_CODE != ''){
		$('#assetCategoryCode').append('<option value="' + CATEGORY_CODE + '">' + $("#assetCategoryName").val() + '</option>');
		
		$("#assetCategoryCode").val(CATEGORY_CODE).prop('selected', true);
	}
	
	$("#assetTypeCode").select2({});
	$("#assetCategoryCode").select2({});
}

function initAssetTypeSelect(){
	
	var assetCategorySelect = $("#assetCategoryJsonHidden").val();
	
	if (assetCategorySelect != null && assetCategorySelect != '') {
		setDataAssetCategory(assetCategorySelect);
	}
}

function setDataAssetCategory(data){
	LIST_ASSET_CATEGORY = jQuery.parseJSON(data);
	
	$('#assetCategoryCode').find('option:not(:first)').remove();
	
	$.each(jQuery.parseJSON(data), function(key, val) {
		$('#assetCategoryCode').append('<option value="' + val.code + '">' + val.name + '</option>');
	});
	
	if (CATEGORY_CODE != null && CATEGORY_CODE != '') {
		$('#assetCategoryCode').val(CATEGORY_CODE).prop('selected', true);
	}
}