WITH stepStatus AS (
	SELECT DISTINCT
		  process.ID				AS		process_id
		, step.STATUS				AS		status_code
		, CASE WHEN TRIM(statusLang.STATUS_NAME) IS NULL THEN processStatus.STATUS_NAME 
			ELSE  statusLang.STATUS_NAME END  AS STATUS_NAME
		, step.STEPNO				AS		step_no
	FROM
	    JPR_PROCESS process
	
	JOIN JPR_STEP step						-- Get step of process
		ON step.PROCESSID = process.id
		
	JOIN JPR_PROCESS_STATUS processStatus		-- Get all status
		ON processStatus.business_code = process.BUSINESSCODE
		AND processStatus.status_code = step.STATUS
		
	LEFT JOIN JPR_PROCESS_STATUS_LANG STATUSLANG
	        ON statusLang.MAIN_ID = processStatus.ID
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*languageCode*/'VI')
)

SELECT
    introCate.*,
    m_language.LABEL						AS	title
    , CASE WHEN introCate.status = 1 THEN TO_NCHAR('Lưu nháp')
			ELSE  stepStatus.STATUS_NAME
		END  		  						AS STATUS_NAME
FROM
    m_introduction_category introCate
JOIN m_introduction_category_language m_language ON (m_language.m_introduce_category_id = introCate.id
	    AND m_language.m_language_code = UPPER(/*languageCode*/)
	    AND m_language.delete_by is NULL)
LEFT JOIN stepStatus
	ON stepStatus.process_id = introCate.process_id
	and stepStatus.step_no = introCate.status
WHERE introCate.delete_by is NULL
	AND introCate.ENABLED = 1
	AND introCate.id = /*id*/