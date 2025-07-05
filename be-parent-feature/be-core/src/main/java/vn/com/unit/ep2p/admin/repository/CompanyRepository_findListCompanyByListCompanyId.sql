SELECT
	*
FROM
    jca_company
WHERE DELETED_ID = 0
/*IF !companyAdmin*/
	AND ID IN /*companyIds*/()
/*END*/
ORDER BY NAME