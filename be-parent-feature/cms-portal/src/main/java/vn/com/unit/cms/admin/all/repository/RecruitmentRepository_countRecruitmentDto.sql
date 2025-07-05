SELECT count(*)
FROM m_recruitment_test tbr
WHERE  tbr.deleted_date is null
	/*BEGIN*/AND (
	/*IF recDto.position != null && recDto.position != ''*/
	OR tbr.position LIKE concat('%',  /*recDto.position*/, '%')
	/*END*/
	)/*END*/