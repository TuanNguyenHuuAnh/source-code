SELECT
    m_role.id
 	, m_role.code 	
    , m_role.name
    , m_role.description
    , m_role.actived 
FROM
    JCA_ROLE m_role
WHERE DELETED_ID = 0
   	AND UPPER (m_role.code) = UPPER (/*code*/)
	/*IF companyId != null*/
	AND (company_id is null or company_id = /*companyId*/1)
	/*END*/