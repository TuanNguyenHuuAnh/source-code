UPDATE 
	JCA_APP_INBOX
SET 
	DELETED_BY = /*user*/
	, DELETED_DATE = /*sysDate*/
WHERE 
	ACCOUNT_ID = /*accountId*/
	/*IF appInboxId != null*/
	AND ID = /*appInboxId*/
	/*END*/