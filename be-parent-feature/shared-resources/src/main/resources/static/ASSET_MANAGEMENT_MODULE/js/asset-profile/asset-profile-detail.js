$(function(){
	// init date picker
	/*
	$('.date').datepicker({
		format: 'dd/mm/yyyy',
		viewMode : "days",
		minViewMode : "days",
		autoclose : true,
		language : '${sessionScope.localeSelect}',
		todayHighlight : true,
		onRender : function(date) {
		}
	});
	*/
	
	//on click list
	$('#linkList').on('click', function (event) {
		event.preventDefault();
		var url = BASE_URL + "asset-profile/list";
		// Redirect to page list
		ajaxRedirect(url);
	});
	
	$(window).on("popstate", function () {
		// if the state is the page you expect, pull the name and load it.
		let url = BASE_URL + "asset-profile/list";
		if (url === window.location.pathname.split('?')[0]) {
	  		ajaxRedirect(url);
		}
	});

});