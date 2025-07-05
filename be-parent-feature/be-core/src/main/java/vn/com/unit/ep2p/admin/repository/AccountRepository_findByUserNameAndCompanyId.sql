--
-- AccountRepository_findByUserNameAndCompanyId.sql

SELECT
  *
FROM
    jca_account m_account with (nolock)
WHERE
	m_account.DELETED_ID = 0
	AND m_account.ENABLED = 1
	AND (
		m_account.username = /*username*/
	OR
		m_account.email = /*username*/
		)
	/*IF companyId != null*/
	AND company_id = /*companyId*/1
	/*END*/