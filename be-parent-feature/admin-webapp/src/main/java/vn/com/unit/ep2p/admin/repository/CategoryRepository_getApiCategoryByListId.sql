SELECT
    ITEM.ID AS category_id,
    LANG.CATEGORY_NAME AS category_name,
    ITEM.COMPANY_ID	   AS company_id 
FROM 
	EFO_CATEGORY ITEM
LEFT JOIN EFO_CATEGORY_LANGUAGE LANG
ON 
	ITEM.ID = LANG.CATEGORY_ID AND UPPER(LANG.LANGUAGE_CODE) = UPPER(/*languageCode*/'')
WHERE ITEM.DELETED_ID = 0
	/*IF listId.size()>0*/ 
	AND ITEM.ID IN /*listId*/()
	/*END*/
	/*IF listId.size()<1*/ 
	AND 1 != 1
	/*END*/
	/*IF keySearch != null && keySearch != ''*/
	AND UPPER(LANG.CATEGORY_NAME) LIKE concat('%',  concat(UPPER(/*keySearch*/''), '%'))
	/*END*/
ORDER BY category_id