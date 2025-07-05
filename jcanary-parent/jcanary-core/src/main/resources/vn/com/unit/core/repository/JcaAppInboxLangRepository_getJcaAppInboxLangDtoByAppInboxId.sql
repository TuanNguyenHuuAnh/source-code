SELECT 
  lang.ID             			AS APP_INBOX_LANG_ID
  , lang.APP_INBOX_ID         	AS APP_INBOX_ID
  , lang.TITLE         			AS TITLE
  , lang.DESCRIPTION         	AS DESCRIPTION
  , lang.LANG_CODE         		AS LANG_CODE

 FROM 
  JCA_APP_INBOX_LANG lang
 WHERE 
  lang.DELETED_ID = 0
  AND lang.APP_INBOX_ID = /*appInboxId*/
