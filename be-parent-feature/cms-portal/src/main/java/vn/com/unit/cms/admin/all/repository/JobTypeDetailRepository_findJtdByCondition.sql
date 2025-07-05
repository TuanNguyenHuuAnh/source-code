SELECT
	job_type_detail.id 				AS id,
	job_type_detail.code 			AS code,
	job_type_detail.m_type_id 		AS mTypeId,
	job_type_detail.m_type_sub_id 	AS mTypeSubId,
	job_type_detail.note 			AS note,
	job_type_detail.create_date 	AS createDate,
	jtd_lg.title 					AS title
FROM	
	m_job_type_detail job_type_detail
LEFT JOIN m_job_type_detail_language jtd_lg ON job_type_detail.id = jtd_lg.m_type_detail 
WHERE
	job_type_detail.delete_by IS NULL
	AND jtd_lg.m_language_code = UPPER(/*jtdSearchDto.languageCode*/)
	/*BEGIN*/
	AND(
		/*IF jtdSearchDto.code != null && jtdSearchDto.code != ''*/
	 	OR job_type_detail.code LIKE CONCAT('%',/*jtdSearchDto.code*/,'%')
		/*END*/
	)
	/*END*/ 
ORDER BY job_type_detail.create_date DESC