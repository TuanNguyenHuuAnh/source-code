SELECT
	count(*)
FROM
    m_constant const
    LEFT JOIN m_constant_language ml ON const.code = ml.m_constant_code 
    LEFT JOIN jca_constant_display dis ON const.type = dis.cat AND dis.type = "JOB"
WHERE
    const.delete_by IS NULL
    AND ml.m_language_code = UPPER(/*constantDto.languageCode*/)
	/*IF constantDto.name != null && constantDto.name != ''*/
		AND ml.name LIKE concat('%',  /*constantDto.name*/, '%')
	/*END*/	
	/*IF constantDto.type != null && constantDto.type != ''*/
		AND const.type = /*constantDto.type*/
	/*END*/