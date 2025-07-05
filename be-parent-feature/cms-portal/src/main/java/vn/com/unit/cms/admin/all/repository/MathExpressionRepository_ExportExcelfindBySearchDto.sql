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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*searchDto.languageCode*/'')
)

SELECT   ROW_NUMBER() OVER (ORDER BY math_expression.create_date DESC, math_expression.code ASC) AS stt
		,math_expression.code as code 
		,case when UPPER(math_expression.expression_type) = UPPER('SAVING') then 'Gửi' 
          	  when UPPER(math_expression.expression_type) = UPPER('BORROW') then 'Vay' 
          	  else '' end as expression_type
		,math_expression.name as name
    	,math_expression.is_highlights as is_highlights
    	,(Case math_expression.is_highlights WHEN 1 THEN 'x' ELSE '' END) as is_highlights_string
		,math_expression.description as description 
    	,math_expression.max_interest_rate as max_interest_rate
    	,math_expression.max_interest_rate as max_interest_rate_str
		,math_expression.term_value as term_value
		,math_expression.max_loan_amount as max_loan_amount
		,math_expression.max_loan_amount as max_loan_amount_str
		,math_expression.math_expresss_comment as math_expresss_comment
		,CASE WHEN math_expression.status = 1 THEN /*searchDto.statusName*/'Lưu nháp'
			  ELSE  stepStatus.STATUS_NAME  		END  			AS status_name
		,math_expression.create_date as create_date
		,math_expression.create_by as create_by
	FROM
		m_math_expression math_expression
	LEFT JOIN m_customer_type customer_type ON math_expression.m_customer_type_id = customer_type.id
	LEFT JOIN stepStatus ON stepStatus.process_id = math_expression.process_id
							 AND stepStatus.step_no = math_expression.status
	WHERE 
		math_expression.delete_by is NULL
		and math_expression.m_customer_type_id = /*searchDto.customerTypeId*/''
		/*IF searchDto.name != null && searchDto.name != ''*/
		AND math_expression.name like concat('%',/*searchDto.name*/,'%')
		/*END*/
		/*IF searchDto.code != null && searchDto.code != ''*/
		and math_expression.code like concat('%',/*searchDto.code*/,'%')
		/*END*/
		/*IF searchDto.status != null*/
		AND math_expression.status like concat('%',/*searchDto.status*/,'%')
		/*END*/
		/*IF searchDto.typeText != null && searchDto.typeText != ''*/
		AND math_expression.expression_type like concat('%',/*searchDto.typeText*/,'%')
		/*END*/
	ORDER BY math_expression.create_date DESC, math_expression.code ASC

		

