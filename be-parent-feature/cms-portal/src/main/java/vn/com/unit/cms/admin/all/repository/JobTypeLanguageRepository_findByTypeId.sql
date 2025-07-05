SELECT *
FROM m_job_type_language
WHERE delete_date is null
	AND m_type_id = /*typeId*/