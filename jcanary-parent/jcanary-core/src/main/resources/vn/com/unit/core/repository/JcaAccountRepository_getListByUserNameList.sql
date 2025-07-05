
SELECT
    *    
FROM
    jca_account m_account
WHERE
	m_account.DELETED_ID = 0
	AND m_account.username in (select value from STRING_SPLIT(/*userNameList*/'114922,131516,138188,139784,140117,141315', ','))
	AND m_account.ENABLED = 1
	