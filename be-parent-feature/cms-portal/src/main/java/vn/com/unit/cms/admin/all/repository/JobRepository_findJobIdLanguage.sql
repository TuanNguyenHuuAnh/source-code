SELECT
	*	
FROM
	m_job_language 			    
WHERE 
	delete_by IS NULL
	/*IF jobId != null && jobId != ''*/
	AND m_job_id =/*jobId*/
	/*END*/
	ORDER BY create_date DESC