SELECT 
  COUNT(1)
FROM  
  JCA_APP_INBOX app_inbox
WHERE
	app_inbox.DELETED_ID = 0
	AND app_inbox.READ_FLAG = 0
	AND app_inbox.ACCOUNT_ID = /*userId*/
	
	