SELECT
	id AS id,
	m_language_code AS languageId,
	m_type_id AS typeId,
	name AS name
FROM
	 m_job_type_language job_type_language
WHERE
	job_type_language.delete_by IS NULL
	/*BEGIN*/
	AND(
		/*IF langCode != null && langCode != ''*/
	 	OR m_language_code = /*langCode*/
		/*END*/
	) 
	/*END*/