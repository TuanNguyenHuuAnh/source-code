SELECT
    job.id 			AS job_id,
	job.CODE 		AS job_code,	
	job.career 		AS career_id,
	job.create_date	AS create_date,
	job.link_alias	AS link_alias,
	job.expiry_date AS expiry_date,
	joblg.job_title AS job_title
FROM
    m_job job
LEFT JOIN m_job_language joblg ON job.id = joblg.m_job_id AND joblg.m_language_code = /*languageCode*/'vi'
WHERE job.delete_by is NULL
AND job.expiry_date >= /*currentDate*/'2017-10-31'
ORDER BY
   job.sort ASC, job.create_date DESC, job.code ASC