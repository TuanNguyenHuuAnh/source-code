select count(1) from (SELECT
	acc.ID 																			AS ID
	, CONCAT(acc.FULLNAME, CONCAT(' (', CONCAT(acc.EMAIL,')')))						AS TEXT
	, acc.USERNAME 																	AS NAME
	, acc_pos.POSITION_NAME_MERGE													AS POSITION_NAME
	, acc_org.ORG_NAME_MERGE                                                       	AS DEPARTMENT_NAME
  	, CASE WHEN acct_org.ORG_ID = /*orgId*/1039 THEN 1 ELSE 2 END 					AS SORT_ORDER
FROM
	jca_account acc
LEFT JOIN JCA_M_ACCOUNT_ORG acct_org ON acct_org.ACCOUNT_ID = acc.id AND acct_org.DELETED_ID = 0 AND acct_org.ASSIGN_TYPE = 1
LEFT JOIN VW_GET_ORG_FOR_ACCOUNT acc_org ON acc.ID = acc_org.ACCOUNT_ID AND acc_org.RN = 1
LEFT JOIN VW_GET_POSITION_FOR_ACCOUNT acc_pos ON acc.ID = acc_pos.ACCOUNT_ID AND acc_pos.RN = 1
WHERE
	acc.DELETED_ID = 0
	AND acc.ENABLED = 1
	AND acc.COMPANY_ID IN /*listCompanyId*/()
	/*IF key != null*/
	AND (
		UPPER(acc.EMAIL) LIKE CONCAT('%',CONCAT(UPPER(/*key*/''),'%'))
		OR FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(acc.FULLNAME)) LIKE CONCAT('%',CONCAT(FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(/*key*/'')),'%'))
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
	, POSITION_NAME
	, DEPARTMENT_NAME
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
		, acc_pos.POSITION_NAME_MERGE													AS POSITION_NAME
		, acc_org.ORG_NAME_MERGE                                                       	AS DEPARTMENT_NAME
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
	LEFT JOIN VW_GET_ORG_FOR_ACCOUNT acc_org ON acct.ID = acc_org.ACCOUNT_ID AND acc_org.RN = 1
	LEFT JOIN VW_GET_POSITION_FOR_ACCOUNT acc_pos ON acct.ID = acc_pos.ACCOUNT_ID AND acc_pos.RN = 1
	WHERE acct.DELETED_ID = 0 AND acct.ENABLED = 1
) DISPLAY_EMAIL

WHERE 
	1=1
	/*IF key != null*/
	AND (
		UPPER(DISPLAY_EMAIL.EMAIL) LIKE CONCAT('%',CONCAT(UPPER(/*key*/''),'%'))
		OR FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(DISPLAY_EMAIL.FULLNAME)) LIKE CONCAT('%',CONCAT(FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(/*key*/'')),'%'))
	)
	/*END*/
	/*IF orgId != null && null != accountId*/
	AND DISPLAY_EMAIL.ACCOUNT_ID <> /*accountId*/ 
	/*END*/
)