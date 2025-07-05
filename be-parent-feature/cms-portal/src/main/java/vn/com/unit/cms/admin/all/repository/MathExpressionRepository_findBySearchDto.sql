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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*searchDto.languageCode*/'VI')
)

SELECT  distinct math_expression.id
		,math_expression.code
		,math_expression.name
		,math_expression.expression
		,math_expression.description
		,math_expression.available_date_from
		,math_expression.available_date_to
		,math_expression.expression_type
		,math_expression.create_date
		,math_expression.status
		,math_expression.process_id
		,math_expression.create_by
		,math_expression.approved_by
		,math_expression.approved_date
		,math_expression.published_by
		,math_expression.published_date
		,math_expression.is_highlights
		,CASE WHEN math_expression.status = 1 THEN /*searchDto.statusName*/'Lưu nháp'
			  ELSE  stepStatus.STATUS_NAME  		END  			AS STATUS_NAME
	FROM
		m_math_expression math_expression
		LEFT JOIN m_customer_type customer_type ON math_expression.m_customer_type_id = customer_type.id
		LEFT JOIN stepStatus ON stepStatus.process_id = math_expression.process_id
							 AND stepStatus.step_no = math_expression.status
	WHERE 
		math_expression.delete_by is NULL
		and math_expression.m_customer_type_id = /*searchDto.customerTypeId*/''
		and math_expression.m_customer_type_id = /*searchDto.customerTypeId*/9
		/*IF searchDto.name != null && searchDto.name != ''*/
		AND math_expression.name like concat('%',/*searchDto.name*/,'%')
		/*END*/
		/*IF searchDto.code != null && searchDto.code != ''*/
		and math_expression.code like concat('%',/*searchDto.code*/,'%')
		/*END*/
		/*IF searchDto.status != null && searchDto.status != ''*/
		AND math_expression.status like concat('%',/*searchDto.status*/,'%')
		/*END*/
		/*IF searchDto.typeText != null && searchDto.typeText != ''*/
		AND math_expression.expression_type like concat('%',/*searchDto.typeText*/,'%')
		/*END*/
		ORDER BY
		math_expression.create_date DESC, math_expression.code ASC
		OFFSET /*offset*/ ROWS FETCH NEXT  /*limit*/ ROWS ONLY
		

