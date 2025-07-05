SELECT 
	proDeploy.ID                        	 AS PROCESS_DEPLOY_ID
	, proDeploy.PROCESS_CODE               AS PROCESS_CODE
	, proDeploy.PROCESS_NAME               AS PROCESS_NAME
	, proDeploy.DESCRIPTION                AS DESCRIPTION
	, proDeploy.COMPANY_ID                 AS COMPANY_ID
	, proDeploy.MAJOR_VERSION              AS MAJOR_VERSION
	, proDeploy.MINOR_VERSION              AS MINOR_VERSION
	, proDeploy.EFFECTIVE_DATE             AS EFFECTIVE_DATE
	, proDeploy.BUSINESS_ID                AS BUSINESS_ID
	, proDeploy.ACTIVED                    AS ACTIVED
	, proDeploy.BPMN_FILE_PATH             AS BPMN_FILE_PATH
	, proDeploy.BPMN_FILE_NAME             AS BPMN_FILE_NAME
	, proDeploy.PROCESS_DEFINITION_ID      AS PROCESS_DEFINITION_ID
	, proDeploy.BPMN_REPO_ID               AS BPMN_REPO_ID
	, proDeploy.SHOW_WORKFLOW              AS SHOW_WORKFLOW
	, proDeploy.PROCESS_ID                 AS PROCESS_ID
	, proDeploy.CREATED_DATE 				AS DEPLOYED_DATE 
	, comp.NAME 							AS COMPANY_NAME
FROM  
	JPM_PROCESS_DEPLOY proDeploy
LEFT JOIN
	JCA_COMPANY comp
ON
	comp.ID = proDeploy.COMPANY_ID
WHERE
	proDeploy.DELETED_ID = 0
	/*IF searchDto.keySearch != null && searchDto.keySearch != ''*/
	/*BEGIN*/
	AND 
		(
			/*IF searchDto.processName != null && searchDto.processName != ''*/
			UPPER(proDeploy.PROCESS_NAME) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.processName*/), '%' ))
			/*END*/
			/*IF searchDto.processCode != null && searchDto.processCode != ''*/
			OR UPPER(proDeploy.PROCESS_CODE) LIKE CONCAT( '%', CONCAT(UPPER(/*searchDto.processCode*/), '%' ))
			/*END*/
		)
	/*END*/
	/*END*/
		    
	/*IF searchDto.companyId != null*/
	AND proDeploy.COMPANY_ID = /*searchDto.companyId*/
	/*END*/
		    
	/*IF searchDto.businessId != null*/
	AND proDeploy.BUSINESS_ID = /*searchDto.businessId*/
	/*END*/
		    
	/*IF searchDto.processId != null*/
	AND proDeploy.PROCESS_ID = /*searchDto.processId*/
	/*END*/
		    
	/*IF searchDto.fromDate != null*/
	AND proDeploy.EFFECTIVE_DATE >= /*searchDto.fromDate*/
	/*END*/
		    
	/*IF searchDto.toDate != null*/
	AND proDeploy.EFFECTIVE_DATE <= /*searchDto.toDate*/
	/*END*/
	
/*IF orders != null*/
ORDER BY /*$orders*/proDeploy.UPDATED_DATE
-- ELSE ORDER BY proDeploy.UPDATED_DATE DESC
/*END*/


/*BEGIN*/
  /*IF offset != null*/
		OFFSET /*offset*/ ROWS FETCH NEXT  /*size*/ ROWS ONLY
  /*END*/
/*END*/