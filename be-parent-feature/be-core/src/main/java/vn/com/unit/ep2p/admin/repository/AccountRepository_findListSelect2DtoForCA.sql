SELECT
    m_acc.id AS id,
    m_acc.username AS name,
    m_acc.username AS text
FROM
    jca_account m_acc
WHERE m_acc.DELETED_ID = 0
	AND m_acc.ENABLED = 1
	AND m_acc.company_id = /*companyId*/
	/*IF keySearch != null && keySearch != ''*/
	AND UPPER(m_acc.username) like concat('%',  concat(UPPER(/*keySearch*/''), '%'))
	/*END*/
	AND m_acc.id NOT IN (SELECT ca.account_id 
						FROM JCA_ACCOUNT_CA ca
						WHERE ca.DELETED_ID = 0
						AND ca.account_id IS NOT NULL
						/*IF accountId != null && accountId != ''*/
						AND ca.account_id <> /*accountId*/
						/*END*/
						)
ORDER BY m_acc.username
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/