SELECT *
FROM jca_m_account
WHERE DELETED_ID = 0 AND ENABLED = 1 
	AND code = /*code*/
	AND company_id = /*companyId*/