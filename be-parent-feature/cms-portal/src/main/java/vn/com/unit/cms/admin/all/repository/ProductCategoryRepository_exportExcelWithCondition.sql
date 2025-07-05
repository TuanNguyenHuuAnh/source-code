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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*categCond.languageCode*/'VI')
)

SELECT
	  ROW_NUMBER() OVER (ORDER BY categ.sort ASC,categ.create_date desc) AS stt
  	, categ.code							AS	code
  	, clanguage.title						AS	title
  	, CASE WHEN categ.status = 1 THEN /*categCond.statusName*/'Lưu nháp'
		ELSE  stepStatus.STATUS_NAME
	END  									AS STATUS_NAME
	, CASE WHEN categ.enabled = 1 THEN 'x'
		ELSE  ''
	END 									AS	enabled
  	, customerType.title					AS  type_name
  	, categ.create_by						AS 	create_by
  	, categ.create_date						AS  create_date
FROM m_product_category categ
LEFT JOIN m_product_category_language clanguage
	ON (categ.id = clanguage.m_product_category_id AND clanguage.delete_date is null)
LEFT JOIN m_customer_type_language customerType
	ON (customerType.m_customer_type_id = categ.m_customer_type_id 
	AND customerType.m_language_code = clanguage.m_language_code
	AND customerType.delete_date is null)
LEFT JOIN stepStatus
	ON stepStatus.process_id = categ.process_id
	and stepStatus.step_no = categ.status
WHERE
	categ.delete_date is null
	AND UPPER(clanguage.m_language_code) = UPPER(/*categCond.languageCode*/)
	AND categ.M_CUSTOMER_TYPE_ID = /*categCond.customerId*/
	
	/*IF categCond.code != null && categCond.code != ''*/
	AND categ.code LIKE concat('%',/*categCond.code*/,'%')
	/*END*/
	/*IF categCond.name != null && categCond.name != ''*/
	AND clanguage.title LIKE concat('%',/*categCond.name*/,'%')
	/*END*/
	/*IF categCond.status != null && categCond.status != ''*/
	AND categ.status = /*categCond.status*/
	/*END*/
	/*IF categCond.enabled != null*/
	AND categ.enabled = /*categCond.enabled*/
	/*END*/
ORDER BY categ.sort ASC,categ.create_date DESC