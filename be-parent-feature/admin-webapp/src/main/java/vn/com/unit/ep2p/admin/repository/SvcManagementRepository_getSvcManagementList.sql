SELECT
	ITEM.ID
	,ITEM.COMPANY_ID
	,co.name 		  AS company_name
	,ITEM.CATEGORY_ID
	,ITEM.NAME
	,ITEM.DESCRIPTION
	,ITEM.DISPLAY_ORDER
	,ITEM.DEVICE_TYPE
	,ITEM.ACTIVED
	,ITEM.CREATED_ID
	,ITEM.CREATED_DATE
	,ITEM.UPDATED_ID
	,ITEM.UPDATED_DATE
	,ITEM.DELETED_ID
	,ITEM.DELETED_DATE,
	FC.FUNCTION_NAME
FROM EFO_FORM ITEM
LEFT JOIN EFO_CATEGORY CA ON CA.ID = ITEM.CATEGORY_ID AND CA.DELETED_ID IS NULL AND CA.COMPANY_ID = ITEM.COMPANY_ID
LEFT JOIN EFO_CATEGORY_LANG LANG ON CA.ID = LANG.CATEGORY_ID AND LANG.LANG_CODE = UPPER(/*languageCode*/'EN')
LEFT JOIN JCA_ITEM FC ON FC.DELETED_ID IS NULL AND FC.COMPANY_ID = ITEM.COMPANY_ID
LEFT JOIN jca_company co ON item.company_id = co.id and co.deleted_id is null
WHERE ITEM.DELETED_ID IS NULL
/*IF search.companyId != null && search.companyId != 0*/
	AND ITEM.COMPANY_ID = /*search.companyId*/1
/*END*/ 
/*BEGIN*/
	AND (
		/*IF search.name != null && search.name != ''*/
			OR UPPER(ITEM.NAME) LIKE UPPER(concat(concat('%',  /*search.name*/''), '%'))
		/*END*/
		/*IF search.description != null && search.description != ''*/
			OR UPPER(ITEM.DESCRIPTION) LIKE UPPER(concat(concat('%',  /*search.description*/''), '%'))
		/*END*/
		/*IF search.fileName != null && search.fileName != ''*/
			OR UPPER(ITEM.NAME) LIKE UPPER(concat(concat('%',  /*search.fileName*/''), '%'))
		/*END*/
	)
/*END*/
/*IF search.categoryId != null*/
	AND ITEM.CATEGORY_ID = /*search.categoryId*/0
/*END*/
ORDER BY ITEM.DISPLAY_ORDER, ITEM.NAME
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY