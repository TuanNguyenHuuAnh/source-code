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

SELECT
	cust.id									AS	id
	, ROW_NUMBER() OVER( ORDER BY cust.create_date desc, cust.sort asc, custLang.title asc) as stt
	, cust.code								AS	code
	, cust.create_by						AS	create_by
	, cust.create_date						AS	create_date
	, cust.enabled							AS	enabled
	, custLang.title						AS	title
	, cust.status							AS	status
	,	CASE WHEN cust.status = 1 THEN /*searchDto.statusName*/'Lưu nháp'
			ELSE  stepStatus.STATUS_NAME
		END						AS STATUS_NAME
FROM M_CUSTOMER_VIP cust
LEFT JOIN M_CUSTOMER_VIP_LANGUAGE custLang
	ON (custLang.M_CUSTOMER_VIP_ID = cust.ID
		AND custLang.delete_by IS NULL
		AND UPPER(custLang.M_LANGUAGE_CODE) = UPPER('vi')
	)
LEFT JOIN stepStatus
	ON stepStatus.process_id = cust.process_id
	AND stepStatus.step_no = cust.status
WHERE
	cust.delete_date is NULL
	/*IF searchDto.fdi == 1*/
	AND cust.fdi = 1
	/*END*/
	/*IF searchDto.vip == 1*/
	AND cust.vip = 1
	/*END*/
	/*IF searchDto.title != null && searchDto.title != ''*/
	AND custLang.title LIKE concat('%',/*searchDto.title*/,'%')
	
	/*END*/
	/*IF searchDto.code != null && searchDto.code != ''*/
	AND cust.code LIKE concat('%',/*searchDto.code*/,'%')
	/*END*/
	/*IF searchDto.enabled != null*/
	AND cust.enabled = /*searchDto.enabled*/
	/*END*/
	/*IF searchDto.status != null && searchDto.status != ''*/
	AND cust.status = /*searchDto.status*/
	/*END*/
ORDER BY cust.create_date desc, cust.sort asc, custLang.title asc