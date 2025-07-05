SELECT
      ITEM.id                AS id
    , ITEM.code 			AS code
	, ITEM.name            AS name
	, ITEM.display_order AS display_order
	, ITEM.description     AS description
	, ITEM.CREATED_BY     AS created_by
	, co.id 			  	 AS company_id
	, co.name 			  	 AS company_name
	, case when co.name is null then N'ZZZ' else co.name end AS order_name
FROM
	SLA_CALENDAR_TYPE ITEM

LEFT JOIN
	jca_company co ON ITEM.company_id = co.id and co.DELETED_ID = 0
WHERE
	ITEM.DELETED_ID = 0
	/*IF search.companyId != null && search.companyId != 0*/
	AND ITEM.company_id = /*search.companyId*/1
	/*END*/
	/*IF search.companyId == null*/
	AND ITEM.company_id is null
	/*END*/
	/*IF search.companyId == 0 && !search.companyAdmin*/
	AND (ITEM.company_id  IN /*search.companyIdList*/()
	OR ITEM.company_id IS NULL)
	/*END*/
	/*BEGIN*/
	AND(
		/*IF search.code != null && search.code != ''*/
		OR UPPER(ITEM.code) LIKE CONCAT( '%', CONCAT(UPPER(/*search.code*/) , '%') )
		/*END*/
			
		/*IF search.name != null && search.name != ''*/
		OR UPPER(ITEM.name) LIKE CONCAT( '%', CONCAT(UPPER(/*search.name*/) , '%') )
		/*END*/
			
		/*IF search.description != null && search.description != ''*/
	    OR UPPER(ITEM.description) LIKE CONCAT( '%', CONCAT(UPPER(/*search.description*/) , '%') )
	    /*END*/
    )
    /*END*/
ORDER BY
	order_name,
	ITEM.display_order,
    ITEM.name ASC
	OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY