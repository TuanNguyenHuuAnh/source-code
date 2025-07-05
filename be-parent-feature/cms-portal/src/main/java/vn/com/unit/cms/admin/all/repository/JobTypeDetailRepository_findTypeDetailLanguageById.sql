SELECT
	job_type_detail_language.id 				as id,
	job_type_detail_language.m_language_code 	as languageCode,
	job_type_detail_language.m_type_detail 		as typeId,
	job_type_detail_language.title 				as title,
	job_type_detail_language.description 		as description
FROM
	m_job_type_detail_language job_type_detail_language
WHERE
	job_type_detail_language.delete_by IS NULL
	/*BEGIN*/
	AND (
		/*IF typeDetailId != null && typeDetailId != ''*/
		OR job_type_detail_language.m_type_detail = /*typeDetailId*/
		/*END*/
	)
	/*END*/
ORDER BY job_type_detail_language.m_type_detail ASC