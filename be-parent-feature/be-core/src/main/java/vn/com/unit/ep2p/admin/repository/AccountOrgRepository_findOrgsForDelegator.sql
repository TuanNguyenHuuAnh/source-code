SELECT DISTINCT 
    m_acc_org.ORG_ID        AS ID
    , org.ORG_NAME          AS ORG_NAME 
    , org.COMPANY_ID        AS COMPANY_ID
    , 1                     ORDER_DISPLAY 
FROM (SELECT 
    grp.TEAM_ID
FROM JCA_DELEGATE delegate
LEFT JOIN
 	JCA_DELEGATE_GROUP grp 
 ON
 	grp.DELEGATE_ID = delegate.ID 
WHERE 
    grp.DELETED_ID = 0 
    AND delegate.DELETED_ID = 0 
    AND delegate.STATUS IN ('APPLIED', 'RUNNING') 
    AND grp.ACCOUNT_ID = /*accountId*/1950 
    AND delegate.EFFECTED_DATE <= /*expiredDate*/sysdate 
	AND delegate.EXPIRED_DATE >= /*expiredDate*/sysdate
) del 
LEFT JOIN jca_account_org m_acc_org ON m_acc_org.ORG_ID = del.TEAM_ID 
INNER JOIN JCA_M_ORG org ON m_acc_org.ORG_ID = org.ID AND org.DELETED_ID = 0 
WHERE 
    m_acc_org.org_id <> 0
	AND m_acc_org.ASSIGN_TYPE = '1' 
	/*IF ids != null */
	AND m_acc_org.ORG_ID NOT IN /*ids*/(0) 
	/*END*/