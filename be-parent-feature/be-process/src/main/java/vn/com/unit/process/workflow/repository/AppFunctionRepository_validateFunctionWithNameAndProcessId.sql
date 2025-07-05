SELECT  CASE WHEN COUNT(*) > 0 THEN 0 ELSE 1 END
FROM    JPM_PERMISSION
WHERE   PROCESS_ID = /*processId*/
AND     PERMISSION_NAME = /*functionName*/
AND     (/*functionId*/ IS NULL OR ID != /*functionId*/)
AND 	DELETED_ID = 0