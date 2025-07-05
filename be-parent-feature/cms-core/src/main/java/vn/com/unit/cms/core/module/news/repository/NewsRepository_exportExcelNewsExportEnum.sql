--
-- NewsRepository_exportExcelNewsExportEnum.sql

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
	ROW_NUMBER() OVER (ORDER BY 	newsType.label 					ASC
	, news.sort 					ASC
	, news.create_date 				DESC
	, news.ENABLED 					DESC) as stt
  	, news.code					AS	code
  	, newsLan.title				AS	title
  	, newsType.label 			AS  type_name
  	, news.create_date			AS  create_date
  	, CASE WHEN news.headlines = 1 THEN 'x'
			ELSE  ''
	END							AS  headlines
  	, CASE WHEN news.enabled = 1 THEN 'x'
			ELSE  ''
	END							AS  enabled
  	, CASE WHEN news.latest_news = 1 THEN 'x'
			ELSE  ''
	END							AS  latest_news
  	, CASE WHEN news.press_release = 1 THEN 'x'
			ELSE  ''
	END							AS  press_release
  	, CASE WHEN news.is_promotion = 1 THEN 'x'
			ELSE  ''
	END							AS  promotion
  	, news.create_by			AS  create_by
  	, CASE WHEN news.status = 1 THEN /*searchCond.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END						AS STATUS_NAME
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
	AND news.customer_type_id = /*searchCond.custTypeId*/''
	
	/*IF searchCond.typeHighlight != null*/
	AND news.HEADLINES = /*searchCond.typeHighlight*/
	/*END*/
	
	/*IF searchCond.statusActive != null*/
	AND news.ENABLED = /*searchCond.statusActive*/
	/*END*/
	
	/*IF searchCond.promotion != null*/
	AND news.is_promotion = /*searchCond.promotion*/
	/*END*/
	
	/*IF searchCond.typeId != null*/
	AND news.m_news_type_id = /*searchCond.typeId*/
	/*END*/

	/*IF searchCond.categoryId != null*/
	AND news.M_NEWS_CATEGORY_ID = /*searchCond.categoryId*/
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
	AND REPLACE(newsLan.title, N'Ð', N'Đ') LIKE REPLACE(concat('%',/*searchCond.title*/,'%'), N'Ð', N'Đ')
	/*END*/
	
ORDER BY
	newsType.label 					ASC
	, news.sort 					ASC
	, news.create_date 				DESC
	, news.ENABLED 					DESC