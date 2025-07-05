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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*searchCond.languageCode*/'')
)

SELECT
	ROW_NUMBER() OVER (ORDER BY  ntype.sort ASC, ntype.create_date DESC, ntype.ENABLED DESC, typeLang.label) AS stt
	, ntype.id				AS	id
  	, ntype.code			AS	code
  	, ntype.name			AS	name
  	, typeLang.label		AS	label
  	, ntype.sort  			AS	sort
  	, ntype.description		AS	description
  	, ntype.create_date		AS  create_date
  	, ntype.create_by		AS	create_by
  	, CASE WHEN ntype.enabled = 1 THEN TO_NCHAR('x')
	ELSE  TO_NCHAR('')
	END 									AS	enabled
  	, CASE WHEN ntype.status = 1 THEN TO_NCHAR(/*searchCond.statusName*/'Lưu nháp')
		ELSE  stepStatus.STATUS_NAME
	END						AS STATUS_NAME
	, CASE WHEN ntype.TYPE_OF_LIBRARY = 1 THEN TO_NCHAR('x')
	END  					AS type_of_library
	, CASE WHEN ntype.TYPE_OF_LIBRARY = 0 THEN TO_NCHAR('x')
	END 					AS type_of_new
FROM m_news_type ntype
JOIN m_news_type_language typeLang ON (ntype.id = typeLang.m_news_type_id AND typeLang.delete_date is null)
LEFT JOIN stepStatus
	ON stepStatus.process_id = ntype.process_id
	and stepStatus.step_no = ntype.status
WHERE
	ntype.delete_date is null
	AND UPPER(typeLang.m_language_code) = UPPER(/*searchCond.languageCode*/)
	AND ntype.m_customer_type_id = /*searchCond.customerTypeId*/
	/*IF searchCond.code != null && searchCond.code != ''*/
	AND UPPER(ntype.code) LIKE UPPER(TRIM('%'||/*searchCond.code*/ ||'%'))
	/*END*/
	/*IF searchCond.label != null && searchCond.label != ''*/
	AND UPPER(typeLang.label) LIKE UPPER(TRIM('%'||/*searchCond.label*/ ||'%'))
	/*END*/
	/*IF searchCond.status != null && searchCond.status != ''*/
	AND UPPER(ntype.status) LIKE UPPER(TRIM('%'||/*searchCond.status*/ ||'%'))
	/*END*/
	/*IF searchCond.statusActive != null*/
	AND ntype.ENABLED = /*searchCond.statusActive*/
	/*END*/
	/*IF searchCond.typeOfLibrary != null*/
	AND ntype.TYPE_OF_LIBRARY = /*searchCond.typeOfLibrary*/
	/*END*/
ORDER BY  ntype.sort ASC, ntype.create_date DESC, ntype.ENABLED DESC, typeLang.label