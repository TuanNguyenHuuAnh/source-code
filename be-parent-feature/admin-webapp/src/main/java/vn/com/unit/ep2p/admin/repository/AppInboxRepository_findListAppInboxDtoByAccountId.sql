SELECT
    app.ID 
    , app.ACCOUNT_ID 
    , app.read_flag 
    , app.FIREBASE_RESPONSE 
    , app.CREATED_DATE 
    , app.DATA
    , app.JSON_DATA
FROM
	JCA_APP_INBOX	app
WHERE
	app.DELETED_ID = 0
	AND app.JSON_DATA IS NOT NULL
	AND app.ACCOUNT_ID = /*accountId*/0
ORDER BY
	app.CREATED_DATE DESC
/*IF isPaging == 1*/
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY
/*END*/