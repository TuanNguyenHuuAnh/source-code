SELECT 
	pro.ID                        	 AS PROCESS_ID
	, pro.PROCESS_CODE               AS PROCESS_CODE
	, pro.PROCESS_NAME               AS PROCESS_NAME
	, pro.DESCRIPTION                AS DESCRIPTION
	, pro.DEPLOYED                   AS DEPLOYED
	, pro.COMPANY_ID                 AS COMPANY_ID
	, pro.MAJOR_VERSION              AS MAJOR_VERSION
	, pro.MINOR_VERSION              AS MINOR_VERSION
	, pro.EFFECTIVE_DATE             AS EFFECTIVE_DATE
	, pro.BUSINESS_ID                AS BUSINESS_ID
	, pro.ACTIVED                    AS ACTIVED
	, pro.BPMN_FILE_PATH             AS BPMN_FILE_PATH
	, pro.BPMN_FILE_NAME             AS BPMN_FILE_NAME
	, pro.PROCESS_DEFINITION_ID      AS PROCESS_DEFINITION_ID
	, pro.BPMN_REPO_ID               AS BPMN_REPO_ID
	, pro.SHOW_WORKFLOW              AS SHOW_WORKFLOW
	, comp.NAME						 AS COMPANY_NAME
FROM  
	JPM_PROCESS pro
LEFT JOIN 
	JCA_COMPANY comp
ON 
	pro.COMPANY_ID = comp.ID
WHERE
	pro.DELETED_ID = 0
	/*IF searchDto.keySearch != null && searchDto.keySearch != ''*/
	/*BEGIN*/
	AND 
		(	/*IF searchDto.processName != null && searchDto.processName != ''*/
			UPPER(replace(pro.PROCESS_NAME,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.keySearch*/), '%' ))
			/*END*/
			/*IF searchDto.processCode != null && searchDto.processCode != ''*/
			OR
			UPPER(replace(pro.PROCESS_CODE,' ','')) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.keySearch*/), '%' ))
			/*END*/
		)
	/*END*/
	/*END*/	    
	/*IF searchDto.companyId != null*/
	AND pro.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
		    
	/*IF searchDto.businessId != null*/
	AND pro.BUSINESS_ID = /*searchDto.businessId*/
	/*END*/
	
/*IF orders != null*/
ORDER BY /*$orders*/pro.UPDATED_DATE
-- ELSE ORDER BY pro.UPDATED_DATE DESC
/*END*/


/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/