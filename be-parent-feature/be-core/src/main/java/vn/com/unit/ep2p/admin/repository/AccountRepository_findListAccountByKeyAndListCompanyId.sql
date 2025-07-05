SELECT
	acc.ID 																			AS ID
	, CONCAT(acc.FULLNAME, CONCAT(' (', CONCAT(acc.EMAIL,')')))						AS TEXT
	, acc.USERNAME 																	AS NAME
  	, CASE WHEN acct_org.ORG_ID = /*orgId*/1039 THEN 1 ELSE 2 END 					AS SORT_ORDER
FROM
	jca_account acc
LEFT JOIN JCA_M_ACCOUNT_ORG acct_org ON acct_org.ACCOUNT_ID = acc.id AND acct_org.DELETED_ID = 0 AND acct_org.ASSIGN_TYPE = 1
WHERE
	acc.DELETED_ID = 0
	AND acc.ENABLED = 1
	AND acc.COMPANY_ID IN /*listCompanyId*/()
	/*IF key != null*/
	AND (
		FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(CONCAT(acc.FULLNAME, CONCAT(' (', CONCAT(acc.EMAIL,')'))))) 
			LIKE CONCAT('%',CONCAT(FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(/*key*/'')),'%'))
	)
	/*END*/
	/*IF orgId != null && null != accountId*/
	AND acc.ID <> /*accountId*/ 
	/*END*/
UNION
SELECT 
	DISPLAY_EMAIL.ACCOUNT_ID 															AS ID
	, CONCAT(DISPLAY_EMAIL.FULLNAME, CONCAT(' (', CONCAT(DISPLAY_EMAIL.EMAIL,')')))		AS TEXT
	, DISPLAY_EMAIL.USERNAME 															AS NAME
  	, 3 																				AS SORT_ORDER
FROM (
	SELECT 
		acct.ID ACCOUNT_ID
		,acct.USERNAME
		,acct.FULLNAME
		,acct.EMAIL
		,display_email.ORG_ID
		,org.ORG_CODE
		,org.ORG_NAME
	FROM jca_account acct
	INNER JOIN JCA_M_ACCOUNT_ORG acct_org ON acct_org.ACCOUNT_ID = acct.ID AND acct_org.DELETED_ID = 0 AND acct_org.ASSIGN_TYPE = 1    
	INNER JOIN JCA_ROLE_for_display_email display_email ON display_email.ORG_ID = acct_org.ORG_ID  AND display_email.DELETED_ID = 0 AND display_email.DEL_FLG = 0
	INNER JOIN (
	    SELECT ACCOUNT_ID, ROLE_ID 
	    FROM VW_GET_USER_ROLE 
	    WHERE ACCOUNT_ID = /*accountId*/ 
	    GROUP BY ACCOUNT_ID, ROLE_ID    
	) user_role ON display_email.ROLE_ID =  user_role.ROLE_ID
	INNER JOIN JCA_M_ORG org ON org.id = display_email.ORG_ID and org.DELETED_ID = 0
	WHERE acct.DELETED_ID = 0 AND acct.ENABLED = 1
) DISPLAY_EMAIL

WHERE 
	1=1
	/*IF key != null*/
	AND (
		FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(CONCAT(DISPLAY_EMAIL.FULLNAME, CONCAT(' (', CONCAT(DISPLAY_EMAIL.EMAIL,')')))))
			LIKE CONCAT('%',CONCAT(FN_CONVERT_TO_VN(UPPER(/*key*/'')),'%'))
	)
	/*END*/
	/*IF orgId != null && null != accountId*/
	AND DISPLAY_EMAIL.ACCOUNT_ID <> /*accountId*/ 
	/*END*/
ORDER BY
   SORT_ORDER, NAME
/*IF isPaging*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/