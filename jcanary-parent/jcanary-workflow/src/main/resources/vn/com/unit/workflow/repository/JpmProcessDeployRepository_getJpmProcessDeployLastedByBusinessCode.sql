SELECT 
	proDeploy.ID                          AS PROCESS_DEPLOY_ID
	, proDeploy.PROCESS_CODE              AS PROCESS_CODE
	, proDeploy.PROCESS_NAME              AS PROCESS_NAME
	, proDeploy.DESCRIPTION               AS DESCRIPTION
	, proDeploy.COMPANY_ID                AS COMPANY_ID
	, proDeploy.MAJOR_VERSION             AS MAJOR_VERSION
	, proDeploy.MINOR_VERSION             AS MINOR_VERSION
	, proDeploy.EFFECTIVE_DATE            AS EFFECTIVE_DATE
	, proDeploy.BUSINESS_ID               AS BUSINESS_ID
	, proDeploy.ACTIVED                   AS ACTIVED
	, proDeploy.BPMN_FILE_PATH            AS BPMN_FILE_PATH
	, proDeploy.BPMN_FILE_NAME            AS BPMN_FILE_NAME
	, proDeploy.PROCESS_DEFINITION_ID     AS PROCESS_DEFINITION_ID
	, proDeploy.BPMN_REPO_ID              AS BPMN_REPO_ID
	, proDeploy.SHOW_WORKFLOW             AS SHOW_WORKFLOW
FROM
	JPM_PROCESS_DEPLOY proDeploy
LEFT JOIN
	JPM_BUSINESS business
ON
	proDeploy.BUSINESS_ID = business.ID
	AND business.DELETED_ID = 0
WHERE
	proDeploy.DELETED_ID = 0
	AND proDeploy.COMPANY_ID = /*companyId*/0
	AND proDeploy.EFFECTIVE_DATE <= /*sysDate*/
	AND business.BUSINESS_CODE = /*businessCode*/'BUSINESS'
ORDER BY 
	proDeploy.EFFECTIVE_DATE DESC
	, proDeploy.UPDATED_DATE DESC