SELECT
    *
FROM
	m_job_type_sub typeSub
WHERE
	typeSub.delete_by IS NULL
	
	AND typeSub.code = /*code*/