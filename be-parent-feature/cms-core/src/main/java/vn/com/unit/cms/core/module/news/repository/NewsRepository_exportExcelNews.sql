--
-- NewsRepository_exportExcelNews.sql

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
	  news.id					AS	id
	, ROW_NUMBER() OVER(ORDER BY news.sort DESC) as stt  
  	, news.code					AS	code
  	, news.name					AS	name
  	, news.sub_title			AS	sub_title
  	, news.image_url			AS	image_url
  	, news.physical_img			AS  physical_img
  	, news.key_word				AS	key_word
  	, news.headlines			AS	headlines
  	, news.enabled				AS	enabled
  	, news.process_id			AS	process_id
  	, news.process_instance_id 	AS 	process_instance_id
  	, news.status				AS 	status
  	, category.label			AS  category_name
  	, newsType.label 			AS  type_name
  	, newsLan.title				AS	title
  	, newsLan.short_content		AS	short_content
  	, news.description			AS	description
  	, news.create_date			AS  create_date
  	, news.latest_news			AS  latest_news
  	, news.press_release		AS  press_release
  	, news.is_promotion			AS  promotion
  	, news.create_by			AS  create_by
  	, CASE WHEN news.status = 1 THEN /*searchCond.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END						AS STATUS_NAME
		,STUFF((
		    SELECT '; ' + proLang.title
		    FROM m_product_language proLang 
		    WHERE 
				CHARINDEX(CONCAT(',', proLang.M_PRODUCT_ID, ','), CONCAT(',', news.M_PRODUCT_ID, ',')) > 0
				AND proLang.DELETE_DATE is NULL
				AND proLang.m_language_code = newsLan.m_language_code
		    FOR XML PATH(''),TYPE).value('(./text())[1]','NVARCHAR(MAX)')
	  		,1,1,''
	  	) as product_name
		, STUFF((
		    SELECT '; ' + proCateLang.title
		    FROM m_product_category_language proCateLang
		    WHERE 
				 CHARINDEX(CONCAT(',', proCateLang.M_PRODUCT_CATEGORY_ID, ','), CONCAT(',', news.M_PRODUCT_CATEGORY_ID, ',')) > 0
				 AND proCateLang.DELETE_DATE is NULL
				 AND proCateLang.m_language_code = newsLan.m_language_code
		    FOR XML PATH(''),TYPE).value('(./text())[1]','NVARCHAR(MAX)')
	  		,1,1,''
	  	) as product_category_name
		, STUFF((
		    SELECT '; ' + proCateSubLang.title
		    FROM m_product_category_sub_language proCateSubLang
		    WHERE 
				CHARINDEX(CONCAT(',', proCateSubLang.M_PRODUCT_CATEGORY_SUB_ID, ','), CONCAT(',', news.M_PRODUCT_CATEGORY_SUB_ID, ',')) > 0
				AND proCateSubLang.DELETE_DATE is NULL
				AND proCateSubLang.m_language_code = newsLan.m_language_code
		    FOR XML PATH(''),TYPE).value('(./text())[1]','NVARCHAR(MAX)')
	  		,1,2,''
	  	) as product_category_sub_name
FROM m_news news
LEFT JOIN m_news_language newsLan ON (newsLan.m_news_id = news.id AND newsLan.delete_date is null)
LEFT JOIN m_news_category_language category ON (category.m_news_category_id = news.m_news_category_id 
												AND category.m_language_code = newsLan.m_language_code
												AND category.delete_date is null)
LEFT JOIN m_news_type_language newsType ON (newsType.m_news_type_id = news.m_news_type_id 
											AND newsType.m_language_code = newsLan.m_language_code
											AND newsType.delete_date is null
											)
LEFT JOIN stepStatus
	ON stepStatus.process_id = news.process_id
	and stepStatus.step_no = news.status
	
WHERE
	news.delete_date is null
	AND UPPER(newsLan.m_language_code) = UPPER(/*searchCond.languageCode*/'vi')	
	AND news.customer_type_id = /*searchCond.custTypeId*/9
	
	/*IF searchCond.typeHighlight != null*/
	AND news.HEADLINES = /*searchCond.typeHighlight*/
	/*END*/
	
	/*IF searchCond.statusActive != null*/
	AND news.ENABLED = /*searchCond.statusActive*/
	/*END*/
	
	/*IF searchCond.typeId != null*/
	AND news.m_news_type_id = /*searchCond.typeId*/
	/*END*/
	
	/*IF searchCond.categoryId != null*/
	AND news.M_NEWS_CATEGORY_ID = /*searchCond.categoryId*/
	/*END*/
	
	/*IF searchCond.productId != null && searchCond.productId != ''*/
	AND CHARINDEX(CONCAT(',', /*searchCond.productId*/, ','), CONCAT(',',news.M_PRODUCT_ID, ',')) > 0
	/*END*/
	
	/*IF searchCond.productCategoryId != null && searchCond.productCategoryId != ''*/
	AND CHARINDEX(CONCAT(',', /*searchCond.productCategoryId*/, ','), CONCAT(',',news.M_PRODUCT_CATEGORY_ID, ',')) > 0
	/*END*/
	
	/*IF searchCond.productCategorySubId != null && searchCond.productCategorySubId != ''*/
	AND CHARINDEX(CONCAT(',',/*searchCond.productCategorySubId*/, ','), CONCAT(',',news.M_PRODUCT_CATEGORY_SUB_ID, ',')) > 0
	/*END*/
	
	/*IF searchCond.status != null && searchCond.status != ''*/
	AND news.status = /*searchCond.status*/
	/*END*/
	
	/*IF searchCond.latestNews != null*/
	AND news.LATEST_NEWS = /*searchCond.latestNews*/
	/*END*/
	
	/*IF searchCond.pressRelease != null*/
	AND news.PRESS_RELEASE = /*searchCond.pressRelease*/
	/*END*/
	
	/*IF searchCond.code != null && searchCond.code != ''*/
	AND news.code LIKE concat('%', /*searchCond.code*/,'%')
	/*END*/
	
	/*IF searchCond.title != null && searchCond.title != ''*/
	AND newsLan.title LIKE concat('%', /*searchCond.title*/,'%')
	/*END*/
	
ORDER BY
--	  product_category_name 		ASC
--	, product_category_sub_name 	ASC
--	, product_name 					ASC
	  news.sort 					DESC
	, newsType.label 				ASC
	, news.create_date 				DESC
	, news.ENABLED 					DESC