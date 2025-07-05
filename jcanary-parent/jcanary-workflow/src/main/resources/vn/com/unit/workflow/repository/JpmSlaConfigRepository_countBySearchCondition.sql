SELECT 
	COUNT(jsla.ID)
FROM 
	JPM_SLA_CONFIG jsla
LEFT JOIN 
	JPM_STEP_LANG_DEPLOY step_lang 
	ON jsla.STEP_DEPLOY_ID = step_lang.STEP_DEPLOY_ID
WHERE 
	jsla.DELETED_ID = 0
	/*IF searchDto.jpmSlaInfoId != null*/
	AND jsla.JPM_SLA_INFO_ID = /*searchDto.jpmSlaInfoId*/
	/*END*/
	/*IF searchDto.langCode != null && searchDto.langCode != ''*/
	AND UPPER(replace(step_lang.LANG_CODE,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.langCode*/), '%' ))
	/*END*/