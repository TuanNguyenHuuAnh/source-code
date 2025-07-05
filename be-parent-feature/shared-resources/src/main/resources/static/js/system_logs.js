$(function() {
	$(document).on("click", ".systemlogs", function(event) {
		// Get attribute 
		var functionCode = $(this).data("systemlogs-code");
		var logSummary= $(this).data("systemlogs-summary");
		var logType = $(this).data("systemlogs-type");
		var logDetail = $(this).data("systemlogs-detail");
		var username = $(this).data("systemlogs-username");
		// Write system logs
		writeSystemLogs(functionCode, logSummary, logType, logDetail, username)
	});
});


/**
 * write system logs
 * 
 * @param functionCode
 * @param logSummary
 * @param logType
 * @param logDetail
 * @returns
 */
function writeSystemLogs(functionCode, logSummary, logType, logDetail, username) {
	
	// Set URL
	var url = "system-logs/writeSystemLogs"
		
	// Set data
	var data = {
		"functionCode" : functionCode,
		"logSummary" : logSummary,
		"logType" : logType,
		"logDetail" : logDetail,
		"username" : username
	};
	
	// check mandatory to write system logs
	if (functionCode !== undefined && logSummary !== undefined) {
		$.ajax({
			type : "POST",
			url : BASE_URL + url,
			data : data,
			async: true,
			error : function(xhr, textStatus, error) {
				console.log(xhr);
				console.log(textStatus);
				console.log(error);
			}
		});
	}
}
