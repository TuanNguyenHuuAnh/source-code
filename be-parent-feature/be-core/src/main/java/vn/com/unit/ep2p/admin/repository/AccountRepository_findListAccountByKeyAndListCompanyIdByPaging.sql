SELECT
	*,
	ID as USER_ID
FROM
	jca_account acc
WHERE
	acc.DELETED_ID = 0
	AND acc.COMPANY_ID IN /*listCompanyId*/()
	/*IF key != null*/
	AND UPPER(acc.EMAIL) LIKE CONCAT('%',CONCAT(UPPER(/*key*/''),'%'))
		
	/*END*/
ORDER BY
	acc.USERNAME
	, acc.EMAIL
/*IF isPaging == 1*/
	OFFSET /*pageIndex*/ ROWS FETCH NEXT  /*pageSize*/ ROWS ONLY
/*END*/