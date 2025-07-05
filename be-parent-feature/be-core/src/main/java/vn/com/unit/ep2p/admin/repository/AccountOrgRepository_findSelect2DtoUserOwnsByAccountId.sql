SELECT
    m_acc_org.org_id AS id,
    org.org_name AS name,
    org.org_name AS text,
    m_acc_org.EFFECTED_DATE
FROM
    jca_account_org m_acc_org
	INNER JOIN JCA_M_ORG org ON m_acc_org.org_id = org.id AND org.DELETED_ID = 0
WHERE m_acc_org.account_id = /*accountId*/1105
	AND m_acc_org.org_id <> 0
	AND m_acc_org.ASSIGN_TYPE = '1'
	/*IF keySearch != null && keySearch != ''*/
	AND UPPER(org.org_name) like concat('%',  concat(UPPER(/*keySearch*/''), '%'))
	/*END*/

ORDER BY org.ORG_LEVEL, EFFECTED_DATE
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/