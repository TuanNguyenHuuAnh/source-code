
$(function(){
	//on click cancel
	$('#cancel').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "office-location/list";
		
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "office-location/list";

		// Redirect to page list
		ajaxRedirect(url);
	});
	
	// on click add
	$("#add").on("click", function(event) {
		var url = BASE_URL + "office-location/edit";
		// Redirect to page add
		ajaxRedirect(url);
	});	

	// Post edit save
	$('.btn-save').on('click', function(event) {
		if ($(".j-form-validate").valid()) {
			
			var url = "office-location/edit";
			var condition = $("#form-edit").serialize();
			ajaxSubmit(url, condition, event);
		}
		goTopPage();
	});
	
	initData();
	
	$("#regionId").on('change', function(event){
		var value = $("#select2-regionId-container").text();
		$("#regionName").val(value);
	});
	
	function goTopPage(){
		$("html, body").animate({ scrollTop: 0 }, "1");
	}
});

function initData(){
	$("#regionId").select2({});
	
	// date picker
	$(".date").datepicker({autoclose : true,format: 'dd/mm/yyyy'});
	var idEffectedDate = $("#fromDate").val();
	var idExpiredDate = $("#toDate").val();
	changeDatepickerById(idEffectedDate, idExpiredDate,'#fromDate','#toDate');
}