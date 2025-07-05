SELECT  CASE WHEN COUNT(*) > 0 THEN 0 ELSE 1 END
FROM    JPM_PARAM
WHERE   PROCESS_ID = /*processId*/
AND     FIELD_NAME = /*fieldName*/
AND     (/*paramId*/ IS NULL OR ID != /*paramId*/)
AND 	DELETED_ID = 0