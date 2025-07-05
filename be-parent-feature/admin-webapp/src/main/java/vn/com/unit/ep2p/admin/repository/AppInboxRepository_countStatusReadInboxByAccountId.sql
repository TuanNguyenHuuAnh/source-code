SELECT count(ai.ACCOUNT_ID)
FROM JCA_APP_INBOX ai
WHERE 
	ai.ACCOUNT_ID = /*accountId*/
	and read_flag = /*isRead*/
	and ai.DELETED_ID = 0
	AND ai.RESPONSE_JSON IS NOT NULL