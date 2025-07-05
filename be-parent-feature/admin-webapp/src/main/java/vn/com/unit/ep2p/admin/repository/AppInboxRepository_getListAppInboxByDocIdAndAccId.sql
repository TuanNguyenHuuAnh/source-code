SELECT
	app.*
FROM
	JCA_APP_INBOX app
WHERE 
	app.DELETED_ID = 0
	AND app.read_flag = 0
	AND app.ACCOUNT_ID = /*accountId*/
	AND app.DOC_ID = /*docId*/