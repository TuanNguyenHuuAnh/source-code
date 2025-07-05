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
	 ROW_NUMBER() OVER (ORDER BY product.ENABLED DESC, product.is_highlights desc, product.is_priority desc, product.sort, product.create_date desc) AS stt
	 , category.title				AS  category_name
	 , categorySub.title			AS	category_sub_name
  	 , product.code					AS	code
  	 , productLan.title				AS	title
  	 , productType.title 			AS  type_name
	 , CASE WHEN product.is_microsite = 1 THEN 'x'
		ELSE  ''
	 END 						AS	microsite
	 , CASE WHEN product.ENABLED = 1 THEN 'x'
		ELSE  ''
	 END 						AS	enabled
	 , CASE WHEN product.IS_HIGHLIGHTS = 1 THEN 'x'
		ELSE  ''
	 END 						AS	highlights
	 , CASE WHEN product.IS_LENDING = 1 THEN math.NAME
		ELSE  ''
	 END 						AS	highlights_math_express
	 , CASE WHEN product.IS_PRIORITY = 1 THEN 'x'
		ELSE  ''
	 END 						AS	priority
	 , CASE WHEN product.SHOW_FORM = 1 THEN 'x'
		ELSE  ''
	 END 						AS	show_form
	 , CASE WHEN product.status = 1 THEN /*searchCond.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END						AS STATUS_NAME
  	 , product.create_date		AS  create_date
     , product.create_by		    AS  create_by
FROM m_product product
JOIN m_product_language productLan ON (productLan.m_product_id = product.id AND productLan.delete_date is null)
JOIN m_product_category_language category ON (category.m_product_category_id = product.m_product_category_id 
											  AND category.m_language_code = productLan.m_language_code
											  AND category.delete_date is null)
JOIN m_customer_type_language productType ON (productType.m_customer_type_id = product.m_customer_type_id 
											  AND productType.m_language_code = productLan.m_language_code
											  AND productType.delete_date is null
											)
LEFT JOIN m_product_category_sub_language categorySub ON (categorySub.m_product_category_sub_id = product.m_product_category_sub_id 
														  AND categorySub.m_language_code = productLan.m_language_code
														  AND categorySub.delete_date is null)

LEFT JOIN M_MATH_EXPRESSION math
ON (math.DELETE_DATE is NULL
	AND math.ID = product.MATH_EXPRESSION
)
LEFT JOIN stepStatus
	ON stepStatus.process_id = product.process_id
	and stepStatus.step_no = product.status
WHERE
	product.delete_date is null
	AND UPPER(productLan.m_language_code) = UPPER(/*searchCond.languageCode*/)
	/*IF searchCond.microsite != null*/
	AND product.is_microsite = /*searchCond.microsite*/
	/*END*/
	/*IF searchCond.typeId != null */
	AND product.m_customer_type_id = /*searchCond.typeId*/
	/*END*/
	/*IF searchCond.categoryId != null*/
	AND product.m_product_category_id = /*searchCond.categoryId*/
	/*END*/
	/*IF searchCond.categorySubId != null*/
	AND product.m_product_category_sub_id = /*searchCond.categorySubId*/
	/*END*/
	/*IF searchCond.status != null && searchCond.status != ''*/
	AND product.status = /*searchCond.status*/
	/*END*/
	/*IF searchCond.code != null && searchCond.code != ''*/
	AND product.code LIKE concat('%',/*searchCond.code*/,'%')
	/*END*/
	/*IF searchCond.title != null && searchCond.title != ''*/
	AND productLan.title LIKE concat('%',/*searchCond.title*/,'%')
	/*END*/
	/*IF searchCond.enabled != null*/
	AND product.ENABLED = /*searchCond.enabled*/
	/*END*/
	/*IF searchCond.highlights != null*/
	AND product.IS_HIGHLIGHTS = /*searchCond.highlights*/
	/*END*/
	/*IF searchCond.lending != null*/
	AND product.IS_LENDING = /*searchCond.lending*/
	/*END*/
	/*IF searchCond.priority != null*/
	AND product.IS_PRIORITY = /*searchCond.priority*/
	/*END*/
	/*IF searchCond.showForm != null*/
	AND product.SHOW_FORM = /*searchCond.showForm*/
	/*END*/
ORDER BY product.ENABLED DESC, product.is_highlights desc, product.is_priority desc, product.sort, product.create_date desc