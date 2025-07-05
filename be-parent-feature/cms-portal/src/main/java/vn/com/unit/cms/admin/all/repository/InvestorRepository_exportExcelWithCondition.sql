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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*searchDto.language*/'')
)
SELECT 
	ROW_NUMBER() OVER (ORDER BY investorCateLang.title, investor.SORT, investor.CREATE_DATE DESC) AS stt
    , investorLang.title									AS	title
    , investor.M_INVESTOR_CATEGORY_ID					AS	category_id
    , display.CAT_OFFICIAL_NAME			AS  kind_name
    , display.CAT_ABBR_NAME				AS  kind_name_attr
    , CASE WHEN investor.enabled = 1 THEN 'x'
			ELSE  ''
	END							AS enabled
    , investor.*
    , CASE WHEN investor.status = 1 THEN N'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END						AS STATUS_NAME
FROM 
    m_investor  investor
LEFT JOIN m_investor_language   investorLang
    ON (investor.id = investorlang.m_investor_id
        AND investorlang.delete_date is NULL
        AND UPPER(investorlang.m_language_code) = UPPER(/*searchDto.language*/'vi')
    )
LEFT JOIN m_investor_category   investorCate
    ON (investorCate.delete_date is NULL
        AND investorCate.status = 99
        AND investorCate.id = investor.M_INVESTOR_CATEGORY_ID
    )
LEFT JOIN m_investor_category_language investorCateLang
    ON (investor.M_INVESTOR_CATEGORY_ID = investorCateLang.M_INVESTOR_CATEGORY_ID
        AND investorCateLang.delete_date is NULL
        AND UPPER(investorCateLang.m_language_code) = UPPER(/*searchDto.language*/'vi') 
    )
LEFT JOIN jca_constant_display display
    ON (display.type = 'NDT'
        AND display.delete_date is NULL
        AND display.cat = investorCate.INVESTOR_CATEGORY_TYPE
    )
LEFT JOIN stepStatus
	ON stepStatus.process_id = investor.process_id
	and stepStatus.step_no = investor.status
WHERE
    investor.delete_date is NULL
	/*IF searchDto.status != null*/
	AND investor.status = /*searchDto.status*/
	/*END*/
    /*IF searchDto.name != null && searchDto.name != ''*/
	AND investorLang.title LIKE ('%',/*searchDto.name*/,'%')
	/*END*/
    /*IF searchDto.enabled != null && searchDto.enabled != ''*/
	AND investor.enabled = /*searchDto.enabled*/
	/*END*/
    /*IF searchDto.categoryId != null && searchDto.categoryId != ''*/
	AND investor.M_INVESTOR_CATEGORY_ID = /*searchDto.categoryId*/
	/*END*/
    /*IF searchDto.kind != null*/
	AND investor.kind = /*searchDto.kind*/
	/*END*/
ORDER BY investorCateLang.title, investor.SORT, investor.CREATE_DATE DESC