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
FROM
    JPM_PROCESS_DEPLOY proDeploy
LEFT JOIN
	EFO_FORM efoForm
ON
	efoForm.BUSINESS_ID = proDeploy.BUSINESS_ID
	AND efoForm.COMPANY_ID = proDeploy.COMPANY_ID
WHERE 
	proDeploy.DELETED_ID = 0
	/*IF formId != null*/
	AND efoForm.ID = /*formId*/
	/*END*/
	/*IF formId == 0*/
		OR proDeploy.COMPANY_ID IS NULL
	/*END*/
ORDER BY 
	proDeploy.CREATED_DATE DESC
