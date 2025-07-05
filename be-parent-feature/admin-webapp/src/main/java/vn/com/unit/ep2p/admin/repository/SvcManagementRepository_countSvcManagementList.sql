SELECT
	COUNT(ITEM.ID)
FROM EFO_FORM ITEM
LEFT JOIN EFO_CATEGORY CA ON CA.ID = ITEM.CATEGORY_ID AND CA.DELETED_ID = 0 AND CA.COMPANY_ID = ITEM.COMPANY_ID
WHERE ITEM.DELETED_ID = 0
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
			OR UPPER(ITEM.FILE_NAME) LIKE UPPER(concat(concat('%',  /*search.fileName*/''), '%'))
		/*END*/
	)
/*END*/
/*IF search.formType != null && search.formType != ''*/
	AND CASE WHEN ITEM.FORM_TYPE IS NULL THEN '1' ELSE ITEM.FORM_TYPE END = /*search.formType*/
/*END*/
/*IF search.categoryId != null*/
	AND ITEM.CATEGORY_ID = /*search.categoryId*/0
/*END*/