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
FROM  
	JPM_PROCESS pro
WHERE
	pro.DELETED_ID = 0
	AND pro.COMPANY_ID = /*companyId*/
	AND pro.BUSINESS_ID = /*businessId*/