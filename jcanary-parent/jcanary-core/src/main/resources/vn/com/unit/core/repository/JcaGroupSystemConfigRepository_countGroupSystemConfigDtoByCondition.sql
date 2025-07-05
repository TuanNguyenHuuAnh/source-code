SELECT COUNT(1)
FROM JCA_GROUP_SYSTEM_SETTING GROUP_SETTING
WHERE GROUP_SETTING.DELETED_ID = 0
	/*BEGIN*/
	AND 
		(
		/*IF jcaGroupSystemConfigSearchDto.code != null && jcaGroupSystemConfigSearchDto.code != ''*/
			OR UPPER(replace(GROUP_SETTING.GROUP_CODE,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaGroupSystemConfigSearchDto.code*/), '%' ))
		/*END*/
		
		/*IF jcaGroupSystemConfigSearchDto.name != null && jcaGroupSystemConfigSearchDto.name != ''*/
			OR	UPPER(replace(GROUP_SETTING.GROUP_NAME,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaGroupSystemConfigSearchDto.name*/), '%' ))
		/*END*/
		)
	/*END*/	    
	/*IF jcaGroupSystemConfigSearchDto.companyId != null*/
	AND GROUP_SETTING.COMPANY_ID = /*jcaGroupSystemConfigSearchDto.companyId*/
	/*END*/
;