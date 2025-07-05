SELECT
    LANG.NAME AS NAME
	,item.id as category_id
    ,item.description
    ,item.display_order
    ,item.ACTIVED
    ,co.id 			  AS company_id
	,co.name 		  AS company_name
	,LANG.Name 		AS category_name
FROM 
	EFO_CATEGORY ITEM
	LEFT JOIN jca_company co ON item.company_id = co.id and co.DELETED_ID = 0
    LEFT JOIN EFO_CATEGORY_LANG LANG ON ITEM.ID = LANG.CATEGORY_ID AND UPPER(LANG.LANG_CODE) = UPPER(/*languageCode*/'')
WHERE
	ITEM.DELETED_ID = 0
    
	/*IF search.companyId != null && search.companyId != 0*/
	AND ITEM.COMPANY_ID = /*search.companyId*/
	/*END*/
	/*IF search.companyId == 0 && !search.companyAdmin*/
	AND ITEM.COMPANY_ID  IN /*search.companyIdList*/()
	/*END*/
	
	/*BEGIN*/
	AND (
    /*IF search.name != null && search.name != ''*/
	UPPER(LANG.Name) LIKE UPPER(concat(concat('%',  /*search.name*/''), '%'))
	/*END*/
	/*IF search.description != null && search.description != ''*/
	OR UPPER(ITEM.description) LIKE UPPER(concat(concat('%',  /*search.description*/''), '%'))
	/*END*/
	)
	/*END*/
ORDER BY
	ITEM.display_order,
	LANG.NAME
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY