SELECT 
	acc.ID AS USER_ID,
	acc.*
FROM 
	JCA_ACCOUNT acc
LEFT JOIN
	JCA_ACCOUNT_TEAM acctea
ON 
	acc.ID = acctea.ACCOUNT_ID 

WHERE
	acc.DELETED_ID = 0
	AND acc.ENABLED = '1'
	AND acctea.TEAM_ID = /*teamId*/
	/*IF keySearch != null || keySearch != ''*/
	AND	(
		UPPER(acc.USERNAME) LIKE UPPER(CONCAT('%', CONCAT(/*keySearch*/'', '%')))
		OR UPPER(acc.EMAIL) LIKE UPPER(CONCAT('%', CONCAT(/*keySearch*/'', '%')))
		OR UPPER(acc.FULLNAME) LIKE UPPER(CONCAT('%', CONCAT(/*keySearch*/'', '%')))
	)
	/*END*/
ORDER BY
	acctea.CREATED_DATE DESC
OFFSET /*startIndex*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY