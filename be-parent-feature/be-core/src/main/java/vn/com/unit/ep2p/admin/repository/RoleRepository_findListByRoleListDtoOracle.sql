SELECT
    m_role.id                	AS id
    , m_role.code 			 	AS code
	, m_role.name            	AS name
	, item.cat_official_name    AS role_type
	, m_role.description     	AS description
	, status_code.code       	AS status_code
FROM
	JCA_ROLE m_role
LEFT JOIN
    JCA_CONSTANT status_code
ON
    status_code.type = 'M04'
    AND status_code.cat = m_role.active
LEFT JOIN
    JCA_CONSTANT item
ON
    item.type = 'ROLETP'
    AND item.code = m_role.role_type
WHERE
	m_role.DELETED_ID = 0
	/*BEGIN*/
	AND(
			/*IF roleSearchDto.code != null && roleSearchDto.code != ''*/
			OR replace(UPPER(m_role.code),' ','') LIKE ( '%' || UPPER(TRIM(/*roleSearchDto.code*/)) || '%' )
			/*END*/
			
			/*IF roleSearchDto.name != null && roleSearchDto.name != ''*/
			OR replace(UPPER(m_role.name),' ','') LIKE ( '%' || UPPER(TRIM(/*roleSearchDto.name*/)) || '%' )
			/*END*/
			
		    /*IF roleSearchDto.description != null && roleSearchDto.description != ''*/
	        OR replace(UPPER(m_role.description),' ','') LIKE ( '%' || UPPER(TRIM(/*roleSearchDto.description*/)) || '%' )
	        /*END*/
    )
    /*END*/
ORDER BY
    m_role.name
	OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY