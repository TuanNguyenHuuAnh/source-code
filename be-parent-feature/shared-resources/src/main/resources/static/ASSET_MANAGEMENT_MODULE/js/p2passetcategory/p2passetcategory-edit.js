var LIST_ASSET_CATEGORY = [];
$(function(){
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-category/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-category/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "asset-category/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	

	// Post edit save
	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			var url = "asset-category/edit";
			var condition = $("#form-edit").serialize();
			ajaxSubmit(url, condition, event);
		}			
		goTopPage();
	});
	
	initData();
	
//	$("#assetTypeCode").on('select2:open', function(event) {
//		$('#assetTypeCode').find('option:not(:first)').remove();
//		
//		if (LIST_ASSET_TYPE != null && LIST_ASSET_TYPE.length != 0){
//			for (var i=0; i<LIST_ASSET_TYPE.length; i++){
//				var val = LIST_ASSET_TYPE[i];
//				if (ASSET_TYPE_CODE != null && ASSET_TYPE_CODE == val.id){
//					$('#assetTypeCode').append('<option selected="selected" value="' + val.id + '">' + val.name + '</option>');
//				}else{
//					$('#assetTypeCode').append('<option value="' + val.id + '">' + val.name + '</option>');
//				}
//
//			}
//		}
//	});
	
	$("#assetTypeCode").on('change', function(event){
		ASSET_TYPE_CODE = $(this).val();
		
		var value = $("#select2-assetTypeCode-container").text();
		$("#assetTypeName").val(value);
		
		loadTitle();
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
	}
	
	loadTitle();
	
	$("#assetTypeCode").select2({});
}

function loadTitle(){
	if ($("#assetTypeCode").val() == ""){
		document.getElementById("showTitle").title = '3 arbitrary characters according to the category name';
	}else{
		$.ajax({
			type: 'GET',
			url: BASE_URL + 'asset-category/checkAssetType',
			data: {
				assetTypeCode: $("#assetTypeCode").val()
			},
			global: false,
			success: function(data){
				if (!data){
					document.getElementById("code").placeholder = "TAB";
					document.getElementById("showTitle").title = '3 arbitrary characters according to the category name';
				}else{
					document.getElementById("code").placeholder = "HW-TL, SW-TS, HW-TH";
					document.getElementById("showTitle").title = 'The two left characters (HW, SW) are for asset category >= 30M. \nThe two right characters (TL, TS, TH) are for asset category < 30M';
				}
			}
		});
	}
}