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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*homepageSearchDto.lang*/'')
)

SELECT
	homepage.id 					AS id,
	homepage.speed_roll 			AS speed_roll,
	homepage.effective_date_from 	AS effective_date_from,
	homepage.effective_date_to 		AS effective_date_to,	
	homepage.description 			AS description,
	homepage.process_id 			AS process_id,
	homepage.create_date 			AS create_date,
	homepage.status_code 			AS status_code,
	homepage.create_by 				AS create_by,
	homepage.approve_by 			AS approve_by,
	homepage.approve_date 			AS approve_date,
	homepage.publish_by 			AS publish_by,
	homepage.publish_date 			AS publish_date,
	homepage.status					as status,
	acc_code.code					AS banner_page_name,
	homepage.m_banner_top_id		AS banner_top_id,
	homepage.m_banner_top_mobile_id	AS banner_top_mobile_id
	, CASE WHEN homepage.status = 1 THEN (/*homepageSearchDto.statusName*/'Lưu nháp')
			ELSE  stepStatus.STATUS_NAME
		END  									AS STATUS_NAME
FROM
	m_homepage_setting homepage
LEFT JOIN jca_constant_display acc_code ON acc_code.type = 'B01' AND acc_code.cat = homepage.banner_page
LEFT JOIN stepStatus
	ON stepStatus.process_id = homepage.process_id
	and stepStatus.step_no = homepage.status
WHERE
	homepage.delete_date IS NULL 
	/*BEGIN*/ AND (
	/*IF homepageSearchDto.status != null && homepageSearchDto.status != ''*/
	AND homepage.status = /*homepageSearchDto.status*/
	/*END*/
	/*IF homepageSearchDto.startDate != null*/
	AND CAST(homepage.effective_date_from as DATE) = CAST(/*homepageSearchDto.startDate*/'' as DATE)
	/*END*/
	
	/*IF homepageSearchDto.endDate != null*/
	AND CAST(homepage.effective_date_to as DATE) = CAST(/*homepageSearchDto.endDate*/'' as DATE)
	/*END*/
	/*IF homepageSearchDto.bannerPage != null && homepageSearchDto.bannerPage != ''*/
	AND acc_code.cat = /*homepageSearchDto.bannerPage*/
	/*END*/
	) /*END*/
ORDER BY
	homepage.create_date DESC
OFFSET /*offset*/0 ROWS FETCH NEXT /*pageSize*/10 ROWS ONLY;
