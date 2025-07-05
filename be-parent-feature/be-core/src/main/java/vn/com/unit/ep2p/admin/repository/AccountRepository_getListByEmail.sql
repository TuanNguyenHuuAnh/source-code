
SELECT
    *    
FROM
    jca_account m_account
WHERE
	m_account.DELETED_ID = 0
	AND m_account.email = /*email*/
	AND m_account.ENABLED = 1
	