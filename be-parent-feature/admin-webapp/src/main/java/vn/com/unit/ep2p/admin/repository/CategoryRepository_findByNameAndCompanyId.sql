SELECT
    LANG.NAME AS NAME
	,item.id
    ,item.description
    ,item.display_order
    ,item.ACTIVED
FROM 
	EFO_CATEGORY ITEM
    LEFT JOIN EFO_CATEGORY_LANG LANG ON ITEM.ID = LANG.CATEGORY_ID AND UPPER(LANG.LANG_CODE) = UPPER(/*languageCode*/'')
WHERE LANG.NAME = /*name*/''
	AND ITEM.DELETED_ID = 0
	/*IF companyId != null*/
	AND ITEM.company_id = /*companyId*/1
	/*END*/