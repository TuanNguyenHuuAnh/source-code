UPDATE 
	JCA_APP_INBOX
SET 
	read_flag = /*isRead*/
WHERE 
	DELETED_ID = 0
	AND ACCOUNT_ID = /*accountId*/