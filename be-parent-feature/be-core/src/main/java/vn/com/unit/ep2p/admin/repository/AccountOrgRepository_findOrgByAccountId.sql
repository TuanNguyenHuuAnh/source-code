SELECT
    m_acc_org.org_id AS id,
    org.org_name AS org_name,
    org.company_id AS company_id,
    1 order_display, 
    m_acc_org.EFFECTED_DATE
    , ROWNUM AS INDX 
FROM
    jca_account_org m_acc_org
	INNER JOIN JCA_M_ORG org ON m_acc_org.org_id = org.id AND org.DELETED_ID = 0
WHERE m_acc_org.account_id = /*accountId*/1105
	AND m_acc_org.org_id <> 0
	AND m_acc_org.ASSIGN_TYPE = '1'
	/*IF keySearch != null && keySearch != ''*/
	AND UPPER(org.org_name) like concat('%',  concat(UPPER(/*keySearch*/''), '%'))
	/*END*/
UNION  
SELECT  
	vie.org_id AS id,
	org.org_name AS org_name,
	org.company_id AS company_id,
	2 order_display,
	org.EFFECTED_DATE
	, ROWNUM AS INDX 
FROM VW_GET_AUTHORITY_ACCT_COMPANY vie
INNER JOIN jca_m_org org
	ON  org.ID = vie.ORG_ID
WHERE vie.ORG_ID is not null
	AND vie.ACCOUNT_ID = /*accountId*/1105
	/*IF keySearch != null && keySearch != ''*/
	AND UPPER(org.org_name) like concat('%',  concat(UPPER(/*keySearch*/''), '%'))
	/*END*/    
ORDER BY order_display, EFFECTED_DATE
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/