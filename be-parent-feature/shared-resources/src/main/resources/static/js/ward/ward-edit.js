$(document).ready(function() {
	// set readonly code
	if ($("#wardId").val() != "" && $("#wardId").val() != null) {
		$("#wardCode").attr('readonly', 'readonly');
	}	
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "ward/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$('#mCityId').select2({
        placeholder : 'Select a City name',
        allowClear : true,
    });
	
	$('#districtCityRegionCountry').select2({
		placeholder : 'Select a City name',
		allowClear : true,
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "ward/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "ward/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	
	// Post edit save job
	$('#btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {	
			var url = "ward/edit";
			var condition = $("#form-ward-edit").serialize();

			ajaxSubmit(url, condition, event);
			$("html, body").animate({ scrollTop: 0 }, "slow");
			
		}else{
			// show tab if exists error
			showTabError(LANGUAGE_LIST);
			$("html, body").animate({ scrollTop: 0 }, "slow");
		}			
	});
	// show tab if exists error
	showTabError(LANGUAGE_LIST);
	getDistrictListByCityId();
});

function getDistrictListByCityId() {
    var url = "ward/getListDistrictByCityId";
    searchCombobox('#districtCityRegionCountry', 'Select district', url, function data(params) {
        var cityIdValue = $('#mCityId').val();
        var obj = {
            cityId : cityIdValue
        };
        return obj;
    }, function dataResult(data) {
        return data;
    });
}