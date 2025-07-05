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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*languageCode*/'VI')
)

SELECT
	   m_intro_category.*,
	   m_language.label					AS	title
	   , CASE WHEN m_intro_category.status = 1 THEN /*condition.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END  									AS status_name
	FROM
	    m_introduction_category m_intro_category
	LEFT JOIN m_introduction_category_language m_language ON (m_language.m_introduce_category_id = m_intro_category.id
	    AND m_language.m_language_code = /*languageCode*/
	    AND m_language.delete_by is NULL)
	LEFT JOIN stepStatus
	ON stepStatus.process_id = m_intro_category.process_id
	and stepStatus.step_no = m_intro_category.status
	WHERE m_intro_category.delete_by is NULL
	AND parent_id is NULL
	/*IF condition.name != null && condition.name != ''*/
	AND m_language.label like concat('%',/*condition.name*/,'%')
	/*END*/
	/*IF condition.code != null && condition.code != ''*/
	AND m_intro_category.code like concat('%',/*condition.code*/ ,'%')
	/*END*/
	/*IF condition.enabled != null*/
	AND m_intro_category.enabled = /*condition.enabled*/
	/*END*/
	/*IF condition.status != null && condition.status != ''*/
	AND m_intro_category.status like concat('%',/*condition.status*/,'%')
	/*END*/
	/*IF condition.typeOfMain != null && condition.typeOfMain == 1*/
	AND m_intro_category.TYPE_OF_MAIN = /*condition.typeOfMain*/
	/*END*/
	/*IF condition.typeOfMain != null && condition.typeOfMain == 0*/
	AND m_intro_category.TYPE_OF_MAIN is NULL
	/*END*/
	/*IF condition.pictureIntroduction != null && condition.pictureIntroduction == 1*/
	AND m_intro_category.PICTURE_INTRODUCTION = /*condition.pictureIntroduction*/
	/*END*/
	/*IF condition.pictureIntroduction != null && condition.pictureIntroduction == 0*/
	AND m_intro_category.PICTURE_INTRODUCTION is NULL
	/*END*/
	ORDER BY
		m_intro_category.sort ASC,
		m_intro_category.create_date DESC,
		m_intro_category.code ASC
	OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY