
$(function(){
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-category/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-category/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	initData();
});

function initData(){
	if (ASSET_TYPE_CODE != null && ASSET_TYPE_CODE != ''){
		$('#assetTypeCode').append('<option value="' + ASSET_TYPE_CODE + '">' + $("#assetTypeName").val() + '</option>');
		
		$("#assetTypeCode").val(ASSET_TYPE_CODE).prop('selected', true);
	}
	
	$("#assetTypeCode").select2({});
}