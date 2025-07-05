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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*searchCond.languageCode*/'')
)
SELECT
	 product.id					AS	id,
  	 product.code				AS	code,
  	 product.name				AS	name,
  	 product.image_url			AS	image_url,
  	 physical_img				AS  physical_img,
  	 product.key_word			AS	key_word,
  	 product.enabled			AS	enabled,
  	 product.process_id			AS	process_id,
  	 product.status				AS	status,
  	 category.title				AS  category_name,
  	 productType.title 			AS  type_name,
  	 productLan.title			AS	title,
  	 productLan.short_content	AS	short_content,
  	 product.description		AS	description,
  	 product.create_date		AS  create_date,
  	 categorySub.title			AS	category_sub_name,
     product.create_by		    AS  create_by,
     product.approved_by		as  approved_by,
     product.approved_date		as  approved_date,
     product.published_by		as  published_by,
     product.published_date		as  published_date,
     product.is_highlights 		as is_highlights, 
     product.is_priority 		as is_priority, 
     product.sort 				as sort,
     product.is_microsite  		as microsite,
     product.IS_HIGHLIGHTS		as highlights,
     product.IS_LENDING			as lending,
     product.IS_PRIORITY		as priority,
     product.IS_HIGHLIGHTS_MATH_EXPRESS		as highlights_math_express,
     product.SHOW_FORM			as show_form,
     math.NAME					as tool_name,
     (select count(1) from m_faqs faqs
     	where CHARINDEX(faqs.M_PRODUCT_ID, product.ID, 1) > 0
     	AND faqs.delete_date is null
     )	number_faqs
     ,	CASE WHEN product.status = 1 THEN /*searchCond.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END						AS STATUS_NAME
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

ORDER BY category.title asc, product.sort asc, product.create_date desc
OFFSET /*offset*/ ROWS FETCH NEXT /*sizeOfPage*/ ROWS ONLY;