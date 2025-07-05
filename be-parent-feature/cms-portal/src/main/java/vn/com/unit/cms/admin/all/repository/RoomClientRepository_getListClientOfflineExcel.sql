SELECT
	ROW_NUMBER() OVER (ORDER BY tb1.created_date DESC) AS stt
    , controlValue2.TEXT_FIELD              AS  nickname
    , tb1.*
	, controlValue.TEXT_FIELD				AS	service
	, tb2.fullname as agent_name
FROM m_room_client_offline tb1 
LEFT JOIN jca_m_account tb2 
    ON tb1.updated_by = tb2.username
LEFT JOIN M_SETTING_CHAT_CONTROL_VALUE  controlValue
    ON (controlValue.DELETE_DATE is NULL
        AND controlValue.VALUE_FIELD = tb1.message
        AND UPPER(controlValue.M_LANGUAGE_CODE) = UPPER(/*roomDto.language*/'vi')
    )
LEFT JOIN M_SETTING_CHAT_CONTROL_VALUE  controlValue2
    ON (controlValue2.DELETE_DATE is NULL
        AND UPPER(controlValue2.ID_FIELD) = UPPER(tb1.nickname)
        AND UPPER(controlValue2.M_LANGUAGE_CODE) = UPPER(/*roomDto.language*/'vi')
    )
where tb1.created_date is NOT NULL
	/*BEGIN*/and (
	
	/*IF roomDto.email != null && roomDto.email != ''*/
	OR UPPER(tb1.email)  LIKE ('%'||  UPPER(/*roomDto.email*/)  || '%')
	/*END*/
	
	/*IF roomDto.fullname != null && roomDto.fullname != ''*/
	OR UPPER(tb1.fullname)  LIKE ('%'||  UPPER(/*roomDto.fullname*/) || '%')
	/*END*/
	
	/*IF roomDto.phone != null && roomDto.phone != ''*/
	OR tb1.phone  LIKE ('%'||  /*roomDto.phone*/  || '%')
	/*END*/
	)/*END*/
	/*IF roomDto.fromDate != null*/
	AND TRUNC(tb1.CREATED_DATE) >= TRUNC(/*roomDto.fromDate*/)
	/*END*/
	/*IF roomDto.toDate != null*/
	AND TRUNC(tb1.CREATED_DATE) <= TRUNC(/*roomDto.toDate*/)
	/*END*/
ORDER BY tb1.created_date DESC;