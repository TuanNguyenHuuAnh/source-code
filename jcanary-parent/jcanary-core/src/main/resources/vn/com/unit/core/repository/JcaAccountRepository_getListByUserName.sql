
SELECT
    *    
FROM
    jca_account m_account
WHERE
	m_account.DELETED_ID = 0
	AND (
		m_account.username = /*userName*/
	OR
		m_account.email = /*userName*/
		)
	AND m_account.ENABLED = 1
	