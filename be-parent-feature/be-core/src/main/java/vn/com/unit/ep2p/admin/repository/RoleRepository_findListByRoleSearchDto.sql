SELECT
    m_role.id                AS id
    , m_role.code 			 AS code
	, m_role.name            AS name
	, m_role.description     AS description
	, status_code.code       AS status_code
	, co.id 			  	 AS company_id
	, co.name 			  	 AS company_name
	, case when co.name is null then N'ZZZ' else co.name end AS order_name
FROM
	JCA_ROLE m_role
LEFT JOIN
    JCA_CONSTANT status_code
ON
    status_code.type = 'M04'
    AND status_code.cat = m_role.actived
LEFT JOIN
	jca_company co ON m_role.company_id = co.id and co.DELETED_ID = 0
WHERE
	m_role.DELETED_ID = 0
	/*IF roleSearchDto.companyId != null && roleSearchDto.companyId != 0*/
	AND m_role.COMPANY_ID = /*roleSearchDto.companyId*/
	/*END*/
	/*IF roleSearchDto.companyId == null*/
	AND m_role.COMPANY_ID IS NULL
	/*END*/
	/*IF roleSearchDto.companyId == 0 && !roleSearchDto.companyAdmin*/
	AND (m_role.COMPANY_ID  IN /*roleSearchDto.companyIdList*/()
	OR m_role.COMPANY_ID IS NULL)
	/*END*/
	AND m_role.role_type = '1'
	/*BEGIN*/
	AND(
		/*IF roleSearchDto.code != null && roleSearchDto.code != ''*/
		OR UPPER(m_role.code) LIKE CONCAT( '%', CONCAT(UPPER(/*roleSearchDto.code*/), '%') )
		/*END*/
		
		/*IF roleSearchDto.name != null && roleSearchDto.name != ''*/
		OR UPPER(m_role.name) LIKE CONCAT( '%', CONCAT(UPPER(/*roleSearchDto.name*/), '%') )
		/*END*/
		
	    /*IF roleSearchDto.description != null && roleSearchDto.description != ''*/
        OR UPPER(m_role.description) LIKE CONCAT( '%', CONCAT(UPPER(/*roleSearchDto.description*/), '%') )
        /*END*/
    )
    /*END*/
ORDER BY
	order_name,
    m_role.name ASC
	OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY