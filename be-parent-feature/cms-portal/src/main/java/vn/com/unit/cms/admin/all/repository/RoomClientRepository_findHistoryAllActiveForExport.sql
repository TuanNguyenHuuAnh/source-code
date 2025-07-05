SELECT
    ROW_NUMBER() OVER (ORDER BY tb1.created_date DESC, NLSSORT(tb2.fullname,'NLS_SORT=generic_m') ASC) AS stt
    , tb1.id
    , tb1.clientid
    , tb1.email
    , tb1.fullname
    , tb1.phone
    , tb2.fullname                         AS agent_name
    , controlValue.TEXT_FIELD              AS  service
    , tb1.status
    , tb1.created_date
    , tb1.disconnected_date
    , (case when tb4.created_date - tb1.created_date > 0 
			THEN cast(tb4.created_date - tb1.created_date AS char(50)) 
	   else
			cast(tb1.created_date - tb4.created_date AS char(50))             
		END ) AS total_wait
	, cast(tb1.disconnected_date - tb1.created_date AS char(50)) AS total_support
	, tb1.type_disconnected
	, tb5.fullname AS assign_name
FROM m_room_client tb1
LEFT JOIN jca_m_account tb2 on tb1.agent = tb2.username 
LEFT JOIN jca_m_account tb5 on tb1.assign = tb5.username 
LEFT JOIN
		(SELECT clientid,min(created_date) AS created_date
			FROM m_message
			GROUP BY clientid
		)tb4 on tb1.clientid  = tb4.clientid
LEFT JOIN M_SETTING_CHAT_CONTROL_VALUE  controlValue
    ON (controlValue.DELETE_DATE is NULL
        AND controlValue.VALUE_FIELD = tb1.message
        AND UPPER(controlValue.M_LANGUAGE_CODE) = UPPER(/*roomDto.lang*/'vi')
    )
WHERE tb1.created_date is NOT null
	/*BEGIN*/AND (
        /*IF roomDto.agent != null && roomDto.agent != ''*/
        OR UPPER(tb2.fullname) like ('%' || UPPER(/*roomDto.agent*/) || '%')
        /*END*/
        /*IF roomDto.service != null && roomDto.service != ''*/
        OR UPPER(controlValue.TEXT_FIELD) like ('%' || UPPER(/*roomDto.service*/) || '%')
        /*END*/
        /*IF roomDto.fullname != null && roomDto.fullname != ''*/
        OR UPPER(tb1.fullname) like ('%' || UPPER(/*roomDto.fullname*/) || '%')
        /*END*/
        /*IF roomDto.status != null && roomDto.status != ''*/
        OR tb1.status = /*roomDto.status*/
        /*END*/
        /*IF roomDto.phone != null && roomDto.phone != ''*/
        OR tb1.phone like ('%' || /*roomDto.phone*/ || '%')
        /*END*/
	)/*END*/
	/*IF roomDto.fromDate != null*/
	AND TRUNC(tb1.created_date) >= TRUNC(/*roomDto.fromDate*/)
    /*END*/
	/*IF roomDto.toDate != null*/
	AND TRUNC(tb1.created_date) <= TRUNC(/*roomDto.toDate*/)
	/*END*/
ORDER BY tb1.created_date DESC, NLSSORT(tb2.fullname,'NLS_SORT=generic_m') ASC