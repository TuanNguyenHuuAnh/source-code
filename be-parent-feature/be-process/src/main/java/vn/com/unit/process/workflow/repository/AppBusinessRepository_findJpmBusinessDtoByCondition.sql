SELECT bu.ID           AS id,
	   bu.CODE         AS code,
	   bu.NAME         AS name,
	   bu.DESCRIPTION  AS description,
	   co.NAME         AS company_name,
	   bu.IS_ACTIVE    AS is_active,
	   CASE WHEN co.name IS NULL THEN N'ZZZ' ELSE co.name END AS order_name
	   , CASE WHEN
	   		UPPER(bu.CREATED_BY) = 'APP_SYSTEM' THEN 1
	   ELSE
	   		0 END AS IS_SYSTEM
FROM
	JPM_BUSINESS bu
LEFT JOIN
	jca_company co 
ON  bu.COMPANY_ID = co.ID
    AND co.DELETED_ID = 0
WHERE
	bu.DELETED_ID = 0

	/*IF searchDto.companyId != null && searchDto.companyId != 0*/
	AND ( bu.COMPANY_ID = /*searchDto.companyId*/ OR bu.COMPANY_ID IS NULL )
	/*END*/
	/*IF searchDto.companyId == 0 && !searchDto.companyAdmin*/
	AND ( bu.COMPANY_ID  IN /*searchDto.companyIdList*/() OR bu.COMPANY_ID IS NULL )
	/*END*/
	
	/*BEGIN*/
	AND (
	    /*IF searchDto.code != null && searchDto.code != ''*/
		UPPER(bu.CODE) LIKE CONCAT(CONCAT('%', UPPER(/*searchDto.code*/'')), '%')
		/*END*/
	
		/*IF searchDto.name != null && searchDto.name != ''*/
		OR UPPER(bu.NAME) LIKE CONCAT(CONCAT('%', UPPER(/*searchDto.name*/'')), '%')
		/*END*/
	)
	/*END*/
ORDER BY 
	ORDER_NAME
	, bu.NAME
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY