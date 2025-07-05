SELECT 
	inv.ID					AS ID
    ,inv.CODE_NO			AS CODE
    ,inv_lang.LANG_ID	 	AS LANG_ID
    ,inv_lang.LANG_CODE		AS LANG_CODE
    ,inv_lang.NAME			AS NAME
FROM SLA_INVOLED_TYPE inv
LEFT JOIN SLA_INVOLED_TYPE_LANG inv_lang ON inv.ID = inv_lang.INVOLED_TYPE_ID
WHERE 
	inv.DELETED_ID = 0
	AND inv_lang.LANG_CODE = /*lang*/
