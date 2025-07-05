SELECT
	GROUP_SETTING.ID
    ,GROUP_SETTING.GROUP_CODE 								AS GROUP_CODE
    ,GROUP_SETTING.GROUP_NAME								AS GROUP_NAME
    ,GROUP_SETTING.COMPANY_ID								        AS COMPANY_ID
    ,GROUP_SETTING.CREATED_DATE                       		AS CREATED_DATE
    ,GROUP_SETTING.CREATED_ID                        		AS CREATED_ID
    ,GROUP_SETTING.UPDATED_DATE                       		AS UPDATED_DATE      
    ,GROUP_SETTING.UPDATED_ID                         		AS UPDATED_ID
    ,GROUP_SETTING.DELETED_DATE                       		AS DELETED_DATE
    ,GROUP_SETTING.DELETED_ID                         		AS DELETED_ID
    
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
	
/*IF orders != null*/
ORDER BY /*$orders*/username
-- ELSE ORDER BY GROUP_SETTING.UPDATED_DATE DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/
;;