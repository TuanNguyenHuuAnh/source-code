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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*searchCond.languageCode*/'VI')
)
SELECT 
	 docType.id					AS	id,
  	 docType.code				AS	code,
  	 typeLang.title				AS	name,
  	 docType.note				AS	note,
  	 docType.sort_order  		AS	sort_order,
  	 docType.description		AS	description,
  	 docType.create_date		AS  create_date,
  	 docType.CREATE_BY			AS  create_by,
  	 docType.enabled			AS  enabled
  	 , docType.status			AS  status
  	 ,	CASE WHEN docType.status = 1 THEN /*searchCond.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END						AS STATUS_NAME
FROM m_document_type docType
LEFT JOIN m_document_type_language typeLang ON docType.id = typeLang.m_document_type_id AND UPPER(typeLang.m_language_code) = UPPER(/*languageCode*/)
LEFT JOIN stepStatus
	ON stepStatus.process_id = docType.process_id
	and stepStatus.step_no = docType.status
WHERE docType.delete_date is null
	/*IF searchCond.customerTypeId != null*/
	AND docType.m_customer_type_id = /*searchCond.customerTypeId*/
	/*END*/
	/*IF searchCond.code != null && searchCond.code != ''*/
	AND docType.code LIKE concat('%',/*searchCond.code*/,'%')
	/*END*/
	/*IF searchCond.name != null && searchCond.name != ''*/
	AND typeLang.title LIKE concat('%',/*searchCond.name*/,'%')
	/*END*/
	/*IF searchCond.enabled != null*/
	AND docType.enabled = /*searchCond.enabled*/
	/*END*/
	/*IF searchCond.status != null && searchCond.status != ''*/
	AND docType.status = /*searchCond.status*/
	/*END*/
ORDER BY docType.enabled DESC, docType.sort_order DESC, docType.create_date DESC
OFFSET /*offset*/0 ROWS FETCH NEXT /*sizeOfPage*/10 ROWS ONLY;