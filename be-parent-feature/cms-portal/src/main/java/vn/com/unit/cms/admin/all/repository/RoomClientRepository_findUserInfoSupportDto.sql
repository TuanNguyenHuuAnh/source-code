SELECT
	tb1.clientid
    , tb1.email
    , tb1.fullname
    , tb1.phone
	, controlValue.TEXT_FIELD              AS  service
	, controlValueNickName.TEXT_FIELD      AS  nickname
FROM
	M_ROOM_CLIENT tb1
LEFT JOIN M_SETTING_CHAT_CONTROL_VALUE  controlValue
    ON (controlValue.DELETE_DATE is NULL
        AND controlValue.VALUE_FIELD = tb1.message
        AND UPPER(controlValue.M_LANGUAGE_CODE) = UPPER('VI')
    )
LEFT JOIN M_SETTING_CHAT_CONTROL_VALUE  controlValueNickName
    ON (controlValueNickName.DELETE_DATE is NULL
        AND UPPER(controlValueNickName.VALUE_FIELD) = UPPER(tb1.nickname)
        AND UPPER(controlValueNickName.M_LANGUAGE_CODE) = UPPER('VI')
    )
WHERE
	tb1.created_date is NOT null
	AND tb1.clientid = /*clientid*/
	