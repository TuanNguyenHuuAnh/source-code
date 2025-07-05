SELECT 
  app_inbox.ID              AS APP_INBOX_ID
  ,app_inbox.ACCOUNT_ID     AS USER_ID
  ,app_inbox.TITLE          AS TITLE
  ,app_inbox.DESCRIPTION    AS DESCRIPTION
  ,app_inbox.READ_FLAG      AS READ_FLAG
  ,app_inbox.DATA           AS DATA
  ,app_inbox.RESPONSE_JSON  AS RESPONSE_JSON
FROM  
  JCA_APP_INBOX app_inbox
WHERE
	app_inbox.DELETED_ID = 0
	AND app_inbox.ACCOUNT_ID = /*userId*/
	
	
/*IF orders != null*/
ORDER BY /*$orders*/
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/