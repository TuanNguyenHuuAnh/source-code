SELECT ID AS id,
	   SLA_NAME AS sla_name,
	   BUSINESS_ID AS business_id,
	   PROCESS_ID AS process_id,
	   CALENDAR_TYPE AS calendar_type
	    , CREATED_BY					AS created_by
	    , CREATED_DATE				AS created_date
	    , UPDATED_BY					AS updated_by
	    , UPDATED_DATE				AS updated_date
	    , DELETED_ID					AS DELETED_ID
	    , DELETED_DATE				AS deleted_date
FROM APP_SLA_INFO
/*BEGIN*/
WHERE
	DELETED_ID is null
	/*IF id != null */
	AND ID = /*id*/
	/*END*/
/*END*/
	ORDER BY BUSINESS_ID ASC, PROCESS_ID ASC, SLA_NAME ASC