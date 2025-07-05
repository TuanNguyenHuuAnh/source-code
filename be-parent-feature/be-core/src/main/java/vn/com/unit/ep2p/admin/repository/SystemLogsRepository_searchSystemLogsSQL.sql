SELECT 
    JCA_SYSTEM_LOGS.ID AS id,
    CASE   
		WHEN JCA_ITEM.FUNCTION_NAME IS NULL AND JCA_SYSTEM_LOGS.FUNCTION_CODE = 'LO0#S00_Login' THEN N'Login'  
		WHEN JCA_ITEM.FUNCTION_NAME IS NOT NULL THEN JCA_ITEM.FUNCTION_NAME	
		ELSE CONCAT(N'', JCA_SYSTEM_LOGS.FUNCTION_CODE)
	END AS function_code,
    JCA_SYSTEM_LOGS.LOG_SUMMARY AS log_summary,
    CASE   
		WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 100 THEN 'FATAL'  
		WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 200 THEN 'ERROR'
		WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 300 THEN 'WARN'
		WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 400 THEN 'INFO'
		WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 500 THEN 'DEBUG'
		WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 600 THEN 'TRACE'	
		ELSE ''  
	END AS log_type_text,
    JCA_SYSTEM_LOGS.LOG_DATE AS log_date,
    JCA_SYSTEM_LOGS.LOG_DETAIL AS log_detail,
    JCA_SYSTEM_LOGS.IP AS ip,
    JCA_SYSTEM_LOGS.USERNAME AS username
FROM 
    JCA_SYSTEM_LOGS
LEFT JOIN 
    JCA_ITEM 
ON  
    JCA_ITEM.DELETED_ID = 0
    AND JCA_SYSTEM_LOGS.FUNCTION_CODE = JCA_ITEM.FUNCTION_CODE
    AND (JCA_ITEM.COMPANY_ID IS NULL OR JCA_ITEM.COMPANY_ID = JCA_SYSTEM_LOGS.COMPANY_ID)
WHERE 0 = 0
	/*IF systemLogsDto.fromDate != null*/
		AND CONVERT(DATE, /*systemLogsDto.fromDate*/, 101) <= CONVERT(DATE, JCA_SYSTEM_LOGS.LOG_DATE, 101)
	/*END*/
	/*IF systemLogsDto.toDate != null*/
		AND CONVERT(DATE, /*systemLogsDto.toDate*/, 101) >= CONVERT(DATE, JCA_SYSTEM_LOGS.LOG_DATE, 101)
	/*END*/
		
	/*BEGIN*/
		AND (   
			/*IF systemLogsDto.ip != null && systemLogsDto.ip != ''*/
				OR UPPER(JCA_SYSTEM_LOGS.IP)LIKE CONCAT( '%', UPPER(/*systemLogsDto.ip*/), '%' )
			/*END*/
			/*IF systemLogsDto.username != null && systemLogsDto.username != ''*/
				OR UPPER(JCA_SYSTEM_LOGS.USERNAME) LIKE CONCAT( '%', UPPER(/*systemLogsDto.username*/ ), '%' )
			/*END*/
				/*IF systemLogsDto.logSummary != null && systemLogsDto.logSummary != ''*/
				OR UPPER(JCA_SYSTEM_LOGS.LOG_SUMMARY) LIKE CONCAT( '%', UPPER(/*systemLogsDto.logSummary*/ ), '%' )
			/*END*/
			/*IF systemLogsDto.logDetail != null && systemLogsDto.logDetail!=''*/
				OR UPPER(JCA_SYSTEM_LOGS.LOG_DETAIL) LIKE CONCAT( '%', UPPER(/*systemLogsDto.logDetail*/ ), '%' )
			/*END*/
			/*IF systemLogsDto.logTypeText != null && systemLogsDto.logTypeText!=''*/
				OR CASE   
						WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 100 THEN 'FATAL'  
						WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 200 THEN 'ERROR'
						WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 300 THEN 'WARN'
						WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 400 THEN 'INFO'
						WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 500 THEN 'DEBUG'
						WHEN JCA_SYSTEM_LOGS.LOG_TYPE = 600 THEN 'TRACE'	
						ELSE ''  
					END LIKE CONCAT( '%', UPPER(/*systemLogsDto.logTypeText*/ ), '%' )
			/*END*/
			/*IF systemLogsDto.functionCode != null && systemLogsDto.functionCode!=''*/
				OR UPPER(
					CASE   
						WHEN JCA_ITEM.FUNCTION_NAME IS NULL AND JCA_SYSTEM_LOGS.FUNCTION_CODE = 'LO0#S00_Login' THEN N'Login'  
						WHEN JCA_ITEM.FUNCTION_NAME IS NOT NULL THEN JCA_ITEM.FUNCTION_NAME	
						ELSE CONCAT(N'', JCA_SYSTEM_LOGS.FUNCTION_CODE)
					END
				) LIKE CONCAT( '%', UPPER(/*systemLogsDto.functionCode*/ ), '%' )
			/*END*/
		)
    /*END*/
		
	/*BEGIN*/
		AND (
			/*IF systemLogsDto.companyId != null*/
				OR JCA_SYSTEM_LOGS.COMPANY_ID = /*systemLogsDto.companyId*/1
			/*END*/
		)
	/*END*/
ORDER BY LOG_DATE DESC
OFFSET /*offset*/ ROWS FETCH NEXT /*sizeOfPage*/ ROWS ONLY