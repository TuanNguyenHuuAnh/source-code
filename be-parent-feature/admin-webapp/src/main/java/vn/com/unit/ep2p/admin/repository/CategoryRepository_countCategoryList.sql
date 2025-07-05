SELECT
	COUNT(*)
FROM 
	EFO_CATEGORY ITEM
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
	UPPER(LANG.NAME) LIKE UPPER(concat(concat('%',  /*search.name*/''), '%'))
	/*END*/
	/*IF search.description != null && search.description != ''*/
	OR UPPER(ITEM.description) LIKE UPPER(concat(concat('%',  /*search.description*/''), '%'))
	/*END*/
	)
	/*END*/