
SELECT
	process.ID 						AS ID
	, process.PROCESS_CODE 			AS CODE
	, process.PROCESS_NAME 			AS NAME
	, process.MINOR_VERSION 		AS MINOR_VERSION
FROM
	JPM_PROCESS process
WHERE
	process.DELETED_ID = 0
	AND process.BUSINESS_ID = /*businessId*/