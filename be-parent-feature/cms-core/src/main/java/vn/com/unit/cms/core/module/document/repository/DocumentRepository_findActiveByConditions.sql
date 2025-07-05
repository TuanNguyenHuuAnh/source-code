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
	   DISTINCT m_document.id
	   , m_document.code
	   , m_language.title 						as name
	   , m_document.create_date
	   , m_document.sort_order
	   , m_document.status      				as status
	   , m_document.enabled     				as enabled
	   , m_category_language.TITLE				as  type_name
	   , m_document.create_by					as  create_by
  	   , CASE WHEN m_document.status = 1 THEN /*condition.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		 END						AS STATUS_NAME
	FROM
	    (m_document m_document LEFT JOIN m_document_type m_category 
	    									ON (m_document.m_document_type_id = m_category.id AND m_category.delete_by is NULL))
	    							LEFT JOIN m_document_language m_language 
	    									ON (m_language.m_document_id = m_document.id
	    									AND m_language.m_language_code = UPPER(/*languageCode*/)
	    									AND m_language.delete_by is NULL)
	    							LEFT JOIN m_document_type_language m_category_language 
	    									ON (m_category_language.M_DOCUMENT_TYPE_ID = m_category.id
	    									AND m_category_language.m_language_code = UPPER(/*languageCode*/)
	    									AND m_category_language.delete_by is NULL)
	LEFT JOIN stepStatus
		ON stepStatus.process_id = m_document.process_id
		AND stepStatus.step_no = m_document.status
	WHERE
	m_document.delete_by is NULL
	/*IF condition.customerTypeId != null*/
	and m_document.m_customer_type_id = /*condition.customerTypeId*/
	/*END*/
	/*IF condition.enabled != null*/
	and m_document.enabled = /*condition.enabled*/
	/*END*/
	/*IF condition.typeId != null*/
	and m_document.m_document_type_id = /*condition.typeId*/
	/*END*/
	/*IF condition.status != null && condition.status != ''*/
	and m_document.status = /*condition.status*/
	/*END*/
	/*IF condition.name != null && condition.name != ''*/
	AND
	m_language.title like concat('%',/*condition.name*/,'%')
	/*END*/
	/*IF condition.code != null && condition.code != ''*/
	AND
	m_document.code like concat('%',/*condition.code*/,'%')
	/*END*/
	ORDER BY m_category_language.TITLE ASC, m_document.sort_order ASC, m_document.create_date DESC
	OFFSET /*offset*/0 ROWS FETCH NEXT /*sizeOfPage*/10 ROWS ONLY;
