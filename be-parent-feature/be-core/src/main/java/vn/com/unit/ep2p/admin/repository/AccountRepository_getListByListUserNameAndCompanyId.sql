
SELECT
    *    
FROM
    jca_account m_account
WHERE
	m_account.DELETED_ID = 0
	AND m_account.username IN /*userName*/() 
	AND m_account.ENABLED = 1
	/*IF companyId !=null*/
	AND company_id = /*companyId*/
	/*END*/
ORDER BY m_account.ID
	