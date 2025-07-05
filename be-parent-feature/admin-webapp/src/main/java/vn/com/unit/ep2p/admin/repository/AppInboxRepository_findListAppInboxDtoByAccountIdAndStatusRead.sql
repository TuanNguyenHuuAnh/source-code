SELECT 
    app.ID 
    , app.ACCOUNT_ID 
    , app.TITLE 
    , app.read_flag 
    , app.DESCRIPTION
    , app.FIREBASE_RESPONSE 
    , app.CREATED_DATE
    , app.JSON_DATA
FROM 
	JCA_APP_INBOX app
WHERE
   	app.DELETED_ID = 0
   	AND app.JSON_DATA IS NOT NULL
   	AND app.ACCOUNT_ID = /*accountId*/
ORDER BY
    app.read_flag, app.CREATED_DATE DESC 
OFFSET /*offset*/ ROWS FETCH NEXT /*sizeOfPage*/ ROWS ONLY