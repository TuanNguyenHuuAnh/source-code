SELECT
    DISTINCT tb1.id
    , tb1.clientid
    , tb1.fullname
    , tb1.created_date
    , controlValueService.TEXT_FIELD       AS  service
    , tb1.status
    , tb1.phone
    , controlValueNickName.TEXT_FIELD      AS  nickname
    , tb1.email
    , tb1.agent                            AS  agent
    , tb2.fullname                         AS  agent_name
    , tb1.TYPE_DISCONNECTED                AS  type_disconnected
    , tb1.DISCONNECTED_DATE                AS  disconnected_date
FROM
    m_room_client tb1
LEFT JOIN jca_m_account tb2 
    ON tb1.agent = tb2.username
LEFT JOIN M_SETTING_CHAT_CONTROL_VALUE  controlValueService
    ON (controlValueService.DELETE_DATE is NULL
        AND controlValueService.VALUE_FIELD = tb1.message
        AND UPPER(controlValueService.M_LANGUAGE_CODE) = UPPER(/*lang*/'vi')
    )
LEFT JOIN M_SETTING_CHAT_CONTROL_VALUE  controlValueNickName
    ON (controlValueNickName.DELETE_DATE is NULL
        AND UPPER(controlValueNickName.VALUE_FIELD) = UPPER(tb1.nickname)
        AND UPPER(controlValueNickName.M_LANGUAGE_CODE) = UPPER(/*lang*/'vi')
    )
WHERE
    tb1.created_date is not null
    AND TO_DATE(TB1.CREATED_DATE, 'YYYY-MM-DD') - TO_DATE(sysdate, 'YYYY-MM-DD') = 0
	/*IF roomDto.fullname != null && roomDto.fullname != ''*/
	AND UPPER(tb1.fullname)  like ('%'||  UPPER(/*roomDto.fullname*/) || '%')
	/*END*/
	/*IF roomDto.email != null && roomDto.email != ''*/
    AND UPPER(tb1.email)  like ('%'||  UPPER(/*roomDto.email*/) || '%')
    /*END*/
    /*IF roomDto.phone != null && roomDto.phone != ''*/
    AND UPPER(tb1.phone)  like ('%'||  UPPER(/*roomDto.phone*/) || '%')
    /*END*/
    /*IF roomDto.agent != null && roomDto.agent != ''*/
    AND UPPER(tb2.fullname)  like ('%'||  UPPER(/*roomDto.agent*/) || '%')
    /*END*/
    /*IF roomDto.service != null && roomDto.service != ''*/
    AND UPPER(tb1.message)  like ('%'||  UPPER(/*roomDto.service*/) || '%')
    /*END*/
    /*IF roomDto.status != null && roomDto.status != ''*/
    AND UPPER(tb1.status)  like ('%'||  UPPER(/*roomDto.status*/) || '%')
    /*END*/
ORDER BY tb1.status DESC, tb1.created_date DESC;