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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*categCond.languageCode*/'')
)

SELECT
	  categ.id						AS	id
  	, categ.code					AS	code
  	, categ.name					AS	name
  	, categ.enabled					AS	enabled
  	, clanguage.title				AS	title
  	, categ.sort 					AS	sort
  	, customerType.title			AS  type_name
  	, clanguage.description			AS  description
  	, categ.create_date				AS  create_date
  	, categ.process_id				AS	process_id
  	, categ.status					AS	status
  	, categ.create_by				AS 	create_by
  	, categ.approve_by				AS 	approve_by
  	, categ.approve_date			AS 	approve_date
  	, categ.publish_by				AS 	publish_by
  	, categ.publish_date			AS 	publish_date
  	, (SELECT count(1) FROM m_product_category_sub sub 
	  	WHERE sub.delete_date IS NULL AND sub.delete_by IS NULL
	  	AND sub.m_customer_type_id = categ.m_customer_type_id
	    AND sub.m_product_category_id = categ.id) number_product
    , (SELECT count(1) FROM m_faqs faqs
    	WHERE CHARINDEX(faqs.M_PRODUCT_CATEGORY_ID, categ.ID, 1) > 0
    	AND faqs.delete_date IS NULL
    	AND faqs.ENABLED = 1
    	)	number_faqs
    , CASE WHEN categ.status = 1 THEN /*categCond.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END  									AS STATUS_NAME
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
ORDER BY categ.sort ASC, categ.create_date DESC
OFFSET /*offset*/ ROWS FETCH NEXT  /*sizeOfPage*/ ROWS ONLY