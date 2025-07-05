SELECT
	const.id 		AS id,
	const. CODE 	AS CODE,
	const.type 		AS type,
	ml. NAME 		AS NAME
FROM
	m_constant const
LEFT JOIN m_constant_language ml ON const. CODE = ml.m_constant_code
WHERE
	const.delete_by IS NULL
	AND ml.m_language_code = UPPER(/*languageCode*/)
	AND const.type = /*type*/
	ORDER BY ml.NAME COLLATE utf8_croatian_ci