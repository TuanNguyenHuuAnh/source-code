SELECT
	status.id as id
	,status.status_code as status_code
	,status.status_name as status_name
	,status.process_id as process_id
FROM
	JPM_STATUS status
WHERE
	status.DELETED_ID = 0
	AND status.process_id = /*processId*/