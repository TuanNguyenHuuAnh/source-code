UPDATE 
(SELECT 
  lang.DELETED_ID             AS DELETED_ID
  , lang.DELETED_DATE         AS DELETED_DATE
 FROM 
  JCA_APP_INBOX_LANG lang
 INNER JOIN 
  JCA_APP_INBOX app_inbox
 ON 
  app_inbox.ID = lang.APP_INBOX_ID
 WHERE 
  app_inbox.DELETED_ID = 0
  AND app_inbox.ACCOUNT_ID = /*userId*/
) t
SET 
  t.DELETED_ID = /*userId*/
  , t.DELETED_DATE = /*sysDate*/