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
    ROW_NUMBER() OVER (ORDER BY pcl.title asc,categ.sort ASC, categ.create_date DESC) 					AS	stt
  	, pcl.title													AS	category_title
  	, categ.code												AS	code
  	, clanguage.title											AS	title
  	, CASE WHEN categ.status = 1 THEN /*categCond.statusName*/'Lưu nháp'
		ELSE  stepStatus.STATUS_NAME
	END  														AS STATUS_NAME
	, CASE WHEN categ.enabled = 1 THEN 'x'
		ELSE  ''
	END 														AS	enabled
	, CASE WHEN categ.IS_PRIORITY = 1 THEN 'x'
		ELSE  ''
	END 														AS	priority
  	, categ.create_by											AS	create_by
  	, categ.create_date											AS	create_date
FROM m_product_category_sub categ
LEFT JOIN m_product_category_sub_language clanguage 
	ON (categ.id = clanguage.m_product_category_sub_id AND clanguage.delete_date is null)
LEFT JOIN m_product_category pc 
	ON (pc.id = categ.m_product_category_id AND pc.delete_date is null)
LEFT JOIN m_product_category_language pcl 
	ON (pcl.m_product_category_id = pc.id AND pcl.delete_date is null)
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
	AND UPPER(pcl.m_language_code) = UPPER(/*categCond.languageCode*/)
	AND categ.M_CUSTOMER_TYPE_ID = /*categCond.customerTypeId*/9
	/*IF categCond.categoryId != null*/
	AND categ.m_product_category_id = /*categCond.categoryId*/
	/*END*/
	/*IF categCond.statusText != null && categCond.statusText != ''*/
	AND categ.status = /*categCond.statusText*/
	/*END*/
	/*IF categCond.code != null && categCond.code != ''*/
	AND categ.code LIKE concat('%',/*categCond.code*/,'%')
	/*END*/
	/*IF categCond.title != null && categCond.title != ''*/
	AND clanguage.title LIKE concat('%',/*categCond.title*/,'%')
	/*END*/	
	/*IF categCond.enabled != null*/
	AND categ.enabled = /*categCond.enabled*/
	/*END*/
	/*IF categCond.priority != null*/
	AND categ.is_priority = /*categCond.priority*/
	/*END*/	
ORDER BY pcl.title asc,categ.sort ASC, categ.create_date DESC