SELECT
	*
FROM
	JCA_MENU menu
WHERE
	isnull(menu.DELETED_ID,0) = 0
    AND UPPER(menu.code) = UPPER (/*code*/)
    /*IF companyId != null*/
	AND (menu.company_id = /*companyId*/1)
	/*END*/
    