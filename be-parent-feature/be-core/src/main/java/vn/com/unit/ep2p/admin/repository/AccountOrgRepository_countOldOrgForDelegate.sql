SELECT COUNT(org.ORG_ID) 
FROM (SELECT 
    grp.TEAM_ID
	, delegate.PERFORMER_ID      AS ACCOUNT_ID 
FROM JCA_DELEGATE_GROUP grp 
LEFT JOIN
 	JCA_DELEGATE delegate
 ON
 	delegate.ID = grp.DELEGATE_ID 
WHERE 
    grp.DELETED_ID = 0 
    AND delegate.PERFORMER_ID = /*accountId*/1802
    AND delegate.ID = /*id*/1147   
) del 
LEFT JOIN (
SELECT m_acc_org.ORG_ID
        , org.ORG_NAME AS ORG_NAME 
        , m_acc_org.ACCOUNT_ID 
	FROM jca_account_org m_acc_org
	INNER JOIN JCA_M_ORG org ON m_acc_org.ORG_ID = org.ID AND org.DELETED_ID = 0
	WHERE m_acc_org.ACCOUNT_ID = /*accountId*/1802
		AND m_acc_org.ORG_ID <> 0
		AND m_acc_org.ASSIGN_TYPE = '1'
	UNION  
	SELECT vie.ORG_ID
		,org.ORG_NAME AS ORG_NAME 
        , vie.ACCOUNT_ID 
	FROM VW_GET_AUTHORITY_ACCT_COMPANY vie
	INNER JOIN jca_m_org org ON  org.ID = vie.ORG_ID
	WHERE vie.ORG_ID IS NOT NULL
		AND vie.ACCOUNT_ID = /*accountId*/1802
) org ON org.ORG_ID = del.TEAM_ID 
WHERE 
	org.ORG_ID IS NOT NULL 
	/*IF orgName != null && orgName != ''*/
	AND UPPER(org.ORG_NAME) LIKE CONCAT('%',  CONCAT(UPPER(/*orgName*/''), '%'))
	/*END*/