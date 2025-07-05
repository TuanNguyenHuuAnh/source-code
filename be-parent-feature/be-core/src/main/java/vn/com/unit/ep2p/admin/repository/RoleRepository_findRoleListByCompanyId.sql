SELECT
    m_role.id
    , m_role.code
    , m_role.name
    , m_role.description
    , m_role.actived
    , co.name AS company_name
    , case when co.name is null then N'ZZZ' else co.name end AS order_name
FROM
    JCA_ROLE m_role
LEFT JOIN
	jca_company co ON m_role.company_id = co.id and co.DELETED_ID = 0
WHERE
    m_role.DELETED_ID = 0
    AND m_role.actived = 1
    /*IF companyId != null*/
	AND m_role.company_id = /*companyId*/1
	/*END*/
ORDER BY
	order_name,
    m_role.name ASC
