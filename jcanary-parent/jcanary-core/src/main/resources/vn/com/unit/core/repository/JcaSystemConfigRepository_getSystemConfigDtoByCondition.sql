SELECT 
      ,SETTING.SETTING_KEY                      AS SETTING_KEY
      ,SETTING.SETTING_VALUE                    AS SETTING_VALUE
      ,SETTING.COMPANY_ID                       AS COMPANY_ID
      ,SETTING.GROUP_CODE                       AS GROUP_CODE
      ,SETTING.CREATED_DATE                     AS CREATED_DATE
      ,SETTING.CREATED_ID                       AS CREATED_ID
      ,SETTING.UPDATED_DATE                     AS UPDATED_DATE      
      ,SETTING.UPDATED_ID                       AS UPDATED_ID
      ,SETTING.DELETED_DATE                     AS DELETED_DATE
      ,SETTING.DELETED_ID                       AS DELETED_ID
      ,COMPANY.NAME                             AS NAME
FROM JCA_SYSTEM_SETTING SETTING
LEFT JOIN JCA_COMPANY COMPANY ON SETTING.COMPANY_ID = COMPANY.ID
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
	
/*IF orders != null*/
ORDER BY /*$orders*/username
-- ELSE ORDER BY SETTING.UPDATED_DATE DESC
/*END*/

/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/
;