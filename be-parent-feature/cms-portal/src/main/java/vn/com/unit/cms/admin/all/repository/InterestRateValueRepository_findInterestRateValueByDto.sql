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
	        AND UPPER(statusLang.LANG_CODE) = UPPER(/*dto.languageCode*/'')
)
select  mirt.id,
		mirt.interest_rate_type,
		mirt.language_code,
	    mirt.value01,
	    mirt.value02,
	    mirt.value03,
	    mirt.value04,
	    mirt.value05,
	    mirt.value06,
	    mirt.value07,
	    mirt.value08,
	    mirt.value09,
	    mirt.value10,
	    mirt.m_customer_type_id as customer_type_id,
	    mirt.process_id,
	    mirt.status,
	    CASE WHEN mirt.status = 1 THEN /*dto.statusName*/'Lưu nháp'
	    	ELSE  stepStatus.STATUS_NAME
		END						AS status_name,
		mirt.update_date		AS	update_date
from m_interest_rate_value mirt
LEFT JOIN stepStatus
        ON stepStatus.process_id = mirt.process_id
        and stepStatus.step_no = mirt.status
where mirt.delete_date is null and mirt.delete_by is null

and mirt.interest_rate_type = /*dto.interestRateType*/

/*IF dto.customerTypeId != null*/
and mirt.m_customer_type_id = /*dto.customerTypeId*/
/*END*/
/*IF dto.languageCode != null*/
and UPPER(mirt.language_code) = UPPER(/*dto.languageCode*/)
/*END*/
order by mirt.id