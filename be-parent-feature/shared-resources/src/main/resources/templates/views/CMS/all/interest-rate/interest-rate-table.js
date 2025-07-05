$(document).ready(function($) {
	$('#tabInterestRate a[href="#interestRateDetail"]').tab('show');
	
	// tabLanguage click
	$('#tabInterestRate a').click(function(e) {
		e.preventDefault();
		$(this).tab('show');
	});
	
});
