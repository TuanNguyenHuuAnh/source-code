SELECT
    m_acc_org.org_id AS id,
    org.org_name AS name,
    org.org_name AS text,
    1 order_display,
    m_acc_org.EFFECTED_DATE
FROM
    jca_account_org m_acc_org
	INNER JOIN JCA_M_ORG org ON m_acc_org.org_id = org.id AND org.DELETED_ID = 0
WHERE m_acc_org.account_id = /*accountId*/1105
	AND m_acc_org.org_id <> 0
	AND m_acc_org.ASSIGN_TYPE = '1'
	/*IF keySearch != null && keySearch != ''*/
	AND FN_CONVERT_TO_VN(UPPER(org.org_name)) like concat('%',  concat(FN_CONVERT_TO_VN(UPPER(/*keySearch*/'')), '%'))
	/*END*/
	/*IF companyId != null */
	AND org.company_id = /*companyId*/
	/*END*/
UNION  SELECT  vie.org_id AS id,
		    org.org_name AS name,
		    org.org_name AS text,
		    2 order_display,
        sysdate EFFECTED_DATE
    	FROM VW_GET_AUTHORITY_ACCT_COMPANY vie
    		INNER JOIN jca_m_org org
    		ON  org.ID = vie.ORG_ID
    	WHERE vie.ORG_ID is not null
    		AND vie.ACCOUNT_ID = /*accountId*/1105
    		/*IF keySearch != null && keySearch != ''*/
			AND FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(org.org_name)) like concat('%',  concat(FN_CONVERT_TO_VN_ONLY_UPPER(UPPER(/*keySearch*/'')), '%'))
			/*END*/
			/*IF companyId != null */
			AND org.company_id = /*companyId*/
			/*END*/
ORDER BY order_display, EFFECTED_DATE
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/