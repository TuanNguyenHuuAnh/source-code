/**
 * unit-header progress JS
 */
$(document).ready(function() {
	
	// Logout click	
	$("#logout").on('click',function(){
		disconnect();
		
		deleteAllCookie();
		$("#logoutform").submit();
	});
});
