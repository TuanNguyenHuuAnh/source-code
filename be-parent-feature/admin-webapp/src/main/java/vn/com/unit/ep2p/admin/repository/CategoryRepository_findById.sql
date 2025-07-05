SELECT
    LANG.NAME AS NAME
	,item.id AS CATEGORY_ID
    ,item.description
    ,item.display_order
    ,item.ACTIVED
    ,co.id 			  AS company_id
	,co.name 		  AS company_name
FROM 
	EFO_CATEGORY ITEM
	LEFT JOIN jca_company co ON item.company_id = co.id and co.DELETED_ID = 0
    LEFT JOIN EFO_CATEGORY_LANG LANG ON ITEM.ID = LANG.CATEGORY_ID AND UPPER(LANG.LANG_CODE) = UPPER(/*languageCode*/'')
WHERE ITEM.id = /*id*/0
	