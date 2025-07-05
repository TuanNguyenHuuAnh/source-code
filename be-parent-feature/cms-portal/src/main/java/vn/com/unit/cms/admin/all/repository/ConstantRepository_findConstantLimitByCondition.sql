SELECT
	const.id 			AS id,
	const. CODE 		AS CODE,
	const.type 			AS type,
	ml. NAME 			AS NAME,
	const.create_date 	AS create_date,
	ml.m_language_code	AS language_code,
	ml.m_constant_code 	AS constant_code,
	dis.code 			AS type_name,
	CASE
		WHEN job.`code` IS NULL THEN
			0
		ELSE
			1
		END AS check_update_delete
FROM
    m_constant const
    LEFT JOIN m_constant_language ml ON const.code = ml.m_constant_code  
    LEFT JOIN jca_constant_display dis ON const.type = dis.cat AND dis.type = "JOB"
    LEFT JOIN m_job job ON const. CODE = job.position OR const. CODE = job.division AND job.delete_by IS NULL
WHERE
    const.delete_by IS NULL
    AND ml.m_language_code = UPPER(/*constantDto.languageCode*/)
	/*IF constantDto.name != null && constantDto.name != ''*/
		AND ml.name LIKE concat('%',  /*constantDto.name*/, '%')
	/*END*/	
	/*IF constantDto.type != null && constantDto.type != ''*/
		AND const.type = /*constantDto.type*/
	/*END*/
	ORDER BY const.create_date DESC
	LIMIT /*sizeOfPage*/ OFFSET /*startIndex*/