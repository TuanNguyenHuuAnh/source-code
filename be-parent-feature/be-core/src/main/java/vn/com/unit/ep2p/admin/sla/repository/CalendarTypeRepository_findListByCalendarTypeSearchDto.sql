SELECT

    , ITEM.code 			 AS code
	, ITEM.name            AS name
	, ITEM.description     AS description
	, co.id 			  	 AS company_id
	, co.name 			  	 AS company_name
	, case when co.name is null then N'ZZZ' else co.name end AS order_name
FROM
	SLA_CALENDAR_TYPE ITEM
	
LEFT JOIN
	jca_company co ON ITEM.company_id = co.id and co.DELETED_ID = 0
WHERE
	ITEM.DELETED_ID = 0
		
		AND (company_id is null or company_id = /*calendarTypeSearchDto.companyId*/1)
		/*BEGIN*/
		AND(
			/*IF calendarTypeSearchDto.code != null && calendarTypeSearchDto.code!=''*/
			OR replace(code,' ','') LIKE CONCAT( '%', CONCAT(/*calendarTypeSearchDto.code*/ , '%') )
			/*END*/
			/*IF calendarTypeSearchDto.name != null && calendarTypeSearchDto.name!=''*/
			OR replace(name,' ','') LIKE CONCAT( '%', CONCAT(/*calendarTypeSearchDto.name*/ , '%') )
			/*END*/
			/*IF calendarTypeSearchDto.description != null && calendarTypeSearchDto.description!=''*/
	        OR replace(description,' ','') LIKE CONCAT( '%', CONCAT(/*calendarTypeSearchDto.description*/ , '%') )
	        /*END*/
        )
        /*END*/
ORDER BY
	order_name,
    ITEM.name ASC
	OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY