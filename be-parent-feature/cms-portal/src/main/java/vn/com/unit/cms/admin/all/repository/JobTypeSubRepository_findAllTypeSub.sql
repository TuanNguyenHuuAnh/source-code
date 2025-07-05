select
	jobType.id as id,
	jobType.code as code,
	jobTypeLanguage.name as name
from
	m_job_type jobType
	LEFT JOIN m_job_type_language jobTypeLanguage ON (jobType.id = jobTypeLanguage.m_type_id and m_language_code = /*lang*/)
where 
	jobType.delete_by IS NULL
ORDER BY jobType.create_date