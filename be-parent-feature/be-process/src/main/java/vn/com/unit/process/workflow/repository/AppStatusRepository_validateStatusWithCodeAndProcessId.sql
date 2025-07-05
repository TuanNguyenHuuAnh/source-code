SELECT  
	CASE WHEN COUNT(*) > 0 THEN 0 ELSE 1 END
FROM    
	JPM_STATUS
WHERE   
	PROCESS_ID = /*processId*/
	AND     STATUS_CODE = /*statusCode*/
	AND     (/*statusId*/ IS NULL OR ID != /*statusId*/)
	AND		DELETED_ID = 0