MERGE
INTO
  JCA_APP_INBOX_HI appHis
USING (
    SELECT
       appInbox.ID                  AS  ID
      ,appInbox.ACCOUNT_ID         AS  ACCOUNT_ID
      ,appInbox.TITLE               AS  TITLE
      ,appInbox.read_flag             AS  read_flag
      ,appInbox.DELETED_BY          AS  DELETED_BY
      ,appInbox.DESCRIPTION         AS  DESCRIPTION
      ,appInbox.FIREBASE_RESPONSE   AS  FIREBASE_RESPONSE
      ,appInbox.CREATED_DATE        AS  CREATED_DATE
      ,appInbox.UPDATED_DATE        AS  UPDATED_DATE
      ,appInbox.DELETED_DATE        AS  DELETED_DATE
      ,appInbox.CREATED_BY          AS  CREATED_BY
      ,appInbox.UPDATED_BY          AS  UPDATED_BY
      ,appInbox.DATA                AS  DATA
      ,appInbox.JSON_DATA           AS  JSON_DATA
      ,appInbox.DOC_ID              AS  DOC_ID
    FROM
      JCA_APP_INBOX appInbox
    WHERE
	 	appInbox.ID IN /*ids*/()
  )
  appInbox
ON
  (
    appInbox.ID = appHis.JCA_APP_INBOX_ID
  )
WHEN NOT MATCHED THEN
INSERT
  (JCA_APP_INBOX_ID
,ACCOUNT_ID
,TITLE
,read_flag
,JCA_APP_INBOX_DELETED_BY
,DESCRIPTION
,FIREBASE_RESPONSE
,JCA_APP_INBOX_CREATED_DATE
,JCA_APP_INBOX_UPDATED_DATE
,JCA_APP_INBOX_DELETED_DATE
,JCA_APP_INBOX_CREATED_BY
,JCA_APP_INBOX_UPDATED_BY
,DATA
,JSON_DATA
,DOC_ID
,CREATED_BY
,CREATED_DATE
  )
  VALUES
  ( appInbox.ID       
  ,appInbox.ACCOUNT_ID   
  ,appInbox.TITLE       
  ,appInbox.read_flag     
  ,appInbox.DELETED_BY    
  ,appInbox.DESCRIPTION   
  ,appInbox.FIREBASE_RESPONSE 
  ,appInbox.CREATED_DATE    
  ,appInbox.UPDATED_DATE    
  ,appInbox.DELETED_DATE    
  ,appInbox.CREATED_BY    
  ,appInbox.UPDATED_BY    
  ,appInbox.DATA        
  ,appInbox.JSON_DATA     
  ,appInbox.DOC_ID 
  ,appInbox.CREATED_BY     
  , SYSDATE
  )
;