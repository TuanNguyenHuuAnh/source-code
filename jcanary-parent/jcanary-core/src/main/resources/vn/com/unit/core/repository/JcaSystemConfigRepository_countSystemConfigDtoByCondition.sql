SELECT 
        count(SETTING.ID)
FROM JCA_SYSTEM_SETTING SETTING
WHERE SETTING.DELETED_ID = 0 
/*BEGIN*/
	AND 
		(
			/*IF jcaSystemConfigSearchDto.key != null && jcaSystemConfigSearchDto.key != ''*/
			UPPER(replace(SETTING.SETTING_KEY ,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaSystemConfigSearchDto.key*/), '%' ))
			/*END*/
			
			/*IF jcaSystemConfigSearchDto.value != null && jcaSystemConfigSearchDto.value != ''*/
			UPPER(replace(SETTING.SETTING_VALUE,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*jcaSystemConfigSearchDto.value*/), '%' ))
			/*END*/
		)
	/*END*/
		

		    
	/*IF jcaSystemConfigSearchDto.companyId != null*/
	AND SETTING.COMPANY_ID = /*jcaSystemConfigSearchDto.companyId*/
	/*END*/
	/*IF jcaSystemConfigSearchDto.groupId != null*/
	AND SETTING.GROUP_CODE  = /*jcaSystemConfigSearchDto.groupCode*/
	/*END*/
;