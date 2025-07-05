UPDATE 
  JCA_APP_INBOX
SET  
  DELETED_ID = /*userId*/
  ,DELETED_DATE = /*sysDate*/
WHERE
	DELETED_ID = 0
	AND ACCOUNT_ID = /*userId*/
	
	