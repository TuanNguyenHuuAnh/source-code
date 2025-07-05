WITH stepStatus AS (
	SELECT DISTINCT
		  process.ID				AS		process_id
		, step.STATUS				AS		status_code
		, CASE WHEN statusLang.STATUS_NAME IS NULL THEN processStatus.STATUS_NAME 
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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*languageCode*/'')
)
SELECT
	  m_intro.*,
	  m_language.TITLE		AS title,
	  m_category_lang.LABEL	AS category_name
	  , CASE WHEN m_intro.status = 1 THEN /*condition.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END  									AS STATUS_NAME
FROM m_introduction m_intro
LEFT JOIN m_introduction_category m_category 
	ON (m_intro.m_introduction_category_id = m_category.id
		AND m_category.enabled = 1
	    AND m_category.delete_by is NULL)
LEFT JOIN m_introduction_category_language m_category_lang
	ON(m_category_lang.M_INTRODUCE_CATEGORY_ID = m_category.id
		AND m_category_lang.delete_date is NULL
		AND UPPER(m_category_lang.M_LANGUAGE_CODE) = UPPER(/*languageCode*/)
	)
LEFT JOIN m_introduction_language m_language 
	ON (m_language.m_introduction_id = m_intro.id 
	    AND UPPER(m_language.m_language_code) = UPPER(/*languageCode*/)
	    AND m_language.delete_by is NULL)
LEFT JOIN stepStatus
	ON stepStatus.process_id = m_intro.process_id
		and stepStatus.step_no = m_intro.status
WHERE 
	m_intro.delete_by is NULL 
	/*IF condition.name != null && condition.name != ''*/
	AND m_language.TITLE LIKE concat('%',/*condition.name*/,'%')
	/*END*/
	/*IF condition.code != null && condition.code != ''*/
	AND m_intro.code like concat('%',/*condition.code*/,'%')
	/*END*/
	/*IF condition.status != null*/
	AND m_intro.status = /*condition.status*/
	/*END*/
	/*IF condition.enabled != null*/
	AND m_intro.enabled = /*condition.enabled*/
	/*END*/
	/*IF condition.categoryId != null*/
	AND m_intro.M_INTRODUCTION_CATEGORY_ID = /*condition.categoryId*/
	/*END*/
ORDER BY m_category_lang.LABEL, m_intro.sort, m_intro.create_date DESC, m_intro.code ASC
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY