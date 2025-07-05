SELECT
    m_role.id                AS id
    , m_role.code 			 AS code
	, m_role.name            AS name
	, m_role.description     AS description
	, status_code.code       AS status_code
FROM
	JCA_ROLE m_role
LEFT JOIN
    JCA_CONSTANT status_code
ON
    status_code.type = 'M04'
    AND status_code.cat = m_role.active
WHERE
	m_role.DELETED_ID = 0
	AND m_role.role_type = '1'
	/*BEGIN*/
	AND(
		/*IF roleSearchDto.code != null && roleSearchDto.code != ''*/
		OR replace(m_role.code,' ','') LIKE CONCAT( '%' ,/*roleSearchDto.code*/, '%' )
		/*END*/
		
		/*IF roleSearchDto.name != null && roleSearchDto.name != ''*/
		OR replace(m_role.name,' ','') LIKE CONCAT( '%' ,/*roleSearchDto.name*/, '%' )
		/*END*/
		
	    /*IF roleSearchDto.description != null && roleSearchDto.description != ''*/
        OR replace(m_role.description,' ','') LIKE CONCAT( '%' ,/*roleSearchDto.description*/, '%' )
        /*END*/
    )
    /*END*/
ORDER BY
    m_role.name ASC
 	LIMIT  /*sizeOfPage*/ OFFSET  /*offset*/ 
