SELECT
	job_type_detail.id 				as id,
	job_type_detail.code 			as code,
	job_type_detail.m_type_id 		as mTypeId,
	job_type_detail.m_type_sub_id 	as mTypeSubId,
	job_type_detail.note 			as note,
	job_type_detail.create_date 	as createDate
FROM
	m_job_type_detail job_type_detail
WHERE
	job_type_detail.delete_by IS NULL
	/*BEGIN*/
	AND(
		/*IF jtdId != null && jtdId != ''*/
	 	OR job_type_detail.id = /*jtdId*/
		/*END*/
	) 
	/*END*/