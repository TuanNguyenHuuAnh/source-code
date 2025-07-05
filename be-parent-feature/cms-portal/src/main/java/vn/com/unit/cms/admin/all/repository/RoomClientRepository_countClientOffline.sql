SELECT
    count(*)
FROM m_room_client_offline tb1
WHERE tb1.created_date is NOT NULL
	/*BEGIN*/AND (
	
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
ORDER BY tb1.created_date DESC