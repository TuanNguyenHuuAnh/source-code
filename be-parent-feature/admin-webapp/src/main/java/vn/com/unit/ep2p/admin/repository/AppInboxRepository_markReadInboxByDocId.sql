UPDATE 
	JCA_APP_INBOX
SET 
	read_flag = 1
WHERE 
	DELETED_ID = 0
	AND read_flag = 0
	AND ACCOUNT_ID = /*accountId*/
	AND DOC_ID = /*docId*/