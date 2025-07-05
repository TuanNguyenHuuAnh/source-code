SELECT
    DISTINCT EMAIL
FROM jca_account m_account
WHERE
	m_account.DELETED_ID = 0
	AND m_account.ENABLED = 1
	AND m_account.username = /*username*/''