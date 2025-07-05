SELECT
	id AS id,
	m_language_code AS m_language_id,
	m_type_sub_id AS m_type_sub_id,
	name AS name
FROM
	 m_job_type_sub_language job_type_sub_language
WHERE
	job_type_sub_language.delete_by IS NULL
	/*BEGIN*/
	AND(
		/*IF langCode != null && langCode != ''*/
	 	OR m_language_code = /*langCode*/
		/*END*/
	) 
	/*END*/