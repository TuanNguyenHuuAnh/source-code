UPDATE 
	SLA_ALERT 
SET 
	STATUS = /*status*/
	/*IF updateCountFlag == true*/
	, COUNT_SENDING_ERROR = COUNT_SENDING_ERROR + 1 
	/*END*/
WHERE 
	DELETED_ID = 0 
	AND ID IN /*idList*/()