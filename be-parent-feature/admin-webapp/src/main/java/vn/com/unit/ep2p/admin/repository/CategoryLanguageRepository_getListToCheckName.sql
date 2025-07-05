SELECT
    CAL.*
FROM EFO_CATEGORY CA
LEFT JOIN EFO_CATEGORY_lANGUAGE CAL on CA.id = cal.category_id and CAL.DELETED_ID = 0
WHERE CA.DELETED_ID = 0
    AND CA.company_id = /*companyId*/1
	AND CAL.category_name = /*categoryName*/''
	AND UPPER(CAL.LANGUAGE_CODE) = UPPER(/*languageCode*/'en')
    /*IF categoryID != null*/
	AND CA.id <> /*categoryID*/
	/*END*/