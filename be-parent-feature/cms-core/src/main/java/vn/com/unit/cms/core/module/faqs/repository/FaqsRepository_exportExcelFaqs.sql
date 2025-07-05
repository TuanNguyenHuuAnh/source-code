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
select 
	faqs.id				 	AS faqs_id,
	faqs.code 				AS code,
	faqsLang.title 			AS title,
    faqs.description 		AS description,
    faqs.create_date 		AS create_date,
    category.title 			AS category_name,
    faqs.process_id			AS	process_id,
  	faqs.status				AS	status,
    typeLang.title 			AS type_name,
    faqsLang.content 	    AS content,
    faqs.link_alias			AS link_alias,
    faqs.create_by			AS create_by,
    faqs.enabled			as enabled
	, STUFF((
		    SELECT '; ' + proLang.title
		    FROM m_product_language proLang 
		    WHERE 
				CHARINDEX(CONCAT(',', proLang.M_PRODUCT_ID, ','), CONCAT(',', faqs.M_PRODUCT_ID, ',')) > 0
				AND proLang.DELETE_DATE is NULL
				AND proLang.m_language_code = faqsLang.m_language_code
		     FOR XML PATH(''),TYPE).value('(./text())[1]','NVARCHAR(MAX)')
	  		,1,2,''
	  	) as product_name

		, STUFF((
		    SELECT '; ' + proCateLang.title
		    FROM m_product_category_language proCateLang
		    WHERE 
				 CHARINDEX(CONCAT(',', proCateLang.M_PRODUCT_CATEGORY_ID, ','), CONCAT(',', faqs.M_PRODUCT_CATEGORY_ID, ',')) > 0
				 AND proCateLang.DELETE_DATE is NULL
				 AND proCateLang.m_language_code = faqsLang.m_language_code
		     FOR XML PATH(''),TYPE).value('(./text())[1]','NVARCHAR(MAX)')
	  		,1,2,''
	  	) as product_category_name

		, STUFF((
		    SELECT ', ' + proCateSubLang.title
		    FROM m_product_category_sub_language proCateSubLang
		    WHERE
				CHARINDEX(CONCAT(',', proCateSubLang.M_PRODUCT_CATEGORY_SUB_ID, ','), CONCAT(',', faqs.M_PRODUCT_CATEGORY_SUB_ID, ',')) > 0
				AND proCateSubLang.DELETE_DATE is NULL
				AND proCateSubLang.m_language_code = faqsLang.m_language_code
		     FOR XML PATH(''),TYPE).value('(./text())[1]','NVARCHAR(MAX)')
	  		,1,2,''
	  	) as product_category_sub_name,
    faqs.M_CUSTOMER_TYPE_ID AS customer_id,
    CASE WHEN faqs.status = 1 THEN /*searchCond.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END  									AS STATUS_NAME
FROM
	m_faqs faqs
	LEFT JOIN m_faqs_language faqsLang ON (faqs.id = faqsLang.m_faqs_id)
	LEFT JOIN m_faqs_category_language category ON (category.m_faqs_category_id = faqs.m_faqs_category_id 
													AND category.m_language_code = faqsLang.m_language_code
													AND category.delete_date is null
													)
	LEFT JOIN m_faqs_type_language typeLang ON (typeLang.m_faqs_type_id = faqs.m_faqs_type_id 
												AND typeLang.m_language_code = faqsLang.m_language_code
												AND typeLang.delete_date is null)	
    LEFT JOIN stepStatus ON stepStatus.process_id = faqs.process_id
		and stepStatus.step_no = faqs.status
WHERE
	faqs.delete_date is null
	AND UPPER(faqsLang.m_language_code) = UPPER(/*searchCond.languageCode*/'vi')	
	AND faqs.m_customer_type_id = /*searchCond.customerId*/
	
	/*IF searchCond.typeId != null*/
	AND faqs.m_faqs_type_id =  /*searchCond.typeId*/
	/*END*/
	
	/*IF searchCond.categoryId != null*/
	AND faqs.m_faqs_category_id =  /*searchCond.categoryId*/
	/*END*/

	/*IF searchCond.code != null && searchCond.code != ''*/
	AND faqs.code LIKE concat('%',/*searchCond.code*/,'%')
	/*END*/

	/*IF searchCond.title != null && searchCond.title != ''*/
    AND faqsLang.title LIKE concat('%',/*searchCond.title*/,'%')
    /*END*/
    
    /*IF searchCond.status != null && searchCond.status != ''*/
	AND faqs.status = /*searchCond.status*/
	/*END*/
	
	/*IF searchCond.productId != null && searchCond.productId != ''*/
	AND CHARINDEX(CONCAT(',', /*searchCond.productId*/, ','), CONCAT(',',faqs.M_PRODUCT_ID, ',')) > 0
	/*END*/
	
	/*IF searchCond.productCategoryId != null && searchCond.productCategoryId != ''*/
	AND CHARINDEX(CONCAT(',', /*searchCond.productCategoryId*/, ','), CONCAT(',',faqs.M_PRODUCT_CATEGORY_ID, ',')) > 0
	/*END*/
	
	/*IF searchCond.productCategorySubId != null && searchCond.productCategorySubId != ''*/
	AND CHARINDEX(CONCAT(',', /*searchCond.productCategorySubId*/, ','), CONCAT(',',faqs.M_PRODUCT_CATEGORY_SUB_ID, ',')) > 0
	/*END*/
	
	/*IF searchCond.enabled != null*/
	and faqs.ENABLED = /*searchCond.enabled*/
	/*END*/
ORDER BY faqs.ENABLED DESC, faqs.sort ASC, faqs.create_date DESC
