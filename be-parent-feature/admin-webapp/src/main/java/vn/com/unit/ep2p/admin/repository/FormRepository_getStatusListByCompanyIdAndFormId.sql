SELECT
    status.ID AS id,
    status.STATUS_NAME AS name,
    status.STATUS_CODE AS text
FROM
    JPM_PROCESS process
LEFT JOIN
	JPM_STATUS status
ON
	process.ID = status.PROCESS_ID
WHERE process.DELETED_ID = 0
/*IF companyId != null*/
	AND process.COMPANY_ID = /*companyId*/1
/*END*/
/*IF processId != null*/
	AND process.ID = /*processId*/1
/*END*/
ORDER BY status.ID
/*IF isPaging == true*/
OFFSET 0 ROWS FETCH NEXT 30 ROWS ONLY
/*END*/