SELECT 
	vie.ACCOUNT_ID 																	AS ID
	, CONCAT(vie.FULLNAME, CONCAT(' (', CONCAT(vie.EMAIL,')')))						AS TEXT
	, vie.USERNAME 																	AS NAME
FROM (
	SELECT 
		acct.id ACCOUNT_ID
		,acct.USERNAME
		,acct.FULLNAME
		,acct.EMAIL
		,acct_org.ORG_ID
	FROM JCA_ACCOUNT acct
	INNER JOIN jca_account_org acct_org 
	ON acct_org.ACCOUNT_ID = acct.id AND acct_org.DELETED_ID = 0 AND acct_org.ASSIGN_TYPE = 1
	WHERE acct.DELETED_ID = 0 AND acct.ENABLED = 1
) vie
WHERE 
	vie.ORG_ID = /*orgId*/1039
	/*IF key != null*/
	AND (
		UPPER(vie.EMAIL) LIKE CONCAT('%',CONCAT(UPPER(/*key*/''),'%'))
		OR UPPER(vie.FULLNAME) LIKE CONCAT('%',CONCAT(UPPER(/*key*/''),'%'))
	)
	/*END*/
ORDER BY
	TEXT
/*IF isPaging*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/