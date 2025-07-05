SELECT
	process.id as id
	, process.PROCESS_CODE as code
	,case when processLang.PROCESS_NAME IS NULL THEN process.PROCESS_NAME 
    ELSE processLang.PROCESS_NAME END	AS name
	, process.major_version as major_version
	, process.minor_version as minor_version
FROM
	JPM_PROCESS_DEPLOY process
LEFT JOIN
	JPM_PROCESS_DEPLOY_LANG processLang
ON
	process.ID = processLang.PROCESS_DEPLOY_ID
	AND UPPER(processLang.LANG_CODE) = UPPER(/*lang*/'')
WHERE
	process.deleted_id = 0
	AND process.business_id = /*businessId*/
ORDER BY
	process.CREATED_DATE DESC