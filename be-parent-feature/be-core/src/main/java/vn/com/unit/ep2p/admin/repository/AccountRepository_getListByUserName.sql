--
-- AccountRepository_getListByUserName.sql

SELECT
    *    
FROM
    jca_account m_account with (nolock)
WHERE
	m_account.DELETED_ID = 0
  /*IF username != null */
	AND (
		m_account.username = /*username*/
	OR
		m_account.email = /*username*/
	OR
	    m_account.PHONE =  /*username*/
		)
    /*END*/
	AND m_account.ENABLED = 1
	