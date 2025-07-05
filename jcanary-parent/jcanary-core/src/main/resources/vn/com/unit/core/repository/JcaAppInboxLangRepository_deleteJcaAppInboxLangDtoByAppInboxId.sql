UPDATE 
	JCA_APP_INBOX_LANG
SET 
  DELETED_ID = /*userId*/
  , DELETED_DATE = /*sysDate*/
  
WHERE 
  DELETED_ID = 0
  AND APP_INBOX_ID = /*appInboxId*/