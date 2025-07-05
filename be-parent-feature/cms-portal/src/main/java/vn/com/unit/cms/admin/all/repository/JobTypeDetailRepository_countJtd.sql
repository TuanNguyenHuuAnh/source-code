SELECT
	count(*)
FROM
	m_job_type_detail job_type_detail
LEFT JOIN m_job_type_detail_language jtd_lg ON job_type_detail.id = jtd_lg.m_type_detail 
WHERE
	job_type_detail.delete_by IS NULL
	AND jtd_lg.m_language_code = UPPER(/*jtdSearchDto.languageCode*/)
	/*BEGIN*/
	AND (
		/*IF jtdSearchDto.code != null && jtdSearchDto.code != ''*/
		OR job_type_detail.code LIKE CONCAT('%',/*jtdSearchDto.code*/,'%')
		/*END*/
	)
	/*END*/