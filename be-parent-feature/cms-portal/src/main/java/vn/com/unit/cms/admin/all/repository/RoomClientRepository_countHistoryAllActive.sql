SELECT count(tb1.id)
FROM m_room_client tb1
LEFT JOIN m_product_category tb3 on tb1.product_id = tb3.id
LEFT JOIN jca_m_account tb2 on tb1.agent = tb2.username 
LEFT JOIN jca_m_account tb5 on tb1.assign = tb5.username 
LEFT JOIN
		(
			select clientid,min(created_date) as created_date
			from m_message
			group by clientid
		)tb4 on tb1.clientid  = tb4.clientid
LEFT JOIN M_SETTING_CHAT_CONTROL_VALUE  controlValue
    ON (controlValue.DELETE_DATE is NULL
        AND controlValue.VALUE_FIELD = tb1.message
        AND UPPER(controlValue.M_LANGUAGE_CODE) = UPPER(/*roomDto.lang*/'vi')
    )
	where tb1.created_date is not null
	/*BEGIN*/and (
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