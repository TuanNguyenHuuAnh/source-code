var LIST_ASSET_CATEGORY = [];
$(function(){
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-sub-category/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-sub-category/list";
		// Redirect to page list
		ajaxRedirect(url);
	});

	initData();
});

function initData(){
	initAssetTypeSelect();
	
	if (ASSET_TYPE_CODE != null && ASSET_TYPE_CODE != ''){
		$('#assetTypeCode').append('<option value="' + ASSET_TYPE_CODE + '">' + $("#assetTypeName").val() + '</option>');
		
		$("#assetTypeCode").val(ASSET_TYPE_CODE).prop('selected', true);
		
		$.ajax({
			type: 'GET',
			url: BASE_URL + 'asset-sub-category/getAssetCategoryData',
			data: {
				assetTypeCode: ASSET_TYPE_CODE
			},
			global: false,
			success: function(data){
				CATEGORY_CODE = '';
				
				LIST_ASSET_CATEGORY = data;
			}
		});
	}
	
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
	$('#assetCategoryCode').find('option:not(:first)').remove();

	$.each(jQuery.parseJSON(data), function(key, val) {
		$('#assetCategoryCode').append('<option value="' + val.code + '">' + val.name + '</option>');
	});
	
	if (CATEGORY_CODE != null && CATEGORY_CODE != '') {
		$('#assetCategoryCode').val(CATEGORY_CODE).prop('selected', true);
	}else{
		$('#assetCategoryCode').val("").prop('selected', true);
	}
}