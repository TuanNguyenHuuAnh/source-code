SELECT
    *
FROM
	m_job_type jobType
WHERE
	jobType.delete_by IS NULL
	
	AND jobType.code = /*code*/