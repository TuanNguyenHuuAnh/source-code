SELECT
	status.ID                  AS STATUS_ID
	, status.PROCESS_ID        AS PROCESS_ID
	, status.STATUS_CODE       AS STATUS_CODE
	, status.STATUS_NAME       AS STATUS_NAME
FROM
	JPM_STATUS status
WHERE
	status.DELETED_ID = 0
	AND status.ID = /*statusId*/